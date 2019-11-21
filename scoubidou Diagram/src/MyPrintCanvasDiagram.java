
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Area;

import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import com.sun.javafx.scene.paint.GradientUtils.Point;

class MyPrintCanvasDiagram extends JComponent {
	// thickness of the lines
	public static int stringWidth = 50;
	// thickness of rectangle border
	public static int recWidth = 14;
	// x, a can be changeable
	public static int a = 1;
	// y, b can be changeable
	public static int b = 3;
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
	static int firstLineEndPoint = 7;
	int crissNumberOfLines =1;
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
		allPoints = listNodePoints.listOfNodePoints();
		nodeLine l = new nodeLine(allPoints[0], allPoints[firstLineEndPoint]);
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

	// method that creates all the images of strings.
	public void paint(Graphics g) {

		int k = 4 * a + 4 * b;
		int width = 2 * length * a;
		int hight = 2 * length * b;

		int widthPic = 2 * a * MyPrintCanvasDiagram.length + 2 * MyPrintCanvasDiagram.x1
				+ 2 * MyPrintCanvasDiagram.lengthOfStringEnd + 100;
		int heightPic = 2 * b * MyPrintCanvasDiagram.length + 2 * MyPrintCanvasDiagram.y1 + 100;

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
			allPoints = listNodePoints.listOfNodePoints();

			nodeLine l = new nodeLine(allPoints[0], allPoints[firstLineEndPoint]);

			nodeLine[] horizo = null;
			horizo = stitchAlgoForPrintStitch.paralelReturnOneOptionOposite(l, crissNumberOfLines);
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

			// if we need to paint Criss
			if (isCrissWeave == true) {
				BufferedImage[] horizoRepresentedLinesTemp = new BufferedImage[horizo.length];
				g2.setColor(firstColor);
				getAllStrings(horizoRepresentedLinesTemp, horizo, true);
				// creating a text file for horizo strings
				// positions//////////////////////////////////////////////////////////
				File fileTextHorizo = new File("c://temp//text_files//" + "horizo" + firstLineEndPoint + "_"
						+ crissNumberOfLines + "_" + a + "_" + b + ".txt");

				// Create the file
				try {
					if (fileTextHorizo.createNewFile()) {
						System.out.println("File is created!");
					} else {
						fileTextHorizo = new File("c://temp//text_files//" + "horizo" + firstLineEndPoint + "_"
								+ crissNumberOfLines + "_" + a + "_" + b + ".txt");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// Write Content
				PrintStream fileStreamHorizo;
				try {
					fileStreamHorizo = new PrintStream(fileTextHorizo);
					fileStreamHorizo.println("starting       |ending       ");

					for (int i = 0; i < horizo.length; i++) {
						fileStreamHorizo.println(horizoTop[i].nodeRed.x + " ," + horizoTop[i].nodeRed.y + " |"
								+ horizoTop[i].nodeGreen.x + " ," + horizoTop[i].nodeGreen.y);
					}
				}

				catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				// a plain text file with the same cotent as
				// above///////////////////////////////////////////////////////////////////////
				PrintWriter fileTextHorizoPlain = null;
				PrintWriter fileTextHorizoPlainDelta = null;
				try {
					fileTextHorizoPlain = new PrintWriter(new File("c://temp//text_files//" + "horizoPlain" + firstLineEndPoint + "_"
							+ crissNumberOfLines + "_" + a + "_" + b + ".csv"));
					fileTextHorizoPlainDelta = new PrintWriter(new File("c://temp//text_files//" + "horizoPlainDelta" + firstLineEndPoint + "_"
							+ crissNumberOfLines + "_" + a + "_" + b + ".csv"));
				} catch (FileNotFoundException e) {
				    e.printStackTrace();
				}
				StringBuilder builderHorizo = new StringBuilder();
				String ColumnNamesListHorizo = "nodes";
				
				StringBuilder builderHorizoDelta = new StringBuilder();
				String ColumnNamesListHorizoDelta = "nodes";
				// No need give the headers Like: id, Name on builder.append
				builderHorizo.append(ColumnNamesListHorizo +"\n");
				builderHorizoDelta.append(ColumnNamesListHorizoDelta +"\n");

				for (int i = 0; i < horizo.length; i++) {
					builderHorizo.append(horizoTop[i].nodeRed.x);					
					builderHorizo.append('\n');
					
					builderHorizoDelta.append(0);					
					builderHorizoDelta.append('\n');
					
					builderHorizo.append(horizoTop[i].nodeRed.y);					
					builderHorizo.append('\n');
					
					builderHorizoDelta.append(0);					
					builderHorizoDelta.append('\n');
					
					builderHorizo.append(horizoTop[i].nodeGreen.x);
					builderHorizo.append('\n');
												
					builderHorizo.append(horizoTop[i].nodeGreen.y);
					builderHorizo.append('\n');
					
				}
				fileTextHorizoPlain.write(builderHorizo.toString());
				fileTextHorizoPlain.close();
				fileTextHorizoPlainDelta.write(builderHorizoDelta.toString());
				fileTextHorizoPlainDelta.close();							
				System.out.println("crissNuberOfLines-" + crissNumberOfLines);

			}
			
			//////////////////////////////////////////////////////////////////////////////////////////////////////////

		

			// if we need to paint Cross
			if (isCrissWeave == false) {
				
				nodeLine[] paralel = new nodeLine[crossNumberOfLines];
				paralel = stitchAlgoForPrintStitch.paralelReturnOneOption(l, crossNumberOfLines);

				paralelBottom = new nodeLine[paralel.length];
				paralelTop = new nodeLine[paralel.length];

				paralelRepresentedLines = new BufferedImage[paralel.length];
				paralelRepresentedLinesBottom = new BufferedImage[paralel.length];
				rectangleShapeParalel = new Rectangle2D[paralel.length];
				paralelSegmentDown = new Line2D.Float[paralel.length];
				paralelSegmentUp = new Line2D.Float[paralel.length];
				paralelSegmentVerticle = new Line2D.Float[paralel.length];
				paralelArea = new Area[paralel.length];

				BufferedImage[] paralelRepresentedLinesTemp = new BufferedImage[paralel.length];

				g2.setColor(secondColor);
				getAllStrings(paralelRepresentedLinesTemp, paralel, false);

				// creating a text file for horizo strings
				// positions//////////////////////////////////////////////////////////
				File fileTextParalel = new File("c://temp//text_files//" + "paralel" + firstLineEndPoint + "_"
						+ crissNumberOfLines + "_" + a + "_" + b + ".txt");
				// Create the file
				try {
					if (fileTextParalel.createNewFile()) {
						System.out.println("File is created!");
					} else {
						fileTextParalel = new File("c://temp//text_files//" + "paralel" + firstLineEndPoint + "_"
								+ crissNumberOfLines + "_" + a + "_" + b + ".txt");
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// Write Content
				PrintStream fileStreamParalel;
				try {
					fileStreamParalel = new PrintStream(fileTextParalel);
					fileStreamParalel.println("starting       |ending       ");

					for (int i = 0; i < paralel.length; i++) {
						fileStreamParalel.println(paralelTop[i].nodeRed.x + " ," + paralelTop[i].nodeRed.y + " |"
								+ paralelTop[i].nodeGreen.x + " ," + paralelTop[i].nodeGreen.y);
					}
				}

				catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//////////////////////////////////////////////////////////////////////////////////////////////////////////
				// a plain text file with the same cotent as
				// above///////////////////////////////////////////////////////////////////////
				PrintWriter fileTextParalelPlain = null;
				PrintWriter fileTextParalelPlainDelta = null;

				try {
					fileTextParalelPlain = new PrintWriter(new File("c://temp//text_files//" + "paralelPlain" + firstLineEndPoint + "_"
							+ crissNumberOfLines + "_" + a + "_" + b + ".csv"));
					fileTextParalelPlainDelta = new PrintWriter(new File("c://temp//text_files//" + "paralelPlainDelta" + firstLineEndPoint + "_"
							+ crissNumberOfLines + "_" + a + "_" + b + ".csv"));
					
				} catch (FileNotFoundException e) {
				    e.printStackTrace();
				}
				StringBuilder builderParalel = new StringBuilder();
				String ColumnNamesListParalel = "nodes";
				
				StringBuilder builderParalelDelta = new StringBuilder();
				String ColumnNamesListParalelDelta = "nodes";
				// No need give the headers Like: id, Name on builder.append
				builderParalel.append(ColumnNamesListParalel +"\n");
				builderParalelDelta.append(ColumnNamesListParalelDelta +"\n");
				for (int i = 0; i < paralel.length; i++) {
					builderParalel.append(paralelTop[i].nodeRed.x);
					builderParalel.append('\n');
					
					builderParalelDelta.append(0);
					builderParalelDelta.append('\n');
					
					builderParalel.append(paralelTop[i].nodeRed.y);
					builderParalel.append('\n');
					
					builderParalelDelta.append(0);
					builderParalelDelta.append('\n');
					
					builderParalel.append(paralelTop[i].nodeGreen.x);
					builderParalel.append('\n');
					
				
					
					builderParalel.append(paralelTop[i].nodeGreen.y);
					builderParalel.append('\n');
					
				
				}
				fileTextParalelPlain.write(builderParalel.toString());
				fileTextParalelPlain.close();
				
				fileTextParalelPlainDelta.write(builderParalelDelta.toString());
				fileTextParalelPlainDelta.close();
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

	public BufferedImage drawShapeLine(boolean isHorizo, nodeLine paralelOrHorizo, BasicStroke strokeBorder,
			BasicStroke stroke, int stringWidth, int i, Color[] colors, nodePoint[] lastPoints) {
		int width = 2 * a * MyPrintCanvasDiagram.length + 2 * MyPrintCanvasDiagram.x1
				+ 2 * MyPrintCanvasDiagram.lengthOfStringEnd + 100;
		int height = 2 * b * MyPrintCanvasDiagram.length + 2 * MyPrintCanvasDiagram.y1 + 100;

		// recieving the color of the string
		int indexParalelOrHorizo = listNodePoints.findNodePointIndex(paralelOrHorizo.nodeRed);

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
		float x = hsb[2];
		hsb[2] = (float) ((x * 0.8) - (Math.pow(0.65 * x, 3) / (Math.log(0.8 * x + 10))));

		Color tempColorDarker = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);

		///// draws the border thicker string (black)
		GeneralPath shapeBorder = new GeneralPath();
		shapeBorder.moveTo(paralelOrHorizo.nodeRed.x, paralelOrHorizo.nodeRed.y);
		graphics2DCircleBorder.setColor(Color.BLACK);
		graphics2DCircleBorder.setStroke(strokeBorder);
		DrawingStrings.drawThickShapeLineWithContinuation(shapeBorder, paralelOrHorizo.nodeRed.x,
				paralelOrHorizo.nodeRed.y, paralelOrHorizo.nodeGreen.x, paralelOrHorizo.nodeGreen.y,
				stringWidth + 2 * recWidth, a, b);
		graphics2DCircleBorder.draw(shapeBorder);

		///// getting the last point of the string
		int[] lastPoint = new int[2];
		lastPoint = DrawingStrings.getDrawThickShapeLineWithContinuation(paralelOrHorizo.nodeRed.x,
				paralelOrHorizo.nodeRed.y, paralelOrHorizo.nodeGreen.x, paralelOrHorizo.nodeGreen.y, a, b);

		lastPoints[i] = new nodePoint(lastPoint[0], lastPoint[1], colors[i]);

		///// draws the colored string
		GeneralPath shape = new GeneralPath();
		shape.moveTo(paralelOrHorizo.nodeRed.x, paralelOrHorizo.nodeRed.y);
		graphics2DCircleBorder.setColor(tempColor);
		graphics2DCircleBorder.setStroke(stroke);
	
		// node, y1 is green node

		float x_tangent = ((float) stringWidth) / 2;
		float y_tangent = 0;
		if (paralelOrHorizo.nodeRed.x != paralelOrHorizo.nodeGreen.x) {
			float tangentOfParalelOrHorizo = (float) (Math.atan(
					((float) (paralelOrHorizo.nodeRed.y - lastPoint[1])) / (paralelOrHorizo.nodeRed.x - lastPoint[0])));
			x_tangent = (float) (0.5 * (stringWidth + recWidth) * Math.sin(tangentOfParalelOrHorizo));
			y_tangent = (float) (0.5 * (stringWidth + recWidth) * Math.cos(tangentOfParalelOrHorizo));
			System.out.println("x_tangent- " + x_tangent);
		}

		GradientPaint darkShade = new GradientPaint(paralelOrHorizo.nodeRed.x + x_tangent,
				paralelOrHorizo.nodeRed.y - y_tangent, tempColor, paralelOrHorizo.nodeRed.x - x_tangent,
				paralelOrHorizo.nodeRed.y + y_tangent, tempColorDarker);

		graphics2DCircleBorder.setPaint(darkShade);
		DrawingStrings.drawThickShapeLineWithContinuation(shape, paralelOrHorizo.nodeRed.x, paralelOrHorizo.nodeRed.y,
				paralelOrHorizo.nodeGreen.x, paralelOrHorizo.nodeGreen.y, stringWidth, a, b);
		// draws the circle shaped line to imageCircleBorder
		graphics2DCircleBorder.draw(shape);
		
	
		if(isHorizo == true){
			//nodePoint lastNode = new nodePoint(lastPoint[0], lastPoint[1], Color.GREEN);
			horizoTop[i] = new nodeLine(paralelOrHorizo.nodeRed,lastPoints[i]);
		}
		if(isHorizo == false){
			//nodePoint lastNode = new nodePoint(lastPoint[0], lastPoint[1], Color.GREEN);
			paralelTop[i] = new nodeLine(paralelOrHorizo.nodeRed,lastPoints[i]);	
			}
		
		
		
		x_tangent = ((float) stringWidth) / 2;
		y_tangent = 0;
		// changing tangent a little for making sure strings won't intersect
		if (paralelOrHorizo.nodeRed.x != paralelOrHorizo.nodeGreen.x) {
			float tangentOfParalelOrHorizo = (float) (Math.atan(
					((float) (paralelOrHorizo.nodeRed.y - lastPoint[1])) / (paralelOrHorizo.nodeRed.x - lastPoint[0])));
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
		borderOfShape.moveTo(paralelOrHorizo.nodeRed.x + x_tangent, paralelOrHorizo.nodeRed.y - y_tangent);
		borderOfShape.lineTo(paralelOrHorizo.nodeRed.x - x_tangent, paralelOrHorizo.nodeRed.y + y_tangent);
		borderOfShape.lineTo(lastPoint[0] - x_tangent, lastPoint[1] + y_tangent);
		borderOfShape.lineTo(lastPoint[0] + x_tangent, lastPoint[1] - y_tangent);
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

		imageRectangleBorder = CropCircleImage.cropInverse(imageRectangleBorder, lastPoint[0], lastPoint[1],
				stringWidth);

		CropImage.addImage(imageCircleBorder, imageRectangleBorder, 1, 0, 0);
		// writes down the three points of the line to log
		System.out.println("[" + isHorizo + "," + i + "," + lastPoint[0] + "," + lastPoint[1] + "]");

		return imageCircleBorder;

	}

	/**
	 * 
	 * @param isHorizo
	 * @param firstPoint
	 * @param secondPoint
	 * @param strokeBorder
	 * @param stroke
	 * @param stringWidth
	 * @param i
	 * @param colors
	 * @return draws string with backweaving
	 */

	public BufferedImage drawShapeLineBackWeave(boolean isHorizo, nodePoint paralelOrHorizo, nodePoint firstPoint,
			nodePoint secondPoint, BasicStroke strokeBorder, BasicStroke stroke, int stringWidth, int i, Color[] colors,
			int backWeaveSecondPointx, int backWeaveSecondPointy, nodePoint[] lastPoints) {
		int width = 2 * a * MyPrintCanvasDiagram.length + 2 * MyPrintCanvasDiagram.x1
				+ 2 * MyPrintCanvasDiagram.lengthOfStringEnd + 100;
		int height = 2 * b * MyPrintCanvasDiagram.length + 2 * MyPrintCanvasDiagram.y1 + 100;

		// recieving the color of the string
		int indexParalelOrHorizo = listNodePoints.findNodePointIndex(paralelOrHorizo);

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
		float x = hsb[2];
		hsb[2] = (float) ((x * 0.8) - (Math.pow(0.65 * x, 3) / (Math.log(0.8 * x + 10))));

		Color tempColorDarker = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);

		///// getting the last point of the string
		int[] lastPoint = new int[2];
		lastPoint = DrawingStrings.getDrawThickShapeLineWithContinuationBackWeaving(firstPoint.x, firstPoint.y,
				secondPoint.x, secondPoint.y, stringWidth, backWeaveSecondPointx, backWeaveSecondPointy, a, b);

		lastPoints[i] = new nodePoint(lastPoint[0], lastPoint[1], colors[i]);

		///// draws the border thicker string (black)
		GeneralPath shapeBorder = new GeneralPath();
		shapeBorder.moveTo(firstPoint.x, firstPoint.y);
		graphics2DCircleBorder.setColor(Color.BLACK);
		graphics2DCircleBorder.setStroke(strokeBorder);

		System.out.println("firstPoint- (" + firstPoint.x + "," + firstPoint.y + ")" + " secondPoint-" + secondPoint
				+ "(" + secondPoint.x + "," + secondPoint.y + ")");
		DrawingStrings.drawThickShapeLineWithContinuationBackWeaving(shapeBorder, firstPoint.x, firstPoint.y,
				secondPoint.x, secondPoint.y, stringWidth + 2 * recWidth, backWeaveSecondPointx, backWeaveSecondPointy,
				a, b);
		graphics2DCircleBorder.draw(shapeBorder);

		///// draws the colored string
		GeneralPath shape = new GeneralPath();
		shape.moveTo(firstPoint.x, firstPoint.y);
		graphics2DCircleBorder.setColor(tempColor);
		graphics2DCircleBorder.setStroke(stroke);

		float x_tangent = ((float) stringWidth) / 2;
		float y_tangent = 0;
		if (firstPoint.x != secondPoint.x) {
			float tangentOfParalelOrHorizo = (float) (Math
					.atan(((float) (firstPoint.y - lastPoint[1])) / (firstPoint.x - lastPoint[0])));
			x_tangent = (float) (0.5 * stringWidth * Math.sin(tangentOfParalelOrHorizo));
			y_tangent = (float) (0.5 * stringWidth * Math.cos(tangentOfParalelOrHorizo));
		}

		GradientPaint darkShade = new GradientPaint(firstPoint.x + x_tangent, firstPoint.y - y_tangent, tempColor,
				firstPoint.x - x_tangent, firstPoint.y + y_tangent, tempColorDarker);

		graphics2DCircleBorder.setPaint(darkShade);
		DrawingStrings.drawThickShapeLineWithContinuationBackWeaving(shape, firstPoint.x, firstPoint.y, secondPoint.x,
				secondPoint.y, stringWidth, backWeaveSecondPointx, backWeaveSecondPointy, a, b);
		// draws the circle shaped line to imageCircleBorder
		graphics2DCircleBorder.draw(shape);

		if(isHorizo == true){
			//nodePoint lastNode = new nodePoint(lastPoint[0], lastPoint[1], Color.GREEN);
			horizoTop[i] = new nodeLine(firstPoint,lastPoints[i]);
		}
		if(isHorizo == false){
			//nodePoint lastNode = new nodePoint(lastPoint[0], lastPoint[1], Color.GREEN);
			paralelTop[i] = new nodeLine(firstPoint,lastPoints[i]);	
			}
		
		x_tangent = ((float) stringWidth) / 2 + recWidth;
		y_tangent = 0;
		// changing tangent a little for making sure strings won't intersect
		if (firstPoint.x != secondPoint.x) {
			float tangentOfParalelOrHorizo = (float) (Math
					.atan(((float) (firstPoint.y - lastPoint[1])) / (firstPoint.x - lastPoint[0])));
			x_tangent = (float) (0.5 * x_tangent * Math.sin(tangentOfParalelOrHorizo));
			y_tangent = (float) (0.5 * y_tangent * Math.cos(tangentOfParalelOrHorizo));
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
		GeneralPath borderOfShape = new GeneralPath();
		borderOfShape.moveTo(firstPoint.x + x_tangent, firstPoint.y - y_tangent);
		borderOfShape.lineTo(firstPoint.x - x_tangent, firstPoint.y + y_tangent);
		borderOfShape.lineTo(lastPoint[0] - x_tangent, lastPoint[1] + y_tangent);
		borderOfShape.lineTo(lastPoint[0] + x_tangent, lastPoint[1] - y_tangent);
		borderOfShape.lineTo(firstPoint.x + x_tangent, firstPoint.y - y_tangent);
		borderOfShape.closePath();

		if (isHorizo == true) {
			rectangleShapeHorizo[i] = shape.getBounds2D();
			horizoArea[i] = new Area(borderOfShape);

		}
		if (isHorizo == false) {
			rectangleShapeParalel[i] = shape.getBounds2D();
			paralelArea[i] = new Area(borderOfShape);

		}
		float middlex = ((float) (firstPoint.x + secondPoint.x)) / 2;
		float middley = ((float) (firstPoint.y + secondPoint.y)) / 2;

		imageRectangleBorder = CropCircleImage.cropInverse(imageRectangleBorder, lastPoint[0], lastPoint[1],
				stringWidth);

		CropImage.addImage(imageCircleBorder, imageRectangleBorder, 1, 0, 0);
		// writes down the three points of the line to log
		System.out.println("[" + isHorizo + "," + i + "," + firstPoint.x + "," + firstPoint.y + "," + middlex + ","
				+ middley + "," + lastPoint[0] + "," + lastPoint[1] + "]");

		return imageCircleBorder;

	}

	/**
	 * 
	 * @param isHorizo
	 * @param paralelOrHorizo
	 * @param firstPoint
	 * @param firstPoint_neighbor
	 * @param secondPoint_neighbor
	 * @param strokeBorder
	 * @param stroke
	 * @param stringWidth
	 * @param i
	 * @param colors
	 * @param backWeaveFirstPointx
	 * @param backWeaveFirstPointy
	 * @param backWeaveSecondPointx
	 * @param backWeaveSecondPointy
	 * @return
	 */
	public BufferedImage drawShapeLineBackWeaveParalel(boolean isHorizo, nodeLine paralelOrHorizo, nodePoint firstPoint,
			nodePoint secondPoint, nodePoint firstPoint_neighbor, nodePoint secondPoint_neighbor,
			BasicStroke strokeBorder, BasicStroke stroke, int stringWidth, int i, Color[] colors,
			int backWeaveSecondPointx, int backWeaveSecondPointy, nodePoint[] lastPoints) {
		int width = 2 * a * MyPrintCanvasDiagram.length + 2 * MyPrintCanvasDiagram.x1
				+ 2 * MyPrintCanvasDiagram.lengthOfStringEnd + 100;
		int height = 2 * b * MyPrintCanvasDiagram.length + 2 * MyPrintCanvasDiagram.y1 + 100;

		// recieving the color of the string
		int indexParalelOrHorizo = listNodePoints.findNodePointIndex(paralelOrHorizo.nodeRed);

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
		float x = hsb[2];
		hsb[2] = (float) ((x * 0.8) - (Math.pow(0.65 * x, 3) / (Math.log(0.8 * x + 10))));

		Color tempColorDarker = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);

		///// getting the last point of the string
		int[] lastPoint = new int[2];

		lastPoint = DrawingStrings.getThickShapeParalelToNeighbor(paralelOrHorizo, firstPoint.x, firstPoint.y,
				secondPoint.x, secondPoint.y, firstPoint_neighbor.x, firstPoint_neighbor.y, secondPoint_neighbor.x,
				secondPoint_neighbor.y, stringWidth + 2 * recWidth, backWeaveSecondPointx, backWeaveSecondPointy, a, b);

		lastPoints[i] = new nodePoint(lastPoint[0], lastPoint[1], colors[i]);
		System.out.println("[Cross," + i + "," + lastPoint[0] + "," + lastPoint[1] + "]");
		///// draws the border thicker string (black)
		GeneralPath shapeBorder = new GeneralPath();
		shapeBorder.moveTo(firstPoint.x, firstPoint.y);
		graphics2DCircleBorder.setColor(Color.BLACK);
		graphics2DCircleBorder.setStroke(strokeBorder);

		// drawing with width 2*recWidth
		DrawingStrings.drawThickShapeParalelToNeighbor(shapeBorder, paralelOrHorizo, firstPoint.x, firstPoint.y,
				secondPoint.x, secondPoint.y, firstPoint_neighbor.x, firstPoint_neighbor.y, secondPoint_neighbor.x,
				secondPoint_neighbor.y, stringWidth + 2 * recWidth, backWeaveSecondPointx, backWeaveSecondPointy, a, b);
		graphics2DCircleBorder.draw(shapeBorder);

		///// draws the colored string
		GeneralPath shape = new GeneralPath();
		shape.moveTo(firstPoint.x, firstPoint.y);
		graphics2DCircleBorder.setColor(tempColor);
		graphics2DCircleBorder.setStroke(stroke);

		float x_tangent = ((float) stringWidth) / 2;
		float y_tangent = 0;
		if (firstPoint.x != lastPoint[0]) {
			float tangentOfParalelOrHorizo = (float) (Math
					.atan(((float) (firstPoint.y - lastPoint[1])) / (firstPoint.x - lastPoint[0])));
			x_tangent = (float) (0.5 * stringWidth * Math.sin(tangentOfParalelOrHorizo));
			y_tangent = (float) (0.5 * stringWidth * Math.cos(tangentOfParalelOrHorizo));
		}

		GradientPaint darkShade = new GradientPaint(firstPoint.x + x_tangent, firstPoint.y - y_tangent, tempColor,
				firstPoint.x - x_tangent, firstPoint.y + y_tangent, tempColorDarker);

		graphics2DCircleBorder.setPaint(darkShade);
		DrawingStrings.drawThickShapeParalelToNeighbor(shape, paralelOrHorizo, firstPoint.x, firstPoint.y,
				secondPoint.x, secondPoint.y, firstPoint_neighbor.x, firstPoint_neighbor.y, secondPoint_neighbor.x,
				secondPoint_neighbor.y, stringWidth + recWidth, backWeaveSecondPointx, backWeaveSecondPointy, a, b);

		// draws the circle shaped line to imageCircleBorder
		graphics2DCircleBorder.draw(shape);
		
		// updating horizo/paralel top 
		if(isHorizo == true){
			horizoTop[i] = new nodeLine(firstPoint,lastPoints[i]);
		}
		if(isHorizo == false){
			paralelTop[i] = new nodeLine(firstPoint,lastPoints[i]);
		}
		
		x_tangent = ((float) stringWidth) / 2 + recWidth;
		y_tangent = 0;
		// changing tangent a little for making sure strings won't intersect
		if (firstPoint.x != lastPoint[0]) {
			float tangentOfParalelOrHorizo = (float) (Math
					.atan(((float) (firstPoint.y - lastPoint[1])) / (firstPoint.x - lastPoint[0])));
			x_tangent = (float) (0.5 * x_tangent * Math.sin(tangentOfParalelOrHorizo));
			y_tangent = (float) (0.5 * y_tangent * Math.cos(tangentOfParalelOrHorizo));
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
		GeneralPath borderOfShape = new GeneralPath();
		borderOfShape.moveTo(firstPoint.x + x_tangent, firstPoint.y - y_tangent);
		borderOfShape.lineTo(firstPoint.x - x_tangent, firstPoint.y + y_tangent);
		borderOfShape.lineTo(lastPoint[0] - x_tangent, lastPoint[1] + y_tangent);
		borderOfShape.lineTo(lastPoint[0] + x_tangent, lastPoint[1] - y_tangent);
		borderOfShape.lineTo(firstPoint.x + x_tangent, firstPoint.y - y_tangent);
		borderOfShape.closePath();

		if (isHorizo == true) {
			rectangleShapeHorizo[i] = shape.getBounds2D();
			horizoArea[i] = new Area(borderOfShape);

		}
		if (isHorizo == false) {
			rectangleShapeParalel[i] = shape.getBounds2D();
			paralelArea[i] = new Area(borderOfShape);

		}
		float middlex = ((float) (firstPoint.x + lastPoint[0])) / 2;
		float middley = ((float) (firstPoint.y + lastPoint[1])) / 2;

		imageRectangleBorder = CropCircleImage.cropInverse(imageRectangleBorder, lastPoint[0], lastPoint[1],
				stringWidth);

		CropImage.addImage(imageCircleBorder, imageRectangleBorder, 1, 0, 0);
		// writes down the three points of the line to log
		System.out.println("[Cross," + i + "," + "" + firstPoint.x + "," + firstPoint.y + "," + middlex + "," + middley
				+ "," + lastPoint[0] + "," + lastPoint[1] + "]");

		return imageCircleBorder;

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

	public BufferedImage drawShapeLineSimple(boolean isHorizo, nodeLine paralelOrHorizo, nodeLine paralelOrHorizoToDraw,
			BasicStroke strokeBorder, BasicStroke stroke, int stringWidth, int i, Color[] colors,
			nodePoint[] lastPoints) {
		int width = 2 * a * MyPrintCanvasDiagram.length + 2 * MyPrintCanvasDiagram.x1
				+ 2 * MyPrintCanvasDiagram.lengthOfStringEnd + 100;
		int height = 2 * b * MyPrintCanvasDiagram.length + 2 * MyPrintCanvasDiagram.y1 + 100;

		// recieving the color of the string
		int indexParalelOrHorizo = listNodePoints.findNodePointIndex(paralelOrHorizo.nodeRed);

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
		float x = hsb[2];
		hsb[2] = (float) ((x * 0.8) - (Math.pow(0.65 * x, 3) / (Math.log(0.8 * x + 10))));

		Color tempColorDarker = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);

		///// draws the border thicker string (black)
		GeneralPath shapeBorder = new GeneralPath();
		shapeBorder.moveTo(paralelOrHorizoToDraw.nodeRed.x, paralelOrHorizoToDraw.nodeRed.y);
		shapeBorder.lineTo(paralelOrHorizoToDraw.nodeGreen.x, paralelOrHorizoToDraw.nodeGreen.y);

		graphics2DCircleBorder.setColor(Color.BLACK);
		graphics2DCircleBorder.setStroke(strokeBorder);
		graphics2DCircleBorder.draw(shapeBorder);

		///// getting the last point of the string
		int[] lastPoint = new int[2];
		lastPoint[0] = (int) paralelOrHorizoToDraw.nodeGreen.x;
		lastPoint[1] = (int) paralelOrHorizoToDraw.nodeGreen.y;

		///// draws the colored string
		GeneralPath shape = new GeneralPath();
		shape.moveTo(paralelOrHorizoToDraw.nodeRed.x, paralelOrHorizoToDraw.nodeRed.y);
		shape.lineTo(paralelOrHorizoToDraw.nodeGreen.x, paralelOrHorizoToDraw.nodeGreen.y);

		graphics2DCircleBorder.setColor(tempColor);
		graphics2DCircleBorder.setStroke(stroke);

		// node, y1 is green node
		float x_tangent = ((float) stringWidth) / 2;
		float y_tangent = 0;
		if (paralelOrHorizoToDraw.nodeRed.x != paralelOrHorizoToDraw.nodeGreen.x) {
			float tangentOfParalelOrHorizo = (float) (Math
					.atan(((float) (paralelOrHorizoToDraw.nodeRed.y - lastPoint[1]))
							/ (paralelOrHorizoToDraw.nodeRed.x - lastPoint[0])));
			x_tangent = (float) (0.5 * (stringWidth + recWidth) * Math.sin(tangentOfParalelOrHorizo));
			y_tangent = (float) (0.5 * (stringWidth + recWidth) * Math.cos(tangentOfParalelOrHorizo));
		}

		GradientPaint darkShade = new GradientPaint(paralelOrHorizoToDraw.nodeRed.x + x_tangent,
				paralelOrHorizoToDraw.nodeRed.y - y_tangent, tempColor, paralelOrHorizoToDraw.nodeRed.x - x_tangent,
				paralelOrHorizoToDraw.nodeRed.y + y_tangent, tempColorDarker);

		graphics2DCircleBorder.setPaint(darkShade);
		DrawingStrings.drawThickShapeLineWithContinuation(shape, paralelOrHorizoToDraw.nodeRed.x,
				paralelOrHorizoToDraw.nodeRed.y, paralelOrHorizoToDraw.nodeGreen.x, paralelOrHorizoToDraw.nodeGreen.y,
				stringWidth, a, b);
		// draws the circle shaped line to imageCircleBorder
		graphics2DCircleBorder.draw(shape);

		x_tangent = ((float) stringWidth) / 2;
		y_tangent = 0;
		// changing tangent a little for making sure strings won't intersect
		if (paralelOrHorizoToDraw.nodeRed.x != paralelOrHorizoToDraw.nodeGreen.x) {
			float tangentOfParalelOrHorizo = (float) (Math
					.atan(((float) (paralelOrHorizoToDraw.nodeRed.y - lastPoint[1]))
							/ (paralelOrHorizoToDraw.nodeRed.x - lastPoint[0])));
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
		borderOfShape.moveTo(paralelOrHorizoToDraw.nodeRed.x + x_tangent, paralelOrHorizoToDraw.nodeRed.y - y_tangent);
		borderOfShape.lineTo(paralelOrHorizoToDraw.nodeRed.x - x_tangent, paralelOrHorizoToDraw.nodeRed.y + y_tangent);
		borderOfShape.lineTo(lastPoint[0] - x_tangent, lastPoint[1] + y_tangent);
		borderOfShape.lineTo(lastPoint[0] + x_tangent, lastPoint[1] - y_tangent);
		borderOfShape.lineTo(paralelOrHorizoToDraw.nodeRed.x + x_tangent, paralelOrHorizoToDraw.nodeRed.y - y_tangent);
		borderOfShape.closePath();

		if (isHorizo == true) {
			rectangleShapeHorizo[i] = shape.getBounds2D();
			horizoArea[i] = new Area(borderOfShape);

		}
		if (isHorizo == false) {
			rectangleShapeParalel[i] = shape.getBounds2D();
			paralelArea[i] = new Area(borderOfShape);
		}
		imageRectangleBorder = CropCircleImage.cropInverse(imageRectangleBorder, lastPoint[0], lastPoint[1],
				stringWidth);

		CropImage.addImage(imageCircleBorder, imageRectangleBorder, 1, 0, 0);
		// writes down the three points of the line to log
		System.out.println("[" + isHorizo + "," + i + "," + lastPoint[0] + "," + lastPoint[1] + "]");

		return imageCircleBorder;
	}

	/**
	 *
	 * @param secondPoint
	 * @param paralelOrHorizo
	 * @param strokeBorder
	 * @param stroke
	 * @param stringWidth
	 * @return the buffered image for the bottom string when back-weaving
	 */

	public BufferedImage drawShapeLineSimpleBottom(nodePoint secondPoint, nodeLine paralelOrHorizo,
			BasicStroke strokeBorder, BasicStroke stroke, int stringWidth, boolean isExisted) {
		int width = 2 * a * MyPrintCanvasDiagram.length + 2 * MyPrintCanvasDiagram.x1
				+ 2 * MyPrintCanvasDiagram.lengthOfStringEnd + 100;
		int height = 2 * b * MyPrintCanvasDiagram.length + 2 * MyPrintCanvasDiagram.y1 + 100;

		// rectangle edges
		BufferedImage imageRectangleBorder = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2DRecBorder = imageRectangleBorder.createGraphics();
		graphics2DRecBorder.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
				RenderingHints.VALUE_ANTIALIAS_ON);

		if (isExisted == false) {
			return imageRectangleBorder;
		} else {
			// recieving the color of the string
			int indexParalelOrHorizo = listNodePoints.findNodePointIndex(paralelOrHorizo.nodeRed);
			Color tempColor = colors[indexParalelOrHorizo];
			///// getting a darker color
			int red = tempColor.getRed();
			int green = tempColor.getGreen();
			int blue = tempColor.getBlue();
			float[] hsb = new float[3];
			Color.RGBtoHSB(red, green, blue, hsb);
			float x = hsb[2];
			hsb[2] = (float) ((x * 0.8) - (Math.pow(0.65 * x, 3) / (Math.log(0.8 * x + 10))));

			Color tempColorDarker = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);

			///// getting the last point of the string
			int[] lastPoint = new int[2];
			lastPoint[0] = (int) secondPoint.x;
			lastPoint[1] = (int) secondPoint.y;

			///// draws the colored string
			GeneralPath shape = new GeneralPath();
			shape.moveTo(paralelOrHorizo.nodeRed.x, paralelOrHorizo.nodeRed.y);
			shape.lineTo(secondPoint.x, secondPoint.y);

			// node, y1 is green node
			float x_tangent = ((float) stringWidth) / 2;
			float y_tangent = 0;
			if (secondPoint.x != paralelOrHorizo.nodeRed.x) {
				float tangentOfParalelOrHorizo = (float) (Math.atan(((float) (paralelOrHorizo.nodeRed.y - lastPoint[1]))
						/ (paralelOrHorizo.nodeRed.x - lastPoint[0])));
				x_tangent = (float) (0.5 * (stringWidth + recWidth) * Math.sin(tangentOfParalelOrHorizo));
				y_tangent = (float) (0.5 * (stringWidth + recWidth) * Math.cos(tangentOfParalelOrHorizo));
			}

			GradientPaint darkShade = new GradientPaint(paralelOrHorizo.nodeRed.x + x_tangent,
					paralelOrHorizo.nodeRed.y - y_tangent, tempColor, paralelOrHorizo.nodeRed.x - x_tangent,
					paralelOrHorizo.nodeRed.y + y_tangent, tempColorDarker);

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

			// circle edges
			stroke = new BasicStroke(stringWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 0.1F);
			strokeBorder = new BasicStroke(stringWidth + 2 * recWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL,
					0.1F);
			BufferedImage imageCircleBorder = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D graphics2DCircleBorder = imageCircleBorder.createGraphics();
			graphics2DCircleBorder.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
					RenderingHints.VALUE_ANTIALIAS_ON);

			graphics2DCircleBorder.setColor(Color.BLACK);
			graphics2DCircleBorder.setStroke(strokeBorder);
			graphics2DCircleBorder.draw(shape);

			graphics2DCircleBorder.setPaint(darkShade);
			// graphics2DCircleBorder.setColor(tempColor);
			graphics2DCircleBorder.setStroke(stroke);
			graphics2DCircleBorder.draw(shape);
	
			return imageCircleBorder;
		}
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
		MyPrintCanvasDiagram.isCrissWeave = bool;
	}

	public void setIsPaintRectangle(boolean bool) {
		MyPrintCanvasDiagram.isPaintRectangle = bool;
	}

	public void setWidthOfStitch(int stngWidth, int recWdth) {
		stringWidth = stngWidth;
		recWidth = recWdth;
	}

	public static int getLengthOfStringEnd() {
		return MyPrintCanvasDiagram.lengthOfStringEnd;
	}

	/**
	 * gets all woven strings to representedLine
	 * 
	 * @param horizoRepresentedLinesTemp
	 * @param horizoParalel
	 * @param isHorizo
	 */
	public void getAllStrings(BufferedImage[] horizoRepresentedLinesTemp, nodeLine[] horizoParalel, boolean isHorizo) {

		int width = 2 * length * a;
		int hight = 2 * length * b;

		int widthPic = 2 * a * MyPrintCanvasDiagram.length + 2 * MyPrintCanvasDiagram.x1
				+ 2 * MyPrintCanvasDiagram.lengthOfStringEnd + 100;
		int heightPic = 2 * b * MyPrintCanvasDiagram.length + 2 * MyPrintCanvasDiagram.y1 + 100;

		BasicStroke stroke = new BasicStroke(stringWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 0.1F);
		BasicStroke strokeBorder = new BasicStroke(stringWidth + 2 * recWidth, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL, 0.1F);

		int i = horizoParalel.length / 2 - 1;
		Rectangle2D[] rectangleShapeHorizoParalel = new Rectangle2D[horizoParalel.length];
		nodePoint[] firstPoints = new nodePoint[horizoParalel.length];
		nodePoint[] lastPoints = new nodePoint[horizoParalel.length];

		horizoRepresentedLinesTemp[i] = drawShapeLine(isHorizo, horizoParalel[i], strokeBorder, stroke, stringWidth, i,
				colors, lastPoints);
		horizoRepresentedLinesTemp[i + 1] = drawShapeLine(isHorizo, horizoParalel[i + 1], strokeBorder, stroke,
				stringWidth, i + 1, colors, lastPoints);
		if (isHorizo == true) {
			rectangleShapeHorizoParalel[i] = rectangleShapeHorizo[i];
	
		}

		if (isHorizo == false) {
			rectangleShapeHorizoParalel[i] = rectangleShapeParalel[i];
		
		}

		// if it is a striaght stitch the might deserve back weaving
		if ((horizoParalel[i].nodeGreen.x == horizoParalel[i].nodeRed.x)
				|| (horizoParalel[i].nodeGreen.y == horizoParalel[i].nodeRed.y)) {
			straightStitch(horizoRepresentedLinesTemp, horizoParalel, isHorizo);
		
			return;
		}

		// gets bounds of the area to check if intersecting
		int backWeaveFirstPointx = 0;
		int backWeaveFirstPointy = 0;

		// at the beginning first and second points are:
		nodePoint firstPoint = new nodePoint(horizoParalel[i].nodeRed.x, horizoParalel[i].nodeRed.y, colors[i]);
		nodePoint firstOpposite = new nodePoint(horizoParalel[i + 1].nodeRed.x, horizoParalel[i + 1].nodeRed.y,
				colors[i + 1]);
		nodePoint secondPoint = new nodePoint(horizoParalel[i].nodeGreen.x, horizoParalel[i].nodeGreen.y, colors[i]);
		nodePoint secondOpposite = new nodePoint(horizoParalel[i + 1].nodeGreen.x, horizoParalel[i + 1].nodeGreen.y,
				colors[i + 1]);

		firstPoints[i] = firstPoint;
		firstPoints[i + 1] = firstOpposite;
		// sending images to gif
		if (isGif = true) {
			System.out.println("is gif is entered");
			BufferedImage gifImage = new BufferedImage(widthPic, heightPic, BufferedImage.TYPE_INT_ARGB);
			Graphics2D gif = gifImage.createGraphics();
			gif.drawImage(horizoRepresentedLinesTemp[i], 0, 0, null);
			gif.drawImage(horizoRepresentedLinesTemp[i + 1], 0, 0, null);
			try {
				ImageIO.write(gifImage, "png", new File("c://temp//gif//" + 100 + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			counterGif++;
		}

		// going first through the middle strings
		while (CropImage.isIntersectNew(horizoRepresentedLinesTemp[i], horizoRepresentedLinesTemp[i + 1], x1, y1, width,
				hight) == true) {

			System.out.println("middle: entered first while index i first loop- " + i);

			int backWeaveSecondPointx = 0;
			int backWeaveSecondPointy = 0;

			while (CropImage.isIntersectNew(horizoRepresentedLinesTemp[i], horizoRepresentedLinesTemp[i + 1], x1, y1,
					width, hight) == true) {
				System.out.println("middle: entered second while");

				secondPoint = null;
				secondOpposite = null;

				if (horizoParalel[i].nodeGreen.x == x1) {
					backWeaveSecondPointx -= 8;
					System.out.println("1 second " + backWeaveSecondPointx);
					secondPoint = new nodePoint(horizoParalel[i].nodeGreen.x + backWeaveSecondPointx,
							horizoParalel[i].nodeGreen.y, colors[i]);
					secondOpposite = new nodePoint(horizoParalel[i + 1].nodeGreen.x - backWeaveSecondPointx,
							horizoParalel[i + 1].nodeGreen.y, colors[i + 1]);

				}
				if (horizoParalel[i].nodeGreen.x == x1 + width) {
					backWeaveSecondPointx += 8;
					System.out.println("2 second " + backWeaveSecondPointx);
					secondPoint = new nodePoint(horizoParalel[i].nodeGreen.x + backWeaveSecondPointx,
							horizoParalel[i].nodeGreen.y, colors[i]);
					secondOpposite = new nodePoint(horizoParalel[i + 1].nodeGreen.x - backWeaveSecondPointx,
							horizoParalel[i + 1].nodeGreen.y, colors[i + 1]);

				}
				if (horizoParalel[i].nodeGreen.y == y1) {
					backWeaveSecondPointy -= 8;
					System.out.println("3 second " + backWeaveSecondPointy);
					secondPoint = new nodePoint(horizoParalel[i].nodeGreen.x,
							horizoParalel[i].nodeGreen.y + backWeaveSecondPointy, colors[i]);
					secondOpposite = new nodePoint(horizoParalel[i + 1].nodeGreen.x,
							horizoParalel[i + 1].nodeGreen.y - backWeaveSecondPointy, colors[i + 1]);

				}

				if (horizoParalel[i].nodeGreen.y == y1 + hight) {
					backWeaveSecondPointy += 8;
					System.out.println("4 second " + backWeaveSecondPointy);
					secondPoint = new nodePoint(horizoParalel[i].nodeGreen.x,
							horizoParalel[i].nodeGreen.y + backWeaveSecondPointy, colors[i]);
					secondOpposite = new nodePoint(horizoParalel[i + 1].nodeGreen.x,
							horizoParalel[i + 1].nodeGreen.y - backWeaveSecondPointy, colors[i + 1]);
				}

				horizoRepresentedLinesTemp[i] = drawShapeLineBackWeave(isHorizo, horizoParalel[i].nodeRed, firstPoint,
						secondPoint, strokeBorder, stroke, stringWidth, i, colors, backWeaveSecondPointx,
						backWeaveSecondPointy, lastPoints);

				horizoRepresentedLinesTemp[i + 1] = drawShapeLineBackWeave(isHorizo, horizoParalel[i + 1].nodeRed,
						firstOpposite, secondOpposite, strokeBorder, stroke, stringWidth, i + 1, colors,
						-backWeaveSecondPointx, -backWeaveSecondPointy, lastPoints);
			
				// sending images to gif
				if (isGif = true) {
					System.out.println("is gif is entered");
					BufferedImage gifImage = new BufferedImage(widthPic, heightPic, BufferedImage.TYPE_INT_ARGB);
					Graphics2D gif = gifImage.createGraphics();
					gif.drawImage(horizoRepresentedLinesTemp[i], 0, 0, null);
					gif.drawImage(horizoRepresentedLinesTemp[i + 1], 0, 0, null);
					try {
						ImageIO.write(gifImage, "png", new File("c://temp//gif//" + counterGif + ".png"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					counterGif++;
				}
				/*
				 * // retreaving second point for the later outer neibor strings
				 * lastPoints[i] = secondPoint; lastPoints[horizoParalel.length
				 * - i - 1] = secondOpposite;
				 */

				if ((backWeaveSecondPointx > 200) || (backWeaveSecondPointy > 200) || (backWeaveSecondPointx < -200)
						|| (backWeaveSecondPointy < -200)) {
					break;
				}

			}

			// end of second while

			if ((backWeaveSecondPointx > 200) || (backWeaveSecondPointy > 200) || (backWeaveSecondPointx < -200)
					|| (backWeaveSecondPointy < -200)) {
				if (horizoParalel[i].nodeRed.x == x1) {
					backWeaveFirstPointx -= 8;
					System.out.println("1 first" + backWeaveFirstPointx);
					firstPoint = new nodePoint(horizoParalel[i].nodeRed.x + backWeaveFirstPointx,
							horizoParalel[i].nodeRed.y, colors[i]);
					firstOpposite = new nodePoint(horizoParalel[i + 1].nodeRed.x - backWeaveFirstPointx,
							horizoParalel[i + 1].nodeRed.y, colors[i + 1]);

				}
				if (horizoParalel[i].nodeRed.x == x1 + width) {
					backWeaveFirstPointx += 8;
					System.out.println("2 first" + backWeaveFirstPointx);
					firstPoint = new nodePoint(horizoParalel[i].nodeRed.x + backWeaveFirstPointx,
							horizoParalel[i].nodeRed.y, colors[i]);
					firstOpposite = new nodePoint(horizoParalel[i + 1].nodeRed.x - backWeaveFirstPointx,
							horizoParalel[i + 1].nodeRed.y, colors[i + 1]);

				}
				if (horizoParalel[i].nodeRed.y == y1) {
					backWeaveFirstPointy -= 8;
					System.out.println("3 first" + backWeaveFirstPointy);
					firstPoint = new nodePoint(horizoParalel[i].nodeRed.x,
							horizoParalel[i].nodeRed.y + backWeaveFirstPointy, colors[i]);
					firstOpposite = new nodePoint(horizoParalel[i + 1].nodeRed.x,
							horizoParalel[i + 1].nodeRed.y - backWeaveFirstPointy, colors[i + 1]);

				}
				if (horizoParalel[i].nodeRed.y == y1 + hight) {
					backWeaveFirstPointy += 8;
					System.out.println("4 first" + backWeaveFirstPointy);
					firstPoint = new nodePoint(horizoParalel[i].nodeRed.x,
							horizoParalel[i].nodeRed.y + backWeaveFirstPointy, colors[i]);
					firstOpposite = new nodePoint(horizoParalel[i + 1].nodeRed.x,
							horizoParalel[i + 1].nodeRed.y - backWeaveFirstPointy, colors[i + 1]);
				}
				firstPoints[i] = firstPoint;
				firstPoints[i + 1] = firstOpposite;
				backWeaveSecondPointx = 0;
				backWeaveSecondPointy = 0;

			}

			if ((backWeaveFirstPointx > 200) || (backWeaveFirstPointy > 200) || (backWeaveFirstPointx < -200)
					|| (backWeaveFirstPointy < -200)) {
				break;
			}

		}
		firstPoints[i] = firstPoint;
		firstPoints[i + 1] = firstOpposite;
		// end of first while
		if (isHorizo == true) {
			horizoRepresentedLines[i] = horizoRepresentedLinesTemp[i];
			horizoRepresentedLines[i + 1] = horizoRepresentedLinesTemp[i + 1];

			// if there is no back-weaving then bottom string is just a
			// transparent image
			// if there is no back-weaving then bottom string is just a
			// transparent image
			if (Math.hypot(firstPoint.x - horizoParalel[i].nodeRed.x,
					firstPoint.y - horizoParalel[i].nodeRed.y) <= 10) {
				horizoRepresentedLinesBottom[i] = drawShapeLineSimpleBottom(firstPoint, horizoParalel[i], strokeBorder,
						stroke, stringWidth, false);
				horizoRepresentedLinesBottom[i + 1] = drawShapeLineSimpleBottom(firstOpposite, horizoParalel[i + 1],
						strokeBorder, stroke, stringWidth, false);
				horizoBottom[i] = new nodeLine(horizoParalel[i].nodeRed, firstPoint);
				horizoBottom[i + 1] = new nodeLine(horizoParalel[i + 1].nodeRed, firstOpposite);

			}
			// if there is= back-weaving then bottom string is drawn
			if (Math.hypot(firstPoint.x - horizoParalel[i].nodeRed.x, firstPoint.y - horizoParalel[i].nodeRed.y) > 10) {
				horizoRepresentedLinesBottom[i] = drawShapeLineSimpleBottom(firstPoint, horizoParalel[i], strokeBorder,
						stroke, stringWidth, true);
				horizoRepresentedLinesBottom[i + 1] = drawShapeLineSimpleBottom(firstOpposite, horizoParalel[i + 1],
						strokeBorder, stroke, stringWidth, true);
				horizoBottom[i] = new nodeLine(horizoParalel[i].nodeRed, firstPoint);
				horizoBottom[i + 1] = new nodeLine(horizoParalel[i + 1].nodeRed, firstOpposite);
			}

		} else {
			paralelRepresentedLines[i] = horizoRepresentedLinesTemp[i];
			paralelRepresentedLines[i + 1] = horizoRepresentedLinesTemp[i + 1];
			// if there is no back-weaving then bottom string is just a
			// transparent image
			if (Math.hypot(firstPoint.x - horizoParalel[i].nodeRed.x,
					firstPoint.y - horizoParalel[i].nodeRed.y) <= 10) {
				paralelRepresentedLinesBottom[i] = drawShapeLineSimpleBottom(firstPoint, horizoParalel[i], strokeBorder,
						stroke, stringWidth, false);
				paralelRepresentedLinesBottom[i + 1] = drawShapeLineSimpleBottom(firstOpposite, horizoParalel[i + 1],
						strokeBorder, stroke, stringWidth, false);
				paralelBottom[i] = new nodeLine(horizoParalel[i].nodeRed, firstPoint);
				paralelBottom[i + 1] = new nodeLine(horizoParalel[i + 1].nodeRed, firstOpposite);
			}
			// if there is back-weaving then bottom string is drawn
			if (Math.hypot(firstPoint.x - horizoParalel[i].nodeRed.x, firstPoint.y - horizoParalel[i].nodeRed.y) > 10) {
				paralelRepresentedLinesBottom[i] = drawShapeLineSimpleBottom(firstPoint, horizoParalel[i], strokeBorder,
						stroke, stringWidth, true);
				paralelRepresentedLinesBottom[i + 1] = drawShapeLineSimpleBottom(firstOpposite, horizoParalel[i + 1],
						strokeBorder, stroke, stringWidth, true);
				paralelBottom[i] = new nodeLine(horizoParalel[i].nodeRed, firstPoint);
				paralelBottom[i + 1] = new nodeLine(horizoParalel[i + 1].nodeRed, firstOpposite);
			}
		}

		// sending images to gif
		if (isGif = true) {
			BufferedImage gifImage = new BufferedImage(widthPic, heightPic, BufferedImage.TYPE_INT_ARGB);
			Graphics2D gif = gifImage.createGraphics();
			gif.drawImage(horizoRepresentedLinesTemp[i], 0, 0, null);
			gif.drawImage(horizoRepresentedLinesTemp[i + 1], 0, 0, null);
			try {
				ImageIO.write(gifImage, "png", new File("c://temp//gif//" + counterGif + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			counterGif++;
		}
		/////////// now going through all the other strings (except the two
		/////////// centers)
		for (i = horizoParalel.length / 2 - 2; i >= 0; i--) {
			horizoRepresentedLinesTemp[i] = drawShapeLine(isHorizo, horizoParalel[i], strokeBorder, stroke, stringWidth,
					i, colors, lastPoints);
			if (isHorizo == true) {
				horizoRepresentedLinesTemp[i + 1] = horizoRepresentedLines[i + 1];
			} else {
				horizoRepresentedLinesTemp[i + 1] = paralelRepresentedLines[i + 1];
			}
	

			horizoRepresentedLinesTemp[horizoParalel.length - i - 1] = drawShapeLine(isHorizo,
					horizoParalel[horizoParalel.length - i - 1], strokeBorder, stroke, stringWidth,
					horizoParalel.length - i - 1, colors, lastPoints);
	
			// sending images to gif
			if (isGif = true) {
				System.out.println("is gif is entered");
				BufferedImage gifImage = new BufferedImage(widthPic, heightPic, BufferedImage.TYPE_INT_ARGB);
				Graphics gif = gifImage.createGraphics();
				gif.drawImage(horizoRepresentedLinesTemp[i], 0, 0, null);
				gif.drawImage(horizoRepresentedLinesTemp[horizoParalel.length - i - 1], 0, 0, null);
				try {
					ImageIO.write(gifImage, "png", new File("c://temp//gif//" + counterGif + ".png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				counterGif++;
			}
			// gets bounds of the erea to check if intersting
			if (isHorizo == true) {
				rectangleShapeHorizoParalel[i] = rectangleShapeHorizo[i];
			}
			if (isHorizo == false) {
				rectangleShapeHorizoParalel[i] = rectangleShapeParalel[i];
			}

			backWeaveFirstPointx = 0;
			backWeaveFirstPointy = 0;

			// at the beginning first and second points are:
			firstPoint = new nodePoint(horizoParalel[i].nodeRed.x, horizoParalel[i].nodeRed.y, colors[i]);
			firstOpposite = new nodePoint(horizoParalel[horizoParalel.length - i - 1].nodeRed.x,
					horizoParalel[horizoParalel.length - i - 1].nodeRed.y, colors[horizoParalel.length - i - 1]);

			firstPoints[i] = firstPoint;
			firstPoints[horizoParalel.length - i - 1] = firstOpposite;

			// checks intersection of the two neighbor strings
			if (isHorizo == true) {
				rectangleShapeHorizoParalel[i] = rectangleShapeHorizo[i];
			}
			if (isHorizo == false) {
				rectangleShapeHorizoParalel[i] = rectangleShapeParalel[i];
			}

			while ((CropImage.isIntersectNew(horizoRepresentedLinesTemp[i], horizoRepresentedLinesTemp[i + 1], x1, y1,
					width, hight)) == true) {
				System.out.println("not middle: entered first while index i - " + i);

				int backWeaveSecondPointx = 0;
				int backWeaveSecondPointy = 0;

				while ((CropImage.isIntersectNew(horizoRepresentedLinesTemp[i], horizoRepresentedLinesTemp[i + 1], x1,
						y1, width, hight)) == true) {

					secondPoint = null;
					secondOpposite = null;

					if (horizoParalel[i].nodeGreen.x == x1) {
						backWeaveSecondPointx -= 8;
						System.out.println("1 second " + backWeaveSecondPointx);
						secondPoint = new nodePoint(horizoParalel[i].nodeGreen.x + backWeaveSecondPointx,
								horizoParalel[i].nodeGreen.y, colors[i]);
						secondOpposite = new nodePoint(
								horizoParalel[horizoParalel.length - i - 1].nodeGreen.x - backWeaveSecondPointx,
								horizoParalel[horizoParalel.length - i - 1].nodeGreen.y,
								colors[horizoParalel.length - i - 1]);

					}
					if (horizoParalel[i].nodeGreen.x == x1 + width) {
						backWeaveSecondPointx += 8;
						System.out.println("2 second " + backWeaveSecondPointx);
						secondPoint = new nodePoint(horizoParalel[i].nodeGreen.x + backWeaveSecondPointx,
								horizoParalel[i].nodeGreen.y, colors[i]);
						secondOpposite = new nodePoint(
								horizoParalel[horizoParalel.length - i - 1].nodeGreen.x - backWeaveSecondPointx,
								horizoParalel[horizoParalel.length - i - 1].nodeGreen.y,
								colors[horizoParalel.length - i - 1]);

					}
					if (horizoParalel[i].nodeGreen.y == y1) {
						backWeaveSecondPointy -= 8;
						System.out.println("3 second " + backWeaveSecondPointy);
						secondPoint = new nodePoint(horizoParalel[i].nodeGreen.x,
								horizoParalel[i].nodeGreen.y + backWeaveSecondPointy, colors[i]);
						secondOpposite = new nodePoint(horizoParalel[horizoParalel.length - i - 1].nodeGreen.x,
								horizoParalel[horizoParalel.length - i - 1].nodeGreen.y - backWeaveSecondPointy,
								colors[horizoParalel.length - i - 1]);

					}

					if (horizoParalel[i].nodeGreen.y == y1 + hight) {
						backWeaveSecondPointy += 8;
						System.out.println("4 second " + backWeaveSecondPointy);
						secondPoint = new nodePoint(horizoParalel[i].nodeGreen.x,
								horizoParalel[i].nodeGreen.y + backWeaveSecondPointy, colors[i]);
						secondOpposite = new nodePoint(horizoParalel[horizoParalel.length - i - 1].nodeGreen.x,
								horizoParalel[horizoParalel.length - i - 1].nodeGreen.y - backWeaveSecondPointy,
								colors[horizoParalel.length - i - 1]);

					}
					// retreaving second point for the later outer neighbor
					// strings
					firstPoints[i] = firstPoint;
					firstPoints[horizoParalel.length - i - 1] = firstOpposite;

					// drawing a paralel strings to their neighbor

					horizoRepresentedLinesTemp[i] = drawShapeLineBackWeaveParalel(isHorizo, horizoParalel[i],
							firstPoint, secondPoint, firstPoints[i + 1], lastPoints[i + 1], strokeBorder, stroke,
							stringWidth, i, colors, backWeaveSecondPointx, backWeaveSecondPointy, lastPoints);

					horizoRepresentedLinesTemp[horizoParalel.length - i - 1] = drawShapeLineBackWeaveParalel(isHorizo,
							horizoParalel[horizoParalel.length - i - 1], firstOpposite, secondOpposite,
							firstPoints[horizoParalel.length - i - 2], lastPoints[horizoParalel.length - i - 2],
							strokeBorder, stroke, stringWidth, horizoParalel.length - i - 1, colors,
							-backWeaveFirstPointx, -backWeaveSecondPointy, lastPoints);
			
					// if the paralel strings does intersect then we need to
					// find a different alignment
					if (CropImage.isIntersectNew(horizoRepresentedLinesTemp[i], horizoRepresentedLinesTemp[i + 1], x1,
							y1, width, hight) == false) {
						System.out.println("reached at paralelBackweave");
						// exiting the first and sedond 'while"
						backWeaveFirstPointx = 1000;
						break;
					}
					if (CropImage.isIntersectNew(horizoRepresentedLinesTemp[i], horizoRepresentedLinesTemp[i + 1], x1,
							y1, width, hight) == true) {

						horizoRepresentedLinesTemp[i] = drawShapeLineBackWeave(isHorizo, horizoParalel[i].nodeRed,
								firstPoint, secondPoint, strokeBorder, stroke, stringWidth, i, colors,
								backWeaveSecondPointx, backWeaveSecondPointy, lastPoints);

						horizoRepresentedLinesTemp[horizoParalel.length - i - 1] = drawShapeLineBackWeave(isHorizo,
								horizoParalel[horizoParalel.length - i - 1].nodeRed, firstOpposite, secondOpposite,
								strokeBorder, stroke, stringWidth, horizoParalel.length - i - 1, colors,
								-backWeaveSecondPointx, -backWeaveSecondPointy, lastPoints);
				
						// sending images to gif
						if (isGif = true) {
							System.out.println("is gif is entered");

							BufferedImage gifImage = new BufferedImage(widthPic, heightPic,
									BufferedImage.TYPE_INT_ARGB);
							Graphics gif = gifImage.createGraphics();
							gif.drawImage(horizoRepresentedLinesTemp[i], 0, 0, null);
							gif.drawImage(horizoRepresentedLinesTemp[horizoParalel.length - i - 1], 0, 0, null);
							try {
								ImageIO.write(gifImage, "png", new File("c://temp//gif//" + counterGif + ".png"));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							counterGif++;
						}
					}

					if ((backWeaveSecondPointx > 200) || (backWeaveSecondPointy > 200) || (backWeaveSecondPointx < -200)
							|| (backWeaveSecondPointy < -200)) {
						break;
					}

				}

				// end of second while

				if ((backWeaveFirstPointx > 200) || (backWeaveFirstPointy > 200) || (backWeaveFirstPointx < -200)
						|| (backWeaveFirstPointy < -200)) {
					break;
				}
				if ((backWeaveSecondPointx > 200) || (backWeaveSecondPointy > 200) || (backWeaveSecondPointx < -200)
						|| (backWeaveSecondPointy < -200)) {
					if (horizoParalel[i].nodeRed.x == x1) {
						backWeaveFirstPointx -= 8;
						System.out.println("1 first" + backWeaveFirstPointx);
						firstPoint = new nodePoint(horizoParalel[i].nodeRed.x + backWeaveFirstPointx,
								horizoParalel[i].nodeRed.y, colors[i]);
						firstOpposite = new nodePoint(
								horizoParalel[horizoParalel.length - i - 1].nodeRed.x - backWeaveFirstPointx,
								horizoParalel[horizoParalel.length - i - 1].nodeRed.y,
								colors[horizoParalel.length - i - 1]);

					}
					if (horizoParalel[i].nodeRed.x == x1 + width) {
						backWeaveFirstPointx += 8;
						System.out.println("2 first" + backWeaveFirstPointx);
						firstPoint = new nodePoint(horizoParalel[i].nodeRed.x + backWeaveFirstPointx,
								horizoParalel[i].nodeRed.y, colors[i]);
						firstOpposite = new nodePoint(
								horizoParalel[horizoParalel.length - i - 1].nodeRed.x - backWeaveFirstPointx,
								horizoParalel[horizoParalel.length - i - 1].nodeRed.y,
								colors[horizoParalel.length - i - 1]);

					}
					if (horizoParalel[i].nodeRed.y == y1) {
						backWeaveFirstPointy -= 8;
						System.out.println("3 first" + backWeaveFirstPointy);
						firstPoint = new nodePoint(horizoParalel[i].nodeRed.x,
								horizoParalel[i].nodeRed.y + backWeaveFirstPointy, colors[i]);
						firstOpposite = new nodePoint(horizoParalel[horizoParalel.length - i - 1].nodeRed.x,
								horizoParalel[horizoParalel.length - i - 1].nodeRed.y - backWeaveFirstPointy,
								colors[horizoParalel.length - i - 1]);

					}
					if (horizoParalel[i].nodeRed.y == y1 + hight) {
						backWeaveFirstPointy += 8;
						System.out.println("4 first" + backWeaveFirstPointy);
						firstPoint = new nodePoint(horizoParalel[i].nodeRed.x,
								horizoParalel[i].nodeRed.y + backWeaveFirstPointy, colors[i]);
						firstOpposite = new nodePoint(horizoParalel[horizoParalel.length - i - 1].nodeRed.x,
								horizoParalel[horizoParalel.length - i - 1].nodeRed.y - backWeaveFirstPointy,
								colors[horizoParalel.length - i - 1]);
					}

					backWeaveSecondPointx = 0;
					backWeaveSecondPointy = 0;
				}

				// retreaving second point for the later outer neighbor
				// strings
				firstPoints[i] = firstPoint;
				firstPoints[horizoParalel.length - i - 1] = firstOpposite;

			}

			// end of first while
			if (isHorizo == true) {
				horizoRepresentedLines[i] = horizoRepresentedLinesTemp[i];
				horizoRepresentedLines[horizoParalel.length - i - 1] = horizoRepresentedLinesTemp[horizoParalel.length
						- i - 1];
		
				// if there is no back-weaving then bottom string is just a
				// transparent image
				if (Math.hypot(firstPoint.x - horizoParalel[i].nodeRed.x,
						firstPoint.y - horizoParalel[i].nodeRed.y) <= 10) {
					horizoRepresentedLinesBottom[i] = drawShapeLineSimpleBottom(firstPoint, horizoParalel[i],
							strokeBorder, stroke, stringWidth, false);
					horizoRepresentedLinesBottom[horizoParalel.length - i - 1] = drawShapeLineSimpleBottom(
							firstOpposite, horizoParalel[horizoParalel.length - i - 1], strokeBorder, stroke,
							stringWidth, false);
					horizoBottom[i] = new nodeLine(horizoParalel[i].nodeRed, firstPoint);
					horizoBottom[i + 1] = new nodeLine(horizoParalel[i + 1].nodeRed, firstOpposite);
				}
				// if there is= back-weaving then bottom string is drawn
				if (Math.hypot(firstPoint.x - horizoParalel[i].nodeRed.x,
						firstPoint.y - horizoParalel[i].nodeRed.y) > 10) {
					horizoRepresentedLinesBottom[i] = drawShapeLineSimpleBottom(firstPoint, horizoParalel[i],
							strokeBorder, stroke, stringWidth, true);
					horizoRepresentedLinesBottom[horizoParalel.length - i - 1] = drawShapeLineSimpleBottom(
							firstOpposite, horizoParalel[horizoParalel.length - i - 1], strokeBorder, stroke,
							stringWidth, true);
					horizoBottom[i] = new nodeLine(horizoParalel[i].nodeRed, firstPoint);
					horizoBottom[i + 1] = new nodeLine(horizoParalel[i + 1].nodeRed, firstOpposite);
				}

			} else {
				paralelRepresentedLines[i] = horizoRepresentedLinesTemp[i];
				paralelRepresentedLines[horizoParalel.length - i - 1] = horizoRepresentedLinesTemp[horizoParalel.length
						- i - 1];
			
				// if there is no back-weaving then bottom string is just a
				// transparent image
				if (Math.hypot(firstPoint.x - horizoParalel[i].nodeRed.x,
						firstPoint.y - horizoParalel[i].nodeRed.y) <= 10) {
					paralelRepresentedLinesBottom[i] = drawShapeLineSimpleBottom(firstPoint, horizoParalel[i],
							strokeBorder, stroke, stringWidth, false);
					paralelRepresentedLinesBottom[horizoParalel.length - i - 1] = drawShapeLineSimpleBottom(
							firstOpposite, horizoParalel[horizoParalel.length - i - 1], strokeBorder, stroke,
							stringWidth, false);
					paralelBottom[i] = new nodeLine(horizoParalel[i].nodeRed, firstPoint);
					paralelBottom[i + 1] = new nodeLine(horizoParalel[i + 1].nodeRed, firstOpposite);
				}
				// if there is= back-weaving then bottom string is drawn
				if (Math.hypot(firstPoint.x - horizoParalel[i].nodeRed.x,
						firstPoint.y - horizoParalel[i].nodeRed.y) > 10) {
					paralelRepresentedLinesBottom[i] = drawShapeLineSimpleBottom(firstPoint, horizoParalel[i],
							strokeBorder, stroke, stringWidth, true);
					paralelRepresentedLinesBottom[horizoParalel.length - i - 1] = drawShapeLineSimpleBottom(
							firstOpposite, horizoParalel[horizoParalel.length - i - 1], strokeBorder, stroke,
							stringWidth, true);
					paralelBottom[i] = new nodeLine(horizoParalel[i].nodeRed, firstPoint);
					paralelBottom[i + 1] = new nodeLine(horizoParalel[i + 1].nodeRed, firstOpposite);
				}
			}
			// sending images to gif
			if (isGif = true) {
				System.out.println("is gif is entered");

				BufferedImage gifImage = new BufferedImage(widthPic, heightPic, BufferedImage.TYPE_INT_ARGB);
				Graphics gif = gifImage.createGraphics();
				gif.drawImage(horizoRepresentedLinesTemp[i], 0, 0, null);
				gif.drawImage(horizoRepresentedLinesTemp[horizoParalel.length - i - 1], 0, 0, null);
				try {
					ImageIO.write(gifImage, "png", new File("c://temp//gif//" + counterGif + ".png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				counterGif++;
			}
		}
	}

	/**
	 * returns a stitch if all strings are straight
	 * 
	 * @param middle
	 * @param horizoRepresentedLinesTemp
	 * @param horizoParalel
	 * @param isHorizo
	 */
	public void straightStitch(BufferedImage[] horizoRepresentedLinesTemp, nodeLine[] horizoParalel, boolean isHorizo) {
		int width = 2 * length * a;
		int hight = 2 * length * b;
		int k = width + hight;
		nodePoint[] firstPoints = new nodePoint[horizoParalel.length];
		nodePoint[] lastPoints = new nodePoint[horizoParalel.length];
		BasicStroke stroke = new BasicStroke(stringWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 0.1F);
		BasicStroke strokeBorder = new BasicStroke(stringWidth + 2 * recWidth, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL, 0.1F);
		int i = horizoParalel.length / 2 - 1;
		// the bottom strings images
		BufferedImage[] horizoRepresentedLinesBottomTemp = new BufferedImage[horizoRepresentedLinesTemp.length];
		// counting how many strings that suppose to have a bizier line
		int counterBizier = 0;
		for (int j = 0; j < horizoParalel.length; j++) {
			if (((horizoParalel[j].nodeRed.x == horizoParalel[j].nodeGreen.x) && (horizoParalel[j].nodeRed.x == x1))
					|| ((horizoParalel[j].nodeRed.x == horizoParalel[j].nodeGreen.x)
							&& (horizoParalel[j].nodeRed.x == x1 + width))
					|| ((horizoParalel[j].nodeRed.y == horizoParalel[j].nodeGreen.y)
							&& (horizoParalel[j].nodeRed.y == y1))
					|| ((horizoParalel[j].nodeRed.y == horizoParalel[j].nodeGreen.y)
							&& (horizoParalel[j].nodeRed.y == y1 + hight))) {
				counterBizier++;
			}
		}
		// dividing counterBizier
		counterBizier = counterBizier / 2;
		int backweave = 2 * stringWidth;

		for (int j = 0; j < counterBizier; j++) {
			nodePoint redPoint = null;
			if ((horizoParalel[j].nodeRed.x == horizoParalel[j].nodeGreen.x) && (horizoParalel[j].nodeRed.x == x1)) {
				redPoint = new nodePoint(horizoParalel[j].nodeRed.x - backweave * (counterBizier - j - 1) - stringWidth,
						horizoParalel[j].nodeRed.y, horizoParalel[j].nodeRed.color);
				nodePoint greenPoint = new nodePoint(
						horizoParalel[j].nodeGreen.x - backweave * (counterBizier - j - 1) - stringWidth,
						horizoParalel[j].nodeGreen.y, horizoParalel[j].nodeGreen.color);
				nodeLine backweaveString = new nodeLine(redPoint, greenPoint);
				// drawing the backweaving string
				horizoRepresentedLinesTemp[j] = drawShapeLineSimple(isHorizo, horizoParalel[j], backweaveString,
						strokeBorder, stroke, stringWidth, j, colors, lastPoints);
			}
			if ((horizoParalel[j].nodeRed.x == horizoParalel[j].nodeGreen.x)
					&& (horizoParalel[j].nodeRed.x == x1 + width)) {
				redPoint = new nodePoint(horizoParalel[j].nodeRed.x + backweave * (counterBizier - j - 1) + stringWidth,
						horizoParalel[j].nodeRed.y, horizoParalel[j].nodeRed.color);
				nodePoint greenPoint = new nodePoint(
						horizoParalel[j].nodeGreen.x + backweave * (counterBizier - j - 1) + stringWidth,
						horizoParalel[j].nodeGreen.y, horizoParalel[j].nodeGreen.color);
				nodeLine backweaveString = new nodeLine(redPoint, greenPoint);
				// drawing the backweaving string
				horizoRepresentedLinesTemp[j] = drawShapeLineSimple(isHorizo, horizoParalel[j], backweaveString,
						strokeBorder, stroke, stringWidth, j, colors, lastPoints);
			}
			if ((horizoParalel[j].nodeRed.y == horizoParalel[j].nodeGreen.y) && (horizoParalel[j].nodeRed.y == y1)) {
				redPoint = new nodePoint(horizoParalel[j].nodeRed.x,
						horizoParalel[j].nodeRed.y - backweave * (counterBizier - j - 1) - stringWidth,
						horizoParalel[j].nodeRed.color);
				nodePoint greenPoint = new nodePoint(horizoParalel[j].nodeGreen.x,
						horizoParalel[j].nodeGreen.y - backweave * (counterBizier - j - 1) - stringWidth,
						horizoParalel[j].nodeGreen.color);
				nodeLine backweaveString = new nodeLine(redPoint, greenPoint);
				// drawing the backweaving string
				horizoRepresentedLinesTemp[j] = drawShapeLineSimple(isHorizo, horizoParalel[j], backweaveString,
						strokeBorder, stroke, stringWidth, j, colors, lastPoints);
			}
			if ((horizoParalel[j].nodeRed.y == horizoParalel[j].nodeGreen.y)
					&& (horizoParalel[j].nodeRed.y == y1 + hight)) {
				redPoint = new nodePoint(horizoParalel[j].nodeRed.x,
						horizoParalel[j].nodeRed.y + backweave * (counterBizier - j - 1) + stringWidth,
						horizoParalel[j].nodeRed.color);
				nodePoint greenPoint = new nodePoint(horizoParalel[j].nodeGreen.x,
						horizoParalel[j].nodeGreen.y + backweave * (counterBizier - j - 1) + stringWidth,
						horizoParalel[j].nodeGreen.color);
				nodeLine backweaveString = new nodeLine(redPoint, greenPoint);
				// drawing the backweaving string
				horizoRepresentedLinesTemp[j] = drawShapeLineSimple(isHorizo, horizoParalel[j], backweaveString,
						strokeBorder, stroke, stringWidth, j, colors, lastPoints);
			}

			// if there is no back-weaving then bottom string is just a
			// transparent image
			if (Math.hypot(redPoint.x - horizoParalel[j].nodeRed.x, redPoint.y - horizoParalel[j].nodeRed.y) <= 10) {
				horizoRepresentedLinesBottomTemp[j] = drawShapeLineSimpleBottom(redPoint, horizoParalel[j],
						strokeBorder, stroke, stringWidth, true);
			}
			// if there is= back-weaving then bottom string is drawn
			if (Math.hypot(redPoint.x - horizoParalel[j].nodeRed.x, redPoint.y - horizoParalel[j].nodeRed.y) > 10) {
				horizoRepresentedLinesBottomTemp[j] = drawShapeLineSimpleBottom(redPoint, horizoParalel[j],
						strokeBorder, stroke, stringWidth, true);

			}

		}
		for (int j = counterBizier; j < horizoParalel.length - counterBizier; j++) {
			horizoRepresentedLinesTemp[j] = drawShapeLine(isHorizo, horizoParalel[j], strokeBorder, stroke, stringWidth,
					j, colors, lastPoints);
		}

		for (int j = horizoParalel.length - counterBizier; j < horizoParalel.length; j++) {
			nodePoint redPoint = null;

			if ((horizoParalel[j].nodeRed.x == horizoParalel[j].nodeGreen.x) && (horizoParalel[j].nodeRed.x == x1)) {
				redPoint = new nodePoint(horizoParalel[j].nodeRed.x
						- backweave * (-horizoParalel.length + j + counterBizier) - stringWidth,
						horizoParalel[j].nodeRed.y, horizoParalel[j].nodeRed.color);
				nodePoint greenPoint = new nodePoint(horizoParalel[j].nodeGreen.x
						- backweave * (-horizoParalel.length + j + counterBizier) - stringWidth,
						horizoParalel[j].nodeGreen.y, horizoParalel[j].nodeGreen.color);
				nodeLine backweaveString = new nodeLine(redPoint, greenPoint);
				// drawing the backweaving string
				horizoRepresentedLinesTemp[j] = drawShapeLineSimple(isHorizo, horizoParalel[j], backweaveString,
						strokeBorder, stroke, stringWidth, j, colors, lastPoints);
			}
			if ((horizoParalel[j].nodeRed.x == horizoParalel[j].nodeGreen.x)
					&& (horizoParalel[j].nodeRed.x == x1 + width)) {
				redPoint = new nodePoint(horizoParalel[j].nodeRed.x
						+ backweave * (-horizoParalel.length + j + counterBizier) + stringWidth,
						horizoParalel[j].nodeRed.y, horizoParalel[j].nodeRed.color);
				nodePoint greenPoint = new nodePoint(horizoParalel[j].nodeGreen.x
						+ backweave * (-horizoParalel.length + j + counterBizier) + stringWidth,
						horizoParalel[j].nodeGreen.y, horizoParalel[j].nodeGreen.color);
				nodeLine backweaveString = new nodeLine(redPoint, greenPoint);
				// drawing the backweaving string
				horizoRepresentedLinesTemp[j] = drawShapeLineSimple(isHorizo, horizoParalel[j], backweaveString,
						strokeBorder, stroke, stringWidth, j, colors, lastPoints);
			}
			if ((horizoParalel[j].nodeRed.y == horizoParalel[j].nodeGreen.y) && (horizoParalel[j].nodeRed.y == y1)) {
				redPoint = new nodePoint(
						horizoParalel[j].nodeRed.x, horizoParalel[j].nodeRed.y
								- backweave * (-horizoParalel.length + j + counterBizier) - stringWidth,
						horizoParalel[j].nodeRed.color);
				nodePoint greenPoint = new nodePoint(
						horizoParalel[j].nodeGreen.x, horizoParalel[j].nodeGreen.y
								- backweave * (-horizoParalel.length + j + counterBizier) - stringWidth,
						horizoParalel[j].nodeGreen.color);
				nodeLine backweaveString = new nodeLine(redPoint, greenPoint);
				// drawing the backweaving string
				horizoRepresentedLinesTemp[j] = drawShapeLineSimple(isHorizo, horizoParalel[j], backweaveString,
						strokeBorder, stroke, stringWidth, j, colors, lastPoints);
			}
			if ((horizoParalel[j].nodeRed.y == horizoParalel[j].nodeGreen.y)
					&& (horizoParalel[j].nodeRed.y == y1 + hight)) {
				redPoint = new nodePoint(
						horizoParalel[j].nodeRed.x, horizoParalel[j].nodeRed.y
								+ backweave * (-horizoParalel.length + j + counterBizier) + stringWidth,
						horizoParalel[j].nodeRed.color);
				nodePoint greenPoint = new nodePoint(
						horizoParalel[j].nodeGreen.x, horizoParalel[j].nodeGreen.y
								+ backweave * (-horizoParalel.length + j + counterBizier) + stringWidth,
						horizoParalel[j].nodeGreen.color);
				nodeLine backweaveString = new nodeLine(redPoint, greenPoint);
				// drawing the backweaving string
				horizoRepresentedLinesTemp[j] = drawShapeLineSimple(isHorizo, horizoParalel[j], backweaveString,
						strokeBorder, stroke, stringWidth, j, colors, lastPoints);
			}

			// if there is no back-weaving then bottom string is just a
			// transparent image
			if (Math.hypot(redPoint.x - horizoParalel[j].nodeRed.x, redPoint.y - horizoParalel[j].nodeRed.y) <= 10) {
				horizoRepresentedLinesBottomTemp[j] = drawShapeLineSimpleBottom(redPoint, horizoParalel[j],
						strokeBorder, stroke, stringWidth, false);

			}
			// if there is= back-weaving then bottom string is drawn
			if (Math.hypot(redPoint.x - horizoParalel[j].nodeRed.x, redPoint.y - horizoParalel[j].nodeRed.y) > 10) {
				horizoRepresentedLinesBottomTemp[j] = drawShapeLineSimpleBottom(redPoint, horizoParalel[j],
						strokeBorder, stroke, stringWidth, true);

			}
		}
		// end of first while
		if (isHorizo == true) {
			horizoRepresentedLines = horizoRepresentedLinesTemp;
			horizoRepresentedLinesBottom = horizoRepresentedLinesBottomTemp;

		} else {
			paralelRepresentedLines = horizoRepresentedLinesTemp;
			paralelRepresentedLinesBottom = horizoRepresentedLinesBottomTemp;

		}
	}

}
