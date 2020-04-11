package scoubidouDiagram;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
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
 * @author Yonatan Setbon
 *
 */
class MyPrintCanvasDiagram extends JComponent {

	// x, a can be changeable
	public static int a = 1;
	// y, b can be changeable
	public static int b = 4;

	// the length of a tile
	public static int length = 120;
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
	int crissNumberOfLines = 2;
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
	public static int heightOfImage = 2 * b * length + y1 + 2 * lengthOfStringEnd - length;

	public BufferedImage rectangleImage;
	public static double stepOfAngle = Math.log(a + b);

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

		// if we need to paint criss

		// middle index of horizo array
		int middleIndexOfHorizo = horizo.length / 2;
		double averageEvenTangetsHorizo = 0;
		for (int i = 0; i < horizo.length; i = i + 2) {
			averageEvenTangetsHorizo = averageEvenTangetsHorizo
					+ Math.toDegrees(angleBetween2Lines(new Line2D.Double(horizo[i].nodeGreen.x, horizo[i].nodeGreen.y,
							horizo[i].nodeRed.x, horizo[i].nodeRed.y), new Line2D.Double(x1 - 4, y1, 0, y1)));
		}

		averageEvenTangetsHorizo = averageEvenTangetsHorizo / (double) middleIndexOfHorizo;
		// saves this local averageEvenTangetsHorizo to continue when we run
		// case one of the 'while'
		double averageEvenTangetsHorizoTemp = averageEvenTangetsHorizo;
		double averageOddTangetsHorizo = 0;
		for (int i = 1; i < horizo.length; i = i + 2) {
			averageOddTangetsHorizo = averageOddTangetsHorizo
					+ Math.toDegrees(angleBetween2Lines(new Line2D.Double(horizo[i].nodeGreen.x, horizo[i].nodeGreen.y,
							horizo[i].nodeRed.x, horizo[i].nodeRed.y), new Line2D.Double(x1 - 4, y1, 0, y1)));
		}
		averageOddTangetsHorizo = averageOddTangetsHorizo / (double) middleIndexOfHorizo;
		double averageOddTangetsHorizoTemp = averageOddTangetsHorizo;
		if (isCrissWeave == true) {
			// we run the while three times, once we subtact the angle, once we
			// add, and once we run the best out of them, minimumCaseZero and
			// One will be evaluated during the first two runnings of the for.
			double minimumCaseZero = 10000, minimumCaseOne = 10000;

			// valuation of counters for cases zero and one (case zero is
			// subtracting 0.5 angle each time etc),
			// then we need to also set counters for two and three to
			// equalize them with zero and one, that for not getting to much
			// painting strings
			int counterZero = 0, counterOne = 0, counterFour = 0, counterThree = 0;

			for (int t = 0; t < 3; t++) {
				if (t == 2) {
					// 2 is for case Zero, 3 is for case One
					if (minimumCaseOne < minimumCaseZero) {
						t = 3;
						System.out.println("t= " + 0);

					} else {
						t = 4;
						System.out.println("t= " + 1);
					}
				}
			
				// for running the while
				boolean isDerivitiveDistanceHorizoStrings = false;
				// a temporary strng array that saves all strng
				StringDiagramTangent[] strngTempHorizo = new StringDiagramTangent[horizo.length];

				// average even and odd before running the while, this is
				// necessary while running case One
				averageEvenTangetsHorizo = averageEvenTangetsHorizoTemp;
				averageOddTangetsHorizo = averageOddTangetsHorizoTemp;

				// for finding the smallest dirivitive of angle
				double minimumOfcaseTemp = 10000;
				double minimumOfcase = 10000;

				// checkes if its first iteration
				boolean isFirstItaration = true;

				// going through even indexes of horizo
				while (isDerivitiveDistanceHorizoStrings == false) {

					for (int i = 0; i < horizo.length; i = i + 2) {
						int indexOfHorizo = listNodePoints.findNodePointIndex(horizo[i].nodeRed);
						g.setColor(colors[indexOfHorizo]);
						StringDiagramTangent strng = null;
						PrintStartingPosition strngStart = null;
						strngStart = new PrintStartingPosition();
						strngStart.colors = colors;

						horizoRepresentedLinesStart[i] = new BufferedImage(widthOfImage, heightOfImage,
								BufferedImage.TYPE_INT_ARGB);

						Graphics2D gHorizoStart = horizoRepresentedLinesStart[i].createGraphics();

						// if needed to paint from up to down
						if (horizo[i].nodeRed.y == y1) {
							strng = new StringDiagramTangent("upToDown", (int) horizo[i].nodeRed.x - length / 2,
									(int) horizo[i].nodeRed.y, colors[indexOfHorizo], averageEvenTangetsHorizo,
									stitchRec);
							strngStart = new PrintStartingPosition();
							strngStart.colors = colors;

							final Graphics2D g2d = (Graphics2D) g.create();
							try {
								strng.paint(g2d);
								// paints the starting string to
								// horizoRepresentedLinesStart[i]
								PrintStartingPosition.paintStringAndCircle((int) horizo[i].nodeRed.x,
										(int) horizo[i].nodeRed.y, "up", gHorizoStart, indexOfHorizo);
							} finally {
								g2d.dispose();
							}
						}

						// if needed to paint from down to up
						if (horizo[i].nodeRed.y == y1 + hight) {
							strng = new StringDiagramTangent("downToUp",
									(int) Math.round(horizo[i].nodeRed.x + length / 2), (int) horizo[i].nodeRed.y,
									colors[indexOfHorizo], averageEvenTangetsHorizo, stitchRec);

							final Graphics2D g2d = (Graphics2D) g.create();
							try {
								strng.paint(g2d);
								// paints the starting string to
								// horizoRepresentedLinesStart[i]
								PrintStartingPosition.paintStringAndCircle((int) horizo[i].nodeRed.x,
										(int) horizo[i].nodeRed.y, "down", gHorizoStart, indexOfHorizo);
							} finally {
								g2d.dispose();
							}

						}
						// if needed to paint from left to right
						if (horizo[i].nodeRed.x == x1) {
							strng = new StringDiagramTangent("leftToRight", (int) horizo[i].nodeRed.x,
									Math.round((int) horizo[i].nodeRed.y + (length / 2)), colors[indexOfHorizo],
									averageEvenTangetsHorizo, stitchRec);

							final Graphics2D g2d = (Graphics2D) g.create();
							try {
								strng.paint(g2d);
								// paints the starting string to
								// horizoRepresentedLinesStart[i]
								PrintStartingPosition.paintStringAndCircle((int) horizo[i].nodeRed.x,
										(int) horizo[i].nodeRed.y, "right", gHorizoStart, indexOfHorizo);
							} finally {
								g2d.dispose();
							}
						}
						// if needed to paint from right to left
						if (horizo[i].nodeRed.x == x1 + width) {

							strng = new StringDiagramTangent("rightToLeft", (int) horizo[i].nodeRed.x,
									Math.round(horizo[i].nodeRed.y - (length / 2)), colors[indexOfHorizo],
									averageEvenTangetsHorizo, stitchRec);
							strngStart = new PrintStartingPosition();

							final Graphics2D g2d = (Graphics2D) g.create();
							try {
								strng.paint(g2d);
								// paints the starting string to
								// horizoRepresentedLinesStart[i]
								PrintStartingPosition.paintStringAndCircle((int) horizo[i].nodeRed.x,
										(int) horizo[i].nodeRed.y, "left", gHorizoStart, indexOfHorizo);
							} finally {
								g2d.dispose();
							}

						}

						// if its case 3 or four, then after getting the max of
						// the counter (three or four) we can find
						// horizoRepresentedLines[i]

						g = gBackUp;

						// getting spesific strng to strngTempHorizo
						strngTempHorizo[i] = strng;
					}

					// going through th odd indexes of horizo
					for (int i = 1; i < horizo.length; i = i + 2) {
						// getting index of horizo[i]
						int indexOfHorizo = listNodePoints.findNodePointIndex(horizo[i].nodeRed);
						g.setColor(colors[indexOfHorizo]);
						StringDiagramTangent strng = null;
						PrintStartingPosition strngStart = null;
						strngStart = new PrintStartingPosition();
						strngStart.colors = colors;

						horizoRepresentedLinesStart[i] = new BufferedImage(widthOfImage, heightOfImage,
								BufferedImage.TYPE_INT_ARGB);

						Graphics2D gHorizoStart = horizoRepresentedLinesStart[i].createGraphics();

						if (horizo[i].nodeRed.y == y1) {
							strng = new StringDiagramTangent("upToDown", (int) horizo[i].nodeRed.x - length / 2,
									(int) horizo[i].nodeRed.y, colors[indexOfHorizo], averageOddTangetsHorizo,
									stitchRec);

							final Graphics2D g2d = (Graphics2D) g.create();
							try {
								strng.paint(g2d);
								PrintStartingPosition.paintStringAndCircle((int) horizo[i].nodeRed.x,
										(int) horizo[i].nodeRed.y, "up", gHorizoStart, indexOfHorizo);

							} finally {
								g2d.dispose();
							}
						}

						// if needed to paint from down to up
						if (horizo[i].nodeRed.y == y1 + hight) {
							strng = new StringDiagramTangent("downToUp", (int) horizo[i].nodeRed.x + length / 2,
									(int) horizo[i].nodeRed.y, colors[indexOfHorizo], averageOddTangetsHorizo,
									stitchRec);

							final Graphics2D g2d = (Graphics2D) g.create();
							try {
								strng.paint(g2d);
								PrintStartingPosition.paintStringAndCircle((int) horizo[i].nodeRed.x,
										(int) horizo[i].nodeRed.y, "down", gHorizoStart, indexOfHorizo);

							} finally {
								g2d.dispose();
							}
						}
						// if needed to paint from left to right
						if (horizo[i].nodeRed.x == x1) {
							strng = new StringDiagramTangent("leftToRight", (int) horizo[i].nodeRed.x,
									Math.round((int) horizo[i].nodeRed.y + (length / 2)), colors[indexOfHorizo],
									averageOddTangetsHorizo, stitchRec);

							final Graphics2D g2d = (Graphics2D) g.create();
							try {
								strng.paint(g2d);
								PrintStartingPosition.paintStringAndCircle((int) horizo[i].nodeRed.x,
										(int) horizo[i].nodeRed.y, "right", gHorizoStart, indexOfHorizo);

							} finally {
								g2d.dispose();
							}
						}
						// if needed to paint from right to left
						if (horizo[i].nodeRed.x == x1 + width) {

							strng = new StringDiagramTangent("rightToLeft", (int) horizo[i].nodeRed.x,
									Math.round(horizo[i].nodeRed.y - (length / 2)), colors[indexOfHorizo],
									averageOddTangetsHorizo, stitchRec);

							final Graphics2D g2d = (Graphics2D) g.create();
							try {
								strng.paint(g2d);
								PrintStartingPosition.paintStringAndCircle((int) horizo[i].nodeRed.x,
										(int) horizo[i].nodeRed.y, "left", gHorizoStart, indexOfHorizo);

							} finally {
								g2d.dispose();
							}
						}

						if (t == 3) {
							System.out
									.println("horizo counterThree and counterFour" + counterThree + " " + counterFour);
							if (counterOne == counterThree) {

								horizoRepresentedLines[i] = new BufferedImage(widthOfImage, heightOfImage,
										BufferedImage.TYPE_INT_ARGB);
								Graphics2D gHorizo = horizoRepresentedLines[i].createGraphics();
								try {
									strng.paint(gHorizo);

								} finally {
									gHorizo.dispose();
								}
								
								g = gBackUp;

								// getting spesific strng to strngTempHorizo
								strngTempHorizo[i] = strng;
							}

						}
						if (t == 4) {
							System.out
									.println("horizo counterThree and counterFour" + counterThree + " " + counterFour);
							horizoRepresentedLines[i] = new BufferedImage(widthOfImage, heightOfImage,
									BufferedImage.TYPE_INT_ARGB);
							if (counterZero == counterFour) {

								Graphics2D gHorizo = horizoRepresentedLines[i].createGraphics();
								try {
									strng.paint(gHorizo);

								} finally {
									gHorizo.dispose();
								}
								g = gBackUp;

								// getting spesific strng to strngTempHorizo
								strngTempHorizo[i] = strng;
							}

						}
						
						g = gBackUp;

						// getting spesific strng to strngTempHorizo
						strngTempHorizo[i] = strng;
					}

					double R = Math.round(MyPrintCanvasDiagram.length / (double) 6);

					int length = (int) Math
							.round(MyPrintCanvasDiagram.length - ((double) MyPrintCanvasDiagram.length / (double) 4));

					// the idial gap, that is the gap from option 3 of
					// StringDiagramTangent
					double distanceBetweenOptionThree = 2 * (MyPrintCanvasDiagram.length - (length - R));

					// finding the difference of the average with the actuall
					// distances
					double averageDifferenceBetweenAverageAndDistance;
					// sum subtraction of the average with a distance
					double sumDifferenceBetweenAverageAndDistance = 0;
					System.out.println("horizo length = "+horizo.length);

					//if there are only two strings in criss we try to make it as close as case three of stringDiagramTangent
					if ( horizo.length - 1 == 1){
						double distanceFromTwoStringsHorizo = strngTempHorizo[0].representedLineAfterTransformation
								.ptLineDist(strngTempHorizo[1].representedLineAfterTransformation.getP1());
						sumDifferenceBetweenAverageAndDistance = sumDifferenceBetweenAverageAndDistance
								+  Math.abs(distanceFromTwoStringsHorizo - distanceBetweenOptionThree);
						sumDifferenceBetweenAverageAndDistance = sumDifferenceBetweenAverageAndDistance*2;
					}
					// if there are more than two strings in criss, we find the average of the subtraction of two close distances 
					if ( horizo.length - 1 > 1){ 
					for (int i = 0; i < horizo.length - 2; i++) {
						double distanceFromTwoStringsHorizo = Math.abs(strngTempHorizo[i].representedLineAfterTransformation
								.ptLineDist(strngTempHorizo[i + 1].representedLineAfterTransformation.getP1()));
						double distanceFromThreeStringsHorizo = Math.abs(strngTempHorizo[i + 1].representedLineAfterTransformation
								.ptLineDist(strngTempHorizo[i + 2].representedLineAfterTransformation.getP1()));
											
						sumDifferenceBetweenAverageAndDistance = sumDifferenceBetweenAverageAndDistance
								+  Math.abs(distanceFromTwoStringsHorizo - distanceFromThreeStringsHorizo);
					}
					//adding another time the distance of the center, because we only counted it once.
					double distanceFromCenters = Math.abs(strngTempHorizo[horizo.length/2].representedLineAfterTransformation
							.ptLineDist(strngTempHorizo[(horizo.length/2)-1].representedLineAfterTransformation.getP1()));
					double distanceFromThreeStringsHorizo = Math.abs(strngTempHorizo[horizo.length/2].representedLineAfterTransformation
							.ptLineDist(strngTempHorizo[(horizo.length/2)+1].representedLineAfterTransformation.getP1()));
					
					sumDifferenceBetweenAverageAndDistance = sumDifferenceBetweenAverageAndDistance
							+  Math.abs(distanceFromThreeStringsHorizo - distanceFromCenters);
					}
					// average of the subtraction of average and distance
					averageDifferenceBetweenAverageAndDistance = sumDifferenceBetweenAverageAndDistance
							/(double)(horizo.length);
					System.out.println(averageDifferenceBetweenAverageAndDistance+"averageDifferenceBetweenAverageAndDistance");
					System.out.println("math derivitive" + Math
							.abs(minimumOfcase));

					// minimumOfcaseTemp will be the current minimum of this
					// itaration of while

					minimumOfcaseTemp = Math
							.abs(averageDifferenceBetweenAverageAndDistance );

					// if its exactly distanceBetweenOptionThree then don't turn, because its probably case 3 from stringDiagramTangent
					if (Math.abs(
							averageDifferenceBetweenAverageAndDistance) >  10) {
							if (t == 0 || t == 4) {
								minimumCaseZero = averageDifferenceBetweenAverageAndDistance;
								averageEvenTangetsHorizo = averageEvenTangetsHorizo - stepOfAngle;
								averageOddTangetsHorizo = averageOddTangetsHorizo - stepOfAngle;
								System.out.println("t= " + 4);
							}
							if (t == 1 || t == 3) {
								minimumCaseOne = averageDifferenceBetweenAverageAndDistance;
								averageEvenTangetsHorizo = averageEvenTangetsHorizo + stepOfAngle;
								averageOddTangetsHorizo = averageOddTangetsHorizo + stepOfAngle;
								System.out.println("t= " + 3);
							}

						}
					minimumOfcase = minimumOfcaseTemp;
						// if minimumOfcase is the lowest then it will exit the
						// while
					System.out.println(minimumOfcase);
					if ( horizo.length - 1 == 1){
						if (minimumOfcase < 1) {
							isDerivitiveDistanceHorizoStrings = true;
						}
					}
					if ( horizo.length - 1 > 1){
						if (minimumOfcase < 6) {
							isDerivitiveDistanceHorizoStrings = true;
						}
					}


					// if isDeriviteveDis is true, then we must not add more to
					// the counters
					if (isDerivitiveDistanceHorizoStrings == false) {
						// we count how many times we went through the 'while',
						// for
						// cases Zero and One
						if (t == 0) {
							counterZero++;
						}
						if (t == 1) {
							counterOne++;
						}
						if (t == 4) {
							counterFour++;
						}
						if (t == 3) {
							counterThree++;
							System.out.println("got into case t == 3 " + counterThree);
						}

						// if counters don't get to a minimum diferention of
						// distance of strings then there's prob no minimum in
						// that
						// case
						if (counterZero > 100) {
							isDerivitiveDistanceHorizoStrings = true;
							minimumCaseZero = 10000;
						}
						if (counterOne > 100) {
							isDerivitiveDistanceHorizoStrings = true;
							minimumCaseOne = 10000;
						}
					}

					for (int i = 0; i < horizo.length; i++) {

						if (t == 3) {
							System.out.println("horizo in case counterThree" + counterThree);
							if (counterOne == counterThree) {
								System.out.println(
										"horizo counterThree and counterFour" + counterThree + " " + counterFour);
								horizoRepresentedLines[i] = new BufferedImage(widthOfImage, heightOfImage,
										BufferedImage.TYPE_INT_ARGB);
								Graphics2D gHorizo = horizoRepresentedLines[i].createGraphics();
								try {
									strngTempHorizo[i].paint(gHorizo);

								} finally {
									gHorizo.dispose();
								}
								g = gBackUp;
							}

						}
						if (t == 4) {
							System.out.println("horizo in case counterFour" + counterFour);
							if (counterZero == counterFour) {
								System.out.println(
										"horizo counterThree and counterFour" + counterThree + " " + counterFour);
								horizoRepresentedLines[i] = new BufferedImage(widthOfImage, heightOfImage,
										BufferedImage.TYPE_INT_ARGB);
								Graphics2D gHorizo = horizoRepresentedLines[i].createGraphics();
								try {
									strngTempHorizo[i].paint(gHorizo);

								} finally {
									gHorizo.dispose();
								}
							}
						}
					}
				}
				// end of the while
			g = gBackUp;

			}
	

			g = gBackUp;
		}

		// if we need to paint cross

		// middle index of paralel array
		int middleIndexOfParalel = paralel.length / 2;
		double averageEvenTangetsParalel = 0;
		for (int i = 0; i < paralel.length; i = i + 2) {
			averageEvenTangetsParalel = averageEvenTangetsParalel
					+ Math.toDegrees(
							angleBetween2Lines(
									new Line2D.Double(paralel[i].nodeGreen.x, paralel[i].nodeGreen.y,
											paralel[i].nodeRed.x, paralel[i].nodeRed.y),
									new Line2D.Double(x1 - 4, y1, 0, y1)));
		}

		averageEvenTangetsParalel = averageEvenTangetsParalel / (double) middleIndexOfParalel;
		// saves this local averageEvenTangetsParalel to continue when we
		// run
		// case one of the 'while'
		double averageEvenTangetsParalelTemp = averageEvenTangetsParalel;
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
		double averageOddTangetsParalelTemp = averageOddTangetsParalel;

		if (isCrissWeave == false) {
			// we run the while three times, once we subtact the angle, once we
			// add, and once we run the best out of them, minimumCaseZero and
			// One will be evaluated during the first two runnings of the for.
			double minimumCaseZero = 10000, minimumCaseOne = 10000;

			// valuation of counters for cases zero and one (case zero is
			// subtracting 0.5 angle each time etc),
			// then we need to also set counters for two and three to
			// equalize them with zero and one, that for not getting to much
			// painting strings
			int counterZero = 0, counterOne = 0, counterFour = 0, counterThree = 0;

			for (int t = 0; t < 3; t++) {
				if (t == 2) {
					// 2 is for case Zero, 3 is for case One
					if (minimumCaseOne < minimumCaseZero) {
						t = 3;
						System.out.println("t= " + 0);

					} else {
						t = 4;
						System.out.println("t= " + 1);
					}
				}
				// the average distance of all paralel string,
				// StringDiagramTangent.representedLineAfterTransformation are
				// the
				// paralel strings
				double averageDistanceOfParalelStringsMinimum = 10000;
				// for running the while
				boolean isDerivitiveDistanceParalelStrings = false;
				// a temporary strng array that saves all strng
				StringDiagramTangent[] strngTempParalel = new StringDiagramTangent[paralel.length];

				// average even and odd before running the while, this is
				// necessary while running case One
				averageEvenTangetsParalel = averageEvenTangetsParalelTemp;
				averageOddTangetsParalel = averageOddTangetsParalelTemp;

				// for finding the smallest dirivitive of angle
				double minimumOfcaseTemp = 10000;
				double minimumOfcase = 10000;

				// checkes if its first iteration
				boolean isFirstItaration = true;
				// going through even indexes of paralel
				while (isDerivitiveDistanceParalelStrings == false) {

					for (int i = 0; i < paralel.length; i = i + 2) {
						int indexOfParalel = listNodePoints.findNodePointIndex(paralel[i].nodeRed);
						g.setColor(colors[indexOfParalel]);
						StringDiagramTangent strng = null;
						PrintStartingPosition strngStart = null;
						strngStart = new PrintStartingPosition();
						strngStart.colors = colors;

						paralelRepresentedLinesStart[i] = new BufferedImage(widthOfImage, heightOfImage,
								BufferedImage.TYPE_INT_ARGB);

						Graphics2D gParalelStart = paralelRepresentedLinesStart[i].createGraphics();

						// if needed to paint from up to down
						if (paralel[i].nodeRed.y == y1) {
							strng = new StringDiagramTangent("upToDown", (int) paralel[i].nodeRed.x - length / 2,
									(int) paralel[i].nodeRed.y, colors[indexOfParalel], averageEvenTangetsParalel,
									stitchRec);
							strngStart = new PrintStartingPosition();
							strngStart.colors = colors;

							final Graphics2D g2d = (Graphics2D) g.create();
							try {
								strng.paint(g2d);
								// paints the starting string to
								// paralelRepresentedLinesStart[i]
								PrintStartingPosition.paintStringAndCircle((int) paralel[i].nodeRed.x,
										(int) paralel[i].nodeRed.y, "up", gParalelStart, indexOfParalel);
							} finally {
								g2d.dispose();
							}
						}

						// if needed to paint from down to up
						if (paralel[i].nodeRed.y == y1 + hight) {
							strng = new StringDiagramTangent("downToUp",
									(int) Math.round(paralel[i].nodeRed.x + length / 2), (int) paralel[i].nodeRed.y,
									colors[indexOfParalel], averageEvenTangetsParalel, stitchRec);

							final Graphics2D g2d = (Graphics2D) g.create();
							try {
								strng.paint(g2d);
								// paints the starting string to
								// paralelRepresentedLinesStart[i]
								PrintStartingPosition.paintStringAndCircle((int) paralel[i].nodeRed.x,
										(int) paralel[i].nodeRed.y, "down", gParalelStart, indexOfParalel);
							} finally {
								g2d.dispose();
							}

						}
						// if needed to paint from left to right
						if (paralel[i].nodeRed.x == x1) {
							strng = new StringDiagramTangent("leftToRight", (int) paralel[i].nodeRed.x,
									Math.round((int) paralel[i].nodeRed.y + (length / 2)), colors[indexOfParalel],
									averageEvenTangetsParalel, stitchRec);

							final Graphics2D g2d = (Graphics2D) g.create();
							try {
								strng.paint(g2d);
								// paints the starting string to
								// paralelRepresentedLinesStart[i]
								PrintStartingPosition.paintStringAndCircle((int) paralel[i].nodeRed.x,
										(int) paralel[i].nodeRed.y, "right", gParalelStart, indexOfParalel);
							} finally {
								g2d.dispose();
							}
						}
						// if needed to paint from right to left
						if (paralel[i].nodeRed.x == x1 + width) {

							strng = new StringDiagramTangent("rightToLeft", (int) paralel[i].nodeRed.x,
									Math.round(paralel[i].nodeRed.y - (length / 2)), colors[indexOfParalel],
									averageEvenTangetsParalel, stitchRec);
							strngStart = new PrintStartingPosition();

							final Graphics2D g2d = (Graphics2D) g.create();
							try {
								strng.paint(g2d);
								// paints the starting string to
								// paralelRepresentedLinesStart[i]
								PrintStartingPosition.paintStringAndCircle((int) paralel[i].nodeRed.x,
										(int) paralel[i].nodeRed.y, "left", gParalelStart, indexOfParalel);
							} finally {
								g2d.dispose();
							}

						}

						// if its case 3 or four, then after getting the max of
						// the counter (three or four) we can find
						// paralelRepresentedLines[i]

						g = gBackUp;

						// getting spesific strng to strngTempParalel
						strngTempParalel[i] = strng;
					}

					// going through th odd indexes of paralel
					for (int i = 1; i < paralel.length; i = i + 2) {
						// getting index of paralel[i]
						int indexOfParalel = listNodePoints.findNodePointIndex(paralel[i].nodeRed);
						g.setColor(colors[indexOfParalel]);
						StringDiagramTangent strng = null;
						PrintStartingPosition strngStart = null;
						strngStart = new PrintStartingPosition();
						strngStart.colors = colors;

						paralelRepresentedLinesStart[i] = new BufferedImage(widthOfImage, heightOfImage,
								BufferedImage.TYPE_INT_ARGB);

						Graphics2D gParalelStart = paralelRepresentedLinesStart[i].createGraphics();

						if (paralel[i].nodeRed.y == y1) {
							strng = new StringDiagramTangent("upToDown", (int) paralel[i].nodeRed.x - length / 2,
									(int) paralel[i].nodeRed.y, colors[indexOfParalel], averageOddTangetsParalel,
									stitchRec);

							final Graphics2D g2d = (Graphics2D) g.create();
							try {
								strng.paint(g2d);
								PrintStartingPosition.paintStringAndCircle((int) paralel[i].nodeRed.x,
										(int) paralel[i].nodeRed.y, "up", gParalelStart, indexOfParalel);

							} finally {
								g2d.dispose();
							}
						}

						// if needed to paint from down to up
						if (paralel[i].nodeRed.y == y1 + hight) {
							strng = new StringDiagramTangent("downToUp", (int) paralel[i].nodeRed.x + length / 2,
									(int) paralel[i].nodeRed.y, colors[indexOfParalel], averageOddTangetsParalel,
									stitchRec);

							final Graphics2D g2d = (Graphics2D) g.create();
							try {
								strng.paint(g2d);
								PrintStartingPosition.paintStringAndCircle((int) paralel[i].nodeRed.x,
										(int) paralel[i].nodeRed.y, "down", gParalelStart, indexOfParalel);

							} finally {
								g2d.dispose();
							}
						}
						// if needed to paint from left to right
						if (paralel[i].nodeRed.x == x1) {
							strng = new StringDiagramTangent("leftToRight", (int) paralel[i].nodeRed.x,
									Math.round((int) paralel[i].nodeRed.y + (length / 2)), colors[indexOfParalel],
									averageOddTangetsParalel, stitchRec);

							final Graphics2D g2d = (Graphics2D) g.create();
							try {
								strng.paint(g2d);
								PrintStartingPosition.paintStringAndCircle((int) paralel[i].nodeRed.x,
										(int) paralel[i].nodeRed.y, "right", gParalelStart, indexOfParalel);

							} finally {
								g2d.dispose();
							}
						}
						// if needed to paint from right to left
						if (paralel[i].nodeRed.x == x1 + width) {

							strng = new StringDiagramTangent("rightToLeft", (int) paralel[i].nodeRed.x,
									Math.round(paralel[i].nodeRed.y - (length / 2)), colors[indexOfParalel],
									averageOddTangetsParalel, stitchRec);

							final Graphics2D g2d = (Graphics2D) g.create();
							try {
								strng.paint(g2d);
								PrintStartingPosition.paintStringAndCircle((int) paralel[i].nodeRed.x,
										(int) paralel[i].nodeRed.y, "left", gParalelStart, indexOfParalel);

							} finally {
								g2d.dispose();
							}
						}

						if (t == 3) {
							System.out
									.println("paralel counterThree and counterFour" + counterThree + " " + counterFour);
							if (counterOne == counterThree) {

								paralelRepresentedLines[i] = new BufferedImage(widthOfImage, heightOfImage,
										BufferedImage.TYPE_INT_ARGB);
								Graphics2D gParalel = paralelRepresentedLines[i].createGraphics();
								try {
									strng.paint(gParalel);

								} finally {
									gParalel.dispose();
								}
								g = gBackUp;

								// getting spesific strng to strngTempParalel
								strngTempParalel[i] = strng;
							}

						}
						if (t == 4) {
							System.out
									.println("paralel counterThree and counterFour" + counterThree + " " + counterFour);
							paralelRepresentedLines[i] = new BufferedImage(widthOfImage, heightOfImage,
									BufferedImage.TYPE_INT_ARGB);
							if (counterZero == counterFour) {

								Graphics2D gParalel = paralelRepresentedLines[i].createGraphics();
								try {
									strng.paint(gParalel);

								} finally {
									gParalel.dispose();
								}

							}

						}
						g = gBackUp;

						// getting spesific strng to strngTempParalel
						strngTempParalel[i] = strng;

					}


					double R = Math.round(MyPrintCanvasDiagram.length / (double) 6);

					int length = (int) Math
							.round(MyPrintCanvasDiagram.length - ((double) MyPrintCanvasDiagram.length / (double) 4));

					// the idial gap, that is the gap from option 3 of
					// StringDiagramTangent
					double distanceBetweenOptionThree = 2 * (MyPrintCanvasDiagram.length - (length - R));

					// finding the difference of the average with the actuall
					// distances
					double averageDifferenceBetweenAverageAndDistance;
					// sum subtraction of the average with a distance
					double sumDifferenceBetweenAverageAndDistance = 0;
					System.out.println("paralel length = "+paralel.length);

					//if there are only two strings in criss we try to make it as close as case three of stringDiagramTangent
					if ( paralel.length - 1 == 1){
						double distanceFromTwoStringsParalel = strngTempParalel[0].representedLineAfterTransformation
								.ptLineDist(strngTempParalel[1].representedLineAfterTransformation.getP1());
						sumDifferenceBetweenAverageAndDistance = sumDifferenceBetweenAverageAndDistance
								+  Math.abs(distanceFromTwoStringsParalel - distanceBetweenOptionThree);
						sumDifferenceBetweenAverageAndDistance = sumDifferenceBetweenAverageAndDistance*2;
						
					}
					// if there are more than two strings in criss, we find the average of the subtraction of two close distances 
					if ( paralel.length - 1 > 1){ 
					for (int i = 0; i < paralel.length - 2; i++) {
						double distanceFromTwoStringsParalel = Math.abs(strngTempParalel[i].representedLineAfterTransformation
								.ptLineDist(strngTempParalel[i + 1].representedLineAfterTransformation.getP1()));
						double distanceFromThreeStringsParalel = Math.abs(strngTempParalel[i + 1].representedLineAfterTransformation
								.ptLineDist(strngTempParalel[i + 2].representedLineAfterTransformation.getP1()));
											
						sumDifferenceBetweenAverageAndDistance = sumDifferenceBetweenAverageAndDistance
								+  Math.abs(distanceFromTwoStringsParalel - distanceFromThreeStringsParalel);
					}
					//adding another time the distance of the center, because we only counted it once.
					double distanceFromCenters = Math.abs(strngTempParalel[horizo.length/2].representedLineAfterTransformation
							.ptLineDist(strngTempParalel[(horizo.length/2)-1].representedLineAfterTransformation.getP1()));
					double distanceFromThreeStringsParalel = Math.abs(strngTempParalel[horizo.length/2].representedLineAfterTransformation
							.ptLineDist(strngTempParalel[(horizo.length/2)+1].representedLineAfterTransformation.getP1()));
					
					sumDifferenceBetweenAverageAndDistance = sumDifferenceBetweenAverageAndDistance
							+  Math.abs(distanceFromThreeStringsParalel - distanceFromCenters);
					}
					// average of the subtraction of average and distance
					averageDifferenceBetweenAverageAndDistance = sumDifferenceBetweenAverageAndDistance
							/(double)(paralel.length);
					System.out.println(averageDifferenceBetweenAverageAndDistance+"averageDifferenceBetweenAverageAndDistance");
					System.out.println("math derivitive" + Math
							.abs(minimumOfcase));

					// minimumOfcaseTemp will be the current minimum of this
					// itaration of while

					minimumOfcaseTemp = Math
							.abs(averageDifferenceBetweenAverageAndDistance );

					// if its exactly distanceBetweenOptionThree then don't turn, because its probably case 3 from stringDiagramTangent
					if (Math.abs(
							averageDifferenceBetweenAverageAndDistance) >  10) {
							if (t == 0 || t == 4) {
								minimumCaseZero = averageDifferenceBetweenAverageAndDistance;
								averageEvenTangetsParalel = averageEvenTangetsParalel - stepOfAngle;
								averageOddTangetsParalel = averageOddTangetsParalel - stepOfAngle;
								System.out.println("t= " + 4);
							}
							if (t == 1 || t == 3) {
								minimumCaseOne = averageDifferenceBetweenAverageAndDistance;
								averageEvenTangetsParalel = averageEvenTangetsParalel + stepOfAngle;
								averageOddTangetsParalel = averageOddTangetsParalel + stepOfAngle;
								System.out.println("t= " + 3);
							}

						}
					
					minimumOfcase = minimumOfcaseTemp;
						// if minimumOfcase is the lowest then it will exit the
						// while
					if ( paralel.length - 1 == 1){
						if (minimumOfcase < 1) {
							isDerivitiveDistanceParalelStrings = true;
						}
					}
					if ( paralel.length - 1 > 1){
						if (minimumOfcase < 6) {
							isDerivitiveDistanceParalelStrings = true;
						}
					}


					// if isDeriviteveDis is true, then we must not add more to
					// the counters
					if (isDerivitiveDistanceParalelStrings == false) {
						// we count how many times we went through the 'while',
						// for
						// cases Zero and One
						if (t == 0) {
							counterZero++;
						}
						if (t == 1) {
							counterOne++;
						}
						if (t == 4) {
							counterFour++;
						}
						if (t == 3) {
							counterThree++;
							System.out.println("got into case t == 3 " + counterThree);
						}

						// if counters don't get to a minimum diferention of
						// distance of strings then there's prob no minimum in
						// that
						// case
						if (counterZero > 100) {
							isDerivitiveDistanceParalelStrings = true;
							minimumCaseZero = 10000;
						}
						if (counterOne > 100) {
							isDerivitiveDistanceParalelStrings = true;
							minimumCaseOne = 10000;
						}
					}

					for (int i = 0; i < paralel.length; i++) {

						if (t == 3) {
							System.out.println("paralel in case counterThree" + counterThree);
							if (counterOne == counterThree) {
								System.out.println(
										"paralel counterThree and counterFour" + counterThree + " " + counterFour);
								paralelRepresentedLines[i] = new BufferedImage(widthOfImage, heightOfImage,
										BufferedImage.TYPE_INT_ARGB);
								Graphics2D gParalel = paralelRepresentedLines[i].createGraphics();
								try {
									strngTempParalel[i].paint(gParalel);

								} finally {
									gParalel.dispose();
								}
								g = gBackUp;

							}

						}
						if (t == 4) {
							System.out.println("paralel in case counterFour" + counterFour);
							if (counterZero == counterFour) {
								System.out.println(
										"paralel counterThree and counterFour" + counterThree + " " + counterFour);
								paralelRepresentedLines[i] = new BufferedImage(widthOfImage, heightOfImage,
										BufferedImage.TYPE_INT_ARGB);
								Graphics2D gParalel = horizoRepresentedLines[i].createGraphics();
								try {
									strngTempParalel[i].paint(gParalel);

								} finally {
									gParalel.dispose();
								}
							}
						}
					}
				}
				// end of the while
			g = gBackUp;

			}
	

			g = gBackUp;
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

		int firstLineEndPoint = 5;
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
					new File("c://temp//" + "stringImg " + "_" + "firstLineEndPoint" + firstLineEndPoint + "_(" + canv.a
							+ "," + canv.b + ")" + "," + firstLineEndPoint + "," + crissNumberOfLines + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// painting hoparalelpresentedLines to a file
		if (canv.isCrissWeave == true) {
			for (int i = 0; i < canv.horizoRepresentedLines.length; i++) {
				try {
					System.out.println("horizoRepresentedLines length " + canv.horizoRepresentedLines.length + " index " + i);
					ImageIO.write(canv.horizoRepresentedLines[i], "png",
							new File("c://temp//" + "horizoRepresentedLines " + i + "_" + "firstLineEndPoint"
									+ firstLineEndPoint + "_(" + canv.a + "," + canv.b + ")" + "," + firstLineEndPoint
									+ "," + crissNumberOfLines + ".png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			for (int i = 0; i < canv.horizoRepresentedLinesStart.length; i++) {
				try {
					System.out.println("horizoRepresentedLinesStart" + i);
					ImageIO.write(canv.horizoRepresentedLinesStart[i], "png",
							new File("c://temp//" + "horizoRepresentedLinesStart " + i + "_" + "firstLineEndPoint"
									+ firstLineEndPoint + "_(" + canv.a + "," + canv.b + ")" + "," + firstLineEndPoint
									+ "," + crissNumberOfLines + ".png"));
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
			ImageIO.write(stringImg, "png", new File("c://temp//" + "firstLineEndPoint" + firstLineEndPoint + "_("
					+ canv.a + "," + canv.b + ")" + "," + firstLineEndPoint + "," + crissNumberOfLines + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// painting paralelRepresentedLines to a file
		if (canv.isCrissWeave == false) {

			for (int i = 0; i < canv.paralelRepresentedLines.length; i++) {
				try {
					ImageIO.write(canv.paralelRepresentedLines[i], "png",
							new File("c://temp//" + "paralelRepresentedLines " + i + "_" + "firstLineEndPoint"
									+ firstLineEndPoint + "_(" + canv.a + "," + canv.b + ")" + "," + firstLineEndPoint
									+ "," + crissNumberOfLines + ".png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		for (int i = 0; i < canv.paralelRepresentedLinesStart.length; i++) {
			try {
				ImageIO.write(canv.paralelRepresentedLinesStart[i], "png",
						new File("c://temp//" + "paralelRepresentedLinesStart " + i + "_" + "firstLineEndPoint"
								+ firstLineEndPoint + "_(" + canv.a + "," + canv.b + ")" + "," + firstLineEndPoint + ","
								+ crissNumberOfLines + ".png"));
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
