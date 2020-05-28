import edu.princeton.cs.algs4.Point2D;

import java.util.TreeMap;

public class PointST<Value> {
    private TreeMap<Point2D, Value> map;

    // construct an empty symbol table of points
    public PointST() {
        this.map = new TreeMap<>();
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    // number of points
    public int size() {
        return this.map.size();
    }

    // associate the value val with point p
    public void put(Point2D p, Value val) {
        this.map.put(p, val);
    }

    // value associated with point p
    public Value get(Point2D p) {
        return this.map.get(p);
    }

    // does the symbol table contain point p?
    public boolean contains(Point2D p) {
        return this.map.containsKey(p);
    }

    // all points in the symbol table
    public Iterable<Point2D> points() {
        return this.map.navigableKeySet();
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect)

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p)

    // unit testing (required)
    public static void main(String[] args)

}