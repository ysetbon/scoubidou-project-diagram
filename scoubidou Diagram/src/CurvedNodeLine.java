
/**
 * This is a class to find nodeLines that suppose to to be strait, but because
 * the are at the edge they will need to ba a buzie quad curve.
 * 
 * @author User
 *
 */
public class CurvedNodeLine {
	int counterOfCurvedLines;

	CurvedNodeLine(nodeLine[] nodeLines) {
		counterOfCurvedLines = 0;
	}
	
	/**
	 * 
	 * @param nodeLines
	 * @return lines that are need to be curved, and counting how many are there
	 */
	nodeLine[] findCurvedNodeLines(nodeLine[] nodeLines) {
		
		for (int i = 0; i < nodeLines.length; i++) {
			if (nodeLines[i].nodeGreen.x == nodeLines[i].nodeRed.x) {
				counterOfCurvedLines++;
			}
			if (nodeLines[i].nodeGreen.y == nodeLines[i].nodeRed.y) {
				counterOfCurvedLines++;
			}
		}
		nodeLine[] curvedNodes = new nodeLine[counterOfCurvedLines];	
		for (int i = 0; i < nodeLines.length; i++) {
			if (nodeLines[i].nodeGreen.x == nodeLines[i].nodeRed.x) {
				curvedNodes[i]=nodeLines[i];
			}
			if (nodeLines[i].nodeGreen.y == nodeLines[i].nodeRed.y) {
				curvedNodes[i]=nodeLines[i];
			}
		}	
		return curvedNodes;
	}
	
	
	
}
