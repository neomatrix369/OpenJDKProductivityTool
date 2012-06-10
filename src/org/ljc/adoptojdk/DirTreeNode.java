package org.ljc.adoptojdk;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class DirTreeNode {

	Path location;
	Set<DirTreeNode> children = new HashSet<>();
	Path globalRoot;

	public DirTreeNode(Path location, Path globalRoot) {
		this.location = location;
		this.globalRoot = globalRoot;
	}

	public DirTreeNode(Path globalRoot) {
		this.globalRoot = globalRoot;
	}

	public void addNode(Path file) {
		Path toAdd = location.resolve(location.relativize(file).subpath(0, 1));

		if (location.equals(file))
			return;

		for (DirTreeNode child : children) {
			if (child.location.equals(toAdd)) {
				child.addNode(file);
				return;
			}
		}

		DirTreeNode add = new DirTreeNode(toAdd, globalRoot);
		children.add(add);
		add.addNode(file);
	}

	public int getSize() {
		if (children.size() == 0)
			return 1;

		int size = 0;
		for (DirTreeNode dtn : children) {
			size += dtn.getSize();
		}
		return size;
	}

	private List<DirTreeNode> getLeaves() {
		List<DirTreeNode> out = new ArrayList<>();
		for (DirTreeNode child : children) {
			if (child.getChildren().size() == 0) {
				out.add(child);
			}
		}
		return out;
	}

	private Set<DirTreeNode> getChildren() {
		return children;
	}

	public List<DirTreeNode> getDepthFirstGetBags() {
		List<DirTreeNode> out = new ArrayList<>();

		// ok...not quite depth first
		out.addAll(getLeaves());
		children.removeAll(out);

		Iterator<DirTreeNode> iter = children.iterator();

		// get bags out of children that are large enough
		while (iter.hasNext()) {
			DirTreeNode child = iter.next();

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
