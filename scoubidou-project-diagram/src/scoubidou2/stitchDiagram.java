package scoubidou2;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Arc2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class stitchDiagram extends Frame {
	int firstPointx = 150;
	int firstPointy = 100;
	int secondPointx = 175, secondPointy = 200;
	int x = firstPointx - 50, y = firstPointy;

	public static void main(String[] args) {
		stitchDiagram s = new stitchDiagram();
		s.setVisible(true);
	}

	public stitchDiagram() {
		super("Shape Sampler");
		setSize(400, 550);
	}

	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		int width = 400, height = 550;

		g2d.drawString("(x:" + x + "," + y + ")", x - 50, y);

		g2d.drawLine(x, y, x + 50, y);

		// line AB x1,y1,x2,y2
		g2d.drawLine(x + 50, y, x + 175, y + 300);

		int x1 = x + 50;
		Point one = new Point(firstPointx, firstPointy);
		int pointInbetweenTwoAndOnex = secondPointx - 25;
		int pointInbetweenTwoAndOney = secondPointy;
		int downPointInbetweenTwoAndOnex = pointInbetweenTwoAndOnex;
		int downPointInbetweenTwoAndOney = pointInbetweenTwoAndOney + 600;
		Point inbtwnTwoOne = new Point(pointInbetweenTwoAndOnex,
				pointInbetweenTwoAndOney);
		Point downInbtwnTwoOne = new Point(downPointInbetweenTwoAndOnex,
				downPointInbetweenTwoAndOney);
		Point two = myGetClosestPoint(one, inbtwnTwoOne, downInbtwnTwoOne);
		g2d.drawString("inbt", inbtwnTwoOne.x, inbtwnTwoOne.y);
		g2d.drawString("down", downInbtwnTwoOne.x, downInbtwnTwoOne.y);

		int y2 = y + 300;
		int x2 = x + 175;
		int y1 = y;
		int x3 = x + 100;
		int y3 = y;
		int x4 = two.x + 50;
		int y4 = two.y;

		// Point two = new Point(x2,y2);
		Point three = new Point(x3, y3);
		Point four = new Point(x4, y4);
		Point five = getClosestPointOnSegment(three, four, two);
		Point centerFiveTwo = new Point((two.x + five.x) / 2,
				(two.y + five.y) / 2);
		QuadCurve2D quadcurve = new QuadCurve2D.Float(firstPointx - 50,
				firstPointy, x + 75, y - 50, x + 100, y);
		g2d.draw(quadcurve);
		g2d.drawString("...", x + 75, y - 50);
		g2d.drawString("one", one.x, one.y);
		g2d.drawString("two", two.x, two.y);
		g2d.drawString("three", three.x, three.y);
		g2d.drawString("four", four.x, four.y);
		g2d.drawString("five", five.x, five.y);
		g2d.drawString("c", centerFiveTwo.x, centerFiveTwo.y);
		g2d.drawLine(x1, y1, x2, y2);

		// line CD
		g2d.drawLine(x3, y3, five.x, five.y);
		double distance = Math.sqrt((x2 - five.x) * (x2 - five.x)
				+ (y2 - five.y) * (y2 - five.y));
		Arc2D.Double arc = new Arc2D.Double();

		arc.setAngles(two.x, two.y, five.x, five.y);
		arc.setArcByCenter(centerFiveTwo.x + 1, centerFiveTwo.y, distance / 2,
				arc.getAngleStart(), arc.getAngleExtent(), Arc2D.OPEN);
		g2d.draw(arc);
		BufferedImage bi = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		// StraightLine2D a = new StraightLine2D();

		// Draw your chart

		try {
			ImageIO.write(bi, "PNG", new File("c://temp//" + "lala" + ".PNG"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}
	}

	public void drawCenteredCircle(Graphics2D g, int x, int y, int r) {
		x = x - (r / 2);
		y = y - (r / 2);
		g.drawOval(x, y, r, r);
	}

	public static Point getClosestPointOnSegment(Point ss, Point se, Point p) {
		return getClosestPointOnSegment(ss.x, ss.y, se.x, se.y, p.x, p.y);
	}

	/**
	 * Returns closest point on segment to point
	 * 
	 * @param sx1
	 *            segment x coord 1
	 * @param sy1
	 *            segment y coord 1
	 * @param sx2
	 *            segment x coord 2
	 * @param sy2
	 *            segment y coord 2
	 * @param px
	 *            point x coord
	 * @param py
	 *            point y coord
	 * @return closets point on segment to point
	 */
	public static Point getClosestPointOnSegment(int sx1, int sy1, int sx2,
			int sy2, int px, int py) {
		double xDelta = sx2 - sx1;
		double yDelta = sy2 - sy1;

		if ((xDelta == 0) && (yDelta == 0)) {
			throw new IllegalArgumentException(
					"Segment start equals segment end");
		}

		double u = ((px - sx1) * xDelta + (py - sy1) * yDelta)
				/ (xDelta * xDelta + yDelta * yDelta);

		final Point closestPoint;
		if (u < 0) {
			closestPoint = new Point(sx1, sy1);
		} else if (u > 1) {
			closestPoint = new Point(sx2, sy2);
		} else {
			closestPoint = new Point((int) Math.round(sx1 + u * xDelta),
					(int) Math.round(sy1 + u * yDelta));
		}

		return closestPoint;
	}

	public static Point myGetClosestPoint(Point pt1, Point pt2, Point p) {
		double u = ((p.x - pt1.x) * (pt2.x - pt1.x) + (p.y - pt1.y)
				* (pt2.y - pt1.y))
				/ (sqr(pt2.x - pt1.x) + sqr(pt2.y - pt1.y));
		if (u > 1.0)
			return (Point) pt2.clone();
		else if (u <= 0.0)
			return (Point) pt1.clone();
		else
			return new Point((int) (pt2.x * u + pt1.x * (1.0 - u) + 0.5),
					(int) (pt2.y * u + pt1.y * (1.0 - u) + 0.5));
	}

	private static double sqr(double x) {
		return x * x;
	}
}
