package T145.salesman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {

	private static class Point implements Comparable<Point> {

		private final double x;
		private final double y;

		private boolean colliding;

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

		public void setColliding() {
			colliding = true;
		}

		public boolean isColliding() {
			return colliding;
		}

		@Override
		public String toString() {
			return "{ X: " + x + "; Y: " + y + " }";
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
			int result = Double.compare(x, other.getX());

			if (result == 0) {
				result = Double.compare(y, other.getY());
			}

			return result;
		}
	}

	private static double getTotalDistance(List<Point> points) {
		double shortestDist = 0;

		for (int t = 0; t < points.size(); ++t) {
			shortestDist += points.get(t).getDistance(points.get(t == points.size() - 1 ? 0 : t + 1));
		}

		return shortestDist;
	}

	private static void printResults(double[][] graph, List<Point> points) {
		System.out.println('\n' + " --- RESULT ---");

		for (Point point : points) {
			System.out.println(point);
		}

		System.out.println('\n' + " --- VERIFICATION ---");
		System.out.println("Graph Length:\t" + graph.length);
		System.out.println("Solution Size:\t" + points.size());
		System.out.println("VERIFIED: " + (points.size() == graph.length));
		System.out.println('\n' + " --- FINAL PHASE ---");
		System.out.println("SOLUTION: " + getTotalDistance(points));
	}

	public static void main(String[] args) {
		double[][] graph = Reference.SQUARE_WITH_CENTER;

		if (graph.length <= 1) {
			System.out.println("SOLUTION: 0");
			return;
		}

		List<Point> points = new ArrayList<>();
		List<Point> collisions = new ArrayList<>();

		// O(n)
		System.out.println("Input Graph: ");
		for (int t = 0; t < graph.length; ++t) {
			Point point = new Point(graph[t][0], graph[t][1]);
			points.add(point);
			System.out.println(point);
		}

		// O(nlog(n))
		Collections.sort(points);

		// O(n^2)
		for (int t = 0; t < points.size(); ++t) {
			Point point = points.get(t);

			for (int s = t + 1; s < points.size(); ++s) {
				Point other = points.get(s);

				if (point.getY() == other.getY()) {
					other.setColliding();
					collisions.add(other);
					points.remove(s);
				}
			}
		}

		if (collisions.isEmpty()) {
			printResults(graph, points);
		} else {
			List<Point> virtualSolution;

			// final verification; check if collisions are in the right spot
			if (collisions.isEmpty()) {

				// O(n^2)
				for (int t = 1; t < points.size(); ++t) { // a collision cannot be the first point, so skip it
					Point p = points.get(t);

					if (p.isColliding()) {
						virtualSolution = new ArrayList<>(points);
						Collections.swap(virtualSolution, t, t - 1);

						if (getTotalDistance(virtualSolution) < getTotalDistance(points)) {
							points = new ArrayList<>(virtualSolution);
						}
					}
				}
			} else {
				Map<Double, Integer> distances = new HashMap<>();

				// O(n^3)
				while (!collisions.isEmpty()) {
					Point c = collisions.remove(0);

					// BUG TODO: Flat line misplaces the last collision
					for (int t = 0; t < points.size(); ++t) {
						virtualSolution = new ArrayList<>(points);
						virtualSolution.add(t, c);
						distances.put(getTotalDistance(virtualSolution), t);
					}

					points.add(distances.get(Collections.min(distances.keySet())), c);
					distances.clear();
				}
			}

			printResults(graph, points);
		}
	}
}