
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**creating the algorithm**/
public class stitchAlgoForPrintTextStitch {

	
	public static nodeLine[] paralelReturnOneOption(nodeLine l, int lineStart){
		int length=-1;
		int opLineDiretion = -1;
		nodeLine[] arrParalel = null;
		int k =4*MyPrintCanvasDiagram.b+4*MyPrintCanvasDiagram.a;
		length=2*lineStart;
		opLineDiretion=2*((k-4*lineStart)/4);
		arrParalel = new nodeLine[length];
		arrParalel[0]=l;
		nodeLine nextLine = l;		


		int switchPoz=1;
		while((listNodeTextPoints.pointsInBetween(nextLine.nodeGreen, nextLine.nodeRed)!=opLineDiretion)&&
				(listNodeTextPoints.pointsInBetween(nextLine.nodeRed, nextLine.nodeGreen)!=opLineDiretion)){
			nodePoint[] closestRed = listNodeTextPoints.closestPoints(nextLine.nodeGreen);
			//closest of the red point, they are green
			nodePoint[] closestGreen = listNodeTextPoints.closestPoints(nextLine.nodeRed);
			if(switchPoz == 1){
				nodePoint greenOfNextLine= closestGreen[0];
				nodePoint redOfNextLine = closestRed[1];
			    nextLine= new nodeLine(redOfNextLine,greenOfNextLine);
				
			}
			if(switchPoz == -1){
				nodePoint greenOfNextLine= closestGreen[1];
				nodePoint redOfNextLine = closestRed[0];
			    nextLine= new nodeLine(redOfNextLine,greenOfNextLine);
					
			}
			switchPoz = switchPoz * -1;	
		}
			return paralelReturnComplete(nextLine,lineStart);
		}

	
	/**returns the strings that in the other side**/
	public static nodeLine[] paralelReturnOneOptionOposite (nodeLine l, int lineNewStart){
		nodeLine[] theOldStrings = null;
		nodePoint newRed = null;
		nodePoint newGreen = null;
		int allOldStrings=-1;
		nodeLine newLine=null;
		int k =4*MyPrintCanvasDiagram.b+4*MyPrintCanvasDiagram.a;
		allOldStrings=2*((k-4*lineNewStart)/4);
		theOldStrings = paralelReturnOneOption(l,(k-4*lineNewStart)/4);
		nodePoint[] closestGreensOfFirstRed = listNodeTextPoints.closestPoints(theOldStrings[0].nodeRed);
		nodePoint[] closestRedsOfLastGreen = listNodeTextPoints.closestPoints(theOldStrings[allOldStrings-1].nodeGreen);
		if(listNodeTextPoints.findNodePointIndex(closestGreensOfFirstRed[0])==
				listNodeTextPoints.findNodePointIndex(theOldStrings[1].nodeGreen)){
			newGreen=closestGreensOfFirstRed[1];
		}
		if(listNodeTextPoints.findNodePointIndex(closestGreensOfFirstRed[1])==
				listNodeTextPoints.findNodePointIndex(theOldStrings[1].nodeGreen)){
			newGreen=closestGreensOfFirstRed[0];
		}
		if(listNodeTextPoints.findNodePointIndex(closestRedsOfLastGreen[0])==
				listNodeTextPoints.findNodePointIndex(theOldStrings[allOldStrings-2].nodeRed)){
			newRed=closestRedsOfLastGreen[1];
		}
		if(listNodeTextPoints.findNodePointIndex(closestRedsOfLastGreen[1])==
				listNodeTextPoints.findNodePointIndex(theOldStrings[allOldStrings-2].nodeRed)){
			newRed=closestRedsOfLastGreen[0];
		}
		newLine=new nodeLine(newRed,newGreen);
		return paralelReturnComplete(newLine,lineNewStart);
	}

	
	public static nodeLine[] paralelReturnCriss(nodeLine l,int lineStart){
		
		int lineStartOp=-1;
		nodeLine[] arrParalel = new nodeLine[lineStart];
		arrParalel[0]=l;
		int numOfLines = 2*lineStart;
		int k =4*MyPrintCanvasDiagram.b+4*MyPrintCanvasDiagram.a;
		lineStartOp=(k-4*lineStart)/4;
		if(listNodeTextPoints.pointsInBetween(l.nodeGreen, l.nodeRed)==lineStart){
			
		}
		
		
		
		return arrParalel;
	}
	/**
	 * 
	 * @param l- the given line
	 * @param lineStart- can be a or b
	 * @return assuming the the given l is at the left/right side it return arr of all lines that 
	 * comes after l! (as for now it works perfectly!!)
	 */
	public static nodeLine[] paralelReturnComplete(nodeLine l, int lineStart){
		int lineStartOp=-1;
		nodeLine[] arrParalel;
		int numOfLines = 2*lineStart;
		int k =4*MyPrintCanvasDiagram.b+4*MyPrintCanvasDiagram.a;
		lineStartOp=(k-4*lineStart)/4;
		arrParalel=paralelReturnCompleteHelper(l,"goLeft",lineStart,lineStartOp);
		if((listNodeTextPoints.pointsInBetween(arrParalel[numOfLines-1].nodeRed, 
				arrParalel[numOfLines-1].nodeGreen)==2*lineStartOp)
				||				
				(listNodeTextPoints.pointsInBetween(arrParalel[numOfLines-1].nodeGreen, 
						arrParalel[numOfLines-1].nodeRed)==2*lineStartOp))							
		{
			return arrParalel;
		}
		else{
			arrParalel=paralelReturnCompleteHelper(l,"goRight",lineStart,lineStartOp);
			return arrParalel;
		}

	}

	/**
	 * 
	 * @param l is the given line
	 * @param direction which dir we will go
	 * @param lineStart is a or b
	 * @param lineStartOp is b or a
	 * @return
	 */
	public static nodeLine[] paralelReturnCompleteHelper(nodeLine l ,String direction, int lineStart, int lineStartOp){
		nodeLine nextLine=l;
		int switchPoz=1;
		int numOfLines = 2*lineStart;
		nodeLine[] arrParalel = new nodeLine[numOfLines];
		arrParalel[0]=l;
		if(direction=="goLeft"){
			if(listNodeTextPoints.pointsInBetween(l.nodeRed, l.nodeGreen)==2*lineStartOp){
				switchPoz=1;
				nextLine=l;
				for(int i=1;i<numOfLines;i++){	
					nodePoint[] closestRed = listNodeTextPoints.closestPoints(nextLine.nodeGreen);
					//closest of the red point, they are green
					nodePoint[] closestGreen = listNodeTextPoints.closestPoints(nextLine.nodeRed);
					//if one of the red is in the same edges

					if(switchPoz==-1){
						nodePoint greenOfNextLine= closestGreen[1];
						nodePoint redOfNextLine = closestRed[0];
						arrParalel[i]= new nodeLine(redOfNextLine,greenOfNextLine);
						nextLine=arrParalel[i];
					}
					if(switchPoz==1){
						nodePoint greenOfNextLine= closestGreen[0];
						nodePoint redOfNextLine = closestRed[1];
						arrParalel[i]= new nodeLine(redOfNextLine,greenOfNextLine);
						nextLine=arrParalel[i];
					}
					switchPoz=switchPoz*-1;
				}
			}
			//the opposite situation 
			if(listNodeTextPoints.pointsInBetween(l.nodeGreen, l.nodeRed)==2*lineStartOp){
				switchPoz=-1;
				nextLine=l;
				for(int i=1;i<numOfLines;i++){	
					nodePoint[] closestRed = listNodeTextPoints.closestPoints(nextLine.nodeGreen);
					//closest of the red point, they are green
					nodePoint[] closestGreen = listNodeTextPoints.closestPoints(nextLine.nodeRed);
					//if one of the red is in the same edges
					if(switchPoz==-1){
						nodePoint greenOfNextLine= closestGreen[1];
						nodePoint redOfNextLine = closestRed[0];
						arrParalel[i]= new nodeLine(redOfNextLine,greenOfNextLine);
						nextLine=arrParalel[i];
					}
					if(switchPoz==1){
						nodePoint greenOfNextLine= closestGreen[0];
						nodePoint redOfNextLine = closestRed[1];
						arrParalel[i]= new nodeLine(redOfNextLine,greenOfNextLine);
						nextLine=arrParalel[i];
					}
					switchPoz=switchPoz*-1;
				}
			}
		}
		if(direction=="goRight"){
			if(listNodeTextPoints.pointsInBetween(l.nodeGreen, l.nodeRed)==2*lineStartOp){
				switchPoz=1;
				nextLine=l;
				for(int i=1;i<numOfLines;i++){	
					nodePoint[] closestRed = listNodeTextPoints.closestPoints(nextLine.nodeGreen);
					//closest of the red point, they are green
					nodePoint[] closestGreen = listNodeTextPoints.closestPoints(nextLine.nodeRed);
					//if one of the red is in the same edges

					if(switchPoz==-1){
						nodePoint greenOfNextLine= closestGreen[1];
						nodePoint redOfNextLine = closestRed[0];
						arrParalel[i]= new nodeLine(redOfNextLine,greenOfNextLine);
						nextLine=arrParalel[i];
					}
					if(switchPoz==1){
						nodePoint greenOfNextLine= closestGreen[0];
						nodePoint redOfNextLine = closestRed[1];
						arrParalel[i]= new nodeLine(redOfNextLine,greenOfNextLine);
						nextLine=arrParalel[i];
					}
					switchPoz=switchPoz*-1;
				}
			}
			//if its opposite 
			if(listNodeTextPoints.pointsInBetween(l.nodeRed, l.nodeGreen)==2*lineStartOp){
				switchPoz=-1;
				nextLine=l;
				for(int i=1;i<numOfLines;i++){	
					nodePoint[] closestRed = listNodeTextPoints.closestPoints(nextLine.nodeGreen);
					//closest of the red point, they are green
					nodePoint[] closestGreen = listNodeTextPoints.closestPoints(nextLine.nodeRed);
					//if one of the red is in the same edges

					if(switchPoz==-1){
						nodePoint greenOfNextLine= closestGreen[1];
						nodePoint redOfNextLine = closestRed[0];
						arrParalel[i]= new nodeLine(redOfNextLine,greenOfNextLine);
						nextLine=arrParalel[i];
					}
					if(switchPoz==1){
						nodePoint greenOfNextLine= closestGreen[0];
						nodePoint redOfNextLine = closestRed[1];
						arrParalel[i]= new nodeLine(redOfNextLine,greenOfNextLine);
						nextLine=arrParalel[i];
					}
					switchPoz=switchPoz*-1;
				}
			}
		}	
		return arrParalel;
	}
}

