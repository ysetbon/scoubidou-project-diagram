
import java.awt.Point;
import java.awt.geom.GeneralPath;

public class DrawingStrings {
	
	// thickness of the lines
	public static int stringWidth = MyPrintCanvasDiagram.stringWidth;

	public static int lengthOfStringEnd = MyPrintCanvasDiagram.lengthOfStringEnd;

	// spaces in-between 2 lines
	public static int yOfStitch = 10 + lengthOfStringEnd;
	public static int length = MyPrintCanvasDiagram.length;
	// starting point of square (left up)+a little extra for the length of the
	// string
	public static int x1 = MyPrintCanvasDiagram.x1;
	public static int y1 = MyPrintCanvasDiagram.y1;



	
	private static double sqr(double x) {
		return x * x;
	}

	/**
	 * @param pt1
	 *            - first point of the line
	 * @param pt2
	 *            - second point of the line
	 * @param pt0
	 *            - the given point to be transform
	 * @return get closest point from a line, this is an checked method
	 */
	public static Point getClosestPointOnALine(Point pt1, Point pt2, Point pt0) {
		double ratio = Math
				.pow(((sqr(pt1.x - pt0.x) + sqr(pt1.y - pt0.y)) * (sqr(pt2.x - pt1.x) + sqr(pt2.y - pt1.y))
						- sqr((pt2.x - pt1.x) * (pt1.y - pt0.y) - (pt1.x - pt0.x) * (pt2.y - pt1.y))), 0.5)
				/ (sqr(pt2.x - pt1.x) + sqr(pt2.y - pt1.y));

		int xc = (int) Math.round(pt1.x + (pt2.x - pt1.x) * ratio);
		int yc = (int) Math.round(pt1.y + (pt2.y - pt1.y) * ratio);
		Point newP = new Point(xc, yc);
		return newP;
	}

	// drawing the continuation of the strait line
	public static void drawThickShapeLineWithContinuation(GeneralPath shape, float x, float y, float x2, float y2,
			double thickness, int a, int b) {
		int width = 2 * length * a;
		int hight = 2 * length * b;

		if ((x == x2) && ((x == x1) || (x == (x1 + width)))) {
			shape.lineTo(x2, y2);
		}

		else if ((y == y2) && ((y == y1) || (y == (y1 + hight)))) {
			shape.lineTo(x2, y2);
		}

		else {
			if ((x != x2) || (y != y2)) {
				Point firstPoint = new Point((int) x, (int) y);
				Point secondPoint = new Point((int) x2, (int) y2);
				Point new_secondPoint = new Point();
				if (x2 == x1) {
					Point outsidePoint = new Point((int) x2 - lengthOfStringEnd, (int) y2);
					new_secondPoint = getClosestPointOnALine(firstPoint, secondPoint, outsidePoint);
					int tempo_lengthOfStringEnd = lengthOfStringEnd;
					int i = 1;
					// adding a little more length
					while (new_secondPoint.distance(secondPoint) < lengthOfStringEnd) {
						tempo_lengthOfStringEnd = tempo_lengthOfStringEnd + i;
						outsidePoint = new Point((int) x2 - tempo_lengthOfStringEnd, (int) y2);
						new_secondPoint = getClosestPointOnALine(firstPoint, secondPoint, outsidePoint);
						i++;
					}
					shape.lineTo(new_secondPoint.x, new_secondPoint.y);

				}
				if (x2 == x1 + width) {
					Point outsidePoint = new Point((int) x2 + lengthOfStringEnd, (int) y2);
					new_secondPoint = getClosestPointOnALine(firstPoint, secondPoint, outsidePoint);
					int tempo_lengthOfStringEnd = lengthOfStringEnd;
					int i = 1;
					// adding a little more length
					while (new_secondPoint.distance(secondPoint) < lengthOfStringEnd) {
						tempo_lengthOfStringEnd = tempo_lengthOfStringEnd + i;
						outsidePoint = new Point((int) x2 + tempo_lengthOfStringEnd, (int) y2);
						new_secondPoint = getClosestPointOnALine(firstPoint, secondPoint, outsidePoint);
						i++;
					}
					shape.lineTo(new_secondPoint.x, new_secondPoint.y);

				}

				if (y2 == y1) {
					Point outsidePoint = new Point((int) x2, (int) y2 - lengthOfStringEnd);
					new_secondPoint = getClosestPointOnALine(firstPoint, secondPoint, outsidePoint);
					int tempo_lengthOfStringEnd = lengthOfStringEnd;
					int i = 1;
					// adding a little more length
					while (new_secondPoint.distance(secondPoint) < lengthOfStringEnd) {
						tempo_lengthOfStringEnd = tempo_lengthOfStringEnd + i;
						outsidePoint = new Point((int) x2, (int) y2 - tempo_lengthOfStringEnd);
						new_secondPoint = getClosestPointOnALine(firstPoint, secondPoint, outsidePoint);
						i++;
					}
					shape.lineTo(new_secondPoint.x, new_secondPoint.y);

				}
				if (y2 == y1 + hight) {
					Point outsidePoint = new Point((int) x2, (int) y2 + lengthOfStringEnd);
					new_secondPoint = getClosestPointOnALine(firstPoint, secondPoint, outsidePoint);
					int tempo_lengthOfStringEnd = lengthOfStringEnd;
					int i = 1;
					// adding a little more length
					while (new_secondPoint.distance(secondPoint) < lengthOfStringEnd) {
						tempo_lengthOfStringEnd = tempo_lengthOfStringEnd + i;
						outsidePoint = new Point((int) x2, (int) y2 + tempo_lengthOfStringEnd);
						new_secondPoint = getClosestPointOnALine(firstPoint, secondPoint, outsidePoint);
						i++;
					}
					shape.lineTo(new_secondPoint.x, new_secondPoint.y);

				}

			}
		}

	}

	// drawing the continuation of the strait line
	public static void drawThickShapeLineWithContinuationBackWeaving(GeneralPath shape, float first_x, float first_y, float second_x, float second_y,
			double thickness, int backWeaveSecondPointx,
			int backWeaveSecondPointy, int a, int b) {
		int width = 2 * length * a;
		int hight = 2 * length * b;
		
		Point new_secondPoint = new Point((int) second_x, (int) second_y);
		if ((first_x == second_x) && ((first_x == (x1+backWeaveSecondPointx)) || (first_x == (x1 + width+backWeaveSecondPointx)))) {
			shape.lineTo(second_x, second_y);
		}

		else if ((first_y == second_y) && ((first_y == (y1+backWeaveSecondPointy)) || (first_y == (y1 + hight+backWeaveSecondPointy)))) {
			shape.lineTo(second_x, second_y);
		}

		else {
			if ((first_x != second_x) || (first_y != second_y)) {
				Point firstPoint = new Point((int) first_x, (int) first_y);
				Point secondPoint = new Point((int) second_x, (int) second_y);
				if (second_x == (x1+backWeaveSecondPointx)) {
					
				/*	Point outsidePoint = new Point((int) x2 - lengthOfStringEnd, (int) y2);
					new_secondPoint = getClosestPointOnALine(firstPoint, secondPoint, outsidePoint);
					*/
					Point A = new Point(x1-lengthOfStringEnd, 0);
					Point B = new Point(x1-lengthOfStringEnd, 1);
					Point C = secondPoint;
					Point D = firstPoint;
					
					new_secondPoint = lineIntersection(A, B, C, D);
					shape.lineTo(new_secondPoint.x, new_secondPoint.y);

				}
				if (second_x == (x1+backWeaveSecondPointx) + width) {
					Point A = new Point(x1+lengthOfStringEnd+width, 0);
					Point B = new Point(x1+lengthOfStringEnd+width, 1);
					Point C = secondPoint;
					Point D = firstPoint;
					
					new_secondPoint = lineIntersection(A, B, C, D);			
					shape.lineTo(new_secondPoint.x, new_secondPoint.y);


				}

				if (second_y == (y1+backWeaveSecondPointy)) {
					Point A = new Point(0, y1-lengthOfStringEnd);
					Point B = new Point(1,y1-lengthOfStringEnd);
					Point C = secondPoint;
					Point D = firstPoint;
					
					new_secondPoint = lineIntersection(A, B, C, D);					
					shape.lineTo(new_secondPoint.x, new_secondPoint.y);

				}
				if (second_y == (y1+backWeaveSecondPointy) + hight) {
					Point A = new Point(0, (y1+lengthOfStringEnd) + hight);
					Point B = new Point(1, (y1+lengthOfStringEnd) + hight);
					Point C = secondPoint;
					Point D = firstPoint;
					new_secondPoint = lineIntersection(A, B, C, D);					
					shape.lineTo(new_secondPoint.x, new_secondPoint.y);

					}

				}

			}

		

	}
	/**
	 * Draws a line that is paralel to the neighbor of the string
	 * 
	 * @param shape
	 * @param x
	 * @param y
	 * @param x_neighbor
	 * @param y_neighbor
	 * @param x2_neighbor
	 * @param y2_neighbor
	 * @param thickness
	 * @param backWeaveSecondPointx
	 * @param backWeaveSecondPointy
	 * @param a
	 * @param b
	 */
	public static void drawThickShapeParalelToNeighbor(GeneralPath shape, nodeLine horizoParalel,float first_x, float first_y,float second_x, float second_y, float x_neighbor,
			float y_neighbor, float x2_neighbor, float y2_neighbor, double thickness, int backWeaveSecondPointx,
			int backWeaveSecondPointy, int a, int b) {
		float x = first_x;
		float y = first_y;
		Point new_secondPoint = new Point((int) second_x, (int) second_y);

		int width = 2 * length * a;
		int hight = 2 * length * b;

		if (x_neighbor == x2_neighbor) {
			shape.lineTo(new_secondPoint.x,new_secondPoint.y);
		}

		else if (y_neighbor == y2_neighbor) {
			shape.lineTo(new_secondPoint.x,new_secondPoint.y);
		}  else {
			double angle = Math.atan2(y_neighbor - y2_neighbor, x_neighbor - x2_neighbor);
			// there's a difference for new_secondPoint if its -300 or +300, so
			// we check both cases and take the nearer to outsidePoint
			float kMinus = (float) (-3000 / Math.pow(1 + Math.pow(Math.tan(angle), 2), 2));
			float endXMinus = (float) (x + kMinus);
			float endYMinus = (float) (y + kMinus * Math.tan(angle));

			float kPlus = (float) (3000 / Math.pow(1 + Math.pow(Math.tan(angle), 2), 2));
			float endXPlus = (float) (x + kPlus);
			float endYPlus = (float) (y + kPlus * Math.tan(angle));
			float endX = 0;
			float endY = 0;

			boolean isPlus;
			if (Point.distance(endXMinus, endYMinus, x_neighbor, y_neighbor) < Point.distance(endXPlus, endYPlus,
					x_neighbor, y_neighbor)) {
				endX = endXMinus;
				endY = endYMinus;
				isPlus = false;

			} else {
				endX = endXPlus;
				endY = endYPlus;
				isPlus = true;
			}

			Point firstPoint = new Point((int) Math.round(x), (int) Math.round(y));
			Point secondPoint = new Point((int) Math.round(endX), (int) Math.round(endY));
			// up
			if (horizoParalel.nodeGreen.y == y1) {
				Point A = new Point(0, y1-lengthOfStringEnd);
				Point B = new Point(1, y1-lengthOfStringEnd);
				Point C = secondPoint;
				Point D = firstPoint;
				new_secondPoint=lineIntersection(A, B, C, D);
			}
			// left
			if (horizoParalel.nodeGreen.x == x1) {
				Point A = new Point(x1 -lengthOfStringEnd, 0);
				Point B = new Point(x1 -lengthOfStringEnd, 1);
				Point C = secondPoint;
				Point D = firstPoint;
				new_secondPoint=lineIntersection(A, B, C, D);
			}
			// down
			if (horizoParalel.nodeGreen.y == y1 + hight) {
				Point A = new Point(0, y1+hight+lengthOfStringEnd);
				Point B = new Point(1, y1+hight+lengthOfStringEnd);
				Point C = secondPoint;
				Point D = firstPoint;
				new_secondPoint=lineIntersection(A, B, C, D);			
				}

			// right
			if (horizoParalel.nodeGreen.x == x1 + width) {
				Point A = new Point(x1 +width+lengthOfStringEnd, 0);
				Point B = new Point(x1 +width+lengthOfStringEnd, 1);
				Point C = secondPoint;
				Point D = firstPoint;
				new_secondPoint=lineIntersection(A, B, C, D);
			}
		
			//
			shape.lineTo(new_secondPoint.x, new_secondPoint.y);
			//

		}
	}
	/**
	 * Draws a line that is paralel to the neighbor of the string
	 * 
	 * @param shape
	 * @param x
	 * @param y
	 * @param x_neighbor
	 * @param y_neighbor
	 * @param x2_neighbor
	 * @param y2_neighbor
	 * @param thickness
	 * @param backWeaveSecondPointx
	 * @param backWeaveSecondPointy
	 * @param a
	 * @param b
	 */
	public static int[] getThickShapeParalelToNeighbor(nodeLine horizoParalel,float first_x, float first_y,float second_x, float second_y, float x_neighbor, float y_neighbor,
			float x2_neighbor, float y2_neighbor, double thickness, int backWeaveSecondPointx,
			int backWeaveSecondPointy, int a, int b) {
		float x = first_x;
		float y = first_y;
		Point new_secondPoint = new Point((int) second_x, (int) second_y);
		
		int width = 2 * length * a;
		int hight = 2 * length * b;

		if (x_neighbor == x2_neighbor) {
			
		}

		else if (y_neighbor == y2_neighbor){

		} else {
			double angle = Math.atan2(y_neighbor - y2_neighbor, x_neighbor - x2_neighbor);
			// there's a difference for new_secondPoint if its -300 or +300, so
			// we check both cases and take the nearer to outsidePoint
			float kMinus = (float) (-3000 / Math.pow(1 + Math.pow(Math.tan(angle), 2), 2));
			float endXMinus = (float) (x + kMinus);
			float endYMinus = (float) (y + kMinus * Math.tan(angle));

			float kPlus = (float) (3000 / Math.pow(1 + Math.pow(Math.tan(angle), 2), 2));
			float endXPlus = (float) (x + kPlus);
			float endYPlus = (float) (y + kPlus * Math.tan(angle));
			float endX = 0;
			float endY = 0;

			boolean isPlus;
			if (Point.distance(endXMinus, endYMinus, x_neighbor, y_neighbor) < Point.distance(endXPlus, endYPlus,
					x_neighbor, y_neighbor)) {
				endX = endXMinus;
				endY = endYMinus;
				isPlus = false;

			} else {
				endX = endXPlus;
				endY = endYPlus;
				isPlus = true;
			}

			Point firstPoint = new Point((int) Math.round(x), (int) Math.round(y));
			Point secondPoint = new Point((int) Math.round(endX), (int) Math.round(endY));
			// up
			if (horizoParalel.nodeGreen.y == y1) {
				Point A = new Point(0, y1-lengthOfStringEnd);
				Point B = new Point(1, y1-lengthOfStringEnd);
				Point C = secondPoint;
				Point D = firstPoint;
				new_secondPoint=lineIntersection(A, B, C, D);
			}
			// left
			if (horizoParalel.nodeGreen.x == x1) {
				Point A = new Point(x1 -lengthOfStringEnd, 0);
				Point B = new Point(x1 -lengthOfStringEnd, 1);
				Point C = secondPoint;
				Point D = firstPoint;
				new_secondPoint=lineIntersection(A, B, C, D);
			}
			// down
			if (horizoParalel.nodeGreen.y == y1 + hight) {
				Point A = new Point(0, y1+hight+lengthOfStringEnd);
				Point B = new Point(1, y1+hight+lengthOfStringEnd);
				Point C = secondPoint;
				Point D = firstPoint;
				new_secondPoint=lineIntersection(A, B, C, D);			
				}

			// right
			if (horizoParalel.nodeGreen.x == x1 + width) {
				Point A = new Point(x1 +width+lengthOfStringEnd, 0);
				Point B = new Point(x1 +width+lengthOfStringEnd, 1);
				Point C = secondPoint;
				Point D = firstPoint;
				new_secondPoint=lineIntersection(A, B, C, D);
			}
		}

		int[] ret = new int[2];
		ret[0] = new_secondPoint.x;
		ret[1] = new_secondPoint.y;
		return ret;

	}
	// drawing the continuation of the strait line
	public static int[] getDrawThickShapeLineWithContinuationBackWeaving(float first_x, float first_y, float second_x, float second_y,
			double thickness, int backWeaveSecondPointx,
			int backWeaveSecondPointy, int a, int b) {
		int width = 2 * length * a;
		int hight = 2 * length * b;
		Point new_secondPoint = new Point((int) second_x, (int) second_y);
		if ((first_x == second_x) && ((first_x == (x1+backWeaveSecondPointx)) || (first_x == (x1 + width+backWeaveSecondPointx)))) {
		}

		else if ((first_y == second_y) && ((first_y == (y1+backWeaveSecondPointy)) || (first_y == (y1 + hight+backWeaveSecondPointy)))) {
		}

		else {
			if ((first_x != second_x) || (first_y != second_y)) {
				Point firstPoint = new Point((int) first_x, (int) first_y);
				Point secondPoint = new Point((int) second_x, (int) second_y);
				if (second_x == (x1+backWeaveSecondPointx)) {
					
				/*	Point outsidePoint = new Point((int) x2 - lengthOfStringEnd, (int) y2);
					new_secondPoint = getClosestPointOnALine(firstPoint, secondPoint, outsidePoint);
					*/
					Point A = new Point(x1-lengthOfStringEnd, 0);
					Point B = new Point(x1-lengthOfStringEnd, 1);
					Point C = secondPoint;
					Point D = firstPoint;
					
					new_secondPoint = lineIntersection(A, B, C, D);

					/*int tempo_lengthOfStringEnd = lengthOfStringEnd;
					int i = 1;
					// adding a little more length
					while (new_secondPoint.distance(secondPoint) < lengthOfStringEnd) {
						tempo_lengthOfStringEnd = tempo_lengthOfStringEnd + i;
						outsidePoint = new Point((int) x2 - tempo_lengthOfStringEnd, (int) y2);
						new_secondPoint = getClosestPointOnALine(firstPoint, secondPoint, outsidePoint);
						i++;
					}*/

				}
				if (second_x == (x1+backWeaveSecondPointx) + width) {
					Point A = new Point(x1+lengthOfStringEnd+width, 0);
					Point B = new Point(x1+lengthOfStringEnd+width, 1);
					Point C = secondPoint;
					Point D = firstPoint;
					
					new_secondPoint = lineIntersection(A, B, C, D);			

				/*	Point outsidePoint = new Point((int) x2 + lengthOfStringEnd, (int) y2);
					new_secondPoint = getClosestPointOnALine(firstPoint, secondPoint, outsidePoint);
					int tempo_lengthOfStringEnd = lengthOfStringEnd;
					int i = 1;
					// adding a little more length
					while (new_secondPoint.distance(secondPoint) < lengthOfStringEnd) {
						tempo_lengthOfStringEnd = tempo_lengthOfStringEnd + i;
						outsidePoint = new Point((int) x2 + tempo_lengthOfStringEnd, (int) y2);
						new_secondPoint = getClosestPointOnALine(firstPoint, secondPoint, outsidePoint);
						i++;
					}*/

				}

				if (second_y == (y1+backWeaveSecondPointy)) {
					Point A = new Point(0, y1-lengthOfStringEnd);
					Point B = new Point(1,y1-lengthOfStringEnd);
					Point C = secondPoint;
					Point D = firstPoint;
					
					new_secondPoint = lineIntersection(A, B, C, D);					

				}
				if (second_y == (y1+backWeaveSecondPointy) + hight) {
					Point A = new Point(0, (y1+lengthOfStringEnd) + hight);
					Point B = new Point(1, (y1+lengthOfStringEnd) + hight);
					Point C = secondPoint;
					Point D = firstPoint;
					new_secondPoint = lineIntersection(A, B, C, D);					

					}

				}

			}
					
		int[] ret = new int[2];
		ret[0] = new_secondPoint.x;
		ret[1] = new_secondPoint.y;
		return ret;

	}

	
	// recieves the last point of the line as an array of two.
	public static int[] getDrawThickShapeLineWithContinuation(float x, float y, float x2, float y2, int a, int b) {

		int width = 2 * length * a;
		int hight = 2 * length * b;
		Point firstPoint = new Point((int) x, (int) y);
		Point secondPoint = new Point((int) x2, (int) y2);
		Point new_secondPoint = new Point();

		/// if we draw at the corners
		if ((x == x2) && ((x == x1) || (x == (x1 + width)))) {
			new_secondPoint = secondPoint;
		}

		else if ((y == y2) && ((y == y1) || (y == (y1 + hight)))) {
			new_secondPoint = secondPoint;
		}

		else {
			if (x2 == x1) {
				Point outsidePoint = new Point((int) x2 - lengthOfStringEnd, (int) y2);
				new_secondPoint = getClosestPointOnALine(firstPoint, secondPoint, outsidePoint);
				int tempo_lengthOfStringEnd = lengthOfStringEnd;
				int i = 1;
				// adding a little more length
				while (new_secondPoint.distance(secondPoint) < lengthOfStringEnd) {
					tempo_lengthOfStringEnd = tempo_lengthOfStringEnd + i;
					outsidePoint = new Point((int) x2 - tempo_lengthOfStringEnd, (int) y2);
					new_secondPoint = getClosestPointOnALine(firstPoint, secondPoint, outsidePoint);
					i++;
				}

			}
			if (x2 == x1 + width) {
				Point outsidePoint = new Point((int) x2 + lengthOfStringEnd, (int) y2);
				new_secondPoint = getClosestPointOnALine(firstPoint, secondPoint, outsidePoint);
				int tempo_lengthOfStringEnd = lengthOfStringEnd;
				int i = 1;
				// adding a little more length
				while (new_secondPoint.distance(secondPoint) < lengthOfStringEnd) {
					tempo_lengthOfStringEnd = tempo_lengthOfStringEnd + i;
					outsidePoint = new Point((int) x2 + tempo_lengthOfStringEnd, (int) y2);
					new_secondPoint = getClosestPointOnALine(firstPoint, secondPoint, outsidePoint);
					i++;
				}
			}

			if (y2 == y1) {
				Point outsidePoint = new Point((int) x2, (int) y2 - lengthOfStringEnd);
				new_secondPoint = getClosestPointOnALine(firstPoint, secondPoint, outsidePoint);
				int tempo_lengthOfStringEnd = lengthOfStringEnd;
				int i = 1;
				// adding a little more length
				while (new_secondPoint.distance(secondPoint) < lengthOfStringEnd) {
					tempo_lengthOfStringEnd = tempo_lengthOfStringEnd + i;
					outsidePoint = new Point((int) x2, (int) y2 - tempo_lengthOfStringEnd);
					new_secondPoint = getClosestPointOnALine(firstPoint, secondPoint, outsidePoint);
					i++;
				}
			}
			if (y2 == y1 + hight) {
				Point outsidePoint = new Point((int) x2, (int) y2 + lengthOfStringEnd);
				new_secondPoint = getClosestPointOnALine(firstPoint, secondPoint, outsidePoint);
				int tempo_lengthOfStringEnd = lengthOfStringEnd;
				int i = 1;
				// adding a little more length
				while (new_secondPoint.distance(secondPoint) < lengthOfStringEnd) {
					tempo_lengthOfStringEnd = tempo_lengthOfStringEnd + i;
					outsidePoint = new Point((int) x2, (int) y2 + tempo_lengthOfStringEnd);
					new_secondPoint = getClosestPointOnALine(firstPoint, secondPoint, outsidePoint);
					i++;
				}

			}
		}

		int[] ret = new int[2];
		ret[0] = new_secondPoint.x;
		ret[1] = new_secondPoint.y;
		return ret;

	}
	/**
	 * A,B are first line, C D second line
	 * @param A
	 * @param B
	 * @param C
	 * @param D
	 * @return point of intersection
	 */
	static Point lineIntersection(Point A, Point B, Point C, Point D)
	    {
	        // Line AB represented as a1x + b1y = c1
	        double a1 = B.y - A.y;
	        double b1 = A.x - B.x;
	        double c1 = a1*(A.x) + b1*(A.y);
	      
	        // Line CD represented as a2x + b2y = c2
	        double a2 = D.y - C.y;
	        double b2 = C.x - D.x;
	        double c2 = a2*(C.x)+ b2*(C.y);	      
	        double determinant = a1*b2 - a2*b1;
	      
	        if (determinant == 0)
	        {
	            // The lines are parallel. This is simplified
	            // by returning a pair of FLT_MAX
	            return  null;
	        }
	        else
	        {
	            double x = (b2*c1 - b1*c2)/determinant;
	            double y = (a1*c2 - a2*c1)/determinant;
	            Point ret = new Point();
	            ret.x = (int) Math.round(x);
	            ret.y = (int) Math.round(y);
	            return ret;
	        }
	    }


}
