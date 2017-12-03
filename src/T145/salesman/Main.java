package T145.salesman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Main {

	private static class Point {

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
			return Math.sqrt(Math.pow(dest.getX() - x, 2) + Math.pow(dest.getY() - y, 2));
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

	/*
	 * NOTES:
	 * - a point CANNOT have more than two connections, otherwise we'd be visiting it more than once
	 * - if collisionDist <= neighborDist add the collision after the point else cache for later calculation
	 */

	public static void main(String[] args) {
		System.out.println("Hello World!"); // superstition lives free

		double[][] rawGraph = Reference.TRICKY_TRAPEZOID;
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

		// TODO: Optimize to skip indexes that were registered as collisions
		for (int t = 0; t < points.size(); ++t) {
			Point point = points.get(t);

			for (int s = t + 1; s < points.size(); ++s) {
				Point other = points.get(s);

				if (point.getY() == other.getY()) {
					collisions.add(new Collision(point, other));

					// remove, b/c collisions can fit later in the list, and we want to find where they can go
					points.remove(s);
				}
			}
		}

		List<Point> solution;

		if (!collisions.isEmpty()) {
			solution = new ArrayList<>();

			for (int t = 0; t < points.size(); ++t) {
				Point point = points.get(t);
				solution.add(point);

				for (int s = 0; s < collisions.size(); ++s) {
					Collision collision = collisions.get(s);

					// decide if the collision is the closest point to add and dequeue it, else leave it in the cache
					if (collision.getSource().equals(point)) {
						Point next = collision.getHit();
						Point neighbor = points.get(t == points.size() - 1 ? 0 : t + 1);
						double collisionDist = point.getDistance(next);
						double neighborDist = point.getDistance(neighbor);

						if (collisionDist <= neighborDist) {
							solution.add(next);
							collisions.remove(s);
						} else {
							// the collision is left in the cache
							// if there's anything smart we need to do w/ it do it here
						}
					}
				}
			}

			Deque<Integer> indices = new LinkedList<>();

			// if we have any leftover points, find the best place to put it
			// the collisions should still be in a sorted order, so just start from the bottom and work our way up
			// fixes multiple collision cases
			while (!collisions.isEmpty()) {
				Collision collision = collisions.remove(0);
				System.out.println("Leftovers: " + collision);
				Point target = collision.getHit();

				indices.clear();

				for (int t = 0; t < solution.size(); ++t) {
					Point candidate = solution.get(t);
					double dist = candidate.getDistance(target);

					if (indices.isEmpty()) {
						indices.add(t);
					} else if (solution.get(indices.getFirst()).getDistance(target) <= dist) {
						indices.addFirst(t);
					} else {
						indices.addLast(t);
					}
				}

				solution.add(indices.getFirst(), target);
			}
		} else {
			solution = new ArrayList<>(points);
		}

		for (Point point : solution) {
			System.out.println(point);
		}

		System.out.println('\n' + " --- VERIFICATION ---");
		System.out.println("Graph Length:\t" + rawGraph.length);
		System.out.println("Solution Size:\t" + solution.size());
		System.out.println("VERIFIED: " + (solution.size() == rawGraph.length));
		System.out.println('\n' + " --- FINAL PHASE ---");

		double shortestDist = 0;

		for (int t = 0; t < solution.size(); ++t) {
			shortestDist += solution.get(t).getDistance(solution.get(t == solution.size() - 1 ? 0 : t + 1));
		}

		System.out.println("SOLUTION: " + shortestDist);
	}
}