package pal;

class ZigOnlySplayTree {
    class Node {
        int key;
        Node left, right, parent;
        Node(int key) { this.key = key; }
    }

    Node root;

    private void zig(Node x) {
        Node p = x.parent;
        if (p == null) return; // Root node, no rotation possible

        p.left = x.right;
        if (x.right != null) {
            x.right.parent = p;
        }
        x.right = p;
        x.parent = p.parent;
        p.parent = x;

        if (x.parent != null) {
            if (x.parent.left == p) {
                x.parent.left = x;
            } else {
                x.parent.right = x;
            }
        } else {
            root = x;
        }
    }

    private void zag(Node x) {
        Node p = x.parent;
        if (p == null) return; // Root node, no rotation possible

        p.right = x.left;
        if (x.left != null) {
            x.left.parent = p;
        }
        x.left = p;
        x.parent = p.parent;
        p.parent = x;

        if (x.parent != null) {
            if (x.parent.right == p) {
                x.parent.right = x;
            } else {
                x.parent.left = x;
            }
        } else {
            root = x;
        }
    }

    private void splay(Node x) {
        while (x.parent != null) {
            if (x.parent.left == x) {
                zig(x);
            } else {
                zag(x);
            }
        }
    }

    public void insert(int key) {
        if (root == null) {
            root = new Node(key);
            return;
        }

        Node node = root, parent = null;
        while (node != null) {
            parent = node;
            if (key < node.key) {
                node = node.left;
            } else if (key > node.key) {
                node = node.right;
            } else {
                return; // Key already exists, do nothing
            }
        }

        Node newNode = new Node(key);
        newNode.parent = parent;
        if (key < parent.key) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }

        splay(newNode);
    }

    public void delete(int key) {
        Node node = search(key);
        if (node == null) return;

        splay(node);
        if (node.left == null) {
            root = node.right;
        } else {
            Node maxLeft = node.left;
            while (maxLeft.right != null) {
                maxLeft = maxLeft.right;
            }
            splay(maxLeft);
            maxLeft.right = node.right;
            if (node.right != null) {
                node.right.parent = maxLeft;
            }
            root = maxLeft;
        }
    }

    private Node search(int key) {
        Node node = root;
        while (node != null) {
            if (key < node.key) {
                node = node.left;
            } else if (key > node.key) {
                node = node.right;
            } else {
                return node;
            }
        }
        return null;
    }

    public int getHeight() {
        return getHeight(root);
    }

    private int getHeight(Node node) {
        if (node == null) return -1;
        return 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }
}
