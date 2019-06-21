
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class CropImage {
	
/**
 * 
 * @param under- under picture
 * @param over - over picture
 * @return a BufferedImage that draws only the intersection of top and under 
 */
	public static BufferedImage croppedIntersect(BufferedImage under,
			BufferedImage over, Rectangle2D bounds) {
		
		BufferedImage overResult = addTransparant(over);
		int[][] arrayOver = convertTo2DUsingGetRGB(over);
		
			for (int y = (int) bounds.getY()-40; y < (int)(bounds.getHeight()+bounds.getY()+40); y++) {
				for (int x = (int) bounds.getX()-40; x < (int)(bounds.getWidth()+bounds.getX()+40); x++) {
				int colorUnder = under.getRGB(x, y);
				int alphaUnder = (colorUnder >> 24) & 0xff;
				int colorOver = over.getRGB(x, y);
				int alphaOver = (colorOver >> 24) & 0xff;
				// if top pixel and under pixel both arn't transparent then they
				// are intersecting
				if ((alphaOver != 0) && (alphaUnder != 0)) {
					overResult.setRGB(x, y, arrayOver[y][x]);
					//outer pixels that need to also paint
				/*	overResult.setRGB(x-1, y-1, arrayOver[x-1][y-1]);
					overResult.setRGB(x+1, y+1, arrayOver[x+1][y+1]);
					overResult.setRGB(x, y-1, arrayOver[x][y-1]);
					overResult.setRGB(x-1, y, arrayOver[x-1][y]);
					overResult.setRGB(x-1, y+1 , arrayOver[x-1][y+1]);
					overResult.setRGB(x+1, y-1, arrayOver[x+1][y-1]);
					
					overResult.setRGB(x-2, y-2, arrayOver[x-2][y-2]);
					overResult.setRGB(x+2, y+2, arrayOver[x+2][y+2]);
					overResult.setRGB(x, y-2, arrayOver[x][y-2]);
					overResult.setRGB(x-2, y, arrayOver[x-2][y]);
					overResult.setRGB(x-2, y+2 , arrayOver[x-2][y+2]);
					overResult.setRGB(x+2, y-2, arrayOver[x+2][y-2]);*/
				}
			}
		}
		return overResult;
	}

	/**
	 * 
	 * @param bi
	 * @return create a transparece image the same size as the input
	 *         BufferedImage
	 */
	public static BufferedImage addTransparant(BufferedImage bi) {
		// Create a buffered image in which to draw
		BufferedImage transparentImage = new BufferedImage(bi.getWidth(),
				bi.getHeight(), BufferedImage.TYPE_INT_ARGB);

		// Create a graphics contents on the buffered image
		Graphics2D g2d = transparentImage.createGraphics();

		// Draw graphics
		g2d.setComposite(AlphaComposite.Clear);
		g2d.fillRect(0, 0, bi.getWidth(), bi.getWidth());
		return transparentImage;
	}

	/**
	 * prints the contents of buff2 on buff1 with the given opaque value. Opaque
	 * is transparency.
	 */
	public static void addImage(BufferedImage buff1, BufferedImage buff2,
			float opaque, int x, int y) {
		Graphics2D g2d = buff1.createGraphics();
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
				opaque));
		g2d.drawImage(buff2, x, y, null);
		g2d.dispose();
	}

	private static int[][] convertTo2DWithoutUsingGetRGB(BufferedImage image) {

		final byte[] pixels = ((DataBufferByte) image.getRaster()
				.getDataBuffer()).getData();
		final int width = image.getWidth();
		final int height = image.getHeight();
		final boolean hasAlphaChannel = image.getAlphaRaster() != null;

		int[][] result = new int[height][width];
		if (hasAlphaChannel) {
			final int pixelLength = 4;
			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += (((int) pixels[pixel] & 0xff) << 24); // alpha
				argb += ((int) pixels[pixel + 1] & 0xff); // blue
				argb += (((int) pixels[pixel + 2] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 3] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		} else {
			final int pixelLength = 3;
			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += pixelLength) {
				int argb = 0;
				argb += -16777216; // 255 alpha
				argb += ((int) pixels[pixel] & 0xff); // blue
				argb += (((int) pixels[pixel + 1] & 0xff) << 8); // green
				argb += (((int) pixels[pixel + 2] & 0xff) << 16); // red
				result[row][col] = argb;
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		}

		return result;
	}
	 private static int[][] convertTo2DUsingGetRGB(BufferedImage image) {
	      int width = image.getWidth();
	      int height = image.getHeight();
	      int[][] result = new int[height][width];

	      for (int row = 0; row < height; row++) {
	         for (int col = 0; col < width; col++) {
	            result[row][col] = image.getRGB(col, row);
	         }
	      }

	      return result;
	   }


	 /**
	  * 
	  * @param under
	  * @param over
	  * @param bounds
	  * @return if the two strings intersect each other
	  */
	 public static boolean isIntersect(BufferedImage under,
				BufferedImage over, Rectangle2D bounds) {
	
		 
			for (int x = 0 ; x < under.getWidth(); x= x+5) {
				for (int y = 0; y < over.getHeight(); y=y+5) {
						
					int colorUnder = under.getRGB(x, y);
					int alphaUnder = (colorUnder >> 24) & 0xff;
					int colorOver = over.getRGB(x, y);
					int alphaOver = (colorOver >> 24) & 0xff;
					
					//if same pixel on both pictures are not transparent then they are intersected
					if ((alphaUnder != 0) && (alphaOver != 0)) {
						return true;

					}
				}
			}
			return false;
		}
	 
	 /**
	  * 
	  * @param under
	  * @param over
	  * @param bounds
	  * @return if the two strings intersect each other
	  */
	 public static boolean isIntersectNew(BufferedImage under,
				BufferedImage over, int x1, int y1, int widthOfRec, int heightOfRec) {
	
		    BufferedImage underImg = new BufferedImage(under.getWidth(), under.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		    underImg.getGraphics().drawImage(under, 0, 0, null);
		    
		    BufferedImage overImg = new BufferedImage(over.getWidth(), over.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		    overImg.getGraphics().drawImage(over, 0, 0, null);
		    
		 final byte[] overByte = ((DataBufferByte) overImg.getRaster().getDataBuffer()).getData();	
		final  byte[] underByte = ((DataBufferByte) underImg.getRaster().getDataBuffer()).getData();	
		
	/*	 final int pixelLength = 4;
         for (int pixel = 0; pixel < overByte.length; pixel += 10*pixelLength) {
        	 
            int overB = 0;
            overB += (((int) overByte[pixel] & 0xff) << 24); // alpha
            
            int underB = 0;
            underB +=(((int) underByte[pixel] & 0xff) << 24); // alpha

            if ((overB != 0) && (underB != 0)) {
				return true;

			}
         }*/
      
			for (int x = 0 ; x <under.getWidth(); x = x+5) {
				for (int y = 0; y < under.getHeight(); y= y+5) {		
					
						byte over_b = overByte[4*(x+y*under.getWidth())];
						byte under_b = underByte[4*(x+y*under.getWidth())];
								
					//if same pixel on both pictures are not transparent then they are intersected
					if ((over_b != 0) && (under_b != 0)) {
						return true;

					}
				}
			}
			return false;
		}
	 
	public static void main(String[] args) {
		BufferedImage over = null;
		BufferedImage under = null;
		BufferedImage trans = null;
		try {
			over = ImageIO.read(new File("C:/temp/2.png"));
			under = ImageIO.read(new File("C:/temp/1.png"));
		
			} catch (IOException e) {
				System.out.println("didn't write image");
			}
		try {
		//	trans =croppedIntersect(under, over);
			// addImage(over, trans, 1, 0,0);
			File outputfile = new File("C:/temp/hello.png");
			ImageIO.write(trans, "png", outputfile);
		} catch (IOException e) {
			System.out.println("didn't wrote transparent image");
		}
	}
	
}

