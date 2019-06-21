package scoubidou;


import javax.swing.event.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Graphics;




import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import scoubidou2.listNodePoints;
import scoubidouDiagram.nodeLine;
import scoubidouDiagram.nodePoint;

/** a ,b ,length, firstPoint and secondPoint are the only variables that you can change! **/
class MyCanvas extends JComponent {

	// x, a can be changeable 
	public static int a=1 ;
	// y, b can be changeable
	public static int b=1;
	//spaces in-between 2 lines 
	public static int yOfStitch=250;
	public static int length = 20;
	public static int x1 = 10;
	public static int y1 = yOfStitch;
	public static int width = 2 * length * a;
	public static int hight = 2 * length * b;
	//choose you first point
	public static int firstNodePoint =5;
	//choose your second point. make sure that one point is odd and the second is even!!
	public static int secondNodePoint =0;
	int lineStart=b;
	int lineStartOp=a;
	JPanel mouse = new JPanel();
	public int firstPozMouseX;
	public int firstPozMouseY;
	public int secondPozMouseX;
	public int secondPozMouseY;

	public void changePoints (int firstPozMouseX,int firstPozMouseY,
			int secondPozMouseX,int secondPozMouseY,nodePoint[] allPoints){
		int length=allPoints.length;
		Point firstPoint = new Point(firstPozMouseX,firstPozMouseY);
		Point secondPoint = new Point(secondPozMouseX,secondPozMouseY);
		//default position, always works in any size a and b we want 
		nodePoint selectedFirstPoint = allPoints[0];
		nodePoint selectedSecondPoint =allPoints[5];	
		int k = 4 * a + 4 * b;
		int min=50000;
		int indexSelectedFirstPoint=0;
		int indexSelectedSecondPoint=5;
		//finding closest nodePoints for the 2 clicks 
		for(int i=0; i<length;i++){
			Point p= new Point(allPoints[i].x,allPoints[i].y);
			if(min >= p.distance(firstPoint)){
				min=(int) p.distance(firstPoint);
				indexSelectedFirstPoint=i;
			}
		}
		min = 50000;
		for(int i=0; i<length;i++){
			Point p= new Point(allPoints[i].x,allPoints[i].y);
			if(min >= p.distance(secondPoint)){
				min = (int) p.distance(secondPoint);
				indexSelectedSecondPoint=i;
			}
		}
		firstNodePoint=indexSelectedFirstPoint;
		secondNodePoint=indexSelectedSecondPoint;
		//setting back to default 
		if(Math.abs(indexSelectedFirstPoint-indexSelectedSecondPoint)<=1){
			firstNodePoint = 0;
			secondNodePoint = 5;
		}
		//if both of them are odd or even set to default
		if(Math.abs(indexSelectedFirstPoint-indexSelectedSecondPoint)%2==0){
			firstNodePoint = 0;
			secondNodePoint = 5;
		}
		//if the points are bigger than k
		if(firstNodePoint>k || secondNodePoint>k){
			firstNodePoint = 0;
			secondNodePoint = 5;
		}

	}

	public void paint(Graphics g) {
		lineStart=b;
		lineStartOp=a;
		g.drawRect(x1, y1, 2 * length * a, 2 * length * b);
		int k = 4 * a + 4 * b;
		nodePoint[] allPoints = new nodePoint[k];
		allPoints = listNodePoints.listOfNodePoints();
	/*	for (int i = 0; i < k; i++) {
			Color clr = allPoints[i].color;
			g.setColor(clr);
			g.drawLine(allPoints[i].x, allPoints[i].y, allPoints[i].x + 10,
					allPoints[i].y + 10);
			String number=String.valueOf(i);
			g.drawString(number, allPoints[i].x+1, allPoints[i].y+1);
		}*/

		//adding information of location of 2 mouse clicks
		mouse.addMouseListener(new MouseAdapter() {// empty implementation of all
			// MouseListener`s methods
			@Override //I override only one method for presentation
			public void mousePressed(MouseEvent e) {
				firstPozMouseX = e.getX();
				firstPozMouseY = e.getY();
			}
		});
		mouse.addMouseListener(new MouseAdapter() {// empty implementation of all
			// MouseListener`s methods
			@Override //I override only one method for presentation
			public void mousePressed(MouseEvent e) {
				secondPozMouseX = e.getX();
				secondPozMouseY = e.getY();
			}
		});
		mouse.repaint();
		//changing the 2 chosen nodePoints if we clicked well
		if(firstPozMouseY>=yOfStitch && secondPozMouseY>=yOfStitch){
			changePoints(firstPozMouseX,firstPozMouseY,secondPozMouseX,secondPozMouseY,allPoints);
		}


		//if its already at the edge that has the minimum of in-between points		
		if((listNodePoints.pointsInBetween(allPoints[firstNodePoint], allPoints[secondNodePoint])<2*a)){
			lineStartOp=listNodePoints.pointsInBetween(allPoints[firstNodePoint], allPoints[secondNodePoint])/2;
			lineStart=(k-4*lineStartOp)/4;
		}
		if(listNodePoints.pointsInBetween(allPoints[secondNodePoint], allPoints[firstNodePoint])<2*a)
		{
			lineStartOp=listNodePoints.pointsInBetween(allPoints[secondNodePoint], allPoints[firstNodePoint])/2;
			lineStart=(k-4*lineStartOp)/4;
		}			
		if((listNodePoints.pointsInBetween(allPoints[firstNodePoint], allPoints[secondNodePoint])<2*b)){
			lineStartOp=listNodePoints.pointsInBetween(allPoints[firstNodePoint], allPoints[secondNodePoint])/2;
			lineStart=(k-4*lineStartOp)/4;
		}
		if(listNodePoints.pointsInBetween(allPoints[secondNodePoint], allPoints[firstNodePoint])<2*b)
		{
			lineStartOp=listNodePoints.pointsInBetween(allPoints[secondNodePoint], allPoints[firstNodePoint])/2;
			lineStart=(k-4*lineStartOp)/4;
		}
		//			System.out.println("lineStart="+lineStart+" lineStartOp="+lineStartOp);


		//drawing the actual strings 
		try {
			nodeLine l = new nodeLine(allPoints[firstNodePoint],
					allPoints[secondNodePoint]);
			nodeLine[] paralel = new nodeLine[lineStart];
			paralel = stitchAlgo.paralelReturnOneOption(l, lineStart);
			Color clr = allPoints[0].color;
			g.setColor(clr);
			for (int i = 0; i < 2 * lineStart; i++) {
				g.drawLine(paralel[i].nodeRed.x, paralel[i].nodeRed.y,
						paralel[i].nodeGreen.x, paralel[i].nodeGreen.y);
			}

			Color firstClr = allPoints[1].color;
			g.setColor(firstClr);
			g.drawLine(l.nodeRed.x, l.nodeRed.y, l.nodeGreen.x, l.nodeGreen.y);

			nodeLine[] horizo = null;
			horizo = stitchAlgo.paralelReturnOneOptionOposite(l, lineStartOp);
			g.setColor(clr);
			for (int i = 0; i < 2 * lineStartOp; i++) {
				g.drawLine(horizo[i].nodeRed.x, horizo[i].nodeRed.y,
						horizo[i].nodeGreen.x, horizo[i].nodeGreen.y);
			}

		} catch (NullPointerException c) {
			nodeLine l = new nodeLine(allPoints[firstNodePoint],
					allPoints[secondNodePoint]);
			nodeLine[] paralel = null;
			paralel = stitchAlgo.paralelReturnOneOption(l, lineStartOp);
			Color clr = allPoints[0].color;
			g.setColor(clr);
			for (int i = 0; i < 2 * lineStartOp; i++) {
				g.drawLine(paralel[i].nodeRed.x, paralel[i].nodeRed.y,
						paralel[i].nodeGreen.x, paralel[i].nodeGreen.y);
			}

			Color firstClr = allPoints[1].color;
			g.setColor(firstClr);
			g.drawLine(l.nodeRed.x, l.nodeRed.y, l.nodeGreen.x, l.nodeGreen.y);

			nodeLine[] horizo = null;
			horizo = stitchAlgo.paralelReturnOneOptionOposite(l, lineStart);
			g.setColor(clr);
			for (int i = 0; i < 2 * lineStart; i++) {
				g.drawLine(horizo[i].nodeRed.x, horizo[i].nodeRed.y,
						horizo[i].nodeGreen.x, horizo[i].nodeGreen.y);
			}

		}

	}

}


public class drawStitch {

	private static  int FPS_MIN = 1;
	private static  int FPS_MAX = 100;
	private static  int FPS_INIT = 1;
	public  JSlider sliderA;
	public  JLabel lableA;
	public  JSlider sliderB;
	public  JLabel lableB;
	JFrame window;
	  public JTextField numberForA;
    public JTextField numberForB;
    public  JButton enterA;
    public  JButton enterB;
	public drawStitch(final JFrame window) {
		
		super();
		this.window = window;
		window.getContentPane().add(new MyCanvas());
		window.setVisible(true);
	

		sliderA = new JSlider(JSlider.HORIZONTAL,FPS_MIN,FPS_MAX,FPS_INIT);
		window.add(sliderA);
		sliderA.setMajorTickSpacing(5);
		sliderA.setPaintTicks(true);
		sliderA.setVisible(true);
		sliderA.setBounds(300, 100, 300, 50);	

		lableA = new JLabel ("current value of a is: 1");
		window.add(lableA);
	   lableA.setVisible(true);	
		lableA.setBounds(300, 150, 200,10);							

		sliderB = new JSlider(JSlider.HORIZONTAL,FPS_MIN,FPS_MAX,FPS_INIT);
		window.add(sliderB);
		sliderB.setMajorTickSpacing(5);
		sliderB.setPaintTicks(true);
		sliderB.setSize(300, 100);
		sliderB.setLocation(300, 200);
		
		lableB = new JLabel ("current value of b is: 1");	
		window.add(lableB);
		
		//lableB.setVisible(true);
		//lableB.setLocation(300, 100);
		//lableB.setSize(200,10);
			

		event evt = new event();
		sliderA.addChangeListener(evt);
		sliderB.addChangeListener(evt);
		
		
		JPanel panelFirst = new JPanel();
		//window.add(panelFirst);		
		numberForA = new JTextField(5);
		JLabel firstPointLable= new JLabel("first point:");
		panelFirst.add(firstPointLable);
		panelFirst.add(numberForA);
		numberForA.setVisible(true);
	
		JPanel panelSecond = new JPanel();
	//	window.add(panelSecond);
		numberForB = new JTextField(5);
		JLabel secondPointLable= new JLabel("second point:");
		panelSecond.add(secondPointLable);
		panelSecond.add(numberForB);
		numberForB.setVisible(true);
		
		JPanel panelboth = new JPanel();
		window.add(panelboth);
		panelboth.add(panelFirst);
		panelboth.add(panelSecond);
		
		numberForA.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if(keyCode == KeyEvent.VK_ENTER){
					int newA=Integer.parseInt(numberForA.getText());
					MyCanvas.firstNodePoint=newA;
					//set to default 
					if((newA+MyCanvas.secondNodePoint)%2==0){
						MyCanvas.firstNodePoint=5;
					}
					System.out.println("bkabka");
					window.repaint();
				}				
			}
		});
		
numberForB.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if(keyCode == KeyEvent.VK_ENTER){
					int newB=Integer.parseInt(numberForB.getText());
					MyCanvas.secondNodePoint=newB;
					//set to default 
					if((newB+MyCanvas.firstNodePoint)%2==0){
						MyCanvas.secondNodePoint=0;
					}
					System.out.println("bkabka");
					window.repaint();
				}				
			}
		});
		
		
		
		
		
	}
	public static void main(String[] args) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setBounds(0, 0, 1000, 700);
		drawStitch ds = new drawStitch(window);




	}
	public class event implements ChangeListener{
		public void stateChanged(ChangeEvent e){
			int value = sliderA.getValue();
			if(value==0){
				value=1;
			}
			lableA.setText("current value: "+value);
			if(value != MyCanvas.a){
				MyCanvas.a=value;
			}


			int valueB = sliderB.getValue();
			if(valueB==0){
				valueB=1;
			}
			lableB.setText("current value: "+valueB);
			if(valueB!=MyCanvas.b){
				MyCanvas.b=valueB;
			}
			MyCanvas.firstNodePoint =5;	
			MyCanvas.secondNodePoint =0;
			window.repaint();	

		}
	}





}




