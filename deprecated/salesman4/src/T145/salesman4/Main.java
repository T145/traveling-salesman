package T145.salesman4;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import T145.salesman4.graphing.Graph;
import T145.salesman4.graphing.Point;
import T145.salesman4.lib.Reference;

public class Main {

	/*
	 * PROCESS:
	 * 
	 * STEP 1: Sort the data to make it easier to apply linear mechanics
	 * 
	 * We don't change the actual data, just rearrange it. It takes a total of time
	 * of O(2n) for initialization and sorting.
	 * 
	 * STEP 2: Calculate collisions, merge them into the source point, and remove it
	 * from the list.
	 * 
	 * NOTE: A collision is any point that shares a y value w/ another point.
	 * 
	 * Since we're virtually making the whole graph into one line along the y axis
	 * so everything can be calculated easily, matching y values are a problem.
	 * 
	 * QUESTION: Do collisions need to be identified as the start? It's
	 * characteristic of the TSP to start and end at the same point. We can add this
	 * functionality into Point, as any point would share that characteristic. The
	 * start is at the beginning of the list by default, but can be any point in the list.
	 * 
	 * A few rules to keep in mind:
	 * - A collision is just a simplified concept of a point, so it can be a start-point
	 * - Collisions can be put into the offset list, but never in the source list
	 * 
	 * OPTIONS FOR SOLVING COLLISIONS:
	 * 
	 * I. Offset the collisions by a tiny amount (something like 0.01) along their y axis for calculation.
	 * This way we can treat them like normal points, and if said point is a collision, then we just subtract
	 * the final int COLLISION_OFFSET so we get the original value
	 * 
	 * II. Stack cache. Have a simple stack that stores any collisions during the mapping process
	 * (aka graph construction). When a collision is properly calculated pop it off. The remaining
	 * collision(s) can be thrown against the distances from it and the following source points.
	 * Get the min distance, and add it to the offset list after its shortest source path.
	 * If it comes before another offset point, be sure it is the optimal path for the point it supplants,
	 * else we have to keep searching. In the case that it can't find a home, the graph is unsolvable.
	 * This should never happen as it should at least become the end-point (point before the start).
	 * 
	 * STEP 3: Split up the graph into two separate lists
	 * 
	 * When calculating the final distance, the algorithm looks at the horizontals in the source and offset
	 * as the closest points, and diagonals from offset to source as the next connection.
	 * 
	 * The hard part is deciding where the collisions go. There are a few cases to consider:
	 * 
	 * 1. Equilateral triangle, where it really doesn't matter which route is planned.
	 * The best method here is to add the collision to offset, and then the next point to source.
	 * 2. Scalene or isosceles triangle, where we have to compare the distances btwn. the neighboring point
	 * and collision, and add the shorter point to offset.
	 * 
	 * STEP 4: Calculate the final distance
	 * 
	 * After calculating this graph, we don't need to find the distance from one point specifically, as
	 * the total distance from each point will be same regardless of the graph. This makes the start point
	 * completely arbitrary.
	 * 
	 */
	public static void main(String[] args) {
		double[][] rawGraph = Reference.ANNOYING_TRIANGLE;
		List<Point> points = new ArrayList<>();

		System.out.println('\n' + "Input Graph: ");
		for (int t = 0; t < rawGraph.length; ++t) {
			Point point = new Point(rawGraph[t][0], rawGraph[t][1]);
			points.add(point);
			System.out.println(point);
		}

		Collections.sort(points, new Comparator<Point>() {
			public int compare(Point x1, Point x2) {
				int result = Double.compare(x1.getX(), x2.getX());

				if (result == 0) {
					result = Double.compare(x1.getY(), x2.getY());
				}

				return result;
			}
		});

		// initialization total: O(2n)

		System.out.println('\n' + "Sorted Graph: ");
		for (Point point : points) {
			System.out.println(point);
		}

		for (int t = 0; t < points.size(); ++t) {
			Point point = points.get(t);

			for (int s = t + 1; s < points.size(); ++s) {
				Point other = points.get(s);

				if (point.getY() == other.getY()) {
					point.addCollision(other);
					points.remove(s);
				}
			}
		}

		// collision calculation total: O(n^2)

		System.out.println('\n' + " --- PHASE 1 ---");

		for (Point city : points) {
			System.out.println(city.toString());

			if (city.hasCollisions()) {
				for (Point collision : city.getCollisions()) {
					System.out.println("|- " + collision);
				}
			}
		}

		// total so far: O(2n+n^2)

		Graph graph = new Graph(points);
		double shortestDist = 0;
		int sourceSize = graph.getSource().size();
		int offsetSize = graph.getOffset().size();
		boolean verified = sourceSize + offsetSize == rawGraph.length;

		// graph init: O(n^2)

		System.out.println('\n' + " --- PHASE 2 ---");
		System.out.println("Graph Size:\t" + rawGraph.length);
		System.out.println("Source Size:\t" + sourceSize);
		System.out.println("Offset Size:\t" + offsetSize);
		System.out.println("VERIFIED: " + verified);
		System.out.println('\n' + " --- PHASE 3 ---");

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
		//		Point atLast = sourceSize == offsetSize ? graph.getOffset().get(offsetSize - 1) : graph.getSource().get(sourceSize - 1);
		//		Point toFirst = graph.getSource().get(0);
		//		double finalDist = atLast.getDistance(toFirst);
		//		shortestDist += finalDist < shortestDist ? finalDist : shortestDist; // handles linear cases to balloon cases

		// solution calculation: O(2n)
		// section total: O(2n+n^2)

		// total time: O(4n+2n^2)

		System.out.println("SOLUTION: " + shortestDist);
	}
}