package T145.salesman3.graphing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Graph {

	private final List<Point> source = new ArrayList<>();
	private final List<Point> offset = new ArrayList<>();
	private short id;

	public Graph(GraphList points) {
		for (int t = 0; t < points.getPoints().size(); ++t) {
			Point city = points.getPoints().get(t);

			addPoint(city);

			if (city.hasCollisions()) {
				// TODO: Properly handle collisions
			}
		}
	}

	public void addPoint(Point city) {
		switch (id) {
		case 0:
			++id;
			source.add(city);
			break;
		case 1:
			--id;
			offset.add(city);
			break;
		default: // the solar radiation case
			System.err.println("No destination to add cities to!");
			System.err.println(city.toString() + "; id: " + id);
			break;
		}
	}

	public List<Point> getSource() {
		return Collections.unmodifiableList(source);
	}

	public List<Point> getOffset() {
		return Collections.unmodifiableList(offset);
	}
}