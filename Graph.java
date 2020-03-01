import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.Stack;


public class Graph {
	private Set<Node> nodes;
	private boolean directed;
	private Stack<Integer> lineStack = new Stack<Integer>();
	private Stack<Integer> lineStacktemp = new Stack<Integer>();
	private int i = 0;
	private boolean flag=true;

	Graph(boolean directed) {
		this.directed = directed;
		nodes = new HashSet<>();
	}

	public void addEdge(Node source, Node destination, int weight, int direction, LinkedList<Object> lines, int line) {
		nodes.add(source);
		nodes.add(destination);
		source.edges.add(new Edge(source, destination, weight, direction, lines, line));
	}

	public void printEdges() {
		for (Node node : nodes) {
			LinkedList<Edge> edges = node.edges;

			if (edges.isEmpty()) {
				System.out.println(node.StopID + " " + node.stopName + " has no edges.");
				System.out.println();
				continue;
			}
			System.out.print(node.StopID + "  " + node.stopName + " --> ");

			for (Edge edge : edges) {
				System.out
						.print(edge.finishStop.StopID + " " + edge.finishStop.stopName + "(" + edge.Distance + ") - ");
				if (edge.lines != null) {
					System.out.print(edge.lines.get(0) + " " + edge.lines.get(1));
					System.out.print(" Direction :" + edge.Direction + " ");
				} else {
					System.out.print(" Line : Walk ");
				}

			}
			System.out.println();
		}
	}

	public boolean hasEdge(Node source, Node destination) {
		LinkedList<Edge> edges = source.edges;
		for (Edge edge : edges) {
			if (edge.finishStop == destination) {
				return true;
			}
		}
		return false;
	}

	public void resetNodesVisited() {
		for (Node node : nodes) {
			node.visit = false;
		}
	}

	public void DijkstraShortestPath(Node start, Node end,Integer vehicleType) {

		HashMap<Node, Node> ChildParent = new HashMap<>();
		ChildParent.put(start, null);
		HashMap<Node, Integer> NodeDistance = new HashMap<>();
		HashMap<Node, LinkedList<Integer>> lines =new HashMap<>();
        LinkedList<Integer> linelinkedlist=new LinkedList<Integer>();
	
		for (Node node : nodes) {
			if (node == start)
				NodeDistance.put(start, 0);
			else
				NodeDistance.put(node, Integer.MAX_VALUE);
		}
		LinkedList<Integer> ebb_power = new LinkedList<Integer>();
		for (Edge edge : start.edges) {
			NodeDistance.put(edge.finishStop, edge.Distance);
			ChildParent.put(edge.finishStop, start);
			
		}

		start.visit = true;

		while (true) {
			Node currentNode = closestNode(NodeDistance);

			if (currentNode == null) {
				System.out.println("There isn't a path between " + start.stopName + " and " + end.stopName);
				return;
			}

			if (currentNode == end) {
				if (ChildParent.get(currentNode)==start) {
					for (Edge e :ChildParent.get(currentNode).edges) {
						 if (e.finishStop==currentNode ) {
							  linelinkedlist.add(e.line);
						
							 
							  }
					}
					  lines.put(currentNode, linelinkedlist);
				}
				
				
				
				else {
					 linelinkedlist=new LinkedList<Integer>();
						
					  for (Edge e :ChildParent.get(currentNode).edges) { 	
				
						 int size=lines.get(ChildParent.get(currentNode)).size();

						for (int i = 0; i <size; i++) {
							 if ( e.finishStop==currentNode &&lines.get(ChildParent.get(currentNode)).get(i)==e.line) {
							
								  linelinkedlist.add(e.line); }
							  
							  }	
						}
					
					  lines.put(currentNode, linelinkedlist);
					
				}
			
				
				
				
				System.out.println("Orijin Stop : "+start.StopID+"-"+start.stopName);
				System.out.println("Destination Stop : "+end.StopID+"-"+end.stopName);
				
				Node child = end;

				String path = end.stopName;
				Node parent = ChildParent.get(child);

				while (true) {

					parent = ChildParent.get(child);

					
					if (parent == null) {

						break;
					}

					path = parent.stopName + "-" + path;
					child = parent;

				}
				System.out.println("Stops : "+path);
				System.out.println("The path costs: " + NodeDistance.get(end));
				System.out.println("line : "+lines.get(end));
			    Node tempNode=ChildParent.get(end);
				for (int i = 0; i <lines.get(end).size(); i++) {
					
					for (int j = 0; j <tempNode.edges.size(); j++) {
						
						if(tempNode.edges.get(j).line==lines.get(end).get(i)) {
							if (Integer.valueOf((String) tempNode.edges.get(j).lines.get(2))==vehicleType) {
								System.out.println(tempNode.edges.get(j).lines.get(0)+"-"+tempNode.edges.get(j).lines.get(1));
							}
							else {
								System.out.println("Type "+vehicleType+" ile ulaþým yok.");
							}
							
						}
						
						
					}
				}
				
				
				return;
			}
			currentNode.visit = true;
			if (ChildParent.get(currentNode)==start) {
				for (Edge e :ChildParent.get(currentNode).edges) {
					 if (e.finishStop==currentNode ) {
						  linelinkedlist.add(e.line);
						
						 
						  }
				}

				  lines.put(currentNode, linelinkedlist);
			}
			
			else {
				  linelinkedlist=new LinkedList<Integer>();
				
			
				  for (Edge e :ChildParent.get(currentNode).edges) { 	
					
				
					 
					 int size=lines.get(ChildParent.get(currentNode)).size();
					for (int i = 0; i <size; i++) {
						 if ( e.finishStop==currentNode &&lines.get(ChildParent.get(currentNode)).get(i)==e.line) {
					
							 
							  linelinkedlist.add(e.line); }
						  
						  }	
					}
					  lines.put(currentNode, linelinkedlist);
			}
			
		
			  
			  if (lines.get(currentNode).size()==0) { 
			
				  NodeDistance.put(currentNode,Integer.MAX_VALUE);
				  currentNode.visit=false;
				  ChildParent.put(currentNode, null);
				  
				  flag=false;
			  }
			  
			 if (flag==true) {
				 for (Edge edge : currentNode.edges) {
						if (edge.finishStop.visit)
							continue;

						if (NodeDistance.get(currentNode) + edge.Distance < NodeDistance.get(edge.finishStop)) {
							NodeDistance.put(edge.finishStop, NodeDistance.get(currentNode) + edge.Distance);
							ChildParent.put(edge.finishStop, currentNode);
							
							
						}

					}
			}
			flag=true;

		}

	}

	private Node closestNode(HashMap<Node, Integer> shortestPathMap) {

		int shortestDistance = Integer.MAX_VALUE;
		Node closestNode = null;
		for (Node node : nodes) {
			if (node.visit)
				continue;

			int currentDistance = shortestPathMap.get(node);
			if (currentDistance == Integer.MAX_VALUE)
				continue;

			if (currentDistance < shortestDistance) {
				shortestDistance = currentDistance;
				closestNode = node;
			}
		}
		return closestNode;
	}
}