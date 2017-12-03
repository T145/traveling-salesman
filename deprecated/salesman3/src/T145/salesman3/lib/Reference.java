package T145.salesman3.lib;

public class Reference {

	private Reference() {}

	public static final boolean DEBUG = true;

	public static final int[][] WANKY = { { 0, 0 }, { 4, 1 }, { 5, 6 }, { 7, 10 } };
	public static final int[][] ODD_X = { { 1, 1 }, { 2, 2 }, { 3, 3 }, { 4, 4 }, { 5, 5 }, { 6, 6 }, { 7, 7 } };
	public static final int[][] EVEN_X = { { 1, 1 }, { 2, 2 }, { 3, 3 }, { 4, 4 }, { 5, 5 }, { 6, 6 }, { 7, 7 }, { 8, 8 } };
	public static final int[][] SQUARE = { { 1, 1 }, { 4, 4 }, { 1, 4 }, { 4, 1 } };
	public static final int[][] SIMPLE_GRAPH_WITH_COLLISIONS = { { 1, 1 }, { 2, 3 }, { 3, 5 }, { 4, 3 }, { 5, 5 }, { 6, 1 }, { 7, 6 } };
}