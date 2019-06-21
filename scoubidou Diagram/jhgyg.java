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

public class StitchWithWeahjfhving {

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

	Color firstColor = Color.BLACK;
	Color secondColor = Color.BLACK;

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
		StitchWithWeaving.paralel = paralel;
		StitchWithWeaving.horizo = horizo;
	}

	/**
	 * 
	 * @param crissColor
	 * @param crossColor
	 *            sets colors of criss and cross strings
	 */
	public void setColorsOfStrings(Color crissColor, Color crossColor) {
		this.firstColor = crissColor;
		this.secondColor = crossColor;
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
				+ 2 * MyPrintCanvasDiagram.lengthOfStringEnd;
		int height = 2 * b * MyPrintCanvasDiagram.length + 2 * MyPrintCanvasDiagram.y1;
		JFrame localwindow = new JFrame();
		localwindow.setAutoRequestFocus(true);
		// creating a set of colors for the strings
		Color[] colors = new Color[4 * a + 4 * b];

		for (int i = 0; i < colors.length; i++) {
			Random rand = new Random();
			float red = rand.nextFloat();
			float green = rand.nextFloat();
			float blue = rand.nextFloat();
			Color randomColor = new Color(red, green, blue);
			colors[i] = randomColor;
		}

		for (int i = 0; i < colors.length; i++) {
			Random rand = new Random();
			float red = rand.nextFloat();
			float green = rand.nextFloat();
			float blue = rand.nextFloat();
			Color randomColor = new Color(red, green, blue);
			colors[i] = randomColor;
			colors[i] = colors[i % 16];
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

		// Extracting paralel array and painting cross to an image
		canv.setIsCrissWeave(false);
		canv.setIsPaintRectangle(false);
		BufferedImage crossImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gd2 = crossImg.createGraphics();
		canv.paint(gd2);
		BufferedImage[] paralel = canv.paralelRepresentedLines;

		// receiving points to erase
		BufferedImage[] croppedSegments = new BufferedImage[paralel.length];

		BufferedImage horizoEven = CropImage.addTransparant(horizo[0]);
		for (int i = 0; i < horizo.length; i = i + 2) {
			CropImage.addImage(horizoEven, horizo[i], 1, 0, 0);
		}
		BufferedImage horizoOdd = CropImage.addTransparant(horizo[1]);
		for (int i = 1; i < horizo.length; i = i + 2) {
			CropImage.addImage(horizoOdd, horizo[i], 1, 0, 0);
		}
		BufferedImage paralelOdd = CropImage.addTransparant(horizo[1]);
		for (int i = 1; i < paralel.length; i = i + 2) {
			CropImage.addImage(paralelOdd, paralel[i], 1, 0, 0);
		}
		/*
		 * try { ImageIO.write(horizoEven, "png", new File("c://temp//" +
		 * "horizoEven"+ "_" + firstLineEndPoint + "_" + crissNumberOfLines +
		 * "_" + a + "X" + b + ".png")); } catch (IOException e){};
		 * 
		 * try { ImageIO.write(horizoOdd, "png", new File("c://temp//" +
		 * "horizoOdd"+ "_" + firstLineEndPoint + "_" + crissNumberOfLines + "_"
		 * + a + "X" + b + ".png")); } catch (IOException e){}; try {
		 * ImageIO.write(paralelOdd, "png", new File("c://temp//" +
		 * "paralelOdd"+ "_" + firstLineEndPoint + "_" + crissNumberOfLines +
		 * "_" + a + "X" + b + ".png")); } catch (IOException e){};
		 */

		int croppedSegmentIndex = 0;

		for (int j = 0; j < paralel.length; j = j + 2) {
			croppedSegments[croppedSegmentIndex] = CropImage.croppedIntersect(horizoEven, paralel[j]);
			croppedSegmentIndex++;

		}
		for (int j = 1; j < paralel.length; j = j + 2) {
			croppedSegments[croppedSegmentIndex] = CropImage.croppedIntersect(horizoOdd, paralel[j]);
			/*
			 * try { ImageIO.write(croppedSegments[croppedSegmentIndex], "png",
			 * new File("c://temp//" + "croppedSegments"+croppedSegmentIndex+
			 * "_" + firstLineEndPoint + "_" + crissNumberOfLines + "_" + a +
			 * "X" + b + ".png")); } catch (IOException e){};
			 */
			croppedSegmentIndex++;

		}
		BufferedImage result = CropImage.addTransparant(recImg);
		CropImage.addImage(result, recImg, 1, 0, 0);
		for (int i = 0; i < paralel.length; i++) {
			CropImage.addImage(result, paralel[i], 1, 0, 0);
		}
		for (int i = 0; i < horizo.length; i++) {
			CropImage.addImage(result, horizo[i], 1, 0, 0);
		}
		for (int i = 0; i < croppedSegments.length; i++) {
			CropImage.addImage(result, croppedSegments[i], 1, 0, 0);
		}
		try {
			ImageIO.write(result, "png", new File("c://temp//" + "result" + "_" + firstLineEndPoint + "_"
					+ crissNumberOfLines + "_" + a + "X" + b + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		JFrame localwindow = new JFrame();
		localwindow.setAutoRequestFocus(true);
		int a = 2;
		int b = 10;
		int firstLineEndPoint = 27;
		int crissNumberOfLines = 10;
		StitchWithWeaving wea = new StitchWithWeaving();
		wea.printPicture(firstLineEndPoint, crissNumberOfLines, a, b);

		/*
		 * int j=3;
		 * 
		 * while(j<=(2*a+2*b-1)){ for(int criss=1; criss<=((j-1)/2);criss++){
		 * wea.printPicture(j, criss, a, b); } j=j+2;
		 * 
		 * } while(j<=(4*a+4*b-3)){ for(int criss=1;
		 * criss<=(4*a+4*b-1-j)/2;criss++){ wea.printPicture(j, criss, a, b); }
		 * j=j+2; }
		 */
	}

}
