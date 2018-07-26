import java.util.Random;

public class Reference {

    public static double[][] getRandomIntegerGraph(int maxSize) {
        double[][] graph = new double[maxSize][maxSize];
        Random rand = new Random();

        for (int t = 0; t < maxSize; ++t) {
            for (int s = 0; s < maxSize; ++s) {
                graph[t][s] = rand.nextInt(maxSize);
            }
        }

        return graph;
    }

    public static double[][] getRandomDoubleGraph(int maxSize) {
        double[][] graph = new double[maxSize][maxSize];
        Random rand = new Random();

        for (int t = 0; t < maxSize; ++t) {
            for (int s = 0; s < maxSize; ++s) {
                graph[t][s] = rand.nextDouble();
            }
        }

        return graph;
    }
}