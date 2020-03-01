import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Set;

import org.omg.CORBA.Current;

public class Readtxt {

	public static void main(String[] args) throws IOException {
		Graph graph = new Graph(true);

		HashMap<Integer, Node> stophash = new HashMap<Integer, Node>();
		Node node;

		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("Stop.txt"), "UTF-8"));
		String st;
		br.readLine();
		while ((st = br.readLine()) != null) {
			String[] temp = st.split(";");
			stophash.put(Integer.valueOf(temp[0]), new Node(Integer.valueOf(temp[0]), temp[1]));
		}

		HashMap<Integer, LinkedList<Object>> Linehash = new HashMap<Integer, LinkedList<Object>>();

		BufferedReader brc = new BufferedReader(new InputStreamReader(new FileInputStream("Line.txt"), "UTF-8"));
		st = "";
		brc.readLine();
		while ((st = brc.readLine()) != null) {
			LinkedList lineList = new LinkedList<Object>();
			String[] temp = st.split(";");
			for (int i = 1; i < 4; i++) {
				lineList.add(temp[i]);
			}
			Linehash.put(Integer.valueOf(temp[0]), lineList);
		}

		HashMap<String, Integer> distancehash = new HashMap<String, Integer>();

		BufferedReader dst = new BufferedReader(new InputStreamReader(new FileInputStream("Distance.txt"), "UTF-8"));
		st = "";
		dst.readLine();
		while ((st = dst.readLine()) != null) {
			String[] temp = st.split(";");
			String key = temp[0] + temp[1];
			distancehash.put(key, Integer.valueOf(temp[2]));
		}

		
		
		

		/*
		 * BufferedReader stp = new BufferedReader(new InputStreamReader(new
		 * FileInputStream("Stop.txt"), "UTF-8")); st = ""; int k=0; stp.readLine();
		 * while ((st = stp.readLine()) != null) { String[] temp = st.split(";");
		 * if(!temp[5].equals("NULL")) { String kelimeString=temp[5].replace(".", " ");
		 * String [] tempN=kelimeString.split(" "); for (int i = 0; i <= tempN.length-1;
		 * i++) { String[] ttemp=tempN[i].split(":");
		 * graph.addEdge(stophash.get(Integer.valueOf(temp[0])),
		 * stophash.get(Integer.valueOf(ttemp[0])), Integer.valueOf(ttemp[1]), -1,
		 * null,999); }
		 * 
		 * }
		 * 
		 * }
		 */
		
		
		

		
		

		BufferedReader trp = new BufferedReader(new InputStreamReader(new FileInputStream("Trip.txt"), "UTF-8"));
		st = "";
		int i = 1;
		trp.readLine();
		ArrayList<String> stopArray = new ArrayList<String>();
		String tempfirst = trp.readLine();
		String[] temp = tempfirst.split(";");
		String line = temp[0];
		String direct = temp[1];
		stopArray.add(temp[3]);
		while ((st = trp.readLine()) != null) {
			temp = st.split(";");
			if (temp[0].equals(line) & temp[1].equals(direct)) {
				stopArray.add(temp[3]);
				i++;
			} else {
				

				for (int j = 0; j < stopArray.size() - 1; j++) {
					String ebb_brc = stopArray.get(j) + stopArray.get(j + 1);
					if (distancehash.get(ebb_brc) == null) {
						distancehash.put(ebb_brc, 625);
					}

					if  (stophash.get(Integer.valueOf(stopArray.get(j))) != null
							& stophash.get(Integer.valueOf(stopArray.get(j + 1))) != null) {
						graph.addEdge(stophash.get(Integer.valueOf(stopArray.get(j))),
								stophash.get(Integer.valueOf(stopArray.get(j + 1))), distancehash.get(ebb_brc),
								Integer.valueOf(direct), Linehash.get(Integer.valueOf(line)),Integer.valueOf(line));

					
					}
				}
				stopArray=new ArrayList<String>();
				line = temp[0];
				direct = temp[1];
				i = 0;
				}
			

		}
		
		/*
		 * BufferedReader test = new BufferedReader(new InputStreamReader(new
		 * FileInputStream("test_stops.txt"), "UTF-8")); st = ""; test.readLine(); while
		 * ((st = test.readLine()) != null) { String[] temp1 = st.split(";");
		 * graph.DijkstraShortestPath(stophash.get(Integer.valueOf(temp1[0])),
		 * stophash.get(Integer.valueOf(temp1[1])),Integer.valueOf(temp1[4]));
		 * 
		 * }
		 */
		
		
		long start = System.currentTimeMillis();
		graph.DijkstraShortestPath(stophash.get(20646), stophash.get(20158),1);
		long finish = System.currentTimeMillis();
		System.out.println();
		long time=finish-start;
		System.out.println("Time : "+time+" milisecond");

	}

}
