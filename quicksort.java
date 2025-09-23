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
