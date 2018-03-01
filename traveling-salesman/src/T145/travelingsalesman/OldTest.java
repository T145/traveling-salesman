package T145.travelingsalesman;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OldTest {

	private static class Point implements Comparable<Point> {

		private final double x;
		private final double y;

		public Point(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public double getX() {
			return x;
		}

		public double getY() {
			return y;
		}

		public double getDistance(Point dest) {
			double distX = dest.getX() - x;
			double distY = dest.getY() - y;
			distX *= distX;
			distY *= distY;
			return Math.sqrt(distX + distY);
		}

		@Override
		public String toString() {
			return " x: " + x + "; y: " + y;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Point) {
				Point point = (Point) obj;
				return point.x == x && point.y == y;
			}

			return false;
		}

		@Override
		public int compareTo(Point other) {
			int result = Double.compare(x, other.x);

			if (result == 0) {
				result = Double.compare(y, other.y);
			}

			return result;
		}
	}

	private static Point getNextPoint(List<Point> solution, int start) {
		return solution.get(start == solution.size() - 1 ? 0 : start + 1);
	}

	private static double getTotalDistance(List<Point> solution) {
		double shortestDist = 0;

		for (int t = 0; t < solution.size(); ++t) {
			shortestDist += solution.get(t).getDistance(getNextPoint(solution, t));
		}

		return shortestDist;
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		double[][] graph = Reference.SIMPLE_GRAPH;

		if (graph.length <= 1) {
			System.out.println("SOLUTION: 0");
			return;
		}

		LinkedList<Point> solution = new LinkedList<>();

		// O(n)
		System.out.println("Input Graph: ");
		for (int t = 0; t < graph.length; ++t) {
			Point point = new Point(graph[t][0], graph[t][1]);
			solution.add(point);
			System.out.println(point);
		}

		// O(nlog(n))
		Collections.sort(solution);

		LinkedList<Point> solutionCopy;
		ArrayDeque<Point> collisions = new ArrayDeque<>(graph.length);

		// O(n^2)
		for (int t = 0; t < solution.size(); ++t) {
			Point point = solution.get(t);

			solutionCopy = new LinkedList<>(solution);
			Collections.swap(solutionCopy, t, t == 0 ? solution.size() - 1 : t - 1);

			double solutionDist = 0;
			double virtualDist = 0;

			for (int s = 0; s < solution.size(); ++s) {
				if (s > t) {
					Point other = solution.get(s);

					if (point.getY() == other.getY()) {
						collisions.add(other);
						solution.remove(s);
						solutionCopy.remove(s);
						break;
					}
				}

				solutionDist += solution.get(s).getDistance(getNextPoint(solution, s));
				virtualDist += solutionCopy.get(s).getDistance(getNextPoint(solutionCopy, s));
			}

			if (virtualDist < solutionDist) {
				solution = new LinkedList<>(solutionCopy);
			}
		}

		if (!collisions.isEmpty()) {
			List<Point> temp; // use ArrayList for faster calculation on large data sets
			Map<Double, Integer> distances = new HashMap<>(solution.size(), 1F);

			// O(n^3)
			while (!collisions.isEmpty()) {
				Point c = collisions.remove();

				for (int t = 0; t < solution.size(); ++t) {
					temp = new ArrayList<>(solution);
					temp.add(t, c);
					distances.put(getTotalDistance(temp), t);
				}

				solution.add(distances.get(Collections.min(distances.keySet())), c);
				distances.clear();
			}
		}

		System.out.println('\n' + " --- RESULT ---");

		for (Point point : solution) {
			System.out.println(point);
		}

		System.out.println('\n' + " --- VERIFICATION ---");
		System.out.println("Graph Length:\t" + graph.length);
		System.out.println("Solution Size:\t" + solution.size());
		System.out.println("VERIFIED: " + (solution.size() == graph.length));
		System.out.println('\n' + " --- FINAL PHASE ---");
		System.out.println("SOLUTION: " + getTotalDistance(solution));
		System.out.println("Runtime: " + (System.currentTimeMillis() - start) + " ms");
	}
}