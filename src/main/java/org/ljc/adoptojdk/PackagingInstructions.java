package org.ljc.adoptojdk;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

public class PackagingInstructions {

    public static final ImmutableMap<String, Collection<String>> PACKAGING_DEFINITIONS =
               new ImmutableMap.Builder<String, Collection<String>>()
                   .put("AWT-Component",      Arrays.asList(".*java.awt.Component.*"))
                   .put("AWT-Container",      Arrays.asList(".*java.awt.Container.*"))
                   .put("AWT-Window",         Arrays.asList(".*java.awt.Window.*"))
                   .put("AWT-Frame",          Arrays.asList(".*java.awt.Frame.*"))
                   .put("AWT-Dialog",         Arrays.asList(".*java.awt.Dialog.*"))
                   .put("AWT-Menu",           Arrays.asList(".*java.awt.Menu.*"))
                   .put("AWT-MenuItem",       Arrays.asList(".*java.awt.MenuItem.*"))
                   .put("AWT-MenuBar",        Arrays.asList(".*java.awt.MenuBar.*"))
                   .put("AWT-PopupMenu",      Arrays.asList(".*java.awt.PopupMenu.*"))
                   .put("AWT-MenuComponent",  Arrays.asList(".*java.awt.MenuComponent.*"))
                   .put("AWT-Widget",         Arrays.asList(".*java.awt.Canvas.*", ".*java.awt.TextComponent.*", ".*java.awt.AWTEventMulticaster.*", ".*java.awt.Container.*", ".*java.awt.Label.*", ".*java.awt.List.*", ".*java.awt.Choice.*", ".*java.awt.Checkbox.*", ".*java.awt.Button.*", ".*java.awt.Scrollbar.*"))
                   .put("AWT-Keyboard",       Arrays.asList(".*java.awt.KeyboardFocusManager.*",".*java.awt.DefaultKeyboardFocusManager.*"))
                   .put("AWT-Toolkit",        Arrays.asList(".*java.awt.Toolkit.*"))
                   .put("AWT-datatransfer",   Arrays.asList(".*java.awt.datatransfer.*"))
                   .put("AWT-dnd",            Arrays.asList(".*java.awt.dnd.*"))
                   .build();

    public static ImmutableMap<String, String> PACKAGE_REGEX_TO_PATCH_NAME;

    static {
        Map<String, String> result = new HashMap<>();

        for (Entry<String, Collection<String>> entry : PACKAGING_DEFINITIONS.entrySet()) {
            for (String packageRegex : entry.getValue()) {
                result.put(packageRegex, entry.getKey());
            }
        }

        PACKAGE_REGEX_TO_PATCH_NAME = ImmutableMap.copyOf(result);

    }

    public static Optional<String> getPatchName(final String className) {

        Collection<String> packageNames = Collections2.filter(
                PACKAGE_REGEX_TO_PATCH_NAME.keySet(), new Predicate<String>() {
                    @Override
                    public boolean apply(@Nullable String arg0) {
                        return className.matches(arg0);
                    }
                });

        String result = null;
        if (!packageNames.isEmpty()) {

            result = PACKAGE_REGEX_TO_PATCH_NAME.get(Iterables.get(packageNames, 0));

            if (packageNames.size() > 1) {
                System.err.println("WARNING, found multiple packages for class " + className + " selecting: " + result);
            }
        }

        return Optional.fromNullable(result);

    }
}
