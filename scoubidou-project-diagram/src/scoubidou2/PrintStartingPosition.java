package scoubidou2;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * This works but needs some improvements. The class is for drawing starting positions righ and left hand
 * @author Yonatan Setbon
 *
 */
public class PrintStartingPosition extends JComponent {
	int lengthOfStringEnd = MyPrintCanvasDiagram.lengthOfStringEnd;
	static int a=3;
	static int b=1;
	int crissNumberOfLines=1;
	int stringWidth = 8 ;
	int recWidth = 8;
	static int length = MyPrintCanvasDiagram.length;
	static int x1 = MyPrintCanvasDiagram.x1;
	static int y1 = MyPrintCanvasDiagram.y1;

	public void setAandB(int a, int b) {
		this.a = a;
		this.b = b;
	}

	public void setCrissNumberOfLines(int crissNumberOfLines) {
		this.crissNumberOfLines = crissNumberOfLines;
	}

	public void paint(Graphics2D g2) {

		int k = 4 * a + 4 * b;
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(stringWidth));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
				RenderingHints.VALUE_ANTIALIAS_ON);
		// painting the rectangle border and tiles underneafe
		g2.setStroke(new BasicStroke(recWidth / 2));
		g2.setColor(new Color(10, 108, 179));
		for (int i = x1; i < 2 * length * a + x1; i = i + length) {
			for (int j = y1; j < 2 * length * b + y1; j = j + length) {
				g2.drawRect(i, j, length, length);
			}
		}
		g2.setStroke(new BasicStroke(recWidth));

		g2.setColor(Color.BLACK);
		//allPoints are points of the rectangle to draw vertical lines from
		nodePoint[] allPoints = new nodePoint[k];
		allPoints = listOfNodePoints();
		
		
		boolean leftHand=true;
		if(leftHand==true){
		for (int i = 0; i < 2*a; i+=2) {
			g2.drawLine((int)allPoints[i].x, (int)allPoints[i].y,
					(int)allPoints[i].x , (int)allPoints[i].y - lengthOfStringEnd);
		}
		for (int i = 2*a; i < 2*a+2*b; i+=2) {
			g2.drawLine((int)allPoints[i].x, (int)allPoints[i].y,
					(int)allPoints[i].x + lengthOfStringEnd, (int)allPoints[i].y);
		}
		for (int i = 2*a+2*b; i < 4*a+2*b; i+=2) {
			g2.drawLine((int)allPoints[i].x, (int)allPoints[i].y,
					(int)allPoints[i].x, (int)allPoints[i].y + lengthOfStringEnd);
		}
		for (int i = 4*a+2*b; i < 4*a+4*b; i+=2) {
			System.out.println(i);
			g2.drawLine((int)allPoints[i].x, (int)allPoints[i].y,
					(int)allPoints[i].x - lengthOfStringEnd, (int)allPoints[i].y);
		}
		}
		else{
			for (int i = 1; i < 2*a; i+=2) {
				g2.drawLine((int)allPoints[i].x, (int)allPoints[i].y,
						(int)allPoints[i].x , (int)allPoints[i].y - lengthOfStringEnd);
			}
			for (int i = 2*a+1; i < 2*a+2*b; i+=2) {
				g2.drawLine((int)allPoints[i].x, (int)allPoints[i].y,
						(int)allPoints[i].x + lengthOfStringEnd, (int)allPoints[i].y);
			}
			for (int i = 2*a+2*b+1; i < 4*a+2*b; i+=2) {
				g2.drawLine((int)allPoints[i].x, (int)allPoints[i].y,
						(int)allPoints[i].x, (int)allPoints[i].y + lengthOfStringEnd);
			}
			for (int i = 4*a+2*b+1; i < 4*a+4*b; i+=2) {
				g2.drawLine((int)allPoints[i].x, (int)allPoints[i].y,
						(int)allPoints[i].x - lengthOfStringEnd, (int)allPoints[i].y);
			}
		}
		
		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(recWidth));
		g2.drawRect(x1, y1, 2 * length * a, 2 * length * b);

	}
	
	/** this is the main method that will give us all the points given int's a and b**/
	public static nodePoint[] listOfNodePoints(){
		//switches is 1 when green, -1 when red
		int switches=1;
		int length=MyPrintCanvasDiagram.length;
		int width=2*length*a;
		int hight=2*length*b;
		int k = 4*a+4*b;
		nodePoint[] allPoints = new nodePoint[k];
		//building all the points need to be green and red in top x axis 
		for(int i=0;i<2*a;i++){
			if(switches==1){
				allPoints[i]= new nodePoint(x1+length/2+i*length,y1,Color.GREEN);

			}
			if(switches==-1){
				allPoints[i]= new nodePoint(x1+length/2+i*length,y1,Color.RED);
			}
			switches=switches*-1;
		}
		//right Y axis, opposite colors from the left one
		switches=1;
		for(int j=0; j<2*b;j++){
			if(switches==1){
				allPoints[2*a+j] = new nodePoint(x1+width,y1+length/2+j*length,Color.GREEN);			
			}
			if(switches==-1){
				allPoints[2*a+j] = new nodePoint(x1+width,y1+length/2+j*length,Color.RED);			
			}
			switches=switches*-1;
		}
		//because its opposite colors in the down x axis
		switches=1;
		for(int i=0;i<2*a;i++){
			if(switches==1){
				allPoints[2*a+2*b+i]= new nodePoint(x1+length/2+(2*a-1)*length-i*length,y1+hight,Color.GREEN);

			}
			if(switches==-1){
				allPoints[2*a+2*b+i]= new nodePoint(x1+length/2+(2*a-1)*length-i*length,y1+hight,Color.RED);
			}
			switches=switches*-1;
		}
		switches=1;
		//building all the points need to be green and red in left Y axis 
		for(int j=0; j<2*b;j++){
			if(switches==1){
				allPoints[4*a+2*b+j] = new nodePoint(x1,y1+length/2+(2*b-1)*length-j*length,Color.GREEN);			
			}
			if(switches==-1){
				allPoints[4*a+2*b+j] = new nodePoint(x1,y1+length/2+(2*b-1)*length-j*length,Color.RED);			
			}
			switches=switches*-1;
		}


		return allPoints;
	}
	public static void main(String[]args){
		int a=3;
		int b=1;
		PrintStartingPosition p = new PrintStartingPosition();
		BufferedImage rectangle = new BufferedImage(2*x1+2*a*length,
													2*y1+2*b*length,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = rectangle.createGraphics();        
		
		p.paint(g2);
		try {
			ImageIO.write(rectangle,"png", new File("c://temp//"+a+"x"+b+"startingPosition"+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
}
