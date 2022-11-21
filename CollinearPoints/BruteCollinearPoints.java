import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

public class BruteCollinearPoints {
    // finds all line segments containing 4 points
    private Point[] points;
    private List<LineSegment> colinearSegments = new ArrayList<LineSegment>();
    public BruteCollinearPoints(Point[] points) {
        checkNull(points);
        checkDuplicate(points);
        this.points = points.clone();
    }    
    // the number of line segments
    public int numberOfSegments() {
        return colinearSegments.size();
    }       
    // the line segments must be 4 ponts
    public LineSegment[] segments() {
        Point[] clonedPoints = points.clone();
        Arrays.sort(clonedPoints);

        for (int p = 0; p < clonedPoints.length;p++) {
            for (int r = clonedPoints.length-1;r>=3;r--) {
                
                Comparator<Point> pointComparator = clonedPoints[p].slopeOrder();
                int s = r - 1;
                for (int q = p+1; q<s; q++) {

                    for (s = r-1; s>q; s--) {
                        if (pointComparator.compare(clonedPoints[q],clonedPoints[r]) == 0  && pointComparator.compare(clonedPoints[r],clonedPoints[s]) == 0) {
                            LineSegment lineSeg = new LineSegment(clonedPoints[p], clonedPoints[r]);
                            colinearSegments.add(lineSeg);
                        }
                    }
                    s = r -1;
                }
            }
        }
        LineSegment[] lineSegment = new LineSegment[numberOfSegments()];
        lineSegment =  colinearSegments.toArray(lineSegment);
        return lineSegment;  
        
    }
    private void checkDuplicate(Point[] points) {
        for (int j=0;j < points.length;j++)
            for (int k=j+1;k<points.length;k++)
                if (points[k].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("frunk");
                }
    }
    private void checkNull(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("frunk");
        }
        for (Point p : points) {
            if (p == null) {
                throw new IllegalArgumentException("frunk");
            }
        }
    }

}
