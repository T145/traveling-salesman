package T145.travelingsalesman;

public class Point implements Comparable<Point> {

	private double x;
	private double y;

	private Point parent;
	private Point child;

	// distance from the origin point (first point in the map)
	private double totalDist;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public Point swapCoords() {
		double temp = x;
		this.x = y;
		this.y = temp;
		return this;
	}

	public Point getParent() {
		return parent;
	}

	public void setParent(Point parent) {
		this.parent = parent;
	}

	public Point getChild() {
		return child;
	}

	public void setChild(Point child) {
		this.child = child;
	}

	public double getTotalDistance() {
		return totalDist;
	}

	public void setTotalDistance(double totalDist) {
		this.totalDist = totalDist;
	}

	public double getChildDistance() {
		return this.getDistanceTo(child);
	}

	public double getParentDistance() {
		return this.getDistanceTo(parent);
	}

	public double getDistanceTo(Point dest) {
		double distX = dest.getX() - x;
		double distY = dest.getY() - y;
		distX *= distX;
		distY *= distY;
		return Math.sqrt(distX + distY);
	}

	@Override
	public String toString() {
		return "x: " + x + "; y: " + y;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Point) {
			Point point = (Point) obj;
			return point.x == x && point.y == y;
		}

		return false;
	}

	@Override
	public int compareTo(Point other) {
		int result = Double.compare(x, other.x);

		if (result == 0) {
			result = Double.compare(y, other.y);
		}

		return result;
	}
}