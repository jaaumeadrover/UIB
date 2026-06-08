/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package efficientsorting;

/**
 *
 * @author xisca
 * @param <T>
 */

// La classe té una variable de tipus T (genèric)
public class SortingGeneric <T extends Comparable<T>> {
    
    private void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    private T medianOf3(T[] arr, int low, int high) {
        int middle = (high + low)/2;
        
        if (arr[low].compareTo(arr[middle]) > 0 ) {
            swap(arr, low, middle);
        }
        if (arr[high].compareTo(arr[low]) < 0 ) {
            swap(arr, low, high);
        }
        if (arr[middle].compareTo(arr[high]) < 0 ) {
            swap(arr, middle, high);
        }
        
        return arr[high];
        
    }
    
    private int partition(T[] arr, int low, int high) {
        T pivot = medianOf3(arr, low, high);
        
        int split = low;
        
        for (int j = low; j <= high - 1; j++) {
            
            if (arr[j].compareTo(pivot) < 0) {
                swap(arr, split, j);
                split++;
            }
        
        }
        
        swap(arr, split, high);
        
        return split;
    }

    public void quickSort(T[] arr, int low, int high) {
        if (low < high) {

            int split = partition(arr, low, high);

            quickSort(arr, low, split - 1);
            quickSort(arr, split + 1, high);
        }
    }
    
    public void mergeSort (T[] arr, int l, int r) {
        if (l<r) {
            int m = (l+r)/2;
            mergeSort(arr, l, m);
            mergeSort(arr, m+1, r);
            merge(arr, l, m , r);
        }
    }
    
    private void merge(T[] arr, int l, int m, int r) {
        T lArr[] = (T[])new Comparable[m-l+1];
        T rArr[] = (T[])new Comparable[r-m];
        
        // Copy
        for (int i = 0; i<lArr.length; i++) {
            lArr[i] = arr[l+i];
        }

        for (int j = 0; j<rArr.length; j++) {
            rArr[j] = arr[m+1+j];
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
