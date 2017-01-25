import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class KdTree {

    // rep
    private int size;
    private Node root;
    private double minDist;
    private Point2D champion;
    
    private static class Node {
        
        public Point2D point;
        public Node leftNode;
        public Node rightNode;
        public enum NodeType {X, Y};
        public NodeType type;
        public RectHV rect;
        
        public Node (Point2D p, NodeType type, RectHV rectangle) {
            rect = rectangle;
            this.type = type; 
            point = p;
            leftNode = null;
            rightNode = null;
        }
        
    }
    
    public KdTree()                               // construct an empty set of points 
    {
        size = 0;
        root = null;
        champion = null;
        minDist = 2;
        
    }
    public boolean isEmpty()                      // is the set empty? 
    {
        return size == 0;
    }
    public int size()                         // number of points in the set 
    {
        return size;
    }
    public void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (p == null)
            throw new java.lang.NullPointerException();
        
        boolean endLoop = false;
        if (root == null) {
            
            root  = new Node(p, Node.NodeType.X, new RectHV(0.0, 0.0, 1.0, 1.0));
            endLoop = true;
            size++;
        }
        Node myNode = root;

        
        while (!endLoop) {
            
            if (myNode.type.equals(Node.NodeType.X)) { // Node of type X
//                StdOut.println("node of type X");
                if (Double.compare(myNode.point.x(), p.x()) > 0) {
                    
                    if (myNode.leftNode == null) {
//                        StdOut.println("creating new leftNode of type Y of point " + p);
                        myNode.leftNode = new Node(p, Node.NodeType.Y, 
                                new RectHV(myNode.rect.xmin(), myNode.rect.ymin(), myNode.point.x(), myNode.rect.ymax()));
//                        StdOut.println("rect " + myNode.leftNode.rect);
                        size++;
                        endLoop = true;
                    }
                    else
                        myNode = myNode.leftNode;
                }
                else if (Double.compare(myNode.point.x(), p.x()) < 0) {
                    
                    if (myNode.rightNode == null) {
//                        StdOut.println("creating new RightNode of type Y of point " + p);
                        myNode.rightNode = new Node(p, Node.NodeType.Y,
                                new RectHV(myNode.point.x(), myNode.rect.ymin(), myNode.rect.xmax(), myNode.rect.ymax()));
//                        StdOut.println("rect " + myNode.rightNode.rect);
                        size++;
                        endLoop = true;
                        
                    }
                    else {

                        myNode = myNode.rightNode;
                    }
                }
                else { // same x-coordinate

                    if (Double.compare(myNode.point.y(), p.y()) != 0) {// don't insert same point twice
                        
                        if (myNode.rightNode == null) {
//                          StdOut.println("creating new RightNode of type Y of point " + p);
                          myNode.rightNode = new Node(p, Node.NodeType.Y,
                                  new RectHV(myNode.point.x(), myNode.rect.ymin(), myNode.rect.xmax(), myNode.rect.ymax()));
//                          StdOut.println("rect " + myNode.rightNode.rect);
                          size++;
                          endLoop = true;
                          
                      }
                      else {

                          myNode = myNode.rightNode;
                        }
                        
                    }
                    else
                        endLoop = true;
                }
            }
            else { // Node of type Y
//                StdOut.println("node of type Y");
                if (Double.compare(myNode.point.y(), p.y()) > 0) {
                    
                    if (myNode.leftNode == null) {
//                        StdOut.println("creating new leftNode of type X of point " + p);
                        myNode.leftNode = new Node(p, Node.NodeType.X,
                                new RectHV(myNode.rect.xmin(), myNode.rect.ymin(), myNode.rect.xmax(), myNode.point.y()));
//                        StdOut.println("rect " + myNode.leftNode.rect);
                        size++;
                        endLoop = true;
                    }
                    else
                        myNode = myNode.leftNode;
                }
                else if (Double.compare(myNode.point.y(), p.y()) < 0) {
                    
                    if (myNode.rightNode == null) {
//                        StdOut.println("creating new rightNode of type X of point " + p);
                        myNode.rightNode = new Node(p, Node.NodeType.X,
                                new RectHV(myNode.rect.xmin(), myNode.point.y(), myNode.rect.xmax(), myNode.rect.ymax()));
//                        StdOut.println("rect " + myNode.rightNode.rect);
                        size++;
                        endLoop = true;
                    }
                    else
                        myNode = myNode.rightNode;
                }
                else { // same y-coordinate
                   if (Double.compare(myNode.point.x(), p.x()) != 0) {// don't insert same point twice
                        
                       if (myNode.rightNode == null) {
//                         StdOut.println("creating new rightNode of type X of point " + p);
                         myNode.rightNode = new Node(p, Node.NodeType.X,
                                 new RectHV(myNode.rect.xmin(), myNode.point.y(), myNode.rect.xmax(), myNode.rect.ymax()));
//                         StdOut.println("rect " + myNode.rightNode.rect);
                         size++;
                         endLoop = true;
                     }
                     else
                         myNode = myNode.rightNode;
                    }
                    else
                        endLoop = true;
                }
            }
            
        }
        
    }
    public boolean contains(Point2D p)            // does the set contain point p? 
    {
        Node node = root;
        // value we compare with, alternates between x an y

        
        while (true) {
            if (node == null) return false;
            if (node.point.equals(p)) return true;

            if ((node.type == Node.NodeType.X && (p.x() < node.point.x()))
                || (node.type == Node.NodeType.Y  && (p.y() < node.point.y()))) {
                    node = node.leftNode;
            }
            else {
                    node = node.rightNode;
            }

        }
    }
    public void draw()                         // draw all points to standard draw 
    {
        if (root != null) {
//            StdOut.println("drawing node of point " + root.point);
            StdDraw.setPenRadius(0.01);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.point(root.point.x(), root.point.y());
            StdDraw.setPenRadius();
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(root.point.x(), 0, root.point.x(), 1);
            if (root.leftNode != null)
                draw(root.leftNode, root, true);
            if (root.rightNode != null)
                draw(root.rightNode, root, false);
        }
    }    
    private void draw(Node node, Node parent, boolean isLeft) {

        if (node != null) {
//            StdOut.println("drawing node of point " + node.point);
            if (node.type.equals(Node.NodeType.X)) {
                
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                StdDraw.point(node.point.x(), node.point.y());
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius();
                StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
            }
            else {
                
                StdDraw.setPenColor(StdDraw.BLACK);
                StdDraw.setPenRadius(0.01);
                StdDraw.point(node.point.x(), node.point.y());
                StdDraw.setPenRadius();
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line( node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
            }
            if (node.leftNode != null) 
                draw(node.leftNode, node, true);
            if (node.rightNode != null)
                draw(node.rightNode, node, false);
        }
    }
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle 
    {
        if (rect == null)
            throw new java.lang.NullPointerException();
        else if (this.size() > 0) {
            List<Point2D> solution = new ArrayList<Point2D>();
            if (rect.contains(root.point))
                solution.add(root.point);
            if (root.leftNode != null && root.leftNode.rect.intersects(rect))
                range(rect, root.leftNode, solution);
            if (root.rightNode != null && root.rightNode.rect.intersects(rect))
                range(rect, root.rightNode, solution);
            return solution;
            }
        else return Arrays.asList();
    }
    private void range (RectHV rect, Node node, List<Point2D> solution) {

        
        if (rect.contains(node.point))
            solution.add(node.point);
        if (node.leftNode != null && node.leftNode.rect.intersects(rect))
            range(rect, node.leftNode, solution);
        if (node.rightNode != null && node.rightNode.rect.intersects(rect))
            range(rect, node.rightNode, solution);

    }
    public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
    {
        // fail fast
        if (p == null)
            throw new java.lang.NullPointerException();
        else if (root == null)
            return null;


        champion = root.point;
        
        if (p.x() < root.point.x()){
            nearest(root.leftNode, p);
            nearest(root.rightNode, p);
        }
        else {
            nearest(root.rightNode, p);
            nearest(root.leftNode, p);
        }

//        StdOut.println("closest point is " + champion);
        return champion;
    }

    private void nearest(Node node, Point2D p) {

        if (node != null) {
            // if this point is closer than the current champion update it
            if (p.distanceSquaredTo(champion) > node.point.distanceSquaredTo(p))
                champion = node.point;
            
            
            // its sub-nodes
            // only explored if they can contain a point closer than the current champion
            if (node.rect.distanceSquaredTo(p) < p.distanceSquaredTo(champion)) {

                if ((node.type == Node.NodeType.X && p.x() < node.point.x()) || 
                                        (node.type == Node.NodeType.Y && p.y() < node.point.y()) ) {
                    // explore left node first
                    nearest(node.leftNode, p);
                    nearest(node.rightNode, p);
                }
                else { // explore right node first
                
                    nearest(node.rightNode, p);
                    nearest(node.leftNode, p);
                }
            }
         }
    }
    public static void main(String[] args)                  // unit testing of the methods (optional) 
    {
//        KdTree test = new KdTree();
//        test.insert(new Point2D(0.7,0.2));
//        test.insert(new Point2D(0.5,0.4));
//        test.insert(new Point2D(0.2,0.3));
//        test.insert(new Point2D(0.4,0.7));
//        test.insert(new Point2D(0.9,0.6));
//        
//
//        RectHV myRect = new RectHV(0.1,0.1,0.5,0.5);
//
//        test.draw();

            RectHV rect = new RectHV(0.0, 0.0, 1.0, 1.0);
//            StdDraw.enableDoubleBuffering();
            KdTree kdtree = new KdTree();
            // kdtree.insert(new Point2D(0.1, 0.1));
            kdtree.range(new RectHV(0.1, 0.2, 0.4, 0.5));
            StdOut.print("is (0.1,0.1) in kdtree?: " + kdtree.contains(new Point2D(0.1, 0.1)));
            while (true) {
                if (StdDraw.mousePressed()) {
                    double x = StdDraw.mouseX();
                    double y = StdDraw.mouseY();
                    StdOut.printf("%8.6f %8.6f\n", x, y);
                    Point2D p = new Point2D(x, y);
                    if (rect.contains(p)) {
                        StdOut.printf("%8.6f %8.6f\n", x, y);
                        kdtree.insert(p);
                        StdDraw.clear();
                        kdtree.draw();
                        StdDraw.show();
                        Point2D p1 = new Point2D(0.5, 0.5);
//                        StdOut.println("closer point to " + p1 + " is " + kdtree.nearest(p1) + " with d= " + kdtree.nearest(p1).distanceSquaredTo(p1));
//                        StdOut.println("root " + kdtree.root.point);
                    }
                }
                StdDraw.pause(50);
            }

}
 
}
