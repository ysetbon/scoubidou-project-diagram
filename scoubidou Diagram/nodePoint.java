package scoubidou4;

import java.awt.Color;

/** information of all points of the rectangle**/
public class nodePoint {
public float x;
public float y;
public Color color;
nodePoint(float x, float y, Color color){
	this.x=x;
	this.y=y;
	this.color=color;
}
float getPozX (){
	return (x-MyPrintCanvasDiagram.x1-((MyPrintCanvasDiagram.length)/2))/(MyPrintCanvasDiagram.length);
}
float getPozY (){
	return (y-MyPrintCanvasDiagram.y1-((MyPrintCanvasDiagram.length)/2))/(MyPrintCanvasDiagram.length);
}

public void toString (nodePoint p){
}


	

public static void main(String[]args){
	int a=MyPrintCanvasDiagram.a;
	int b=MyPrintCanvasDiagram.b;
	int k = 4*a+4*b;
	nodePoint[] allPoints= new nodePoint[k];
	allPoints= listNodePoints.listOfNodePoints();
	for(int i=0;i<k;i++)
	{
	}
	int [] arr = new int [4];
	arr[0]=1;
	arr[1]=6;
	arr[2]=2;
	arr[3]=100;
	
	for(int i=0 ;i<4 ; i++){
		System.out.println(arr[i]);
	}
}
}
