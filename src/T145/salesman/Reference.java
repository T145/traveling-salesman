package T145.salesman;

public class Reference {

	private Reference() {}

	public static final double[][] TRICKY_TRAPEZOID = { { 2, 4 }, { 4, 4 }, { 6, 4 }, { 3, 1 }, { 5, 1 } };
	public static final double[][] SIMPLE_GRAPH = { { 1, 1 }, { 2, 3 }, { 3, 5 }, { 4, 3 }, { 5, 5 }, { 6, 1 }, { 7, 6 } };
	public static final double[][] LINE = { { 1, 1 }, { 2, 2 }, { 3, 3 }, { 4, 4 }, { 5, 5 }, { 6, 6 }, { 7, 7 } };
	public static final double[][] FLAT_LINE = { { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 }, { 6, 1 }, { 7, 1 }, { 8, 1 } };
	public static final double[][] SQUARE = { { 1, 1 }, { 5, 5 }, { 1, 5 }, { 5, 1 } };
	public static final double[][] SQUARE_WITH_CENTER = { { 1, 1 }, { 5, 5 }, { 1, 5 }, { 5, 1 }, { 3, 3 } };
}