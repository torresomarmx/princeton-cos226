import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayList;
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
    public Iterable<Point2D> range(RectHV rect) {
        ArrayList<Point2D> rangePoints = new ArrayList<>();
        for (Point2D point : this.map.navigableKeySet())
            if (rect.contains(point)) rangePoints.add(point);
        return rangePoints;
    }

    // a nearest neighbor of point p; null if the symbol table is empty
    public Point2D nearest(Point2D p) {
        Point2D nearestPoint = null;
        for (Point2D point : this.map.navigableKeySet()) {
            if (nearestPoint == null) {
                nearestPoint = point;
            } else if (point.distanceSquaredTo(p) < nearestPoint.distanceSquaredTo(p)) {
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }

    // unit testing (required)
    public static void main(String[] args) {
        Point2D p1 = new Point2D(0.0, 1.0);
        Point2D p2 = new Point2D(4.0, 6.0);
        Point2D p3 = new Point2D(5.0, 8.0);
        PointST<Integer> pointST = new PointST<>();
        pointST.put(p1, 1);
        pointST.put(p2, 2);
        pointST.put(p2, 3);
        System.out.println(pointST.nearest(new Point2D(5.0, 2.0)));
        RectHV rect = new RectHV(0.0, 0.0, 4.0, 7.0);
        for (Point2D point : pointST.range(rect)) {
            System.out.println(point);
        }
    }

}