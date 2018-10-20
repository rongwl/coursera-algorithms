import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

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
    //for (Point p : points)
      //StdOut.println(p);
    segments = new ArrayList<LineSegment>();
  }
  
  public int numberOfSegments() {
    return segments.size();
  }
  
  public LineSegment[] segments() {
    for (int i = 0; i < points.length-3; i++) {
      Point[] pointsBuff = new Point[points.length-1-i];
      for (int j = 0, k = i+1; j < pointsBuff.length; j++, k++)
        pointsBuff[j] = points[k];
      
      Arrays.sort(pointsBuff, points[i].slopeOrder());
      /*StdOut.print("sort points: ");
      for (Point p : pointsBuff)
        StdOut.print(p);
      StdOut.println("");*/
      int equal = 0;
      for (int j = 0; j < pointsBuff.length-1; j++) {
        //StdOut.println("point i:" + points[i] + " pointBuf j:" + pointsBuff[j] + " pointBuf j+1:" + pointsBuff[j+1]);
        //StdOut.println("slope i-j:" + points[i].slopeTo(pointsBuff[j]) + " slope i-j+1:" + points[i].slopeTo(pointsBuff[j+1]));
        if (points[i].slopeTo(pointsBuff[j]) == points[i].slopeTo(pointsBuff[j+1]))
          equal++;
        else {
          //StdOut.println("equal:" + equal);
          if (equal >= 2)
            segments.add(new LineSegment(points[i], pointsBuff[j]));
          equal = 0;
        }
          
      }
    }
    return (LineSegment[])segments.toArray(new LineSegment[segments.size()]);
  }
  
  private Point[] points;
  List<LineSegment> segments;
  
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