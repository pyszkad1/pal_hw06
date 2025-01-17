package pal;

class ZigOnlySplayTree {
    class Node {
        int key;
        Node left, right;

        Node(int key) {
            this.key = key;
        }
    }

    Node root;

    // Only zig rotation
    private Node zig(Node x) {
        if (x == null || x.left == null) return x; // Prevent NullPointerException
        Node p = x.left;
        x.left = p.right;
        p.right = x;
        return p;
    }


    private Node splay(Node root, int key) {
        while (root != null && root.key != key) {
            if (key < root.key && root.left != null) {
                root = zig(root);
            } else if (key > root.key && root.right != null) {
                root.right = splay(root.right, key); // Ensure key moves up
            } else {
                break;
            }
        }
        return root;
    }

    public void insert(int key) {
        if (root == null) {
            root = new Node(key);
            return;
        }
        root = splay(root, key);
        if (root.key == key) return;

        Node newNode = new Node(key);
        if (key < root.key) {
            newNode.right = root;
            newNode.left = root.left;
            root.left = null;
        } else {
            newNode.left = root;
            newNode.right = root.right;
            root.right = null;
        }
        root = newNode;
    }

    public void delete(int key) {
        if (root == null) return;

        root = splay(root, key);
        if (root == null || root.key != key) return; // Extra null check to prevent NPE

        if (root.left == null) {
            root = root.right;
        } else {
            Node temp = root.right;
            root = root.left;
            root = splay(root, key); // Fix: Assign returned value to root
            root.right = temp;
        }
    }

    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }
}
