
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Area;

import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

class MyPrintCanvasTextDiagram extends JComponent {
	// thickness of the lines
	public static int stringWidth = 50;
	// thickness of rectangle border
	public static int recWidth = 14;
	// x, a can be changeable
	public static int a = 1;
	// y, b can be changeable
	public static int b = 1;
	public static int lengthOfStringEnd = 50;

	// spaces in-between 2 lines
	public static int yOfStitch = 10 + lengthOfStringEnd;
	public static int length = 100;
	// starting point of square (left up)+a little extra for the length of the
	// string
	public static int x1 = 200 + lengthOfStringEnd;
	public static int y1 = 200 + yOfStitch;

	// width and height of the rectangle
	public static int width = 2 * length * a;
	public static int height = 2 * length * b;
	public static int counterGif = 1;
	public static Color[] colors;
	// if true,criss is weaved.if false cross is weaved.
	public static Boolean isCrissWeave = true;
	// if to draw tiles and black rectangle
	public static Boolean isPaintRectangle = true;
	int lineStart = b;
	int lineStartOp = a;
	static int firstLineEndPoint = 5;
	int crissNumberOfLines = 1;
	Color firstColor = Color.RED;
	Color secondColor = Color.BLACK;
	// the line representations of horizo strings
	public BufferedImage[] horizoRepresentedLines = null;
	// the underline if needed backweaving
	public BufferedImage[] horizoRepresentedLinesBottom = null;
	// the lines of the top strings
	public nodeLine[] horizoTop = null;
	// the lines of the bottom strings, if there's a backweave then we get a
	// line that is not 0
	public nodeLine[] horizoBottom = null;

	// the line representations of paralel strings
	public BufferedImage[] paralelRepresentedLines = null;
	// the underline if needed backweaving
	public BufferedImage[] paralelRepresentedLinesBottom = null;
	// the lines of the top strings
	public nodeLine[] paralelTop = null;
	// the lines of the bottom strings, if there's a backweave then we get a
	// line that is not 0
	public nodeLine[] paralelBottom = null;

	// lines of the border of the strings
	public Line2D.Float[] horizoSegmentUp = null;
	public Line2D.Float[] horizoSegmentDown = null;
	public Line2D.Float[] horizoSegmentVerticle = null;

	public Line2D.Float[] paralelSegmentUp = null;
	public Line2D.Float[] paralelSegmentDown = null;
	public Line2D.Float[] paralelSegmentVerticle = null;

	public Area[] horizoArea = null;
	public Area[] paralelArea = null;

	// the bounds of the shape
	Rectangle2D[] rectangleShapeHorizo;
	Rectangle2D[] rectangleShapeParalel;

	boolean isGif = true;

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
		allPoints = listNodeTextPoints.listOfNodePoints();
		nodeLine l = new nodeLine(allPoints[0], allPoints[firstLineEndPoint]);
		int inbetweenPoints = listNodeTextPoints.pointsInBetween(l.nodeGreen, l.nodeRed);
		int inbetweenPointsSmall = listNodeTextPoints.pointsInBetween(l.nodeRed, l.nodeGreen);

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

	// method that creates all the images of strings.
	public void paint(Graphics g) {

		int k = 4 * a + 4 * b;
		int width = 2 * length * a;
		int hight = 2 * length * b;

		int widthPic = 2 * a * MyPrintCanvasTextDiagram.length + 2 * MyPrintCanvasTextDiagram.x1
				+ 2 * MyPrintCanvasTextDiagram.lengthOfStringEnd + 100;
		int heightPic = 2 * b * MyPrintCanvasTextDiagram.length + 2 * MyPrintCanvasTextDiagram.y1 + 100;

		boolean isLInside = false;
		int crossNumberOfLines = (k - 4 * crissNumberOfLines) / 4;
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);

		BasicStroke stroke = new BasicStroke(stringWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 0.1F);
		BasicStroke strokeBorder = new BasicStroke(stringWidth + 2 * recWidth, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL, 0.1F);

		// g2.setStroke(new BasicStroke(stringWidth));
		g2.setStroke(stroke);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
				RenderingHints.VALUE_ANTIALIAS_ON);

		////////////////////////////////////////////////////////////////////////////////////////// painting
		////////////////////////////////////////////////////////////////////////////////////////// the
		////////////////////////////////////////////////////////////////////////////////////////// rectangle
		////////////////////////////////////////////////////////////////////////////////////////// border
		////////////////////////////////////////////////////////////////////////////////////////// and
		////////////////////////////////////////////////////////////////////////////////////////// tiles
		////////////////////////////////////////////////////////////////////////////////////////// underneafe
		if (isPaintRectangle == true) {

			g2.setStroke(new BasicStroke(recWidth));
			g2.setColor(Color.BLACK);
			for (int i = x1; i < 2 * length * a + x1; i = i + length) {
				for (int j = y1; j < 2 * length * b + y1; j = j + length) {
					g2.drawRect(i, j, length, length);
				}
			}

			g2.setStroke(new BasicStroke(recWidth));
			g2.drawRect(x1, y1, 2 * length * a, 2 * length * b);

			// drawing tiles from up to down
			int indexColor = 0;
			for (int i = x1; i < 2 * length * a + x1; i = i + 2 * length) {
				for (int j = y1 + length; j < 2 * length * b + y1; j = j + 2 * length) {

					///// getting a darker color
					Color tempColor = colors[indexColor];
					int red = tempColor.getRed();
					int green = tempColor.getGreen();
					int blue = tempColor.getBlue();
					float[] hsb = new float[3];
					Color.RGBtoHSB(red, green, blue, hsb);
					// formula for finding the shade
					float x = hsb[2];
					hsb[2] = (float) ((x * 0.8) - (Math.pow(0.65 * x, 3) / (Math.log(0.8 * x + 10))));

					Color tempColorDarker = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);

					GradientPaint darkShade = new GradientPaint(i, j, tempColor, i, j + length, tempColorDarker, true);
					g2.setPaint(darkShade);

					// filling the rectangle
					Rectangle tileRec = new Rectangle(i, j, length, length);
					g2.fill(tileRec);

					g2.setColor(Color.BLACK);
					g2.setStroke(new BasicStroke(recWidth));
					g2.draw(tileRec);
				}
				indexColor = indexColor + 2;
			}

			// drawing tiles from down to up
			indexColor = 4 * a + 2 * b - 2;
			for (int i = x1 + length; i < 2 * length * a + x1; i = i + 2 * length) {
				for (int j = y1; j < 2 * length * b + y1; j = j + 2 * length) {

					///// getting a darker color
					Color tempColor = colors[indexColor];
					int red = tempColor.getRed();
					int green = tempColor.getGreen();
					int blue = tempColor.getBlue();
					float[] hsb = new float[3];
					Color.RGBtoHSB(red, green, blue, hsb);

					// formula for finding the shade
					float x = hsb[2];
					hsb[2] = (float) ((x * 0.8) - (Math.pow(0.65 * x, 3) / (Math.log(0.8 * x + 15))));

					Color tempColorDarker = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
					GradientPaint darkShade = new GradientPaint(i, j, tempColor, i, j + length, tempColorDarker, true);
					g2.setPaint(darkShade);

					// filling the rectangle
					Rectangle tileRec = new Rectangle(i, j, length, length);
					g2.fill(tileRec);
					g2.setColor(Color.BLACK);
					g2.setStroke(new BasicStroke(recWidth));
					g2.draw(tileRec);

				}
				indexColor = indexColor - 2;

			}

			// drawing tiles from right to left
			indexColor = 2 * a;

			for (int j = y1; j < 2 * length * b + y1; j = j + 2 * length) {
				for (int i = x1; i < 2 * length * a + x1; i = i + 2 * length) {
					///// getting a darker color
					Color tempColor = colors[indexColor];
					int red = tempColor.getRed();
					int green = tempColor.getGreen();
					int blue = tempColor.getBlue();
					float[] hsb = new float[3];
					Color.RGBtoHSB(red, green, blue, hsb);
					// formula for finding the shade
					float x = hsb[2];
					hsb[2] = (float) ((x * 0.8) - (Math.pow(0.65 * x, 3) / (Math.log(0.8 * x + 10))));

					Color tempColorDarker = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
					GradientPaint darkShade = new GradientPaint(i, j, tempColor, i, j + length, tempColorDarker, true);
					g2.setPaint(darkShade);

					// filling the rectangle
					Rectangle tileRec = new Rectangle(i, j, length, length);
					g2.fill(tileRec);
					g2.setColor(Color.BLACK);
					g2.setStroke(new BasicStroke(recWidth));
					g2.draw(tileRec);
				}
				indexColor = indexColor + 2;
			}

			// drawing tiles from left to right
			indexColor = 4 * a + 4 * b - 2;
			for (int j = y1 + length; j < 2 * length * b + y1; j = j + 2 * length) {
				for (int i = x1 + length; i < 2 * length * a + x1; i = i + 2 * length) {
					///// getting a darker color
					Color tempColor = colors[indexColor];
					int red = tempColor.getRed();
					int green = tempColor.getGreen();
					int blue = tempColor.getBlue();
					float[] hsb = new float[3];
					Color.RGBtoHSB(red, green, blue, hsb);

					// formula for finding the shade
					float x = hsb[2];
					hsb[2] = (float) ((x * 0.8) - (Math.pow(0.65 * x, 3) / (Math.log(0.8 * x + 10))));

					Color tempColorDarker = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
					GradientPaint darkShade = new GradientPaint(i, j, tempColor, i, j + length, tempColorDarker, true);
					g2.setPaint(darkShade);

					// filling the rectangle
					Rectangle tileRec = new Rectangle(i, j, length, length);
					g2.fill(tileRec);
					g2.setColor(Color.BLACK);
					g2.setStroke(new BasicStroke(recWidth));
					g2.draw(tileRec);

				}
				indexColor = indexColor - 2;
			}

			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// drawing the actual strings

			g2.setStroke(stroke);

			nodePoint[] allPoints = new nodePoint[k];
			// getting all points
			allPoints = listNodeTextPoints.listOfNodePoints();

			nodeLine l = new nodeLine(allPoints[0], allPoints[firstLineEndPoint]);

			if (isCrissWeave == true) {
				nodeLine[] horizo = null;
				horizo = stitchAlgoForPrintTextStitch.paralelReturnOneOptionOposite(l, crissNumberOfLines);
				// node points the bottom and top of the string
				horizoBottom = new nodeLine[horizo.length];
				horizoTop = new nodeLine[horizo.length];

				horizoRepresentedLines = new BufferedImage[horizo.length];
				horizoRepresentedLinesBottom = new BufferedImage[horizo.length];
				rectangleShapeHorizo = new Rectangle2D[horizo.length];
				horizoSegmentDown = new Line2D.Float[horizo.length];
				horizoSegmentUp = new Line2D.Float[horizo.length];
				horizoSegmentVerticle = new Line2D.Float[horizo.length];
				horizoArea = new Area[horizo.length];

				/*
				 * InputStream isHorizo =
				 * this.getClass().getResourceAsStream("c://temp//text_files//"
				 * + "horizoPlain" + firstLineEndPoint + "_" +
				 * crissNumberOfLines + "_" + a + "_" + b + ".txt");
				 * System.out.println("c://temp//text_files//" + "horizoPlain" +
				 * firstLineEndPoint + "_" + crissNumberOfLines + "_" + a + "_"
				 * + b + ".txt"); float[] numbersOfTextHorizo;
				 * numbersOfTextHorizo = new float[horizo.length*4]; Scanner
				 * fileScannerHorizo = new Scanner("c://temp//text_files//" +
				 * "horizoPlain" + firstLineEndPoint + "_" + crissNumberOfLines
				 * + "_" + a + "_" + b + ".txt"); for (int i = 0;
				 * fileScannerHorizo.hasNextFloat(); i++) {
				 * numbersOfTextHorizo[i] = (float)
				 * fileScannerHorizo.nextDouble(); } fileScannerHorizo.close();
				 */
				float[] numbersOfTextHorizo = new float[horizo.length * 4];
				Scanner fileScannerHorizo;
				try {
					fileScannerHorizo = new Scanner(new File("c://temp//text_files//" + "horizoPlain"
							+ firstLineEndPoint + "_" + crissNumberOfLines + "_" + a + "_" + b + ".csv"));
					fileScannerHorizo.useDelimiter("\n");		
					fileScannerHorizo.next();
					String horizoCsvFloat = fileScannerHorizo.next();
					for (int i = 0; i < numbersOfTextHorizo.length-1; i++) {									
						System.out.println(horizoCsvFloat);
						numbersOfTextHorizo[i] = Float.parseFloat(horizoCsvFloat);	
						 horizoCsvFloat = fileScannerHorizo.next();
					}
					//adding the last node number
					numbersOfTextHorizo[numbersOfTextHorizo.length-1] = Float.parseFloat(horizoCsvFloat);	
					int indexTextHorizo = 0;
					for (int i = 0; i < horizo.length; i = i + 1) {

						horizoRepresentedLines[i] = drawShapeLineStatic(true, horizo[i], numbersOfTextHorizo[indexTextHorizo],
								numbersOfTextHorizo[indexTextHorizo + 1], numbersOfTextHorizo[indexTextHorizo + 2], numbersOfTextHorizo[indexTextHorizo + 3],
								strokeBorder, stroke, stringWidth, i, colors);
						if ((horizo[i].nodeRed.x != numbersOfTextHorizo[indexTextHorizo])
								|| (horizo[i].nodeRed.y != numbersOfTextHorizo[indexTextHorizo + 1])) {
							horizoRepresentedLinesBottom[i] = drawShapeLineStatic(true, horizo[indexTextHorizo], numbersOfTextHorizo[indexTextHorizo],
									numbersOfTextHorizo[indexTextHorizo+1], numbersOfTextHorizo[indexTextHorizo + 2], numbersOfTextHorizo[indexTextHorizo + 3],
									strokeBorder, stroke, stringWidth, i, colors);
						}
						else{
							int widthHorizo = 2 * a * MyPrintCanvasTextDiagram.length + 2 * MyPrintCanvasTextDiagram.x1
									+ 2 * MyPrintCanvasTextDiagram.lengthOfStringEnd + 100;
							int heightHorizo = 2 * b * MyPrintCanvasTextDiagram.length + 2 * MyPrintCanvasTextDiagram.y1 + 100;
							horizoRepresentedLinesBottom[i] =  new BufferedImage(widthHorizo, heightHorizo, BufferedImage.TYPE_INT_ARGB);
						}
						indexTextHorizo = indexTextHorizo +4;
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			//////////////////////////////////////////////////////////////////////////////////////////////////////////

			// if we need to paint Cross
			if (isCrissWeave == false) {

				nodeLine[] paralel = new nodeLine[crossNumberOfLines];
				paralel = stitchAlgoForPrintTextStitch.paralelReturnOneOption(l, crossNumberOfLines);

				paralelBottom = new nodeLine[paralel.length];
				paralelTop = new nodeLine[paralel.length];

				paralelRepresentedLines = new BufferedImage[paralel.length];
				paralelRepresentedLinesBottom = new BufferedImage[paralel.length];
				rectangleShapeParalel = new Rectangle2D[paralel.length];
				paralelSegmentDown = new Line2D.Float[paralel.length];
				paralelSegmentUp = new Line2D.Float[paralel.length];
				paralelSegmentVerticle = new Line2D.Float[paralel.length];
				paralelArea = new Area[paralel.length];

				float[] numbersOfTextParalel = new float[paralel.length * 4];
				Scanner fileScannerParalel;
				try {
					fileScannerParalel = new Scanner(new File("c://temp//text_files//" + "paralelPlain"
							+ firstLineEndPoint + "_" + crissNumberOfLines + "_" + a + "_" + b + ".csv"));
					fileScannerParalel.useDelimiter("\n");		
					fileScannerParalel.next();
					String paralelCsvFloat = fileScannerParalel.next();
					for (int i = 0; i < numbersOfTextParalel.length-1; i++) {									
						System.out.println(paralelCsvFloat);
						numbersOfTextParalel[i] = Float.parseFloat(paralelCsvFloat);	
						 paralelCsvFloat = fileScannerParalel.next();
					}
					//adding the last node number
					numbersOfTextParalel[numbersOfTextParalel.length-1] = Float.parseFloat(paralelCsvFloat);	
					int indexTextParalel = 0;

					for (int i = 0; i < paralel.length; i = i + 1) {

						paralelRepresentedLines[i] = drawShapeLineStatic(false, paralel[i], numbersOfTextParalel[indexTextParalel],
								numbersOfTextParalel[indexTextParalel + 1], numbersOfTextParalel[indexTextParalel + 2], numbersOfTextParalel[indexTextParalel + 3],
								strokeBorder, stroke, stringWidth, i, colors);
						if ((paralel[i].nodeRed.x != numbersOfTextParalel[indexTextParalel])
								|| (paralel[i].nodeRed.y != numbersOfTextParalel[indexTextParalel + 1])) {
							paralelRepresentedLinesBottom[i] = drawShapeLineStatic(false, paralel[i], numbersOfTextParalel[indexTextParalel],
									numbersOfTextParalel[indexTextParalel+1], numbersOfTextParalel[indexTextParalel + 2], numbersOfTextParalel[indexTextParalel + 3],
									strokeBorder, stroke, stringWidth, i, colors);
						}
						else{
							int widthParalel = 2 * a * MyPrintCanvasTextDiagram.length + 2 * MyPrintCanvasTextDiagram.x1
									+ 2 * MyPrintCanvasTextDiagram.lengthOfStringEnd + 100;
							int heightParalel = 2 * b * MyPrintCanvasTextDiagram.length + 2 * MyPrintCanvasTextDiagram.y1 + 100;
							paralelRepresentedLinesBottom[i] =  new BufferedImage(widthParalel, heightParalel, BufferedImage.TYPE_INT_ARGB);
						}
						indexTextParalel = indexTextParalel+4;
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
	}

	/**
	 * same as drawShape but not a Bizier (just a line)
	 * 
	 * @param paralelOrHorizo
	 * @param strokeBorder
	 * @param stroke
	 * @param stringWidth
	 * @param secondColor
	 * @param i
	 * @param controlx
	 * @param controly
	 * @return
	 */
	// drawing the continuation of the strait line with known starting and
	// ending points
	public static void drawLine(GeneralPath shape, float x, float y, float x2, float y2) {
		shape.moveTo(x, y);
		shape.lineTo(x2, y2);

	}

	public BufferedImage drawShapeLineStatic(boolean isHorizo, nodeLine paralelOrHorizo, float x, float y, float x2,
			float y2, BasicStroke strokeBorder, BasicStroke stroke, int stringWidth, int i, Color[] colors) {
		int width = 2 * a * MyPrintCanvasTextDiagram.length + 2 * MyPrintCanvasTextDiagram.x1
				+ 2 * MyPrintCanvasTextDiagram.lengthOfStringEnd + 100;
		int height = 2 * b * MyPrintCanvasTextDiagram.length + 2 * MyPrintCanvasTextDiagram.y1 + 100;

		// recieving the color of the string
		int indexParalelOrHorizo = listNodeTextPoints.findNodePointIndex(paralelOrHorizo.nodeRed);

		// circle edges
		BufferedImage imageCircleBorder = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2DCircleBorder = imageCircleBorder.createGraphics();
		graphics2DCircleBorder.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
				RenderingHints.VALUE_ANTIALIAS_ON);

		Color tempColor = colors[indexParalelOrHorizo];

		///// getting a darker color
		int red = tempColor.getRed();
		int green = tempColor.getGreen();
		int blue = tempColor.getBlue();

		float[] hsb = new float[3];
		Color.RGBtoHSB(red, green, blue, hsb);
		float t = hsb[2];
		hsb[2] = (float) ((t * 0.8) - (Math.pow(0.65 * t, 3) / (Math.log(0.8 * t + 10))));

		Color tempColorDarker = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);

		///// draws the border thicker string (black)
		GeneralPath shapeBorder = new GeneralPath();

		graphics2DCircleBorder.setColor(Color.BLACK);
		graphics2DCircleBorder.setStroke(strokeBorder);
		drawLine(shapeBorder, x, y, x2, y2);
		graphics2DCircleBorder.draw(shapeBorder);

		///// draws the colored string
		GeneralPath shape = new GeneralPath();

		graphics2DCircleBorder.setColor(tempColor);
		graphics2DCircleBorder.setStroke(stroke);

		// node, y1 is green node

		float x_tangent = ((float) stringWidth) / 2;
		float y_tangent = 0;
		if (paralelOrHorizo.nodeRed.x != paralelOrHorizo.nodeGreen.x) {
			float tangentOfParalelOrHorizo = (float) (Math
					.atan(((float) (paralelOrHorizo.nodeRed.y - y2)) / (paralelOrHorizo.nodeRed.x - x2)));
			x_tangent = (float) (0.5 * (stringWidth + recWidth) * Math.sin(tangentOfParalelOrHorizo));
			y_tangent = (float) (0.5 * (stringWidth + recWidth) * Math.cos(tangentOfParalelOrHorizo));
			System.out.println("x_tangent- " + x_tangent);
		}

		GradientPaint darkShade = new GradientPaint(paralelOrHorizo.nodeRed.x + x_tangent,
				paralelOrHorizo.nodeRed.y - y_tangent, tempColor, paralelOrHorizo.nodeRed.x - x_tangent,
				paralelOrHorizo.nodeRed.y + y_tangent, tempColorDarker);

		graphics2DCircleBorder.setPaint(darkShade);
		drawLine(shape, x, y, x2, y2);

		// draws the circle shaped line to imageCircleBorder
		graphics2DCircleBorder.draw(shape);

		x_tangent = ((float) stringWidth) / 2;
		y_tangent = 0;
		// changing tangent a little for making sure strings won't intersect
		if (paralelOrHorizo.nodeRed.x != paralelOrHorizo.nodeGreen.x) {
			float tangentOfParalelOrHorizo = (float) (Math
					.atan(((float) (paralelOrHorizo.nodeRed.y - y2)) / (paralelOrHorizo.nodeRed.x - x2)));
			x_tangent = (float) (0.5 * (stringWidth + recWidth) * Math.sin(tangentOfParalelOrHorizo));
			y_tangent = (float) (0.5 * (stringWidth + recWidth) * Math.cos(tangentOfParalelOrHorizo));
			System.out.println("x_tangent- " + x_tangent);
		}

		// rectangle edges
		BufferedImage imageRectangleBorder = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2DRecBorder = imageRectangleBorder.createGraphics();
		graphics2DRecBorder.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
				RenderingHints.VALUE_ANTIALIAS_ON);

		stroke = new BasicStroke(stringWidth, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0.1F);
		strokeBorder = new BasicStroke(stringWidth + 2 * recWidth, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL,
				0.1F);
		///// draws the thicker squared string
		graphics2DRecBorder.setColor(Color.BLACK);
		graphics2DRecBorder.setStroke(strokeBorder);
		graphics2DRecBorder.draw(shape);

		///// draws the colored string
		graphics2DRecBorder.setPaint(darkShade);
		// graphics2DRecBorder.setColor(tempColor);
		graphics2DRecBorder.setStroke(stroke);
		graphics2DRecBorder.draw(shape);

		// gets the bounds of the shape. When getting intersected strings it
		// will be more efficient to only search the bounds of the shape.
		// gets the bounds of the shape. When getting intersected strings it
		// will be more efficient to only search the bounds of the shape.

		GeneralPath borderOfShape = new GeneralPath();
		borderOfShape.moveTo(x + x_tangent, y - y_tangent);
		borderOfShape.lineTo(x - x_tangent, y + y_tangent);
		borderOfShape.lineTo(x2 - x_tangent, y2 + y_tangent);
		borderOfShape.lineTo(x2 + x_tangent, y2 - y_tangent);
		borderOfShape.lineTo(paralelOrHorizo.nodeRed.x + x_tangent, paralelOrHorizo.nodeRed.y - y_tangent);
		borderOfShape.closePath();

		if (isHorizo == true) {
			rectangleShapeHorizo[i] = shape.getBounds2D();
			horizoArea[i] = new Area(borderOfShape);
		}
		if (isHorizo == false) {
			rectangleShapeParalel[i] = shape.getBounds2D();
			paralelArea[i] = new Area(borderOfShape);
		}

		float middlex = ((float) (paralelOrHorizo.nodeRed.x + paralelOrHorizo.nodeGreen.x)) / 2;
		float middley = ((float) (paralelOrHorizo.nodeRed.y + paralelOrHorizo.nodeGreen.y)) / 2;

		imageRectangleBorder = CropCircleImage.cropInverse(imageRectangleBorder, (int) x2, (int) y2, stringWidth);

		CropImage.addImage(imageCircleBorder, imageRectangleBorder, 1, 0, 0);
		// writes down the three points of the line to log
		System.out.println("[" + isHorizo + "," + i + "," + x2 + "," + y2 + "]");
		try {
			ImageIO.write(imageRectangleBorder, "png", new File("c://temp//" + "imageCircleBorder" + i + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return imageCircleBorder;

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
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}

	public void setIsCrissWeave(boolean bool) {
		MyPrintCanvasTextDiagram.isCrissWeave = bool;
	}

	public void setIsPaintRectangle(boolean bool) {
		MyPrintCanvasTextDiagram.isPaintRectangle = bool;
	}

	public void setWidthOfStitch(int stngWidth, int recWdth) {
		stringWidth = stngWidth;
		recWidth = recWdth;
	}

	public static int getLengthOfStringEnd() {
		return MyPrintCanvasTextDiagram.lengthOfStringEnd;
	}

}
