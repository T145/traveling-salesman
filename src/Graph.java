import java.util.ArrayList;
import java.util.List;

public enum Graph {

    TRICKY_TRAPEZOID(
            2, 4,
            4, 4,
            6, 4,
            3, 1,
            5, 1
    ),
    SIMPLE_GRAPH(
            1, 1,
            2, 3,
            3, 5,
            4, 3,
            5, 5,
            6, 1,
            7, 6
    ),
    DIAGONAL_LINE(
            1, 1,
            2, 2,
            3, 3,
            4, 4,
            5, 5,
            6, 6,
            7, 7
    ),
    HORIZONTAL_LINE(
            1, 1,
            2, 1,
            3, 1,
            4, 1,
            5, 1,
            6, 1,
            7, 1,
            8, 1
    ),
    SQUARE(
            1, 1,
            5, 5,
            1, 5,
            5, 1
    ),
    SQUARE_WITH_CENTER(
            1, 1,
            5, 5,
            1, 5,
            5, 1,
            3, 3
    ),
    RHOMBUS(
            2, 2,
            3, 5,
            4, 3,
            5, 6
    ),
    OSCAR(
            0, 0,
            1, 1000,
            2, 0,
            3, 1000
    ),
    GREYBEARD(
            0, 0,
            1, 99,
            2, 98,
            3, 3,
            4, 4,
            5, 95
    ),
    RANDOM_ONLINE_EXAMPLE(
            17, 19,
            18, 18,
            19, 17,
            20, 15,
            20, 16,
            21, 14,
            22, 13,
            23, 13,
            24, 12,
            25, 12,
            26, 12,
            27, 12,
            28, 12,
            29, 12,
            30, 12,
            31, 12,
            31, 19,
            32, 12,
            32, 19,
            33, 12,
            33, 19,
            34, 13,
            34, 19,
            35, 14,
            35, 18,
            36, 15,
            36, 16,
            36, 17
    );

    private final List<Point> points = new ArrayList<>();

    Graph(double... coords) {
        // even index is an x value, odd is y
        double x = 0.0D;

        for (int i = 0; i < coords.length; ++i) {
            if (i % 2 == 0) {
                x = coords[i];
            } else {
                points.add(new Point(x, coords[i]));
            }
        }
    }

    public List<Point> getPoints() {
        return points;
    }
}