package pal;

import ajs.printutils.PrettyPrintTree;
import ajs.printutils.Color;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TreeDebugger {

    // Helper function to create the PrettyPrintTree for SplayTree
    public static void printTree(SplayTree.Node root) {
        PrettyPrintTree<SplayTree.Node> printer = new PrettyPrintTree<>(
                node -> {
                    if (node == null) return List.of();  // Ensure node is not null before processing
                    if (node.left == null && node.right == null) return List.of();  // Skip leaf nodes
                    // Filter out null values from children
                    if (node.left == null) return List.of(node.right);
                    if (node.right == null) return List.of(node.left);
                    return List.of(node.left, node.right);
                },


                node -> node == null ? "null" : String.valueOf(node.key)  // Handle null node gracefully
        );

        printer.setColor(Color.GREEN)               // Optional: Set color for the tree nodes
                .setBorder(true)                    // Optional: Enable border around nodes
                .setMaxDepth(5)                     // Optional: Set the max depth of the tree to print
                .setTrim(15)                        // Optional: Trim node values longer than 15 chars
                .display(root);                      // Print the tree
    }

    // Helper function to create the PrettyPrintTree for ZigOnlySplayTree
    public static void printTree(ZigOnlySplayTree.Node root) {
        PrettyPrintTree<ZigOnlySplayTree.Node> printer = new PrettyPrintTree<>(
                node -> {
                    if (node == null) return List.of();  // Ensure node is not null before processing
                    if (node.left == null && node.right == null) return List.of();  // Skip leaf nodes
                    // Filter out null values from children
                    if (node.left == null) return List.of(node.right);
                    if (node.right == null) return List.of(node.left);
                    return List.of(node.left, node.right);
                },


                node -> node == null ? "null" : String.valueOf(node.key)  // Handle null node gracefully
        );

        printer.setColor(Color.BLUE)                // Optional: Set color for the tree nodes
                .setBorder(true)                    // Optional: Enable border around nodes
                .setMaxDepth(5)                     // Optional: Set the max depth of the tree to print
                .setTrim(15)                        // Optional: Trim node values longer than 15 chars
                .display(root);                      // Print the tree
    }
}
