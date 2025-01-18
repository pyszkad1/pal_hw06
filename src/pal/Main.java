package pal;

import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        String[] operations = br.readLine().split(" ");

        SplayTree standardTree = new SplayTree();
        ZigOnlySplayTree zigOnlyTree = new ZigOnlySplayTree();
        boolean doZigOnly = true;
        boolean verbose = false;

        for (String op : operations) {
            int key = Integer.parseInt(op);
            if (key > 0) {

                if (verbose) System.out.println("Inserting " + key + "...");
                standardTree.insert(key);
                if (doZigOnly) zigOnlyTree.insert(key);

            } else {
                if (verbose) System.out.println("Deleting " + (-key) + "...");
                standardTree.delete(-key);
                if (doZigOnly) zigOnlyTree.delete(-key);
            }

            // Debugging: Print tree structure
            if (verbose) {
                System.out.println("Standard Tree:");
                TreeDebugger.printTree(standardTree.root);
                System.out.println("                    ");
                if (doZigOnly) TreeDebugger.printTree(zigOnlyTree.root);
                System.out.println("--------------------------------");
            }

        }

        int H1 = standardTree.getHeight();
        if (doZigOnly) {
            int H2 = zigOnlyTree.getHeight();
            System.out.println(H1 + " " + H2);
        } else {
            System.out.println(H1);
        }
    }
}