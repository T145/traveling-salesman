package T145.salesman3.graphing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import T145.salesman3.lib.Reference;

public class GraphList {

	private final List<Point> points = new ArrayList<>();

	public GraphList(int[][] graph) {
		for (int t = 0; t < graph.length; ++t) {
			points.add(new Point(graph[t][0], graph[t][1]));
		}

		for (int t = 0; t < graph.length; ++t) {
			Point city = points.get(t);

			for (int s = t + 1; s < graph.length; ++s) {
				if (graph[t][1] == graph[s][1]) {
					city.addCollision(graph[s][0]);
					points.get(s).setDuplicate();
				}
			}
		}

		if (Reference.DEBUG) {
			debug(1);
		}

		for (Point city : points) {
			if (city.isDuplicate()) {
				points.remove(city);
			}
		}

		if (Reference.DEBUG) {
			debug(2);
		}
	}

	public List<Point> getPoints() {
		return Collections.unmodifiableList(points);
	}

	private void debug(int phase) {
		System.out.println('\n' + " --- PHASE " + phase + " ---");

		for (Point city : points) {
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
}