import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
// import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
    
    private Point[] points;
    private List<LineSegment> colinearSegments = new ArrayList<LineSegment>();
    private Point[] pointsClone;
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null || Arrays.asList(points).contains(null)) {
            throw new IllegalArgumentException("argument is null");
        }
        checkDuplicate(points);
        this.points = points.clone();
        // ls = new LineSegment[1];
    }     
    // the number of line segments
    public int numberOfSegments() {
        return colinearSegments.size();
    }
    // the line segments
    public LineSegment[] segments() {        
        // int lineSegmentIndex = 0;
        
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
                    while (end < points.length) {
                    
                        if(pointComparator.compare(points[start],points[end]) == 0){
                            // lineSegmentLength++;
                            end++;
                        }else{
                            addLineSegment(p,start,end);
                            start = end;
                            end = start + 1;
                            // lineSegmentLength = 0;
                        }
                        
                    }
                    if (end - start + 1 > 3){
                        addLineSegment(p, start, end);
                    }

                }
                start++;
                end++;
            }
            // addLineSegment(p,start,end);
        }
        LineSegment[] lineSegment = new LineSegment[numberOfSegments()];
        lineSegment =  colinearSegments.toArray(lineSegment);
        return lineSegment;    
    }
    private void addLineSegment(int p, int start, int end) {
        if (!duplicateLineSegment(p, start, end-1) && (end - start +1) > 3) {
            LineSegment lineSeg = new LineSegment(pointsClone[p], points[end -1]);
            colinearSegments.add(lineSeg);
        }
    }
    private boolean duplicateLineSegment(int p,int start, int end) {
        final int GREATERTHAN = 1;
        //less than p
        if (pointsClone[p].compareTo(points[start]) == GREATERTHAN) {
            return true;
        }
        if (pointsClone[p].compareTo(points[end]) == GREATERTHAN) {
            return true;
        }
        return false;
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
