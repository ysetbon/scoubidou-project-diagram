
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class StitchWithWeaving {

	// thickness of the lines
	public static int stringWidth = MyPrintCanvasDiagram.stringWidth;
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

	// if true,criss is weaved.if false cross is weaved.
	public static Boolean isCrissWeave = true;

	int lineStart = b;
	int lineStartOp = a;
	static int firstLineEndPoint = 7;
	static int crissNumberOfLines = 1;
	Color firstColor = Color.BLACK;
	Color secondColor = Color.BLACK;

	// paralel and horizontal arrays
	static nodeLine[] paralel, horizo;

	public void dataOfStitch(int firstLindEndPoint, int crissNumberOfLines, int a, int b) {

		int k = 4 * a + 4 * b;
		boolean isLInside = false;
		int crossNumberOfLines = (k - 4 * crissNumberOfLines) / 4;

		nodePoint[] allPoints = new nodePoint[k];
		// getting all points
		allPoints = listNodePoints.listOfNodePoints();

		nodeLine l = new nodeLine(allPoints[0], allPoints[firstLineEndPoint]);
		nodeLine[] paralel = new nodeLine[crissNumberOfLines];
		paralel = stitchAlgoForPrintStitch.paralelReturnOneOption(l, crissNumberOfLines);
		for (int i = 0; i < 2 * crissNumberOfLines; i++) {
			if (paralel[i] == l) {
				isLInside = true;
			}
		}

		nodeLine[] horizo = null;
		horizo = stitchAlgoForPrintStitch.paralelReturnOneOptionOposite(l, crissNumberOfLines);
		nodeLine temp = null;
		/*
		 * temp = horizo[0]; horizo[0] = horizo[1]; horizo[1] =temp;
		 */
		paralel = new nodeLine[crossNumberOfLines];
		paralel = stitchAlgoForPrintStitch.paralelReturnOneOption(l, crossNumberOfLines);
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
	 * @param firstLindEndPoint
	 * @param crissNumberOfLines
	 * @param a
	 * @param b
	 * @throws IOException
	 *             The method of getting a stitch with weaving.
	 */
	public void printPicture(int firstLindEndPoint, int crissNumberOfLines, int a, int b, Color colrCriss,
			Color colrCross) throws IOException {
		int width = 2 * a * MyPrintCanvasDiagram.length + 2 * MyPrintCanvasDiagram.x1
				+ 2 * MyPrintCanvasDiagram.lengthOfStringEnd + 300;
		int height = 2 * b * MyPrintCanvasDiagram.length + 2 * MyPrintCanvasDiagram.y1 + 300;
		JFrame localwindow = new JFrame();
		localwindow.setAutoRequestFocus(true);

		Color[] colors = new Color[4 * a + 4 * b];
		for (int i = 0; i < colors.length; i++) {
			Random rand = new Random();
			float red = rand.nextFloat();
			float green = rand.nextFloat();
			float blue = rand.nextFloat();
			colors[i] = new Color(183, 159, 200);
		}

		colors[0] = new Color(68, 161, 150);
		colors[2] = new Color(202, 117, 63);
		colors[4] = new Color(3, 83, 180);
		colors[6] = new Color(68, 161, 150);
		colors[8] = new Color(202, 117, 63);
		colors[10] = new Color(3, 83, 180);
		 colors[12] = new Color(163,123,214);
		 colors[14] = new Color(163,123,214) ;

		/*
		 * colors[8] = new Color(69 ,139 ,116); colors[9] = new Color(69, 139
		 * ,116); colors[10] = new Color( 250 ,191, 171); colors[11] = new
		 * Color( 250, 191, 171); colors[12] =new Color( 255 ,165 ,0) ;
		 * colors[13] = new Color( 255, 165, 0); colors[14] = new Color( 255
		 * ,255, 255); colors[15] = new Color( 255 ,255, 255);
		 * 
		 * colors[16] =new Color( 150, 0 ,15) ; colors[17] =new Color( 150, 0
		 * ,15) ; colors[18] =new Color( 255 ,78 ,122) ; colors[19] = new Color(
		 * 255, 78 ,122); colors[20] = new Color(120, 78 ,35); colors[21] = new
		 * Color(120 ,78 ,35); colors[22] =new Color( 9 ,9 ,206) ; colors[23]
		 * =new Color( 9, 9 ,206) ;
		 */

		// localwindow.setLocation(firstLindEndPoint*10, firstLindEndPoint*10);
		MyPrintCanvasDiagram canv = new MyPrintCanvasDiagram();
		// setting the colors of the strings
		canv.colors = colors;
		canv.changeFirstLineEndPoint(firstLindEndPoint);
		canv.changeCrissNumberOfLines(crissNumberOfLines);
		canv.setA(a);
		canv.setB(b);
		canv.setWidthOfStitch(stringWidth, 6);
		// sets colors of criss and cross
		canv.setColorsOfStrings(colrCriss, colrCross);
		// setting to print only criss strings
		canv.setIsCrissWeave(true);
		canv.setIsPaintRectangle(true);

		// paints a rectangle picure
		BufferedImage recImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gdRec = recImg.createGraphics();
		canv.paint(gdRec);

		/////////////////////////////// sending images to gif
		try {
			ImageIO.write(recImg, "png", new File("c://temp//gif//" + 0 + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D tempGraphics = temp.createGraphics();
		canv.setIsCrissWeave(true);

		// paints the criss strings
		canv.paint(tempGraphics);

		// getting bounds of horizo strings
		Rectangle2D[] horizoBounds = canv.rectangleShapeHorizo;
		BufferedImage[] horizo = canv.horizoRepresentedLines;
		BufferedImage[] horizoBottom = canv.horizoRepresentedLinesBottom;

		// extracting the paralels bufferImages
		BufferedImage temp2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D tempGraphics2 = temp.createGraphics();
		canv.setIsCrissWeave(false);
		canv.paint(tempGraphics2);

		BufferedImage[] paralelBottom = canv.paralelRepresentedLinesBottom;
		BufferedImage[] paralel = canv.paralelRepresentedLines;

		BufferedImage crissImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < canv.horizoRepresentedLines.length; i++) {
			CropImage.addImage(crissImg, horizoBottom[i], 1, 0, 0);
		}
		for (int i = 0; i < canv.paralelRepresentedLines.length; i++) {
			CropImage.addImage(crissImg, paralelBottom[i], 1, 0, 0);
		}
		CropImage.addImage(crissImg, recImg, 1, 0, 0);

		for (int i = 0; i < canv.horizoRepresentedLines.length; i++) {
			CropImage.addImage(crissImg, horizo[i], 1, 0, 0);
		}

		BufferedImage crossImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < canv.paralelRepresentedLines.length; i++) {
			CropImage.addImage(crossImg, paralel[i], 1, 0, 0);
		}

		////////////// adding the layers. First criss then cross then
		////////////// croppedSegments

		CropImage.addImage(crissImg, crossImg, 1, 0, 0);

		BufferedImage[] croppedSegments = new BufferedImage[horizo.length];
		BufferedImage paralelEven = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < paralel.length; i = i + 2) {
			CropImage.addImage(paralelEven, paralel[i], 1, 0, 0);
		}

		BufferedImage paralelOdd = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		for (int i = 1; i < paralel.length; i = i + 2) {
			CropImage.addImage(paralelOdd, paralel[i], 1, 0, 0);
		}

		System.out.println("horizo-" + horizo.length + "paralel-" + paralel.length);
		int croppedSegmentIndex = 0;
		for (int j = 0; j < horizo.length; j = j + 2) {

			croppedSegments[croppedSegmentIndex] = CropImage.croppedIntersect(paralelOdd, horizo[j], horizoBounds[j]);

			croppedSegmentIndex++;

		}
		for (int j = 1; j < horizo.length; j = j + 2) {
			croppedSegments[croppedSegmentIndex] = CropImage.croppedIntersect(paralelEven, horizo[j], horizoBounds[j]);

			croppedSegmentIndex++;

		}
		for (int i = 0; i < croppedSegments.length; i++) {
			CropImage.addImage(crissImg, croppedSegments[i], 1, 0, 0);
		}
		System.out.println("cropped segemnts is " + croppedSegments.length);
		/////////////////////
		// final picture
		BufferedImage returnImg = crissImg;
		try {
			ImageIO.write(returnImg, "png", new File("c://temp//" + "basicStitch" + "_" + firstLindEndPoint + "_"
					+ crissNumberOfLines + "_" + a + "X" + b + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedImage seg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		for (int i = 0; i < croppedSegments.length; i++) {
			CropImage.addImage(seg, croppedSegments[i], 1, 0, 0);
		}

		///////////////////// sending all string pictures to temp/gif
		int counterGif = 1;
		for (int i = 0; i < horizo.length; i++) {
			try {
				ImageIO.write(horizo[i], "png", new File("c://temp//gif//" + counterGif + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			counterGif++;
		}
		for (int i = 0; i < paralel.length; i++) {
			try {
				ImageIO.write(paralel[i], "png", new File("c://temp//gif//" + counterGif + ".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			counterGif++;
		}

		if (horizoBottom != null) {
			for (int i = 0; i < horizoBottom.length; i++) {
				if (horizoBottom[i] != null) {
					try {
						ImageIO.write(horizoBottom[i], "png", new File("c://temp//gif//" + counterGif + ".png"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					counterGif++;
				}
			}
		}
		if (paralelBottom != null) {
			for (int i = 0; i < paralelBottom.length; i++) {
				if (paralelBottom[i] != null) {
					try {
						ImageIO.write(paralelBottom[i], "png", new File("c://temp//gif//" + counterGif + ".png"));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					counterGif++;
				}
			}
		}
	}

	private static String toString(long nanoSecs) {
		int minutes = (int) (nanoSecs / 60000000000.0);
		int seconds = (int) (nanoSecs / 1000000000.0) - (minutes * 60);
		int millisecs = (int) (((nanoSecs / 1000000000.0) - (seconds + minutes * 60)) * 1000);

		if (minutes == 0 && seconds == 0)
			return millisecs + "ms";
		else if (minutes == 0 && millisecs == 0)
			return seconds + "s";
		else if (seconds == 0 && millisecs == 0)
			return minutes + "min";
		else if (minutes == 0)
			return seconds + "s " + millisecs + "ms";
		else if (seconds == 0)
			return minutes + "min " + millisecs + "ms";
		else if (millisecs == 0)
			return minutes + "min " + seconds + "s";

		return minutes + "min " + seconds + "s " + millisecs + "ms";
	}

	public static void main(String[] args) throws IOException {
		long startTime = System.nanoTime();
		JFrame localwindow = new JFrame();
		localwindow.setAutoRequestFocus(true);
		int a = 1;
		int b = 1;
		int firstLindEndPoint = 3;
		int crissNumberOfLines = 1;
		// localwindow.setLocation(firstLindEndPoint*10, firstLindEndPoint*10);
		MyPrintCanvasDiagram canv = new MyPrintCanvasDiagram();
		StitchWithWeaving wea = new StitchWithWeaving();

		wea.firstLineEndPoint = firstLindEndPoint;
		wea.crissNumberOfLines = crissNumberOfLines;
		wea.a = a;
		wea.b = b;

		wea.printPicture(firstLindEndPoint, crissNumberOfLines, a, b, Color.RED, Color.BLUE);
		long endTime = System.nanoTime();
		System.out.println(String.format("%-2d: %s", 1, toString(endTime - startTime)));
	}

}
