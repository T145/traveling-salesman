package T145.salesman3.graphing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Point {

	private final int x;
	private final int y;

	private List<Integer> collisions = new ArrayList<>();
	private boolean duplicate;

	public Point(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public double getDistance(int otherX, int otherY) {
		return Math.sqrt(Math.pow(otherX - x, 2) + Math.pow(otherY - y, 2));
	}

	public double getDistance(Point point) {
		return getDistance(point.x, point.y);
	}

	public List<Integer> getCollisions() {
		return Collections.unmodifiableList(collisions);
	}

	public void addCollision(int x) {
		collisions.add(x);
	}

	public boolean hasCollisions() {
		return !collisions.isEmpty();
	}

	public void setDuplicate() {
		duplicate = true;
	}

	public boolean isDuplicate() {
		return duplicate;
	}

	@Override
	public String toString() {
		return "Point: { X:" + x + ", Y: " + y + " }";
	}
}