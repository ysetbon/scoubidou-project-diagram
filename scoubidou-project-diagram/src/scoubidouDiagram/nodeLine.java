package scoubidouDiagram;

import java.awt.Color;
import java.awt.geom.Line2D;

public class nodeLine {
	 nodePoint nodeRed;
	 nodePoint nodeGreen;
	 Color clr;
	nodeLine(nodePoint red, nodePoint green, Color clr){
		this.nodeRed=red;
		this.nodeGreen=green;
		this.clr=clr;
	}
	nodeLine() {
		// TODO Auto-generated constructor stub
	}
	public static boolean isIntersect (nodeLine line1,nodeLine line2){
		
		if(Line2D.linesIntersect(line1.nodeRed.x,line1.nodeRed.y,line1.nodeGreen.x
				,line1.nodeGreen.y, line2.nodeRed.x,line2.nodeRed.y,
				line2.nodeGreen.x, line2.nodeGreen.y)== true)
			return true;
		else{
			return false;
		}
	}
	//checks if constractor is really red and really green
	public boolean isRealLine(){
		if(nodeRed.color!=Color.RED){
			return false;
		}
		if(nodeGreen.color!=Color.GREEN){
			return false;
		}
		return true;	
		
	}
	

/**
public static void drawLine(Graphics g){
	Graphics2D g2d = (Graphics2D) g;
	g2d.drawLine(nodeRed.getPozX(), nodeRed.getPozY(), nodeGreen.getPozX(),nodeGreen.getPozY());	
	}
	**/
public static void main(String[]args){
	
}
}
