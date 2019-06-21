

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class StitchWithWeavingTextForTutorials {
	public static int stringlength = 150;
	public static int stringWidth = MyPrintCanvasDiagram.stringWidth;
	public static int recWidth = MyPrintCanvasDiagram.recWidth;

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

	// starting strings imgages
	public static BufferedImage[] horizoStarting;
	public static BufferedImage[] paralelStarting;
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
		temp = horizo[0];
		horizo[0] = horizo[1];
		horizo[1] = temp;
		paralel = new nodeLine[crossNumberOfLines];
		paralel = stitchAlgoForPrintStitch.paralelReturnOneOption(l, crossNumberOfLines);
		// changing paralel and horizo parameters to what we got here
		StitchWithWeavingTextForTutorials.paralel = paralel;
		StitchWithWeavingTextForTutorials.horizo = horizo;
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

	public void printPicture(int firstLindEndPoint, int crissNumberOfLines, int a, int b) throws IOException {

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
			//colors[i] = new Color(red, green, blue);
			colors[i] = new Color(183, 159, 200);
		}
		

		colors[0] = new Color(140, 108, 132);
		colors[2] = new Color(107, 167, 17);
		colors[4] = new Color(140, 108, 132);
		colors[6] = new Color(107, 167, 17);
        colors[8] = new Color(202, 117, 63);
	//	colors[10] = new Color(3, 83, 180);
	/*	    colors[0] = new Color(87,87,87);
			colors[1] = new Color(87,87,87);
			colors[2] = new Color(217,61,13);
			colors[3] = new Color(255,193,222);
			colors[4] = new Color(255,193,222);
			colors[5] = new Color(217,61,13);
			colors[6] = new Color(87,87,87);
			colors[7] = new Color(87,87,87);
			colors[8] = new Color(217,61,13) ;			
			colors[9] = new Color(255,193,222);
			colors[10] = new Color(255,193,222);
			colors[11] = new Color(217,61,13);
			*/
			
		// creates the starting strings images
		BasicStroke strokeBorder = new BasicStroke(stringWidth, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0.1F);
		shapeLineStarting(firstLindEndPoint, crissNumberOfLines, a, b, strokeBorder, stringWidth, colors);

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
		canv.setColorsOfStrings(Color.BLACK, Color.BLACK);
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
		BufferedImage[] horizoTemp = canv.horizoRepresentedLines;
		// extracting the paralels bufferImages
		BufferedImage temp2 = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D tempGraphics2 = temp.createGraphics();
		canv.setIsCrissWeave(false);
		canv.paint(tempGraphics2);

		BufferedImage[] paralelBottom = canv.paralelRepresentedLinesBottom;
		BufferedImage[] paralel = canv.paralelRepresentedLines;
		Rectangle2D[] paralelBounds = canv.rectangleShapeParalel;
		BufferedImage[] paralelTemp = canv.paralelRepresentedLines;

	//BufferedImage tempo = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		//tempo= horizo[1];
	//horizo[1] = horizo[0];
		//horizo[0] = tempo;
	
		BufferedImage crissImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		for (int i = 0; i < canv.horizoRepresentedLines.length; i++) {
			CropImage.addImage(crissImg, horizoBottom[i], 1, 0, 0);
		//	CropImage.addImage(crissImg, horizoStarting[i], 1, 0, 0);
		}
		for (int i = 0; i < canv.paralelRepresentedLines.length; i++) {
			CropImage.addImage(crissImg, paralelBottom[i], 1, 0, 0);
		//	CropImage.addImage(crissImg, paralelStarting[i], 1, 0, 0);
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

		////////////////////////////////////////////////// creates pictures for
		////////////////////////////////////////////////// tutorials
		//////////////////////////////////////////////////
		////////// first changing segemnt to paralel instead horizo
		
		try {
			ImageIO.write(horizo[0], "png", new File("c://temp//" +"horizo0"+ ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ImageIO.write(horizo[1], "png", new File("c://temp//" +"horizo1"+ ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ImageIO.write(paralel[0], "png", new File("c://temp//" +"paralel0"+ ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ImageIO.write(paralel[1], "png", new File("c://temp//" +"paralel1"+ ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int counterForFinalDiagrams = 0;
		croppedSegments = new BufferedImage[paralel.length];
		BufferedImage horizoEven = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		for (int i = 0; i < horizo.length; i = i + 2) {
			CropImage.addImage(horizoEven, horizo[i], 1, 0, 0);
		}

		BufferedImage horizoOdd = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		for (int i = 1; i < horizo.length; i = i + 2) {
			CropImage.addImage(horizoOdd, horizo[i], 1, 0, 0);
		}
		 croppedSegmentIndex = 0;
		 
		for (int j = 0; j < paralel.length; j = j + 2) {			
			for (int i = 0; i < horizo.length; i = i + 2) {
			croppedSegments[j] = CropImage.croppedIntersect( paralel[j],horizo[i], paralelBounds[j]);
			}
		}
		
		for (int j = 1; j < paralel.length; j = j + 2) {			
			for (int i = 1; i < horizo.length; i = i + 2) {
			croppedSegments[j] = CropImage.croppedIntersect( paralel[j],horizo[i], paralelBounds[j]);
			}
		}
		//finishing segments changing
		/////////////////////////////
		
		
		// horizo pictures, first picture is non woven
		for (int indexOfStitch = 0; indexOfStitch < canv.horizoRepresentedLines.length; indexOfStitch++) {
			// refreshing horizo strings
			crissImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

			// adding starting strings
			for (int i = indexOfStitch; i < canv.horizoRepresentedLines.length; i++) {
				CropImage.addImage(crissImg, horizoStarting[i], 1, 0, 0);
			}

			for (int i = 0; i < canv.paralelRepresentedLines.length; i++) {
				CropImage.addImage(crissImg, paralelStarting[i], 1, 0, 0);
			}

			CropImage.addImage(crissImg, recImg, 1, 0, 0);
			// adding
			for (int i = indexOfStitch - 1; i >= 0; i--) {
				CropImage.addImage(crissImg, horizo[i], 1, 0, 0);
			}

			try {
				ImageIO.write(crissImg, "png", new File("c://temp//" + counterForFinalDiagrams+ ".png"));
				counterForFinalDiagrams++;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		////////// now paralel pictures, first picture is no paralels
		//////////////////////////////////////////
	
		for (int indexOfStitch = 0; indexOfStitch < canv.paralelRepresentedLines.length; indexOfStitch++) {
			// refreshing horizo strings
			crissImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			
			// adding starting strings
			
			for (int i = indexOfStitch; i < canv.paralelRepresentedLines.length; i++) {
				CropImage.addImage(crissImg, paralelStarting[i], 1, 0, 0);
			}

			CropImage.addImage(crissImg, recImg, 1, 0, 0);
			// adding all horizo strings
			for (int i = 0; i < canv.horizoRepresentedLines.length; i++) {
				CropImage.addImage(crissImg, horizo[i], 1, 0, 0);
			}
			
			for (int i = indexOfStitch-1; i >= 0; i--) {
				CropImage.addImage(crissImg, paralel[i], 1, 0, 0);
				
			}
			
			//adding cropped segements
			for (int i = indexOfStitch-1; i >= 0; i--) {
				CropImage.addImage(crissImg, croppedSegments[i], 1, 0, 0);
				
			}					
			
			try {
				ImageIO.write(crissImg, "png", new File("c://temp//" + counterForFinalDiagrams+ ".png"));
				counterForFinalDiagrams++;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		////////painting last stitch
		crissImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		
		CropImage.addImage(crissImg, recImg, 1, 0, 0);
		// adding all horizo strings
		for (int i = 0; i < canv.horizoRepresentedLines.length; i++) {
			CropImage.addImage(crissImg, horizo[i], 1, 0, 0);
		}
		
		for (int i =  canv.paralelRepresentedLines.length-1; i >= 0; i--) {
			CropImage.addImage(crissImg, paralel[i], 1, 0, 0);
			
		}
		//adding cropped segements
		for (int i =  canv.paralelRepresentedLines.length-1; i >= 0; i--) {
			CropImage.addImage(crissImg, croppedSegments[i], 1, 0, 0);
			
		}					
		
		try {
			ImageIO.write(crissImg, "png", new File("c://temp//" + counterForFinalDiagrams+ ".png"));
			counterForFinalDiagrams++;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//// getting starting strings to temp/gif
		// adding starting strings
		
	   int counter =0;
		for (int i = 0; i <horizoStarting.length; i++) {
			try{
			ImageIO.write(horizoStarting[i], "png", new File(
					"c://temp//gif//" + "a starting string" + "_" + counter + "_" + "_" + a + "X" + b + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			counter ++;
		}
			
		for (int i = 0; i <paralelStarting.length; i++) {
			try{
			ImageIO.write(paralelStarting[i], "png", new File(
					"c://temp//gif//" + "a starting string" + "_" + counter + "_" + "_" + a + "X" + b + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			counter ++;
		}
		
		try {
			ImageIO.write(paralel[0], "png", new File("c://temp//" +"paralel0"+ ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ImageIO.write(paralel[1], "png", new File("c://temp//" +"paralel1"+ ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
			

		
	}

	/**
	 * Creating all the starting strings
	 * 
	 * @param firstLineEndPoint
	 * @param crissNumberOfLines
	 * @param a
	 * @param b
	 * @param strokeBorder
	 * @param stringWidth
	 * @param colors
	 */
	public void shapeLineStarting(int firstLineEndPoint, int crissNumberOfLines, int a, int b, BasicStroke strokeBorder,
			int stringWidth, Color[] colors) {
		dataOfStitch(firstLineEndPoint, crissNumberOfLines, a, b);
		horizoStarting = new BufferedImage[horizo.length];
		paralelStarting = new BufferedImage[paralel.length];

		int width = 2 * length * a + x1;
		int hight = 2 * length * b + y1;
		for (int i = 0; i < paralel.length; i++) {

			if (paralel[i].nodeRed.x == x1) {
				int[] lastPoints = new int[2];
				lastPoints[0] = x1 - stringlength;
				lastPoints[1] = (int) paralel[i].nodeRed.y;

				paralelStarting[i] = drawShapeLineStarting(a, b, paralel[i], strokeBorder, stringWidth, colors,
						lastPoints);
			}
			if (paralel[i].nodeRed.x == width) {
				int[] lastPoints = new int[2];
				lastPoints[0] = width + stringlength;
				lastPoints[1] = (int) paralel[i].nodeRed.y;
				paralelStarting[i] = drawShapeLineStarting(a, b, paralel[i], strokeBorder, stringWidth, colors,
						lastPoints);
			}

			if (paralel[i].nodeRed.y == y1) {
				int[] lastPoints = new int[2];
				lastPoints[0] = (int) paralel[i].nodeRed.x;
				lastPoints[1] = y1 - stringlength;
				paralelStarting[i] = drawShapeLineStarting(a, b, paralel[i], strokeBorder, stringWidth, colors,
						lastPoints);
			}
			if (paralel[i].nodeRed.y == hight) {
				int[] lastPoints = new int[2];
				lastPoints[0] = (int) paralel[i].nodeRed.x;
				lastPoints[1] = hight + stringlength;
				paralelStarting[i] = drawShapeLineStarting(a, b, paralel[i], strokeBorder, stringWidth, colors,
						lastPoints);
			}
		}

		for (int i = 0; i < horizo.length; i++) {

			if (horizo[i].nodeRed.x == x1) {
				int[] lastPoints = new int[2];
				lastPoints[0] = x1 - stringlength;
				lastPoints[1] = (int) horizo[i].nodeRed.y;

				horizoStarting[i] = drawShapeLineStarting(a, b, horizo[i], strokeBorder, stringWidth, colors,
						lastPoints);
			}

			if (horizo[i].nodeRed.x == width) {
				int[] lastPoints = new int[2];
				lastPoints[0] = width + stringlength;
				lastPoints[1] = (int) horizo[i].nodeRed.y;
				horizoStarting[i] = drawShapeLineStarting(a, b, horizo[i], strokeBorder, stringWidth, colors,
						lastPoints);
			}

			if (horizo[i].nodeRed.y == y1) {
				int[] lastPoints = new int[2];
				lastPoints[0] = (int) horizo[i].nodeRed.x;
				lastPoints[1] = y1 - stringlength;
				horizoStarting[i] = drawShapeLineStarting(a, b, horizo[i], strokeBorder, stringWidth, colors,
						lastPoints);
			}
			if (horizo[i].nodeRed.y == hight) {
				int[] lastPoints = new int[2];
				lastPoints[0] = (int) horizo[i].nodeRed.x;
				lastPoints[1] = hight + stringlength;
				horizoStarting[i] = drawShapeLineStarting(a, b, horizo[i], strokeBorder, stringWidth, colors,
						lastPoints);
			}
		}
	}

	/**
	 * same as drawShape but not a Bizier (just a line)
	 * 
	 * @param paralelOrHorizo
	 * @param strokeBorder
	 * @param stringWidth
	 * @param secondColor
	 * @param i
	 * @param controlx
	 * @param controly
	 * @return
	 */

	public static BufferedImage drawShapeLineStarting(int a, int b, nodeLine paralelOrHorizo, BasicStroke strokeBorder,
			int stringWidth, Color[] colors, int[] lastPoints) {
		int width = 2 * a * MyPrintCanvasDiagram.length + 2 * MyPrintCanvasDiagram.x1
				+ 2 * MyPrintCanvasDiagram.lengthOfStringEnd + 100;
		int height = 2 * b * MyPrintCanvasDiagram.length + 2 * MyPrintCanvasDiagram.y1 + 100;

		// recieving the color of the string
		int indexParalelOrHorizo = listNodePoints.findNodePointIndex(paralelOrHorizo.nodeRed);
		// circle edges
		BufferedImage imageRectangleBorder = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2DRecBorder = imageRectangleBorder.createGraphics();
		graphics2DRecBorder.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
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

		graphics2DRecBorder.setColor(Color.BLACK);
		graphics2DRecBorder.setStroke(strokeBorder);
		shapeBorder.moveTo((int) paralelOrHorizo.nodeRed.x, (int) paralelOrHorizo.nodeRed.y);
		shapeBorder.lineTo(lastPoints[0], lastPoints[1]);
		// node, y1 is green node
		float x_tangent = ((float) stringWidth) / 2;
		float y_tangent = 0;
		if (paralelOrHorizo.nodeRed.x != lastPoints[0]) {
			float tangentOfParalelOrHorizo = (float) (Math.atan(((float) (paralelOrHorizo.nodeRed.y - lastPoints[1]))
					/ (paralelOrHorizo.nodeRed.x - lastPoints[0])));
			x_tangent = (float) (0.5 * (stringWidth + recWidth) * Math.sin(tangentOfParalelOrHorizo));
			y_tangent = (float) (0.5 * (stringWidth + recWidth) * Math.cos(tangentOfParalelOrHorizo));
		}

		GradientPaint darkShade = new GradientPaint(paralelOrHorizo.nodeRed.x + x_tangent,
				paralelOrHorizo.nodeRed.y - y_tangent, tempColor, paralelOrHorizo.nodeRed.x - x_tangent,
				paralelOrHorizo.nodeRed.y + y_tangent, tempColorDarker);
		BasicStroke stroke = new BasicStroke(stringWidth, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0.1F);
		// should always keep at +12
		strokeBorder = new BasicStroke(stringWidth + 12, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL, 0.1F);

		graphics2DRecBorder.setColor(Color.BLACK);
		graphics2DRecBorder.setStroke(strokeBorder);
		graphics2DRecBorder.draw(shapeBorder);
		graphics2DRecBorder.setStroke(stroke);
		graphics2DRecBorder.setPaint(darkShade);
		graphics2DRecBorder.draw(shapeBorder);
		try {
			ImageIO.write(imageRectangleBorder, "png", new File(
					"c://temp//" + "a starting string" + "_" + lastPoints[0] + "_" + "_" + a + "X" + b + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return imageRectangleBorder;

		/*
		 * imageRectangleBorder =
		 * CropCircleImage.cropInverse(imageRectangleBorder, lastPoint[0],
		 * lastPoint[1], stringWidth);
		 * 
		 * CropImage.addImage(imageCircleBorder, imageRectangleBorder, 1, 0, 0);
		 * // writes down the three points of the line to log
		 * System.out.println("[" + isHorizo + "," + i + "," + lastPoint[0] +
		 * "," + lastPoint[1] + "]");
		 */

	}

	public static void main(String[] args) throws IOException {
		JFrame localwindow = new JFrame();
		localwindow.setAutoRequestFocus(true);

		int a = 1;
		int b = 3;
		MyPrintCanvasDiagram.a = a;
		MyPrintCanvasDiagram.b = b;
		Color[] colors = new Color[1];
		colors[0] = new Color(255, 244, 222);
		nodePoint red = new nodePoint(50, 50, Color.BLACK);
		nodePoint green = new nodePoint(100, 30, Color.BLACK);
		nodeLine paralelOrHorizo = new nodeLine(red, green);
		int[] lastPoints = new int[2];
		lastPoints[0] = 100;
		lastPoints[1] = 30;
		BasicStroke stroke = new BasicStroke(stringWidth, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL, 0.1F);
		BasicStroke strokeBorder = new BasicStroke(stringWidth + 2 * recWidth, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL, 0.1F);

		// int firstLineEndPoint = 2 * a + 2 * b + 2 * a - 3;
		int firstLineEndPoint = 5;
		int crissNumberOfLines = 1;
		StitchWithWeavingTextForTutorials wea = new StitchWithWeavingTextForTutorials();
		// checikng an example 

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
