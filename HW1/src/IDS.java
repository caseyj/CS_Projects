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
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
