public enum Graphs {

    TRICKY_TRAPEZOID(new double[][]{{2, 4}, {4, 4}, {6, 4}, {3, 1}, {5, 1}}),
    SIMPLE_GRAPH(new double[][]{{1, 1}, {2, 3}, {3, 5}, {4, 3}, {5, 5}, {6, 1}, {7, 6}}),
    LINE(new double[][]{{1, 1}, {2, 2}, {3, 3}, {4, 4}, {5, 5}, {6, 6}, {7, 7}}),
    FLAT_LINE(new double[][]{{1, 1}, {2, 1}, {3, 1}, {4, 1}, {5, 1}, {6, 1}, {7, 1}, {8, 1}}),
    SQUARE(new double[][]{{1, 1}, {5, 5}, {1, 5}, {5, 1}}),
    SQUARE_WITH_CENTER(new double[][]{{1, 1}, {5, 5}, {1, 5}, {5, 1}, {3, 3}}),
    RHOMBUS(new double[][]{{2, 2}, {3, 5}, {4, 3}, {5, 6}}),
    // user-created problems
    OSCAR_DILEMA(new double[][]{{0, 0}, {1, 1000}, {2, 0}, {3, 1000}}),
    GREYBEARD(new double[][]{{0, 0}, {1, 99}, {2, 98}, {3, 3}, {4, 4}, {5, 95}});

    private final double[][] graph;

    Graphs(double[][] graph) {
        this.graph = graph;
    }

    public double[][] getGraph() {
        return graph;
    }
}
