import java.util.Arrays;
import java.util.HashMap;

/**
 * @author Casey
 * Title: IDS.java
 * Language: Java
 * Description: A working implementation of Iterative Deepening Search with working tests to simulate
 * 		indoor GPS of the third floor of the GCCIS building at RIT
 * Usage: > java IDS [location 1] [location 2]
 */

/**
 * A doubly linked list implementation for graph objects
 * @author Casey
 */

public class IDS {
	static int[] parents;
	
	/**
	 * The starting function to run iterative deepening search
	 * @param G The graph we are running the search on
	 * @param start The starting Vertex
	 * @param target The Target Vertex
	 * @return The path taken from Start to target vertices!
	 */
	public static String iterative_deepening(Graph G, gVert start, gVert target){
		//keeps track of lineage, such that a node cannot be added to search 
		//		more than once
		parents = new int[G.SIZE];
		
		//loop as many times as the system will allow progressively deepening 
		//	the search
		for(int i = 0; i<Integer.MAX_VALUE; i++){
			//reset the parents array every time
			Arrays.fill( parents, -1);
			//add the starting index to the parents array
			parents[start.NAME] = start.NAME;
			//initialize the recursive search!
			String returner = depth_limited(start, i, target);
			//if the return is not null then we return the response set
			if(returner!=null){
				return (start.NAME + " " + returner);
			}
		}
		//if all roads return null, then return null
		return null;
	}
	
	/**
	 * The recursive routine to run Iterative Deepening Search
	 * @param Start The starting vertex
	 * @param depth How many more times to recurse this run
	 * @param target The target vertex 
	 * @return the string representation of the path taken
	 */
	public static String depth_limited(gVert Start, int depth, gVert target){
		//IF we have reached the target depth, and we have arived at the target return a string!
		if((Start == target) && (depth==0)){
			return ("");
		}
		//Otherwise...
		else{
			//iterate across all of the neighbors of the starting node
			for(int i = 0; i<Start.MLIST; i++){
				//only look at this neighbor if they have not yet been added to
				//		the RAM stack
				if(parents[Start.NEIGHBORS[i].NAME]== -1){
					//set the current neighbor's parent in PARENTS to the 
					//		starting node's ID
					parents[Start.NEIGHBORS[i].NAME] = Start.NAME;		
					//recurse and add this neighbor to the stack as a starting point
					String check = depth_limited(Start.NEIGHBORS[i], depth-1, target);
					//if through recursion this neighbors' neighbors are the target
					//		return!
					if(check != null){
						return (Start.NEIGHBORS[i].NAME + " " + check);
					}
				}
			}
		}
		//if no neighbors are found and you are not the maximum depth, 
		//		just return null
		return null;
	}
	
	public static void main(String[] args) {
		//checks for correct IO
		if(args.length<2){
			System.out.println("Please run as follows:");
			System.out.println(">java IDS [Starting Room Name] [Target Room Name]");
			System.exit(0);
		}
		
		
		//initialize initial graph and the thing that keeps track of room numbers
		Graph G = new Graph(23);
		String[] roomNumbers = new String[23];
		
		HashMap<String, Integer> FloorPlan = new HashMap<String, Integer>();
		
		//The following lines are hardcoded to create the map of the floor
		//hardcoding begin
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
		roomNumbers[18] = "RND".toLowerCase();
		G.VERTICES[18].add_Undirect_Neighbor_0(G.VERTICES[16], G.VERTICES[18]);
		
		G.VERTICES[19] = new gVert(19, 23, 43.084452,  -77.679899);
		roomNumbers[19] = "Computational Studies".toLowerCase();
		G.VERTICES[19].add_Undirect_Neighbor_0(G.VERTICES[19], G.VERTICES[18]);
		
		G.VERTICES[20] = new gVert(20, 23, 43.084456, -77.679822);
		roomNumbers[20] = "Honors".toLowerCase();
		G.VERTICES[20].add_Undirect_Neighbor_0(G.VERTICES[19], G.VERTICES[20]);
		G.VERTICES[20].add_Undirect_Neighbor_0(G.VERTICES[20], G.VERTICES[0]);
		
		G.VERTICES[21] = new gVert(21, 23, 43.084398, -77.679824);
		roomNumbers[21] = "Xerox".toLowerCase();
		G.VERTICES[21].add_Undirect_Neighbor_0(G.VERTICES[21], G.VERTICES[20]);
		G.VERTICES[21].add_Undirect_Neighbor_0(G.VERTICES[21], G.VERTICES[0]);
		G.VERTICES[21].add_Undirect_Neighbor_0(G.VERTICES[21], G.VERTICES[1]);
	
		G.VERTICES[22] = new gVert(22, 23, 43.084337,  -77.679828);
		roomNumbers[22] = "Adjunct Office".toLowerCase();
		G.VERTICES[22].add_Undirect_Neighbor_0(G.VERTICES[22], G.VERTICES[21]);
		G.VERTICES[22].add_Undirect_Neighbor_0(G.VERTICES[22], G.VERTICES[17]);
		G.VERTICES[22].add_Undirect_Neighbor_0(G.VERTICES[22], G.VERTICES[1]);
		//hardcoded values end here
		
		//take in the names of each room and add it to the hashmap
		for(int i = 0; i<G.SIZE; i++){
			FloorPlan.put(roomNumbers[i], i);
		}
		
		//intake parameters, first arg is start, second arg is target
		String start = args[0].toLowerCase();
		String finish = args[1].toLowerCase();
		
		int st = FloorPlan.get(start);
		int fi = FloorPlan.get(finish);
		
		String h = iterative_deepening(G, G.VERTICES[st], G.VERTICES[fi]);

		String[] returner = h.split(" ", h.length());
		for(int i = 0; i < returner.length-1; i++){
			System.out.print(roomNumbers[Integer.parseInt(returner[i])] + " ");
		}
		System.out.println(" ");
		
	}

}
