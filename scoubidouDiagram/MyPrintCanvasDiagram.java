package scoubidouDiagram;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;

import scoubidouDiagram.stitchAlgoForPrintStitch;

/**
 * this class is for drawing one stitch that is only drawn with lines and a
 * square
 * 
 * Paint method draws the entire stitch (given Graphic g). And it also fills the
 * BufferedImages horizoRepresentedLines and
 *
 * 
 * 
 * 
 * @author Yonatan Setbon
 *
 */
class MyPrintCanvasDiagram extends JComponent {

	// x, a can be changeable
	public static int a = 1;
	// y, b can be changeable
	public static int b = 1;

	// the length of a tile
	public static int length = 100;
	// thickness of rectangle border, suggested to be set as 3
	public static int recWidth = 5;
	// for finding the width and height of the picture
	public static int lengthOfStringEnd = length + 112;

	public static int x1 = lengthOfStringEnd + 50;

	public static int yOfStitch = lengthOfStringEnd;

	public static int y1 = yOfStitch + 50;
	public int width = 2 * length * a;
	public int hight = 2 * length * b;

	// JPanel mouse = new JPanel();
	static int firstLineEndPoint = 5;
	int crissNumberOfLines = 1;
	// Color firstColor = Color.RED;
	// Color secondColor = Color.BLACK;
	// if to paint the rectangle of the stitch
	public static Boolean isPaintRectangle = true;
	// this is the center of circle after transformation, for finding the line
	// of the string for intersection weaving. These points are for the line
	// that will represent the string.
	Point centerCircleTrasformation;
	Point centerOfRedPoint;
	// the line representations of horizo strings
	public BufferedImage[] horizoRepresentedLines = null;
	// the line representations of paralel strings
	public BufferedImage[] paralelRepresentedLines = null;
	// the line representations of horizo strings starting position
	public BufferedImage[] horizoRepresentedLinesStart = null;
	// the line representations of paralel strings starting position
	public BufferedImage[] paralelRepresentedLinesStart = null;
	// if true,criss is weaved.if false cross is weaved.
	public static Boolean isCrissWeave;
	// the list of colors for the strings
	public static Color[] colors;

	public static int widthOfImage = 2 * a * length + x1 + 2 * lengthOfStringEnd - length;
	public static int heightOfImage = 2 * b * length + y1 + 3 * lengthOfStringEnd;

	public BufferedImage rectangleImage;
	public static double stepOfAngle = 0.5;

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
		// first line of the algorithm
		nodeLine l = new nodeLine(allPoints[0], allPoints[firstLineEndPoint], allPoints[0].color);
		int inbetweenPoints = listNodePoints.pointsInBetween(l.nodeGreen, l.nodeRed);
		int inbetweenPointsSmall = listNodePoints.pointsInBetween(l.nodeRed, l.nodeGreen);

		if ((crissNumberOfLine >= (inbetweenPoints / 2)) || (crissNumberOfLine >= (inbetweenPointsSmall / 2))) {
			if (firstLineEndPoint <= 2 * a + 2 * b - 1) {
				this.crissNumberOfLines = (((firstLineEndPoint + 1) / 2) - 1);
			} else {
				this.crissNumberOfLines = (((4 * a + 4 * b + 1 - firstLineEndPoint) / 2) - 1);
			}
		} else {

			this.crissNumberOfLines = crissNumberOfLine;

		}
	}

	/**
	 * Paint method draws the entire stitch (given Graphic g). And it also fills
	 * the BufferedImages horizoRepresentedLines and paralelRepresentedLines
	 * 
	 */
	public void paint(Graphics g) {
		int k = 4 * a + 4 * b;

		boolean isLInside = false;
		// changeCrissNumberOfLines(crissNumberOfLines);
		int crossNumberOfLines = (k - 4 * crissNumberOfLines) / 4;
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
				RenderingHints.VALUE_ANTIALIAS_ON);
		// painting the rectangle border and tiles underneafe
		if (isPaintRectangle == true) {
			g2.setColor(Color.BLACK);
			for (int i = x1; i < 2 * length * a + x1; i = i + length) {
				for (int j = y1; j < 2 * length * b + y1; j = j + length) {
					g2.drawRect(i, j, length, length);
				}
			}
			g2.setColor(Color.BLACK);
			g2.drawRect(x1, y1, 2 * length * a, 2 * length * b);

			// painting the rectangle into an image

			BufferedImage recImg = new BufferedImage(widthOfImage, heightOfImage, BufferedImage.TYPE_INT_ARGB);
			Graphics2D graph = recImg.createGraphics();

			graph.setColor(Color.BLACK);
			// drawing tiles from up to down
			int indexColor = 0;
			for (int i = x1; i < 2 * length * a + x1; i = i + 2 * length) {
				for (int j = y1 + length; j < 2 * length * b + y1; j = j + 2 * length) {
					graph.setColor(colors[indexColor]);
					Rectangle tileRec = new Rectangle(i, j, length, length);
					graph.fill(tileRec);
					graph.setColor(Color.BLACK);
					graph.setStroke(new BasicStroke(recWidth));
					graph.draw(tileRec);
				}
				indexColor = indexColor + 2;
			}
			// drawing tiles from down to up
			indexColor = 4 * a + 2 * b - 2;
			for (int i = x1 + length; i < 2 * length * a + x1; i = i + 2 * length) {
				for (int j = y1; j < 2 * length * b + y1; j = j + 2 * length) {
					graph.setColor(colors[indexColor]);
					Rectangle tileRec = new Rectangle(i, j, length, length);
					graph.fill(tileRec);
					graph.setColor(Color.BLACK);
					graph.setStroke(new BasicStroke(recWidth));
					graph.draw(tileRec);

				}
				indexColor = indexColor - 2;
			}
			// drawing tiles from right to left
			indexColor = 2 * a;
			for (int j = y1; j < 2 * length * b + y1; j = j + 2 * length) {
				for (int i = x1; i < 2 * length * a + x1; i = i + 2 * length) {
					graph.setColor(colors[indexColor]);
					Rectangle tileRec = new Rectangle(i, j, length, length);
					graph.fill(tileRec);
					graph.setColor(Color.BLACK);
					graph.setStroke(new BasicStroke(recWidth));
					graph.draw(tileRec);
				}
				indexColor = indexColor + 2;
			}
			// drawing tiles from left to right
			indexColor = 4 * a + 4 * b - 2;
			for (int j = y1 + length; j < 2 * length * b + y1; j = j + 2 * length) {
				for (int i = x1 + length; i < 2 * length * a + x1; i = i + 2 * length) {
					graph.setColor(colors[indexColor]);
					Rectangle tileRec = new Rectangle(i, j, length, length);
					graph.fill(tileRec);
					graph.setColor(Color.BLACK);
					graph.setStroke(new BasicStroke(recWidth));
					graph.draw(tileRec);

				}
				indexColor = indexColor - 2;
			}

			graph.setColor(Color.BLACK);
			graph.setStroke(new BasicStroke(recWidth));
			graph.drawRect(x1, y1, 2 * length * a, 2 * length * b);
			graph.dispose();
			rectangleImage = recImg;

		}
		nodePoint[] allPoints = new nodePoint[k];
		// getting all points
		allPoints = listNodePoints.listOfNodePoints();

		//////////////////////////////////////////////////////////////////////////////////////////
		// drawing the actual strings

		nodeLine l = new nodeLine(allPoints[0], allPoints[firstLineEndPoint], allPoints[0].color);
		nodeLine[] paralel = new nodeLine[crissNumberOfLines];

		paralel = stitchAlgoForPrintStitch.paralelReturnOneOption(l, crissNumberOfLines, colors);
		for (int i = 0; i < 2 * crissNumberOfLines; i++) {
			if (paralel[i] == l) {
				isLInside = true;
			}
		}
		nodeLine[] horizo = null;
		Graphics gBackUp = g;
		// getting horizo strings
		horizo = stitchAlgoForPrintStitch.paralelReturnOneOptionOposite(l, crissNumberOfLines, colors);
		// setting horizoRepresentedLines
		horizoRepresentedLines = new BufferedImage[horizo.length];
		horizoRepresentedLinesStart = new BufferedImage[horizo.length];

		paralel = new nodeLine[crossNumberOfLines];
		paralel = stitchAlgoForPrintStitch.paralelReturnOneOption(l, crossNumberOfLines, colors);
		// setting paralelRepresentedLines
		paralelRepresentedLines = new BufferedImage[paralel.length];
		paralelRepresentedLinesStart = new BufferedImage[paralel.length];

		Rectangle stitchRec = new Rectangle(x1, y1, 2 * length * a, 2 * length * b);

		//////////////////////////////////////////////////////////////////////////////////////////

		// if we need to paint criss

		// middle index of horizo array
		int middleIndexOfHorizo = horizo.length / 2;
		double averageEvenTangetsHorizo = 0;

		int[] backWeaving = new int[horizo.length];

		for (int i = 0; i < horizo.length; i++) {
			backWeaving[i] = 0;
		}

		// computing the average tangents of all horizo strings
		for (int i = 0; i < horizo.length; i = i + 2) {
			averageEvenTangetsHorizo = averageEvenTangetsHorizo
					+ Math.toDegrees(angleBetween2Lines(new Line2D.Double(horizo[i].nodeGreen.x, horizo[i].nodeGreen.y,
							horizo[i].nodeRed.x, horizo[i].nodeRed.y), new Line2D.Double(x1 - 4, y1, 0, y1)));
		}

		averageEvenTangetsHorizo = averageEvenTangetsHorizo / (double) middleIndexOfHorizo;
		// saves this local averageEvenTangetsHorizo to continue when we run
		// case one of the 'while'
		double averageEvenTangetsHorizoTemp = Math.round(averageEvenTangetsHorizo);
		double averageOddTangetsHorizo = 0;
		for (int i = 1; i < horizo.length; i = i + 2) {
			averageOddTangetsHorizo = averageOddTangetsHorizo
					+ Math.toDegrees(angleBetween2Lines(new Line2D.Double(horizo[i].nodeGreen.x, horizo[i].nodeGreen.y,
							horizo[i].nodeRed.x, horizo[i].nodeRed.y), new Line2D.Double(x1 - 4, y1, 0, y1)));
		}
		averageOddTangetsHorizo = averageOddTangetsHorizo / (double) middleIndexOfHorizo;
		double averageOddTangetsHorizoTemp = Math.round(averageOddTangetsHorizo);
		
		
		if (isCrissWeave == true) {
			PrintCanvasWeave printCanvas = new PrintCanvasWeave();
			
			// changing backWeaving center strings
			backWeaving[horizo.length / 2] = 5;
			backWeaving[(horizo.length / 2) - 1] = 5;

			printCanvas.WeaveCase(averageEvenTangetsHorizo, averageEvenTangetsHorizoTemp, averageOddTangetsHorizo,
					averageOddTangetsHorizoTemp, colors, horizo, horizoRepresentedLines, horizoRepresentedLinesStart,
					stitchRec, g, gBackUp, width, hight, widthOfImage, heightOfImage, y1, x1, length, stepOfAngle,
					backWeaving);
			double minimumOfcaseTempNew = printCanvas.minimumOfcaseTemp;
			
			// changing backWeaving center strings
			backWeaving[horizo.length / 2] = 0;
			backWeaving[(horizo.length / 2) - 1] = 0;
			// setting strings with backWeaving = 0
			printCanvas.WeaveCase(averageEvenTangetsHorizo, averageEvenTangetsHorizoTemp, averageOddTangetsHorizo,
					averageOddTangetsHorizoTemp, colors, horizo, horizoRepresentedLines, horizoRepresentedLinesStart,
					stitchRec, g, gBackUp, width, hight, widthOfImage, heightOfImage, y1, x1, length, stepOfAngle,
					backWeaving);
			double minimumOfcaseTemp = printCanvas.minimumOfcaseTemp;

		

			// else..we backweaving the middle strings until an optimal weaving arises
			while (minimumOfcaseTempNew > minimumOfcaseTemp && backWeaving[horizo.length / 2]<40) {
				minimumOfcaseTempNew = minimumOfcaseTemp;
				backWeaving[horizo.length / 2] = backWeaving[horizo.length / 2] +1;
				backWeaving[(horizo.length / 2) - 1] =backWeaving[(horizo.length / 2) - 1]+1;

				printCanvas.WeaveCase(averageEvenTangetsHorizo, averageEvenTangetsHorizoTemp, averageOddTangetsHorizo,
						averageOddTangetsHorizoTemp, colors, horizo, horizoRepresentedLines,
						horizoRepresentedLinesStart, stitchRec, g, gBackUp, width, hight, widthOfImage, heightOfImage,
						y1, x1, length, stepOfAngle, backWeaving);
				minimumOfcaseTempNew = printCanvas.minimumOfcaseTemp;
			}
		}

		//////////////////////////////////////////////////////////////////
		// if we need to paint cross

		// middle index of paralel array
		int middleIndexOfParalel = paralel.length / 2;
		double averageEvenTangetsParalel = 0;

		backWeaving = new int[paralel.length];

		for (int i = 0; i < paralel.length; i++) {
			backWeaving[i] = 0;
		}

		for (int i = 0; i < paralel.length; i = i + 2) {
			averageEvenTangetsParalel = averageEvenTangetsParalel
					+ Math.toDegrees(
							angleBetween2Lines(
									new Line2D.Double(paralel[i].nodeGreen.x, paralel[i].nodeGreen.y,
											paralel[i].nodeRed.x, paralel[i].nodeRed.y),
									new Line2D.Double(x1 - 4, y1, 0, y1)));
		}

		averageEvenTangetsParalel = averageEvenTangetsParalel / (double) middleIndexOfParalel;
		// saves this local averageEvenTangetsParalel to continue when we run
		// case one of the 'while'
		double averageEvenTangetsParalelTemp = Math.round(averageEvenTangetsParalel);
		double averageOddTangetsParalel = 0;
		for (int i = 1; i < paralel.length; i = i + 2) {
			averageOddTangetsParalel = averageOddTangetsParalel
					+ Math.toDegrees(
							angleBetween2Lines(
									new Line2D.Double(paralel[i].nodeGreen.x, paralel[i].nodeGreen.y,
											paralel[i].nodeRed.x, paralel[i].nodeRed.y),
									new Line2D.Double(x1 - 4, y1, 0, y1)));
		}
		averageOddTangetsParalel = averageOddTangetsParalel / (double) middleIndexOfParalel;

		double averageOddTangetsParalelTemp = Math.round(averageOddTangetsParalel);

		if (isCrissWeave == false) {
			PrintCanvasWeave printCanvas = new PrintCanvasWeave();

			backWeaving[paralel.length / 2] = 5;
			backWeaving[(paralel.length / 2) - 1] = 5;
			printCanvas.WeaveCase(averageEvenTangetsParalel, averageEvenTangetsParalelTemp, averageOddTangetsParalel,
					averageOddTangetsParalelTemp, colors, paralel, paralelRepresentedLines, paralelRepresentedLinesStart,
					stitchRec, g, gBackUp, width, hight, widthOfImage, heightOfImage, y1, x1, length, stepOfAngle,
					backWeaving);
			double minimumOfcaseTempNew = printCanvas.minimumOfcaseTemp;
			backWeaving[paralel.length / 2] = 0;
			backWeaving[(paralel.length / 2) - 1] =0;
			printCanvas.WeaveCase(averageEvenTangetsParalel, averageEvenTangetsParalelTemp, averageOddTangetsParalel,
					averageOddTangetsParalelTemp, colors, paralel, paralelRepresentedLines,
					paralelRepresentedLinesStart, stitchRec, g, gBackUp, width, hight, widthOfImage, heightOfImage, y1,
					x1, length, stepOfAngle, backWeaving);

			double minimumOfcaseTemp = printCanvas.minimumOfcaseTemp;

		
			// else..
		/*	while (minimumOfcaseTempNew > minimumOfcaseTemp && backWeaving[paralel.length / 2]<40) {
				minimumOfcaseTempNew = minimumOfcaseTemp;
				backWeaving[paralel.length / 2] = backWeaving[paralel.length / 2] +1;
				backWeaving[(paralel.length / 2) - 1] =backWeaving[(paralel.length / 2) - 1]+1;

				printCanvas.WeaveCase(averageEvenTangetsParalel, averageEvenTangetsParalelTemp, averageOddTangetsParalel,
						averageOddTangetsParalelTemp, colors, paralel, paralelRepresentedLines,
						paralelRepresentedLinesStart, stitchRec, g, gBackUp, width, hight, widthOfImage, heightOfImage,
						y1, x1, length, stepOfAngle, backWeaving);
				minimumOfcaseTempNew = printCanvas.minimumOfcaseTemp;
			}*/
		}
	}

	public void drawThickLine(Graphics g, float x, float y, float x2, float y2, double thickness, Color c) {
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

	public void setWidthOfStitch(int recWdth) {
		recWidth = recWdth;
	}

	/*
	 * public void setColorsOfStrings(Color crissColor, Color crossColor) {
	 * this.firstColor = crissColor; this.secondColor = crossColor; }
	 */

	public void setIsCrissWeave(boolean bool) {
		isCrissWeave = bool;
	}

	public void setIsPaintRectangle(boolean bool) {
		isPaintRectangle = bool;
	}

	public BufferedImage[] getHorizoRepresentedLines() {
		return this.horizoRepresentedLines;
	}

	public BufferedImage[] getParalelRepresentedLines() {
		return this.paralelRepresentedLines;
	}

	public static void main(String[] args) {

		JFrame localwindow = new JFrame();

		int firstLineEndPoint = 7;
		int crissNumberOfLines = 2;
		localwindow.setAutoRequestFocus(true);
		MyPrintCanvasDiagram canv = new MyPrintCanvasDiagram();
		canv.changeFirstLineEndPoint(firstLineEndPoint);
		canv.changeCrissNumberOfLines(crissNumberOfLines);
		canv.setIsCrissWeave(true);
		canv.a = 1;
		canv.b = 2;
		Color[] colors = new Color[4 * canv.a + 4 * canv.b];
		for (int i = 0; i < canv.a * 4 + canv.b * 4; i++) {
			Random rand = new Random();
			float r = rand.nextFloat();
			float g = rand.nextFloat();
			float b = rand.nextFloat();
			colors[i] = new Color(r, g, b);
		}

		canv.colors = colors;
		canv.setIsPaintRectangle(true);
		BufferedImage stringImg = new BufferedImage(2 * canv.a * length + canv.x1 + 100,
				2 * canv.b * length + canv.y1 + 100, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = stringImg.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
				RenderingHints.VALUE_ANTIALIAS_ON);
		canv.paint(g2);
		try {
			ImageIO.write(stringImg, "png",
					new File("c://temp//trashStrings//" + "stringImg " + "_" + "firstLineEndPoint" + firstLineEndPoint
							+ "_(" + canv.a + "," + canv.b + ")" + "," + firstLineEndPoint + "," + crissNumberOfLines
							+ ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// painting hoparalelpresentedLines to a file
		if (canv.isCrissWeave == true) {
			for (int i = 0; i < canv.horizoRepresentedLines.length; i++) {
				try {
					System.out.println(
							"horizoRepresentedLines length " + canv.horizoRepresentedLines.length + " index " + i);
					ImageIO.write(canv.horizoRepresentedLines[i], "png",
							new File("c://temp//trashStrings//" + "horizoRepresentedLines " + i + "_"
									+ "firstLineEndPoint" + firstLineEndPoint + "_(" + canv.a + "," + canv.b + ")" + ","
									+ firstLineEndPoint + "," + crissNumberOfLines + ".png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			for (int i = 0; i < canv.horizoRepresentedLinesStart.length; i++) {
				try {
					System.out.println("horizoRepresentedLinesStart" + i);
					ImageIO.write(canv.horizoRepresentedLinesStart[i], "png",
							new File("c://temp//trashStrings//" + "horizoRepresentedLinesStart " + i + "_"
									+ "firstLineEndPoint" + firstLineEndPoint + "_(" + canv.a + "," + canv.b + ")" + ","
									+ firstLineEndPoint + "," + crissNumberOfLines + ".png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		canv.setIsCrissWeave(false);
		canv.setIsPaintRectangle(false);

		canv.paint(g2);

		try {
			ImageIO.write(stringImg, "png",
					new File("c://temp//trashStrings//" + "firstLineEndPoint" + firstLineEndPoint + "_(" + canv.a + ","
							+ canv.b + ")" + "," + firstLineEndPoint + "," + crissNumberOfLines + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// painting paralelRepresentedLines to a file
		if (canv.isCrissWeave == false) {

			for (int i = 0; i < canv.paralelRepresentedLines.length; i++) {
				try {
					ImageIO.write(canv.paralelRepresentedLines[i], "png",
							new File("c://temp//trashStrings//" + "paralelRepresentedLines " + i + "_"
									+ "firstLineEndPoint" + firstLineEndPoint + "_(" + canv.a + "," + canv.b + ")" + ","
									+ firstLineEndPoint + "," + crissNumberOfLines + ".png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		for (int i = 0; i < canv.paralelRepresentedLinesStart.length; i++) {
			try {
				ImageIO.write(canv.paralelRepresentedLinesStart[i], "png",
						new File("c://temp//trashStrings//" + "paralelRepresentedLinesStart " + i + "_"
								+ "firstLineEndPoint" + firstLineEndPoint + "_(" + canv.a + "," + canv.b + ")" + ","
								+ firstLineEndPoint + "," + crissNumberOfLines + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * 
	 * @param line1
	 * @param line2
	 * @return calculate angle between two lines
	 */
	public static double angleBetween2Lines(Line2D line1, Line2D line2) {
		double angle1 = Math.atan2(line1.getY1() - line1.getY2(), line1.getX1() - line1.getX2());
		double angle2 = Math.atan2(line2.getY1() - line2.getY2(), line2.getX1() - line2.getX2());
		return angle1 - angle2;
	}
}
