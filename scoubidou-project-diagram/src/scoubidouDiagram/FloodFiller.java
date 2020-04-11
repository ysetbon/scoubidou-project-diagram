package scoubidouDiagram;

import java.awt.*;
import java.awt.image.*;
import java.util.*;

public class FloodFiller
{

  public BufferedImage fill(Image img, int xSeed, int ySeed, Color col)
  {
	  //changing to an ARGB color
    BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
    bi.getGraphics().drawImage(img, 0, 0, null);
    int x = xSeed;
    int y = ySeed;
    int width = bi.getWidth();
    int height = bi.getHeight();

    DataBufferInt data = (DataBufferInt) (bi.getRaster().getDataBuffer());
    int[] pixels = data.getData();

    if (x >= 0 && x < width && y >= 0 && y < height)
    {

      int oldColor = pixels[y * width + x];
      int fillColor = col.getRGB();

      if (oldColor != fillColor)
      {
        floodIt(pixels, x, y, width, height, oldColor, fillColor);
      }
    }
    return bi;
  }

  /**
   * 
   * @param pixels
   * @param x
   * @param y
   * @param width
   * @param height
   * @param oldColor - should be white
   * @param fillColor - should be transparent
   */

  private void floodIt(int[] pixels, int x, int y, int width, int height, int oldColor, int fillColor)
  {

    int[] point = new int[] { x, y };
    LinkedList<int[]> points = new LinkedList<int[]>();
    points.addFirst(point);
    int differenceBetweenOldColorAndthePixel=370;
    while (!points.isEmpty())
    {
      point = points.remove();

      x = point[0];
      y = point[1];
      int xr = x;

      int yp = y * width;
      int ypp = yp + width;
      int ypm = yp - width;

      do
      {
        pixels[xr + yp] = fillColor;
        xr++;
      }
      while (xr < width && ColorDistance(pixels[xr + y * width], oldColor)<=differenceBetweenOldColorAndthePixel);

      int xl = x;
      do
      {
        pixels[xl + yp] = fillColor;
        xl--;
      }
      while (xl >= 0 && ColorDistance(pixels[xl + y * width],oldColor)<=differenceBetweenOldColorAndthePixel);

      xr--;
      xl++;

      boolean upLine = false;
      boolean downLine = false;

      for (int xi = xl; xi <= xr; xi++)
      {
        if (y > 0 && ColorDistance(pixels[xi + ypm],oldColor)<=differenceBetweenOldColorAndthePixel && !upLine)
        {
          points.addFirst(new int[] { xi, y - 1 });
          upLine = true;
        }
        else
        {
          upLine = false;
        }
        if (y < height - 1 && ColorDistance(pixels[xi + ypp],oldColor)<=differenceBetweenOldColorAndthePixel && !downLine)
        {
          points.addFirst(new int[] { xi, y + 1 });
          downLine = true;
        }
        else
        {
          downLine = false;
        }
      }
    }
  }
  /**
   * 
   * @param rgb1 - first color
   * @param rgb2 - second color
   * @return
   */
  public double ColorDistance(int rgb1, int rgb2)
	 {
		 Color c1 = new Color(rgb1);
		 Color c2 = new Color (rgb2);
	     double rmean = ( c1.getRed() + c2.getRed() )/2;
	     int r = c1.getRed() - c2.getRed();
	     int g = c1.getGreen() - c2.getGreen();
	     int b = c1.getBlue() - c2.getBlue();
	     double weightR = 2 + rmean/256;
	     double weightG = 4.0;
	     double weightB = 2 + (255-rmean)/256;
	     return Math.sqrt(weightR*r*r + weightG*g*g + weightB*b*b);
	 } 
}