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

	private static class Collision {

		private final Point source;
		private final Point hit;

		public Collision(Point source, Point hit) {
			this.source = source;
			this.hit = hit;
		}

		public Point getSource() {
			return source;
		}

		public Point getHit() {
			return hit;
		}

		@Override
		public String toString() {
			return "Source: " + source + "; Hit: " + hit;
		}
	}

	private static double getTotalDistance(List<Point> solution) {
		double shortestDist = 0;

		for (int t = 0; t < solution.size(); ++t) {
			shortestDist += solution.get(t).getDistance(solution.get(t == solution.size() - 1 ? 0 : t + 1));
		}

		return shortestDist;
	}

	private static void printResults(double[][] graph, List<Point> solution) {
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
	}

	public static void main(String[] args) {
		double[][] graph = Reference.SIMPLE_GRAPH;

		if (graph.length <= 1) {
			System.out.println("SOLUTION: 0");
			return;
		}

		List<Point> points = new ArrayList<>();
		List<Collision> collisions = new ArrayList<>();

		System.out.println("Input Graph: ");
		for (int t = 0; t < graph.length; ++t) {
			Point p = new Point(graph[t][0], graph[t][1]);
			boolean isSource = true;

			for (int s = 0; s < t; ++s) {
				if (graph[t][1] == graph[s][1]) {
					// then this point is a collision, and the location at s is the source
					isSource = false;
					p.setColliding();
					collisions.add(new Collision(points.get(s), p));
				}
			}

			if (isSource) {
				points.add(p);
			}

			System.out.println(p);
		}

		Collections.sort(points);

		if (collisions.isEmpty()) {
			printResults(graph, points);
		} else {
			List<Point> solution = new ArrayList<>();

			// O(n^2)
			for (int t = 0; t < points.size(); ++t) {
				Point point = points.get(t);
				solution.add(point);

				for (int s = 0; s < collisions.size(); ++s) {
					Collision collision = collisions.get(s);

					// add the collision if it is the closer neighbor, else leave it in the cache
					if (collision.getSource().equals(point)) {
						Point next = collision.getHit();
						Point neighbor = points.get(t == points.size() - 1 ? 0 : t + 1);

						if (point.getDistance(next) <= point.getDistance(neighbor)) {
							solution.add(next);
							collisions.remove(s);
						}
					}
				}
			}

			List<Point> virtualSolution;

			// final verification; check if collisions are in the right spot
			if (collisions.isEmpty()) {

				// O(n^2)
				for (int t = 1; t < solution.size(); ++t) { // a collision cannot be the first point, so skip it
					Point p = solution.get(t);

					if (p.isColliding()) {
						virtualSolution = new ArrayList<>(solution);
						Collections.swap(virtualSolution, t, t - 1);

						if (getTotalDistance(virtualSolution) < getTotalDistance(solution)) {
							solution = new ArrayList<>(virtualSolution);
						}
					}
				}
			} else {
				Map<Double, Integer> distances = new HashMap<>();

				// O(n^3)
				while (!collisions.isEmpty()) {
					Point c = collisions.remove(0).getHit();

					// BUG TODO: Flat line misplaces the last collision
					for (int t = 0; t < solution.size(); ++t) {
						virtualSolution = new ArrayList<>(solution);
						virtualSolution.add(t, c);
						distances.put(getTotalDistance(virtualSolution), t);
					}

					solution.add(distances.get(Collections.min(distances.keySet())), c);
					distances.clear();
				}
			}

			printResults(graph, solution);
		}
	}
}