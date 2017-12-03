package T145.salesman4.lib;

public class Reference {

	private Reference() {}

	public static final double COLLISION_OFFSET = 0.01;

	public static final boolean DEBUG = true;

	public static final double[][] ANNOYING_TRIANGLE = { {1,5}, {3,6}, {2,2} };
	public static final double[][] SCRAMBLED_GRAPH = { { 1, 1 }, { 4, 5 }, { 9, 8 }, { 3, 2 }, { 6, 7 } };
	public static final double[][] WANKY = { { 0, 0 }, { 4, 1 }, { 5, 6 }, { 7, 10 } };
	public static final double[][] ODD_X = { { 1, 1 }, { 2, 2 }, { 3, 3 }, { 4, 4 }, { 5, 5 }, { 6, 6 }, { 7, 7 } };
	public static final double[][] EVEN_X = { { 1, 1 }, { 2, 2 }, { 3, 3 }, { 4, 4 }, { 5, 5 }, { 6, 6 }, { 7, 7 }, { 8, 8 } };
	public static final double[][] SQUARE = { { 1, 1 }, { 4, 4 }, { 1, 4 }, { 4, 1 } };
	public static final double[][] SIMPLE_GRAPH_WITH_COLLISIONS = { { 1, 1 }, { 2, 3 }, { 3, 5 }, { 4, 3 }, { 5, 5 }, { 6, 1 }, { 7, 6 } };
}