package T145.salesman3;

import T145.salesman3.graphing.Graph;
import T145.salesman3.graphing.GraphList;
import T145.salesman3.graphing.Point;
import T145.salesman3.lib.Reference;

public class Main {

	/*
	 * As of now, the algorithm requires the following:
	 * - An input multidimensional array w/ x-values in a sorted order
	 * - No collisions in the final case
	 */
	public static void main(String[] args) {
		GraphList points = new GraphList(Reference.ODD_X);
		Graph graph = new Graph(points);

		double shortestDist = 0;
		int sourceSize = graph.getSource().size();
		int offsetSize = graph.getOffset().size();

		if (Reference.DEBUG) {
			System.out.println('\n' + " --- PHASE 3 ---");
			System.out.println("Graph Size:\t" + points.getPoints().size());
			System.out.println("Source Size:\t" + sourceSize);
			System.out.println("Offset Size:\t" + offsetSize);
			System.out.println("VERIFIED: " + (sourceSize + offsetSize == points.getPoints().size()));
		}

		// calculate every diagonal
		for (int t = sourceSize - 1; t >= 1; --t) {
			int offsetIndex = t - 1;
			Point fromOffset = graph.getOffset().get(offsetIndex);
			Point toSource = graph.getSource().get(t);
			shortestDist += fromOffset.getDistance(toSource);
		}

		// calculate every horizontal
		for (int s = 0; s < (offsetSize < sourceSize ? offsetSize : sourceSize); ++s) {
			Point fromSource = graph.getSource().get(s);
			Point atOffset = graph.getOffset().get(s);
			shortestDist += fromSource.getDistance(atOffset);
		}

		// calculate the final distance btwn. the last point and the first point
		/*
			Point atLast = sourceSize == offsetSize ? graph.getOffset().get(offsetSize - 1) : graph.getSource().get(sourceSize - 1);
			Point toFirst = graph.getSource().get(0);
			double finalDist = atLast.getDistance(toFirst);
			shortestDist += finalDist < shortestDist ? finalDist : shortestDist; // handles linear cases to balloon cases
		 */
		System.out.println("SOLUTION: " + shortestDist);
	}
}