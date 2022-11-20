import org.junit.Test;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import static org.junit.Assert.assertEquals;

public class CollinearPointsTests {

    @Test
    public void LineSegmengSmallTest() {
        Point[] points = new Point[7];
        Point p = new Point(-1, 12);
        Point q = new Point(2, 2);
        Point r = new Point(3, 3);
        Point s = new Point(4, 4);
        Point t = new Point(5,5);
        Point u = new Point(3,7);
        Point v = new Point(2,-4);
        points[0] = p;
        points[1] = q;
        points[6] = r;
        points[3] = s;
        points[4] = t;
        points[5] = u;
        points[2] = v;

        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        System.out.println("number of line segments: " + bcp.numberOfSegments());
        LineSegment[] ls  = bcp.segments();
        assertEquals("(2, 2) -> (5, 5)",ls[0].toString());
    }

    @Test
    public void TwoLineSegmengSmallTest() {
        Point[] points = new Point[11];
        Point p = new Point(-1, 12);
        Point q = new Point(2, 2);
        Point r = new Point(3, 3);
        Point s = new Point(4, 4);
        Point t = new Point(5,5);
        Point u = new Point(3,7);
        Point v = new Point(2,-4);
        Point w = new Point(-2, 2);
        Point x = new Point(-3, 3);
        Point y = new Point(-4, 4);
        Point z = new Point(-5,5);
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
        System.out.println("number of line segments: " + bcp.numberOfSegments());
        LineSegment[] ls  = bcp.segments();
        assertEquals("(-2, 2) -> (-5, 5)",ls[0].toString());
        assertEquals("(2, 2) -> (5, 5)",ls[1].toString());
        
    }
    
    @Test
    public void LineSegmengLargeTest() {
        Point[] points = new Point[11];
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
        System.out.println("number of line segments: " + bcp.numberOfSegments());
        LineSegment[] ls  = bcp.segments();
        assertEquals("(2, 2) -> (5, 5)",ls[0].toString());

    }


    @Test
    public void FastLineSegmengSmallTest() {
        Point[] points = new Point[7];
        Point p = new Point(-1, 12);
        Point q = new Point(2, 2);
        Point r = new Point(3, 3);
        Point s = new Point(4, 4);
        Point t = new Point(5,5);
        Point u = new Point(3,7);
        Point v = new Point(2,-4);
        points[0] = p;
        points[1] = q;
        points[6] = r;
        points[3] = s;
        points[4] = t;
        points[5] = u;
        points[2] = v;

        FastCollinearPoints fcp = new FastCollinearPoints(points);
        System.out.println("number of line segments: " + fcp.numberOfSegments());
        LineSegment[] ls  = fcp.segments();
        assertEquals("(2, 2) -> (5, 5)",ls[0].toString());
    }



    @Test
    public void FastTwoLineSegmengSmallTest() {
        Point[] points = new Point[11];
        Point p = new Point(-1, 12);
        Point q = new Point(2, 2);
        Point r = new Point(3, 3);
        Point s = new Point(4, 4);
        Point t = new Point(5,5);
        Point u = new Point(3,7);
        Point v = new Point(2,-4);
        Point w = new Point(-2, 2);
        Point x = new Point(-3, 3);
        Point y = new Point(-4, 4);
        Point z = new Point(-5,5);
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

        FastCollinearPoints bcp = new FastCollinearPoints(points);
        System.out.println("number of line segments: " + bcp.numberOfSegments());
        LineSegment[] ls  = bcp.segments();
        assertEquals("(-2, 2) -> (-5, 5)",ls[0].toString());
        assertEquals("(2, 2) -> (5, 5)",ls[1].toString());
        
    }

    @Test
    public void Input6FastTest() {

        In in = new In("./testfiles/input6.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        FastCollinearPoints bcp = new FastCollinearPoints(points);
        LineSegment[] ls  = bcp.segments();
        assertEquals("(14000, 10000) -> (32000, 10000)",ls[0].toString());

        // draw the points
        // StdDraw.setPenRadius(0.015);
        // StdDraw.enableDoubleBuffering();
        // StdDraw.setXscale(0, 32768);
        // StdDraw.setYscale(0, 32768);
        // for (Point p : points) {
        //     p.draw();
        // }
        // StdDraw.show();

        // print and draw the line segments
        // FastCollinearPoints collinear = new FastCollinearPoints(points);
        // for (LineSegment segment : collinear.segments()) {
        //     StdOut.println(segment);
        //     segment.draw();
        // }



    }
    @Test
    public void Input8FastTest() {

        In in = new In("./testfiles/input8.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        FastCollinearPoints bcp = new FastCollinearPoints(points);
        LineSegment[] ls  = bcp.segments();
        assertEquals("(3000, 4000) -> (20000, 21000)",ls[1].toString());
        assertEquals("(0, 10000) -> (10000, 0)",ls[0].toString());

    }


    @Test
    public void Input8BruteTest() {

        In in = new In("./testfiles/input8.txt");
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        BruteCollinearPoints bcp = new BruteCollinearPoints(points);
        LineSegment[] ls  = bcp.segments();
        assertEquals("(10000, 0) -> (0, 10000)",ls[0].toString());
        assertEquals("(3000, 4000) -> (20000, 21000)",ls[1].toString());

    }

}
