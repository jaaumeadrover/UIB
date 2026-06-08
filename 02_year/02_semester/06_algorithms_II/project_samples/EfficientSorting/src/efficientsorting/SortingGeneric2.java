/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package efficientsorting;

/**
 *
 * @author Jaume
 */
/**
 *
 * @author joanbalaguer
 */
public class SortingGeneric2 {

    public static <T extends Comparable<T>> void mergeSort(T[] arr, int l, int r) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(arr, l, m);
            mergeSort(arr, m + 1, r);
            merge(arr, l, m, r);
        }
    }

    // merges two subarrays of array[].
    private static <T extends Comparable<T>> void merge(T[] arr, int l, int m, int r) {
        // Crear els subarrays lArr i rArr
        T lArr[] = (T[]) new Comparable[m - l + 1];
        T rArr[] = (T[]) new Comparable[r - m];

        // Emplenar els subarrays lArr i rArr (a partir de arr) 
        for (int i = 0; i < lArr.length; i++) {
            lArr[i] = arr[l + i];
        }
        for (int j = 0; j < rArr.length; j++) {
            rArr[j] = arr[m + 1 + j];
        }

        // Merge
        int i = 0, j = 0;
        int k = l;
        while (i < lArr.length && j < rArr.length) {
            if (lArr[i].compareTo(rArr[j]) < 0) {
                arr[k] = lArr[i];
                i++;
            } else {
                arr[k] = rArr[j];
                j++;
            }
            k++;
        }

        // Elements restants
        while (i < lArr.length) {
            arr[k] = lArr[i];
            i++;
            k++;
        }
        while (j < rArr.length) {
            arr[k] = rArr[j];
            j++;
            k++;
        }
    }
}
