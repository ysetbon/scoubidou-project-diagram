package scoubidou2;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CropImage {
	//1 is 100 percent not transparent, this is for the croped image how much to add transparency to
	//the selected image to crop in.Not the background transparecy!
	public static float transparentPercent=1;		
	/**
	 * 
	 * @param bi
	 * @param x1-point1 of intersection
	 * @param y1
	 * @param x2-point2 of intersection
	 * @param y2
	 * @return BufferedImage of the cropped intersection. Intersection is a rectangle with 2 given points.
	 * Similar to crop method , but this is for using with the weaving methods.
	 * @throws IOException 
	 */
	public static BufferedImage croppedIntersect(BufferedImage bi,int x1,int y1, int x2, int y2) throws IOException{
	//the rectangle points
	Point point1 = new Point (x1,y1);
	Point point2 = new Point (x2,y2);
	Point point3 = new Point (x1,y2);
	Point point4 = new Point (x2,y1);
	//width and height of rectangle
	int width=Math.abs(x1-x2);
	int height=Math.abs(y1-y2);
	//finding upper left point
	int minimumY;
	if(y1<=y2){
		minimumY=y1;
	}
	else{
		minimumY=y2;
	}
	int minimumX;
	if(x1<=x2){
		minimumX=x1;
	}
	else{
		minimumX=x2;
	}
	Point upperLeft = new Point(minimumX,minimumY);
	//return cropped image


		int stringWidth =MyPrintCanvasDiagram.stringWidth;
		BufferedImage ret = new BufferedImage(width+2*stringWidth, height+2*stringWidth, BufferedImage.TYPE_INT_ARGB);	
		 ret=crop(bi,upperLeft.x-stringWidth,upperLeft.y-stringWidth,width+2*stringWidth,height+2*stringWidth);	
			return ret;
			
	}
	
	/**
	 * 
	 * @param bi
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return cropped image with transparency background.
	 * @throws IOException
	 */
	public static BufferedImage crop(BufferedImage bi, int x, int y, int width, int height) throws IOException {
		int srcHeight = bi.getHeight();
		int srcWidth = bi.getWidth();
		if (srcWidth > 0 && srcHeight > 0) {
			//if the cropped image is width and hight are not 0
			if(width > 0 && height > 0){
				Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
				ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
				Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, width, height, null);
				g.dispose();
				//first ret is a transparent image with size of bi
				BufferedImage ret = addTransparant(bi);
				addImage(ret,tag,transparentPercent,x,y);
				//gives transparency and the cropped image with size of bi
				return ret;
			}
			
		    //if the cropped image is width== and height>0
			if(width==0 && height > 0){
				Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
				//need to crop width of the string
				width=MyPrintCanvasDiagram.stringWidth;
				//cropping a width of a string
				x=(int)(x-(MyPrintCanvasDiagram.stringWidth+10))/2;
				ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
				Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, width, height, null);
				g.dispose();
				//first ret is a transparent image with size of bi
				BufferedImage ret = addTransparant(bi);
				addImage(ret,tag,transparentPercent,x,y);
				//gives transparency and the cropped image with size of bi
				return ret;
			}
			//if the cropped image is width>0 and height==0
			if(width > 0 && height == 0){
				Image image = bi.getScaledInstance(srcWidth, srcHeight, Image.SCALE_DEFAULT);
				//need to crop height of the string
				height=MyPrintCanvasDiagram.stringWidth+10;
				//cropping a height of a string
				y=(int)(y-(MyPrintCanvasDiagram.stringWidth+10))/2;
				ImageFilter cropFilter = new CropImageFilter(x, y, width, height);
				Image img = Toolkit.getDefaultToolkit().createImage(new FilteredImageSource(image.getSource(), cropFilter));
				BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				Graphics g = tag.getGraphics();
				g.drawImage(img, 0, 0, width, height, null);
				g.dispose();
				//first ret is a transparent image with size of bi
				BufferedImage ret = addTransparant(bi);
				addImage(ret,tag,transparentPercent,x,y);
				//gives transparency and the cropped image with size of bi
				return ret;
			}
			else{
				return null;
			}
		}
		else{
			return null;
		}
	}
	/**
	 * 
	 * @param bi
	 * @return create a transparece image the same size as the input BufferedImage
	 */
	public static BufferedImage addTransparant(BufferedImage bi){
		// Create a buffered image in which to draw
		BufferedImage transparentImage = new BufferedImage(bi.getWidth(), bi.getHeight(),
				BufferedImage.TYPE_INT_ARGB);

		// Create a graphics contents on the buffered image
		Graphics2D g2d = transparentImage.createGraphics();

		// Draw graphics
		g2d.setComposite(AlphaComposite.Clear);
		g2d.fillRect(0, 0, bi.getWidth(),bi.getWidth());
		return transparentImage;
	}

	/**
	 * prints the contents of buff2 on buff1 with the given opaque value. Opaque is transparency.
	 */
	public static void addImage(BufferedImage buff1, BufferedImage buff2,
			float opaque, int x, int y) {
		Graphics2D g2d = buff1.createGraphics();
		g2d.setComposite(
				AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opaque));
		g2d.drawImage(buff2, x, y, null);
		g2d.dispose();
	}


	public static void main(String[]args){
		BufferedImage bi = null;
		BufferedImage ret = null;
		BufferedImage trans=null;
		try {
			bi = ImageIO.read(new File("C:/temp/Untitled.png"));
			ret = croppedIntersect(bi, 50, 50, 500, 800);
			File outputfile = new File("C:/temp/OutputOfRet.png");
			//cropping
			try {
				ImageIO.write(ret, "png", outputfile);
			}
			catch (IOException e) {
				System.out.println("didn't write image");
			}
			System.out.println(" wrote image");
			//transparent picture
			try {
				trans=addTransparant(bi);
				File outputfileTrans = new File("C:/temp/OutputTrans.png");
				ImageIO.write(trans, "png", outputfileTrans);
			}
			catch (IOException e) {
				System.out.println("didn't wrote transparent image");
			}
			System.out.println("wrote transparent image");

		}
		catch (IOException e) {
			System.out.println("didn't retrive image");
		}
		System.out.println("retrive");
	}
}
