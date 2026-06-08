/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package efficientsorting;

/**
 *
 * @author xisca
 */
public class Sorting {
    
    

    // O(n^2)
    public static void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {

            int minIdx = i;

            //Find minimum
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[minIdx] > arr[j]) {
                    minIdx = j;
                }
            }

            //Swap elements
            int min = arr[minIdx];
            arr[minIdx] = arr[i];
            arr[i] = min;
        }
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high];

        int split = low;

        for (int j = low; j <= high - 1; j++) {

            if (arr[j] < pivot) {
                swap(arr, split, j);
                split++;
            }

        }

        swap(arr, split, high);
        return split;
    }
    
    private static int partitionFirst(int[] arr, int low, int high) {
        int pivot = arr[low];
        
        int split = low;
        
        for (int j = low+1; j <= high; j++) {
            
            if (arr[j] < pivot) {
                split++;
                swap(arr, split, j);
            }
            
        }
        
        swap(arr, split, low);
        return split;
    }

    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            //int split = partition(arr, low, high);
            int split = partitionFirst(arr, low, high);

            quickSort(arr, low, split - 1);
            quickSort(arr, split + 1, high);
        }
    }

    public static void mergeSort(int arr[], int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    private static void merge(int[] arr, int l, int m, int r) {
        int lArr[] = new int[m - l + 1];
        int rArr[] = new int[r - m];

        // Copy
        System.out.println("lArr: ");
        for (int i = 0; i < lArr.length; i++) {
            lArr[i] = arr[l + i];
            System.out.print(lArr[i] + ", ");
        }
        System.out.println();
        System.out.println("rArr: ");
        for (int j = 0; j < rArr.length; j++) {
            rArr[j] = arr[m + 1 + j];
            System.out.print(rArr[j] + ", ");
        }
        System.out.println();

        // Merge
        int i = 0, j = 0;
        int k = l;
        while (i < lArr.length && j < rArr.length) {
            if (lArr[i] < rArr[j]) {
                arr[k] = lArr[i];
                i++;
            } else {
                arr[k] = rArr[j];
                j++;
            }
            k++;
        }

        // Remaining lArr
        while (i < lArr.length) {
            arr[k] = lArr[i];
            i++;
            k++;
        }

        // Remaining rArr
        while (j < rArr.length) {
            arr[k] = rArr[j];
            j++;
            k++;
        }
    }

}
