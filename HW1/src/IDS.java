/**
 * 
 * @author Casey
 *
 */

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
	
	/**
	 * 
	 * @param name
	 * @param n
	 */
	gVert(int name, int n){
		NAME = name;
		NEIGHBORS = new gVert[n];
	}
	
	/**
	 * 
	 * @param a
	 * @param b
	 */
	public void add_Undirect_Neighbor_0(gVert a, gVert b){
		a.NEIGHBORS[MLIST] = b;
		b.NEIGHBORS[MLIST] = a;
		a.MLIST++; b.MLIST++;
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
	
	/**
	 * 
	 * @param verts
	 */
	Graph(int verts){
		VERTICES = new gVert[verts];
		SIZE = verts;
	}
}

public class IDS {
	int[] parents;
	
	/**
	 * 
	 * @param G
	 * @param start
	 * @param target
	 * @return
	 */
	public static int iterative_deepening(Graph G, gVert start, gVert target){
		found = new int[G.SIZE];
		parents = new int[G.SIZE];
		int returnPath = 0;
		for(int i = 0; i<Integer.MAX_VALUE; i++){
			check = check = depth_limited(Start.NEIGHBORS[i], i, target);
		}
		return returnPath;
	}
	
	/**
	 * 
	 * @param Start
	 * @param depth
	 * @param target
	 * @return
	 */
	public static int depth_limited(gVert Start, int depth, gVert target){
		if((Start == target) && (depth==0)){
			return 0
		}
		else{
			for(int i = 0; i<Start.MLIST; i++){
				check = depth_limited(Start.NEIGHBORS[i], depth-1, target);
				if(check!=null){
					parents[depth] = Start.NAME;
					return check;
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
