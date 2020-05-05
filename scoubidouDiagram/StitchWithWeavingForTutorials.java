package scoubidouDiagram;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class StitchWithWeavingForTutorials {

	// thickness of the lines

	public static int lengthOfStringEnd = MyPrintCanvasDiagram.lengthOfStringEnd;
	// exporting the colors of the strings from MyprintCanvasDiagram
	public static Color[] colors = MyPrintCanvasDiagram.colors;
	// spaces in-between 2 lines
	public static int yOfStitch = 10 + lengthOfStringEnd;
	public static int length = MyPrintCanvasDiagram.length;
	// starting point of square (left up)+a little extra for the length of the
	// string
	public static int x1 = MyPrintCanvasDiagram.x1;
	public static int y1 = MyPrintCanvasDiagram.y1;

	// if true,criss is weaved.if false cross is weaved.
	public static Boolean isCrissWeave = true;

	public float[][] colorsRGB;
	// paralel and horizontal arrays
	static nodeLine[] paralel, horizo;

	public void dataOfStitch(int firstLineEndPoint, int crissNumberOfLines, int a, int b) {

		int k = 4 * a + 4 * b;
		boolean isLInside = false;
		int crossNumberOfLines = (k - 4 * crissNumberOfLines) / 4;

		nodePoint[] allPoints = new nodePoint[k];
		// getting all points
		allPoints = listNodePoints.listOfNodePoints();

		nodeLine l = new nodeLine(allPoints[0], allPoints[firstLineEndPoint], allPoints[0].color);
		nodeLine[] paralel = new nodeLine[crissNumberOfLines];
		paralel = stitchAlgoForPrintStitch.paralelReturnOneOption(l, crissNumberOfLines, colors);
		for (int i = 0; i < 2 * crissNumberOfLines; i++) {
			if (paralel[i] == l) {
				isLInside = true;
			}
		}

		nodeLine[] horizo = null;
		horizo = stitchAlgoForPrintStitch.paralelReturnOneOptionOposite(l, crissNumberOfLines, colors);
		paralel = new nodeLine[crossNumberOfLines];
		paralel = stitchAlgoForPrintStitch.paralelReturnOneOption(l, crossNumberOfLines, colors);
		// changing paralel and horizo parameters to what we got here
		StitchWithWeavingForTutorials.paralel = paralel;
		StitchWithWeavingForTutorials.horizo = horizo;
	}

	/**
	 * 
	 * @param firstLindEndPoint
	 * @param crissNumberOfLines
	 * @param a
	 * @param b
	 * @throws IOException
	 *             The method of getting a stitch with weaving.
	 */

	public void printPicture(int firstLineEndPoint, int crissNumberOfLines, int a, int b) throws IOException {
		int width = 2 * a * MyPrintCanvasDiagram.length + 2 * MyPrintCanvasDiagram.x1
				+ 3 * MyPrintCanvasDiagram.lengthOfStringEnd;
		int height = 2 * b * MyPrintCanvasDiagram.length + 2 * MyPrintCanvasDiagram.y1
				+ 3 * MyPrintCanvasDiagram.lengthOfStringEnd + 300;

		// Create a directory; all non-existent ancestor directories are
		File dir = new File("C:/temp/" + a + "_" + b + "_" + firstLineEndPoint + "_" + crissNumberOfLines);
		dir.mkdir();
		String dirString = dir.toString();
		dirString = dirString + "/";

		JFrame localwindow = new JFrame();
		localwindow.setAutoRequestFocus(true);
		// creating a set of colors for the strings
		Color[] colors = new Color[4 * a + 4 * b];
		/*
		 * When A selected color is implemented for (int i = 0; i <
		 * colors.length; i++) { float red = colorsRGB[i][0]; float green =
		 * colorsRGB[i][1]; float blue = colorsRGB[i][2]; Color randomColor =
		 * new Color((int) red, (int) green, (int) blue); colors[i] =
		 * randomColor; }
		 */

		for (int i = 0; i < colors.length; i++) {
			Random rand = new Random();
			float red = rand.nextFloat();
			float green = rand.nextFloat();
			float blue = rand.nextFloat();
			Color randomColor = new Color(red, green, blue);
			colors[i] = randomColor;
		}

		// image recImg is the image of the rectangle
		MyPrintCanvasDiagram drawRec = new MyPrintCanvasDiagram();
		drawRec.changeFirstLineEndPoint(firstLineEndPoint);
		drawRec.changeCrissNumberOfLines(crissNumberOfLines);
		drawRec.setA(a);
		drawRec.setB(b);
		// sets colors of criss and cross
		drawRec.colors = colors;
		// setting to print only criss strings
		drawRec.setIsCrissWeave(true);
		drawRec.setIsPaintRectangle(true);
		BufferedImage recImg = new BufferedImage(MyPrintCanvasDiagram.widthOfImage, MyPrintCanvasDiagram.heightOfImage,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D gdRec = recImg.createGraphics();
		drawRec.paint(gdRec);

		// extracting string starting positions
		BufferedImage[] startingPos = new BufferedImage[2 * a + 2 * b];
		PrintStartingPosition startpos = new PrintStartingPosition();
		startpos.colors = colors;
		// allPoints are points of the rectangle to draw vertical lines from
		int k = 4 * a + 4 * b;
		nodePoint[] allPoints = new nodePoint[k];
		allPoints = listNodePoints.listOfNodePoints();
		// draws starting string as buffer images, one for each string
		int counter = 0;
		for (int i = 0; i < 2 * a; i = i + 2) {
			startingPos[counter] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D gd2 = startingPos[counter].createGraphics();
			PrintStartingPosition.paintStringAndCircle((int) allPoints[i].x, (int) allPoints[i].y, "up", gd2, i);
			counter++;
		}
		for (int i = 2 * a; i < 2 * a + 2 * b; i = i + 2) {
			startingPos[counter] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D gd2 = startingPos[counter].createGraphics();
			PrintStartingPosition.paintStringAndCircle((int) allPoints[i].x, (int) allPoints[i].y, "right", gd2, i);
			counter++;
		}
		for (int i = 2 * a + 2 * b; i < 4 * a + 2 * b; i = i + 2) {
			startingPos[counter] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D gd2 = startingPos[counter].createGraphics();
			PrintStartingPosition.paintStringAndCircle((int) allPoints[i].x, (int) allPoints[i].y, "down", gd2, i);
			counter++;
		}
		for (int i = 4 * a + 2 * b; i < 4 * a + 4 * b; i = i + 2) {
			startingPos[counter] = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			Graphics2D gd2 = startingPos[counter].createGraphics();
			PrintStartingPosition.paintStringAndCircle((int) allPoints[i].x, (int) allPoints[i].y, "left", gd2, i);
			counter++;
		}
		// recImage is now the image of the rectangle
		recImg = drawRec.rectangleImage;

		MyPrintCanvasDiagram canv = new MyPrintCanvasDiagram();
		canv.changeFirstLineEndPoint(firstLineEndPoint);
		canv.changeCrissNumberOfLines(crissNumberOfLines);
		canv.setIsCrissWeave(true);
		canv.setIsPaintRectangle(true);
		canv.a = a;
		canv.b = b;
		canv.colors = colors;
		// Extracting horizo array and painting criss to an image
		BufferedImage crissImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gd = crissImg.createGraphics();
		canv.paint(gd);
		BufferedImage[] horizo = canv.horizoRepresentedLines;
		BufferedImage[] horizoStart = canv.horizoRepresentedLinesStart;

		// Extracting paralel array and painting cross to an image
		canv.setIsCrissWeave(false);
		canv.setIsPaintRectangle(false);
		BufferedImage crossImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gd2 = crossImg.createGraphics();
		canv.paint(gd2);
		BufferedImage[] paralel = canv.paralelRepresentedLines;
		BufferedImage[] paralelStart = canv.paralelRepresentedLinesStart;

		// receiving points to erase
		BufferedImage[] croppedSegments = new BufferedImage[paralel.length];
		counter = 0;
		// for getting into the horizo case
		boolean flagToHorizo = false;

		/*while (counter < paralelStart.length + horizoStart.length + 1) {
			BufferedImage result = CropImage.addTransparant(recImg);
			CropImage.addImage(result, recImg, 1, 0, 0);

			if (counter == 2) {
				flagToHorizo = true;
			}

			
			 * if (counter == paralelStart.length) { flagToHorizo = true; }
			 

			if (flagToHorizo == false) {
				for (int i = 0; i < horizoStart.length; i++) {
					CropImage.addImage(result, horizoStart[i], 1, 0, 0);

				}
				for (int i = counter; i < paralelStart.length; i++) {
					CropImage.addImage(result, paralelStart[i], 1, 0, 0);
				}
				for (int i = 0; i < counter; i++) {
					CropImage.addImage(result, paralel[i], 1, 0, 0);
				}

				try {
					// color the corners to transparent
					colorCorners(result, a, b);
					ImageIO.write(result, "png", new File(dirString + (counter + 1) + ".png"));
				} catch (IOException e) {
				}
				;
			}

		
			
			if (flagToHorizo == true) {
				BufferedImage paralelTotalEven = CropImage.addTransparant(recImg);
				BufferedImage paralelTotalOdd = CropImage.addTransparant(recImg);

				for (int i = 0; i < paralelStart.length; i++) {
					CropImage.addImage(result, paralel[i], 1, 0, 0);

				}
				for (int i = 0; i < paralelStart.length; i = i + 2) {
					CropImage.addImage(paralelTotalEven, paralel[i], 1, 0, 0);

				}
				for (int i = 1; i < paralelStart.length; i = i + 2) {
					CropImage.addImage(paralelTotalOdd, paralel[i], 1, 0, 0);

				}
				for (int i = 0; i < counter - paralelStart.length; i = i + 2) {
					BufferedImage intersect = CropImage.croppedIntersect(horizo[i], paralelTotalEven);
					CropImage.addImage(result, horizo[i], 1, 0, 0);
					CropImage.addImage(result, intersect, 1, 0, 0);
					try {
						// color the corners to transparent
						colorCorners(result, a, b);
						ImageIO.write(result, "png", new File(dirString + (counter + 1) + ".png"));
					} catch (IOException e) {
					}
				}

				for (int i = 1; i < counter - paralelStart.length; i = i + 2) {
					BufferedImage intersect = CropImage.croppedIntersect(horizo[i], paralelTotalOdd);
					CropImage.addImage(result, horizo[i], 1, 0, 0);
					CropImage.addImage(result, intersect, 1, 0, 0);
				}
				for (int i = counter - paralelStart.length; i < horizoStart.length; i++) {
					CropImage.addImage(result, horizoStart[i], 1, 0, 0);
				}
				try {
					// color the corners to transparent
					colorCorners(result, a, b);
					ImageIO.write(result, "png", new File(dirString + (counter + 1) + ".png"));
				} catch (IOException e) {
				}
				;
			}
			result = null;
			counter++;

		}*/
		
		//This will weave two strings from paralel and all strings from horizo
		while (counter < paralelStart.length + 2 + 1) {
			BufferedImage result = CropImage.addTransparant(recImg);
			CropImage.addImage(result, recImg, 1, 0, 0);

			if (counter == 3) {
				flagToHorizo = true;
			}

			/*
			 * if (counter == paralelStart.length) { flagToHorizo = true; }
			 */

			if (flagToHorizo == false) {
				
				//starting strings 
				for (int i = 0; i < horizoStart.length; i++) {
					CropImage.addImage(result, horizoStart[i], 1, 0, 0);

				}
				for (int i = counter; i < paralelStart.length; i++) {
					CropImage.addImage(result, paralelStart[i], 1, 0, 0);
				}
				
				//adding 0 2 or 1 paralel strings
				for (int i = 0; i < counter; i++) {
					CropImage.addImage(result, paralel[i], 1, 0, 0);
				}

				try {
					// color the corners to transparent
					colorCorners(result, a, b);
					ImageIO.write(result, "png", new File(dirString + (counter + 1) + ".png"));
				} catch (IOException e) {
				}
				;
			}

		
			
			if (flagToHorizo == true) {
				BufferedImage paralelTotalEven = CropImage.addTransparant(recImg);
				BufferedImage paralelTotalOdd = CropImage.addTransparant(recImg);
				//adding the two paralel strings 
				for (int i = 0; i < 2; i++) {
					CropImage.addImage(result, paralel[i], 1, 0, 0);

				}
				for (int i = 0; i < 1; i = i + 2) {
					CropImage.addImage(paralelTotalEven, paralel[i], 1, 0, 0);

				}
				for (int i = 1; i < 2; i = i + 2) {
					CropImage.addImage(paralelTotalOdd, paralel[i], 1, 0, 0);

				}
				for (int i = 0; i < counter - 2; i = i + 2) {
					BufferedImage intersect = CropImage.croppedIntersect(horizo[i], paralelTotalEven);
					CropImage.addImage(result, horizo[i], 1, 0, 0);
					CropImage.addImage(result, intersect, 1, 0, 0);
					try {
						// color the corners to transparent
						colorCorners(result, a, b);
						ImageIO.write(result, "png", new File(dirString + (counter + 1) + ".png"));
					} catch (IOException e) {
					}
				}

				for (int i = 1; i < counter - 2; i = i + 2) {
					BufferedImage intersect = CropImage.croppedIntersect(horizo[i], paralelTotalOdd);
					CropImage.addImage(result, horizo[i], 1, 0, 0);
					CropImage.addImage(result, intersect, 1, 0, 0);
				}
				
				//starting strings 
				for (int i = 2; i < paralelStart.length; i++) {
					CropImage.addImage(result, paralelStart[i], 1, 0, 0);
				}
				for (int i = counter - 2; i < horizoStart.length; i++) {
					CropImage.addImage(result, horizoStart[i], 1, 0, 0);
				}
				try {
					// color the corners to transparent
					colorCorners(result, a, b);
					ImageIO.write(result, "png", new File(dirString + (counter + 1) + ".png"));
				} catch (IOException e) {
				}
				;
			}
			result = null;
			counter++;

		}
	
		//This will weave all other paralel strings
		while (counter < paralelStart.length + horizoStart.length + 1) {
			BufferedImage result = CropImage.addTransparant(recImg);
			CropImage.addImage(result, recImg, 1, 0, 0);

			BufferedImage horizoTotalEven = CropImage.addTransparant(recImg);
			BufferedImage horizoTotalOdd = CropImage.addTransparant(recImg);

			for (int i = 0; i < horizoStart.length; i++) {
				CropImage.addImage(result, horizo[i], 1, 0, 0);

			}
			for (int i = 0; i < horizoStart.length; i = i + 2) {
				CropImage.addImage(horizoTotalEven, horizo[i], 1, 0, 0);

			}
			for (int i = 1; i < horizoStart.length; i = i + 2) {
				CropImage.addImage(horizoTotalOdd, horizo[i], 1, 0, 0);

			}
			//starts by weaving from the second paralel string
			for (int i = 1; i < counter - paralelStart.length; i = i + 2) {
				BufferedImage intersect = CropImage.croppedIntersect(paralel[i], horizoTotalEven);
				CropImage.addImage(result, paralel[i], 1, 0, 0);
				CropImage.addImage(result, intersect, 1, 0, 0);
				try {
					// color the corners to transparent
					colorCorners(result, a, b);
					ImageIO.write(result, "png", new File(dirString + (counter + 1) + ".png"));
				} catch (IOException e) {
				}
			}
			//starts by weaving from the second paralel string (odd numbers )

			for (int i = 0; i < counter - paralelStart.length; i = i + 2) {
				BufferedImage intersect = CropImage.croppedIntersect(paralel[i], horizoTotalOdd);
				CropImage.addImage(result, paralel[i], 1, 0, 0);
				CropImage.addImage(result, intersect, 1, 0, 0);
			}
			//adding starting string in paralel 
			for (int i = counter - paralelStart.length; i < paralelStart.length ; i++) {
				
				CropImage.addImage(result, paralelStart[i], 1, 0, 0);
			}
			try {
				// color the corners to transparent
				colorCorners(result, a, b);
				ImageIO.write(result, "png", new File(dirString + (counter + 1) + ".png"));
			} catch (IOException e) {
			}
			;
			result = null;
			counter++;
				
		}
		
		

	}

	public static void main(String[] args) throws IOException {
		JFrame localwindow = new JFrame();
		localwindow.setAutoRequestFocus(true);

		int a = 1;
		int b = 1;
		MyPrintCanvasDiagram.a = a;
		MyPrintCanvasDiagram.b = b;
	//	int firstLineEndPoint = 2 * a + 2 * b + 2 * a - 3;
		int firstLineEndPoint =5;

		int crissNumberOfLines =1;
		StitchWithWeavingForTutorials wea = new StitchWithWeavingForTutorials();
		// wea.colorsRGB = new float[12][3];

		/*
		 * colors[0] = new Color(1,143,21); colors[1] = new Color(1,143,21);
		 * 
		 * colors[2] = new Color(255,255,255); colors[3] = new
		 * Color(152,121,82);
		 * 
		 * colors[4] =new Color(1,143,21); colors[5] = new Color(255,255,255);
		 * 
		 * colors[6] = new Color(1,143,21); colors[7] = new Color(1,143,21);
		 * 
		 * colors[8] = new Color(255,255,255); colors[9] = new
		 * Color(152,121,82);
		 * 
		 * colors[10] = new Color(152,121,82); colors[11] = new
		 * Color(255,255,255);
		 */

		/*
		 * green brown white wea.colorsRGB[0][0] = 1; wea.colorsRGB[0][1] = 143;
		 * wea.colorsRGB[0][2] = 21; wea.colorsRGB[1] = wea.colorsRGB[0];
		 * wea.colorsRGB[6] = wea.colorsRGB[0];
		 * 
		 * wea.colorsRGB[2][0] = 255; wea.colorsRGB[2][1] = 255;
		 * wea.colorsRGB[2][2] = 255; wea.colorsRGB[5] = wea.colorsRGB[2];
		 * wea.colorsRGB[8] = wea.colorsRGB[2]; wea.colorsRGB[11] =
		 * wea.colorsRGB[2];
		 * 
		 * wea.colorsRGB[3][0] = 152; wea.colorsRGB[3][1] = 121;
		 * wea.colorsRGB[3][2] = 82; wea.colorsRGB[4] = wea.colorsRGB[3];
		 * wea.colorsRGB[9] = wea.colorsRGB[3]; wea.colorsRGB[10] =
		 * wea.colorsRGB[3];
		 * 
		 */

		/*
		 * wea.colorsRGB[0][0] = 113; wea.colorsRGB[0][1] = 48;
		 * wea.colorsRGB[0][2] = 146; wea.colorsRGB[1] = wea.colorsRGB[0];
		 * 
		 * wea.colorsRGB[2][0] = 157; wea.colorsRGB[2][1] = 139;
		 * wea.colorsRGB[2][2] = 206; wea.colorsRGB[3] = wea.colorsRGB[2];
		 * 
		 * wea.colorsRGB[4][0] = 255; wea.colorsRGB[4][1] = 255;
		 * wea.colorsRGB[4][2] = 255; wea.colorsRGB[5] = wea.colorsRGB[4];
		 * 
		 * wea.colorsRGB[6][0] = wea.colorsRGB[0][0]; wea.colorsRGB[6][1] =
		 * wea.colorsRGB[0][1]; wea.colorsRGB[6][2] = wea.colorsRGB[0][2];
		 * wea.colorsRGB[7] = wea.colorsRGB[6];
		 * 
		 * wea.colorsRGB[8][0] = wea.colorsRGB[4][0]; wea.colorsRGB[8][1] =
		 * wea.colorsRGB[4][1]; wea.colorsRGB[8][2] = wea.colorsRGB[4][2];
		 * wea.colorsRGB[9] = wea.colorsRGB[8];
		 * 
		 * wea.colorsRGB[10][0] = wea.colorsRGB[2][0]; wea.colorsRGB[10][1] =
		 * wea.colorsRGB[2][1]; wea.colorsRGB[10][2] = wea.colorsRGB[2][2];
		 * wea.colorsRGB[11] = wea.colorsRGB[10];
		 */

		wea.printPicture(firstLineEndPoint, crissNumberOfLines, a, b);

	}

	public void colorCorners(BufferedImage result, int a, int b) {
		// setting edges of the images to transparent, becuase sometimes it sets
		// to some black color
		int redCorner = 0;// red component 0...255
		int greenCorner = 0;// green component 0...255
		int blueCorner = 0;// blue component 0...255
		int alphaCorner = 0;// alpha (transparency) component 0...255
		int colCorner = (alphaCorner << 24) | (redCorner << 16) | (greenCorner << 8) | blueCorner;
		// width and height of the rectangle base of the stitch
		int width = 2 * a * MyPrintCanvasDiagram.length;
		int height = 2 * b * MyPrintCanvasDiagram.length;
		int corner2 = 3;
		int corner3 = 4;
		result.setRGB(x1 - corner2, y1 - 1, colCorner);
		result.setRGB(x1 - corner2, y1, colCorner);
		result.setRGB(x1 - corner2, y1 + 1, colCorner);
		result.setRGB(x1 - corner2, y1 + 2, colCorner);
		result.setRGB(x1 - corner3, y1 - 1, colCorner);
		result.setRGB(x1 - corner3, y1, colCorner);
		result.setRGB(x1 - corner3, y1 + 1, colCorner);
		result.setRGB(x1 - corner3, y1 + 2, colCorner);

		result.setRGB(x1 + width + 1, y1 - corner2, colCorner);
		result.setRGB(x1 + width - 1, y1 - corner2, colCorner);
		result.setRGB(x1 + width - 2, y1 - corner2, colCorner);
		result.setRGB(x1 + width, y1 - corner2, colCorner);
		result.setRGB(x1 + width + 1, y1 - corner3, colCorner);
		result.setRGB(x1 + width - 1, y1 - corner3, colCorner);
		result.setRGB(x1 + width - 2, y1 - corner3, colCorner);
		result.setRGB(x1 + width, y1 - corner3, colCorner);

		result.setRGB(x1 - 1, y1 + height + corner2, colCorner);
		result.setRGB(x1, y1 + height + corner2, colCorner);
		result.setRGB(x1 + 1, y1 + height + corner2, colCorner);
		result.setRGB(x1 + 2, y1 + height + corner2, colCorner);
		result.setRGB(x1 - 1, y1 + height + corner3, colCorner);
		result.setRGB(x1, y1 + height + corner3, colCorner);
		result.setRGB(x1 + 1, y1 + height + corner3, colCorner);
		result.setRGB(x1 + 2, y1 + height + corner3, colCorner);

		result.setRGB(x1 + width + corner2, y1 + height + 1, colCorner);
		result.setRGB(x1 + width + corner2, y1 + height - 1, colCorner);
		result.setRGB(x1 + width + corner2, y1 + height - 2, colCorner);
		result.setRGB(x1 + width + corner2, y1 + height, colCorner);
		result.setRGB(x1 + width + corner3, y1 + height + 1, colCorner);
		result.setRGB(x1 + width + corner3, y1 + height - 1, colCorner);
		result.setRGB(x1 + width + corner3, y1 + height - 2, colCorner);
		result.setRGB(x1 + width + corner3, y1 + height, colCorner);

	}

}
