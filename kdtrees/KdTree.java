import java.util.TreeSet;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {
    private static final double SCREEN_MIN = -1;
    private static final double SCREEN_MAX =  2;
    private class Node {
        Point2D point;
        Node left;
        Node right;
        //x = true;  y = false
        boolean compare_x_coord;

        public Node(Point2D point,boolean coord) {
            this.point = point;
            compare_x_coord = coord;
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
            if (parent == null) return new Node(point,true);

            return new Node(point,!parent.compare_x_coord);
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
        int count = 0;
        drawTree(root,null,"");
        // for (Point2D p : pointSet){
        //     p.draw();
        // }
        //draw.show?
    }
    private void drawTree(Node node,Node parent,String direction) {
        if (node == null) {
            return;
        }
 
        if (node.compare_x_coord) {
            StdDraw.setPenColor(StdDraw.RED);
            if (parent != null) {
                //if left
                //actual left
                if (direction == "left") {
                    StdDraw.line(node.point.x(),SCREEN_MIN,node.point.x(),parent.point.y());
                }
                else {
                    StdDraw.line(node.point.x(),SCREEN_MAX,node.point.x(),parent.point.y());
                }
            }else {

                //right 
                StdDraw.line(node.point.x(),SCREEN_MIN,node.point.x(),SCREEN_MAX);
            }
        } else {

            StdDraw.setPenColor(StdDraw.BLUE);
            if (parent != null) {
                //if left below paretn
                if (direction == "left") {
                    StdDraw.line(SCREEN_MIN,node.point.y(),parent.point.x(), node.point.y());
                } else {
                //if right above
                    StdDraw.line(SCREEN_MAX,node.point.y(),parent.point.x(), node.point.y());   
                }
            } else {
                StdDraw.line(SCREEN_MIN,node.point.y(),SCREEN_MAX, node.point.y());
            }
        }

        StdDraw.setPenColor(StdDraw.BLACK);
        node.point.draw();

        if (node.left != null)  drawTree(node.left,node,"left");
        if (node.right != null) drawTree(node.right,node,"right");

        return;
    }

    // all points that are inside the rectangle (or on the boundary)                        
    public Iterable<Point2D> range(RectHV rect) {
        Stack<Point2D> points = new Stack<Point2D>();
        findRange(root,points,rect);
        return points;
    }
    private void findRange(Node node,Stack<Point2D> points,RectHV rect) {
        if (node == null) {
            return;
        }
        if (node.point.x() >= rect.xmin() && node.point.x() <= rect.xmax()  && node.point.y() >= rect.ymin() && node.point.y() <= rect.ymax()) {
            points.push(node.point);
        }
        if(node.compare_x_coord) { 
            //is the left point outside of the box
            if (node.left != null && node.left.point.x() >= rect.xmin()) {
                findRange(node.left,points,rect);
            }
            //is the right point outside the box
            if (node.right != null && node.right.point.x() <= rect.xmax()) {
                findRange(node.right,points,rect);
            }
        }else {

            if (node.left != null && node.left.point.y() >= rect.ymin()) {
                findRange(node.left,points,rect);
            }
            if (node.right != null && node.right.point.y() <= rect.ymin()) {
                findRange(node.right,points,rect);
            }
        }

        return;
          
    }

    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (pointSet.isEmpty()) return null;
        double closest = Double.MAX_VALUE;
        Point2D nearestPoint = null;
        return findNearest(p, nearestPoint, closest, root);
    }
    private Point2D findNearest(Point2D queryPoint, Point2D nearestPoint, double closest,Node node) {
        if (node == null) {
            return null;
            // return;
        }
        //find distance at current point
        if (queryPoint.distanceSquaredTo(node.point) < closest) {
            nearestPoint = node.point;
            closest = queryPoint.distanceSquaredTo(node.point);
        }

        if (node.left != null && queryPoint.distanceSquaredTo(node.left.point) < closest) {
            nearestPoint = findNearest(queryPoint,nearestPoint,closest,node.left);
            closest = queryPoint.distanceSquaredTo(node.left.point);
        }
        if (node.right != null && queryPoint.distanceSquaredTo(node.right.point) < closest) {
            nearestPoint = findNearest(queryPoint,nearestPoint,closest,node.right);
            closest = queryPoint.distanceSquaredTo(node.right.point);
        }

        return nearestPoint;
        // return;
    }

    // unit testing of the methods (optional) 
    public static void main(String[] args) {

        KdTree kd = new KdTree();
        //root
        Point2D p1 = new Point2D(0.7, 0.2);
        
        //left
        Point2D p2 = new Point2D(0.5, 0.4);
        
        //right
        Point2D p3 = new Point2D(0.2, 0.3);
        
        // left of left
        Point2D p4 = new Point2D(0.4, 0.7);
        
        Point2D p5 = new Point2D(0.9, 0.6);

        Point2D queryPoint = new Point2D(0.6,0.4);
        
        kd.insert(p1);
        kd.insert(p2);
        kd.insert(p3);
        kd.insert(p4);
        kd.insert(p5);
        RectHV r = new RectHV(0.45,0,1.0,0.8);
        
        kd.range(r);
        // kd.draw();

        StdOut.println(kd.nearest(queryPoint));
        kd.isEmpty();
        kd.size();

        StdDraw.setPenRadius(0.015);
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-1, 2);
        StdDraw.setYscale(-1, 2);
        // p1.draw();
        // p2.draw();
        // p3.draw();
        // p4.draw();
        // p5.draw();
        // r.draw();
        kd.draw();
        StdDraw.show();

    }
    
}
    

