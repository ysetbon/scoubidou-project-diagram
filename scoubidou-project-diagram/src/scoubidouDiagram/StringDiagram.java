package scoubidouDiagram;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class StringDiagram extends Frame {
	int firstPointx, firstPointy, secondPointx, secondPointy, movingPointx,
			movingPointy;
	Shape string;
	Point centerCircle;
	static int transx, transy;
	String stringCases;
	Color clrOfString;
	Stroke strk = new BasicStroke(3f);
	// radious of the circle shape at the end of each string
	double R = MyPrintCanvasDiagram.length / 6;
	// this is the center of circle after transformation, for finding the line
	// of the string for intersection weaving
	Point centerCircleAfterTrasformation;

	public static void main(String[] args) {
		// first 2 slots are not to be changed. they are for shape's world first
		// point.
		// second 2 slots are point in the rectangular in the real picture.
		// last 2 slots is the distance of the first point (2 lines above) and
		// the real first point on the rectangle.
		int x2 = 400;
		int y2 = 530;
		int x1 = 50;
		int y1 = 50;
		// int x1OnLine=x1+MyPrintCanvasDiagram.length / 2;
		// int y1onLine =y1;
		StringDiagram s = new StringDiagram(MyPrintCanvasDiagram.length / 2, 0,
				x2, y2, "upToDown", x1, y1, Color.GREEN);
		s.setBounds(0, 0, 1000, 1000);
		BufferedImage stringImg = new BufferedImage(500, 500,
				BufferedImage.TYPE_INT_ARGB);
		// drawing the string into a picture
		Graphics2D g2 = stringImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.translate(x1 + 10, y1 + 10);
		g2.setColor(Color.GRAY);
		g2.fill(s.string);
		g2.setColor(Color.BLACK);
		Stroke strk = new BasicStroke(3f);
		g2.setStroke(strk);
		g2.draw(s.string);
		try {
			ImageIO.write(stringImg, "png", new File("c://temp//" + "stringImg"
					+ "_(" + x1 + "," + y1 + ")" + "_(" + x2 + "," + y2 + ")"
					+ "_" + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(strk);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
				RenderingHints.VALUE_ANTIALIAS_ON);
		System.out.println("transform g2 " + g2.getTransform().getTranslateX()
				+ "," + g2.getTransform().getTranslateY());
		g2.translate(transx, transy);
		Shape circle = drawCenteredCircle(centerCircle, R);

		if (stringCases == "upToDown") {
			g2.setColor(clrOfString);
			g2.fill(string);
			g2.setColor(Color.BLACK);
			g2.setStroke(strk);
			g2.draw(string);
			g2.setColor(clrOfString);
			g2.fill(circle);
			g2.setColor(Color.BLACK);
			g2.setStroke(strk);
			g2.draw(circle);
			Point newCenterOfCircle = new Point(0, 0);
			g2.getTransform().transform(centerCircle, newCenterOfCircle);
			// this is for knowing the center of circle after transformation,
			// used then for finding intersections of to strings for weaving
			// (the second point of the string line is the center of the circle)
			centerCircleAfterTrasformation = newCenterOfCircle;

		}

		if (stringCases == "leftToRight") {
			AffineTransform f = new AffineTransform();
			f.rotate(Math.toRadians(90));
			Point newSecondPoint = new Point(0, 0);
			Point oldSecondPoint = new Point(secondPointx, secondPointy);
			f.transform(oldSecondPoint, newSecondPoint);

			System.out.println("new secondPoint " + newSecondPoint.toString());
			this.string = paintString(firstPointx, firstPointy,
					newSecondPoint.x, newSecondPoint.y);
			g2.rotate(Math.toRadians(-90));
			g2.setColor(clrOfString);
			g2.fill(string);
			g2.setColor(Color.BLACK);
			g2.setStroke(strk);
			g2.draw(string);
			circle = drawCenteredCircle(centerCircle, R);
			// this is for knowing the center of circle after transformation,
			// used then for finding intersections of to strings for weaving
			// (the second point of the string line is the center of the circle)
			Point newCenterOfCircle = new Point(0, 0);
			g2.getTransform().transform(centerCircle, newCenterOfCircle);
			centerCircleAfterTrasformation = newCenterOfCircle;
			System.out.println("centerCircleAfterTrasformation:"
					+ centerCircleAfterTrasformation.x + ","
					+ centerCircleAfterTrasformation.y);
			g2.setColor(clrOfString);
			g2.fill(circle);
			g2.setColor(Color.BLACK);
			g2.setStroke(strk);
			g2.draw(circle);
			g2.rotate(Math.toRadians(90));

		}

		if (stringCases == "downToUp") {
			AffineTransform f = new AffineTransform();
			f.rotate(Math.toRadians(180));
			Point newSecondPoint = new Point(0, 0);
			Point oldSecondPoint = new Point(secondPointx, secondPointy);
			f.transform(oldSecondPoint, newSecondPoint);
			this.string = paintString(firstPointx, firstPointy,
					newSecondPoint.x, newSecondPoint.y);
			g2.rotate(Math.toRadians(-180));
			g2.setColor(clrOfString);
			g2.fill(string);
			g2.setColor(Color.BLACK);
			g2.setStroke(strk);
			g2.draw(string);
			circle = drawCenteredCircle(centerCircle, R);
			// this is for knowing the center of circle after transformation,
			// used then for finding intersections of to strings for weaving
			// (the second point of the string line is the center of the circle)
			Point newCenterOfCircle = new Point(0, 0);
			g2.getTransform().transform(centerCircle, newCenterOfCircle);
			centerCircleAfterTrasformation = newCenterOfCircle;
			System.out.println("centerCircleAfterTrasformation:"
					+ centerCircleAfterTrasformation.x + ","
					+ centerCircleAfterTrasformation.y);
			g2.setColor(clrOfString);

			g2.fill(circle);
			g2.setColor(Color.BLACK);
			g2.setStroke(strk);
			g2.draw(circle);
			g2.rotate(Math.toRadians(180));

		}

		if (stringCases == "rightToLeft") {
			AffineTransform f = new AffineTransform();
			f.rotate(Math.toRadians(-90));
			Point newSecondPoint = new Point(0, 0);
			Point oldSecondPoint = new Point(secondPointx, secondPointy);
			f.transform(oldSecondPoint, newSecondPoint);
			System.out.println("new secondPoint " + newSecondPoint.toString());
			this.string = paintString(firstPointx, firstPointy,
					newSecondPoint.x, newSecondPoint.y);
			g2.rotate(Math.toRadians(90));
			g2.setColor(clrOfString);
			g2.fill(string);
			g2.setColor(Color.BLACK);
			g2.setStroke(strk);
			g2.draw(string);
			circle = drawCenteredCircle(centerCircle, R);
			// this is for knowing the center of circle after transformation,
			// used then for finding intersections of to strings for weaving
			// (the second point of the string line is the center of the circle)
			Point newCenterOfCircle = new Point(0, 0);
			g2.getTransform().transform(centerCircle, newCenterOfCircle);
			centerCircleAfterTrasformation = newCenterOfCircle;
			System.out.println("centerCircleAfterTrasformation:"
					+ centerCircleAfterTrasformation.x + ","
					+ centerCircleAfterTrasformation.y);
			g2.setColor(clrOfString);
			g2.fill(circle);
			g2.setColor(Color.BLACK);
			g2.setStroke(strk);
			g2.draw(circle);
			g2.rotate(Math.toRadians(-90));

		}
		g2.translate(-transx, -transy);

	}

	public StringDiagram(int firstPointx, int firstPointy,
			int secondPointxAfter, int secondPointyAfter, String stringCases,
			int transx, int transy, Color clrOfString) {
		this.firstPointx = firstPointx;
		this.firstPointy = firstPointy;
		// getting to the shape world
		this.secondPointx = secondPointxAfter - transx;
		this.secondPointy = secondPointyAfter - transy;
		this.transx = transx;
		this.transy = transy;
		this.stringCases = stringCases;
		this.string = paintString(firstPointx, firstPointy, secondPointx,
				secondPointy);
		this.clrOfString = clrOfString;

	}

	/**
	 * 
	 * @param firstPointx
	 *            -
	 * @param firstPointy
	 * @param secondPointx
	 * @param secondPointy
	 * @return
	 */
	public Shape paintString(int oneX, int oneY, int twoX, int twoY) {
		Point one = new Point(oneX, oneY);
		Point two = new Point(twoX, twoY);
		int length = MyPrintCanvasDiagram.length - MyPrintCanvasDiagram.length
				/ 4;
		// option 1 and 2
		if (twoX > oneX) {
			// 2.calculation of point 3
			Point three = new Point(oneX + length, oneY);
			// 3.finding four
			Point[] circleIntersect = CircleLine
					.getCircleLineIntersectionPoint(two, three, R);
			Point four = new Point();
			if (circleIntersect[0].x < circleIntersect[1].x) {
				four = circleIntersect[1];
			}

			else {
				four = circleIntersect[0];
			}
			// 4+5. finding 5 and 6
			Point five = findOppositeOfPointInCircle(four, two);
			double tangentOfThree = ((double)(three.y-four.y))/((double)(three.x-four.x));
			Point six = findPointSix(tangentOfThree, one, five);
			// option 1, will skip option 2 to later
			//6. finding 7 and eight
			Point seven = new Point(six.x,(int) (six.y-4*R));
			Point eight = getClosestPointOnALine(four,three,seven);
			Path2D.Double path = new Path2D.Double();
			path.moveTo(one.x,one.y);
			path.curveTo(one.x, one.y, eight.x, eight.y, three.x, three.y);
			path.lineTo(four.x, four.y);
			path.lineTo(five.x, five.y);
			path.lineTo(six.x, six.y);
			path.lineTo(one.x, one.y);
			AffineTransform at = AffineTransform.getTranslateInstance(0, 0);

			Shape path2 = path.createTransformedShape(at);
			System.out.println((four.getY()-three.getY())/(four.getX()-three.getX()));
			return path2;


		}
		return null;

		// ///////////////////////////////////////////////////////////////////
	/*	int x = firstPointx - MyPrintCanvasDiagram.length / 2, y = firstPointy;
		System.out.println("paintString: x" + x + ",y" + y);
		Path2D.Double path = new Path2D.Double();
		movingPointx = x;
		movingPointy = y;
		System.out.println("in paintString movingPoint x=" + movingPointx
				+ " ,y=" + movingPointy);

		Point one = new Point(firstPointx, firstPointy);
		int pointInbetweenTwoAndOnex = secondPointx;
		int pointInbetweenTwoAndOney = secondPointy;
		int downPointInbetweenTwoAndOnex = pointInbetweenTwoAndOnex;
		int downPointInbetweenTwoAndOney = pointInbetweenTwoAndOney
				+ MyPrintCanvasDiagram.length / 4;
		Point inbtwnTwoOne = new Point(pointInbetweenTwoAndOnex,
				pointInbetweenTwoAndOney);
		Point downInbtwnTwoOne = new Point(downPointInbetweenTwoAndOnex,
				downPointInbetweenTwoAndOney);
		Point two = getClosestPointOnALine(one, inbtwnTwoOne, downInbtwnTwoOne);
		int x3 = firstPointx + MyPrintCanvasDiagram.length / 2
				- MyPrintCanvasDiagram.length / 8;
		int y3 = firstPointy;
		int x4 = two.x + MyPrintCanvasDiagram.length / 2
				- MyPrintCanvasDiagram.length / 8;
		int y4 = two.y;
		if (firstPointx > secondPointx) {
			Point three = new Point(x3, y3);
			Point four = new Point(x4, y4);
			Point five = getClosestPointOnALine(three, four, two);

			Point centerFiveTwo = findUpCenterOfCircle(
					MyPrintCanvasDiagram.length / 6, five, four);
			// getting the opposite point of five in the circle
			Point oppositePointOfFive = findOppositeOfPointInCircle(five,
					centerFiveTwo);

			// secondBize needs to be right
			Point secondBizePoint = new Point(three.x
					+ MyPrintCanvasDiagram.length / 2, three.y
					- MyPrintCanvasDiagram.length / 2);

			Point secondOnLineBizePoint = getClosestPointOnALine(four, three,
					secondBizePoint);
			// chainging the firstPoint to a better point

			Point changeFirstPoint = findHorizontalPoint(five, three,
					oppositePointOfFive);

			// here it'll enter only the first if

			path.moveTo(x, y);
			path.lineTo(changeFirstPoint.x, firstPointy);
			path.lineTo(oppositePointOfFive.x, oppositePointOfFive.y);
			int centerCirclex = centerFiveTwo.x;
			int centerCircley = centerFiveTwo.y;

			// path.c

			// going to point four
			path.lineTo(five.x, five.y);
			path.lineTo(three.x, three.y);
			// back to point zero
			path.moveTo(x, y);
			// doing the curve
			path.curveTo(x, y, secondOnLineBizePoint.x,
					secondOnLineBizePoint.y, three.x, three.y);
			this.centerCircle = new Point(centerCirclex, centerCircley);

		}

		if (firstPointx == secondPointx) {
			Point three = new Point(x3, y3);
			Point four = new Point(x4, y4);
			Point five = getClosestPointOnALine(three, four, two);

			Point centerFiveTwo = new Point(five.x
					- MyPrintCanvasDiagram.length / 6, five.y);
			Point oppositePointOfFive = new Point(five.x - 2
					* (MyPrintCanvasDiagram.length / 6), five.y);
			// secondBize needs to be right
			Point secondBizePoint = new Point(three.x
					+ MyPrintCanvasDiagram.length / 2, three.y
					- MyPrintCanvasDiagram.length / 2);

			Point secondOnLineBizePoint = getClosestPointOnALine(four, three,
					secondBizePoint);
			// chainging the firstPoint to a better point
			Point changeFirstPoint = findHorizontalPoint(five, three,
					oppositePointOfFive);

			path.moveTo(x, y);
			path.lineTo(changeFirstPoint.x, firstPointy);
			path.lineTo(oppositePointOfFive.x, oppositePointOfFive.y);
			int centerCirclex = centerFiveTwo.x;
			int centerCircley = centerFiveTwo.y;

			// going to point four
			path.lineTo(five.x, five.y);
			path.lineTo(three.x, three.y);
			// back to point zero
			path.moveTo(x, y);
			// doing the curve
			path.curveTo(x, y, secondOnLineBizePoint.x,
					secondOnLineBizePoint.y, three.x, three.y);
			this.centerCircle = new Point(centerCirclex, centerCircley);

		}

		if (firstPointx < secondPointx) {
			Point three = new Point(x3, y3);
			Point four = new Point(x4, y4);
			Point five = getClosestPointOnALine(three, four, two);
			// secondBize needs to be in left up
			Point secondBizePoint = new Point(firstPointx
					- MyPrintCanvasDiagram.length / 2, firstPointy
					- MyPrintCanvasDiagram.length / 2);
			Point secondOnLineBizePoint = getClosestPointOnALine(four, three,
					secondBizePoint);

			Point centerFiveTwo = findDownCenterOfCircle(
					MyPrintCanvasDiagram.length / 6, five, four);
			Point oppositePointOfFive = findOppositeOfPointInCircle(five,
					centerFiveTwo);
			Point changeFirstPoint = findHorizontalPoint(five, three,
					oppositePointOfFive);
			if (changeFirstPoint.x >= x) {

				path.moveTo(x, y);
				path.lineTo(changeFirstPoint.x, firstPointy);
				path.lineTo(oppositePointOfFive.x, oppositePointOfFive.y);
				int centerCirclex = centerFiveTwo.x;
				int centerCircley = centerFiveTwo.y;

				// path.c

				// going to point four
				path.lineTo(five.x, five.y);
				path.lineTo(three.x, three.y);
				// back to point zero
				path.moveTo(x, y);
				// doing the curve
				path.curveTo(x, y, secondOnLineBizePoint.x,
						secondOnLineBizePoint.y, three.x, three.y);
				this.centerCircle = new Point(centerCirclex, centerCircley);

			}
			// here it can enter to both "if"s
			if (changeFirstPoint.x < x) {

				changeFirstPoint = findVerticalPoint(five, three,
						oppositePointOfFive);
				path.moveTo(x, y);
				// curving
				Point threeOnTheLine = getClosestPointOnSegment(
						oppositePointOfFive, changeFirstPoint, three);
				path.curveTo(x, y, changeFirstPoint.x, changeFirstPoint.y,
						threeOnTheLine.x, threeOnTheLine.y);
				path.lineTo(oppositePointOfFive.x, oppositePointOfFive.y);
				int centerCirclex = centerFiveTwo.x;
				int centerCircley = centerFiveTwo.y;

				// path.c

				// going to point four
				path.lineTo(five.x, five.y);
				path.lineTo(three.x, three.y);
				// back to point zero
				path.moveTo(x, y);
				// doing the curve
				path.curveTo(x, y, secondOnLineBizePoint.x,
						secondOnLineBizePoint.y, three.x, three.y);
				this.centerCircle = new Point(centerCirclex, centerCircley);

			}
		}
*/
		
	}

	public Shape drawCenteredCircle(Point centerCircle, double R) {
		centerCircle = this.centerCircle;
		double centerCirclex = centerCircle.x;
		double centerCircley = centerCircle.y;

		R = this.R;
		Path2D.Double path = new Path2D.Double();

		path.moveTo(centerCirclex, centerCircley - R);
		// path.c
		double kappa = 0.55228;

		path.curveTo(centerCirclex + R * kappa, centerCircley - R,
				centerCirclex + R, centerCircley - R * kappa,
				centerCirclex + R, centerCircley); // curve to A', B', B
		path.curveTo(centerCirclex + R, centerCircley + R * kappa,
				centerCirclex + R * kappa, centerCircley + R, centerCirclex,
				centerCircley + R);
		path.curveTo(centerCirclex - R * kappa, centerCircley + R,
				centerCirclex - R, centerCircley + R * kappa,
				centerCirclex - R, centerCircley);
		path.curveTo(centerCirclex - R, centerCircley - R * kappa,
				centerCirclex - R * kappa, centerCircley - R, centerCirclex,
				centerCircley - R);

		AffineTransform at = AffineTransform.getTranslateInstance(0, 0);

		Shape path2 = path.createTransformedShape(at);

		return path2;

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
	public static Point getClosestPointOnSegment(int sx1, int sy1, int sx2,
			int sy2, int px, int py) {
		double xDelta = sx2 - sx1;
		double yDelta = sy2 - sy1;

		if ((xDelta == 0) && (yDelta == 0)) {
			throw new IllegalArgumentException(
					"Segment start equals segment end");
		}

		double u = ((px - sx1) * xDelta + (py - sy1) * yDelta)
				/ (xDelta * xDelta + yDelta * yDelta);
		final Point closestPoint;
		if (u < 0) {
			closestPoint = new Point(sx1, sy1);
		} else if (u > 1) {
			closestPoint = new Point(sx2, sy2);
		} else {
			closestPoint = new Point((int) Math.round(sx1 + u * xDelta),
					(int) Math.round(sy1 + u * yDelta));
		}
		return closestPoint;
	}

	public static Point myGetClosestPoint(Point pt1, Point pt2, Point p) {
		double u = ((p.x - pt1.x) * (pt2.x - pt1.x) + (p.y - pt1.y)
				* (pt2.y - pt1.y))
				/ (sqr(pt2.x - pt1.x) + sqr(pt2.y - pt1.y));
		if (u > 1.0)
			return (Point) pt2.clone();
		else if (u <= 0.0)
			return (Point) pt1.clone();
		else
			return new Point((int) (pt2.x * u + pt1.x * (1.0 - u) + 0.5),
					(int) (pt2.y * u + pt1.y * (1.0 - u) + 0.5));
	}

	private static double sqr(double x) {
		return x * x;
	}
/**
 * @param pt1 - point of the line
 * @param pt2 - point on the line
 * @param pt0 - point outside the line
 * @return the closest point from pt0 on the line
 */
	public static Point getClosestPointOnALine(Point pt1, Point pt2, Point pt0) {
		double ratio = Math
				.pow(((sqr(pt1.x - pt0.x) + sqr(pt1.y - pt0.y))
						* (sqr(pt2.x - pt1.x) + sqr(pt2.y - pt1.y)) - sqr((pt2.x - pt1.x)
						* (pt1.y - pt0.y) - (pt1.x - pt0.x) * (pt2.y - pt1.y))),
						0.5)
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
	public static Point findDownCenterOfCircle(double radius,
			Point borderPoint, Point outsidePoint) {
		Point res = new Point();
		double dx = borderPoint.x - outsidePoint.x; // x1-x2
		double dy = borderPoint.y - outsidePoint.y; // y1-y2
		System.out.println(" 0 dx dy" + dx + "," + dy);

		double dist = Math.sqrt(dx * dx + dy * dy);

		dx /= dist;
		dy /= dist;
		System.out.println("dx dy" + dx + "," + dy);
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
	public static Point findUpCenterOfCircle(double radius, Point borderPoint,
			Point outsidePoint) {
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

	public static Point findOppositeOfPointInCircle(Point firstPoint,
			Point center) {

		Point res = new Point();
		double vx = center.x - firstPoint.x;
		double vy = center.y - firstPoint.y;

		res.x = (int) (vx + center.x);
		res.y = (int) (vy + center.y);
		return res;
	}

	/**
	 * 
	 * @param pointAtCircleAndAtGivenLine
	 *            - point B in the digram1 picture
	 * @param inTheGivenLineOutCircle
	 *            - point A in the dagram1 picture
	 * @param PointAtCircle
	 *            - point C in the dagram1 picture
	 * @return
	 */
	public Point findHorizontalPoint(Point pointAtCircleAndAtGivenLine,
			Point inTheGivenLineOutCircle, Point PointAtCircle) {

		Point res = new Point();

		if (inTheGivenLineOutCircle.y != pointAtCircleAndAtGivenLine.y) {
			// Dy is the firstPoint.y
			double resX = PointAtCircle.x
					+ ((firstPointy - PointAtCircle.y) * (inTheGivenLineOutCircle.x - pointAtCircleAndAtGivenLine.x))
					/ (inTheGivenLineOutCircle.y - pointAtCircleAndAtGivenLine.y);
			res.y = firstPointy;
			res.x = (int) resX;
			return res;
		} else {
			res.y = firstPointy;
			res.x = firstPointx;
			System.out.println("keta kriti");
			return res;

		}

	}

	/**
	 * 
	 * @param pointAtCircleAndAtGivenLine
	 *            - point B in the digram1 picture
	 * @param inTheGivenLineOutCircle
	 *            - point A in the dagram1 picture
	 * @param PointAtCircle
	 *            - point C in the dagram1 picture
	 * @return
	 */
	public Point findVerticalPoint(Point pointAtCircleAndAtGivenLine,
			Point inTheGivenLineOutCircle, Point PointAtCircle) {
		Point res = new Point();
		// intersection of vertical line from firstPoint-MyP.length/2 with the
		// line from C
		double resY = PointAtCircle.y
				+ ((firstPointx - MyPrintCanvasDiagram.length / 2 - PointAtCircle.x) * (inTheGivenLineOutCircle.y - pointAtCircleAndAtGivenLine.y))
				/ (inTheGivenLineOutCircle.x - pointAtCircleAndAtGivenLine.x);
		res.y = (int) resY;
		res.x = firstPointx - MyPrintCanvasDiagram.length / 2;
		return res;

	}

	/**
	 * gets the center of circle 
	 * @return
	 */
	public Point getCenterCircleAfterTrasformation() {
		return this.centerCircleAfterTrasformation;
	}
/*	*//**
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param r
	 *//*
	public void drawCenteredCircle(Graphics2D g, int x, int y, int r) {
		x = x - (r / 2);
		y = y - (r / 2);
		g.fillOval(x, y, r, r);
	}*/
	/**
	 * 
	 * @param tangent- the tangent of line 3-4 
	 * @param one - point one
	 * @param five - point five, only in option #5 it will be point four.
	 * @return point six if the string.
	 */
	public Point findPointSix(double tangent, Point one, Point five){
		int sixX= (int) Math.round(((double)(one.y-five.y+tangent*five.x))/tangent);
		 Point six = new Point (sixX,one.y);
		 return six;
	}
}
