/**
 * 
 */

import java.util.comparator;
import java.util.PriorityQueue;

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
		// TODO Auto-generated method stub

	}

}
