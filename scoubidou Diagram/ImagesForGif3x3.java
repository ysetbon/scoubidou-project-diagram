package scoubidou4;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

// a class for printing pictures for gif
public class ImagesForGif3x3 {
	public static void main(String[] args) {
		
		BufferedImage base = null;
		try {
			base = ImageIO.read(new File("C://temp//gif//"+0 +".png"));
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		for (int i = 348; i < 349; i++) {
			BufferedImage strings = null;
			try {
			    strings = ImageIO.read(new File("C://temp//gif//"+i +".png"));
			} catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			
			BufferedImage pictureGif = new BufferedImage(base.getWidth(), base.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D gif = pictureGif.createGraphics();
			gif.drawImage(base, 0, 0, null);
			gif.drawImage(strings, 0,0, null);
			
			try {
				ImageIO.write(pictureGif, "png", new File("c://temp//gif2//" + i+".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		BufferedImage strings2 = null;
		try {
		    strings2 = ImageIO.read(new File("C://temp//gif//"+ 348 +".png"));
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		
		
		for (int i = 349; i < 351; i++) {
			BufferedImage strings = null;
			try {
			    strings = ImageIO.read(new File("C://temp//gif//"+i +".png"));
			} catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			
			BufferedImage pictureGif2 = new BufferedImage(base.getWidth(), base.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D gif = pictureGif2.createGraphics();
			gif.drawImage(base, 0, 0, null);
			gif.drawImage(strings2, 0,0, null);
			gif.drawImage(strings, 0,0, null);
			try {
				ImageIO.write(pictureGif2, "png", new File("c://temp//gif2//" + i+".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		BufferedImage strings3 = null;
		try {
		    strings3 = ImageIO.read(new File("C://temp//gif//"+ 350+".png"));
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		for (int i = 351; i < 405; i++) {
			BufferedImage strings = null;
			try {
			    strings = ImageIO.read(new File("C://temp//gif//"+i +".png"));
			} catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			
			BufferedImage pictureGif3 = new BufferedImage(base.getWidth(), base.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D gif = pictureGif3.createGraphics();
			gif.drawImage(base, 0, 0, null);
			gif.drawImage(strings2, 0,0, null);
			gif.drawImage(strings3, 0,0, null);
			gif.drawImage(strings, 0,0, null);
			try {
				ImageIO.write(pictureGif3, "png", new File("c://temp//gif2//" + i+".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		
		
		
		BufferedImage strings4 = null;
		try {
		    strings4 = ImageIO.read(new File("C://temp//gif//"+ 404+".png"));
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		for (int i = 405; i < 407; i++) {
			BufferedImage strings = null;
			try {
			    strings = ImageIO.read(new File("C://temp//gif//"+i +".png"));
			} catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			
			BufferedImage pictureGif3 = new BufferedImage(base.getWidth(), base.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D gif = pictureGif3.createGraphics();
			gif.drawImage(base, 0, 0, null);
			gif.drawImage(strings2, 0,0, null);
			gif.drawImage(strings3, 0,0, null);
			gif.drawImage(strings4, 0,0, null);
			gif.drawImage(strings, 0,0, null);
			try {
				ImageIO.write(pictureGif3, "png", new File("c://temp//gif2//" + i+".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		

		BufferedImage strings5 = null;
		try {
		    strings5 = ImageIO.read(new File("C://temp//gif//"+ 406+".png"));
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		for (int i = 407; i < 695; i++) {
			BufferedImage strings = null;
			try {
			    strings = ImageIO.read(new File("C://temp//gif//"+i +".png"));
			} catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			
			BufferedImage pictureGif3 = new BufferedImage(base.getWidth(), base.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D gif = pictureGif3.createGraphics();
			gif.drawImage(base, 0, 0, null);
			gif.drawImage(strings2, 0,0, null);
			gif.drawImage(strings3, 0,0, null);
			gif.drawImage(strings4, 0,0, null);
			gif.drawImage(strings5, 0,0, null);

			gif.drawImage(strings, 0,0, null);
			try {
				ImageIO.write(pictureGif3, "png", new File("c://temp//gif2//" + i+".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}



		BufferedImage strings6 = null;
		try {
		    strings6 = ImageIO.read(new File("C://temp//gif//"+ 694+".png"));
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		for (int i = 695; i < 696; i++) {
			BufferedImage strings = null;
			try {
			    strings = ImageIO.read(new File("C://temp//gif//"+i +".png"));
			} catch (IOException e) {
			    // TODO Auto-generated catch block
			    e.printStackTrace();
			}
			
			BufferedImage pictureGif3 = new BufferedImage(base.getWidth(), base.getHeight(), BufferedImage.TYPE_INT_ARGB);
			Graphics2D gif = pictureGif3.createGraphics();
			gif.drawImage(base, 0, 0, null);
			gif.drawImage(strings2, 0,0, null);
			gif.drawImage(strings3, 0,0, null);
			gif.drawImage(strings4, 0,0, null);
			gif.drawImage(strings5, 0,0, null);
			gif.drawImage(strings6, 0,0, null);

			gif.drawImage(strings, 0,0, null);
			try {
				ImageIO.write(pictureGif3, "png", new File("c://temp//gif2//" + i+".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}

		
	}

}
