import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class AutomaticallyCropDiagram {
	public static String path = "E://chevron 3x1//video//diagrams//4//0";
	public static void main(String args[]) throws IOException {
		BufferedImage sourceImage = null;
		
		try {
			BufferedImage in = ImageIO.read(new File(path+".png"));

			 sourceImage = new BufferedImage(
			    in.getWidth(), in.getHeight(), BufferedImage.TYPE_INT_ARGB);

			Graphics2D g = sourceImage.createGraphics();
			g.drawImage(in, 0, 0, null);
			g.dispose();
			
			int left = 0;
			int right = 0;
			int top = 0;
			int bottom = 0;

			for (int x = 0; x < sourceImage.getWidth(); x++) {
				for (int y = 0; y < sourceImage.getHeight(); y++) {
					// pixel is not empty					
					System.out.println(isTransparent(sourceImage, x, y));
					if (!isTransparent(sourceImage, x, y)) {
						System.out.println(x+","+y);
						left = x;
						x =  sourceImage.getWidth()+1;
						y = sourceImage.getHeight()+1;
						break;
					}
				}
			}
			
			for (int x = sourceImage.getWidth() - 1; x >= 0; x--) {
				for (int y = 0; y < sourceImage.getHeight(); y++) {
					// pixel is not empty
					System.out.println("right"+x+","+y);
					System.out.println(isTransparent(sourceImage, x, y));

					if (!isTransparent(sourceImage, x, y)) {
						right = x;
						x =  -1;
						y = sourceImage.getHeight()+1;
						break;
					}
				}
			}

			for (int x = 0; x < sourceImage.getHeight(); x++) {
				for (int y = 0; y < sourceImage.getWidth(); y++) {
					// pixel is not empty
			
					if (!isTransparent(sourceImage, y, x)) {
						top = x;
						x =  sourceImage.getHeight()+1;
						y = sourceImage.getWidth()+1;
						break;
					}
				}
			}

			for (int x = sourceImage.getHeight() - 1; x >= 0; x--) {
				for (int y = 0; y < sourceImage.getWidth(); y++) {
					// pixel is not empty
					System.out.println("bottom"+x+","+y);
					System.out.println(isTransparent(sourceImage, y, x));
					
					if (!isTransparent(sourceImage, y, x)) {
						bottom = x;
						x =  -1;
						y = sourceImage.getWidth()+1;
						break;
					}
				}
			}
			
			System.out.println(left);
			System.out.println(right);
			System.out.println(bottom);
			System.out.println(top);

			BufferedImage dest = sourceImage.getSubimage(left, top, right-left, bottom-top);
			File f = new File(path+"_1.png");
			ImageIO.write(dest, "png", f);

		} catch (IOException e) {
		}
	}
	
	   public static boolean isTransparent(BufferedImage image, int x, int y ) {
	        int pixel = image.getRGB(x,y);
	        return (pixel>>24) == 0x00;
	    }
}
