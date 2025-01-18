package pal;

class SplayTree {
    class Node {
        int key;
        Node left, right, parent;

        Node(int key) {
            this.key = key;
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }

    Node root;

    private boolean nodeEquals(Node a, Node b) {
        return a == b;
    }

    private void zig(Node x) {
        Node p = x.parent;
        if (p == null) return;

        p.left = x.right;
        if (x.right != null) {
            x.right.parent = p;
        }
        x.right = p;
        x.parent = p.parent;
        p.parent = x;

        if (x.parent != null) {
            if (nodeEquals(x.parent.left, p)) {
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
        if (p == null) return;

        p.right = x.left;
        if (x.left != null) {
            x.left.parent = p;
        }
        x.left = p;
        x.parent = p.parent;
        p.parent = x;

        if (x.parent != null) {
            if (nodeEquals(x.parent.right, p)) {
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
            Node p = x.parent;
            Node gp = p.parent;

            if (gp == null) {
                if (nodeEquals(p.left, x)) {
                    zig(x);
                } else {
                    zag(x);
                }
            } else {
                if (nodeEquals(p.left, x) && nodeEquals(gp.left, p)) {
                    zig(p);
                    zig(x);
                } else if (nodeEquals(p.right, x) && nodeEquals(gp.right, p)) {
                    zag(p);
                    zag(x);
                } else if (nodeEquals(p.left, x) && nodeEquals(gp.right, p)) {
                    zig(x);
                    zag(x);
                } else {
                    zag(x);
                    zig(x);
                }
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
                splay(node);
                return;
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
        Node node = search(root, key);
        if (node == null) return;

        splay(node);

        if (node.left == null) {
            root = node.right;
            if (root != null) root.parent = null;
        } else {
            Node temp = node.right;
            root = node.left;
            root.parent = null;
            Node maxLeft = root;
            while (maxLeft.right != null) {
                maxLeft = maxLeft.right;
            }
            splay(maxLeft);
            root.right = temp;
            if (temp != null) temp.parent = root;
        }
    }

    private Node search(Node node, int key) {
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
