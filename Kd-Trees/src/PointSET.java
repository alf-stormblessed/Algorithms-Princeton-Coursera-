import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class PointSET {
    
    // Rep
    private int size;
    private TreeSet<Point2D> pointsTree;
    
   public PointSET()                               // construct an empty set of points 
   {
       size = 0;
       pointsTree = new TreeSet<Point2D>();
       
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
       
       if (!pointsTree.contains(p)) {
           pointsTree.add(p);
           size++;
       }
   }
   public boolean contains(Point2D p)            // does the set contain point p? 
   {
       if (p == null)
           throw new java.lang.NullPointerException();
       return pointsTree.contains(p);
   }
   public void draw()                         // draw all points to standard draw 
   {
       StdDraw.setPenRadius(0.01);
       for (Point2D p : pointsTree) {
           
           StdDraw.point(p.x(), p.y());
       }
   }
   public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle
   {
       if (rect == null)
           throw new java.lang.NullPointerException();
       List<Point2D> solution = new ArrayList<Point2D>();
       
       for(Point2D p : pointsTree) {
           
           if (p.x() <= rect.xmax() && p.x() >= rect.xmin())
               if (p.y() <= rect.ymax() && p.y() >= rect.ymin())
                   solution.add(p);
           
       }
             
       return solution;
   }
   public Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty 
   {
       if (p == null)
           throw new java.lang.NullPointerException();
       double minDist = Double.MAX_VALUE;
       Point2D solution = null;
       
       if (pointsTree.isEmpty())
           return null;
       else {
           
           for (Point2D point : pointsTree) {
//                   StdOut.println("     COMPARING " + p + " TO " + point);
                   if (Double.compare(point.distanceTo(p), minDist) < 0) {
                       minDist = point.distanceTo(p);
                       solution = point;
                   }
           }
       }
       return solution;
   }

   public static void main(String[] args)                  // unit testing of the methods (optional) 
   {
       PointSET test = new PointSET();
       test.insert(new Point2D(0.3,0.2));
       for (int i = 0; i < 100000; i++) {
           
           test.insert(new Point2D(StdRandom.uniform(), StdRandom.uniform()));
       }
       RectHV myRect = new RectHV(0.1,0.1,0.5,0.5);
       StdOut.println(test.contains(new Point2D(0.3,0.2)));
       StdOut.println(test.nearest(new Point2D(0.1,0.1)));
       // StdOut.println(test.range(myRect));
       StdOut.println(test.size());
       test.draw();
   }
}
