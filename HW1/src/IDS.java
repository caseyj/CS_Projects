import java.util.Arrays;

/**
 * 
 * @author Casey
 *
 */

/**
 * A doubly linked list implementation for graph objects
 * @author Casey
 */

public class IDS {
	static int[] parents;
	
	/**
	 * 
	 * @param G
	 * @param start
	 * @param target
	 * @return
	 */
	public static String iterative_deepening(Graph G, gVert start, gVert target){
		parents = new int[G.SIZE];
		
		for(int i = 0; i<Integer.MAX_VALUE; i++){
			Arrays.fill( parents, -1);
			parents[start.NAME] = start.NAME;
			String returner = depth_limited(start, i, target);
			if(returner!=null){
				return (start.NAME + " " + returner);
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param Start
	 * @param depth
	 * @param target
	 * @return
	 */
	public static String depth_limited(gVert Start, int depth, gVert target){
		if((Start == target) && (depth==0)){
			return ("");
		}
		else{
			for(int i = 0; i<Start.MLIST; i++){
				if(parents[Start.NEIGHBORS[i].NAME]== -1){
					parents[Start.NEIGHBORS[i].NAME] = Start.NAME;		
					String check = depth_limited(Start.NEIGHBORS[i], depth-1, target);
					if(check != null){
						return (Start.NEIGHBORS[i].NAME + " " + check);
					}
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
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
		
		String h = iterative_deepening(G, G.VERTICES[10], G.VERTICES[7]);
		System.out.println(h);
	}

}
