import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class FastCollinearPoints {
  public FastCollinearPoints(Point[] ps) {
    if (ps == null || ps.length == 0)
      throw new IllegalArgumentException("points is null");
    for (int i = 0; i < ps.length; i++) {
      if (ps[i] == null)
        throw new IllegalArgumentException("points is null");
    }
    points = Arrays.copyOfRange(ps, 0, ps.length); 
    Arrays.sort(points);
    for (int i = 0; i < points.length-1; i++) {
      if (points[i].compareTo(points[i+1]) == 0)
        throw new IllegalArgumentException(points[i].toString() + " is repeated");
    }

    segments = new ArrayList<LineSegment>();
    findCollinearPoints();
  }
  
  public int numberOfSegments() {
    return segments.size();
  }
  
  public LineSegment[] segments() {
    return segments.toArray(new LineSegment[segments.size()]);
  }
  
  private Point[] points;
  private List<LineSegment> segments;
  
  private void findCollinearPoints()
  {
    for (int i = 0; i < points.length-3; i++) {
      Point[] sortedPoints = Arrays.copyOfRange(points, 0, points.length);      
      Arrays.sort(sortedPoints, points[i].slopeOrder());
      
      sortedPoints = append(sortedPoints, points[i]);
      double slope = points[i].slopeTo(sortedPoints[1]);
      int from, to;
      for (from = 1, to = 2; to < sortedPoints.length; to++) {
        if (slope != points[i].slopeTo(sortedPoints[to])) {
          if ((to - from) >= 3 && points[i].compareTo(sortedPoints[from]) < 0) {
            segments.add(new LineSegment(sortedPoints[0], sortedPoints[to-1]));
          }
          from = to;
        }
        slope = points[i].slopeTo(sortedPoints[to]);
      }
    }
  }
  
  private void showPoints(Point[] points)
  {
    for (Point p : points)
      StdOut.print(p);
    StdOut.println("");
  }
  
  private Point[] append(Point[] old, Point p)
  {
    int len = old.length;
    Point[] newPoints = new Point[len+1];
    System.arraycopy(old, 0, newPoints, 0, len);
    newPoints[len] = p;
    return newPoints;
  }
  
  public static void main(String[] args) {
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
    }

    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
      p.draw();
    }
    StdDraw.show();
    
    FastCollinearPoints collinear = new FastCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
  }
}
