/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package org.ljc.adoptojdk.javacWarningsSummary;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.SourceVersion;
import javax.tools.Diagnostic;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;

import com.sun.source.util.JavacTask;
import com.sun.tools.javac.code.Lint.LintCategory;
import com.sun.tools.javac.util.JCDiagnostic;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.tools.DiagnosticListener;
import javax.tools.JavaFileManager;

/**
 * Simple program that prints a summary of how many of each type of warning
 * are generated in files and packages.
 *
 * Usage:
 *  java -jar JavacWarnings.jar options files-or-packages
 *
 * Options:
 *  -bootclasspath, -classpath, -sourcepath, all as for javac
 *  -Xlint and -Xlint:opts as for javac
 *
 * Files-or-packages
 *  Files are recognized by ending in .java
 *  Packages can be given as p.q or p.q.**.  They are expanded using the
 *  value of -sourcepath. p.q expands to the set of compilation units in
 *  package p.q; p.q.** expands to the set of compilation units in package
 *  p.q and all subpackages.  Take care to quote the wildcard form when
 *  necessary, e.g. on a shell command line.
 *
 * @author jjg
 */
public class JavacWarningsSummary {

    private static final int LAST_THREE_PLACES = 3;

	/**
     * Command-line entry point.
     * @param args command line args
     */
    public static void main(String[] args) {
        try {
            JavacWarningsSummary m = new JavacWarningsSummary();
            boolean ok = m.run(args);
            if (!ok) {
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.format("IO error: " + e);
            System.exit(2);
        }
    }

    /**
     * API entry point.
     * @param args command line args
     * @return true if operation completed successfully
     * @throws IOException if an IO error occurs during eexcution
     */
    boolean  run(String... args) throws IOException {
        processArgs(args);
        if (errors > 0) {
            return false;
        }
        
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fm = javac.getStandardFileManager(null, null, null);
        if (bootclasspath != null) {
            fm.setLocation(StandardLocation.PLATFORM_CLASS_PATH, pathToFiles(bootclasspath));
        }
        if (classpath != null) {
            fm.setLocation(StandardLocation.CLASS_PATH, pathToFiles(classpath));
        }
        if (sourcepath != null) {
            fm.setLocation(StandardLocation.SOURCE_PATH, pathToFiles(sourcepath));
        }
        List<? extends JavaFileObject> files = getFiles(fm, items);
        List<String> opts = new ArrayList<String>();
        if (xlint.isEmpty()) {
            opts.add("-Xlint:all");
        } else {
            opts.addAll(xlint);
        }
        opts.addAll(Arrays.asList("-Xmaxwarns", "9999"));
        Table t =  new Table(fm, files);
        System.out.format("Compiling " + files.size() + " files");
        JavacTask task = (JavacTask) javac.getTask(null, fm, t, opts, null, files);
        task.analyze();
        t.print(System.out);

        return true;
    }

    /**
     * Expand the set of command line items into a list of JavaFileObjects.
     * @param fm the file manager to use
     * @param items a list of command line items to be expanded
     * @return a list of JavaFileObjects
     * @throws IOException if an error occurs
     */
    List<JavaFileObject> getFiles(StandardJavaFileManager fm, List<String> items)
            throws IOException {
        List<JavaFileObject> files = new ArrayList<JavaFileObject>();
        for (String item: items) {
            if (item.endsWith(".java")) {
                addAll(files, fm.getJavaFileObjects(item));
            } else {
                boolean recursive = false;
                if (item.endsWith(".**")) {
                    item = item.substring(0, item.length() - LAST_THREE_PLACES);
                    recursive=true;
                }
                addAll(files, fm.list(StandardLocation.SOURCE_PATH,
                        item,
                        EnumSet.of(JavaFileObject.Kind.SOURCE),
                        recursive));
            }
        }
        return files;
    }

    /**
     * Utility method to add all members of an iterable to a Collection.
     * @param <T> The type of the each item
     * @param dest The collection to which to add the items
     * @param items The source of items to be added to the collection
     */
    static <T> void addAll(Collection<T> dest, Iterable<? extends T> items) {
        for (T item: items) {
            dest.add(item);
        }
    }

    /**
     * Convert a path option to a list of files, ignoring entries
     * which do not exist or cannot be read.
     * @param path the path value to be split
     * @return a list of files
     */
    List<File> pathToFiles(String path) {
        List<File> files = new ArrayList<File>();
        for (String p: path.split(File.pathSeparator)) {
            File f = new File(p);
            if (f.canRead()) {
                files.add(f);
            }
        }
        return files;
    }

    /**
     * Process command-line arguments.
     * @param args the arguments to be processed
     */
    void processArgs(String... args) {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            if (arg.equals("-bootclasspath") && i + 1 < args.length) {
                bootclasspath = args[++i];
            } else if (arg.equals("-classpath") && i + 1 < args.length) {
                classpath = args[++i];
            } else if (arg.equals("-sourcepath") && i + 1 < args.length) {
                sourcepath = args[++i];
            } else if (arg.startsWith("-Xlint")) {
                xlint.add(arg);
            } else if (arg.startsWith("-")) {
                error("Unrecognized option: " + arg);
            } else if ((arg.endsWith(".java") && new File(arg).exists())) {
                items.add(arg);
            } else if (SourceVersion.isName(arg) 
                    || arg.endsWith(".**") && SourceVersion.isName(arg.substring(0, arg.length() - LAST_THREE_PLACES))) {
                items.add(arg);
            } else {
                error("Unrecognized argument: " + arg);
            }
        }
    }

    /**
     * Record an error message.
     * @param msg the message
     */
    void error(String msg) {
        System.err.format(msg);
        errors++;
    }

    private String bootclasspath;
    private String classpath;
    private String sourcepath;
    private Set<String> xlint = new LinkedHashSet<String>();
    private List<String> items = new ArrayList<String>();

    /** The number of errors that have been reported. */
    private int errors;

    /**
     * Main data structure recording the number of each type of warning
     * encountered in each package.
     */
    static class Table implements DiagnosticListener<JavaFileObject> {

        private static final int EIGHT_PLACES = 8;

		static class Row {
        	private Map<String, Integer> counts = new HashMap<String, Integer>();
            void inc(String warn) {
                counts.put(warn, getCount(warn) + 1);
            }
            int getCount(String warn) {
                Integer v = counts.get(warn);
                return v == null ? 0 : v;
            }
        }

		private JavaFileManager fm;
        private Set<JavaFileObject> files;
        private Set<String> headings = new TreeSet<String>();
        private Map<String, Row> map = new TreeMap<String, Row>();
        private Row totals = new Row();
        private Map<Diagnostic.Kind, Integer> kindCounts
                = new EnumMap<Diagnostic.Kind, Integer>(Diagnostic.Kind.class);

        Table(JavaFileManager fm, Collection<? extends JavaFileObject> files) {
            this.fm = fm;
            this.files = new HashSet<JavaFileObject>(files);
        }

        void inc(String pkg, String warn) {
            Row row = map.get(pkg);
            if (row == null) {
            	row = new Row();
                map.put(pkg, row);
            }
            headings.add(warn);
            row.inc(warn);
            totals.inc(warn);
        }

        @Override
        public void report(Diagnostic<? extends JavaFileObject> d) {
            if (d.getCode().equals("compiler.warn.sun.proprietary")) {
                return;
            }

            if (!files.contains(d.getSource())) {
                return;
            }

            Integer kc = kindCounts.get(d.getKind());
            kindCounts.put(d.getKind(), kc == null ? 1 : kc + 1);

            switch (d.getKind()) {
                case ERROR:
                    System.err.format(d.getSource().getName() + ": " + d.getMessage(null));
                    return;
                case NOTE:
                    return;
            }

            JCDiagnostic jd = (JCDiagnostic) d;
            LintCategory lc = jd.getLintCategory();
            String warn = lc == null ? "default" : lc.toString().toLowerCase();
            JavaFileObject f = d.getSource();
            String binaryName = fm.inferBinaryName(StandardLocation.SOURCE_PATH, f);
            int lastDot = binaryName.lastIndexOf('.');
            String pkgName = (lastDot == -1) ? "" : binaryName.substring(0, lastDot);
            inc(pkgName, warn);
        }

        void print(PrintStream out) {
            if (map.isEmpty()) {
                System.err.format("No warnings found");
                return;
            }
            
            printHeadings(out);
            for (Map.Entry<String, Row> e: map.entrySet()) {
                printRow(out, e.getKey(), e.getValue());
            }
            printRow(out, "(total)", totals);

            out.println();
            String sep = "";
            for (Diagnostic.Kind k: Diagnostic.Kind.values()) {
                Integer count = kindCounts.get(k);
                if (count != null) {
                    out.print(sep + k.toString().toLowerCase() + ": " + count);
                    sep = ", ";
                }
            }
            out.println();
        }

        void printHeadings(PrintStream out) {
            int col1w = 0;
            for (String pkg: map.keySet()) {
                col1w = Math.max(col1w, pkg.length());
            }
            col1f = "%-" + col1w + "s";
            out.print(String.format(col1f, ""));
            for (String w: headings) {
                String head = w.length() <= EIGHT_PLACES ? w : w.substring(0, EIGHT_PLACES);
                out.print(String.format(" %8s", head));
            }
            out.print(String.format(" %8s", "(total)"));
            out.println();
        }

        void printRow(PrintStream out, String pkg, Row row) {
            int total = 0;
            out.print(String.format(col1f, pkg));
            for (String w: headings) {
                int v = row.getCount(w);
                out.print(String.format(" %8d", v));
                total += v;
            }
            out.print(String.format(" %8d", total));
            out.println();
        }

        private  String col1f;
    }
}
