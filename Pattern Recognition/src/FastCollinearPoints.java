import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
// import edu.princeton.cs.algs4.Stopwatch;

public class FastCollinearPoints {
    
    // rep
    private Set<Segment> solution;
//    private double time;
    private LineSegment[] segments;

    private LineSegment[] segmentsCopy;
    private static final Comparator<PointAndSlope> BySlopeAndPosition = new BySlopeAndPosition();
    
    private class PointAndSlope {
        public Point point;
        public double slope;
        
        PointAndSlope(Point point, double slope) {
            
            this.point = point;
            this.slope = slope;
        }
        
    }
    private class Segment {
        
        private Point start;
        private Point end;
        
        public Segment(Point start, Point end) {
            
            this.start = start;
            this.end = end;
                       
        }
        public Point getStart() {
            
            return this.start;
        }
        
        public Point getEnd() {
            
            return this.end;
        }
        @Override
        public boolean equals(Object a) {
            
            return this.start == ((Segment)a).getStart() && this.end == ((Segment)a).getEnd();
        }
        
        @Override
        public int hashCode() {
            return (this.start.toString() + this.end.toString()).hashCode();
        }
        
    }
   public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
   {
       if (points == null)
           throw new java.lang.NullPointerException();
       else {
           
           for (int i = 0; i < points.length; i++) {
               
               if (points[i] == null)
                   throw new java.lang.NullPointerException();
           }
       }
       boolean oneDimensionGrid = false;
       solution = new HashSet<Segment>();
       // Stopwatch myWatch = new Stopwatch();
       
       for (int i = 0; i < points.length; i++) {
           // StdOut.println("progress: " + 100.0*(double)i/(double)points.length + " %");
           if (points[i] == null)
               throw new java.lang.NullPointerException();
           
           List<PointAndSlope> mySlopeArray = new ArrayList<PointAndSlope>();

           for (int j = 0; j < points.length ; j++) {

               PointAndSlope myPointAndSlope = new PointAndSlope(points[j], points[j].slopeTo(points[i]));
               mySlopeArray.add(myPointAndSlope);
           }
           
           Collections.sort(mySlopeArray, BySlopeAndPosition);
//           for (PointAndSlope p : mySlopeArray) {
//               
//               StdOut.println("point" + p.point + ", " + p.slope);
//           }
           
           double pastValue = mySlopeArray.get(0).slope;
           int count = 0;
           Point myFirstPoint = null;
           Point myLastPoint = null;
           
           for (int j = 1; j < mySlopeArray.size(); j++) {
               if (mySlopeArray.get(1).slope == Double.NEGATIVE_INFINITY)
                   throw new java.lang.IllegalArgumentException();
               // StdOut.println("count = " + count + ", point[i]= " + points[i] + ", first point = " + myFirstPoint);
               // StdOut.println("this point " + mySlopeArray.get(j).point + ", slope= " + mySlopeArray.get(j).slope + ", past = " + pastValue + ", count " + count);
               
                if (mySlopeArray.get(j).slope == pastValue) {
                    
                    if (count == 0) {
                        
                        if (mySlopeArray.get(0).point.compareTo(mySlopeArray.get(j-1).point) < 0 ) {
                            // mySlopeArray.get(0) is lower than the lowest point -> myFirstPoint = mySlopeArray.get(0)
                            myFirstPoint = mySlopeArray.get(0).point;
                            }
                        else {
                            
                            myFirstPoint = mySlopeArray.get(j-1).point;
                        }
                    }


                    count++;
                    if ((j == mySlopeArray.size()-1) && count >= 2) {
                        if (mySlopeArray.get(0).point.compareTo(mySlopeArray.get(j).point) > 0 ) {
                            // mySlopeArray.get(0) is bigger than the last point -> myLastPoint = mySlopeArray.get(0)
                            
                            myLastPoint = mySlopeArray.get(0).point;

                        }
                        else {
                            myLastPoint = mySlopeArray.get(j).point;
                        }
                        
                        aux(mySlopeArray, myLastPoint, myFirstPoint, j);
                        if (count == mySlopeArray.size()-2) {
                            oneDimensionGrid = true;
                            break;
                        }
                    }
                }
                else {

                    if (count >= 2) {// create the segment
                        

                        if (mySlopeArray.get(0).point.compareTo(mySlopeArray.get(j-1).point) > 0 ) {
                            // mySlopeArray.get(0) is bigger than the last point -> myLastPoint = mySlopeArray.get(0)
                            
                            myLastPoint = mySlopeArray.get(0).point;

                        }
                        else {
                            myLastPoint = mySlopeArray.get(j-1).point;
                        }
                        aux(mySlopeArray, myLastPoint, myFirstPoint, j-1);
                    }
                    
                    count = 0;
                    
                }

                
                pastValue = mySlopeArray.get(j).slope;
           
           }
           if (oneDimensionGrid)
               break;


       }
       segments = new LineSegment[solution.size()];
       segmentsCopy = new LineSegment[solution.size()];
       int l = 0;
       for (Segment s : solution) {
           
           segments[l] = new LineSegment(s.start, s.end);
           segmentsCopy[l] = segments[l];
           l++;
       }
       // time = myWatch.elapsedTime();
   }
   
   private void aux(List<PointAndSlope> mySlopeArray, Point myLastPoint, Point myFirstPoint, int j) {
       
       if (mySlopeArray.get(0).point.compareTo(myFirstPoint) < 0) {
           // the point[i] is less than myfirstpoint -> start from point[i] 
           Segment mySegment = new Segment(mySlopeArray.get(0).point, mySlopeArray.get(j-1).point);
           if (!solution.contains(mySegment))
               solution.add(mySegment);
       }
       else if (mySlopeArray.get(0).point.compareTo(mySlopeArray.get(j).point) > 0 ) {

           // the point[i] is bigger than my last point -> end on point[i]
           Segment mySegment = new Segment(myFirstPoint, mySlopeArray.get(0).point);
           if (!solution.contains(mySegment))
               solution.add(mySegment);
       }
       else {

       // the point[i] is somewhere on the segment -> segment from myfirstpoint to the last
           Segment mySegment = new Segment(myFirstPoint, mySlopeArray.get(j).point);
           if (!solution.contains(mySegment))
               solution.add(mySegment);
       
           
       }
   }
   private static class BySlopeAndPosition implements Comparator<PointAndSlope>
   {
       @Override
       public int compare(PointAndSlope p1, PointAndSlope p2)
       {
           // StdOut.println("Comparing " + p1.slope + ", " + p2.slope);
           if (Double.compare(p1.slope, p2.slope) == 0) {
               // StdOut.println("equal slope, ordering by point position");
               return p1.point.compareTo(p2.point);
           }
           else {
               // StdOut.println("different slope");
               return Double.compare(p1.slope, p2.slope);
           }
       }

   }

   public int numberOfSegments()        // the number of line segments
   {
       return solution.size();
   }
   public LineSegment[] segments()                // the line segments
   {
       for (int i = 0; i < segments.length; i++) {
           
           segments[i] = segmentsCopy[i];
       }
       return segments;
   }
   

//   private double getTime()
//   {
//       return time;
//       
//   }
   
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
       FastCollinearPoints collinear = new FastCollinearPoints(points);


       for (LineSegment segment : collinear.segments()) {
           StdOut.println("segments found " + segment);
           segment.draw();
       }
       LineSegment[] s = collinear.segments();
       s[0] = new LineSegment(new Point(4,5), new Point(5,4));
       for (LineSegment segment : collinear.segments()) {
           StdOut.println("segments found " + segment);
           segment.draw();
       }
       StdDraw.show();
       // StdOut.println("time: " + collinear.getTime() + " seconds");
   }
   
}
