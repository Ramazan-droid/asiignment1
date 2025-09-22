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
