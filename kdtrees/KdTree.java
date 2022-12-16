import java.util.ArrayList;
import java.util.List;


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    private static final double SCREEN_MIN = 0.0;
    private static final double SCREEN_MAX = 1.0;
    
    private static class Node {
        Point2D point;
        Node left;
        Node right;
        // the axis-aligned rectangle corresponding to this node
        //root node should be (0,0)(1,1)
        RectHV rect;    
        //x = true;  y = false
        boolean compare_x_coord;

        public Node(Point2D point,boolean coord,RectHV rect) {
            this.point = point;
            compare_x_coord = coord;
            this.rect = rect;
        }
    }

    private Node root;

    private SET<Point2D> pointSet;
    // private boolean toogleCoord = true;
    // construct an empty set of points 
    public KdTree() {
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
        root = insertNode(root, p,null);
    }

    private Node insertNode(Node node, Point2D point,Node parent) {
        if (node == null) {
            if (parent == null) {
                RectHV rect = new RectHV(SCREEN_MIN, SCREEN_MIN, SCREEN_MAX, SCREEN_MAX);
                return new Node(point,true,rect);
            }
            RectHV rect;
            if (parent.compare_x_coord){
                rect = new RectHV(SCREEN_MIN, SCREEN_MIN, parent.point.x(), SCREEN_MAX);
            }
            else {
                rect = new RectHV(SCREEN_MIN, SCREEN_MIN, SCREEN_MAX, parent.point.y());
            }
            return new Node(point,!parent.compare_x_coord,rect);
        }
    
        int cmp = compare(point,node);
        if (cmp < 0) {
          node.left = insertNode(node.left, point,node);
        } else if (cmp > 0) {
          node.right = insertNode(node.right, point,node);
        }
    
        return node;
    }
    
    // private int compare(Point2D child, Point2D parent,boolean compareX) {
    private int compare(Point2D child, Node parent) {

        if (parent.compare_x_coord) {
            // place child in the right
            if (parent.point.x() < child.x()) return 1;
            //place child in the left
            if (parent.point.x() > child.x()) return -1;
        }else {
            if (parent.point.y() < child.y()) return 1;
            //place child in the left
            if (parent.point.y() > child.y()) return -1;
        }
        return 0;
    }

    // does the set contain point p? 
    public boolean contains(Point2D p) {
        return pointSet.contains(p);
    }

    // draw all points to standard draw             
    public void draw() {
        drawTree(root,null,0.0);
        // drawNodes(root);
    }
    // private void drawTree(Node node, double xmin, double ymin, double xmax, double ymax, int level) {
    private void drawTree(Node node,Node parent ,double lineDirection) {
        if (node == null) {
            return;
        }
        // Draw the line separating the left and right subtrees
        if (node.compare_x_coord) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius(0.0015);
            //TODO pass xmin, ymin, xmax, ymax 
            //do math.min
            if (parent != null) {
                StdDraw.line(node.point.x(),lineDirection,node.point.x(),parent.point.y());
            }else {
                StdDraw.line(node.point.x(),SCREEN_MIN,node.point.x(),SCREEN_MAX);
            }
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius(0.0015);
            StdDraw.line(lineDirection,node.point.y(),parent.point.x(), node.point.y());
        }

        // // Draw the point and its coordinates
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.015);
        StdDraw.point(node.point.x(), node.point.y());
        StdDraw.text(node.point.x(), node.point.y(), String.format("(%.2f, %.2f)", node.point.x(), node.point.y()));
    
        // Draw the level of the node
        // StdDraw.setPenColor(StdDraw.BLACK);
        // StdDraw.text(node.point.x(), node.point.y() - 0.05, String.format("Level: %d", level));

        drawTree(node.left,node,SCREEN_MIN);
        drawTree(node.right,node,SCREEN_MAX);

        return;
    }

   
    // all points that are inside the rectangle (or on the boundary)                        
    public Iterable<Point2D> range(RectHV rect) {
        // Stack<Point2D> points = new Stack<Point2D>();
        List<Point2D> points = new ArrayList<Point2D>();
        findRange(root,points,rect);
        return points;
    }
    private void findRange(Node node,List<Point2D> points,RectHV rect) {
        if (node == null) {
            return;
        }
        if (rect.contains(node.point)) {
            points.add(node.point);
        }
        if(node.compare_x_coord) { 

            if (node.point.x() >= rect.xmin()) {
                findRange(node.left,points,rect);
            }
            
            if (node.point.x() <= rect.xmax()) {
                findRange(node.right,points,rect);
            }

        }else {

            if (node.point.y() >= rect.ymin()) {
                findRange(node.left,points,rect);
            }
            
            if (node.point.y() <= rect.ymax()) {
                findRange(node.right,points,rect);
            }
        }

        return;
          
    }

    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (pointSet.isEmpty()) return null;
        double closest = Double.MAX_VALUE;
        Point2D nearestPoint = new Point2D(Double.MAX_VALUE, Double.MAX_VALUE);
        return findNearest(p, nearestPoint, closest, root);
    }
    private Point2D findNearest(Point2D queryPoint, Point2D nearestPoint, double closest,Node node) {

        if (queryPoint.distanceSquaredTo(node.point) < queryPoint.distanceSquaredTo(nearestPoint)) {
            nearestPoint = node.point;
            closest = queryPoint.distanceSquaredTo(node.point);
        }

        StdOut.println("node :" + node.point.toString());
        if (nearestPoint == null) StdOut.println("new nearest Point : null");
        else StdOut.println("new nearest Point :" + nearestPoint.toString());
        StdOut.println("new current closest :" + closest);
        StdOut.println();
        
        if (node.compare_x_coord) {
        
            //if query point is left go left
            if (node.left != null && queryPoint.x() <= node.point.x()) {
                nearestPoint = findNearest(queryPoint, nearestPoint, closest, node.left);
            }
            //otherwise go right
            if (node.right != null && queryPoint.x() >= node.point.x()) {
                 nearestPoint = findNearest(queryPoint, nearestPoint, closest, node.right);
            }
        }else {
            if (node.left != null && queryPoint.y() <= node.point.y()) {
                nearestPoint = findNearest(queryPoint, nearestPoint, closest, node.left);
            }
            if (node.right != null && queryPoint.y() >= node.point.y()) {
                nearestPoint = findNearest(queryPoint, nearestPoint, closest, node.right);
            }
        }
        return nearestPoint;
        // return;
    }
    // unit testing of the methods (optional) 
    public static void main(String[] args) {

        KdTree kd = new KdTree();
        In in = new In("./kdtree/test6a.txt");
        // int pointNum = 20;
        while (!in.isEmpty()) {  
            float x = in.readFloat();
            float y = in.readFloat();
            // StdOut.println("x" + x);
            // StdOut.println("Y" + y);
            Point2D p = new Point2D(x, y);
            kd.insert(p);
            // pointNum--;
        }
        Point2D queryPoint = new Point2D(0.493, 0.926);
        // Point2D queryPoint = new Point2D(0.111, 0.58);
        StdOut.println(kd.nearest(queryPoint));

        // RectHV r = new RectHV(0.03125, 0.5625, 0.65625, 0.625);
        // RectHV r = new RectHV(0.0, 0.0, 1.0, 1.0);
        // RectHV rh = new RectHV(0.0, 0.0, 0.7, 1.0);
        // RectHV rhs = new RectHV(0.0, 0.0, 1.0, 0.4);
        // for (Point2D p :kd.range(r)) {
        //     StdOut.println(p);
        // }
        // kd.print();
        StdDraw.setPenRadius(0.015);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(SCREEN_MIN, SCREEN_MAX);
        StdDraw.setYscale(SCREEN_MIN, SCREEN_MAX);
        StdDraw.setPenColor(StdDraw.PINK);
        queryPoint.draw();
        StdDraw.setPenColor(StdDraw.BLACK);
        kd.draw();

        // r.draw();
        // rh.draw();
        // rhs.draw(); 

        // Point2D nearesPoint = new Point2D(.5, 2);
        // nearesPoint.draw();
        // StdOut.println(kd.nearest(nearesPoint));
        StdDraw.show();
    }
    
}
    

