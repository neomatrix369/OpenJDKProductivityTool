package org.ljc.adoptojdk;

import java.nio.file.Path;
import java.util.*;

public class DirectoryTreeNode {

    Path location;
    private Set<DirectoryTreeNode> children = new HashSet<>();
    private Path globalRoot;

    public DirectoryTreeNode(Path location, Path globalRoot) {
        this.location = location;
        this.globalRoot = globalRoot;
    }

    public void addNode(Path file) {
        Path toAdd = location.resolve(location.relativize(file).subpath(0, 1));

        if (location.equals(file)) {
            return;
        }

        for (DirectoryTreeNode child : children) {
            if (child.location.equals(toAdd)) {
                child.addNode(file);
                return;
            }
        }

        DirectoryTreeNode add = new DirectoryTreeNode(toAdd, globalRoot);
        children.add(add);
        add.addNode(file);
    }

    public int getSize() {
        if (children.isEmpty()) {
            return 1;
        }

        int size = 0;
        for (DirectoryTreeNode dtn : children) {
            size += dtn.getSize();
        }
        return size;
    }

    private List<DirectoryTreeNode> getLeaves() {
        List<DirectoryTreeNode> out = new ArrayList<>();
        for (DirectoryTreeNode child : children) {
            if (child.getChildren().isEmpty()) {
                out.add(child);
            }
        }
        return out;
    }

    private Set<DirectoryTreeNode> getChildren() {
        return Collections.unmodifiableSet(children);
    }

    public List<DirectoryTreeNode> getDepthFirstGetBags() {
        List<DirectoryTreeNode> out = new ArrayList<>();

        // ok...not quite depth first
        out.addAll(getLeaves());
        children.removeAll(out);

        Iterator<DirectoryTreeNode> iter = children.iterator();

        // get bags out of children that are large enough
        while (iter.hasNext()) {
            DirectoryTreeNode child = iter.next();

            int dist = 0;
            if (out.size() > 0) {
                Path relative = out.get(0).location.relativize(child.location);
                dist = relative.getNameCount();
            }

            if (dist < MergePatches.MERGIENESS) {
                out.addAll(child.getDepthFirstGetBags());

                //remove children from the tree that are fully processed
                if (child.getSize() == 1) {
                    iter.remove();
                }
            }
        }

        return out;
    }
}
