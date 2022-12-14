import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class PointSET {
    private SET<Point2D> pointSet;
    
    // construct an empty set of points 
    public PointSET() {
        pointSet = new SET<Point2D>();
    }

    // is the set empty? 
    public boolean isEmpty() {
        return pointSet.isEmpty();
    }

    // number of points in the set 
    public int size() {
        return pointSet.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        pointSet.add(p);
    }

    // does the set contain point p? 
    public boolean contains(Point2D p) {
        return pointSet.contains(p);
    }

    // draw all points to standard draw             
    public void draw() {
        for (Point2D p : pointSet) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)                        
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> points = new Stack<Point2D>();
        for (Point2D p : pointSet) {
            if (p.x() >= rect.xmin() && p.x() <= rect.xmax()  && p.y() >= rect.ymin() && p.y() <= rect.ymax()) {
                points.push(p);
            }
        }
        return points;
    }

    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        Point2D nearestPoint = null;
        double closest = Double.MAX_VALUE;
        if (pointSet.isEmpty()) return null;
        for (Point2D point : pointSet) {
            if (point.distanceSquaredTo(p) < closest) {
                nearestPoint = p;
            }
        }
        return nearestPoint;
    }

    // unit testing of the methods (optional) 
    // public static void main(String[] args) {
    //     PointSET ps = new PointSET();

    //     In in = new In("./kdtree/circle4.txt");
    //     int pointNum = 4;
    //     while (pointNum > 0) {  
    //         float x = in.readFloat();
    //         float y = in.readFloat();
    //         // StdOut.println("x" + x);
    //         // StdOut.println("Y" + y);
    //         Point2D p = new Point2D(x, y);
    //         ps.insert(p);
    //         pointNum--;
    //     }
    //     StdDraw.setPenRadius(0.015);
    //     StdDraw.enableDoubleBuffering();
    //     StdDraw.setXscale(-1, 10);
    //     StdDraw.setYscale(-1, 10);
    //     ps.draw();
    //     RectHV r = new RectHV(-10, -10, 10, 10);
    //     r.draw();
    //     // for (Point2D p: ps.range(r)){
    //     //     StdOut.println(p);
    //     // }
    //     Point2D nearesPoint = new Point2D(.5, 2);
    //     nearesPoint.draw();
    //     StdOut.println(ps.nearest(nearesPoint));
    //     StdDraw.show();
    // }
}
