package T145.salesman.attempt.one;

import java.util.ArrayList;
import java.util.List;

import T145.salesman.GraphReference;

public class Main {

	private static void debugCities(List<CityOne> cities, int phase) {
		System.out.println('\n' + " --- PHASE " + phase + " ---");

		for (CityOne city : cities) {
			System.out.println("CityOne Coords: " + city.toString());

			if (!city.collisions.isEmpty()) {
				System.out.print("; Has collisions at: X { ");

				for (int x : city.collisions) {
					System.out.print(x + " ");
				}

				System.out.print("}");
				System.out.println();
			}
		}
	}

	/*
	 * TODO:
	 * [ ] Handle duplicate cities (points)
	 * [X] Decided btwn. which colliding path is shortest
	 */
	public static void main(String[] args) {
		int[][] graph = GraphReference.SIMPLE_GRAPH_WITH_COLLISIONS;
		List<CityOne> cities = new ArrayList<>();

		for (int t = 0; t < graph.length; ++t) {
			cities.add(new CityOne(graph[t][0], graph[t][1]));
		}

		for (int t = 0; t < graph.length; ++t) {
			CityOne city = cities.get(t);

			for (int s = t + 1; s < graph.length; ++s) {
				if (graph[t][1] == graph[s][1]) {
					city.collisions.add(graph[s][0]);
					cities.get(s).mark();
				}
			}
		}

		debugCities(cities, 1);

		for (CityOne city : new ArrayList<>(cities)) {
			if (city.isMarked()) {
				cities.remove(city);
			}
		}

		debugCities(cities, 2);

		List<CityOne> collisions = new ArrayList<>();

		for (int t = 0; t < cities.size(); ++t) {
			CityOne city = cities.get(t);

			if (!city.collisions.isEmpty()) {
				for (int x : city.collisions) {
					CityOne dim = new CityOne(x, city.getY());
					dim.mark();
					collisions.add(dim);
				}
			}
			city.collisions.clear();
		}

		System.out.println('\n' + " --- PHASE 3 ---");
		System.out.println("Graph Size:\t" + graph.length);
		System.out.println("Sources:\t" + cities.size());
		System.out.println("Collisions:\t" + collisions.size());
		System.out.println("VERIFIED:\t" + (graph.length == (cities.size() + collisions.size())));

		double shortestDist = 0;

		// the last city cannot have collisions but can be a collision
		for (int s = 0; s < cities.size(); ++s) {
			// works for straight lines and basic graphs w/ one minimal collision
			if (collisions.isEmpty()) {
				if (s != cities.size() - 1) {
					shortestDist += cities.get(s).getDistance(cities.get(s + 1));
				}
			} else {
				CityOne from = cities.get(s);
				CityOne to = collisions.get(s == collisions.size() ? s - 1 : s);
				double distToNeighbor = 0;
				double distToCollision = from.getDistance(to);

				if (s != cities.size() - 1) {
					distToNeighbor += cities.get(s).getDistance(cities.get(s + 1));
				}

				if (distToCollision > distToNeighbor) {
					shortestDist += distToCollision;
				} else {
					shortestDist += distToNeighbor;
				}
			}
		}

		System.out.println("SOLUTION: " + shortestDist);
	}
}