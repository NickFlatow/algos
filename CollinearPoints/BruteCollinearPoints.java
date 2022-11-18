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
        ls = new LineSegment[1];
    }    
    // the number of line segments
    public int numberOfSegments() {
        return ls.length;
    }       
    // the line segments must be 4 ponts
    public LineSegment[] segments() {
        Arrays.sort(points);
        int lineSegmentIndex = 0;

        for (int p = 0; p < points.length;p++) {
            for (int r = points.length-1;r>=3;r--) {
                
                Comparator<Point> pointComparator = points[p].slopeOrder();
                int s = r - 1;
                for (int q = p+1; q<s; q++) {

                    for (s = r-1; s>q; s--) {
                        if (pointComparator.compare(points[q],points[r]) == 0  && pointComparator.compare(points[r],points[s]) == 0) {
                            if(lineSegmentIndex == ls.length) resize(2 * ls.length);
                            ls[lineSegmentIndex++] = new LineSegment(points[p], points[r]);
                        }
                    }
                    s = r -1;
                }
            }
        }
        return ls;
    }
    private void resize(int capacity) {
        LineSegment[] copy = new LineSegment[capacity];
        for (int i = 0;i < ls.length; i++){
            copy[i] = ls[i];
        }        
        ls = copy;
    }
}
