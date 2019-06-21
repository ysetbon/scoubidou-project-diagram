

import java.awt.Color;






import java.io.IOException;

import javax.swing.JFrame;
/** a ,b ,length, firstPoint and secondPoint are the only variables that you can change! **/


public class printStitch{

	Color crissColor= new Color(255,255, 160);
	Color crossColor=new Color(10, 203, 100);
	
	public static void main(String[] args) throws IOException {
		int a=1, b=3;
		int j=3;

		while(j<=(2*a+2*b-1)){
			for(int criss=1; criss<=((j-1)/2);criss++){
				printStitch pt = new printStitch();
				System.out.println("a- "+a+"b- "+b+"j- "+j+"criss- "+criss);
				pt.printPicture(j,criss,a,b);
			}
			j=j+2;			
		}
		
		while(j<=(4*a+4*b-3)){
			for(int criss=1; criss<=(4*a+4*b-1-j)/2;criss++){
				printStitch pt = new printStitch();
				System.out.println("a"+a+"b"+b+"j"+j+"criss"+criss);
				pt.printPicture(j,criss,a,b);
				
			}
			j=j+2;			
		}
	}
	/**
	 * 
	 * @param firstLindEndPoint- end point of 0
	 * @param crissNumberOfLines
	 * @param a
	 * @param b
	 * @param counter
	 * @throws IOException 
	 */
	public  void printPicture(int firstLindEndPoint,int crissNumberOfLines, int a, int b) throws IOException{
		JFrame localwindow = new JFrame();
		localwindow.setAutoRequestFocus(true);

		StitchWithWeaving stitch = new StitchWithWeaving();
		stitch.a=a;
		
		stitch.b=b;
		stitch.firstLineEndPoint=firstLindEndPoint;
		stitch.crissNumberOfLines=crissNumberOfLines;
		stitch.printPicture(firstLindEndPoint, crissNumberOfLines, a, b, crissColor, crossColor);
		localwindow.revalidate();
		
	}
}
	







