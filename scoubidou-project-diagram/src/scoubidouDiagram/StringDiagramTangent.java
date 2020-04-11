package scoubidouDiagram;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This is a class that draws the string given the average curve of all strings
 * 
 * @author User
 *
 */
public class StringDiagramTangent extends Frame {
	Shape string;
	Point centerCircle;
	public int transx, transy;
	String stringCases;
	Color clrOfString;
	Stroke strk = new BasicStroke(MyPrintCanvasDiagram.recWidth);
	// Radios of the circle shape at the end of each string
	double R = Math.round(MyPrintCanvasDiagram.length / (double) 6);
	// this is the center of circle after transformation, for finding the line
	// of the string for intersection weaving
	Point centerCircleAfterTrasformation;
	// the average tangent
	double tangent;
	// axis of big rectangle, far right corner is axisY and far down corner is
	// axisX. The center of the big rectangle is 0,0
	int axisX;
	int axisY;
	int axisX2;
	int axisY2;
	static int index = 0;
	Rectangle stitchRec;

	// represented (middle) line of the string BEFORE transformation 
	Line2D reperesnetedLine = new Line2D.Double();
	
	Line2D representedLineAfterTransformation = new Line2D.Double();
	
	public static void main(String[] args) {
		// first 2 slots are not to be changed. they are for shape's world first
		// point.
		// second 2 slots are point in the rectangular in the real picture.
		// last 2 slots is the distance of the first point (2 lines above) and
		// the real first point on the rectangle.
		int i = 0;
		// String direction = "upToDown";
		// String direction = "rightToLeft";
		// String direction = "downToUp";
		 String direction = "leftToRight";
		if (direction == "leftToRight") {

			for (double angle = -87; angle < 87; angle += 2) {
				int transx = 30;
				int transy = 250;
				// tangent is a degree angle

				// stitch's rectangle
				Rectangle stitchRec = new Rectangle(30, 20, 500, 400);

				StringDiagramTangent s = new StringDiagramTangent(direction, transx, transy, Color.GREEN, angle,
						stitchRec);
				s.setBounds(0, 0, 700, 700);
				BufferedImage stringImg = new BufferedImage(700, 700, BufferedImage.TYPE_INT_ARGB);
				// drawing the string into a picture

				Graphics2D g2 = stringImg.createGraphics();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
						RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(Color.GRAY);
				g2.setColor(Color.BLACK);
				g2.draw(stitchRec);
				s.paint(g2);

				try {
					ImageIO.write(stringImg, "png", new File("c://temp//stringpaints//-90 to 90//" + i + "," + angle
							+ ",stringImg" + "_(" + transx + "," + transy + ")" + s.axisX + "_" + s.axisY + ".png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			}
		}
		if (direction == "upToDown") {
			for (double angle = 1; angle < 180; angle += 2) {
				// translating the string to the point (x1,y1)
				int transx = 150;
				int transy = 20;
				// tangent is a degree angle

				// stitch's rectangle
				Rectangle stitchRec = new Rectangle(30, 20, 400, 300);

				StringDiagramTangent s = new StringDiagramTangent(direction, transx, transy, Color.GREEN, angle,
						stitchRec);
				s.setBounds(0, 0, 700, 700);
				BufferedImage stringImg = new BufferedImage(700, 700, BufferedImage.TYPE_INT_ARGB);
				// drawing the string into a picture

				Graphics2D g2 = stringImg.createGraphics();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
						RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(Color.GRAY);
				g2.setColor(Color.BLACK);
				s.paint(g2);
				g2.setStroke(s.strk);
				g2.draw(stitchRec);
				// g2.draw(s.string);
				try {
					ImageIO.write(stringImg, "png", new File("c://temp//stringpaints//0 to 180//" + i + "," + angle
							+ ",stringImg" + "_(" + transx + "," + transy + ")" + s.axisX + "_" + s.axisY + ".png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			}
		}

		if (direction == "downToUp") {
			for (double angle = -179; angle < 0; angle += 2) {
				// translating the string to the point (x1,y1)
				int transx = 200;
				int transy = 320;
				// tangent is a degree angle

				// stitch's rectangle
				Rectangle stitchRec = new Rectangle(30, 20, 400, 300);

				StringDiagramTangent s = new StringDiagramTangent(direction, transx, transy, Color.GREEN, angle,
						stitchRec);
				s.setBounds(0, 0, 700, 700);
				BufferedImage stringImg = new BufferedImage(700, 700, BufferedImage.TYPE_INT_ARGB);
				// drawing the string into a picture

				Graphics2D g2 = stringImg.createGraphics();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
						RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(Color.GRAY);
				g2.setColor(Color.BLACK);
				g2.setStroke(s.strk);
				g2.draw(stitchRec);
				s.paint(g2);

				try {
					ImageIO.write(stringImg, "png", new File("c://temp//stringpaints//-180 to 0//" + i + "," + angle
							+ ",stringImg" + "_(" + transx + "," + transy + ")" + s.axisX + "_" + s.axisY + ".png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			}
		}
		if (direction == "rightToLeft") {

			for (double angle = 92; angle < 270; angle += 2) {
				int transx = 530;
				int transy = 200;
				// tangent is a degree angle

				// stitch's rectangle
				Rectangle stitchRec = new Rectangle(30, 20, 500, 400);

				StringDiagramTangent s = new StringDiagramTangent(direction, transx, transy, Color.GREEN, angle,
						stitchRec);
				s.setBounds(0, 0, 700, 700);
				BufferedImage stringImg = new BufferedImage(700, 700, BufferedImage.TYPE_INT_ARGB);
				// drawing the string into a picture

				Graphics2D g2 = stringImg.createGraphics();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
						RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(Color.GRAY);
				g2.setColor(Color.BLACK);
				g2.draw(stitchRec);
				s.paint(g2);

				try {
					ImageIO.write(stringImg, "png", new File("c://temp//stringpaints//90 to -90//" + i + "," + angle
							+ ",stringImg" + "_(" + transx + "," + transy + ")" + s.axisX + "_" + s.axisY + ".png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i++;
			}
		}

	}

	/**
	 * 
	 * @param stringCases
	 *            - either up,down,left,right
	 * @param transx
	 *            - x exponent of first point
	 * @param transy
	 *            - y exponent of first point
	 * @param clrOfString
	 *            - color of string
	 * @param tangent
	 *            - the angle of the string
	 * @param stitchRec
	 *            - rectangle of the stitch
	 */
	public StringDiagramTangent(String stringCases, int transx, int transy, Color clrOfString, double tangent,
			Rectangle stitchRec) {
		// getting to the shape world
		this.transx = transx;
		this.transy = transy;
		this.stringCases = stringCases;
		this.tangent = tangent;
		this.clrOfString = clrOfString;
		this.stitchRec = stitchRec;
		this.string = paintString(0, 0, tangent, stitchRec);
	}
	
	/**
	 * Create StringDiagramTangent with no data
	 */
	public StringDiagramTangent(){
		
	}

	/**
	 * points out the string
	 */
	public void paint(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(strk);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
				RenderingHints.VALUE_ANTIALIAS_ON);

		// Shape circle = drawCenteredCircle(centerCircle, R);

		if (stringCases == "upToDown") {
			// distance between axisX and point one, this is to be set
			// stitchRecReversed's origin X point
			int distanceOneToaxisY2 = transx - stitchRec.x;
			// axises are rotated 90 degrees

			Rectangle stitchRecReversed = new Rectangle(-distanceOneToaxisY2, 0, stitchRec.width, stitchRec.height);


			string = paintString(0, 0, tangent, stitchRecReversed);
			g2.translate(transx, transy);
			// transelating back to origin point (transx,transy)
			g2.setColor(clrOfString);
			g2.fill(string);
			g2.setColor(Color.BLACK);
			g2.setStroke(strk);
			g2.draw(string);
			// drawing the circle
			g2.setColor(clrOfString);
			g2.fill(drawCenteredCircle(centerCircle, R));
			g2.setColor(Color.BLACK);
			g2.setStroke(strk);
			g2.draw(drawCenteredCircle(centerCircle, R));
			
			representedLineAfterTransformation = reperesnetedLine;
		  //  g2.setColor(Color.BLACK);
          //  g2.setStroke(strk);
		//	g2.draw(representedLineAfterTransformation);
			
			g2.translate(-transx, -transy);
			
		
		}

		if (stringCases == "leftToRight") {
			// distance between axisX and point one, this is to be set
			// stitchRecReversed's origin X point
			int distanceOneToaxisX = stitchRec.y + stitchRec.height - transy;
			// axises are rotated 90 degrees
			g2.rotate(Math.toRadians(90), stitchRec.getX() + stitchRec.width / 2,
					stitchRec.getY() + stitchRec.height / 2);
			Point pointOneAfterTranspose = new Point();
			
			// getting the new point of rotation (transx', transy')
			g2.getTransform().transform(new Point(transx, transy), pointOneAfterTranspose);
			
		
        
            //rotating back
			g2.rotate(Math.toRadians(-90), stitchRec.getX() + stitchRec.width / 2,
					stitchRec.getY() + stitchRec.height / 2);

			
			Rectangle stitchRecReversed = new Rectangle(-distanceOneToaxisX, 0, stitchRec.height, stitchRec.width);


			string = paintString(0, 0, tangent + 90, stitchRecReversed);


			g2.translate(transx, transy);

			g2.rotate(Math.toRadians(-90), 0, 0);

			g2.setColor(clrOfString);
			g2.fill(string);
			g2.setColor(Color.BLACK);
			g2.setStroke(strk);
			g2.draw(string);
			// drawing the circle

			g2.setColor(clrOfString);
			g2.fill(drawCenteredCircle(centerCircle, R));
			g2.setColor(Color.BLACK);
			g2.setStroke(strk);
			g2.draw(drawCenteredCircle(centerCircle, R));
			//getting transformation of the points of representedLineAfterTransformation
			Point firstPointforRepAfter = new Point();
			Point secondPointforRepAfter = new Point();
			g2.getTransform().transform(reperesnetedLine.getP1(),firstPointforRepAfter);
			g2.getTransform().transform(reperesnetedLine.getP2(),secondPointforRepAfter);
            representedLineAfterTransformation.setLine(firstPointforRepAfter,secondPointforRepAfter);
			
			g2.rotate(Math.toRadians(90), 0, 0);
			g2.translate(-transx, -transy);
			
		   	  //draw representedLineAfterTransformation
				 //   g2.setColor(Color.BLACK);
		         //   g2.setStroke(strk);
				//	g2.draw(representedLineAfterTransformation);
		

		}

		if (stringCases == "downToUp") {
			// distance between axisX and point one, this is to be set
			// stitchRecReversed's origin X point
			int distanceOneToaxisY = stitchRec.x + stitchRec.width - transx;
			// axises are rotated 90 degrees

			g2.rotate(Math.toRadians(180), stitchRec.getX() + stitchRec.width / 2,
					stitchRec.getY() + stitchRec.height / 2);
			Point pointOneAfterTranspose = new Point();
			// getting the new point of rotation (transx', transy')
			g2.getTransform().transform(new Point(transx, transy), pointOneAfterTranspose);
			
       
			//rotating back
			g2.rotate(Math.toRadians(-180), stitchRec.getX() + stitchRec.width / 2,
					stitchRec.getY() + stitchRec.height / 2);

			Rectangle stitchRecReversed = new Rectangle(-distanceOneToaxisY, 0, stitchRec.width, stitchRec.height);

			string = paintString(0, 0, tangent + 180, stitchRecReversed);

			g2.translate(transx, transy);

			g2.rotate(Math.toRadians(180), 0, 0);

			g2.setColor(clrOfString);
			g2.fill(string);
			g2.setColor(Color.BLACK);
			g2.setStroke(strk);
			g2.draw(string);
			// drawing the circle

			g2.setColor(clrOfString);
			g2.fill(drawCenteredCircle(centerCircle, R));
			g2.setColor(Color.BLACK);
			g2.setStroke(strk);
			g2.draw(drawCenteredCircle(centerCircle, R));
			
			//getting transformation of the points of representedLineAfterTransformation
			Point firstPointforRepAfter = new Point();
			Point secondPointforRepAfter = new Point();
			g2.getTransform().transform(reperesnetedLine.getP1(),firstPointforRepAfter);
			g2.getTransform().transform(reperesnetedLine.getP2(),secondPointforRepAfter);
            representedLineAfterTransformation.setLine(firstPointforRepAfter,secondPointforRepAfter);
      
			g2.rotate(Math.toRadians(-180), 0, 0);
			g2.translate(-transx, -transy);
			
			//draw representedLineAfterTransformation
		  //  g2.setColor(Color.BLACK);
           // g2.setStroke(strk);
		//	g2.draw(representedLineAfterTransformation);
			

		}

		if (stringCases == "rightToLeft") {
			// distance between axisX and point one, this is to be set
			// stitchRecReversed's origin X point
			int distanceOneToaxisX2 = -stitchRec.y + transy;
			// axises are rotated 90 degrees

			g2.rotate(Math.toRadians(-90), stitchRec.getX() + stitchRec.width / 2,
					stitchRec.getY() + stitchRec.height / 2);
			Point pointOneAfterTranspose = new Point();
			// getting the new point of rotation (transx', transy')
			g2.getTransform().transform(new Point(transx, transy), pointOneAfterTranspose);
			
			
         
			
			//rotating back
			g2.rotate(Math.toRadians(90), stitchRec.getX() + stitchRec.width / 2,
					stitchRec.getY() + stitchRec.height / 2);
			
			Rectangle stitchRecReversed = new Rectangle(-distanceOneToaxisX2, 0, stitchRec.height, stitchRec.width);

			string = paintString(0, 0, tangent - 90, stitchRecReversed);

			g2.translate(transx, transy);

			g2.rotate(Math.toRadians(90), 0, 0);

			g2.setColor(clrOfString);
			g2.fill(string);
			g2.setColor(Color.BLACK);
			g2.setStroke(strk);
			g2.draw(string);
			// drawing the circle

			g2.setColor(clrOfString);
			g2.fill(drawCenteredCircle(centerCircle, R));
			g2.setColor(Color.BLACK);
			g2.setStroke(strk);
			g2.draw(drawCenteredCircle(centerCircle, R));
			
			//getting transformation of the points of representedLineAfterTransformation
			Point firstPointforRepAfter = new Point();
			Point secondPointforRepAfter = new Point();
			g2.getTransform().transform(reperesnetedLine.getP1(),firstPointforRepAfter);
			g2.getTransform().transform(reperesnetedLine.getP2(),secondPointforRepAfter);
			System.out.println("firstPointforRepAfter: "+firstPointforRepAfter.x);
            representedLineAfterTransformation.setLine(firstPointforRepAfter,secondPointforRepAfter);
            
			g2.rotate(Math.toRadians(-90), 0, 0);
			g2.translate(-transx, -transy);
			
			//draw representedLineAfterTransformation
		//    g2.setColor(Color.BLACK);
         //   g2.setStroke(strk);
		//	g2.draw(representedLineAfterTransformation);

		}
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
	public Shape paintString(int oneX, int oneY, double tangent, Rectangle stitchRec) {
		// point one is (0,0)
		Point one = new Point(oneX, oneY);
		int length = (int) Math
				.round(MyPrintCanvasDiagram.length - ((double) MyPrintCanvasDiagram.length / (double) 4));
		// gets the rectangle stitch already translated to the point of view of
		// the string (X2 accodinate is 0)
		axisX = (int) Math.round((stitchRec.getY() + stitchRec.getHeight())) + MyPrintCanvasDiagram.length / 3;
		// that's because stitchRec.getY()=0
		axisX2 = 0;
		axisY = (int) Math.round((stitchRec.getX() + stitchRec.getWidth())) + MyPrintCanvasDiagram.length / 3;
		axisY2 = (int) Math.round(stitchRec.getX()) - MyPrintCanvasDiagram.length / 3;

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
				path.moveTo(one.x, one.y);
				path.curveTo(one.x, one.y, eight.x, eight.y, three.x, three.y);
				path.lineTo(four.x, four.y);
				path.lineTo(five.x, five.y);
				path.lineTo(six.x, six.y);
				path.lineTo(one.x, one.y);
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
				path.moveTo(one.x, one.y);
				path.curveTo(one.x, one.y, eight.x, eight.y, three.x, three.y);
				path.lineTo(four.x, four.y);
				path.lineTo(five.x, five.y);
				path.lineTo(one.x, one.y);
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
			int distanceThreeFromSix = (int) Math.round((double) 2 * R / Math.sin(angle));
			Point five = new Point((int) Math.round(four.x - 2 * R), four.y);
			Point six = new Point((int) Math.round(three.x - 2 * R), three.y);
			Point eight = new Point(three.x, (int) Math.round(three.y - 4 * R));
	
			Path2D.Double path = new Path2D.Double();
			path.moveTo(one.x, one.y);
			path.curveTo(one.x, one.y, eight.x, eight.y, three.x, three.y);
			path.lineTo(four.x, four.y);
			path.lineTo(five.x, five.y);
			path.lineTo(six.x, six.y);
			path.lineTo(one.x, one.y);
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
			one = new Point(oneX + (int) Math.round((double) MyPrintCanvasDiagram.length / (double) 4), oneY);
			length = (int) Math
					.round(MyPrintCanvasDiagram.length - ((double) MyPrintCanvasDiagram.length / (double) 4));
			// changing tangent to a sharp angle
			tangent = 180 - tangent;
			// angle in radians
			double angle = tangent * Math.PI / 180;
			// 2.calculation of point 3, 6, and 9
			Point three = new Point(one.x + length, oneY);

			int distanceOneFromSix = (int) Math.round((double) 2 * R / Math.sin(angle));
			System.out.println("distanceOneFromSix:" + distanceOneFromSix);
			Point six = new Point(one.x + distanceOneFromSix, one.y);
			System.out.println("point six:" + six.x + "," + six.y);

			Point nine = new Point((int) Math.round((one.getX() + six.getX()) / 2), one.y);
			System.out.println("point nine:" + nine.x + "," + nine.y);
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
				path.moveTo(three.x, three.y);
				path.curveTo(three.x, three.y, eight.x, eight.y, one.x, one.y);
				path.lineTo(four.x, four.y);
				path.lineTo(five.x, five.y);
				path.lineTo(six.x, six.y);
				path.lineTo(three.x, three.y);
				AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
				Shape path2 = path.createTransformedShape(at);
				// center of circle is center of four and five
				centerCircle = two;
				
				// creating the represented line of the strings (the middle
				// line)
				// two-middleSixOne
				Point middleSixOne = new Point((int) Math.round((six.getX() + one.getX()) / 2),
						(int) Math.round((six.getY() + one.getY()) / 2));
				reperesnetedLine = new Line2D.Double();
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
				path.moveTo(three.x, three.y);
				path.curveTo(three.x, three.y, eight.x, eight.y, one.x, one.y);
				path.lineTo(four.x, four.y);
				path.lineTo(five.x, five.y);
				path.lineTo(three.x, three.y);
				AffineTransform at = AffineTransform.getTranslateInstance(0, 0);
				Shape path2 = path.createTransformedShape(at);
				// center of circle is center of four and five
				centerCircle = two;
				
				// creating the represented line of the strings (the middle
				// line)
				// two-middleThreeOne
				Point middleThreeOne = new Point((int) Math.round((three.getX() + one.getX()) / 2),
						(int) Math.round((three.getY() + one.getY()) / 2));
				reperesnetedLine.setLine(middleThreeOne, two);
				
				return path2;
			}
		}

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

		path.curveTo(centerCirclex + R * kappa, centerCircley - R, centerCirclex + R, centerCircley - R * kappa,
				centerCirclex + R, centerCircley); // curve to A', B', B
		path.curveTo(centerCirclex + R, centerCircley + R * kappa, centerCirclex + R * kappa, centerCircley + R,
				centerCirclex, centerCircley + R);
		path.curveTo(centerCirclex - R * kappa, centerCircley + R, centerCirclex - R, centerCircley + R * kappa,
				centerCirclex - R, centerCircley);
		path.curveTo(centerCirclex - R, centerCircley - R * kappa, centerCirclex - R * kappa, centerCircley - R,
				centerCirclex, centerCircley - R);

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
	 * gets the center of circle
	 * 
	 * @return
	 */
	public Point getCenterCircleAfterTrasformation() {
		return this.centerCircleAfterTrasformation;
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
	public Point findPointSix(double tangent, Point one, Point five) {
		int sixX = (int) Math.round(((double) (one.y - five.y + tangent * five.x)) / tangent);
		Point six = new Point(sixX, one.y);
		return six;
	}

	/**
	 * @param tangent
	 *            - set average tangent
	 */
	public void setTangent(double tangent) {
		this.tangent = tangent;
	}

	/**
	 * Draws a circle
	 * 
	 * @param g
	 * @param x
	 * @param y
	 * @param r
	 */
	public void drawCenteredCircle(Graphics2D g, int x, int y, int r) {
		x = x - (r / 2);
		y = y - (r / 2);
		g.fillOval(x, y, r, r);
	}
}
