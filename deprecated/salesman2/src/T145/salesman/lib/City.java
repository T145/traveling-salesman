package T145.salesman.lib;

import java.util.ArrayList;
import java.util.List;

public class City {

	private final int x;
	private final int y;
	private List<Integer> collisions = new ArrayList<>();
	private boolean duplicate;

	public City(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "City: { X:" + x + "; Y:" + y + " }";
	}

	public double getDistance(int otherX, int otherY) {
		return Math.sqrt(Math.pow(otherX - x, 2) + Math.pow(otherY - y, 2));
	}

	public double getDistance(City city) {
		return getDistance(city.x, city.y);
	}

	public List<Integer> getCollisions() {
		//return new ArrayList<>(collisions);
		return collisions;
	}

	public void addCollision(int x) {
		collisions.add(x);
	}

	public boolean hasCollisions() {
		return !collisions.isEmpty();
	}

	public void setAsDuplicate() {
		this.duplicate = true;
	}

	public boolean isDuplicate() {
		return duplicate;
	}
}