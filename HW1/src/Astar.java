/**
 * 
 */

import java.util.comparator;
import java.util.PriorityQueue;

/**
 * 
 * @author Casey
 *
 */
class NodeComparator implements Comparator<gVert>{
	@Override
	public int compare(gVert x, gVert y){
		if(x.tardis < y.tardis){
			return -1;
		}
		if(x.tardis > tardis){
			return 1;
		}
		return 0;
	}
}

/**
 * A doubly linked list implementation for graph objects
 * @author Casey
 */
class gNode{
	/**
	 * NAME refers to the identity of the object stored in the list
	 */
	gVert NAME;
	/**
	 * next and previous refer to the direction of doublely 
	 * linked list connections
	 */
	gNode next, previous;
	/**
	 * The singular node in a linked list implementation
	 * @param name the object being stored, in this implementation a vertex for 
	 * 		a graph
	 * @param n the next node in the list
	 * @param prev the previous node in the list
	 */
	gNode(gVert name, gNode n, gNode prev){
		NAME = name;
		next = n;
		previous = prev;
	}
}

/**
 * 
 * @author Casey
 *
 */
class gVert{

	int NAME, MLIST = 0;
	gVert[] NEIGHBORS;
	double lat, lon, tardis, soFar;
	
	/**
	 * 
	 * @param name
	 * @param n
	 */
	gVert(int name, int n, double latitude, double longitude){
		NAME = name;
		NEIGHBORS = new gVert[n];
		lat = latitude;
		lon = longitude;
		tardis = 0;
		soFar = 0;
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 */
	public void add_Undirect_Neighbor_0(gVert a, gVert b){
		a.NEIGHBORS[MLIST] = b;
		b.NEIGHBORS[MLIST] = a;
		a.MLIST++; 
		b.MLIST++;
	}
}

/**
 * 
 * @author Casey
 *
 */
public class Graph {
	int SIZE, edge;
	gVert[] VERTICES;
	double[] targetDistances
	
	/**
	 * 
	 * @param verts
	 */
	Graph(int verts){
		VERTICES = new gVert[verts];
		SIZE = verts;
		targetDistances = new double[verts];
	}
}

/**
 * @author Casey
 * Title: Astar.java
 * Language: Java 1.8
 * Description: an implementation and test case of the A* algorithm
 */
public class Astar {
	
	/**
	 * Uses the distance formula in the form of 
	 * 	distance c^2 = a^2 + b^2
	 * 	such that variable a are latitude lines
	 * 	such that variable b are longitude lines
	 * @param A the starting vertex
	 * @param B the target vertex
	 * @return the distance between the two points
	 */
	public static double distance(gVert A, gVert B){
		return Math.sqrt(Math.pow((B.lat-A.lat), 2)+ Math.pow((B.lon-A.lon), 2));
	}
	
	/**
	 * The A* implementation, from a start point Start to a target point 
	 * 	Target. 
	 * @param G The graph that includes all given points 
	 * @param Start The starting point
	 * @param Target The target point
	 * @return An array of parents of the respective path points taken
	 */
	public static int[] A_Star(Graph G, gVert Start, gVert Target){
		//initialize the parents array, this will keep track of the path 
		//	taken by A*
		int[] parents = new int[G.SIZE];
		
		//Find the distance from all nodes to the target node and set their 
		//	tardis variable to this number
		for(int i = 0; i<G.SIZE; i++){
			G.targetDistances[i] = distance(G.VERTICES, Target);
			tardis = G.targetDistances[i];
		}
		
		//initialize the comparator used for priority queue
		Comparator<gVert> comparator = new NodeComparator();
		
		//initialize the priority queue
		PriorityQueue<gVert> queue = new PriorityQueue<gVert>(G.SIZE, comparator);
		
		//current parent being operated on
		gVert cParent = Start;
		queue.add(Start);
		
		//A* running loop, will quit when queue is empty, 
			//or return the parents of the nodes after successfully finding the
			//target node
		while(!queue.isEmpty()){
			//lets make sure cParent is set
			cParent = queue.remove();
			
			//loop through cParent's neighbors
			for(int i = 0; i<cParent.MLIST; i++){
				if(cParent == Start || parent[cParent.NEIGHBORS[i].NAME] != 0){
					//the g(n) = distance between the parent and the current neighbor
						//minus the distance already travelled between the parent
					//the h(n) = the raw straight line distance between the current
						//neighbor and the target point.
					cParent.NEIGHBORS[i].tardis += (distance(cParent, cParent.NEIGHBORS[i]) - G.targetDistances[cParent.NAME]);
					
					//add this node to the queue and set it's parent to the
						//name of the current node/ it's parent
					queue.add(cParent.NEIGHBORS[i]);
					parents[i] = cParent.NAME;
					
					//if this is the Target node, return the parent list
					if(cParent.NEIGHBORS[i] == Target){
						return parents;
					}
				}
			}
		}
		return null;
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//initialize initial graph and the thing that keeps track of room numbers
		Graph G = new Graph(23);
		String[] roomNumbers = new String[23];
		
		G.VERTICES[0] = gVert(0, 23, 43.084450, -77.679715);
		roomNumbers[0] = "3435";
		
		G.VERTICES[1] = gVert(1, 23, 43.084394, -77.679716);
		roomNumbers[1] = "3445";
		G.VERTICES[1].add_Undirect_Neighbor_0(G.VERTICES[1], G.VERTICES[0]);
		
		G.VERTICES[2] = gVert(2, 23, 43.084313, -77.679679);
		roomNumbers[2] = "34551";
		G.VERTICES[2].add_Undirect_Neighbor_0(G.VERTICES[1], G.VERTICES[2]);
		
		G.VERTICES[3] = gVert(3, 23, 43.084244, -77.679726);
		roomNumbers[3] = "35151";
		G.VERTICES[3].add_Undirect_Neighbor_0(G.VERTICES[3], G.VERTICES[2]);
	
		G.VERTICES[4] = gVert(4, 23, 43.084221, -77.679725);
		roomNumbers[4] = "35171";
		G.VERTICES[4].add_Undirect_Neighbor_0(G.VERTICES[3], G.VERTICES[4]);

		G.VERTICES[5] = gVert(5, 23, 43.084200, -77.679726);
		roomNumbers[5] = "35191";
		G.VERTICES[5].add_Undirect_Neighbor_0(G.VERTICES[5], G.VERTICES[4]);
		
		G.VERTICES[6] = gVert(6, 23, 43.084192, -77.680066);
		roomNumbers[6] = "3519";
		
		G.VERTICES[7] = gVert(7, 23, 43.084213, -77.680065);
		roomNumbers[7] = "3517";
		G.VERTICES[7].add_Undirect_Neighbor_0(G.VERTICES[6], G.VERTICES[7]);
		
		G.VERTICES[8] = gVert(8, 23, 43.084234, -77.680069);
		roomNumbers[8] = "3515";
		G.VERTICES[8].add_Undirect_Neighbor_0(G.VERTICES[8], G.VERTICES[7]);
		
		G.VERTICES[9] = gVert(9, 23, 43.084262,  -77.680076);
		roomNumbers[9] = "3511";
		G.VERTICES[9].add_Undirect_Neighbor_0(G.VERTICES[8], G.VERTICES[9]);
		
		G.VERTICES[10] = gVert(10, 23, 43.084288,  77.680068);
		roomNumbers[10] = "3509";
		G.VERTICES[10].add_Undirect_Neighbor_0(G.VERTICES[10], G.VERTICES[9]);
		
		G.VERTICES[11] = gVert(11, 23, 43.084214,  -77.679837);
		roomNumbers[11] = "3610";
		G.VERTICES[11].add_Undirect_Neighbor_0(G.VERTICES[11], G.VERTICES[5]);
		G.VERTICES[11].add_Undirect_Neighbor_0(G.VERTICES[11], G.VERTICES[4]);
		G.VERTICES[11].add_Undirect_Neighbor_0(G.VERTICES[11], G.VERTICES[3]);
		
		G.VERTICES[12] = gVert(12, 23, 43.084215, -77.679967);
		roomNumbers[12] = "3510";
		G.VERTICES[12].add_Undirect_Neighbor_0(G.VERTICES[12], G.VERTICES[6]);
		G.VERTICES[12].add_Undirect_Neighbor_0(G.VERTICES[12], G.VERTICES[7]);
		G.VERTICES[12].add_Undirect_Neighbor_0(G.VERTICES[12], G.VERTICES[8]);
		
		G.VERTICES[13] = gVert(13, 23, 43.084275, -77.679835);
		roomNumbers[13] = "3600";
		G.VERTICES[13].add_Undirect_Neighbor_0(G.VERTICES[13], G.VERTICES[6]);
		G.VERTICES[13].add_Undirect_Neighbor_0(G.VERTICES[13], G.VERTICES[7]);
	}

}
