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

        for (int t = 0; t < Graphs.values().length; ++t) {
            out.println("(" + t + ") : " + Graphs.values()[t].name());
        }

        double[][] graph = Graphs.values()[in.nextInt()].getGraph();
        long startTime = System.currentTimeMillis();

        if (graph.length == 0) {
            out.println("SOLUTION: 0");
            return;
        }

        // O(n)
        // build the graph in terms of our point
        List<Point> points = new ArrayList<>(graph.length);

        System.out.println("Input Graph: ");
        for (double[] dot : graph) {
            points.add(new Point(dot[1], dot[0]));
            System.out.println("x: " + dot[0] + "; y: " + dot[1]);
        }

        // NOTE: X & Y coords are swapped

        // O(nlgn)
        // sort the graph first to make finding duplicates easier
        Collections.sort(points);

        // O(n)
        // determine if the graph has any collisions
        //List<Point> wreck = new ArrayList<>();
        ArrayDeque<Point> wreck = new ArrayDeque<>();

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

        // O(n)
        // determine the shortest possible circuit
        List<Point> temp;

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

        if (!wreck.isEmpty()) {
            Map<Double, Integer> distances = new HashMap<>(points.size(), 1F);

            // O(n^3)
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
        System.out.println("Graph Length:\t" + graph.length);
        System.out.println("Solution Size:\t" + points.size());
        System.out.println("VERIFIED: " + (points.size() == graph.length));
        System.out.println('\n' + " --- FINAL PHASE ---");
        System.out.println("SOLUTION: " + getTotalDistance(points));
        System.out.println("Runtime: " + (System.currentTimeMillis() - startTime) + " ms");
    }
}