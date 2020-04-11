package scoubidouDiagram;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.QuadCurve2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;


import java.util.Deque;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class stitchDiagram extends Frame {
	int firstPointx,  firstPointy,  secondPointx, secondPointy, movingPointx, movingPointy;
	Shape string;
	Shape stringLines;
	//this is for transformation of the image
	Rectangle r;
	public static void main(String[] args) {
		stitchDiagram s = new stitchDiagram(200,200,450,700, "upToDown");
		s.setVisible(true);
		s.setBounds(0, 0, 1000, 1000);
	}


	public stitchDiagram(int firstPointx, int firstPointy, int secondPointx, int secondPointy, String s ) {
		Point one = new Point (firstPointx, firstPointy);
		Point two = new Point (secondPointx,secondPointy);
		Point newOne = new Point();
		Point newTwo = new Point();
		this.firstPointx=firstPointx;
		this.firstPointy=firstPointy;
		this.secondPointx=secondPointx;
		this.secondPointy=secondPointy;

		if(s == "upToDown"){
		this.string = paintString(firstPointx, firstPointy, secondPointx, secondPointy);
	//	this.stringLines = paintStringAddLastLine(firstPointx, firstPointy, secondPointx, secondPointy,string);
		}
		if(s=="leftToRight"){
			AffineTransform transform = new AffineTransform();
			transform.rotate(Math.toRadians(90), string.getBounds().getX() + string.getBounds().width/2,
					string.getBounds().getY() + string.getBounds().height/2);
			
			transform.transform(one, newOne);
			transform.transform(two, newTwo);
			//this.string= paintString(newOne.x, newOne.y, newTwo.x, newTwo.y);
		//this.string = paintdeg( newOne.x, newOne.y, newTwo.x, newTwo.y, -90);
		}
		
	}
	/**
	 * don't use this method, it's still not working. it should at the end do the "over" of the string
	 * @param firstPointx
	 * @param firstPointy
	 * @param secondPointx
	 * @param secondPointy
	 * @param s
	 * @return
	 */
	public Shape paintStringAddLastLine(int firstPointx, int firstPointy, int secondPointx, int secondPointy, Shape s ){
		int x1=firstPointx;
		Point one = new Point(firstPointx,firstPointy);
		int pointInbetweenTwoAndOnex = secondPointx ;
		int pointInbetweenTwoAndOney = secondPointy;
		int downPointInbetweenTwoAndOnex = pointInbetweenTwoAndOnex;
		int downPointInbetweenTwoAndOney = pointInbetweenTwoAndOney+MyPrintCanvasDiagram.length/4;
		Point inbtwnTwoOne = new Point(pointInbetweenTwoAndOnex,pointInbetweenTwoAndOney);
		Point downInbtwnTwoOne = new Point(downPointInbetweenTwoAndOnex,downPointInbetweenTwoAndOney);
		Point two = getClosestPointOnALine(one, inbtwnTwoOne,downInbtwnTwoOne);
		int y1=firstPointy;
		int x3=firstPointx+MyPrintCanvasDiagram.length/2-MyPrintCanvasDiagram.length/8;
		int y3=firstPointy;
		int x4 = two.x+MyPrintCanvasDiagram.length/2-MyPrintCanvasDiagram.length/8;
		int y4= two.y;
		Point three = new Point (x3,y3);
		Point four = new Point(x4,y4);
		Point five = getClosestPointOnALine(three, four,two);
		AffineTransform at = AffineTransform.getTranslateInstance(0,0);

		GeneralPath path = new GeneralPath(s);
		path.moveTo(two.x, two.y);
		path.lineTo(one.x, one.y);
		path.moveTo(five.x, five.y);
		path.lineTo(three.x, three.y);
		Shape path2 = path.createTransformedShape(at);
		return path2;
	}
	/**
	 * 
	 * @param firstPointx - 
	 * @param firstPointy
	 * @param secondPointx
	 * @param secondPointy
	 * @return
	 */
	public Shape paintString(int firstPointx, int firstPointy, int secondPointx, int secondPointy) {
		int x=firstPointx-MyPrintCanvasDiagram.length/2,  y=firstPointy;
		System.out.println("paintString: x"+x+",y"+y);
		Path2D.Double path = new Path2D.Double();
		movingPointx=x;
		movingPointy=y;
		System.out.println("in paintString movingPoint x="+movingPointx+" ,y="+movingPointy);

		int x1=firstPointx;
		Point one = new Point(firstPointx,firstPointy);
		int pointInbetweenTwoAndOnex = secondPointx ;
		int pointInbetweenTwoAndOney = secondPointy;
		int downPointInbetweenTwoAndOnex = pointInbetweenTwoAndOnex;
		int downPointInbetweenTwoAndOney = pointInbetweenTwoAndOney+MyPrintCanvasDiagram.length/4;
		Point inbtwnTwoOne = new Point(pointInbetweenTwoAndOnex,pointInbetweenTwoAndOney);
		Point downInbtwnTwoOne = new Point(downPointInbetweenTwoAndOnex,downPointInbetweenTwoAndOney);
		Point two = getClosestPointOnALine(one, inbtwnTwoOne,downInbtwnTwoOne);
		int y1=firstPointy;
		int x3=firstPointx+MyPrintCanvasDiagram.length/2-MyPrintCanvasDiagram.length/8;
		int y3=firstPointy;
		int x4 = two.x+MyPrintCanvasDiagram.length/2-MyPrintCanvasDiagram.length/8;
		int y4= two.y;
		if(firstPointx > secondPointx){
			Point three = new Point (x3,y3);
			Point four = new Point(x4,y4);
			Point five = getClosestPointOnALine(three, four,two);
			Point centerFiveTwo = new Point((int)Math.round((two.x+five.x)/2),(int)Math.round((two.y+five.y)/2));
			
			//if second given point is lefter than the first point then we sub a little more		
			Point secondBizePoint = new Point (three.x+MyPrintCanvasDiagram.length/2, three.y-MyPrintCanvasDiagram.length/2);
			Point secondOnLineBizePoint = getClosestPointOnALine(four, three,secondBizePoint);
			QuadCurve2D quadcurve = new QuadCurve2D.Float(	firstPointx-MyPrintCanvasDiagram.length/2, firstPointy,													
					secondOnLineBizePoint.x, secondOnLineBizePoint.y, three.x,three.y);

			double distance = Math.sqrt((two.x-five.x)*(two.x-five.x) + (two.y-five.y)*(two.y-five.y));
			Arc2D.Double arc = new Arc2D.Double();			
			arc.setAngles(two.x, two.y, five.x, five.y);
			arc.setArcByCenter(centerFiveTwo.x, centerFiveTwo.y, (int)Math.round(distance/2), arc.getAngleStart(), arc.getAngleExtent(), Arc2D.OPEN);
			
			path.moveTo(x, y);
			path.lineTo(firstPointx, firstPointy);
			path.lineTo(two.x-1, two.y);
			int centerCirclex = centerFiveTwo.x;
			int centerCircley = centerFiveTwo.y;
			double kappa = 0.55228;		
			//creating circle
			distance = Math.sqrt((two.x-five.x)*(two.x-five.x) + (two.y-five.y)*(two.y-five.y));
			double  R= distance/2;
			path.moveTo(centerCirclex, centerCircley-R);
			//path.c
			path.curveTo(centerCirclex+R*kappa, centerCircley-R, centerCirclex+R, centerCircley-R*kappa, centerCirclex+R, centerCircley); // curve to A', B', B
			path.curveTo(centerCirclex+R, centerCircley+R*kappa, centerCirclex+R*kappa, centerCircley+R, centerCirclex, centerCircley+R );
			path.curveTo(centerCirclex-R*kappa, centerCircley+R, centerCirclex-R, centerCircley+R*kappa, centerCirclex-R, centerCircley);
			path.curveTo(centerCirclex-R, centerCircley-R*kappa, centerCirclex-R*kappa, centerCircley-R, centerCirclex, centerCircley-R );
			//going to point four
			path.moveTo(five.x-1, five.y);
			path.lineTo(three.x, three.y);
			//back to point zero
			path.moveTo(x, y);
			//doing the curve
			path.curveTo(x, y,													
					secondOnLineBizePoint.x, secondOnLineBizePoint.y, three.x,three.y);
		}
		
		if(firstPointx == secondPointx){
			Point three = new Point (x3,y3);
			Point four = new Point(x4,y4);
			Point five = getClosestPointOnSegment(four, three,two);
			Point centerFiveTwo = new Point((int)Math.round((two.x+five.x)/2),(int)Math.round((two.y+five.y)/2));

			Point secondBizePoint = new Point (firstPointx-MyPrintCanvasDiagram.length/2, firstPointy-MyPrintCanvasDiagram.length/2);
			Point secondOnLineBizePoint = getClosestPointOnALine(four, three,secondBizePoint);
			QuadCurve2D quadcurve = new QuadCurve2D.Float(firstPointx-MyPrintCanvasDiagram.length/2, firstPointy,														
					secondOnLineBizePoint.x, secondOnLineBizePoint.y, three.x,three.y);		
			double distance = Math.sqrt((two.x-five.x)*(two.x-five.x) + (two.y-five.y)*(two.y-five.y));
			Arc2D.Double arc = new Arc2D.Double();

			arc.setAngles(two.x, two.y, five.x, five.y);
			arc.setArcByCenter(centerFiveTwo.x, centerFiveTwo.y, (int)Math.round(distance/2), arc.getAngleStart(), arc.getAngleExtent(), Arc2D.OPEN);
			
			int centerCirclex = centerFiveTwo.x;
			int centerCircley = centerFiveTwo.y;
			System.out.println(centerCirclex+"y:"+centerCircley);
			double kappa = 0.55228;		
			//creating circle
			distance = Math.sqrt((two.x-five.x)*(two.x-five.x) + (two.y-five.y)*(two.y-five.y));
			double  R= (distance/2)+0.5;
				path.moveTo(centerCirclex, centerCircley-R);
				path.curveTo(centerCirclex+R*kappa, centerCircley-R, centerCirclex+R, centerCircley-R*kappa, centerCirclex+R, centerCircley); // curve to A', B', B
				path.curveTo(centerCirclex+R, centerCircley+R*kappa, centerCirclex+R*kappa, centerCircley+R, centerCirclex, centerCircley+R );
				path.curveTo(centerCirclex-R*kappa, centerCircley+R, centerCirclex-R, centerCircley+R*kappa, centerCirclex-R, centerCircley);
				path.curveTo(centerCirclex-R, centerCircley-R*kappa, centerCirclex-R*kappa, centerCircley-R, centerCirclex, centerCircley-R );
				
			//going to point five
			path.moveTo(firstPointx-1, firstPointy);
			path.lineTo(two.x-1, two.y);

			path.moveTo(five.x, five.y);
			path.lineTo(three.x, three.y);
			//back to point zero
			path.moveTo(x, y);
			//doing the curve
			path.quadTo(secondOnLineBizePoint.x, secondOnLineBizePoint.y, three.x,three.y);
			path.moveTo(x, y);
			path.lineTo(firstPointx-1, firstPointy);
		}

		if(firstPointx < secondPointx){
			Point three = new Point (x3,y3);
			Point four = new Point(x4,y4);
			Point five = getClosestPointOnSegment(four, three,two);
			Point centerFiveTwo = new Point((int)Math.round((two.x+five.x)/2),(int)Math.round((two.y+five.y)/2));

			Point secondBizePoint = new Point (firstPointx-MyPrintCanvasDiagram.length/2, firstPointy-MyPrintCanvasDiagram.length/2);
			Point secondOnLineBizePoint = getClosestPointOnALine(four, three,secondBizePoint);
	
			double distance = Math.sqrt((two.x-five.x)*(two.x-five.x) + (two.y-five.y)*(two.y-five.y));
			Arc2D.Double arc = new Arc2D.Double();

			arc.setAngles(two.x, two.y, five.x, five.y);
			arc.setArcByCenter(centerFiveTwo.x, centerFiveTwo.y, (int)Math.round(distance/2), arc.getAngleStart(), arc.getAngleExtent(), Arc2D.OPEN);
			
			int centerCirclex = centerFiveTwo.x;
			int centerCircley = centerFiveTwo.y;
			System.out.println(centerCirclex+"y:"+centerCircley);
			//for crearing a circle and not a curved rectangle we need kappa
			double kappa = 0.55228;		
			//creating circle
			distance = Math.sqrt((two.x-five.x)*(two.x-five.x) + (two.y-five.y)*(two.y-five.y));
			double  R= distance/2;
			
				path.moveTo(centerCirclex, centerCircley-R);
				path.curveTo(centerCirclex+R*kappa, centerCircley-R, centerCirclex+R, centerCircley-R*kappa, centerCirclex+R, centerCircley); // curve to A', B', B
				path.curveTo(centerCirclex+R, centerCircley+R*kappa, centerCirclex+R*kappa, centerCircley+R, centerCirclex, centerCircley+R );
				path.curveTo(centerCirclex-R*kappa, centerCircley+R, centerCirclex-R, centerCircley+R*kappa, centerCirclex-R, centerCircley);
				path.curveTo(centerCirclex-R, centerCircley-R*kappa, centerCirclex-R*kappa, centerCircley-R, centerCirclex, centerCircley-R );
			//going to point five
			path.moveTo(firstPointx, firstPointy);
		
			path.quadTo(firstPointx, firstPointy, two.x-1, two.y);
			path.moveTo(five.x, five.y);
			path.lineTo(three.x, three.y);
			//back to point zero
			path.moveTo(x, y);
			//doing the curve
			path.quadTo(secondOnLineBizePoint.x, secondOnLineBizePoint.y, three.x,three.y);
			path.moveTo(x, y);
			path.lineTo(firstPointx, firstPointy);
		}

		AffineTransform at = AffineTransform.getTranslateInstance(0, 0);

		Shape path2 = path.createTransformedShape(at);

		return path2;


	}

	public void drawCenteredCircle(Graphics2D g, int x, int y, int r) {
		x = x-(r/2);
		y = y-(r/2);
		g.drawOval(x,y,r,r);
	}
	public static Point getClosestPointOnSegment(Point ss, Point se, Point p)
	{
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
	public static Point getClosestPointOnSegment(int sx1, int sy1, int sx2, int sy2, int px, int py)
	{
		double xDelta = sx2 - sx1;
		double yDelta = sy2 - sy1;

		if ((xDelta == 0) && (yDelta == 0))
		{
			throw new IllegalArgumentException("Segment start equals segment end");
		}

		double u = ((px - sx1) * xDelta + (py - sy1) * yDelta) / (xDelta * xDelta + yDelta * yDelta);
		final Point closestPoint;
		if (u < 0)
		{
			closestPoint = new Point(sx1, sy1);
		}
		else if (u > 1)
		{
			closestPoint = new Point(sx2, sy2);
		}
		else
		{
			closestPoint = new Point((int) Math.round(sx1 + u * xDelta), (int) Math.round(sy1 + u * yDelta));
		}
		return closestPoint;
	}


	public static Point myGetClosestPoint(Point pt1, Point pt2, Point p)
	{
		double u = ((p.x-pt1.x)*(pt2.x-pt1.x)+(p.y-pt1.y)*(pt2.y-pt1.y))/(sqr(pt2.x-pt1.x)+sqr(pt2.y-pt1.y));
		if (u > 1.0)
			return (Point)pt2.clone();
		else if (u <= 0.0)
			return (Point)pt1.clone();
		else
			return new Point((int)(pt2.x*u+pt1.x*(1.0-u)+0.5), (int)(pt2.y*u+pt1.y*(1.0-u)+0.5));
	}

	private static double sqr(double x)
	{
		return x*x;
	}

	public static Point getClosestPointOnALine(Point pt1,Point pt2, Point pt0){
		double  ratio = Math.pow(((sqr(pt1.x-pt0.x)+sqr(pt1.y-pt0.y))*(sqr(pt2.x-pt1.x) + sqr(pt2.y-pt1.y)) 
				- sqr((pt2.x-pt1.x)*(pt1.y-pt0.y) 
						- (pt1.x-pt0.x)*(pt2.y-pt1.y))),0.5)/(sqr(pt2.x-pt1.x) + sqr(pt2.y-pt1.y));

		int xc = (int) Math.round(pt1.x + (pt2.x -pt1.x)*ratio);
		int yc = (int) Math.round(pt1.y + (pt2.y -pt1.y)*ratio);
		Point newP = new Point (xc,yc);
		return newP;
	}
	public void paint(Graphics g) {
		   Graphics2D g2 = (Graphics2D)g;
		    RenderingHints rh = new RenderingHints(
		             RenderingHints.KEY_TEXT_ANTIALIASING,
		             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		    RenderingHints rendring = new RenderingHints(RenderingHints.KEY_STROKE_CONTROL,RenderingHints.VALUE_STROKE_NORMALIZE);
		    g2.setRenderingHints(rh);
		    g2.setRenderingHints(rendring);
	
		Rectangle r = string.getBounds();
	
		// Draw the shapes in their original locations.

		g2.setPaint(Color.BLACK);
		System.out.println("before drawing to graphics :movingPoint x="+movingPointx+" ,y="+movingPointy);

		BufferedImage img = makeImage(firstPointx,  firstPointy,  secondPointx,  secondPointy,string,Color.CYAN);
		System.out.println("after drawing to graphics :movingPoint x="+movingPointx+" ,y="+movingPointy);

		g2.drawImage(img,null,0,0);

	//	paintdeg90(firstPointx, firstPointy, 90, g2, img);

		try {
			ImageIO.write(img,"png", new File("c://temp//"+002+".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("movingPoint x="+movingPointx+" ,y="+movingPointy);
		// Transform the Graphics2D.

	}	
	/**
	 * 
	 * @param firstPointx
	 * @param firstPointy
	 * @param angle
	 * @param g2d
	 * @param img
	 */
	public void paintdeg90(int firstPointx, int firstPointy, int angle, Graphics2D g2d, BufferedImage img){

		//x and y are the specific point I want to rotate with,the img its (0,77)
		//but in the g2d its the (x,y) point.
		int x=firstPointx-MyPrintCanvasDiagram.length/2 ;
		int	y=firstPointy ;
		AffineTransform at = AffineTransform.getRotateInstance(0);	
		// Rotation information
		double rotationRequired = Math.toRadians (angle);
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, 0, 0);
		AffineTransform tt = AffineTransform.getTranslateInstance(x, y);
		AffineTransform tOfImage=AffineTransform.getTranslateInstance(0, 77);
		
		g2d.setTransform(at);
		g2d.transform(tOfImage);
		g2d.transform(tt);
		g2d.transform(tx);
		
	     Point2D c = new Point2D.Double(0,0);
		 AffineTransform att = g2d.getTransform();
         Point2D screenPoint = att.transform(c, new Point2D.Double());
         //trying to set the point I rotate back to the 0,0 point
 		g2d.translate(-screenPoint.getX(),screenPoint.getY());
		double newx=g2d.getTransform().getTranslateX();
		double newy=g2d.getTransform().getTranslateY();
 		g2d.translate(+newx,-newy);
 		//setting the point to where I want it to be, (y,-x) becuase I rotate in 90 degrees
 		g2d.translate(y,-x);		
		g2d.drawImage(img,0,0, null);

	}
	public void paintdeg180(int firstPointx, int firstPointy, int angle, Graphics2D g2d, BufferedImage img){

		System.out.println("at the very beginning g2d x:"+g2d.getTransform().getTranslateX()+" g2d: y "+g2d.getTransform().getTranslateY());

		int x=firstPointx-MyPrintCanvasDiagram.length/2 ;
		int	y=firstPointy ;
		
	

		AffineTransform at = AffineTransform.getRotateInstance(0);
		System.out.println("MyPrintCanvasdiagram.length="+MyPrintCanvasDiagram.length);
		System.out.println("myprintCanvas= "+MyPrintCanvasDiagram.length/2);
		// Rotation information
		double rotationRequired = Math.toRadians (angle);
		System.out.println("g2d x:"+g2d.getTransform().getTranslateX()+" g2d: y "+g2d.getTransform().getTranslateY());
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, 0, 0);
		AffineTransform tt = AffineTransform.getTranslateInstance(x, y);
		AffineTransform tOfImage=AffineTransform.getTranslateInstance(2, 72);
		g2d.setTransform(at);
		g2d.transform(tOfImage);
	//	g2d.drawImage(img,0,0, null);

		System.out.println("after tOfImage ,g2d x:"+g2d.getTransform().getTranslateX()+" g2d: y "+g2d.getTransform().getTranslateY());

		g2d.transform(tt);
	//	g2d.drawImage(img,0,0, null);

		System.out.println("after x and y ,g2d x:"+g2d.getTransform().getTranslateX()+" g2d: y "+g2d.getTransform().getTranslateY());

		g2d.transform(tx);
		g2d.drawImage(img,0,0, null);

		System.out.println("after trasfrom tx ,g2d x:"+g2d.getTransform().getTranslateX()+" g2d: y "+g2d.getTransform().getTranslateY());

		//g2d.translate(-72,-71);
		System.out.println("after transelating back  ,g2d x:"+g2d.getTransform().getTranslateX()+" g2d: y "+g2d.getTransform().getTranslateY());
		
		//g2d.drawImage(img,0,0, null);

	//	tx.translate(distOfThe2PointsX, distOfThe2PointsY);
	
		// Drawing the rotated image at the required drawing locations
		
		System.out.println("yis"+y+" x is "+x);
	//	g2d.drawImage(op.filter(img, null), 0, y/2, null);
		System.out.println("img y is "+y);

	}
	public void paintdeg270(int firstPointx, int firstPointy, int angle, Graphics2D g2d, BufferedImage img){

		System.out.println("at the very beginning g2d x:"+g2d.getTransform().getTranslateX()+" g2d: y "+g2d.getTransform().getTranslateY());

		int x=firstPointx-MyPrintCanvasDiagram.length/2 ;
		int	y=firstPointy ;
		
	

		AffineTransform at = AffineTransform.getRotateInstance(0);
		System.out.println("MyPrintCanvasdiagram.length="+MyPrintCanvasDiagram.length);
		System.out.println("myprintCanvas= "+MyPrintCanvasDiagram.length/2);
		// Rotation information
		double rotationRequired = Math.toRadians (angle);
		System.out.println("g2d x:"+g2d.getTransform().getTranslateX()+" g2d: y "+g2d.getTransform().getTranslateY());
		AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, 0, 0);
		AffineTransform tt = AffineTransform.getTranslateInstance(x, y);
		AffineTransform tOfImage=AffineTransform.getTranslateInstance(2, 72);
		g2d.setTransform(at);
		g2d.transform(tOfImage);
	//	g2d.drawImage(img,0,0, null);

		System.out.println("after tOfImage ,g2d x:"+g2d.getTransform().getTranslateX()+" g2d: y "+g2d.getTransform().getTranslateY());

		g2d.transform(tt);
	//	g2d.drawImage(img,0,0, null);

		System.out.println("after x and y ,g2d x:"+g2d.getTransform().getTranslateX()+" g2d: y "+g2d.getTransform().getTranslateY());

		g2d.transform(tx);

		System.out.println("after trasfrom tx ,g2d x:"+g2d.getTransform().getTranslateX()+" g2d: y "+g2d.getTransform().getTranslateY());

		g2d.translate(+68,-72);
		System.out.println("after transelating back  ,g2d x:"+g2d.getTransform().getTranslateX()+" g2d: y "+g2d.getTransform().getTranslateY());
		
		g2d.drawImage(img,0,0, null);

	
		
	}
	
	public BufferedImage makeImage(int firstPointx, int firstPointy, int secondPointx, int secondPointy,Shape s, Color stringC) {
		int x1=firstPointx;
		int x=firstPointx-MyPrintCanvasDiagram.length/2 ;
		int	y=firstPointy ;
		Point one = new Point(firstPointx,firstPointy);
		int pointInbetweenTwoAndOnex = secondPointx ;
		int pointInbetweenTwoAndOney = secondPointy;
		int downPointInbetweenTwoAndOnex = pointInbetweenTwoAndOnex;
		int downPointInbetweenTwoAndOney = pointInbetweenTwoAndOney+MyPrintCanvasDiagram.length/4;
		Point inbtwnTwoOne = new Point(pointInbetweenTwoAndOnex,pointInbetweenTwoAndOney);
		Point downInbtwnTwoOne = new Point(downPointInbetweenTwoAndOnex,downPointInbetweenTwoAndOney);
		Point two = getClosestPointOnALine(one, inbtwnTwoOne,downInbtwnTwoOne);
		int y1=firstPointy;
		int x3=firstPointx+MyPrintCanvasDiagram.length/2-MyPrintCanvasDiagram.length/8;
		int y3=firstPointy;
		int x4 = two.x+MyPrintCanvasDiagram.length/2-MyPrintCanvasDiagram.length/8;
		int y4= two.y;
		Point three = new Point (x3,y3);
		Point four = new Point(x4,y4);
		Point five = getClosestPointOnALine(three, four,two);
		Point centerFiveTwo = new Point((int)Math.round((two.x+five.x)/2),(int)Math.round((two.y+five.y)/2));


		
		this.r = s.getBounds();
		BufferedImage image = new BufferedImage(r.width, r.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D gr = image.createGraphics();
		// move the shape in the region of the image. Remember that we will need to do in some point of the code to
		//do -1.
		
		gr.translate(-r.x+1, -r.y);
		movingPointx= movingPointx-r.x+1;
		movingPointy= movingPointy -r.y;
		System.out.println("in makeImage movingPoint x="+movingPointx+" ,y="+movingPointy);

		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // Anti-alias!
	        RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setStroke(new BasicStroke(3f));
		gr.draw(s);
		  for (int i = 0; i < image.getWidth(); i++) {
	            for (int j = 0; j < image.getHeight(); j++) {
	                int rgba = image.getRGB(i, j);
	                Color col = new Color(rgba, true);
	                col = new Color(255 - col.getRed(),
	                                255 - col.getGreen(),
	                                255 - col.getBlue());
	                image.setRGB(i, j, col.getRGB());
	            }
	        }

		BufferedImage bufferedImage = image;
		//cropping and painting the bufferedImage
		Color c=new Color(1f,0f,0f,0f );
		
		Point p1 = new Point((one.x+three.x)/2-r.x, one.y-r.y);
		Point centerOfCircle = new Point(centerFiveTwo.x-r.x, centerFiveTwo.y-r.y);
		floodFill(bufferedImage, centerOfCircle, Color.WHITE, Color.RED);
		floodFill(bufferedImage, p1, Color.WHITE, Color.RED);

		
	//	floodFill2(bufferedImage,clear1,Color.WHITE, Color.WHITE);
		FloodFiller flood = new FloodFiller();
		bufferedImage=flood.fill(bufferedImage, 1, 1, c);
		//flood.fill( r.width,  r.height, c);
	
	
		return bufferedImage;
	}
	
	public BufferedImage makeImageWithTransparancy(BufferedImage image_to_save){
		BufferedImage image_to_save2=new BufferedImage(image_to_save.getWidth(),image_to_save.getHeight(), BufferedImage.TYPE_INT_ARGB);
		image_to_save2.getGraphics().drawImage(image_to_save,0,0,null);
		image_to_save = image_to_save2;
		
		return image_to_save;
		
	}
	public int[][] getMatrixOfImage(BufferedImage bufferedImage) {
		int width = bufferedImage.getWidth(null);
		int height = bufferedImage.getHeight(null);
		int[][] pixels = new int[width][height];
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				pixels[i][j] = bufferedImage.getRGB(i, j);
			}
		}

		return pixels;
	}
	
	
	 public void floodFill(BufferedImage image, Point node, Color targetColor, Color replacementColor) {
		    int width = image.getWidth();
		    int height = image.getHeight();
		    int target = targetColor.getRGB();
		    int replacement = replacementColor.getRGB();
		    int distanceOfColor=330;
		    if (target != replacement) {
		      Deque<Point> queue = new LinkedList<Point>();
		      do {
		        int x = node.x;
		        int y = node.y;
		        while (x > 0 && ColorDistance(image.getRGB(x - 1, y), target)<=distanceOfColor) {
		          x--;
		        }
		        boolean spanUp = false;
		        boolean spanDown = false;
		        while (x < width && ColorDistance(image.getRGB(x, y), target) <=distanceOfColor) {
		          image.setRGB(x, y, replacement);
		          if (!spanUp && y > 0 && image.getRGB(x, y - 1) == target) {
		            queue.add(new Point(x, y - 1));
		            spanUp = true;
		          } else if (spanUp && y > 0 && ColorDistance(image.getRGB(x, y - 1), target) >distanceOfColor) {
		            spanUp = false;
		          }
		          if (!spanDown && y < height - 1 && ColorDistance(image.getRGB(x, y + 1), target) <=distanceOfColor) {
		            queue.add(new Point(x, y + 1));
		            spanDown = true;
		          } else if (spanDown && y < height - 1 && ColorDistance(image.getRGB(x, y + 1), target) >distanceOfColor) {
		            spanDown = false;
		          }
		          x++;
		        }
		      } while ((node = queue.pollFirst()) != null);
		    }
		  }
	 
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
	
		  public void floodFill2(BufferedImage image, Point node, Color targetColor, Color replacementColor) {
		    int width = image.getWidth();
		    int height = image.getHeight();
		    int target = targetColor.getRGB();
		    int replacement = replacementColor.getRGB();
		    if (target != replacement) {
		      Deque<Point> queue = new LinkedList<Point>();
		      do {
		        int x = node.x;
		        int y = node.y;
		        while (x > 0 && image.getRGB(x - 1, y) == target) {
		          x--;
		        }
		        boolean spanUp = false;
		        boolean spanDown = false;
		        while (x < width && image.getRGB(x, y) == target) {
		          image.setRGB(x, y, replacement);
		          if (!spanUp && y > 0 && image.getRGB(x, y - 1) == target) {
		            queue.add(new Point(x, y - 1));
		            spanUp = true;
		          } else if (spanUp && y > 0 && image.getRGB(x, y - 1) != target) {
		            spanUp = false;
		          }
		          if (!spanDown && y < height - 1 && image.getRGB(x, y + 1) == target) {
		            queue.add(new Point(x, y + 1));
		            spanDown = true;
		          } else if (spanDown && y < height - 1 && image.getRGB(x, y + 1) != target) {
		            spanDown = false;
		          }
		          x++;
		        }
		      } while ((node = queue.pollFirst()) != null);
		    }
		  }
	 public void floodFillTransparent(BufferedImage budderedImage, Point node, Color targetColor, Color replacementColor) {
		 BufferedImage image = makeImageWithTransparancy(budderedImage);
		    int width = image.getWidth();
		    int height = image.getHeight();
		    int target = targetColor.getRGB();
		    int replacement = replacementColor.getRGB();
		    Color transparent = new Color(0f, 0f, 0f, 1f);
		    int distanceOfColor=0;
		    if (target == replacement) {
		      Deque<Point> queue = new LinkedList<Point>();
		      do {
		        int x = node.x;
		        int y = node.y;
		        while (x > 0 && ColorDistance(image.getRGB(x - 1, y), target)<=distanceOfColor) {
		          x--;
		        	System.out.println("hello");

		        }
		        boolean spanUp = false;
		        boolean spanDown = false;
		        
		        while (x < width && ColorDistance(image.getRGB(x, y), target) <=distanceOfColor) {
		        	byte alpha= (byte) 255;
		        	 alpha %= 0xff;
			        	System.out.println("hello2");

		        	 int color = image.getRGB(x, y);

		             int mc = (alpha << 24) | 0x00ffffff;
		             int newcolor = color & mc;
		             image.setRGB(x, y, newcolor);            

		          
		          if (!spanUp && y > 0 && image.getRGB(x, y - 1) == target) {
		            queue.add(new Point(x, y - 1));
		            spanUp = true;
		          } else if (spanUp && y > 0 && ColorDistance(image.getRGB(x, y - 1), target) >distanceOfColor) {
		            spanUp = false;
		          }
		          if (!spanDown && y < height - 1 && ColorDistance(image.getRGB(x, y + 1), target) <=distanceOfColor) {
		            queue.add(new Point(x, y + 1));
		            spanDown = true;
		          } else if (spanDown && y < height - 1 && ColorDistance(image.getRGB(x, y + 1), target) >distanceOfColor) {
		            spanDown = false;
		          }
		          x++;
		        }
		      } while ((node = queue.pollFirst()) != null);
		    }
		  }

	 /** 
	  * Fills the selected pixel and all surrounding pixels of the same color with the fill color. 
	  * @param img image on which operation is applied 
	  * @param fillColor color to be filled in 
	  * @param loc location at which to start fill 
	  * @throws IllegalArgumentException if loc is out of bounds of the image 
	  */  
	 public static void floodFill(BufferedImage img, Color fillColor, Point loc) {  
	   if (loc.x < 0 || loc.x >= img.getWidth() || loc.y < 0 || loc.y >= img.getHeight()) throw new IllegalArgumentException();  
	     
	   WritableRaster raster = img.getRaster();  
	   int[] fill =  
	       new int[] {fillColor.getRed(), fillColor.getGreen(), fillColor.getBlue(),  
	           fillColor.getAlpha()};  
	   int[] old = raster.getPixel(loc.x, loc.y, new int[4]);  
	     
	   // Checks trivial case where loc is of the fill color  
	   if (isEqualRgba(fill, old)) return;  
	     
	   floodLoop(raster, loc.x, loc.y, fill, old);  
	 }  
	   
	 // Recursively fills surrounding pixels of the old color  
	 private static void floodLoop(WritableRaster raster, int x, int y, int[] fill, int[] old) {  
	   Rectangle bounds = raster.getBounds();  
	   int[] aux = {255, 255, 255, 255};  
	     
	   // finds the left side, filling along the way  
	   int fillL = x;  
	   do {  
	     raster.setPixel(fillL, y, fill);  
	     fillL--;  
	   } while (fillL >= 0 && isEqualRgba(raster.getPixel(fillL, y, aux), old));  
	   fillL++;  
	     
	   // find the right right side, filling along the way  
	   int fillR = x;  
	   do {  
	     raster.setPixel(fillR, y, fill);  
	     fillR++;  
	   } while (fillR < bounds.width - 1 && isEqualRgba(raster.getPixel(fillR, y, aux), old));  
	   fillR--;  
	     
	   // checks if applicable up or down  
	   for (int i = fillL; i <= fillR; i++) {  
	     if (y > 0 && isEqualRgba(raster.getPixel(i, y - 1, aux), old)) floodLoop(raster, i, y - 1,  
	         fill, old);  
	     if (y < bounds.height - 1 && isEqualRgba(raster.getPixel(i, y + 1, aux), old)) floodLoop(  
	         raster, i, y + 1, fill, old);  
	   }  
	 }  
	   
	 // Returns true if RGBA arrays are equivalent, false otherwise  
	 // Could use Arrays.equals(int[], int[]), but this is probably a little faster...  
	 private static boolean isEqualRgba(int[] pix1, int[] pix2) {  
	   return pix1[0] == pix2[0] && pix1[1] == pix2[1] && pix1[2] == pix2[2] && pix1[3] == pix2[3];  
	 }  
}



