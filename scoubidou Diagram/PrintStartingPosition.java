package scoubidou4;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

/**
 * This works but needs some improvements. The class is for drawing starting
 * positions righ and left hand
 * 
 * @author Yonatan Setbon
 *
 */
public class PrintStartingPosition extends JComponent {

	static Point centerCircle;
	static Shape string;
	static Color[] colors;
	int lengthOfStringEnd = MyPrintCanvasDiagram.lengthOfStringEnd;
	static int a =4;
	static int b = 4;
	int crissNumberOfLines = 4;
	int stringWidth = MyPrintCanvasDiagram.recWidth;
	int recWidth = MyPrintCanvasDiagram.recWidth;
	static int length = MyPrintCanvasDiagram.length;
	static int x1 = 2 * MyPrintCanvasDiagram.x1;
	static int y1 = 2 * MyPrintCanvasDiagram.y1;
	static// stroke of a string
	Stroke strk = new BasicStroke(MyPrintCanvasDiagram.recWidth);

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
		g2.setStroke(strk);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
				RenderingHints.VALUE_ANTIALIAS_ON);
		// painting the rectangle border and tiles underneafe
		g2.setStroke(strk);
		g2.setColor(Color.BLACK);
		for (int i = x1; i < 2 * length * a + x1; i = i + length) {
			for (int j = y1; j < 2 * length * b + y1; j = j + length) {
				g2.drawRect(i, j, length, length);
			}
		}

		// allPoints are points of the rectangle to draw vertical lines from
		nodePoint[] allPoints = new nodePoint[k];
		allPoints = listOfNodePoints();

		boolean leftHand = true;
		if (leftHand == true) {
			for (int i = 0; i < 2 * a; i += 2) {
				paintStringAndCircle((int) allPoints[i].x,
						(int) allPoints[i].y, "up", g2, i);
			}
			for (int i = 2 * a; i < 2 * a + 2 * b; i += 2) {
				paintStringAndCircle((int) allPoints[i].x,
						(int) allPoints[i].y, "left", g2, i);

			}
			for (int i = 2 * a + 2 * b; i < 4 * a + 2 * b; i += 2) {
				paintStringAndCircle((int) allPoints[i].x,
						(int) allPoints[i].y, "down", g2, i);

			}
			for (int i = 4 * a + 2 * b; i < 4 * a + 4 * b; i += 2) {
				paintStringAndCircle((int) allPoints[i].x,
						(int) allPoints[i].y, "right", g2, i);

			}
		} else {
			for (int i = 1; i < 2 * a; i += 2) {
				paintStringAndCircle((int) allPoints[i].x,
						(int) allPoints[i].y, "up", g2, i);

			}
			for (int i = 2 * a + 1; i < 2 * a + 2 * b; i += 2) {
				paintStringAndCircle((int) allPoints[i].x,
						(int) allPoints[i].y, "left", g2, i);

			}
			for (int i = 2 * a + 2 * b + 1; i < 4 * a + 2 * b; i += 2) {
				paintStringAndCircle((int) allPoints[i].x,
						(int) allPoints[i].y, "down", g2, i);

			}
			for (int i = 4 * a + 2 * b + 1; i < 4 * a + 4 * b; i += 2) {
				paintStringAndCircle((int) allPoints[i].x,
						(int) allPoints[i].y, "right", g2, i);

			}
		}

		g2.setColor(Color.BLACK);
		g2.setStroke(new BasicStroke(recWidth));
		// g2.drawRect(x1, y1, 2 * length * a, 2 * length * b);

	}

	/**
	 * this is the main method that will give us all the points given int's a
	 * and b
	 **/
	public static nodePoint[] listOfNodePoints() {
		// switches is 1 when green, -1 when red
		int switches = 1;
		int length = MyPrintCanvasDiagram.length;
		int width = 2 * length * a;
		int hight = 2 * length * b;
		int k = 4 * a + 4 * b;
		nodePoint[] allPoints = new nodePoint[k];
		// building all the points need to be green and red in top x axis
		for (int i = 0; i < 2 * a; i++) {
			if (switches == 1) {
				allPoints[i] = new nodePoint(x1 + length / 2 + i * length, y1,
						Color.GREEN);

			}
			if (switches == -1) {
				allPoints[i] = new nodePoint(x1 + length / 2 + i * length, y1,
						Color.RED);
			}
			switches = switches * -1;
		}
		// right Y axis, opposite colors from the left one
		switches = 1;
		for (int j = 0; j < 2 * b; j++) {
			if (switches == 1) {
				allPoints[2 * a + j] = new nodePoint(x1 + width, y1 + length
						/ 2 + j * length, Color.GREEN);
			}
			if (switches == -1) {
				allPoints[2 * a + j] = new nodePoint(x1 + width, y1 + length
						/ 2 + j * length, Color.RED);
			}
			switches = switches * -1;
		}
		// because its opposite colors in the down x axis
		switches = 1;
		for (int i = 0; i < 2 * a; i++) {
			if (switches == 1) {
				allPoints[2 * a + 2 * b + i] = new nodePoint(x1 + length / 2
						+ (2 * a - 1) * length - i * length, y1 + hight,
						Color.GREEN);

			}
			if (switches == -1) {
				allPoints[2 * a + 2 * b + i] = new nodePoint(x1 + length / 2
						+ (2 * a - 1) * length - i * length, y1 + hight,
						Color.RED);
			}
			switches = switches * -1;
		}
		switches = 1;
		// building all the points need to be green and red in left Y axis
		for (int j = 0; j < 2 * b; j++) {
			if (switches == 1) {
				allPoints[4 * a + 2 * b + j] = new nodePoint(x1, y1 + length
						/ 2 + (2 * b - 1) * length - j * length, Color.GREEN);
			}
			if (switches == -1) {
				allPoints[4 * a + 2 * b + j] = new nodePoint(x1, y1 + length
						/ 2 + (2 * b - 1) * length - j * length, Color.RED);
			}
			switches = switches * -1;
		}

		return allPoints;
	}

	public static Shape paintString(int firstPointx, int firstPointy,
			String direction) {
		if (direction == "up") {
			int x1 = firstPointx - MyPrintCanvasDiagram.length / 6;
			int x3 = firstPointx + MyPrintCanvasDiagram.length / 6;
			Point one = new Point(x1, firstPointy);
			int y2 = firstPointy -  (MyPrintCanvasDiagram.length+112);
			Point two = new Point(x1, y2);
			Point three = new Point(x3, y2);
			Point four = new Point(x3, y1);
			int centerCirclex = (x1 + x3) / 2;
			int centerCircley = y2;
			centerCircle = new Point(centerCirclex, centerCircley);

			Path2D.Double path = new Path2D.Double();
			// moving to point one
			path.moveTo(x1, firstPointy);
			// moving to point two
			path.lineTo(x1, y2);
			// moving to point three
			path.lineTo(x3, y2);
			// moving to point four
			path.lineTo(x3, firstPointy);
			// moving to point one
			path.lineTo(x1, firstPointy);
			// thuse finishing the square of the string
			return path;

		}

		if (direction == "down") {
			int x1 = firstPointx + MyPrintCanvasDiagram.length / 6;
			int x3 = firstPointx - MyPrintCanvasDiagram.length / 6;
			firstPointy = firstPointy;
			Point one = new Point(x1, firstPointy);
			int y2 = firstPointy +  (MyPrintCanvasDiagram.length+112);
			Point two = new Point(x1, y2);
			Point three = new Point(x3, y2);
			Point four = new Point(x3, y1);
			int centerCirclex = (x1 + x3) / 2;
			int centerCircley = y2;
			centerCircle = new Point(centerCirclex, centerCircley);
			Path2D.Double path = new Path2D.Double();
			// moving to point one
			path.moveTo(x1, firstPointy);
			// moving to point two
			path.lineTo(x1, y2);
			// moving to point three
			path.lineTo(x3, y2);
			// moving to point four
			path.lineTo(x3, firstPointy);
			// moving to point one
			path.lineTo(x1, firstPointy);
			// thuse finishing the square of the string
			return path;

		}
		if (direction == "right") {
			int y1 = firstPointy - MyPrintCanvasDiagram.length / 6;
			int y3 = firstPointy + MyPrintCanvasDiagram.length / 6;
			Point one = new Point(firstPointx, y1);
			int x2 = firstPointx -  (MyPrintCanvasDiagram.length+112);
			Point two = new Point(x2, y1);
			Point three = new Point(x2, y3);
			Point four = new Point(firstPointx, y3);
			int centerCirclex = x2;
			int centerCircley = (y1 + y3) / 2;
			centerCircle = new Point(centerCirclex, centerCircley);

			Path2D.Double path = new Path2D.Double();
			// moving to point one
			path.moveTo(firstPointx, y1);
			// moving to point two
			path.lineTo(x2, y1);
			// moving to point three
			path.lineTo(x2, y3);
			// moving to point four
			path.lineTo(firstPointx, y3);
			// moving to point one
			path.lineTo(firstPointx, y1);

			// thuse finishing the square of the string
			return path;
		}
		if (direction == "left") {
			int y1 = firstPointy + MyPrintCanvasDiagram.length / 6;
			int y3 = firstPointy - MyPrintCanvasDiagram.length / 6;
			Point one = new Point(firstPointx, y1);
			int x2 = firstPointx + (MyPrintCanvasDiagram.length+112);
			Point two = new Point(x2, y1);
			Point three = new Point(x2, y3);
			Point four = new Point(firstPointx, y3);
			int centerCirclex = x2;
			int centerCircley = (y1 + y3) / 2;
			centerCircle = new Point(centerCirclex, centerCircley);

			Path2D.Double path = new Path2D.Double();
			// moving to point one
			path.moveTo(firstPointx, y1);
			// moving to point two
			path.lineTo(x2, y1);
			// moving to point three
			path.lineTo(x2, y3);
			// moving to point four
			path.lineTo(firstPointx, y3);
			// moving to point one
			path.lineTo(firstPointx, y1);

			// thus finishing the square of the string
			return path;
		}
		// the method shouldn't get here
		System.out.println("error in print starting stitch");
		return null;

	}

	
	/**
	 * 
	 * @param x
	 * @param y
	 * @param direction
	 *            paints the string with the circle. for now I'm coloring with
	 *            white color.
	 */
	public static void paintStringAndCircle(int firstPointx, int firstPointy,
			String direction, Graphics2D g2, int i) {
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
				RenderingHints.VALUE_ANTIALIAS_ON);
		string = paintString(firstPointx, firstPointy, direction);
		Shape circle = drawCenteredCircle(centerCircle,
				MyPrintCanvasDiagram.length / 6);
	/*colors =  new Color[12];
		colors[0] = new Color(1,143,21);
		colors[1] = new Color(1,143,21);

		colors[2] = new Color(255,255,255);
		colors[3] = new Color(152,121,82);

		colors[4] =new Color(1,143,21);
		colors[5] = new Color(255,255,255);

		colors[6] = new  Color(1,143,21);
		colors[7] = new Color(1,143,21);

		colors[8] = new Color(255,255,255);
		colors[9] = new Color(152,121,82);

		colors[10] = new Color(152,121,82);
		colors[11] = new Color(255,255,255);*/
		g2.setColor(new Color(183, 159, 200));
		g2.fill(string);
		g2.setColor(Color.BLACK);
		g2.setStroke(strk);
		g2.draw(string);
		g2.setColor(new Color(183, 159, 200));
		g2.fill(circle);
		g2.setColor(Color.BLACK);
		g2.setStroke(strk);
		g2.draw(circle);
	}

	public static Color getColors(int i) {
		return colors[i];
	}

	/**
	 * 
	 * @param centerCircle
	 * @param R
	 * @return drawn circle for a single string
	 */
	public static Shape drawCenteredCircle(Point centerCircle, double R) {
		double centerCirclex = centerCircle.x;
		double centerCircley = centerCircle.y;

		Path2D.Double path = new Path2D.Double();

		path.moveTo(centerCirclex, centerCircley - R);
		// path.c
		double kappa = 0.55228;

		path.curveTo(centerCirclex + R * kappa, centerCircley - R,
				centerCirclex + R, centerCircley - R * kappa,
				centerCirclex + R, centerCircley); // curve to A', B', B
		path.curveTo(centerCirclex + R, centerCircley + R * kappa,
				centerCirclex + R * kappa, centerCircley + R, centerCirclex,
				centerCircley + R);
		path.curveTo(centerCirclex - R * kappa, centerCircley + R,
				centerCirclex - R, centerCircley + R * kappa,
				centerCirclex - R, centerCircley);
		path.curveTo(centerCirclex - R, centerCircley - R * kappa,
				centerCirclex - R * kappa, centerCircley - R, centerCirclex,
				centerCircley - R);

		AffineTransform at = AffineTransform.getTranslateInstance(0, 0);

		Shape path2 = path.createTransformedShape(at);

		return path2;

	}

	public static void main(String[] args) {
		PrintStartingPosition p = new PrintStartingPosition();
		p.a = 4;
		p.b = 4;
		
		BufferedImage rectangle = new BufferedImage(2 * x1 + 2 * a * length, 2
				* y1 + 2 * b * length, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = rectangle.createGraphics();
		p.paint(g2);
		try {
			ImageIO.write(rectangle, "png", new File("c://temp//" + a + "x" + b
					+ "startingPosition" + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
