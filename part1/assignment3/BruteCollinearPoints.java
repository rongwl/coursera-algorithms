import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
  public BruteCollinearPoints(Point[] points) {
    if (points == null || points.length == 0)
      throw new IllegalArgumentException("points is null");
    for (int i = 0; i < points.length; i++) {
      if (points[i] == null)
        throw new IllegalArgumentException("point[" + i + "] is null");
    }    
    this.points = points;
    Arrays.sort(this.points);
    for (int i = 0; i < this.points.length-1; i++) {
      if (this.points[i].compareTo(this.points[i+1]) == 0)
        throw new IllegalArgumentException(this.points[i].toString() + " is repeated");
    }
    segments = new ArrayList<LineSegment>();
  }
  
  public int numberOfSegments() {
    return segments.size();
  }
  
  public LineSegment[] segments() {
    for (int i = 0; i <= points.length-4; i++) {
      for (int j = i+1; j <= points.length-3; j++) {
        for (int k = j+1; k <= points.length-2; k++) {
          for (int l = k+1; l <= points.length-1; l++) {
            if (points[i].slopeTo(points[j]) == points[j].slopeTo(points[k]) &&
                points[j].slopeTo(points[k]) == points[k].slopeTo(points[l]))
              segments.add(new LineSegment(points[i], points[l]));
          }
        }
      }
    }    
    return (LineSegment[])segments.toArray(new LineSegment[segments.size()]);
  }
  
  private Point[] points;
  private List<LineSegment> segments;
  
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
    
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
        StdOut.println(segment);
        segment.draw();
    }
    StdDraw.show();
  }
}
