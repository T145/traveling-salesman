package T145.salesman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Main {

	private static class Point {

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
			return Math.sqrt(Math.pow(dest.getX() - x, 2) + Math.pow(dest.getY() - y, 2));
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

	public static void main(String[] args) {
		System.out.println("Hello World!"); // superstition lives free

		double[][] rawGraph = Reference.SQUARE;
		List<Point> points = new ArrayList<>();

		System.out.println('\n' + "Input Graph:");
		for (int t = 0; t < rawGraph.length; ++t) {
			Point point = new Point(rawGraph[t][0], rawGraph[t][1]);
			points.add(point);
			System.out.println(point);
		}

		// single point case
		if (rawGraph.length == 1) {
			System.out.println("SOLUTION: 0");
			return;
		}

		Collections.sort(points, new Comparator<Point>() {

			@Override
			public int compare(Point a, Point b) {
				int result = Double.compare(a.getX(), b.getX());

				if (result == 0) {
					result = Double.compare(a.getY(), b.getY());
				}

				return result;
			}
		});

		System.out.println('\n' + "Sorted Graph:");
		for (Point point : points) {
			System.out.println(point);
		}

		System.out.println('\n' + " --- SHORTEST PATH ---");

		List<Collision> collisions = new ArrayList<>();

		for (int t = 0; t < points.size(); ++t) {
			Point point = points.get(t);

			for (int s = t + 1; s < points.size(); ++s) {
				Point other = points.get(s);

				if (point.getY() == other.getY()) {
					other.setColliding();
					collisions.add(new Collision(point, other));
					points.remove(s);
				}
			}
		}

		List<Point> solution;

		if (collisions.isEmpty()) {
			solution = new ArrayList<>(points);
		} else {
			solution = new ArrayList<>();

			for (int t = 0; t < points.size(); ++t) {
				Point point = points.get(t);
				solution.add(point);

				for (int s = 0; s < collisions.size(); ++s) {
					Collision collision = collisions.get(s);

					// add the collision if it is the closer neighbor, else leave it in the cache
					if (collision.getSource().equals(point)) {
						Point next = collision.getHit();
						Point neighbor = points.get(t == points.size() - 1 ? 0 : t + 1);
						double collisionDist = point.getDistance(next);
						double neighborDist = point.getDistance(neighbor);

						if (collisionDist <= neighborDist) {
							solution.add(next);
							collisions.remove(s);
						}
					}
				}
			}

			// any leftover collisions fall into the scalene triangle case
			// we need to calculate the total distance of the graph w/ the collision at every point,
			// decide which is the shortest distance, and which index the collision can be inserted at to get said distance
			Map<Double, Integer> distances = new HashMap<>();
			List<Point> virtualSolution /*= new ArrayList<>(solution)*/;

			// final verification; check if collisions are in the right spot
			if (collisions.isEmpty()) {
				for (int t = 1; t < solution.size(); ++t) { // a collision cannot be the first point, so skip it
					Point p = solution.get(t);

					if (p.isColliding()) {
						virtualSolution = new ArrayList<>(solution);
						Collections.swap(virtualSolution, t, t - 1);

						System.out.println();
						for (Point point : virtualSolution) {
							System.out.println(point);
						}

						double virtualDist = getTotalDistance(virtualSolution);
						double currentDist = getTotalDistance(solution);

						System.out.println("Colliding Index: " + t);
						System.out.println("Potential Distance: " + virtualDist);
						System.out.println("Colliding Distance: " + currentDist);

						if (virtualDist < currentDist) {
							solution = new ArrayList<>(virtualSolution);
						}
					}
				}

				System.out.println();
			} else {
				while (!collisions.isEmpty()) {
					Collision collision = collisions.remove(0);
					Point hit = collision.getHit();
					System.out.println("Processing leftovers: " + hit);

					// BUG TODO: Flat line misplaces the last collision
					for (int t = 0; t < solution.size(); ++t) {
						virtualSolution = new ArrayList<>(solution);
						virtualSolution.add(t, hit);
						distances.put(getTotalDistance(virtualSolution), t);
					}

					for (Entry<Double, Integer> dist : distances.entrySet()) {
						System.out.println("Distance: " + dist.getKey() + "; Index: " + dist.getValue());
					}

					solution.add(distances.get(Collections.min(distances.keySet())), hit);
					distances.clear();
				}
			}
		}

		for (Point point : solution) {
			System.out.println(point);
		}

		System.out.println('\n' + " --- VERIFICATION ---");
		System.out.println("Graph Length:\t" + rawGraph.length);
		System.out.println("Solution Size:\t" + solution.size());
		System.out.println("VERIFIED: " + (solution.size() == rawGraph.length));
		System.out.println('\n' + " --- FINAL PHASE ---");
		System.out.println("SOLUTION: " + getTotalDistance(solution));
	}

	private static double getTotalDistance(List<Point> solution) {
		double shortestDist = 0;

		for (int t = 0; t < solution.size(); ++t) {
			shortestDist += solution.get(t).getDistance(solution.get(t == solution.size() - 1 ? 0 : t + 1));
		}

		return shortestDist;
	}
}