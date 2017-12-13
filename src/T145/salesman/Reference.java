package T145.salesman;

import java.util.Random;

public class Reference {

	private Reference() {}

	public static final double[][] TRICKY_TRAPEZOID = { { 2, 4 }, { 4, 4 }, { 6, 4 }, { 3, 1 }, { 5, 1 } };
	public static final double[][] SIMPLE_GRAPH = { { 1, 1 }, { 2, 3 }, { 3, 5 }, { 4, 3 }, { 5, 5 }, { 6, 1 }, { 7, 6 } };
	public static final double[][] LINE = { { 1, 1 }, { 2, 2 }, { 3, 3 }, { 4, 4 }, { 5, 5 }, { 6, 6 }, { 7, 7 } };
	public static final double[][] FLAT_LINE = { { 1, 1 }, { 2, 1 }, { 3, 1 }, { 4, 1 }, { 5, 1 }, { 6, 1 }, { 7, 1 }, { 8, 1 } };
	public static final double[][] SQUARE = { { 1, 1 }, { 5, 5 }, { 1, 5 }, { 5, 1 } };
	public static final double[][] SQUARE_WITH_CENTER = { { 1, 1 }, { 5, 5 }, { 1, 5 }, { 5, 1 }, { 3, 3 } };
	public static final double[][] OSCAR_DILEMA = { { 0, 0 }, { 1, 1000 }, { 2, 0 }, { 3, 1000 } };
	public static final double[][] RHOMBUS = { { 2, 2 }, { 3, 5 }, { 4, 3 }, { 5, 6 } };

	public static final double[][] getRandomIntegerGraph(int maxSize) {
		double[][] graph = new double[maxSize][maxSize];
		Random rand = new Random();

		for (int t = 0; t < maxSize; ++t) {
			for (int s = 0; s < maxSize; ++s) {
				graph[t][s] = rand.nextInt(maxSize);
			}
		}

		return graph;
	}

	public static final double[][] getRandomDoubleGraph(int maxSize) {
		double[][] graph = new double[maxSize][maxSize];
		Random rand = new Random();

		for (int t = 0; t < maxSize; ++t) {
			for (int s = 0; s < maxSize; ++s) {
				graph[t][s] = rand.nextDouble();
			}
		}

		return graph;
	}
}