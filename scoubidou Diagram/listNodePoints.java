package scoubidou4;

import java.awt.Color;
import java.awt.Point;
import java.util.*;
/**this class is for gathering all nodePoints into a neighbor structure for the use of the algorithm.
 * it will be a static class that gives us some information of points**/
public class listNodePoints {

	/** this is the main method that will give us all the points given int's a and b**/
	public static nodePoint[] listOfNodePoints(){
		//switches is 1 when green, -1 when red
		int switches=1;

		int a=MyPrintCanvasDiagram.a;
		int b=MyPrintCanvasDiagram.b;
		int x1=MyPrintCanvasDiagram.x1;
		int y1=MyPrintCanvasDiagram.y1;
		int length=MyPrintCanvasDiagram.length;
		int width=2*length*a;
		int hight=2*length*b;
		int k = 4*a+4*b;
		nodePoint[] allPoints = new nodePoint[k];
		//building all the points need to be green and red in top x axis 
		for(int i=0;i<2*a;i++){
			if(switches==1){
				allPoints[i]= new nodePoint(x1+length/2+i*length,y1,Color.GREEN);

			}
			if(switches==-1){
				allPoints[i]= new nodePoint(x1+length/2+i*length,y1,Color.RED);
			}
			switches=switches*-1;
		}
		//right Y axis, opposite colors from the left one
		switches=1;
		for(int j=0; j<2*b;j++){
			if(switches==1){
				allPoints[2*a+j] = new nodePoint(x1+width,y1+length/2+j*length,Color.GREEN);			
			}
			if(switches==-1){
				allPoints[2*a+j] = new nodePoint(x1+width,y1+length/2+j*length,Color.RED);			
			}
			switches=switches*-1;
		}
		//because its opposite colors in the down x axis
		switches=1;
		for(int i=0;i<2*a;i++){
			if(switches==1){
				allPoints[2*a+2*b+i]= new nodePoint(x1+length/2+(2*a-1)*length-i*length,y1+hight,Color.GREEN);

			}
			if(switches==-1){
				allPoints[2*a+2*b+i]= new nodePoint(x1+length/2+(2*a-1)*length-i*length,y1+hight,Color.RED);
			}
			switches=switches*-1;
		}
		switches=1;
		//building all the points need to be green and red in left Y axis 
		for(int j=0; j<2*b;j++){
			if(switches==1){
				allPoints[4*a+2*b+j] = new nodePoint(x1,y1+length/2+(2*b-1)*length-j*length,Color.GREEN);			
			}
			if(switches==-1){
				allPoints[4*a+2*b+j] = new nodePoint(x1,y1+length/2+(2*b-1)*length-j*length,Color.RED);			
			}
			switches=switches*-1;
		}


		return allPoints;
	}
	
	/**returns the index of a point**/ 
	public static int findNodePointIndex(nodePoint p){
		int a=MyPrintCanvasDiagram.a;
		int b=MyPrintCanvasDiagram.b;
		int k = 4*a+4*b;
		int i;
		nodePoint[] listOfNodePoints = listOfNodePoints();
		for(i=0;i<k;i++){		
			if ((p.x == listOfNodePoints[i].x)&&(p.y == listOfNodePoints[i].y)&&(p.color == listOfNodePoints[i].color)){
				return i;

			}
		}
		return -1;

	}
	/**
	 * 
	 * @param first
	 * @param second
	 * @param inbtwn
	 * @return if the inbtwn is in-between first and second in clockwise order.
	 */
	public static boolean isInbetweenClockwise(nodePoint first, nodePoint second,nodePoint inbtwn){
		int a = MyPrintCanvasDiagram.a;
		int b = MyPrintCanvasDiagram.b;
		int k = 4*a+4*b;
		int checker = findNodePointIndex(first);
		while(checker != findNodePointIndex(second)){
			if(checker == findNodePointIndex(inbtwn)){
				return true;
			}
			if (checker == k-1){
				checker = 0;
			}
			if(checker != k-1){
				checker++;
			}
		}
		return false;
	}
	/** this will give us how many points are in-between 2 given points,we count clockwise. first i then j**/
	public static int pointsInBetween(nodePoint i, nodePoint j) {
		int a=MyPrintCanvasDiagram.a;
		int b=MyPrintCanvasDiagram.b;
		int k = 4*a+4*b;
		int indexI = findNodePointIndex(i);
		int indexJ = findNodePointIndex(j);	
		if (indexJ >= indexI){
			return  (indexJ - indexI-1);
		}
		else
			return (k-indexI+indexJ-1);

	}

	/**gives us an array that return the 2 neighbors of a point**/
	public static nodePoint[] closestPoints (nodePoint p){
		int a=MyPrintCanvasDiagram.a;
		int b=MyPrintCanvasDiagram.b;
		int k = 4*a+4*b;
		nodePoint[] closestOfp = new nodePoint [2];
		nodePoint[] listOfNodePoints = listOfNodePoints();
		int iNew= findNodePointIndex(p);
		if((iNew >= 1)&&(iNew!=k-1)){
			closestOfp[0]=listOfNodePoints[iNew-1];
			closestOfp[1]=listOfNodePoints[iNew+1];
		}
		//last index
		if(iNew==(k-1)){
			closestOfp[0]=listOfNodePoints[k-2];
			closestOfp[1]=listOfNodePoints[0];
		}
		//first index
		if (iNew ==0){
			closestOfp[0]=listOfNodePoints[k-1];
			closestOfp[1]=listOfNodePoints[1];
		}
		return closestOfp;
	}
	public static void sort(int[] array) {
		boolean swapped = true;
		int j = 0;
		int tmp;
		while (swapped) {
			swapped = false;
			j++;
			for (int i = 0; i < array.length - j; i++) {
				if (array[i] > array[i + 1]) {
					tmp = array[i];
					array[i] = array[i + 1];
					array[i + 1] = tmp;
					swapped = true;
				}
			}
		}
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


	/** 
	 * @param l- for example if this line is a criss then contraLines are all the cross.
	 * @param contraLines  
	 * @return array of lines for the up and down, that if combined we get a
	 * line of a stitch.
	 */
	public static nodeLine[] fractedLine(nodeLine l , nodeLine[] contraLines){
		//first checking if the red node of l is in inbetween the first contraLine or the last

		//if inbetween red and after that green is smaller distance (clockwise)
		int a = MyPrintCanvasDiagram.a;
		int b = MyPrintCanvasDiagram.b;
		int k = 4*a+4*b;
		boolean flag=false;
		double checker0plus = findNodePointIndex(l.nodeRed);
		double count0plus=0;
		double checker0minus = findNodePointIndex(l.nodeRed);
		double count0minus=0;
		
		double checkerlastplus = findNodePointIndex(l.nodeRed);
		double countlastplus=0;
		double checkerlastminus = findNodePointIndex(l.nodeRed);
		double countlastminus=0;
			//checking what is the string that is closest to l.red
		while(flag==false){
		
				
			if ((checker0plus == k-1)&&(flag==false)){
				checker0plus = 0;
				count0plus++;
			}
			if((checker0plus != k-1)&&(flag==false)){
				checker0plus++;
				count0plus++;
			}
			if((checker0plus == findNodePointIndex(contraLines[0].nodeRed))
					||(checker0plus == findNodePointIndex(contraLines[0].nodeGreen))){
				flag=true;
			}
		}
		
		//
		
		
		flag=false;
		while(flag==false){
			
				
			if ((checker0minus == 0)&&(flag==false)){
				checker0minus = k-1;
				count0minus++;
			}
			if((checker0minus != 0)&&(flag==false)){
				checker0minus--;
				count0minus++;
			}
			if((checker0minus == findNodePointIndex(contraLines[0].nodeRed))
					||(checker0minus == findNodePointIndex(contraLines[0].nodeGreen))){
				flag=true;
			}
		}
			///
			
			flag=false;
			
			while(flag==false){
				
				if ((checkerlastplus == k-1)&&(flag==false)){
					checkerlastplus = 0;
					countlastplus++;
				}
				if((checkerlastplus != k-1)&&(flag==false)){
					checkerlastplus++;
					countlastplus++;
				}
				if((checkerlastplus == findNodePointIndex(contraLines[contraLines.length-1].nodeRed))
						||(checkerlastplus == findNodePointIndex(contraLines[contraLines.length-1].nodeGreen))){
					flag=true;
				}
			
			}
			
			///
			
			flag=false;
			while(flag==false){
				
					
				if ((checkerlastminus == 0)&&(flag==false)){
					checkerlastminus = k-1;
					countlastminus++;
				}
				if((checkerlastminus != 0)&&(flag==false)){
					checkerlastminus --;
					countlastminus ++;
				}
			
				if((checkerlastminus == findNodePointIndex(contraLines[contraLines.length-1].nodeRed))
						||(checkerlastminus == findNodePointIndex(contraLines[contraLines.length-1].nodeGreen))){
					flag=true;
				}
		
			
		}
		double[] array={count0minus,count0plus,countlastminus,countlastplus};
		 Arrays.sort(array);
		 if(array[0]==count0minus||array[0]==count0plus){
				return fractedLineHelper(l, contraLines,true);
				
		 }
		if(array[0]==countlastminus||array[0]==countlastplus){
				return fractedLineHelper(l, contraLines,false);
		 }
		return null;
		}
		
	
	
			
		
	
	
		


	
	/**
	 * 
	 * @param l
	 * @param contraLines0 -return true if the red nodePoint of l is inbetween the first place in the countaLine.
	 * @param redLGreen - returns true if the coutraLine that is "closing" the red nodePoint of l is that so we have a 
	 * red nodePoint (of the contraLine) then the red point of L and then a green nodePoint (of the contraLine).in clockwise.
	 * @param greenLRed - the opposite of redLgreen
	 * @param iscontraLine - "criss" lines if l is a "cross" and the opposite. 
	 * @return
	 */
	public static nodeLine[] fractedLineHelper(nodeLine l , nodeLine[] contraLines,boolean iscontraLine0){
		Color clr = Color.RED;
		nodeLine[] ret = new nodeLine[contraLines.length];
		nodePoint first = l.nodeRed;
		Point first_point =new Point ((int)l.nodeRed.x,(int)l.nodeRed.y);
		Point last_point =new Point ((int)l.nodeGreen.x,(int)l.nodeGreen.y);
		if(iscontraLine0 == true){
			for(int i=0; i<contraLines.length-1;i++){
				nodePoint firstIntersectedPoint= intersection(
						l.nodeRed.x, l.nodeRed.y,
						l.nodeGreen.x, l.nodeGreen.y,
						contraLines[i].nodeRed.x, contraLines[i].nodeRed.y,
						contraLines[i].nodeGreen.x, contraLines[i].nodeGreen.y,
						clr);
				nodePoint secondIntersectedPoint= intersection(
						l.nodeRed.x, l.nodeRed.y,
						l.nodeGreen.x, l.nodeGreen.y,
						contraLines[i+1].nodeRed.x, contraLines[i+1].nodeRed.y,
						contraLines[i+1].nodeGreen.x, contraLines[i+1].nodeGreen.y,
						clr);
				System.out.println("firstRed-"+l.nodeRed.x+","+l.nodeRed.y+','+
						"firstGreen-"+","+l.nodeGreen.x+","+ l.nodeGreen.y+","+
						"secondRed"+","+contraLines[i].nodeRed.x+","+ contraLines[i].nodeRed.y+","+
						"secondGreen"+","+contraLines[i].nodeGreen.x+","+contraLines[i].nodeGreen.y+"\n"+"FIRST case"+"\n"+"intersectpoint-"+firstIntersectedPoint.x+","+firstIntersectedPoint.y);
				if((firstIntersectedPoint.x==secondIntersectedPoint.x)&&(firstIntersectedPoint.y==secondIntersectedPoint.y)){
					System.out.println("they are the same point");
				}
				nodePoint middlePoint = new nodePoint((firstIntersectedPoint.x+secondIntersectedPoint.x)/2,
						((firstIntersectedPoint.y+secondIntersectedPoint.y)/2),
						clr);
				//switching to another nodePoint, it'll work better
				nodePoint middlePointOld=middlePoint;
				Point middle_point=new Point ((int)middlePointOld.x,(int)middlePointOld.y);
				middlePoint =new nodePoint( getClosestPointOnSegment(first_point, last_point,middle_point).x,
						 						getClosestPointOnSegment(first_point, last_point,middle_point).y,clr);
				ret[i]= new nodeLine(first,middlePoint);
				first=middlePoint;

			}
			ret[contraLines.length-1]= new nodeLine(first,l.nodeGreen);	
		}
		if(iscontraLine0 == false){
			int k=0;
			 first = l.nodeRed;
			for(int i=0; i<contraLines.length-1;i++){
				nodePoint firstIntersectedPoint= intersection(
						l.nodeRed.x, l.nodeRed.y,
						l.nodeGreen.x, l.nodeGreen.y,
						contraLines[contraLines.length-1-i].nodeRed.x, contraLines[contraLines.length-1-i].nodeRed.y,
						contraLines[contraLines.length-1-i].nodeGreen.x, contraLines[contraLines.length-1-i].nodeGreen.y,
						clr);
				nodePoint secondIntersectedPoint= intersection(
						l.nodeRed.x, l.nodeRed.y,
						l.nodeGreen.x, l.nodeGreen.y,
						contraLines[contraLines.length-2-i].nodeRed.x, contraLines[contraLines.length-2-i].nodeRed.y,
						contraLines[contraLines.length-2-i].nodeGreen.x, contraLines[contraLines.length-2-i].nodeGreen.y,
						clr);

				if((firstIntersectedPoint.x==secondIntersectedPoint.x)&&(firstIntersectedPoint.y==secondIntersectedPoint.y)){
					System.out.println("they are the same point");
				}
				//gives middle point between 2 intersected points
				nodePoint middlePoint = new nodePoint((Math.abs(firstIntersectedPoint.x+secondIntersectedPoint.x)/2),
						(Math.abs(firstIntersectedPoint.y+secondIntersectedPoint.y)/2),
						clr);
				//switching to another nodePoint, it'll work better
				nodePoint middlePointOld=middlePoint;
				Point middle_point=new Point ((int)middlePointOld.x,(int)middlePointOld.y);
			/*	middlePoint =new nodePoint( getClosestPointOnSegment(first_point, last_point,middle_point).x,
						 						getClosestPointOnSegment(first_point, last_point,middle_point).y,clr);
				*/
				ret[i]= new nodeLine(first,middlePoint);
				first=middlePoint;

			}
			ret[contraLines.length-1]= new nodeLine(first,l.nodeGreen);	
		}


		return ret;
	}

	/**
	 * 
	 * @param x1- first point of 1st segment
	 * @param y1- first point of 1st segment
	 * @param x2- second  point of 1st segment
	 * @param y2- second  point of 1st segment
	 * @param x3 - first point of 2nd segment
	 * @param y3 - first point of 2nd segment
	 * @param x4- second  point of 2nd segment
	 * @param y4= second  point of 2nd segment
	 * @param clr- of the point
	 * @return
	 */
	public static nodePoint intersection(
			float x1,float y1,float x2,float y2, 
			float x3, float y3, float x4,float y4
			,Color clr) {
		float d = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
		if (d == 0) return null;

		float xi = ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
		float yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;

		return new nodePoint(xi,yi,clr);
	}
	public static void main(String[]args){
		int a=MyPrintCanvasDiagram.a;
		int b=MyPrintCanvasDiagram.b;
		int k=4*a+4*b;
		nodePoint[] allPoints= new nodePoint[4*a+4*b];
		allPoints= listOfNodePoints();
		for(int i=0;i<4*a+4*b;i++)
		{
			allPoints[i].toString(allPoints[i]);
		}
		System.out.println("the neighbors of"+allPoints[k-1].toString()+"is:");
		nodePoint[] closers =closestPoints(allPoints[k-1]);
		closers[0].toString(closers[0]);
		closers[1].toString(closers[1]);
		//checking middle point

		double x1,y1,z1;
		x1=5.2;
		y1=6.0;
		z1=x1/y1;
		System.out.println(z1);

	}
}
