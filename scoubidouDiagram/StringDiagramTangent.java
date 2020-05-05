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

	public int backWeaving; 
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
						stitchRec,10);
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
				int transy = 200;
				// tangent is a degree angle

				// stitch's rectangle
				Rectangle stitchRec = new Rectangle(30, 200, 400, 300);

				StringDiagramTangent s = new StringDiagramTangent(direction, transx, transy, Color.GREEN, angle,
						stitchRec,30);
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
					ImageIO.write(stringImg, "png", new File("c://temp//stringpaints//" + i + "," + angle
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
						stitchRec,0);
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
						stitchRec,0);
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
			Rectangle stitchRec, int backWeaving) {
		// getting to the shape world
		this.transx = transx;
		this.transy = transy;
		this.stringCases = stringCases;
		this.tangent = tangent;
		this.clrOfString = clrOfString;
		this.stitchRec = stitchRec;
		this.backWeaving = backWeaving;
		this.string = paintString(0, 0, tangent, stitchRec, backWeaving);
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

			System.out.println(backWeaving);
			string = paintString(0, 0, tangent, stitchRecReversed,backWeaving);
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
			
			Point firstPointforRepAfter = new Point();
			Point secondPointforRepAfter = new Point();
			g2.getTransform().transform(reperesnetedLine.getP1(),firstPointforRepAfter);
			g2.getTransform().transform(reperesnetedLine.getP2(),secondPointforRepAfter);
            representedLineAfterTransformation.setLine(firstPointforRepAfter,secondPointforRepAfter);
			
			
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


			string = paintString(0, 0, tangent + 90, stitchRecReversed,backWeaving);


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

			string = paintString(0, 0, tangent + 180, stitchRecReversed,backWeaving);

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

			string = paintString(0, 0, tangent - 90, stitchRecReversed, backWeaving);

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
            representedLineAfterTransformation.setLine(firstPointforRepAfter,secondPointforRepAfter);
            
			g2.rotate(Math.toRadians(-90), 0, 0);
			g2.translate(-transx, -transy);

		}
	}

	/**
	 * 
	 * @param oneX
	 * @param oneY
	 * @param tangent
	 * @param stitchRec - The recangle of the stitch
	 * @return
	 */
	public Shape paintString(int oneX, int oneY, double tangent, Rectangle stitchRec, int lengthOfBackWeave) {
		// gets the rectangle stitch already translated to the point of view of
		// the string (X2 accodinate is 0)
		
		// its MyPrintCanvasDiagram.length / 3, because we want to get outside the big rectangle just a bit 
		axisX = (int) Math.round((stitchRec.getY() + stitchRec.getHeight())) + MyPrintCanvasDiagram.length / 3;
		// that's because stitchRec.getY()=0
		axisX2 = 0;
		axisY = (int) Math.round((stitchRec.getX() + stitchRec.getWidth())) + MyPrintCanvasDiagram.length / 3;
		axisY2 = (int) Math.round(stitchRec.getX()) - MyPrintCanvasDiagram.length / 3;
		
		if (lengthOfBackWeave == 0){
		StringCases stringCases = new StringCases();
		
		Shape string = stringCases.regularCase( oneX,  oneY,  tangent,  stitchRec,  axisX,  axisX2,
				 axisY,  axisY2,  R);
		this.reperesnetedLine = stringCases.reperesnetedLine;
		this.centerCircle = stringCases.centerCircle;
		return string;
		}
		
		else{
			System.out.println("in back");
			StringCases stringCases = new StringCases();
			
			Shape[] string = stringCases.backWeavingCase( oneX,  oneY,  tangent,  stitchRec,  axisX,  axisX2,
					 axisY,  axisY2,  R,  lengthOfBackWeave);
			this.reperesnetedLine = stringCases.reperesnetedLine;
			this.centerCircle = stringCases.centerCircle;
			//returns the top shape of the string 
			return string[1];
		}
		
		
	}
	
	/**
	 * 
	 * @param oneX
	 * @param oneY
	 * @param tangent
	 * @param stitchRec - The rectangle of the stitch
	 * @return The bottom part of a string if exists 
	 */
	public Shape paintStringBottom(int oneX, int oneY, double tangent, Rectangle stitchRec, int lengthOfBackWeave) 
		{
			StringCases stringCases = new StringCases();	
			Shape[] string = stringCases. backWeavingCase( oneX,  oneY,  tangent,  stitchRec,  axisX,  axisX2,
					 axisY,  axisY2,  R,  lengthOfBackWeave);
			this.reperesnetedLine = stringCases.reperesnetedLine;
			this.centerCircle = stringCases.centerCircle;
			//returns the bottom shape of the string 
			return string[0];
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
/**
 * gets the center of circle
 * 
 * @return
 */
public Point getCenterCircleAfterTrasformation() {
	return this.centerCircleAfterTrasformation;
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
}