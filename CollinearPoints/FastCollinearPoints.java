import java.util.Arrays;
import java.util.Comparator;
// import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    
    Point[] points;
    LineSegment[] ls;
    Point[] pointsClone;
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null){
            throw new IllegalArgumentException();
        }
        this.points = points;
        ls = new LineSegment[1];
    }     
    // the number of line segments
    public int numberOfSegments() {
        return this.ls.length;
    }
    // the line segments
    public LineSegment[] segments() {        
        int lineSegmentIndex = 0;
        pointsClone = points.clone();
        Arrays.sort(pointsClone);

        for (int p = 0; p < points.length; p++ ) {
            int q = 0;
            int r = q + 1;
            int s = r + 1;
            Arrays.sort(points); // at this point points is the same order as pointsClone;
            Comparator<Point> pointComparator = points[p].slopeOrder();
            Arrays.sort(points,pointComparator);
            while(s < points.length) {
                if (pointComparator.compare(points[q],points[r]) == 0  && pointComparator.compare(points[r],points[s]) == 0) {
                    if (!duplicateLineSegment(p, q, r, s)) {
                        if(lineSegmentIndex == ls.length) resize(2 * ls.length);
                        ls[lineSegmentIndex++] = new LineSegment(pointsClone[p], points[s]);
                    }
                }
                q++;
                r++;
                s++;
            }
        }
        return ls;
    }
    private boolean duplicateLineSegment(int p,int q, int r, int s) {
        final int GREATERTHAN = 1;
        final int LESSTHAN = 1;
        //less than p
        if (pointsClone[p].compareTo(points[q]) == GREATERTHAN || pointsClone[p].compareTo(points[r]) == GREATERTHAN || pointsClone[p].compareTo(points[s]) == GREATERTHAN) {
            return true;
        }
        //greater than s
        if (points[s].compareTo(pointsClone[p]) == LESSTHAN || points[s].compareTo(points[q]) == LESSTHAN || points[s].compareTo(points[r]) == LESSTHAN ) {
            return true;
        }
        return false;
    }
    private void resize(int capacity) {
        LineSegment[] copy = new LineSegment[capacity];
        for (int i = 0;i < ls.length; i++){
            copy[i] = ls[i];
        }        
        ls = copy;
    }
}
