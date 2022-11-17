import java.util.Arrays;
import java.util.Comparator;

public class BruteCollinearPoints {
    // finds all line segments containing 4 points
    Point[] points;
    LineSegment[] ls;
    public BruteCollinearPoints(Point[] points) {
        if (points == null){
            throw new IllegalArgumentException();
        }
        this.points = points;
        ls = new LineSegment[this.points.length];
        
    }    
    // the number of line segments
    public int numberOfSegments() {
        return ls.length;
    }       
    // the line segments
    public LineSegment[] segments() {
        Point p = this.points[0];
        Point q = this.points[1];
        Point r = this.points[2];
        Point s = this.points[3];

        // Comparator<Point> pComparator = p.slopeOrder();
        // int longestSegment = 0;
        

        if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == p.slopeTo(s)) {
            ls[0] = new LineSegment(p, s);
        }
        return ls;
    }
}
