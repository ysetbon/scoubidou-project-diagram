package scoubidou2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class StitchWithWeaving {

	//thickness of the lines
	public static int stringWidth=8 ;
	// x, a can be changeable 
	public static int a=1 ;
	// y, b can be changeable
	public static int b=1;
	public static int lengthOfStringEnd=100;

	//spaces in-between 2 lines 
	public static int yOfStitch=10+lengthOfStringEnd;
	public static int length = 100;
	//starting point of square (left up)+a little extra for the length of the string
	public static int x1 = 10+lengthOfStringEnd;
	public static int y1 = yOfStitch;
	//width and height of the rectangle
	public static int width = 2 * length * a;
	public static int height = 2 * length * b;

	//if true,criss is weaved.if false cross is weaved. 
	public static Boolean isCrissWeave=true;

	int lineStart=b;
	int lineStartOp=a;
	static int firstLineEndPoint=5;
	static int crissNumberOfLines=1;
	Color firstColor = Color.BLACK;
	Color secondColor= Color.BLACK;

	//paralel and horizontal arrays
	static nodeLine[] paralel,horizo;

	public void dataOfStitch(int firstLindEndPoint,int crissNumberOfLines, int a, int b){

		int k = 4 * a + 4 * b;
		boolean isLInside = false;
		int crossNumberOfLines =(k-4*crissNumberOfLines)/4;

		nodePoint[] allPoints = new nodePoint[k];
		//getting all points
		allPoints = listNodePoints.listOfNodePoints();

		nodeLine l = new nodeLine(allPoints[0],
				allPoints[firstLineEndPoint]);
		nodeLine[] paralel = new nodeLine[crissNumberOfLines];
		paralel = stitchAlgoForPrintStitch.paralelReturnOneOption(l, crissNumberOfLines);
		for (int i = 0; i < 2 * crissNumberOfLines; i++) {
			if(paralel[i]==l){
				isLInside = true;
			}
		}

		nodeLine[] horizo = null;
		horizo = stitchAlgoForPrintStitch.paralelReturnOneOptionOposite(l, crissNumberOfLines);		
		paralel = new nodeLine[crossNumberOfLines];
		paralel = stitchAlgoForPrintStitch.paralelReturnOneOption(l, crossNumberOfLines);
		//changing paralel and horizo parameters to what we got here		
		StitchWithWeaving.paralel=paralel;
		StitchWithWeaving.horizo=horizo;				
	}
	/**
	 * 
	 * @param crissColor
	 * @param crossColor
	 * sets colors of criss and cross strings
	 */
	public void setColorsOfStrings(Color crissColor, Color crossColor){
		this.firstColor=crissColor;
		this.secondColor=crossColor;
	}
	/**
	 * 
	 * @param firstLindEndPoint
	 * @param crissNumberOfLines
	 * @param a
	 * @param b
	 * @throws IOException
	 * The method of getting a stitch with weaving.
	 */
	public  void printPicture(int firstLindEndPoint,int crissNumberOfLines, int a, int b,Color colrCriss, Color colrCross) throws IOException{		
		int width=2*a*MyPrintCanvasDiagram.length+2*MyPrintCanvasDiagram.x1+2*MyPrintCanvasDiagram.lengthOfStringEnd;
		int height=2*b*MyPrintCanvasDiagram.length+2*MyPrintCanvasDiagram.y1;
		JFrame localwindow = new JFrame();
		localwindow.setAutoRequestFocus(true);
		//localwindow.setLocation(firstLindEndPoint*10, firstLindEndPoint*10);
		MyPrintCanvasDiagram canv = new MyPrintCanvasDiagram();
		canv.changeFirstLineEndPoint(firstLindEndPoint);
		canv.changeCrissNumberOfLines(crissNumberOfLines);
		canv.setA(a);
		canv.setB(b);
		canv.setWidthOfStitch(stringWidth, stringWidth);
		//sets colors of criss and cross
		canv.setColorsOfStrings(colrCriss, colrCross);
		//setting to print only criss strings
		canv.setIsCrissWeave(true);
		canv.setIsPaintRectangle(true);
		BufferedImage crissImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D gd = crissImg.createGraphics();        
		canv.paint(gd);
		//setting not to paint again the rectangle and tiles
		canv.setIsPaintRectangle(false);

		//getting horizo and paralel
		dataOfStitch(firstLindEndPoint, crissNumberOfLines, a, b);
		nodeLine[]paralel = StitchWithWeaving.paralel;
		nodeLine[]horizo = StitchWithWeaving.horizo;
		//for getting all the cropped images
		BufferedImage croppedCrissImg = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D gd2 = croppedCrissImg.createGraphics();        
		canv.paint(gd2);
		//receiving points to erase 
		Point[] pointsToErase = ListOfOverAndUnder.findIntersectAllPointsToErase(paralel, horizo);
		
		for(int i=0; i<pointsToErase.length;i++){
			//erasing the points to erase
			croppedCrissImg=CropCircleImage.croppedIntersect(croppedCrissImg, pointsToErase[i].x, pointsToErase[i].y, 50);
		
		}
	

		//creating the cross image
		canv.setIsCrissWeave(false);
		BufferedImage crossImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D gdCross = crossImg.createGraphics();        
		canv.paint(gdCross);

		//adding the layers. First criss then cross then croppedCriss
		CropImage.addImage(crissImg, crossImg, 1,0,0);
		CropImage.addImage(crissImg, croppedCrissImg, 1, 0, 0);
		//
		BufferedImage returnImg = crissImg;
		try {
			ImageIO.write(returnImg,"png", new File("c://temp//"+"basicStitch"+"_"+firstLindEndPoint+"_"+crissNumberOfLines+"_"+a+"X"+b+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[]args) throws IOException{
		JFrame localwindow = new JFrame();
		localwindow.setAutoRequestFocus(true);
		int a=2;
		int b=1;
		int firstLindEndPoint= 5;
		int crissNumberOfLines=1;
		//localwindow.setLocation(firstLindEndPoint*10, firstLindEndPoint*10);
		MyPrintCanvasDiagram canv = new MyPrintCanvasDiagram();
		StitchWithWeaving wea = new StitchWithWeaving();
		wea.firstLineEndPoint=firstLindEndPoint;
		wea.crissNumberOfLines=crissNumberOfLines;
		wea.a=a;
		wea.b=b;
		wea.printPicture(firstLindEndPoint, crissNumberOfLines, a, b, Color.RED,Color.BLUE);
		
		
	}

}
