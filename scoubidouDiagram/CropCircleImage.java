package scoubidouDiagram;

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

public class CropCircleImage {
	// 1 is 100 percent not transparent, this is for the croped image how much
	// to add transparency to
	// the selected image to crop in.Not the background transparecy!
	public static float transparentPercent = 1;

	/**
	 * 
	 * @param bi
	 * @param x1-point1
	 *            of intersection
	 * @param y1
	 * @param x2-point2
	 *            of intersection
	 * @param y2
	 * @return BufferedImage of the cropped intersection. Intersection is a
	 *         rectangle with 2 given points. Similar to crop method , but this
	 *         is for using with the weaving methods.
	 * @throws IOException
	 */
	public static BufferedImage croppedIntersect(BufferedImage bi, int x, int y, int radius) throws IOException {

		BufferedImage ret = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_ARGB);
		ret = crop(bi, x, y, radius);
		return ret;

	}

	/**
	 * 
	 * @param bi
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return cropped image with with a transparent circle, the invert of the
	 *         circle is not transparent
	 * @throws IOException
	 */
	public static BufferedImage crop(BufferedImage bi, int x, int y, int radius) {
		int srcHeight = bi.getHeight();
		int srcWidth = bi.getWidth();
		if (srcWidth > 0 && srcHeight > 0) {
			// if the cropped image is width and hight are not 0
			Graphics2D g = bi.createGraphics();

			// Create a buffered image in which to draw
			BufferedImage ret = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_ARGB);

			// Create a graphics contents on the buffered image
			Graphics2D g2d = ret.createGraphics();
			g2d.drawImage(bi, 0, 0, bi.getWidth(), bi.getHeight(), null);
			// testing
			// g2d.setColor(Color.BLACK);
			// set to clear
			g2d.setComposite(AlphaComposite.Clear);

			drawCenteredCircle(g2d, x, y, radius);
			// gives transparency and the cropped image with size of bi
			return ret;

		} else {
			return null;
		}
	}

	/**
	 * returns the specific circle, all the rest will be transparent
	 * @param bi
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @return cropped image with with a transparent circle, the invert of the
	 *         circle is not transparent
	 * @throws IOException
	 */
	public static BufferedImage cropInverse(BufferedImage bi, int circle_x, int circle_y, int radius) {
	// Create a buffered image in which to draw
		BufferedImage ret = new BufferedImage(bi.getWidth(), bi.getHeight(), BufferedImage.TYPE_INT_ARGB);

		// Create a graphics contents on the buffered image
		Graphics2D g2d = ret.createGraphics();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR));
		for (int j = 0; j < bi.getHeight(); j++) {
			for (int i = 0; i < bi.getWidth(); i++) {

				if (isInside(circle_x, circle_y, radius, i, j) == true) {
					ret.setRGB(i, j, bi.getRGB(i, j));
				}
			}
		}
		;
		// gives transparency and the cropped image with size of bi
		return ret;

	}

	/**
	 * 
	 * @param circle_x
	 * @param circle_y
	 * @param rad
	 * @param x
	 * @param y
	 * @return if a point is outside a circle or not
	 */

	static boolean isInside(int circle_x, int circle_y, int rad, int x, int y) {
		// Compare radius of circle with distance of its center from
		// given point
		if ((x - circle_x) * (x - circle_x) + (y - circle_y) * (y - circle_y) <= rad * rad)
			return true;
		else
			return false;
	}

	/**
	 * 
	 * @param g
	 * @param x-
	 *            center of circle
	 * @param y-
	 *            center of circle
	 * @param r-
	 *            radius
	 */
	public static void drawCenteredCircle(Graphics2D g, int x, int y, int r) {
		x = x - (r / 2);
		y = y - (r / 2);
		g.fillOval(x, y, r, r);
	}

	/*		*//**
				 * 
				 * @param bi
				 * @return create a transparece image the same size as the input
				 *         BufferedImage
				 */

	/*
	 * public static BufferedImage addTransparant(BufferedImage bi){ // Create a
	 * buffered image in which to draw BufferedImage transparentImage = new
	 * BufferedImage(bi.getWidth(), bi.getHeight(),
	 * BufferedImage.TYPE_INT_ARGB);
	 * 
	 * // Create a graphics contents on the buffered image Graphics2D g2d =
	 * transparentImage.createGraphics();
	 * 
	 * // Draw graphics g2d.setComposite(AlphaComposite.Clear); g2d.fillRect(0,
	 * 0, bi.getWidth(),bi.getWidth()); return transparentImage; }
	 * 
	 *//**
		 * prints the contents of buff2 on buff1 with the given opaque value.
		 * Opaque is transparency.
		 *//*
		 * public static void addImage(BufferedImage buff1, BufferedImage buff2,
		 * float opaque, int x, int y) { Graphics2D g2d =
		 * buff1.createGraphics(); g2d.setComposite(
		 * AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opaque));
		 * g2d.drawImage(buff2, x, y, null); g2d.dispose(); }
		 */

	public static void main(String[] args) {
		BufferedImage bi = null;
		BufferedImage ret = null;
		System.out.println("hello");
		try {
			bi = ImageIO.read(new File("C:/temp/Untitled.png"));
			ret = CropCircleImage.cropInverse(bi, 50, 50, 10);
			
			File outputfile = new File("C:/temp/OutputOfRet.png");
			// cropping
			try {
				ImageIO.write(ret, "png", outputfile);
			} catch (IOException e) {
				System.out.println("didn't write image");

			}

		}

		catch (IOException e) {
			System.out.println("couldn't read image");
		}
	}

}
