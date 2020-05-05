package scoubidouDiagram;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;

import javafx.scene.shape.*;
public class StringCases {
	public Line2D reperesnetedLine = new Line2D.Double();
	public Point centerCircle = new Point();

	/**
	 * A constructor
	 */
	public StringCases() {

	}

	/**
	 * 
	 * @param oneX
	 * @param oneY
	 * @param tangent
	 * @param stitchRec
	 * @param axisX
	 * @param axisX2
	 * @param axisY
	 * @param axisY2
	 * @param R
	 * @return a shape of a string, when its supposed to sit exactly on the
	 *         border of the rectangle stitch stitchRec.
	 */
	public Shape regularCase(int oneX, int oneY, double tangent, Rectangle stitchRec, int axisX, int axisX2, int axisY,
			int axisY2, double R) {
		int cubicYcontrol = 20;
		// point one is (0,0)
		Point one = new Point(oneX, oneY);
		int length = (int) Math
				.round(MyPrintCanvasDiagram.length - ((double) MyPrintCanvasDiagram.length / (double) 4));
System.out.println("MyPrintCanvasDiagram.length:"+MyPrintCanvasDiagram.length);
		// option 1 and 2
		if (tangent < 90 && tangent > 0) {
			// angle in radians
			double angle = tangent * Math.PI / ((double) 180);
			// 2.calculation of point 3, 6, and 9
			Point three = new Point(oneX + length, oneY);
			int distanceThreeFromSix = (int) Math.round((double) 2 * R / Math.sin(angle));
			Point six = new Point(three.x - distanceThreeFromSix, three.y);
			Point nine = new Point((int) Math.round((three.getX() + six.getX()) / 2), three.y);
			Point two = new Point();
			// 3.finding closest intersection from axisX and axisY

			// intersection of axisY with the line point of 3 with angle tangent
			int intersectionAxisY = (int) Math.round(Math.tan(angle) * (axisY - nine.getX()) + nine.getY());
			if (intersectionAxisY < axisX) {
				two = new Point(axisY, intersectionAxisY);
			}
			// intersection of axisX with the line point of 3 with angle tangent
			else {
				int intersectionAxisX = (int) Math
						.round(nine.getX() + (((double) axisX - nine.getY()) / Math.tan(angle)));
				two = new Point(intersectionAxisX, axisX);

			}

			// creating the represented line of the strings (the middle line)
			// two-nine
			reperesnetedLine.setLine(nine, two);

			// 3.finding four

			Point[] circleIntersect = CircleLine.getCircleLineIntersectionPoint(two, three, R);
			Point four = new Point();
			if (circleIntersect[0].x < circleIntersect[1].x) {
				four = circleIntersect[1];
			}

			else {
				four = circleIntersect[0];
			}
			// 4+5. finding 5
			Point five = findOppositeOfPointInCircle(four, two);
			// changing point six such that the line 6-5 is parallel to 3-4
			double tangentOfThree = ((double) (three.y - four.y)) / ((double) (three.x - four.x));
			six = findPointSix(tangentOfThree, one, five);
			Point seven = new Point();
			Point eight = new Point();
			// Continuing with option 1
			if (six.getX() >= one.getX()) {
				// 6. finding 7 and eight
				seven = new Point(six.x, (int) (six.y - 4 * R));
				eight = getClosestPointOnALine(four, three, seven);
				Path2D.Double path = new Path2D.Double();
				
				
				path.moveTo(two.x, two.y);							
				path.lineTo(five.x, five.y);
				path.lineTo(six.x, six.y);
				path.lineTo(one.x , one.y);
				
				path.moveTo(one.x , one.y);
				path.curveTo(one.x , one.y-cubicYcontrol, eight.x, eight.y, three.x, three.y);	
				path.lineTo(four.x, four.y);
				path.lineTo(two.x, two.y);

				AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
				Shape path2 = path.createTransformedShape(at);
				centerCircle = new Point((int) Math.round((five.getX() - four.getX()) / 2),
						(int) Math.round((five.getY() - four.getY()) / 2));
				centerCircle = two;
				return path2;
			}
			// Continuing with option 2
			else {
				// 1. calculating t
				double distanceSixToOne = -six.getX() + one.getX();
				int t = (int) Math.round(Math.tan(angle) * distanceSixToOne);
				// 2. calculating points 2,3,4 and 5
				two = new Point(two.x, two.y - t);
				three = new Point(three.x, three.y - t);
				four = new Point(four.x, four.y - t);
				five = new Point(five.x, five.y - t);
				seven = getClosestPointOnALine(four, three, one);
				nine = new Point((int) Math.round(one.x + 2 * R), one.y);
				double eightY = seven.getY() + Math.tan(angle) * (nine.getX() - seven.getX());
				eight = new Point(nine.x, (int) Math.round(eightY));
				Path2D.Double path = new Path2D.Double();
				
				path.moveTo(two.x, two.y);
				path.lineTo(five.x, five.y);
				path.lineTo(one.x, one.y);	
				path.moveTo(one.x , one.y);
				path.curveTo(one.x, one.y-cubicYcontrol, eight.x, eight.y, three.x, three.y);				
				path.lineTo(four.x, four.y);
				path.lineTo(two.x, two.y);

			
				AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
				Shape path2 = path.createTransformedShape(at);
				// center of circle is center of four and five
				centerCircle = two;

				// creating the represented line of the strings (the middle
				// line)
				// two-middleSixSeven
				Point middleSixSeven = new Point((int) Math.round((six.getX() + seven.getX()) / 2),
						(int) Math.round((six.getY() + seven.getY()) / 2));
				reperesnetedLine.setLine(middleSixSeven, two);

				return path2;
			}
		}
		// option 3
		else if (tangent == 90) {
			double angle = tangent * (Math.PI / ((double) 180));
			// 2.calculation of point 3
			Point three = new Point(oneX + length, oneY);
			Point four = new Point(three.x, axisX);
			Point five = new Point((int) Math.round(four.x - 2 * R), four.y);
			Point six = new Point((int) Math.round(three.x - 2 * R), three.y);
			Point eight = new Point(three.x, (int) Math.round(three.y - 4 * R));

			Path2D.Double path = new Path2D.Double();
			path.moveTo(six.x, six.y);
			path.lineTo(one.x, one.y);
			path.moveTo(one.x , one.y);
			path.curveTo(one.x, one.y-cubicYcontrol, eight.x, eight.y, three.x, three.y);
			path.lineTo(four.x, four.y);
			path.lineTo(five.x, five.y);	
			path.lineTo(six.x, six.y);
			
			AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
			Shape path2 = path.createTransformedShape(at);
			// center of circle is center of four and five
			centerCircle = new Point((int) Math.round(five.getX() + (four.getX() - five.getX()) / 2),
					(int) Math.round(five.getY()));
			Point two = centerCircle;
			// creating the represented line of the strings (the middle
			// line)
			// two-middleSixThree
			Point middleSixThree = new Point((int) Math.round((six.getX() + three.getX()) / 2),
					(int) Math.round((six.getY() + three.getY()) / 2));
			reperesnetedLine.setLine(middleSixThree, two);
			return path2;
		}
		// option 4 and 5
		else {
			one = new Point(oneX +MyPrintCanvasDiagram.length- length, oneY);
			// changing tangent to a sharp angle
			tangent = 180 - tangent;
			// angle in radians
			double angle = tangent * Math.PI / 180;
			// 2.calculation of point 3, 6, and 9
			
			Point three = new Point(oneX + MyPrintCanvasDiagram.length, oneY);
			

			int distanceOneFromSix = (int) Math.round((double) 2 * R / Math.sin(angle));
			Point six = new Point(one.x + distanceOneFromSix, one.y);

			Point nine = new Point((int) Math.round((one.getX() + six.getX()) / 2), one.y);
			Point two = new Point();
			// 3.finding closest intersection from axisX and axisY

			// intersection of axisY with the line point of 3 with angle tangent
			int intersectionAxisY = (int) Math.round(Math.tan(angle) * (nine.getX() - axisY2) + nine.getY());
			if (intersectionAxisY < axisX) {
				two = new Point(axisY2, intersectionAxisY);
				// System.out.println("intersectionAxisY2:" +
				// intersectionAxisY);
			}
			// intersection of axisX with the line point of 3 with angle tangent
			else {
				int intersectionAxisX = (int) Math.round(nine.getX() - ((axisX - nine.getY()) / Math.tan(angle)));
				two = new Point(intersectionAxisX, axisX);
				// System.out.println("intersectionAxisX2:" +
				// intersectionAxisX);

			}

			// 3.finding four
			Point[] circleIntersect = CircleLine.getCircleLineIntersectionPoint(two, one, R);
			Point four = new Point();
			if (circleIntersect[0].x < circleIntersect[1].x) {
				four = circleIntersect[0];
			}

			else {
				four = circleIntersect[1];
			}
			// 4+5. finding 5
			Point five = findOppositeOfPointInCircle(four, two);
			// changing point six such that the line 6-5 is parallel to 3-4
			double tangentOfOne = ((double) (one.y - four.y)) / ((double) (one.x - four.x));
			six = findPointSix(tangentOfOne, three, five);
			Point seven = new Point();
			Point eight = new Point();
			// Continuing with option 4
			if (six.getX() <= three.getX()) {
				// 6. finding 7 and eight
				seven = new Point(six.x, (int) (six.y - 4 * R));
				eight = getClosestPointOnALine(four, one, seven);
				Path2D.Double path = new Path2D.Double();
				//path.moveTo(oneTest.x, oneTest.y);
		//		path.lineTo(two.x, two.y);

				path.moveTo(two.x, two.y);
				path.lineTo(five.x, five.y);
				path.lineTo(six.x, six.y);
				path.lineTo(three.x , three.y);
				path.curveTo(three.x, three.y-cubicYcontrol, eight.x, eight.y, one.x, one.y);
				path.lineTo(four.x, four.y);
				path.lineTo(two.x, two.y);
			//	path.lineTo(oneTest.x+length, oneTest.y);

				AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
				Shape path2 = path.createTransformedShape(at);
				// center of circle is center of four and five
				centerCircle = two;

				// creating the represented line of the strings (the middle
				// line)
				// two-middleSixOne
				Point middleSixOne = new Point((int) Math.round((six.getX() + one.getX()) / 2),
						(int) Math.round((six.getY() + one.getY()) / 2));
				reperesnetedLine.setLine(middleSixOne, two);

				return path2;
			}
			// Continuing with option 5
			else {
				// 1. calculating t
				double distanceThreeToOne = Math.abs(six.getX() - three.getX());
				int t = (int) Math.round(Math.tan(angle) * distanceThreeToOne);
				// 2. calculating points 2,3,4 and 5
				two = new Point(two.x, two.y - t);
				one = new Point(one.x, one.y - t);
				four = new Point(four.x, four.y - t);
				five = new Point(five.x, five.y - t);
				seven = getClosestPointOnALine(one, four, three);
				nine = new Point((int) Math.round(three.x - 2 * R), three.y);
				double eightY = seven.getY() - Math.tan(angle) * (Math.abs(nine.getX() - seven.getX()));
				eight = new Point(nine.x, (int) Math.round(eightY));
				
				Path2D.Double path = new Path2D.Double();

				
				
				path.moveTo(two.x, two.y);
				path.lineTo(five.x, five.y);
				path.lineTo(three.x , three.y);	
				path.curveTo(three.x, three.y-cubicYcontrol, eight.x, eight.y, one.x, one.y);
				path.lineTo(four.x, four.y);
				path.lineTo(two.x, two.y);

				AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
				Shape path2 = path.createTransformedShape(at);
				// center of circle is center of four and five
				centerCircle = two;

				// creating the represented line of the strings (the middle
				// line)
				// two-middleThreeOne
				Point middleThreeOne = new Point((int) Math.round((three.getX() + one.getX()) / 2),
						(int) Math.round((three.getY() + one.getY()) / 2));
				System.out.println("middleThreeOne" + middleThreeOne + "two" + two);
				reperesnetedLine = new Line2D.Double();
				reperesnetedLine.setLine(middleThreeOne, two);

				return path2;
			}
		}
	}

	/**
	 * The method for back weaving where strings don't need to go literally all
	 * outside
	 * 
	 * @param oneX
	 * @param oneY
	 * @param tangent
	 * @param stitchRec
	 * @param axisX
	 * @param axisX2
	 * @param axisY
	 * @param axisY2
	 * @param R
	 * @return a array of two shapes of the string
	 */

	public Shape[] backWeavingCase(int oneX, int oneY, double tangent, Rectangle stitchRec, int axisX, int axisX2,
			int axisY, int axisY2, double R, int lengthOfBackWeave) {

		// point one is (0,0)
		Point one = new Point(oneX, oneY);
		
		//for control of the bezier curve 
		int cubicYcontrol = 20;

		// option 1
		if (tangent < 90 && tangent > 0) {
			// angle in radians
			double angle = tangent * Math.PI / ((double) 180);
			// 2.calculation of point 3, 6, 10, 8

			Point three = new Point((int) Math.round(oneX + 2 * R), oneY - lengthOfBackWeave);
			Point six = new Point(one.x, one.y - lengthOfBackWeave);

			Point ten = new Point((int) Math.round(one.x + 2 * R), one.y);

			int distanceSixFromEight = (int) Math.round((double) (2 * R) / Math.sin(angle));

			Point eight = new Point(six.x + distanceSixFromEight, six.y);

			Point nine = new Point((int) Math.round((six.getX() + eight.getX()) / 2), six.y);

			Point two = new Point();
			// 3.finding closest intersection from axisX and axisY

			// intersection of axisY with the line point of 3 with angle tangent
			int intersectionAxisY = (int) Math.round(Math.tan(angle) * (axisY - nine.getX()) + nine.getY());
			if (intersectionAxisY < axisX) {
				two = new Point(axisY, intersectionAxisY);
			}

			// intersection of axisX with the line point of 3 with angle tangent
			else {
				int intersectionAxisX = (int) Math
						.round(nine.getX() + (((double) axisX - nine.getY()) / Math.tan(angle)));
				two = new Point(intersectionAxisX, axisX);

			}

			// creating the represented line of the strings (the middle line)
			// two-nine
			reperesnetedLine.setLine(nine, two);

			// 4.finding four

			Point[] circleIntersect = CircleLine.getCircleLineIntersectionPoint(two, eight, R);
			Point four = new Point();
			if (circleIntersect[0].x < circleIntersect[1].x) {
				four = circleIntersect[1];
			}

			else {
				four = circleIntersect[0];
			}
			// 5. finding 5
			Point five = findOppositeOfPointInCircle(four, two);
			// changing point six such that the line 6-5 is parallel to 3-4
			double tangentOfEight = ((double) (eight.y - four.y)) / ((double) (eight.x - four.x));
			// six = findPointSix(tangentOfEight, one, five);
			Point seven = new Point();

			// 5.1 finding number seven
			seven = getClosestPointOnALine(four, eight, six);

			// 6. construction of the three paths

			// top path
			Path2D.Double topPath = new Path2D.Double();
			topPath.moveTo(two.x, two.y);

		
			topPath.lineTo(five.x, five.y);
			topPath.lineTo(six.x, six.y);
			topPath.curveTo(six.x, six.y -cubicYcontrol, seven.x, seven.y, eight.x, eight.y);
			topPath.lineTo(eight.x, eight.y);
			topPath.lineTo(four.x, four.y);
			topPath.lineTo(two.x, two.y);

			// bottom path
			Path2D.Double bottomPath = new Path2D.Double();
			bottomPath.moveTo(one.x, one.y);
			bottomPath.lineTo(six.x, six.y);
			bottomPath.lineTo(three.x, three.y);
			bottomPath.lineTo(ten.x, ten.y);
			bottomPath.lineTo(one.x, one.y);
			AffineTransform at = AffineTransform.getTranslateInstance(0, 0);

			// creating the shapes
			Shape topShape = topPath.createTransformedShape(at);
			Shape bottomShape = bottomPath.createTransformedShape(at);

			centerCircle = new Point((int) Math.round((five.getX() - four.getX()) / 2),
					(int) Math.round((five.getY() - four.getY()) / 2));
			centerCircle = two;

			
			// the two parts of the string
			Shape[] fullShapeOfString = new Shape[2];
			fullShapeOfString[0] = bottomShape;
			fullShapeOfString[1] = topShape;
			
			reperesnetedLine.setLine(nine, two);
			return fullShapeOfString;			
		}
		
		// option 3
		else if (tangent == 90) {
			int length = (int) Math
					.round(MyPrintCanvasDiagram.length - ((double) MyPrintCanvasDiagram.length / (double) 4));
			double angle = tangent * (Math.PI / ((double) 180));
			// 2.calculation of point 3
			// 2.calculation of point 3
			Point three = new Point(oneX + length, oneY - lengthOfBackWeave);
			Point four = new Point(three.x, axisX);
			Point five = new Point((int) Math.round(four.x - 2 * R), four.y);
			Point six = new Point((int) Math.round(three.x - 2 * R), three.y);
			Point eight = new Point(three.x, (int) Math.round(three.y - 4 * R) );
			
		/*	
			Point three = new Point((int) Math.round(oneX + 2 * R), oneY - lengthOfBackWeave);
			Point six = new Point(one.x, one.y - lengthOfBackWeave);

			Point ten = new Point((int) Math.round(one.x + 2 * R), one.y);
			

			int length = (int) Math
					.round(MyPrintCanvasDiagram.length - ((double) MyPrintCanvasDiagram.length / (double) 4));

			Point eight = new Point(oneX + length, oneY- lengthOfBackWeave);
			Point nine = new Point((int)Math.round(eight.x - 2*R), six.y);
			Point two = new Point(three.x, axisX);
			
			Point four = new Point(eight.x, axisX);	
			Point five = new Point((int) Math.round(four.x - 2 * R), four.y);*/
			// top path
		
			
			
			Path2D.Double topPath = new Path2D.Double();
			
			topPath.moveTo(six.x, six.y);
			topPath.lineTo(one.x, one.y - lengthOfBackWeave);
			topPath.curveTo(one.x, one.y - lengthOfBackWeave -cubicYcontrol, eight.x, eight.y, three.x, three.y);
			topPath.lineTo(four.x, four.y);
			topPath.lineTo(five.x, five.y);	
			topPath.lineTo(six.x, six.y);
			
		/*	
			topPath.moveTo(two.x, two.y);	
			topPath.lineTo(five.x, five.y);
			topPath.lineTo(nine.x, nine.y);
			topPath.lineTo(six.x, six.y);
			//Should be the same case as the regular weaving, a method above
			topPath.curveTo(six.x, six.y -cubicYcontrol,  eight.x, eight.y - 2*R, eight.x, eight.y);
			topPath.lineTo(eight.x, eight.y);
			topPath.lineTo(four.x, four.y);
			topPath.lineTo(two.x, two.y);*/

			// bottom path
			Path2D.Double bottomPath = new Path2D.Double();
			bottomPath.moveTo(one.x, one.y);
			bottomPath.lineTo(one.x, one.y - lengthOfBackWeave);
			bottomPath.lineTo(six.x, six.y);
			bottomPath.lineTo(six.x, six.x + lengthOfBackWeave);
			bottomPath.lineTo(one.x, one.y);
			AffineTransform at = AffineTransform.getTranslateInstance(0, 0);

			// center of circle is center of four and five
			centerCircle = new Point((int) Math.round(five.getX() + (four.getX() - five.getX()) / 2),
					(int) Math.round(five.getY()));
			Point two = centerCircle;
			
			// creating the shapes
			Shape topShape = topPath.createTransformedShape(at);
			Shape bottomShape = bottomPath.createTransformedShape(at);

			centerCircle = new Point((int) Math.round((five.getX() - four.getX()) / 2),
					(int) Math.round((five.getY() - four.getY()) / 2));
			centerCircle = two;
			Point nine = new Point((int) Math.round((one.getX() + six.getX()) / 2), one.y);

			
			// the two parts of the string
			Shape[] fullShapeOfString = new Shape[2];
			fullShapeOfString[0] = bottomShape;
			fullShapeOfString[1] = topShape;
			
			reperesnetedLine.setLine(nine, two);
			return fullShapeOfString;		
		}
		
		// option 2
		else {
			System.out.println("option 2");
		
			one = new Point(oneX +MyPrintCanvasDiagram.length, oneY);
			// changing tangent to a sharp angle
			tangent = 180 - tangent;
			// angle in radians
			double angle = tangent * Math.PI / 180;
			// 2.calculation of point 3, 6, and 9
			
		//	Point three = new Point(oneX + MyPrintCanvasDiagram.length, oneY);
			
			Point three = new Point((int) Math.round(oneX - 2 * R), oneY - lengthOfBackWeave);
			
			Point six = new Point(one.x, one.y - lengthOfBackWeave);

			Point ten = new Point((int) Math.round(one.x - 2 * R), one.y);

			int distanceSixFromEight = (int) Math.round((double) (2 * R) / Math.sin(angle));

			Point eight = new Point(six.x - distanceSixFromEight, six.y);

			Point nine = new Point((int) Math.round((six.getX() + eight.getX()) / 2), six.y);

			Point two = new Point();
			// 3.finding closest intersection from axisX and axisY

			// intersection of axisY2 with the line point of 3 with angle tangent
			int intersectionAxisY = (int) Math.round(Math.tan(angle) * (nine.getX()-axisY2 ) + nine.getY());
			if (intersectionAxisY < axisX) {
				two = new Point(axisY2, intersectionAxisY);
			}

			// intersection of axisX with the line point of 3 with angle tangent
			else {
				int intersectionAxisX = (int) Math.round(nine.getX() - ((axisX - nine.getY()) / Math.tan(angle)));
				two = new Point(intersectionAxisX, axisX);
			}

			// creating the represented line of the strings (the middle line)
			// two-nine
			reperesnetedLine.setLine(nine, two);

			// 4.finding four

			Point[] circleIntersect = CircleLine.getCircleLineIntersectionPoint(two, eight, R);
			Point four = new Point();
			if (circleIntersect[0].x > circleIntersect[1].x) {
				four = circleIntersect[1];
			}

			else {
				four = circleIntersect[0];
			}
			// 5. finding 5
			Point five = findOppositeOfPointInCircle(four, two);
			// changing point six such that the line 6-5 is parallel to 3-4
			double tangentOfEight = ((double) (eight.y - four.y)) / ((double) (eight.x - four.x));
			// six = findPointSix(tangentOfEight, one, five);
			Point seven = new Point();

			// 5.1 finding number seven
			seven = getClosestPointOnALine(four, eight, six);

			// 6. construction of the three paths

			// top path
			Path2D.Double topPath = new Path2D.Double();
			topPath.moveTo(two.x, two.y);

		
			topPath.lineTo(five.x, five.y);
			topPath.lineTo(six.x, six.y);
			topPath.curveTo(six.x, six.y -cubicYcontrol, seven.x, seven.y, eight.x, eight.y);
			topPath.lineTo(eight.x, eight.y);
			topPath.lineTo(four.x, four.y);
			topPath.lineTo(two.x, two.y);

			// bottom path
			Path2D.Double bottomPath = new Path2D.Double();
			bottomPath.moveTo(one.x, one.y);
			bottomPath.lineTo(six.x, six.y);
			bottomPath.lineTo(three.x, three.y);
			bottomPath.lineTo(ten.x, ten.y);
			bottomPath.lineTo(one.x, one.y);
			AffineTransform at = AffineTransform.getTranslateInstance(0, 0);

			// creating the shapes
			Shape topShape = topPath.createTransformedShape(at);
			Shape bottomShape = bottomPath.createTransformedShape(at);

			centerCircle = new Point((int) Math.round((five.getX() - four.getX()) / 2),
					(int) Math.round((five.getY() - four.getY()) / 2));
			centerCircle = two;

			
			// the two parts of the string
			Shape[] fullShapeOfString = new Shape[2];
			fullShapeOfString[0] = bottomShape;
			fullShapeOfString[1] = topShape;
			
			reperesnetedLine.setLine(nine, two);
			return fullShapeOfString;			

		}

	}

	// this works
	public static Point getClosestPointOnSegment(Point ss, Point se, Point p) {
		return getClosestPointOnSegment(ss.x, ss.y, se.x, se.y, p.x, p.y);
	}

	/**
	 * Returns closest point on segment to point
	 * 
	 * @param sx1
	 *            segment x coord 1
	 * @param sy1
	 *            segment y coord 1
	 * @param sx2
	 *            segment x coord 2
	 * @param sy2
	 *            segment y coord 2
	 * @param px
	 *            point x coord
	 * @param py
	 *            point y coord
	 * @return closets point on segment to point
	 */
	public static Point getClosestPointOnSegment(int sx1, int sy1, int sx2, int sy2, int px, int py) {
		double xDelta = sx2 - sx1;
		double yDelta = sy2 - sy1;

		if ((xDelta == 0) && (yDelta == 0)) {
			throw new IllegalArgumentException("Segment start equals segment end");
		}

		double u = ((px - sx1) * xDelta + (py - sy1) * yDelta) / (xDelta * xDelta + yDelta * yDelta);
		final Point closestPoint;
		if (u < 0) {
			closestPoint = new Point(sx1, sy1);
		} else if (u > 1) {
			closestPoint = new Point(sx2, sy2);
		} else {
			closestPoint = new Point((int) Math.round(sx1 + u * xDelta), (int) Math.round(sy1 + u * yDelta));
		}
		return closestPoint;
	}

	public static Point myGetClosestPoint(Point pt1, Point pt2, Point p) {
		double u = ((p.getX() - pt1.getX()) * (pt2.getX() - pt1.getX())
				+ (p.getY() - pt1.getY()) * (pt2.getY() - pt1.getY()))
				/ (sqr(pt2.getX() - pt1.getX()) + sqr(pt2.getY() - pt1.getY()));
		if (u > 1.0)
			return (Point) pt2.clone();
		else if (u <= 0.0)
			return (Point) pt1.clone();
		else
			return new Point((int) Math.round((pt2.getX() * u + pt1.getX() * (1.0 - u) + 0.5)),
					(int) Math.round((pt2.getY() * u + pt1.getY() * (1.0 - u) + 0.5)));
	}

	private static double sqr(double x) {
		return x * x;
	}

	/**
	 * @param pt1
	 *            - point of the line
	 * @param pt2
	 *            - point on the line
	 * @param pt0
	 *            - point outside the line
	 * @return the closest point from pt0 on the line
	 */
	public static Point getClosestPointOnALine(Point pt1, Point pt2, Point pt0) {
		double ratio = Math
				.pow(((sqr(pt1.x - pt0.x) + sqr(pt1.y - pt0.y)) * (sqr(pt2.x - pt1.x) + sqr(pt2.y - pt1.y))
						- sqr((pt2.x - pt1.x) * (pt1.y - pt0.y) - (pt1.x - pt0.x) * (pt2.y - pt1.y))), 0.5)
				/ (sqr(pt2.x - pt1.x) + sqr(pt2.y - pt1.y));

		int xc = (int) Math.round(pt1.x + (pt2.x - pt1.x) * ratio);
		int yc = (int) Math.round(pt1.y + (pt2.y - pt1.y) * ratio);
		Point newP = new Point(xc, yc);
		return newP;
	}

	/**
	 * 
	 * @param firstPointx
	 * @param firstPointy
	 * @param angle
	 * @param g2d
	 * @param img
	 */

	/**
	 * 
	 * @param radius
	 *            - radius of circle
	 * @param borderPoint
	 *            - point on the line that touches the border of the circle =x1
	 * @param outsidePoint
	 *            - the point on the line that is outside the circle=x2
	 * @return
	 */
	public static Point findDownCenterOfCircle(double radius, Point borderPoint, Point outsidePoint) {
		Point res = new Point();
		double dx = borderPoint.x - outsidePoint.x; // x1-x2
		double dy = borderPoint.y - outsidePoint.y; // y1-y2

		double dist = Math.sqrt(dx * dx + dy * dy);

		dx /= dist;
		dy /= dist;
		res.x = (int) (borderPoint.x + radius * dy);
		res.y = (int) (borderPoint.y - radius * dx);
		return res;

	}

	/**
	 * 
	 * @param radius
	 *            - radius of circle
	 * @param borderPoint
	 *            - point on the line that touches the border of the circle =x1
	 * @param outsidePoint
	 *            - the point on the line that is outside the circle=x2
	 * @return
	 */
	public static Point findUpCenterOfCircle(double radius, Point borderPoint, Point outsidePoint) {
		Point res = new Point();
		double dx = borderPoint.x - outsidePoint.x; // x1-x2
		double dy = borderPoint.y - outsidePoint.y; // y1-y2
		double dist = Math.sqrt(dx * dx + dy * dy);

		dx /= dist;
		dy /= dist;
		res.x = (int) (borderPoint.x - radius * dy);
		res.y = (int) (borderPoint.y + radius * dx);
		return res;

	}

	public static Point findOppositeOfPointInCircle(Point firstPoint, Point center) {

		Point res = new Point();
		double vx = center.x - firstPoint.x;
		double vy = center.y - firstPoint.y;

		res.x = (int) (vx + center.x);
		res.y = (int) (vy + center.y);
		return res;
	}

	/**
	 * @param tangent
	 *            - the tangent of line 3-4
	 * @param one
	 *            - point one
	 * @param five
	 *            - point five, only in option #5 it will be point four.
	 * @return point six if the string.
	 */
	public static Point findPointSix(double tangent, Point one, Point five) {
		int sixX = (int) Math.round(((double) (one.y - five.y + tangent * five.x)) / tangent);
		Point six = new Point(sixX, one.y);
		return six;
	}

}
