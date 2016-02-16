/**
 * 
 */

import java.util.Arrays;
import java.util.Comparator;
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
		if(x.tardis > y.tardis){
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
 * A standard stack implementation for graph ojects
 * @author Casey
 */
class gStack{
	/**
	 * size are how many objects stored in the stack
	 */
	int size;
	/**
	 * The head is the primary unit being operated on in the stack object
	 */
	gNode head;
	/**
	 * The prototype stack object. Head are stacked items and size are how
	 * many items are stacked.
	 */
	public gStack(){
		head = null;
		size = 0;
	}
	/**
	 * push an existing gNode object onto an existing stack object.
	 * This is the "low level" push function.
	 * @param node
	 */
	public void push(gNode node){
		//if the head is null just add the vertex to it.
		if(this.head==null){
			this.head = node;
		}
		else{
			//otherwise point the node towards the current head and set this 
			//		as the new head.
			node.next = this.head;
			this.head = node;
		}
		size++;
	}
	/**
	 * push a vertex object onto an existing stack object
	 * @param node the node to be added to the stack, a gVert object
	 */
	public void push(gVert node){
		//just turn the vertex into a gNode and make this "easy"
		//it would likely be better form to make a different function
		//name for the "low level" function.
		gNode Node = new gNode(node, null, null);
		this.push(Node);
	}
	/**
	 * Pops the top of the stack and provides the vertex on top.
	 * @return The most recently added vertex.
	 */
	public gVert pop(){
		//find the vertex object at the top of the stack
		gVert pop = this.head.NAME;
		//set  the head of the stack to be the next item and decrement the size
		this.head = this.head.next;
		this.size--;
		return pop;
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
		a.NEIGHBORS[a.MLIST] = b;
		b.NEIGHBORS[b.MLIST] = a;
		a.MLIST++; 
		b.MLIST++;
	}
}

/**
 * 
 * @author Casey
 *
 */
class Graph {
	int SIZE, edge;
	gVert[] VERTICES;
	double[] targetDistances;
	
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
		Arrays.fill(parents, -1);
		
		//Find the distance from all nodes to the target node and set their 
		//	tardis variable to this number
		for(int i = 0; i<G.SIZE; i++){
			G.targetDistances[i] = distance(G.VERTICES[i], Target);
			G.VERTICES[i].tardis = G.targetDistances[i];
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
				if(cParent.NAME == Start.NAME || parents[cParent.NEIGHBORS[i].NAME] == -1){
					//the g(n) = distance between the parent and the current neighbor
						//minus the distance already travelled between the parent
					//the h(n) = the raw straight line distance between the current
						//neighbor and the target point.
					cParent.NEIGHBORS[i].tardis += (distance(cParent, cParent.NEIGHBORS[i]) - G.targetDistances[cParent.NAME]);
					
					//add this node to the queue and set it's parent to the
						//name of the current node/ it's parent
					queue.add(cParent.NEIGHBORS[i]);
					parents[cParent.NEIGHBORS[i].NAME] = cParent.NAME;
					
					//if this is the Target node, return the parent list
					if(cParent.NEIGHBORS[i] == Target){
						return parents;
					}
				}
			}
		}
		//if there is no path return null
		return null;
	}
	
	public static void printPath(Graph G, gVert start, gVert finish, int[] path, String[] rooms){
		int current = finish.NAME;
		gStack stacker = new gStack();
		while(current != start.NAME){
			stacker.push(G.VERTICES[current]);
			current = path[current];
		}
		stacker.push(start);
		
		while(stacker.size > 0){
			gVert n = stacker.pop();
			System.out.print(rooms[n.NAME]+ " ");
		}
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//initialize initial graph and the thing that keeps track of room numbers
		Graph G = new Graph(23);
		String[] roomNumbers = new String[23];
		
		G.VERTICES[0] = new gVert(0, 23, 43.084450, -77.679715);
		roomNumbers[0] = "3435";
		
		G.VERTICES[1] = new gVert(1, 23, 43.084394, -77.679716);
		roomNumbers[1] = "3445";
		G.VERTICES[1].add_Undirect_Neighbor_0(G.VERTICES[1], G.VERTICES[0]);
		
		G.VERTICES[2] = new gVert(2, 23, 43.084313, -77.679679);
		roomNumbers[2] = "34551";
		G.VERTICES[2].add_Undirect_Neighbor_0(G.VERTICES[1], G.VERTICES[2]);
		
		G.VERTICES[3] = new gVert(3, 23, 43.084244, -77.679726);
		roomNumbers[3] = "35151";
		G.VERTICES[3].add_Undirect_Neighbor_0(G.VERTICES[3], G.VERTICES[2]);
	
		G.VERTICES[4] = new gVert(4, 23, 43.084221, -77.679725);
		roomNumbers[4] = "35171";
		G.VERTICES[4].add_Undirect_Neighbor_0(G.VERTICES[3], G.VERTICES[4]);

		G.VERTICES[5] = new gVert(5, 23, 43.084200, -77.679726);
		roomNumbers[5] = "35191";
		G.VERTICES[5].add_Undirect_Neighbor_0(G.VERTICES[5], G.VERTICES[4]);
		
		G.VERTICES[6] = new gVert(6, 23, 43.084192, -77.680066);
		roomNumbers[6] = "3519";
		
		G.VERTICES[7] = new gVert(7, 23, 43.084213, -77.680065);
		roomNumbers[7] = "3517";
		G.VERTICES[7].add_Undirect_Neighbor_0(G.VERTICES[6], G.VERTICES[7]);
		
		G.VERTICES[8] = new gVert(8, 23, 43.084234, -77.680069);
		roomNumbers[8] = "3515";
		G.VERTICES[8].add_Undirect_Neighbor_0(G.VERTICES[8], G.VERTICES[7]);
		
		G.VERTICES[9] = new gVert(9, 23, 43.084262,  -77.680076);
		roomNumbers[9] = "3511";
		G.VERTICES[9].add_Undirect_Neighbor_0(G.VERTICES[8], G.VERTICES[9]);
		
		G.VERTICES[10] = new gVert(10, 23, 43.084288,  77.680068);
		roomNumbers[10] = "3509";
		G.VERTICES[10].add_Undirect_Neighbor_0(G.VERTICES[10], G.VERTICES[9]);
		
		G.VERTICES[11] = new gVert(11, 23, 43.084214,  -77.679837);
		roomNumbers[11] = "3610";
		G.VERTICES[11].add_Undirect_Neighbor_0(G.VERTICES[11], G.VERTICES[5]);
		G.VERTICES[11].add_Undirect_Neighbor_0(G.VERTICES[11], G.VERTICES[4]);
		G.VERTICES[11].add_Undirect_Neighbor_0(G.VERTICES[11], G.VERTICES[3]);
		
		G.VERTICES[12] = new gVert(12, 23, 43.084215, -77.679967);
		roomNumbers[12] = "3510";
		G.VERTICES[12].add_Undirect_Neighbor_0(G.VERTICES[12], G.VERTICES[6]);
		G.VERTICES[12].add_Undirect_Neighbor_0(G.VERTICES[12], G.VERTICES[7]);
		G.VERTICES[12].add_Undirect_Neighbor_0(G.VERTICES[12], G.VERTICES[8]);
		
		G.VERTICES[13] = new gVert(13, 23, 43.084275, -77.679835);
		roomNumbers[13] = "3600";
		G.VERTICES[13].add_Undirect_Neighbor_0(G.VERTICES[13], G.VERTICES[11]);
		G.VERTICES[13].add_Undirect_Neighbor_0(G.VERTICES[13], G.VERTICES[2]);
		
		G.VERTICES[14] = new gVert(14, 23, 43.084279, -77.679909);
		roomNumbers[14] = "3441";
		G.VERTICES[14].add_Undirect_Neighbor_0(G.VERTICES[14], G.VERTICES[12]);
		G.VERTICES[14].add_Undirect_Neighbor_0(G.VERTICES[14], G.VERTICES[13]);
		
		G.VERTICES[15] = new gVert(15, 23, 43.084279, -77.679978);
		roomNumbers[15] = "3500";
		G.VERTICES[15].add_Undirect_Neighbor_0(G.VERTICES[15], G.VERTICES[10]);
		G.VERTICES[15].add_Undirect_Neighbor_0(G.VERTICES[15], G.VERTICES[9]);
		G.VERTICES[15].add_Undirect_Neighbor_0(G.VERTICES[15], G.VERTICES[14]);
		G.VERTICES[15].add_Undirect_Neighbor_0(G.VERTICES[15], G.VERTICES[12]);
		
		G.VERTICES[16] = new gVert(16, 23, 43.084363, -77.679969);
		roomNumbers[16] = "3430";
		G.VERTICES[16].add_Undirect_Neighbor_0(G.VERTICES[16], G.VERTICES[15]);
		G.VERTICES[16].add_Undirect_Neighbor_0(G.VERTICES[16], G.VERTICES[14]);
	
		G.VERTICES[17] = new gVert(17, 23, 43.084356, -77.679883);
		roomNumbers[17] = "3455";
		G.VERTICES[17].add_Undirect_Neighbor_0(G.VERTICES[17], G.VERTICES[13]);
		G.VERTICES[17].add_Undirect_Neighbor_0(G.VERTICES[16], G.VERTICES[17]);
	
		G.VERTICES[18] = new gVert(18, 23, 43.084451, -77.679985);
		roomNumbers[18] = "RND";
		G.VERTICES[18].add_Undirect_Neighbor_0(G.VERTICES[16], G.VERTICES[18]);
		
		G.VERTICES[19] = new gVert(19, 23, 43.084452,  -77.679899);
		roomNumbers[19] = "Computational Studies";
		G.VERTICES[19].add_Undirect_Neighbor_0(G.VERTICES[19], G.VERTICES[18]);
		
		G.VERTICES[20] = new gVert(20, 23, 43.084456, -77.679822);
		roomNumbers[20] = "Honors";
		G.VERTICES[20].add_Undirect_Neighbor_0(G.VERTICES[19], G.VERTICES[20]);
		G.VERTICES[20].add_Undirect_Neighbor_0(G.VERTICES[20], G.VERTICES[0]);
		
		G.VERTICES[21] = new gVert(21, 23, 43.084398, -77.679824);
		roomNumbers[21] = "Xerox";
		G.VERTICES[21].add_Undirect_Neighbor_0(G.VERTICES[21], G.VERTICES[20]);
		G.VERTICES[21].add_Undirect_Neighbor_0(G.VERTICES[21], G.VERTICES[0]);
		G.VERTICES[21].add_Undirect_Neighbor_0(G.VERTICES[21], G.VERTICES[1]);
	
		G.VERTICES[22] = new gVert(22, 23, 43.084337,  -77.679828);
		roomNumbers[22] = "Adjunct Office";
		G.VERTICES[22].add_Undirect_Neighbor_0(G.VERTICES[22], G.VERTICES[21]);
		G.VERTICES[22].add_Undirect_Neighbor_0(G.VERTICES[22], G.VERTICES[17]);
		G.VERTICES[22].add_Undirect_Neighbor_0(G.VERTICES[22], G.VERTICES[1]);
		
		//Run A* and store the path contents.
		int[] h= A_Star(G, G.VERTICES[15], G.VERTICES[3]);
		printPath(G, G.VERTICES[15], G.VERTICES[3], h, roomNumbers);
	}

}
