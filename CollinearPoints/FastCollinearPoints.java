import java.util.Arrays;
import java.util.Comparator;
// import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    
    private Point[] points;
    private LineSegment[] ls;
    private Point[] pointsClone;
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
        checkDuplicate(points);
        pointsClone = points.clone();
        Arrays.sort(pointsClone);

        for (int p = 0; p < points.length; p++ ) {
            int start = 0;
            int end = start + 1;

            Arrays.sort(points); // at this point points is the same order as pointsClone;
            Comparator<Point> pointComparator = points[p].slopeOrder();
            Arrays.sort(points,pointComparator);
            while(end < points.length) {
                if (pointComparator.compare(points[start],points[end]) == 0 && (pointsClone[p] != points[start] && pointsClone[p] != points[end])) {
                    while (end < points.length && pointComparator.compare(points[start],points[end]) == 0) {
                        end++;
                    }
                    if (!duplicateLineSegment(p, start, end-1)) {
                        if(lineSegmentIndex == ls.length) resize(2 * ls.length);
                        ls[lineSegmentIndex++] = new LineSegment(pointsClone[p], points[end-1]);
                    }
                }
                start++;
                end++;

            }
        }
        return ls;
    }
    private boolean duplicateLineSegment(int p,int start, int end) {
        final int GREATERTHAN = 1;
        final int LESSTHAN = -1;
        //less than p
        if (pointsClone[p].compareTo(points[start]) == GREATERTHAN) {
            return true;
        }
        if (pointsClone[p].compareTo(points[end]) == GREATERTHAN){
            return true;
        }
        // || points[end].compareTo(points[start]) == LESSTHAN
        //greater than s
        if (points[end].compareTo(pointsClone[p]) == LESSTHAN  ) {
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
    private void checkDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate(s) found.");
            }
        }
    }
}
