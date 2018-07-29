import java.io.PrintStream;
import java.util.*;

public class TravelingSalesman {

    private static int getNextIndex(List<Point> points, int start) {
        return start == points.size() - 1 ? 0 : start + 1;
    }

    private static Point getNextPoint(List<Point> points, int start) {
        return points.get(getNextIndex(points, start));
    }

    private static double getTotalDistance(List<Point> solution) {
        double shortestDist = 0;

        for (int t = 0; t < solution.size(); ++t) {
            shortestDist += solution.get(t).getDistance(getNextPoint(solution, t));
        }

        return shortestDist;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        PrintStream out = System.out;

        out.println("Welcome! Please select a graph to use:");

        for (int t = 0; t < Graph.values().length; ++t) {
            out.println("(" + t + ") : " + Graph.values()[t].name());
        }

        // O(n)
        // build the graph in terms of our point
        List<Point> points = Graph.values()[in.nextInt()].getPoints();
        int initialSize = points.size();

        // O(n)
        // SWAP X & Y to properly sort the points
        for (Point point : points) {
            point.swapCoords();
        }

        // O(nlgn)
        // sort the graph first to make finding duplicates easier
        Collections.sort(points);

        ArrayDeque<Point> wreck = new ArrayDeque<>();

        // O(n)
        // determine if the graph has any collisions
        for (int t = 0; t < points.size(); ++t) {
            Point curr = points.get(t);
            Point next = getNextPoint(points, t);

            if (curr.equals(next)) {
                // avoid O(n) remove
                points.remove(getNextIndex(points, t));
            } else if (curr.getX() == next.getX()) {
                wreck.add(next.swapCoords());
                points.remove(getNextIndex(points, t));
            }

            points.set(t, curr.swapCoords());
        }

        System.out.println('\n' + " --- INPUT GRAPH ---");
        for (Point point : points) {
            System.out.println(point);
        }

        System.out.println('\n' + " --- COLLISIONS ---");
        if (wreck.isEmpty()) {
            System.out.println("nil");
        } else {
            for (Point point : wreck) {
                System.out.println(point);
            }
        }

        List<Point> temp;

        // O(n)
        // determine the shortest possible circuit
        for (int t = 0; t < points.size(); ++t) {
            temp = new ArrayList<>(points);
            Point curr = points.get(t);

            Collections.swap(temp, t, t == 0 ? points.size() - 1 : t - 1);

            double currDist = curr.getDistance(getNextPoint(points, t));
            double tempDist = curr.getDistance(getNextPoint(temp, t));

            if (tempDist < currDist) {
                points = new ArrayList<>(temp);
            }
        }

        // O(n^3)
        // fit in collisions if we have any
        if (!wreck.isEmpty()) {
            Map<Double, Integer> distances = new HashMap<>(points.size(), 1F);

            while (!wreck.isEmpty()) {
                Point c = wreck.remove();

                for (int t = 0; t < points.size(); ++t) {
                    temp = new ArrayList<>(points);
                    temp.add(t, c);
                    distances.put(getTotalDistance(temp), t);
                }

                points.add(distances.get(Collections.min(distances.keySet())), c);
                distances.clear();
            }
        }

        System.out.println('\n' + " --- RESULT ---");

        for (Point point : points) {
            System.out.println(point);
        }

        System.out.println('\n' + " --- VERIFICATION ---");
        System.out.println("Initial Size:\t" + initialSize);
        System.out.println("Final Size:\t" + points.size());
        System.out.println("VERIFIED: " + (points.size() == initialSize));

        System.out.println('\n' + " --- FINAL PHASE ---");
        System.out.println("SOLUTION: " + getTotalDistance(points));
    }
}