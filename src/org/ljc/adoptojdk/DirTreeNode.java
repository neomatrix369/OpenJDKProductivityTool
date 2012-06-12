/* 
 
  The GNU General Public License (GPL)
 
  Version 2, June 1991

  Copyright (C) 1989, 1991 Free Software Foundation, Inc.
  59 Temple Place, Suite 330, Boston, MA 02111-1307 USA

  Everyone is permitted to copy and distribute verbatim copies of this license
  document, but changing it is not allowed.
  
  Copyright (c) 2012, John Oliver <johno@insightfullogic.com> All rights reserved.
  
  DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 
  This code is free software; you can redistribute it and/or modify it
  under the terms of the GNU General Public License version 2 only, as
  published by the Free Software Foundation.  Oracle designates this
  particular file as subject to the "Classpath" exception as provided
  by Oracle in the LICENSE file that accompanied this code.
 
  This code is distributed in the hope that it will be useful, but WITHOUT
  ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
  FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
  version 2 for more details (a copy is included in the LICENSE file that
  accompanied this code).
 
  You should have received a copy of the GNU General Public License version
  2 along with this work; if not, write to the Free Software Foundation,
  Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 
*/

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
