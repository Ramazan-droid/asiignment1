import java.util.Arrays;
import java.util.Comparator;

public class closestpairofpints {

    static class Point {
        double x, y;
        Point(double x, double y) {
            this.x = x;
            this.y = y;
        }
    }

    // Compute distance between two points
    private static double distance(Point p1, Point p2) {
        return Math.hypot(p1.x - p2.x, p1.y - p2.y);
    }

    // Brute force for small number of points
    private static double bruteForce(Point[] points, int left, int right) {
        double minDist = Double.MAX_VALUE;
        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                minDist = Math.min(minDist, distance(points[i], points[j]));
            }
        }
        return minDist;
    }

    // Find the smallest distance in the strip
    private static double stripClosest(Point[] strip, int size, double d) {
        double min = d;

        Arrays.sort(strip, 0, size, Comparator.comparingDouble(p -> p.y));

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size && (strip[j].y - strip[i].y) < min; j++) {
                min = Math.min(min, distance(strip[i], strip[j]));
            }
        }
        return min;
    }

    // Recursive divide-and-conquer function
    private static double closestUtil(Point[] pointsSortedX, int left, int right) {
        // Base case: small number of points
        if (right - left <= 3) {
            return bruteForce(pointsSortedX, left, right);
        }

        int mid = left + (right - left) / 2;
        Point midPoint = pointsSortedX[mid];

        // Recursive calls on left and right halves
        double dl = closestUtil(pointsSortedX, left, mid);
        double dr = closestUtil(pointsSortedX, mid + 1, right);
        double d = Math.min(dl, dr);

        // Build strip of points close to mid line
        Point[] strip = new Point[right - left + 1];
        int j = 0;
        for (int i = left; i <= right; i++) {
            if (Math.abs(pointsSortedX[i].x - midPoint.x) < d) {
                strip[j++] = pointsSortedX[i];
            }
        }

        return Math.min(d, stripClosest(strip, j, d));
    }

    public static double closest(Point[] points) {
        Point[] pointsSortedX = points.clone();
        Arrays.sort(pointsSortedX, Comparator.comparingDouble(p -> p.x));
        return closestUtil(pointsSortedX, 0, pointsSortedX.length - 1);
    }

    // Test
    public static void main(String[] args) {
        Point[] points = {
                new Point(2, 3),
                new Point(12, 30),
                new Point(40, 50),
                new Point(5, 1),
                new Point(12, 10),
                new Point(3, 4)
        };

        System.out.println("The smallest distance is " + closest(points));
    }
}
