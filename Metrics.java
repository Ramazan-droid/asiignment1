public class Metrics {
    public static int comparisons = 0;  // count comparisons

    public static void reset() {
        comparisons = 0;  // reset to 0
    }

    public static void increment() {
        comparisons++;    // increase by 1
    }
}
