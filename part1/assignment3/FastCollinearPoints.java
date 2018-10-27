import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class FastCollinearPoints {
  public FastCollinearPoints(Point[] points) {
    if (points == null || points.length == 0)
      throw new IllegalArgumentException("points is null");
    for (int i = 0; i < points.length; i++) {
      if (points[i] == null)
        throw new IllegalArgumentException("points is null");
    }
    this.points = points;
    Arrays.sort(this.points);
    for (int i = 0; i < this.points.length-1; i++) {
      if (this.points[i].compareTo(this.points[i+1]) == 0)
        throw new IllegalArgumentException(this.points[i].toString() + " is repeated");
    }

    segments = new ArrayList<LineSegment>();
    linePoints = new ArrayList<Point[]>();
  }
  
  public int numberOfSegments() {
    return segments.size();
  }
  
  public LineSegment[] segments() {
    for (int i = 0; i < points.length-3; i++) {
      Point[] sortedPoints = Arrays.copyOfRange(points, i, points.length);      
      Arrays.sort(sortedPoints, sortedPoints[0].slopeOrder());
      
      sortedPoints = append(sortedPoints, sortedPoints[0]);
      double slope = sortedPoints[0].slopeTo(sortedPoints[1]);
      int from, to;
      for (from = 1, to = 2; to < sortedPoints.length; to++) {
        if (slope != sortedPoints[0].slopeTo(sortedPoints[to])) {
          if ((to - from) >= 3 && !containsLine(sortedPoints[from], sortedPoints[to-1])) {
            linePoints.add(Arrays.copyOfRange(sortedPoints, from, to));
            segments.add(new LineSegment(sortedPoints[0], sortedPoints[to-1]));
          }
          from = to;
        }
        slope = sortedPoints[0].slopeTo(sortedPoints[to]);
      }
    }
    
    return (LineSegment[])segments.toArray(new LineSegment[segments.size()]);
  }
  
  private Point[] points;
  private List<Point[]> linePoints;
  private List<LineSegment> segments;
  
  private void showPoints(Point[] points)
  {
    for (Point p : points)
      StdOut.print(p);
    StdOut.println("");
  }
  
  private boolean containsLine(Point a, Point b)
  {
    for (int i = 0; i < linePoints.size(); i++) {
      if (Arrays.binarySearch(linePoints.get(i), a) >= 0 &&
          Arrays.binarySearch(linePoints.get(i), b) >= 0)
        return true;
    }    
    return false;
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