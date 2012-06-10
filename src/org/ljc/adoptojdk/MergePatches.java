package org.ljc.adoptojdk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MergePatches {

    /* 
     * This can be tuned to say how willing the program is to merging patches 
     * from different packages, higher = patches further apart in the
     * package tree will be merged.
     */
    public final static int MERGIENESS = 3;

    private Path globalRoot;
    
    public static void main(String[] args) throws IOException {
        checkArguments(args);
        Path path = Paths.get(args[0]);
        MergePatches mergePatches = new MergePatches(path);
        mergePatches.generateShellScript(path);
    }

    private static void checkArguments(String[] args) {
        if (args == null || args.length != 1) {
            System.out.println("Please pass in one argument, the root of the "
                    + "path that contains the patches you want merged.  This is "
                    + "typically $ADOPT_OPENJDK/reviewed.");
            System.exit(-1);
        }
    }

    public MergePatches(Path path) throws IOException {
        globalRoot = path;
    }

    private void generateShellScript(Path path) throws IOException {
        PatchVisitor visitor = new PatchVisitor(path);
        Files.walkFileTree(path, visitor);

        List<List<DirectoryTreeNode>> out = new ArrayList<>();
        final DirectoryTreeNode rootNode = visitor.getRootNode();

        // Retrieve groups of diffs until the tree is empty
        while (rootNode.getSize() > 1) {
            List<DirectoryTreeNode> bag = rootNode.getDepthFirstGetBags();

            int bagSize = findListSize(bag.size());

            while (bag.size() > 0) {
                List<DirectoryTreeNode> newBag = new ArrayList<>(bag.subList(0, Math.min(bag.size(), bagSize)));
                bag.removeAll(newBag);
                out.add(newBag);
            }
        }

        printBags(out);
    }

    private void printBags(List<List<DirectoryTreeNode>> out) {

        List<String> usedNames = new ArrayList<>();
        for (List<DirectoryTreeNode> bag : out) {

            Path lowestCommonAnc = null;
            //Find common parent
            for (DirectoryTreeNode file : bag) {
                if (lowestCommonAnc == null) {
                    lowestCommonAnc = file.location;
                }
                while (!file.location.startsWith(lowestCommonAnc)) {
                    lowestCommonAnc = lowestCommonAnc.getParent();
                }
            }

            String patchName = globalRoot.relativize(lowestCommonAnc).toString().replace("/", "_") + ".patch";

            int i = 1;
            while (usedNames.contains(patchName)) {
                patchName = globalRoot.relativize(lowestCommonAnc).toString().replace("/", "_") + "_" + i + ".patch";
                i++;
            }
            usedNames.add(patchName);

            System.out.println("cat \\");
            for (DirectoryTreeNode file : bag) {
                System.out.println(globalRoot.relativize(file.location) + " \\");
            }
            System.out.println(" > diffs/" + patchName + "\n\n");
        }

    }

    private static int findListSize(int n) {
        if (n < 10) {
            return n;
        }

        int min = 5;
        for (int i = 5; i < 11; i++) {
            if (n % i == 0) {
                return i;
            }

            if (n % min < n % i) {
                min = i;
            }
        }
        return min;
    }
}
