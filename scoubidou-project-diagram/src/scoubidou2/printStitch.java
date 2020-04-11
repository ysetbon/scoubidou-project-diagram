package scoubidou2;


import javax.imageio.ImageIO;
import javax.swing.event.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;




import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
/** a ,b ,length, firstPoint and secondPoint are the only variables that you can change! **/


public class printStitch{
	
//	Color crissColor=new Color(34,120,130);
//	Color crossColor=new Color(200,200,40);
	Color crissColor= Color.BLACK;
	Color crossColor=Color.BLACK;
	
	public static void main(String[] args) throws IOException {
		int a=2, b=1;
		int j=3;

		while(j<=(2*a+2*b-1)){
			for(int criss=1; criss<=((j-1)/2);criss++){
				printStitch pt = new printStitch();
				pt.printPicture(j,criss,a,b);
				
			}
			j=j+2;
			
		}
		while(j<=(4*a+4*b-3)){
			for(int criss=1; criss<=(4*a+4*b-1-j)/2;criss++){
				printStitch pt = new printStitch();
				pt.printPicture(j,criss,a,b);
				
				
			}
			j=j+2;
			
		}
	}
	/**
	 * 
	 * @param firstLindEndPoint- end point of 0
	 * @param crissNumberOfLines
	 * @param a
	 * @param b
	 * @param counter
	 * @throws IOException 
	 */
	public  void printPicture(int firstLindEndPoint,int crissNumberOfLines, int a, int b) throws IOException{
		JFrame localwindow = new JFrame();
		localwindow.setAutoRequestFocus(true);
		//localwindow.setLocation(firstLindEndPoint*10, firstLindEndPoint*10);
/*		MyPrintCanvasDiagram canv = new MyPrintCanvasDiagram();
		canv.changeFirstLineEndPoint(firstLindEndPoint);
		canv.changeCrissNumberOfLines(crissNumberOfLines);
		canv.setA(a);
		canv.setB(b);*/
		StitchWithWeaving stitch = new StitchWithWeaving();
		stitch.a=a;
		stitch.b=b;
		stitch.firstLineEndPoint=firstLindEndPoint;
		stitch.crissNumberOfLines=crissNumberOfLines;
		stitch.printPicture(firstLindEndPoint, crissNumberOfLines, a, b, crissColor, crossColor);
		localwindow.revalidate();
		
	}




}
	









