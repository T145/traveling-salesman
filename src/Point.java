
public class Point implements Comparable<Point> {

    private double x;
    private double y;

    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    double getX() {
        return x;
    }

    private double getY() {
        return y;
    }

    double getDistance(Point dest) {
        double distX = dest.getX() - x;
        double distY = dest.getY() - y;
        distX *= distX;
        distY *= distY;
        return Math.sqrt(distX + distY);
    }

    Point swapCoords() {
        double temp = x;
        this.x = y;
        this.y = temp;
        return this;
    }

    @Override
    public String toString() {
        return " x: " + x + "; y: " + y;
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
    public int compareTo(Point point) {
        int result = Double.compare(x, point.x);

        if (result == 0) {
            result = Double.compare(y, point.y);
        }

        return result;
    }
}