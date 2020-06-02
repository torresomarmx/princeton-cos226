import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayDeque;
import java.util.ArrayList;

public class KdTreeST<Value> {
    private class Node {
        private Point2D p;
        private Value v;
        private Node left;
        private Node right;
        private RectHV rect;
        public Node(Point2D p, Value v, RectHV rect) {
            this.p = p;
            this.v = v;
            this.rect = rect;
        }

        public void setP(Point2D p) { this.p = p; }

        public Point2D getP() { return this.p; }

        public void setV(Value v) { this.v = v; }

        public Value getV() { return this.v; }

        public void setLeft(Node left) { this.left = left; }

        public Node getLeft() { return this.left; }

        public void setRight(Node right) { this.right = right; }

        public Node getRight() { return this.right; }

        public void setRect(RectHV rect) { this.rect = rect; }

        public RectHV getRect() { return this.rect; }
    }

    private int numberOfKeys;
    private Node rootNode;
    // construct an empty symbol table of points
    public KdTreeST() {
        this.rootNode = null;
        this.numberOfKeys = 0;
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return this.numberOfKeys == 0;
    }

    // number of points
    public int size() {
        return this.numberOfKeys;
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        this.rootNode = this.put(
                this.rootNode,
                p,
                val,
                0,
                new RectHV(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY)
        );
        this.numberOfKeys += 1;
    }

    private Node put(Node node, Point2D p, Value val, int treeLevel, RectHV rect) {
        if (node == null) return new Node(p, val, rect);
        int compareInt;
        if (treeLevel % 2 == 0) compareInt = Double.compare(p.x(), node.getP().x());
        else compareInt = Double.compare(p.y(), node.getP().y());
        if (compareInt < 0) {
            // move left/down, depending on treeLevel
            rect = treeLevel % 2 == 0 ?
                    new RectHV(rect.xmin(), rect.ymin(), node.getP().x(),rect.ymax()) :
                    new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.getP().y());
            node.setLeft(this.put(node.getLeft(), p, val, treeLevel + 1, rect));
        } else if (!node.getP().equals(p)) {
            // move right/up, depending on treeLevel
            rect = treeLevel % 2 == 0 ?
                    new RectHV(node.getP().x(), rect.ymin(), rect.xmax(), rect.ymax()) :
                    new RectHV(rect.xmin(), node.getP().y(), rect.xmax(), rect.ymax());
            node.setRight(this.put(node.getRight(), p, val, treeLevel + 1, rect));
        } else {
            node.setV(val);
        }
        return node;
    }

    // value associated with point p
    public Value get(Point2D p) {
        if (this.numberOfKeys == 0) return null;
        return this.get(this.rootNode, p, 0);
    }

    private Value get(Node node, Point2D p, int treeLevel) {
        if (node == null) return null;
        int compareInt;
        if (treeLevel % 2 == 0) compareInt = Double.compare(p.x(), node.getP().x());
        else compareInt = Double.compare(p.y(), node.getP().y());
        if (compareInt < 0) {
            return this.get(node.getLeft(), p, treeLevel + 1);
        } else if (!node.getP().equals(p)) {
            return this.get(node.getRight(), p, treeLevel + 1);
        } else {
            return node.getV();
        }
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        return this.get(p) != null;
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        ArrayList<Point2D> levelOrderPoints = new ArrayList<>();
        if (this.numberOfKeys == 0) return levelOrderPoints;
        ArrayDeque<Node> nodesDeque = new ArrayDeque<>();
        nodesDeque.add(this.rootNode);
        while (nodesDeque.size() != 0) {
            Node currentNode = nodesDeque.getFirst();
            levelOrderPoints.add(currentNode.getP());
            if (currentNode.getLeft() != null) nodesDeque.add(currentNode.getLeft());
            if (currentNode.getRight() != null) nodesDeque.add(currentNode.getRight());
        }
        return levelOrderPoints;
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> pointsInsideRect = new ArrayList<>();
        this.range(rect, this.rootNode, pointsInsideRect);
        return pointsInsideRect;
    }

    private void range(RectHV rect, Node node, ArrayList<Point2D> pointsInsideRect) {
       if (node == null || !node.getRect().intersects(rect)) return;
       if (rect.contains(node.getP())) pointsInsideRect.add(node.getP());
       this.range(rect, node, pointsInsideRect);
       this.range(rect, node, pointsInsideRect);
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        if (this.numberOfKeys == 0) return null;
        return nearest(p, this.rootNode, this.rootNode.getP());
    }

    private Point2D nearest(Point2D p, Node node, Point2D closestSoFar) {
        if (node == null || node.getRect().distanceSquaredTo(p) >= closestSoFar.distanceSquaredTo(p)) return closestSoFar;
        if (node.getP().distanceSquaredTo(p) < closestSoFar.distanceSquaredTo(p))
            closestSoFar = node.getP();

        Node nextNodeToLookAt = node.getRight();
        if (node.getLeft() != null && node.getLeft().getRect().contains(p))
            closestSoFar = nearest(p, node.getLeft(), closestSoFar);
        else if (node.getRight() != null && node.getRight().getRect().contains(p)) {
            closestSoFar = nearest(p, node.getRight(), closestSoFar);
            nextNodeToLookAt = node.getLeft();
        }
        closestSoFar = nearest(p, nextNodeToLookAt, closestSoFar);
        return closestSoFar;
    }
}