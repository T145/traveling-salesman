package T145.salesman.attempt.one;

import java.util.ArrayList;
import java.util.List;

public class CityOne {

	private final int x;
	private final int y;
	private boolean marked;

	public List<Integer> collisions;

	public CityOne(int x, int y) {
		this.x = x;
		this.y = y;
		this.collisions = new ArrayList<>();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void mark() {
		this.marked = true;
	}

	public boolean isMarked() {
		return marked;
	}

	public double getDistance(int otherX, int otherY) {
		return Math.sqrt(Math.pow(otherX - x, 2) + Math.pow(otherY - y, 2));
	}

	public double getDistance(CityOne cityOne) {
		return getDistance(cityOne.x, cityOne.y);
	}

	@Override
	public String toString() {
		return "{ X:" + x + "; Y:" + y + " }";
	}
}