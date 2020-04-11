package scoubidou2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

class MyPrintCanvasDiagram extends JComponent {
	// thickness of the lines
	public static int stringWidth = 6;
	// thickness of rectangle border
	public static int recWidth = 14;
	// x, a can be changeable
	public static int a = 2;
	// y, b can be changeable
	public static int b = 1;
	public static int lengthOfStringEnd = 90;

	// spaces in-between 2 lines
	public static int yOfStitch = 10 + lengthOfStringEnd;
	public static int length = 100;
	// starting point of square (left up)+a little extra for the length of the
	// string
	public static int x1 = 10 + lengthOfStringEnd;
	public static int y1 = yOfStitch;
	// width and height of the rectangle
	public static int width = 2 * length * a;
	public static int height = 2 * length * b;

	// if true,criss is weaved.if false cross is weaved.
	public static Boolean isCrissWeave = true;
	// if to draw tiles and black rectangle
	public static Boolean isPaintRectangle = true;
	int lineStart = b;
	int lineStartOp = a;
	static int firstLineEndPoint = 15;
	int crissNumberOfLines = 7;
	Color firstColor = Color.RED;
	Color secondColor = Color.BLACK;

	public void setColorsOfStrings(Color crissColor, Color crossColor) {
		this.firstColor = crissColor;
		this.secondColor = crossColor;
	}

	public void setA(int Newa) {
		this.a = Newa;
	}

	public void setB(int Newb) {
		this.b = Newb;
	}

	public void changeFirstLineEndPoint(int firstLineEndPoints) {
		firstLineEndPoint = firstLineEndPoints;
	}

	public void changeCrissNumberOfLines(int crissNumberOfLine) {
		int k = 4 * a + 4 * b;
		nodePoint[] allPoints = new nodePoint[k];
		allPoints = listNodePoints.listOfNodePoints();
		nodeLine l = new nodeLine(allPoints[0], allPoints[firstLineEndPoint]);
		int inbetweenPoints = listNodePoints.pointsInBetween(l.nodeGreen,
				l.nodeRed);
		int inbetweenPointsSmall = listNodePoints.pointsInBetween(l.nodeRed,
				l.nodeGreen);

		if ((crissNumberOfLine >= (inbetweenPoints / 2))
				|| (crissNumberOfLine >= (inbetweenPointsSmall / 2))) {
			if (firstLineEndPoint <= 2 * a + 2 * b - 1) {
				this.crissNumberOfLines = (((firstLineEndPoint + 1) / 2) - 1);
			} else {
				this.crissNumberOfLines = (((4 * a + 4 * b + 1 - firstLineEndPoint) / 2) - 1);
			}
		} else {

			this.crissNumberOfLines = crissNumberOfLine;

		}
	}

	public void paint(Graphics g) {

		int k = 4 * a + 4 * b;
		boolean isLInside = false;
		int crossNumberOfLines = (k - 4 * crissNumberOfLines) / 4;
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(stringWidth));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
				RenderingHints.VALUE_ANTIALIAS_ON);
		// painting the rectangle border and tiles underneafe
		if (isPaintRectangle == true) {
			g2.setStroke(new BasicStroke(recWidth / 2));
			g2.setColor(new Color(10, 108, 179));
			for (int i = x1; i < 2 * length * a + x1; i = i + length) {
				for (int j = y1; j < 2 * length * b + y1; j = j + length) {
					g2.drawRect(i, j, length, length);
				}
			}
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(recWidth));
			g2.drawRect(x1, y1, 2 * length * a, 2 * length * b);

		}
		g2.setStroke(new BasicStroke(stringWidth));

		nodePoint[] allPoints = new nodePoint[k];
		// getting all points
		allPoints = listNodePoints.listOfNodePoints();

		// drawing the actual strings

		nodeLine l = new nodeLine(allPoints[0], allPoints[firstLineEndPoint]);
		nodeLine[] paralel = new nodeLine[crissNumberOfLines];
		paralel = stitchAlgoForPrintStitch.paralelReturnOneOption(l,
				crissNumberOfLines);
		for (int i = 0; i < 2 * crissNumberOfLines; i++) {
			if (paralel[i] == l) {
				isLInside = true;
			}
		}

		nodeLine[] horizo = null;
		horizo = stitchAlgoForPrintStitch.paralelReturnOneOptionOposite(l,
				crissNumberOfLines);
		// if we need to paint Criss
		if (isCrissWeave == true) {
			g2.setColor(firstColor);
			// checks how much Bezier curves we need to draw
			int numberOfBezier = 0;
			boolean isBezier = true;
			int j = 0;
			while (isBezier == true) {
				// if its a string that will be a Bezier
				if ((horizo[j].nodeRed.x == horizo[j].nodeGreen.x)
						&& ((Math.abs(listNodePoints.pointsInBetween(
								horizo[j].nodeRed, horizo[j].nodeGreen)) < 2 * b) || (Math
								.abs(listNodePoints.pointsInBetween(
										horizo[j].nodeRed, horizo[j].nodeGreen)) > 4
								* a + 2 * b))
						|| ((horizo[j].nodeRed.y == horizo[j].nodeGreen.y) && ((Math
								.abs(listNodePoints.pointsInBetween(
										horizo[j].nodeRed, horizo[j].nodeGreen)) < 2 * a) || (Math
								.abs(listNodePoints.pointsInBetween(
										horizo[j].nodeRed, horizo[j].nodeGreen)) > 4
								* b + 2 * a)))) {
					numberOfBezier++;
					// going to next neighbor
					j++;
				} else {
					isBezier = false;
				}
			}
			float ctrlxFirst = (horizo[numberOfBezier].nodeRed.x + horizo[numberOfBezier].nodeGreen.x) / 2;
			float ctrlyFirst = (horizo[numberOfBezier].nodeRed.y + horizo[numberOfBezier].nodeGreen.y) / 2;
			if (numberOfBezier != 0) {
				for (int i = 0; i < numberOfBezier; i++) {
					// create new QuadCurve2D.Float
					QuadCurve2D q = new QuadCurve2D.Float();
					// draw QuadCurve2D.Float with set coordinates
					q.setCurve(horizo[i].nodeRed.x, horizo[i].nodeRed.y,
							ctrlxFirst, ctrlyFirst, horizo[i].nodeGreen.x,
							horizo[i].nodeGreen.y);
					System.out.println(numberOfBezier);
					g2.draw(q);
				}
			}
			for (int i = numberOfBezier; i < 2 * crissNumberOfLines
					- numberOfBezier; i++) {
				drawThickLineWithContinuation(g2, horizo[i].nodeRed.x,
						horizo[i].nodeRed.y, horizo[i].nodeGreen.x,
						horizo[i].nodeGreen.y, stringWidth, firstColor);
			}
			float ctrlxSecond = (horizo[numberOfBezier].nodeRed.x + horizo[numberOfBezier].nodeGreen.x) / 2;
			float ctrlySecond = (horizo[numberOfBezier].nodeRed.y + horizo[numberOfBezier].nodeGreen.y) / 2;
			if (numberOfBezier != 0) {
				for (int i = 2 * crissNumberOfLines - numberOfBezier; i < 2 * crissNumberOfLines; i++) {
					// create new QuadCurve2D.Float
					QuadCurve2D q = new QuadCurve2D.Float();
					// draw QuadCurve2D.Float with set coordinates
					q.setCurve(horizo[i].nodeRed.x, horizo[i].nodeRed.y,
							ctrlxSecond, ctrlySecond, horizo[i].nodeGreen.x,
							horizo[i].nodeGreen.y);
					g2.draw(q);
				}
			}
		}

		paralel = new nodeLine[crossNumberOfLines];
		paralel = stitchAlgoForPrintStitch.paralelReturnOneOption(l,
				crossNumberOfLines);
		g2.setColor(secondColor);
		// if we need to paint Cross
		if (isCrissWeave == false) {
			// checks how much Bezier curves we need to draw
			int numberOfBezier = 0;
			boolean isBezier = true;
			int j = 0;
			while (isBezier == true) {
				// if its a string that will be a Bezier
				if (((paralel[j].nodeRed.x == paralel[j].nodeGreen.x) && ((Math
						.abs(listNodePoints.pointsInBetween(paralel[j].nodeRed,
								paralel[j].nodeGreen)) < 2 * b) || (Math
						.abs(listNodePoints.pointsInBetween(paralel[j].nodeRed,
								paralel[j].nodeGreen)) > 4 * a + 2 * b)))
						|| ((paralel[j].nodeRed.y == paralel[j].nodeGreen.y) && ((Math
								.abs(listNodePoints.pointsInBetween(
										paralel[j].nodeRed,
										paralel[j].nodeGreen)) < 2 * a) || (Math
								.abs(listNodePoints.pointsInBetween(
										paralel[j].nodeRed,
										paralel[j].nodeGreen)) > 4 * b + 2 * a)))) {
					numberOfBezier++;
					// going to next neighbor
					j++;
				} else {
					isBezier = false;
				}
			}
			float ctrlxFirst = (((paralel[numberOfBezier + 1].nodeRed.x + paralel[numberOfBezier + 1].nodeGreen.x) / 2) + ((paralel[numberOfBezier].nodeRed.x + paralel[numberOfBezier].nodeGreen.x) / 2)) / 2;
			float ctrlyFirst = (((paralel[numberOfBezier + 1].nodeRed.y + paralel[numberOfBezier + 1].nodeGreen.y) / 2) + ((paralel[numberOfBezier].nodeRed.y + paralel[numberOfBezier].nodeGreen.y) / 2)) / 2;
			if (numberOfBezier != 0) {
				for (int i = 0; i < numberOfBezier; i++) {
					float indexMiddleX = (paralel[i].nodeRed.x + paralel[i].nodeGreen.x) / 2;
					float indexMiddleY = (paralel[i].nodeRed.y + paralel[i].nodeGreen.y) / 2;

					float t = ((float) i + 1) / ((float) numberOfBezier + 1);
					float controlx = (float) ((1 - t) * indexMiddleX + t
							* (ctrlxFirst));
					float controly = (float) ((1 - t) * indexMiddleY + t
							* (ctrlyFirst));

					// create new QuadCurve2D.Float
					QuadCurve2D q = new QuadCurve2D.Float();
					// draw QuadCurve2D.Float with set coordinates
					q.setCurve(paralel[i].nodeRed.x, paralel[i].nodeRed.y,
							controlx, controly, paralel[i].nodeGreen.x,
							paralel[i].nodeGreen.y);
					System.out.println(numberOfBezier);
					g2.draw(q);

					drawLineContinuationOfBizier(g2,paralel[i].nodeRed.x,
							paralel[i].nodeRed.y, controlx, controly,
							
							paralel[i].nodeGreen.x, paralel[i].nodeGreen.y, 	
							stringWidth, secondColor);
				}
			}
			for (int i = numberOfBezier; i < 2 * crossNumberOfLines
					- numberOfBezier; i++) {
				drawThickLineWithContinuation(g2, paralel[i].nodeRed.x,
						paralel[i].nodeRed.y, paralel[i].nodeGreen.x,
						paralel[i].nodeGreen.y, stringWidth, secondColor);
			}

			if (numberOfBezier != 0) {
				// middle point of the middle points of the last non-beizer
				// lines
				float ctrlxSecond = (((paralel[2 * crossNumberOfLines
						- numberOfBezier - 1].nodeRed.x + paralel[2
						* crossNumberOfLines - numberOfBezier - 1].nodeGreen.x) / 2) + ((paralel[2
						* crossNumberOfLines - numberOfBezier - 2].nodeRed.x + paralel[2
						* crossNumberOfLines - numberOfBezier - 2].nodeGreen.x) / 2)) / 2;
				float ctrlySecond = (((paralel[2 * crossNumberOfLines
						- numberOfBezier - 1].nodeRed.y + paralel[2
						* crossNumberOfLines - numberOfBezier - 1].nodeGreen.y) / 2) + ((paralel[2
						* crossNumberOfLines - numberOfBezier - 2].nodeRed.y + paralel[2
						* crossNumberOfLines - numberOfBezier - 2].nodeGreen.y) / 2)) / 2;
				j = 0;

				for (int i = 2 * crossNumberOfLines - numberOfBezier; i < 2 * crossNumberOfLines; i++) {
					// indexMiddle is the middle point of the nodeLine i
					float indexMiddleX = (paralel[i].nodeRed.x + paralel[i].nodeGreen.x) / 2;
					float indexMiddleY = (paralel[i].nodeRed.y + paralel[i].nodeGreen.y) / 2;
					// t is the blending point
					float t = 1 - (((float) j + 1) / ((float) numberOfBezier + 1));
					// control of the Beizier point
					float controlx = (float) (1 - t) * indexMiddleX + t
							* (ctrlxSecond);
					float controly = (float) (1 - t) * indexMiddleY + t
							* (ctrlySecond);

					// create new QuadCurve2D.Float
					QuadCurve2D q = new QuadCurve2D.Float();
					// draw QuadCurve2D.Float with set coordinates
					q.setCurve(paralel[i].nodeRed.x, paralel[i].nodeRed.y,
							controlx, controly, paralel[i].nodeGreen.x,
							paralel[i].nodeGreen.y);
					System.out.println(numberOfBezier);
					g2.draw(q);
					j++;
					drawLineContinuationOfBizier(g2,paralel[i].nodeRed.x,
							paralel[i].nodeRed.y, controlx, controly,
							
							paralel[i].nodeGreen.x, paralel[i].nodeGreen.y, 	
							stringWidth, secondColor);
				
				}
			}

		}
		Color firstClr = allPoints[1].color;
		g2.setColor(firstClr);

		// crissNumberOfLines is for horizo array,this is for the last "over"

	}

	private static double sqr(double x) {
		return x * x;
	}

	/**
	 * @param pt1
	 *            - first point of the line
	 * @param pt2
	 *            - second point of the line
	 * @param pt0
	 *            - the given point to be transform
	 * @return get closest point from a line, this is an checked method
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
	 * @param g
	 * @param point0x
	 *            - first point x
	 * @param point0y
	 *            - first point y
	 * @param point1x
	 *            - control point x
	 * @param point1y
	 *            - control point y
	 * @param point2x
	 *            - second point x
	 * @param point2y
	 *            - second point y
	 * @param flag
	 *            - if to draw continuation of bizer from right to left or vise
	 *            versa
	 */
	public void drawLineContinuationOfBizier(Graphics g, float point0x,
			float point0y, float point1x, float point1y, float point2x,
			float point2y, double thickness, Color c) {
		double t = 0.99;
		float firstPointx = (float) ((1 - t) * (1 - t) * point0x + 2 * (1 - t)
				* t * point1x + t * t * point2x);
		float firstPointy = (float) ((1 - t) * (1 - t) * point0y + 2 * (1 - t)
				* t * point1y + t * t * point2y);
		t = 1.00;
		float secondPointx = (float) ((1 - t) * (1 - t) * point0x + 2 * (1 - t)
				* t * point1x + t * t * point2x);
		float secondPointy = (float) ((1 - t) * (1 - t) * point0y + 2 * (1 - t)
				* t * point1y + t * t * point2y);
		float tangent = (float) Math.atan((secondPointy - firstPointy)
				/ (secondPointx - firstPointx));
		
		float thirdPointx = Math.abs((float) ((float) Math.cos(tangent) * 1.5 * lengthOfStringEnd));
		float thirdPointy =Math.abs( (float) ((float) Math.sin(tangent) * 1.5 * lengthOfStringEnd));
		// the continuation line is from secondPoint to thirdPoint
		if((secondPointy - firstPointy)>0 && (secondPointx - firstPointx)>0){
			drawThickLine(g, secondPointx, secondPointy, secondPointx
					+ thirdPointx, secondPointy + thirdPointy, thickness, c);
		}
		if((secondPointy - firstPointy)<0 && (secondPointx - firstPointx)<0){
			drawThickLine(g, secondPointx, secondPointy, secondPointx
					- thirdPointx, secondPointy - thirdPointy, thickness, c);
		}
		if((secondPointy - firstPointy)>0 && (secondPointx - firstPointx)<0){
			drawThickLine(g, secondPointx, secondPointy, secondPointx
					- thirdPointx, secondPointy + thirdPointy, thickness, c);
		}
		if((secondPointy - firstPointy)<0 && (secondPointx - firstPointx)>0){
			drawThickLine(g, secondPointx, secondPointy, secondPointx
					+ thirdPointx, secondPointy - thirdPointy, thickness, c);
		}
		
	}

	public void drawThickLineWithContinuation(Graphics g, float x, float y,
			float x2, float y2, double thickness, Color c) {
		if ((x != x2) || (y != y2)) {
			g.setColor(c);
			int width = 2 * length * a;
			int hight = 2 * length * b;
			Point firstPoint = new Point((int) x, (int) y);
			Point secondPoint = new Point((int) x2, (int) y2);
			Point new_secondPoint = new Point();
			if (x2 == x1) {
				Point outsidePoint = new Point((int) x2 - lengthOfStringEnd,
						(int) y2);
				new_secondPoint = getClosestPointOnALine(firstPoint,
						secondPoint, outsidePoint);
				int tempo_lengthOfStringEnd = lengthOfStringEnd;
				int i = 1;
				// adding a little more length
				while (new_secondPoint.distance(secondPoint) < lengthOfStringEnd) {
					tempo_lengthOfStringEnd = tempo_lengthOfStringEnd + i;
					outsidePoint = new Point(
							(int) x2 - tempo_lengthOfStringEnd, (int) y2);
					new_secondPoint = getClosestPointOnALine(firstPoint,
							secondPoint, outsidePoint);
					i++;
				}
				drawThickLine(g, x, y, new_secondPoint.x, new_secondPoint.y,
						thickness, c);

			}
			if (x2 == x1 + width) {
				Point outsidePoint = new Point((int) x2 + lengthOfStringEnd,
						(int) y2);
				new_secondPoint = getClosestPointOnALine(firstPoint,
						secondPoint, outsidePoint);
				int tempo_lengthOfStringEnd = lengthOfStringEnd;
				int i = 1;
				// adding a little more length
				while (new_secondPoint.distance(secondPoint) < lengthOfStringEnd) {
					tempo_lengthOfStringEnd = tempo_lengthOfStringEnd + i;
					outsidePoint = new Point(
							(int) x2 + tempo_lengthOfStringEnd, (int) y2);
					new_secondPoint = getClosestPointOnALine(firstPoint,
							secondPoint, outsidePoint);
					i++;
				}
				drawThickLine(g, x, y, new_secondPoint.x, new_secondPoint.y,
						thickness, c);

			}

			if (y2 == y1) {
				Point outsidePoint = new Point((int) x2, (int) y2
						- lengthOfStringEnd);
				new_secondPoint = getClosestPointOnALine(firstPoint,
						secondPoint, outsidePoint);
				int tempo_lengthOfStringEnd = lengthOfStringEnd;
				int i = 1;
				// adding a little more length
				while (new_secondPoint.distance(secondPoint) < lengthOfStringEnd) {
					tempo_lengthOfStringEnd = tempo_lengthOfStringEnd + i;
					outsidePoint = new Point((int) x2, (int) y2
							- tempo_lengthOfStringEnd);
					new_secondPoint = getClosestPointOnALine(firstPoint,
							secondPoint, outsidePoint);
					i++;
				}
				drawThickLine(g, x, y, new_secondPoint.x, new_secondPoint.y,
						thickness, c);

			}
			if (y2 == y1 + hight) {
				Point outsidePoint = new Point((int) x2, (int) y2
						+ lengthOfStringEnd);
				new_secondPoint = getClosestPointOnALine(firstPoint,
						secondPoint, outsidePoint);
				int tempo_lengthOfStringEnd = lengthOfStringEnd;
				int i = 1;
				// adding a little more length
				while (new_secondPoint.distance(secondPoint) < lengthOfStringEnd) {
					tempo_lengthOfStringEnd = tempo_lengthOfStringEnd + i;
					outsidePoint = new Point((int) x2, (int) y2
							+ tempo_lengthOfStringEnd);
					new_secondPoint = getClosestPointOnALine(firstPoint,
							secondPoint, outsidePoint);
					i++;
				}
				drawThickLine(g, x, y, new_secondPoint.x, new_secondPoint.y,
						thickness, c);

			}

		}
	}

	public void drawThickLine(Graphics g, float x, float y, float x2, float y2,
			double thickness, Color c) {
		// The thick line is in fact a filled polygon
		g.setColor(c);
		double dX = x2 - x;
		double dY = y2 - y;
		// line length
		double lineLength = Math.sqrt(dX * dX + dY * dY);

		double scale = (double) (thickness) / (2 * lineLength);

		// The x,y increments from an endpoint needed to create a rectangle...
		double ddx = -scale * (float) dY;
		double ddy = scale * (float) dX;
		ddx += (ddx > 0) ? 0.5 : -0.5;
		ddy += (ddy > 0) ? 0.5 : -0.5;
		int dx = (int) ddx;
		int dy = (int) ddy;

		// Now we can compute the corner points...
		int xPoints[] = new int[4];
		int yPoints[] = new int[4];

		xPoints[0] = (int) (x + dx);
		yPoints[0] = (int) (y + dy);
		xPoints[1] = (int) (x - dx);
		yPoints[1] = (int) (y - dy);
		xPoints[2] = (int) (x2 - dx);
		yPoints[2] = (int) (y2 - dy);
		xPoints[3] = (int) (x2 + dx);
		yPoints[3] = (int) (y2 + dy);

		g.fillPolygon(xPoints, yPoints, 4);
	}

	/**
	 * Converts a given Image into a BufferedImage
	 *
	 * @param img
	 *            The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null),
				img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}

	public void setIsCrissWeave(boolean bool) {
		MyPrintCanvasDiagram.isCrissWeave = bool;
	}

	public void setIsPaintRectangle(boolean bool) {
		MyPrintCanvasDiagram.isPaintRectangle = bool;
	}

	public void setWidthOfStitch(int stngWidth, int recWdth) {
		stringWidth = stngWidth;
		recWidth = recWdth;
	}

	public static void main(String[] args) {
		BufferedImage rectangle = new BufferedImage(1000, 1000,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = rectangle.createGraphics();
		int a = 2, b = 1;
		if (isPaintRectangle == true) {
			g2.setStroke(new BasicStroke(recWidth / 2));
			g2.setColor(new Color(10, 108, 179));
			for (int i = x1; i < 2 * length * a + x1; i = i + length) {
				for (int j = y1; j < 2 * length * b + y1; j = j + length) {
					g2.drawRect(i, j, length, length);
				}
			}
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(recWidth));
			g2.drawRect(x1, y1, 2 * length * a, 2 * length * b);
			try {
				ImageIO.write(rectangle, "png", new File("c://temp//" + "2X1"
						+ ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public static int getLengthOfStringEnd() {
		return MyPrintCanvasDiagram.lengthOfStringEnd;
	}

}
