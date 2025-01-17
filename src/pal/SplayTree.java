package pal;

class SplayTree {
    class Node {
        int key;
        Node left, right, parent;
        Node(int key) { this.key = key; }
    }

    Node root;

    private Node zig(Node x) {
        if (x == null || x.left == null) return x; // Prevent NullPointerException
        Node p = x.left;
        x.left = p.right;
        p.right = x;
        return p;
    }

    private Node zag(Node x) {
        if (x == null || x.right == null) return x; // Prevent NullPointerException
        Node p = x.right;
        x.right = p.left;
        p.left = x;
        return p;
    }



    private Node splay(Node root, int key) {
        if (root == null || root.key == key) return root;

        // Key is in the left subtree
        if (key < root.key) {
            if (root.left == null) return root;

            // Zig-zig case
            if (key < root.left.key) {
                root.left.left = splay(root.left.left, key);
                root = zig(root);
            }
            // Zig-zag case
            else if (key > root.left.key) {
                root.left.right = splay(root.left.right, key);
                if (root.left.right != null)
                    root.left = zig(root.left);
            }

            return (root.left == null) ? root : zig(root);
        }

        // Key is in the right subtree
        else {
            if (root.right == null) return root;

            // Zag-zag case
            if (key > root.right.key) {
                root.right.right = splay(root.right.right, key);
                root = zag(root);
            }
            // Zag-zig case
            else if (key < root.right.key) {
                root.right.left = splay(root.right.left, key);
                if (root.right.left != null)
                    root.right = zag(root.right);
            }

            return (root.right == null) ? root : zag(root);
        }
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
        if (root.key != key) return;

        if (root.left == null) {
            root = root.right;
        } else {
            Node temp = root.right;
            root = root.left;
            root = splay(root, key); // Fix: Assign returned value to root
            root.right = temp;
        }
    }

    private Node search(int key) {
        Node z = root;
        while (z != null) {
            if (key < z.key) z = z.left;
            else if (key > z.key) z = z.right;
            else return z;
        }
        return null;
    }

    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }
}
