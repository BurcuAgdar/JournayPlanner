import java.util.HashMap;
import java.util.LinkedList;

public class Edge {
	Node startStop;
	Node finishStop;
	int Distance;
	int Direction;
	LinkedList<Object> lines;
	int line;
	public Edge(Node startStop, Node finishStop, int distance, int direction,LinkedList<Object> lines,int line) {
		super();
		this.startStop = startStop;
		this.finishStop = finishStop;
		Distance = distance;
		Direction = direction;
		this.lines = lines;
		this.line=line;
	}
	


}
