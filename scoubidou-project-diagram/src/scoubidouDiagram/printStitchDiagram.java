package scoubidouDiagram;


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


public class printStitchDiagram{
	public static void main(String[] args) {
		int a=4, b=4;
		int counter=1;
		int j=3;
	/*	printStitch pt = new printStitch();
		pt.printPicture(j,1,5,5);*/
		while(j<=(2*a+2*b-1)){
			for(int criss=1; criss<=((j-1)/2);criss++){
				printStitchDiagram pt = new printStitchDiagram();
				pt.printPicture(j,criss,a,b,counter);
				counter++;
			}
			j=j+2;
			
		}
		while(j<=(4*a+4*b-3)){
			for(int criss=1; criss<=(4*a+4*b-1-j)/2;criss++){
				printStitchDiagram pt = new printStitchDiagram();
				pt.printPicture(j,criss,a,b,counter);
				counter++;
				
			}
			j=j+2;
			
		}
	}
	public  void printPicture(int firstLindEndPoint,int crissNumberOfLines, int a, int b,int counter){
		JFrame localwindow = new JFrame();
		localwindow.setAutoRequestFocus(true);
		//localwindow.setLocation(firstLindEndPoint*10, firstLindEndPoint*10);
		MyPrintCanvasDiagram canv = new MyPrintCanvasDiagram();
		canv.changeFirstLineEndPoint(firstLindEndPoint);
		canv.changeCrissNumberOfLines(crissNumberOfLines);
		canv.setA(a);
		canv.setB(b);	
		localwindow.setBounds(firstLindEndPoint, firstLindEndPoint, 700, 700);
		localwindow.setTitle(firstLindEndPoint+"_"+crissNumberOfLines+"_"+a+"X"+b);
		localwindow.getContentPane().add(canv);
		localwindow.setVisible(true);
		try
        {
            BufferedImage image = new BufferedImage(700,700, BufferedImage.SCALE_SMOOTH);
            Graphics2D graphics2D = image.createGraphics();
            localwindow.paint(graphics2D);
            ImageIO.write(image,"png", new File("c://temp//"+counter+"_starting poistion_"+firstLindEndPoint+"_"+crissNumberOfLines+"_"+a+"X"+b+".png"));
           // localwindow.dispose();
            Thread.sleep(100);
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
		System.out.println("firstLindEndPoint="+firstLindEndPoint);
		localwindow.revalidate();
		
	}




}
	









