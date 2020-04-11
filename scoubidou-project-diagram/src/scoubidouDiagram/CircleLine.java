package scoubidouDiagram;
import java.awt.Point;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
/**
 *   Returns a list of intersection points between a line which passes through given points,
     centerPoint and outerPoint, and a circle described by given radius and center coordinate
 * @author Yonatan Setbon
 *
 */
public class CircleLine {

    public static Point[] getCircleLineIntersectionPoint(Point centerPoint,
            Point outerPoint, double radius) {

        float dx = centerPoint.x - outerPoint.x;
        float dy = centerPoint.y - outerPoint.y;
        float dd = (float) Math.sqrt(dx*dx + dy*dy);
        float a = (float) Math.asin(radius / dd);
        float b = (float) Math.atan2(dy, dx);
        float t1 = b - a;
        Point tangentPoint1 = new Point ();
     //   System.out.println(centerPoint.y + radius*(-Math.cos(t1)));
        tangentPoint1.x= (int) Math.round(centerPoint.x + radius*Math.sin(t1)); 
        tangentPoint1.y= (int) Math.round(centerPoint.y + radius*(-Math.cos(t1)));
        float t2 = b + a;
        Point tangentPoint2 = new Point();
        tangentPoint2.x= (int) Math.round(centerPoint.x + radius*-Math.sin(t2)); 
        tangentPoint2.y= (int)Math.round(centerPoint.y + radius*Math.cos(t2));

        Point [] points = new Point [2];
        points[0]=	tangentPoint1;
        points[1] = tangentPoint2;
        return points;
    }
    


    public static void main(String[] args) {
        Point[] points = getCircleLineIntersectionPoint(new Point(0, 0),
                new Point(10, 100), 10);
    System.out.println(points[0].x+","+points[0].y);
    System.out.println(points[1].x+","+points[1].y);

      
    }
}