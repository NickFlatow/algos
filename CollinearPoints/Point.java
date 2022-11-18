/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        double deltaY = that.y - this.y;
        double deltaX = that.x - this.x;

        if (deltaX == 0) { return Double.NEGATIVE_INFINITY; }
        if (deltaY == 0) { return Double.POSITIVE_INFINITY; }
        return deltaY/deltaX;
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (this.y < that.y) { return -1; }
        if (this.y > that.y) { return 1; }
        if (this.x < that.x) { return -1; }
        if (this.x > that.x) { return 1; }
        return 0;

    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        Comparator<Point> cso = new SlopeOrder();
        return cso;
    }

    private class SlopeOrder implements Comparator<Point> {

        @Override
        public int compare(Point p1, Point p2) {
            double p1Slope = slopeTo(p1);
            double p2Slope = slopeTo(p2);
           
            // StdOut.println(Point.this);
            // StdOut.println(p1);
            // StdOut.println(p2);

            if (p1Slope < p2Slope) return -1;
            if (p2Slope < p1Slope) return 1;
            return 0;

        }

    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */


        Point[] points = new Point[11];
        /*four line segemetns no connection*/
        // Point p = new Point(0, 0);
        // Point q = new Point(10, 1);
        // Point r = new Point(7, 2);
        // Point s = new Point(3, 9);

        /*one line segment */
        // Point p = new Point(0, 0);
        // Point q = new Point(1, 1);
        // Point r = new Point(2, 2);
        // Point s = new Point(3, 3);

        /*one line segment and a point */

        //test in random order
        Point p = new Point(-1, 12);
        Point q = new Point(2, 2);
        Point r = new Point(3, 3);
        Point s = new Point(4, 4);
        Point t = new Point(5,5);
        Point u = new Point(3,7);
        Point v = new Point(2,-4);
        Point w = new Point(12,5);
        Point x = new Point(5,2);
        Point y = new Point(17,8);
        Point z = new Point(9,2);
        points[0] = p;
        points[1] = q;
        points[2] = r;
        points[3] = s;
        points[4] = t;
        points[5] = u;
        points[6] = v;
        points[7] = w;
        points[8] = x;
        points[9] = y;
        points[10] = z;


        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        StdOut.println("number of line segments: " + bcp.numberOfSegments());
        LineSegment[] ls  = bcp.segments();



        // StdOut.println(points[0].slopeTo(points[99]));
        // StdOut.println(points[0].slopeTo(points[44]));

        // Comparator<Point> asf = points[0].slopeOrder();
        // StdOut.println(asf.compare(points[10], points[22]));
        
        StdDraw.setPenRadius(0.015);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-10, 20);
        StdDraw.setYscale(-10, 20);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (Point point : points) {
            point.draw();
            // points[0].drawTo(point);
        }
        for (LineSegment l : ls) {
            StdOut.println(l);
            // l.draw();
        }

        StdDraw.show();



        // //standard case 
        // assert points[0].compareTo(points[1]) == -1;
        // //same point
        // assert points[0].compareTo(points[0]) == 0;
        // // y equal x different 
        // // StdOut.println(points[0].compareTo(points[10]) == 1);
        // assert points[0].compareTo(points[10]) == -1;

        // assert points[44].compareTo(points[64]) == -1;
        
    }
}