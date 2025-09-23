import java.util.Arrays;

public class deterministicselect {

    public static void main(String[] args) {
        int[] arr = {12, 3, 5, 7, 4, 19, 26, 23, 1};
        int k = 5; // 4th smallest element
        System.out.println("The " + k + "-th smallest element is " + deterministicSelect(arr, k));
    }

    // Entry method: find k-th smallest element in array arr
    public static int deterministicSelect(int[] arr, int k) {
        return select(arr, 0, arr.length - 1, k - 1); // k-1 because array is 0-indexed
    }

    private static int select(int[] arr, int left, int right, int k) {
        // If the segment has only one element
        if (left == right) return arr[left];

        // Step 1: Divide into groups of 5, find medians
        int pivot = medianOfMedians(arr, left, right);

        // Step 2: Partition around pivot
        int pivotIndex = partition(arr, left, right, pivot);

        // Step 3: Recurse into the side that contains k
        if (k == pivotIndex) {
            return arr[k];
        } else if (k < pivotIndex) {
            return select(arr, left, pivotIndex - 1, k);
        } else {
            return select(arr, pivotIndex + 1, right, k);
        }
    }

    // Partition array around pivot value
    private static int partition(int[] arr, int left, int right, int pivot) {
        // Find pivot index and move it to the end
        int pivotIndex = left;
        for (int i = left; i <= right; i++) {
            if (arr[i] == pivot) {
                pivotIndex = i;
                break;
            }
        }
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

    // Find median of medians
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

        // Recursively find the median of medians
        return select(medians, 0, medians.length - 1, medians.length / 2);
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
