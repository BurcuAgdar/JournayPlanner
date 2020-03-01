import java.util.LinkedList;

public class Node {
	int StopID;
	String stopName;
	boolean visit;
	LinkedList<Edge> edges=new LinkedList<Edge>();

	
	public Node(int stopID, String stopName) {
		super();
		StopID = stopID;
		this.stopName = stopName;
		visit=false;
	
	}
	public void display() {
		System.out.println("StopID :"+StopID+"  StopName:"+stopName);
	}

}
