package T145.salesman;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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

	/*
	 * NOTES:
	 * - a point CANNOT have more than two connections, otherwise we'd be visiting it more than once
	 * - if collisionDist <= neighborDist add the collision after the point else cache for later calculation
	 */

	public static void main(String[] args) {

		// superstition lives free
		System.out.println("Hello World!");

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

		// TODO: Optimize to skip indexes that were registered as collisions
		for (int t = 0; t < points.size(); ++t) {
			Point point = points.get(t);

			for (int s = t + 1; s < points.size(); ++s) {
				Point other = points.get(s);

				if (point.getY() == other.getY()) {
					other.setColliding();
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

			// any leftover points must be added to the top
			// the sources are already in the list
			while (!collisions.isEmpty()) {
				Collision collision = collisions.remove(0);
				System.out.println("Leftovers: " + collision);
				solution.add(collision.getHit());
			}

			for (int t = 1, uBound = solution.size() - 1; t < uBound; ++t) {
				Point point = solution.get(t);

				if (point.isColliding()) {
					System.out.println("Collision: " + t);
				}
			}

			// verify the solution
			// this is basically just for the square case or other level polygon cases; all other cases should be unaffected
			/*for (int t = 1, uBound = solution.size() - 1; t < uBound; ++t) {
				Point point = solution.get(t);

				if (point.isColliding()) {
					// get the previous point (a collision cannot be the first point)
					int sourceIndex = t - 1;
					Point source = solution.get(sourceIndex);
					double sourceDist = source.getDistance(point);

					// get the next point
					int neighborIndex = t + 1;
					Point neighbor = solution.get(neighborIndex);
					double neighborDist = point.getDistance(neighbor);

					// get the neighbor distance for the next point
					int nextNeighborIndex = t == uBound - 2 ? 0 : t + 2;
					Point nextNeighbor = solution.get(nextNeighborIndex);
					double nextNeighborDist = neighbor.getDistance(nextNeighbor);

					// NOTE: B/c we're checking a collision, the y values of it and the point to swap should be the same
					// so, if the x value of the neighbor and swapee are in the right order, then don't swap
					// the same could be said for y values of other graphs, so we don't mess up other cases
					// if x != other.x && y != other.y then swap

					if (sourceDist == nextNeighborDist && neighborDist > sourceDist) {
						int otherNeighborIndex = neighborIndex + 1;
						Point otherNeighbor = solution.get(otherNeighborIndex);

						if (neighbor.getX() != otherNeighbor.getX() && neighbor.getY() != otherNeighbor.getY()) {
							Collections.swap(solution, neighborIndex, otherNeighborIndex);

							// be sure we don't undo intentionally undo our changes by skipping the element we just swapped
							++t;
						}
					}
				}
			}*/
		} else {
			solution = new ArrayList<>(points);
		}

		for (Point point : solution) {
			System.out.println(point);
		}

		// we have the shortest path right now, so we can just iterate over it

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