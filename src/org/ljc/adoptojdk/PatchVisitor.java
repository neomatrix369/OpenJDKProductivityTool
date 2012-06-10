package org.ljc.adoptojdk;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class PatchVisitor extends SimpleFileVisitor<Path> {

    private DirectoryTreeNode rootNode;

    public PatchVisitor(Path root) {
        rootNode = new DirectoryTreeNode(root, root);
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
            throws IOException {

        if (file.toString().endsWith(".patch")) {
            rootNode.addNode(file);
        }

        return FileVisitResult.CONTINUE;
    }

    /**
     * @return the rootNode
     */
    public DirectoryTreeNode getRootNode() {
        return rootNode;
    }
}
