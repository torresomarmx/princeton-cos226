import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

public class NearestNeighborVisualizer {

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        // initialize the two data structures with point from standard input
        PointST<Integer> brute = new PointST<Integer>();
        KdTreeST<Integer> kdtree = new KdTreeST<Integer>();
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.put(p, i);
            brute.put(p, i);
        }

        // process nearest neighbor queries
        StdDraw.enableDoubleBuffering();
        while (true) {
            System.out.println("--");

            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);
            System.out.println("point:");
            System.out.println(query);

            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            for (Point2D p : brute.points()) {
                p.draw();
            }

            // draw in red the nearest neighbor according to the brute-force algorithm
            StdDraw.setPenRadius(0.03);
            StdDraw.setPenColor(StdDraw.RED);
            Point2D bruteNearest = brute.nearest(query);
            if (bruteNearest != null) bruteNearest.draw();
            System.out.println("Brute:");
            System.out.println(bruteNearest);
            StdDraw.setPenRadius(0.02);

            // draw in blue the nearest neighbor according to the kd-tree algorithm
            StdDraw.setPenColor(StdDraw.BLUE);
            Point2D kdtreeNearest = kdtree.nearest(query);
            if (kdtreeNearest != null) kdtreeNearest.draw();
            System.out.println("KD:");
            System.out.println(kdtreeNearest);
            StdDraw.show();
            StdDraw.pause(20);
        }
    }
}