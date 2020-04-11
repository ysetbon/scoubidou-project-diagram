package scoubidouDiagram;

import java.awt.Point;
import java.util.ArrayList;

/**
 * This class is a static class for finding the intersection points that will
 * need to be cover in a transparent circle
 * 
 * @author User
 *
 */
public class ListOfOverAndUnder {
	/**
	 * 
	 * @param lines
	 * @param contraLines
	 * @return points to erase
	 */
	public static Point[] findIntersectAllPointsToErase(nodeLine[] lines,
			nodeLine[] contraLines) {
		int numberOfPointsToErase = (lines.length * contraLines.length) / 2;
		Point[] result = new Point[numberOfPointsToErase];
		int k = 0;
		for (int i = 0; i < lines.length; i++) {
			Point[] intersectedPointsToErase = findIntersectCertainPointsToErase(
					lines[i], contraLines);
			for (int j = 0; j < intersectedPointsToErase.length; j++) {
				result[k] = intersectedPointsToErase[j];
				k++;
			}
		}
		return result;
	}

	/**
	 * 
	 * @param l
	 * @param contraLines
	 * @return points that need to be erased
	 */
	public static Point[] findIntersectCertainPointsToErase(nodeLine l,
			nodeLine[] contraLines) {
		Point[] result = new Point[contraLines.length / 2];
		int j = 0;
		Point[] intersectedPoints = findIntersectCertainPoints(l, contraLines);
		for (int i = 0; i < result.length; i++) {
			result[i] = intersectedPoints[j];
			j = j + 2;
		}
		return result;
	}

	/**
	 * 
	 * @param l
	 *            - the single line
	 * @param contraLines
	 *            - the array of lines
	 * @return - all points that intersected l and contraLines
	 */
	public static Point[] findIntersectCertainPoints(nodeLine l,
			nodeLine[] contraLines) {
		Point[] intersectedPoints = new Point[contraLines.length];
		Point[] orderedIntersectedPoints = new Point[contraLines.length];
		Point redPointOfl = new Point((int) l.nodeRed.x, (int) l.nodeRed.y);
		for (int i = 0; i < contraLines.length; i++) {
			intersectedPoints[i] = intersection(l.nodeRed.x, l.nodeRed.y,
					l.nodeGreen.x, l.nodeGreen.y, contraLines[i].nodeRed.x,
					contraLines[i].nodeRed.y, contraLines[i].nodeGreen.x,
					contraLines[i].nodeGreen.y);
		}

		orderedIntersectedPoints = sortByPoint(redPointOfl, intersectedPoints);
		return orderedIntersectedPoints;
	}

	public static Point[] sortByPoint(Point pointToCompare, Point[] listOfPoints) {
		ArrayList<Point> orderedList = new ArrayList<Point>();
		ArrayList<Point> notOrdered = new ArrayList<Point>();
		for (int i = 0; i < listOfPoints.length; i++) {
			notOrdered.add(listOfPoints[i]);
		}
		while (notOrdered.size() > 0) {
			// Find the index of the closest point (using another method)
			int nearestIndex = findNearestIndex(pointToCompare, notOrdered);

			// Remove from the unorderedList and add to the ordered one
			orderedList.add(notOrdered.remove(nearestIndex));
		}
		for (int i = 0; i < listOfPoints.length; i++) {
			listOfPoints[i] = orderedList.get(i);
		}
		return listOfPoints;
	}

	// Note this is intentialy a simple algorithm, many faster options are out
	// there
	/**
	 * 
	 * @param thisPoint
	 *            - the point to be compared
	 * @param listToSearch
	 *            - list of points to be sorted by the distance of thisPoint
	 * @return
	 */
	static int findNearestIndex(Point thisPoint, ArrayList<Point> listToSearch) {
		double nearestDistSquared = Double.POSITIVE_INFINITY;
		int nearestIndex = 0;
		for (int i = 0; i < listToSearch.size(); i++) {
			Point point2 = listToSearch.get(i);
			int distsq = (thisPoint.x - point2.x) * (thisPoint.x - point2.x)
					+ (thisPoint.y - point2.y) * (thisPoint.y - point2.y);
			if (distsq < nearestDistSquared) {
				nearestDistSquared = distsq;
				nearestIndex = i;
			}
		}
		return nearestIndex;
	}

	/**
	 * 
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @param x3
	 * @param y3
	 * @param x4
	 * @param y4
	 * @return
	 */
	public static Point intersection(float x1, float y1, float x2, float y2,
			float x3, float y3, float x4, float y4) {
		float d = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
		if (d == 0)
			return null;

		float xi = ((x3 - x4) * (x1 * y2 - y1 * x2) - (x1 - x2)
				* (x3 * y4 - y3 * x4))
				/ d;
		float yi = ((y3 - y4) * (x1 * y2 - y1 * x2) - (y1 - y2)
				* (x3 * y4 - y3 * x4))
				/ d;
		Point ret = new Point();
		ret.setLocation((int) xi, (int) yi);
		return ret;
	}

	public static void main(String[] args) {

	}
}
