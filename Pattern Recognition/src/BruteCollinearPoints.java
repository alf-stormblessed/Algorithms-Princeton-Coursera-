
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import java.util.List;


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class BruteCollinearPoints {
    // rep
    private List<LineSegment> solution = new ArrayList<LineSegment>();

    

   public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
   {
       if (points == null)
           throw new java.lang.NullPointerException();
       else {
           
           for (int i = 0; i < points.length; i++) {
               
               if (points[i] == null)
                   throw new java.lang.NullPointerException();
           }
       }
       // Stopwatch myWatch = new Stopwatch();
       
       
       for (int i = 0; i < points.length; i++) {
           
           if (points[i] == null)
               throw new java.lang.NullPointerException();
           
           for (int j = 0; j != i; j++) {
               if (points[i].compareTo(points[j]) == 0)
                   throw new java.lang.IllegalArgumentException();
               for (int k = 0; k != i && k!= j ; k++) {
                   
                   for (int n = 0; n != k && n != j && n!= i ; n++) {
                       
                       if ( (Double.compare(points[i].slopeTo(points[j]), points[i].slopeTo(points[k])) == 0) &&
                       ( Double.compare(points[i].slopeTo(points[n]), points[i].slopeTo(points[k])) == 0 ))
                       {
                           List<Point> myPoints = new ArrayList<Point>(Arrays.asList(points[i], points[k], points[n], points[j]));
                           Comparator<Point> comp = (Point p1, Point p2) -> {
                                   return p2.compareTo(p1);
                           };
                           myPoints.sort(comp);
                           // StdOut.println(myPoints);
                           LineSegment segment = new LineSegment(myPoints.get(3), myPoints.get(0));
                           solution.add(segment);
                       
                       }

                   }

               }

           }  

       }
       // time = myWatch.elapsedTime();
   }
   

   public int numberOfSegments()        // the number of line segments
   {
       return solution.size();
   }
   public LineSegment[] segments()                // the line segments
   {
       LineSegment[] myAnswer = new LineSegment[solution.size()];
       for (int i = 0; i< solution.size(); i++) {
           
           myAnswer[i] = solution.get(i);
       }
       return myAnswer;
   }
   
   public static void main(String[] args) {

       // read the n points from a file
       In in = new In(args[0]);
       int n = in.readInt();
       Point[] points = new Point[n];
       for (int i = 0; i < n; i++) {
           int x = in.readInt();
           int y = in.readInt();
           points[i] = new Point(x, y);
       }

       // draw the points
       StdDraw.enableDoubleBuffering();
       StdDraw.setXscale(0, 32768);
       StdDraw.setYscale(0, 32768);
       for (Point p : points) {
           p.draw();
       }
       StdDraw.show();

       // print and draw the line segments
       BruteCollinearPoints collinear = new BruteCollinearPoints(points);
       for (LineSegment segment : collinear.segments()) {
           StdOut.println(segment);
           segment.draw();
       }
       StdDraw.show();
//       StdOut.println("time: " + collinear.getTime() + " seconds");
   }
}
