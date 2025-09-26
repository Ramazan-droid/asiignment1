# Assignment 1

## Implemented algorithms
We implemented 4 algorithms:
1. Merge Sort
2. Quick Sort
3. Deterministic Select (Median-of-Medians)
4. Closest Pair of Points (2D)


## Code Listing
### Merge Sort
```java
public class mergersort {

    public static void main(String[] args) {
        int[] arr = {5, 2, 8, 4, 1, 7, 3, 6, 9};
        mergesort(arr);

        System.out.println("Sorted array:");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }

    public static void mergesort(int[] arr) {
        if (arr.length <= 1) {
            return;
        }

        int[] buffer = new int[arr.length]; // reusable buffer
        mergesortHelper(arr, 0, arr.length - 1, buffer);
    }

    private static void mergesortHelper(int[] arr, int left, int right, int[] buffer) {
        if (left >= right) {
            return;
        }

        int cutoff = 10; // for small arrays, use insertion sort
        if (right - left + 1 <= cutoff) {
            insertionSort(arr, left, right);
            return;
        }

        int mid = left + (right - left) / 2;
        mergesortHelper(arr, left, mid, buffer);
        mergesortHelper(arr, mid + 1, right, buffer);
        merge(arr, left, mid, right, buffer);
    }

    private static void merge(int[] arr, int left, int mid, int right, int[] buffer) {
        int i = left, j = mid + 1, k = left;

        // Merge the two halves into buffer
        while (i <= mid && j <= right) {
            if (arr[i] <= arr[j]) {
                buffer[k++] = arr[i++];
            } else {
                buffer[k++] = arr[j++];
            }
        }

        // Copy any remaining elements from left half
        while (i <= mid) {
            buffer[k++] = arr[i++];
        }

        // Copy any remaining elements from right half
        while (j <= right) {
            buffer[k++] = arr[j++];
        }

        // Copy back from buffer to original array
        for (int p = left; p <= right; p++) {
            arr[p] = buffer[p];
        }
    }

    private static void insertionSort(int[] arr, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= left && arr[j] > key) {
                arr[j + 1] = arr[j];
                j--;
            }
            arr[j + 1] = key;
        }
    }

}
```
### Quick Sort
```java
import java.util.Random;

public class quicksort {

    private static Random rand = new Random();

    public static void main(String[] args) {
        int[] arr = {9, 3, 7, 1, 5, 4, 8};
        System.out.print("Original array: ");
        for (int num : arr) System.out.print(num + " ");
        System.out.println();

        quickSort(arr, 0, arr.length - 1);

        System.out.print("Sorted array: ");
        for (int num : arr) System.out.print(num + " ");
    }

    private static void quickSort(int[] arr, int low, int high) {
        while (low < high) {
            // Partition with random pivot
            int pivotIndex = randomizedPartition(arr, low, high);

            // Determine which side is smaller
            if (pivotIndex - low < high - pivotIndex) {
                // Recurse on smaller left side
                quickSort(arr, low, pivotIndex - 1);
                // Iterate on the larger right side
                low = pivotIndex + 1;
            } else {
                // Recurse on smaller right side
                quickSort(arr, pivotIndex + 1, high);
                // Iterate on the larger left side
                high = pivotIndex - 1;
            }
        }
    }

    private static int randomizedPartition(int[] arr, int low, int high) {
        int pivotIndex = low + rand.nextInt(high - low + 1);
        swap(arr, pivotIndex, high);
        return partition(arr, low, high);
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }

        swap(arr, i + 1, high);
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
```
### Deterministic select
```java
import java.util.Arrays;

public class DeterministicSelect {
    public static int deterministicSelect(int[] arr, int k) {
        return select(arr, 0, arr.length - 1, k - 1);
    }

    private static int select(int[] arr, int left, int right, int k) {
        if (left == right) return arr[left];
        int pivot = medianOfMedians(arr, left, right);
        int pivotIndex = partition(arr, left, right, pivot);
        if (k == pivotIndex) return arr[k];
        else if (k < pivotIndex) return select(arr, left, pivotIndex - 1, k);
        else return select(arr, pivotIndex + 1, right, k);
    }

    private static int partition(int[] arr, int left, int right, int pivot) {
        int pivotIndex = left;
        for (int i = left; i <= right; i++) if (arr[i] == pivot) { pivotIndex = i; break; }
        swap(arr, pivotIndex, right);
        int storeIndex = left;
        for (int i = left; i < right; i++) {
            if (arr[i] < pivot) {
                swap(arr, storeIndex, i);
                storeIndex++;
            }
        }
        swap(arr, storeIndex, right);
        return storeIndex;
    }

    private static int medianOfMedians(int[] arr, int left, int right) {
        int n = right - left + 1;
        if (n <= 5) {
            Arrays.sort(arr, left, right + 1);
            return arr[left + n / 2];
        }
        int numMedians = (int) Math.ceil(n / 5.0);
        int[] medians = new int[numMedians];
        for (int i = 0; i < numMedians; i++) {
            int subLeft = left + i * 5;
            int subRight = Math.min(subLeft + 4, right);
            Arrays.sort(arr, subLeft, subRight + 1);
            medians[i] = arr[subLeft + (subRight - subLeft) / 2];
        }
        return select(medians, 0, medians.length - 1, medians.length / 2);
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i]; arr[i] = arr[j]; arr[j] = temp;
    }
}
```
### Closest pair of points
```java
import java.util.Arrays;
import java.util.Comparator;

public class ClosestPairOfPoints {
    static class Point { double x, y; Point(double x, double y) { this.x = x; this.y = y; } }
    private static double distance(Point p1, Point p2) { return Math.hypot(p1.x - p2.x, p1.y - p2.y); }
    private static double bruteForce(Point[] points, int left, int right) {
        double minDist = Double.MAX_VALUE;
        for (int i = left; i <= right; i++)
            for (int j = i + 1; j <= right; j++)
                minDist = Math.min(minDist, distance(points[i], points[j]));
        return minDist;
    }
    private static double stripClosest(Point[] strip, int size, double d) {
        double min = d;
        Arrays.sort(strip, 0, size, Comparator.comparingDouble(p -> p.y));
        for (int i = 0; i < size; i++)
            for (int j = i + 1; j < size && (strip[j].y - strip[i].y) < min; j++)
                min = Math.min(min, distance(strip[i], strip[j]));
        return min;
    }
    private static double closestUtil(Point[] pointsSortedX, int left, int right) {
        if (right - left <= 3) return bruteForce(pointsSortedX, left, right);
        int mid = left + (right - left) / 2;
        Point midPoint = pointsSortedX[mid];
        double dl = closestUtil(pointsSortedX, left, mid);
        double dr = closestUtil(pointsSortedX, mid + 1, right);
        double d = Math.min(dl, dr);
        Point[] strip = new Point[right - left + 1];
        int j = 0;
        for (int i = left; i <= right; i++) if (Math.abs(pointsSortedX[i].x - midPoint.x) < d) strip[j++] = pointsSortedX[i];
        return Math.min(d, stripClosest(strip, j, d));
    }
    public static double closest(Point[] points) {
        Point[] pointsSortedX = points.clone();
        Arrays.sort(pointsSortedX, Comparator.comparingDouble(p -> p.x));
        return closestUtil(pointsSortedX, 0, pointsSortedX.length - 1);
    }
}
```
  
# Architecture Notes

**Merge Sort**: Reusable buffer, insertion sort for small arrays, recursion depth O(log n).

**Quick Sort**: Randomized pivot, recurse smaller side, iterate larger side; average recursion depth O(log n).

**Deterministic Select**: Groups of 5, median-of-medians pivot, recursion depth O(log n).

**Closest Pair**: Divide-and-conquer, strip array for points near dividing line, recursion depth O(log n).

## 4. Recurrence Analysis

| Algorithm | Recurrence | Θ Complexity |
|-----------|------------|--------------|
| Merge Sort | T(n) = 2T(n/2) + O(n) | Θ(n log n) |
| Quick Sort (average) | T(n) = 2T(n/2) + O(n) | Θ(n log n) |
| Quick Sort (worst) | T(n) = T(n-1) + O(n) | Θ(n²) |
| Deterministic Select | T(n) = T(n/5) + T(7n/10) + O(n) | Θ(n) |
| Closest Pair | T(n) = 2T(n/2) + O(n) | Θ(n log n) |


##Speed analysis
![Graph Image](https://github.com/Ramazan-droid/asiignment1/raw/main/graphhh.jpg)


## 7. Summary

- Algorithms performed as expected according to theoretical analysis.
- Deterministic Select guarantees linear time, unlike Quick Sort.
- Closest Pair efficiently finds nearest points in O(n log n).
- Merge Sort and Quick Sort demonstrate classic divide-and-conquer efficiency.
