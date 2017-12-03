package T145.salesman4.graphing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Point {

	private final double x;
	private final double y;

	private boolean start;
	private boolean end; // start point - 1
	private List<Point> collisions = new ArrayList<>();

	public Point(final double x, final double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getDistance(Point podouble) {
		return Math.sqrt(Math.pow(podouble.getX() - x, 2) + Math.pow(podouble.getY() - y, 2));
	}

	@Override
	public String toString() {
		return "Point: { X:" + x + ", Y: " + y + " }";
	}

	public void setStart() {
		start = true;
	}

	public boolean isStart() {
		return start;
	}

	public void setEnd() {
		end = true;
	}

	public boolean isEnd() {
		return end;
	}

	public List<Point> getCollisions() {
		return Collections.unmodifiableList(collisions);
	}

	public void addCollision(Point point) {
		collisions.add(point);
	}

	public boolean hasCollisions() {
		return !collisions.isEmpty();
	}
}