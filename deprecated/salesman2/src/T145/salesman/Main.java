package T145.salesman;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import T145.salesman.lib.City;
import T145.salesman.lib.Graph;
import T145.salesman.lib.GraphReference;

public class Main {

	private static void debugCities(List<City> cities, int phase) {
		System.out.println('\n' + " --- PHASE " + phase + " ---");

		for (City city : cities) {
			System.out.println(city.toString());

			if (city.hasCollisions()) {
				System.out.print("; Has collisions at: X { ");

				for (int x : city.getCollisions()) {
					System.out.print(x + " ");
				}

				System.out.print("}");
				System.out.println();
			}
		}
	}

	public static void main(String[] args) {
		int[][] graph = GraphReference.SIMPLE_GRAPH_WITH_COLLISIONS;
		List<City> cities = new ArrayList<>();

		for (int t = 0; t < graph.length; ++t) {
			cities.add(new City(graph[t][0], graph[t][1]));
		}

		for (int t = 0; t < graph.length; ++t) {
			City city = cities.get(t);

			for (int s = t + 1; s < graph.length; ++s) {
				if (graph[t][1] == graph[s][1]) {
					city.addCollision(graph[s][0]);
					cities.get(s).setAsDuplicate();
				}
			}
		}

		debugCities(cities, 1);

		for (Iterator<City> iterator = cities.iterator(); iterator.hasNext();) {
			City city = iterator.next();

			if (city.isDuplicate()) {
				iterator.remove();
			}
		}

		debugCities(cities, 2);
		System.out.println('\n' + " --- PHASE 3 ---");

		Graph map = new Graph();

		// neighbor will be the next city in cities
		// decided if collision is shorter in distance
		// if so, add it to offset, else add to the source and skip the neighbor (t += 2)
		for (int t = 0; t < cities.size(); ++t) {
			City city = cities.get(t);

			// will automatically skip adding to the same graph set
			// if a collision adds to the offset
			// (i.e. has a closer connection to it)
			map.addCity(city);

			if (city.hasCollisions()) {
				for (int x : city.getCollisions()) {
					City collision = new City(x, city.getY());
					collision.setAsDuplicate();

					if (t == cities.size() - 1) {
						// last city in the list; we don't need to compare w/ neighbors
						map.addCity(collision);
					} else {
						System.out.println("Sorting collision: " + collision.toString());
						City neighbor = cities.get(t + 1);
						double distToNeighbor = city.getDistance(neighbor);
						double distToCollision = city.getDistance(collision);

						if (distToNeighbor == distToCollision || distToNeighbor < distToCollision) {
							map.addCity(neighbor);
						} else {
							map.addCity(collision);
						}
					}
				}

				city.getCollisions().clear();
			}
		}

		List<City> source = map.getSource();
		List<City> offset = map.getOffset();

		System.out.println("Graph Length:\t" + graph.length);
		System.out.println("Source Size:\t" + source.size());
		System.out.println("Offset Size:\t" + offset.size());
		System.out.println("VERIFIED: " + (source.size() + offset.size() == graph.length));
		System.out.println('\n' + " --- PHASE 4 ---");

		/*
		 * NOTES:
		 * - The number of paths in the solution will always be the number of points
		 * minus one, so we can't be calculating by only the number of points.
		 * - If even in size, source and offset are good to go.
		 * - If odd in size, be sure source doesn't compute the missing link in offset.
		 * - Offset will always be less than source iff graph length is odd.
		 */

		double shortestDist = 0;

		// OPTIMIZATION: These two for-loops can definitely be combined

		for (City x : source) {
			System.out.println("Source: " + x.toString() + "; duplicate: " + x.isDuplicate());
		}

		for (City xy : offset) {
			System.out.println("Offset: " + xy.toString() + "; duplicate: " + xy.isDuplicate());
		}

		// calculate every diagonal
		for (int t = source.size() - 1; t >= 1; --t) {
			int offsetIndex = t - 1;
			City fromOffset = offset.get(offsetIndex);
			City toSource = source.get(t);
			shortestDist += fromOffset.getDistance(toSource);
		}

		// calculate every horizontal
		for (int s = 0; s < (offset.size() < source.size() ? offset.size() : source.size()); ++s) {
			City fromSource = source.get(s);
			City atOffset = offset.get(s);
			shortestDist += fromSource.getDistance(atOffset);
		}

		System.out.println("SOLUTION: " + shortestDist);
	}
}