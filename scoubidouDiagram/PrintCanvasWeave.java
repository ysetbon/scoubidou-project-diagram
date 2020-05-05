package scoubidouDiagram;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 * A static class for dealing with the cases of MyPrintCanvasDiagram in
 * isCrissWeave==true or ==false.
 * 
 * @author Yonatan Setbon
 */
public class PrintCanvasWeave {

	//we want to find minimumOfcase for changing backweaving if needed 
	public double minimumOfcaseTemp; 
	
	
	/**
	 * A simple construction 
	 */
	public PrintCanvasWeave() {
	}
	
	
	
	
	
	
	
	
	
	/**
	 * A function that fill BufferImages with their actuall pictures of strings that will be presented
	 * @param averageEvenTangetsHorizo
	 * @param averageEvenTangetsHorizoTemp
	 * @param averageOddTangetsHorizo
	 * @param averageOddTangetsHorizoTemp
	 * @param colors
	 * @param horizo
	 * @param horizoRepresentedLines
	 * @param horizoRepresentedLinesStart
	 * @param stitchRec
	 * @param g
	 * @param gBackUp
	 * @param width
	 * @param hight
	 * @param widthOfImage
	 * @param heightOfImage
	 * @param y1
	 * @param x1
	 * @param length
	 * @param stepOfAngle
	 */
	
	
	public  void WeaveCase(double averageEvenTangetsHorizo,double averageEvenTangetsHorizoTemp,
							double averageOddTangetsHorizo,double averageOddTangetsHorizoTemp,
							Color[]colors,
							nodeLine[] horizo, BufferedImage[] horizoRepresentedLines, BufferedImage[] horizoRepresentedLinesStart,
							Rectangle stitchRec ,
							Graphics g,Graphics gBackUp,
							int width ,int hight, int widthOfImage,int heightOfImage,
							int y1, int x1 ,int length ,
							double stepOfAngle , int [] backWeaving){
		
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
				System.out.println("averageOddTangetsHorizo: " + averageOddTangetsHorizo);
				System.out.println("averageEvenTangetsHorizo: " + averageEvenTangetsHorizo);
				// for finding the smallest dirivitive of angle
				double minimumOfcaseTemp = 10000;
				double minimumOfcase = 10000;

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
									stitchRec,backWeaving[i]);
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
									colors[indexOfHorizo], averageEvenTangetsHorizo, stitchRec,backWeaving[i]);

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
									averageEvenTangetsHorizo, stitchRec,backWeaving[i]);

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
									averageEvenTangetsHorizo, stitchRec,backWeaving[i]);
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
									stitchRec,backWeaving[i]);

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
									stitchRec,backWeaving[i]);

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
									averageOddTangetsHorizo, stitchRec,backWeaving[i]);

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
									averageOddTangetsHorizo, stitchRec,backWeaving[i]);

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

					//length between the first point and a 3/4 of a length of a small sqaure
					int Length = (int) Math
							.round(MyPrintCanvasDiagram.length - ((double) MyPrintCanvasDiagram.length / (double) 4));

					// the idial gap, that is the gap from option 3 of
					// StringDiagramTangent
					double distanceBetweenOptionThree = 2 * (MyPrintCanvasDiagram.length - (Length - R));

					// finding the difference of the average with the actuall
					// distances
					double averageDifferenceBetweenAverageAndDistance;
					// sum subtraction of the average with a distance
					double sumDifferenceBetweenAverageAndDistance = 0;
					System.out.println("horizo length = " + horizo.length);

					// the maximum of the differences of distances between
					// strings, if the maximum is getting smaller then we
					// continue the 'while
					// until we find the smallest maximum.
					double maxOfDifferencesOfcase = 0;
					// if there are only two strings in criss we try to make it
					// as close as case three of stringDiagramTangent
					if (horizo.length - 1 == 1) {

						double distanceFromTwoStringsHorizo = strngTempHorizo[0].representedLineAfterTransformation
								.ptLineDist(strngTempHorizo[1].representedLineAfterTransformation.getP1());
						sumDifferenceBetweenAverageAndDistance = sumDifferenceBetweenAverageAndDistance
								+ Math.abs(distanceFromTwoStringsHorizo - distanceBetweenOptionThree);
						sumDifferenceBetweenAverageAndDistance = sumDifferenceBetweenAverageAndDistance * 2;

					}
					// if there are more than two strings in criss, we find the
					// average of the subtraction of two close distances
					if (horizo.length - 1 > 1) {
						for (int i = 0; i < horizo.length - 2; i++) {
							double distanceFromTwoStringsHorizo = Math
									.abs(strngTempHorizo[i].representedLineAfterTransformation.ptLineDist(
											strngTempHorizo[i + 1].representedLineAfterTransformation.getP1()));
							double distanceFromThreeStringsHorizo = Math
									.abs(strngTempHorizo[i + 1].representedLineAfterTransformation.ptLineDist(
											strngTempHorizo[i + 2].representedLineAfterTransformation.getP1()));
							double distanceFromFourStringsHorizo = Math
									.abs(strngTempHorizo[2].representedLineAfterTransformation
											.ptLineDist(strngTempHorizo[3].representedLineAfterTransformation.getP1()));
							System.out.println("index " + i + " first distance " + distanceFromTwoStringsHorizo
									+ " second distance " + distanceFromThreeStringsHorizo);

							// setting maxOfDifferencesOfcase to the maximum of
							// differences between two close distances
							if (maxOfDifferencesOfcase < Math
									.abs(distanceFromTwoStringsHorizo - distanceFromThreeStringsHorizo)) {
								maxOfDifferencesOfcase = Math
										.abs(distanceFromTwoStringsHorizo - distanceFromThreeStringsHorizo);
							}
							if (distanceFromFourStringsHorizo == distanceFromTwoStringsHorizo) {
								maxOfDifferencesOfcase = 0;
								isDerivitiveDistanceHorizoStrings = true;
							}
							// sumDifferenceBetweenAverageAndDistance =
							// sumDifferenceBetweenAverageAndDistance
							// + Math.abs(distanceFromTwoStringsHorizo -
							// distanceFromThreeStringsHorizo);
						}

						// adding another time the distance of the center,
						// because we only counted it once.
						double distanceFromCenters = Math
								.abs(strngTempHorizo[horizo.length / 2].representedLineAfterTransformation.ptLineDist(
										strngTempHorizo[(horizo.length / 2) - 1].representedLineAfterTransformation
												.getP1()));
						double distanceFromThreeStringsHorizo = Math
								.abs(strngTempHorizo[horizo.length / 2].representedLineAfterTransformation.ptLineDist(
										strngTempHorizo[(horizo.length / 2) + 1].representedLineAfterTransformation
												.getP1()));

						sumDifferenceBetweenAverageAndDistance = sumDifferenceBetweenAverageAndDistance
								+ Math.abs(distanceFromThreeStringsHorizo - distanceFromCenters);

					}
					// average of the subtraction of average and distance
					averageDifferenceBetweenAverageAndDistance = sumDifferenceBetweenAverageAndDistance
							/ (double) (horizo.length);
					System.out.println(Math.abs(minimumOfcase) + "minimumOfcase horizo");

					// minimumOfcaseTemp will be the current minimum of this
					// itaration of while
					
					minimumOfcaseTemp = maxOfDifferencesOfcase;
					System.out.println(minimumOfcaseTemp + "minimumOfcaseTemp horizo");

					// if its exactly distanceBetweenOptionThree then don't
					// turn, because its probably case 3 from
					// stringDiagramTangent
					if (Math.abs(minimumOfcaseTemp) != 0) {
						if (t == 0 || t == 4) {
							minimumCaseZero = minimumOfcaseTemp;
							averageEvenTangetsHorizo = averageEvenTangetsHorizo - stepOfAngle;
							averageOddTangetsHorizo = averageOddTangetsHorizo - stepOfAngle;
							System.out.println("t= " + 4);
						}
						if (t == 1 || t == 3) {
							minimumCaseOne = minimumOfcaseTemp;
							averageEvenTangetsHorizo = averageEvenTangetsHorizo + stepOfAngle;
							averageOddTangetsHorizo = averageOddTangetsHorizo + stepOfAngle;
							System.out.println("t= " + 3);
						}
					}
					// if minimumOfcase is the lowest then it will exit the
					// while
					System.out.println(minimumOfcase);
					if (horizo.length - 1 == 1) {
						// if (minimumOfcase < minimumOfcaseTemp) {
						isDerivitiveDistanceHorizoStrings = true;
						// }
					}
					if (horizo.length - 1 > 1) {
						if (minimumOfcase <= minimumOfcaseTemp) {
							isDerivitiveDistanceHorizoStrings = true;
						}
					}

					minimumOfcase = minimumOfcaseTemp;
					this.minimumOfcaseTemp = minimumOfcaseTemp;

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
						/*
						 * if (counterZero > 100) {
						 * isDerivitiveDistanceHorizoStrings = true;
						 * minimumCaseZero = 10000; } if (counterOne > 100) {
						 * isDerivitiveDistanceHorizoStrings = true;
						 * minimumCaseOne = 10000; }
						 */
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

}
