package scoubidou4;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

// a class for printing pictures for gif
public class ImagesForGif2X2 {
	public static void main(String[] args) {
		
		BufferedImage base = null;
		try {
			base = ImageIO.read(new File("C://temp//gif//"+0 +".png"));
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		for (int i = 1; i < 3; i++) {
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
		    strings2 = ImageIO.read(new File("C://temp//gif//"+ 2 +".png"));
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		
		
		for (int i = 3; i < 4; i++) {
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
		    strings3 = ImageIO.read(new File("C://temp//gif//"+ 3+".png"));
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		for (int i = 4; i < 6; i++) {
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
		    strings4 = ImageIO.read(new File("C://temp//gif//"+ 5+".png"));
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		for (int i = 6; i < 112; i++) {
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

	}

}
