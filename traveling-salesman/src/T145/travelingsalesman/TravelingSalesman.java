package T145.travelingsalesman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;

public class TravelingSalesman {

	private static Point getNextPoint(List<Point> solution, int start) {
		return solution.get(start == solution.size() - 1 ? 0 : start + 1);
	}

	private static double getTotalDistance(List<Point> solution) {
		double shortestDist = 0;

		for (int t = 0; t < solution.size(); ++t) {
			shortestDist += solution.get(t).getDistanceTo(getNextPoint(solution, t));
		}

		return shortestDist;
	}

	// O(nlgn) * theoretically
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		double[][] rawGraph = Reference.SIMPLE_GRAPH;

		if (rawGraph.length <= 1) {
			System.out.println("SOLUTION: 0");
			return;
		}

		List<Point> graph = new ArrayList<>();

		// O(n)
		// build the graph in terms of our point
		System.out.println("Input Graph: ");
		for (double[] dot : rawGraph) {
			graph.add(new Point(dot[1], dot[0]));
			System.out.println("x: " + dot[0] + "; y: " + dot[1]);
		}

		// NOTE: X & Y coords are swapped

		// O(nlgn)
		// sort the graph first to make finding duplicates easier
		Collections.sort(graph);

		List<Point> wreck = new ArrayList<>();

		for (int t = 0; t < graph.size(); ++t) {
			Point curr = graph.get(t);
			int nextIndex = t == graph.size() - 1 ? 0 : t + 1;
			Point next = graph.get(nextIndex);

			if (curr.equals(next)) {
				graph.remove(nextIndex);
			} else if (curr.getX() == next.getX()) {
				wreck.add(next.swapCoords());
				graph.remove(nextIndex);
			}

			graph.set(t, curr.swapCoords());
		}

		System.out.println('\n' + " --- INPUT GRAPH ---");
		for (Point point : graph) {
			System.out.println(point);
		}

		System.out.println('\n' + " --- COLLISIONS ---");
		if (wreck.isEmpty()) {
			System.out.println("nil");
		} else {
			for (Point point : wreck) {
				System.out.println(point);
			}
		}

		// build the source graph
		TreeMap<Double, Point> tree = new TreeMap<>();
		List<Point> virtualGraph;

		for (int t = 0; t < graph.size(); ++t) {
			Point curr = graph.get(t);

			virtualGraph = new ArrayList<>(graph);

			// swap with the next point
			Collections.swap(virtualGraph, t, t == 0 ? graph.size() - 1 : t - 1);

			double currDist = curr.getDistanceTo(getNextPoint(graph, t));
			double virtualDist = curr.getDistanceTo(getNextPoint(virtualGraph, t));

			if (virtualDist < currDist) {
				graph = new ArrayList<>(virtualGraph);
				curr = graph.get(t); // be sure to update curr
			}

			// the point is in the right position, so load its metadata
			if (t > 0) {
				Point child = graph.get(t - 1);
				curr.setChild(child);
				child.setParent(curr);

				if (t == graph.size() - 1) {
					Point origin = graph.get(0);
					origin.setChild(curr);
					curr.setParent(origin);
				}

				curr.setTotalDistance(curr.getChild().getTotalDistance() + curr.getChildDistance());
				tree.put(curr.getTotalDistance(), curr);
			} else {
				tree.put(0.0, curr);
			}
		}

		System.out.println('\n' + " --- RESULT GRAPH ---");
		for (Point point : graph) {
			System.out.println(point);
		}

		// TODO: Add collision calculations
	}
}