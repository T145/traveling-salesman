package T145.salesman.lib;

import java.util.ArrayList;
import java.util.List;

public class Graph {

	private List<City> source = new ArrayList<>();
	private List<City> offset = new ArrayList<>();
	private int id;

	public void addCity(City city) {
		switch (id) {
		case 0:
			++id;
			source.add(city);
			break;
		case 1:
			--id;
			offset.add(city);
			break;
		default: // the "solar radiation" case
			System.err.println("No destination to add cities to!");
			System.err.println(city.toString() + "; id: " + id);
			break;
		}
	}

	public List<City> getSource() {
		return source;
	}

	public List<City> getOffset() {
		return offset;
	}
}