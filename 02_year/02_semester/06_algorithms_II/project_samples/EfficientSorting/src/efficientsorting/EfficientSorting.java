/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package efficientsorting;

import java.util.Random;

/**
 *
 * @author xisca
 */
public class EfficientSorting {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        // Pràctica selection sort (no genèric)
        System.out.println("Pràctica selection sort (no genèric)");
        int n = 10;
        int[] array = generateRandomArray(n);
        printArray(array);
        Sorting.selectionSort(array);
        printArray(array);
        
        // Pràctica quicksort (no genèric)
        System.out.println("Pràctica quicksort (no genèric)");
        array = generateRandomArray(n);
        printArray(array);
        Sorting.quickSort(array, 0, array.length-1);
        printArray(array);
        
        // Pràctica quicksort (genèric): median of 3
        System.out.println("Pràctica quicksort (genèric): median of 3");
        SortingGeneric<Integer> sorting = new SortingGeneric();
        Integer[] arrayInteger = generateRandomArrayInteger(n);
        printArray(arrayInteger);
        sorting.quickSort(arrayInteger, 0, array.length-1);
        printArray(arrayInteger);
        
        
        // Pràctica quicksort: problema 1
        System.out.println("Pràctica quicksort: problema 1");
        int[] arrayBinari = {1, 0, 1, 0, 1, 0, 0, 1};
        problema1(arrayBinari, 0, arrayBinari.length-1);
        printArray(arrayBinari);
        
        // Pràctica quicksort: problema 2
        System.out.println("Pràctica quicksort: problema 2");
        int[] arrayEnters = {9,- 3, 5, -2, -8, -6, 1, 3};
        problema2(arrayEnters, 0, arrayEnters.length-1);
        printArray(arrayEnters);
        
        // Pràctica quicksort: problema 3
        System.out.println("Pràctica quicksort: problema 3");
        problema3(arrayEnters, 0, arrayEnters.length-1);
        printArray(arrayEnters);
      
        // Pràctica quicksort: problema 4
        System.out.println("Pràctica quicksort: problema 4");
        problema4(arrayBinari, 0, arrayBinari.length-1);
        printArray(arrayBinari);
        
        // Pràctica mergesort amb llista enllaçada
        System.out.println("Pràctica mergesort amb llista enllaçada");
        LinkedList<Integer> list = new LinkedList();
        list.add(10);
        list.add(5);
        list.add(70);
        list.add(80);
        list.add(20);
        System.out.println(list);
        list.mergeSort();
        System.out.println(list);
    }
    
    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
    
    private static void problema4(int[] arr, int low, int high) {
        int pivot = 1;
        
        int split = low;
        
        for (int j = low; j <= high - 1; j++) {
            
            if (arr[j] >= pivot) {
                swap(arr, split, j);
                split++;
            }
        
        }
        
        swap(arr, split, high);
    }
    
    private static void problema1(int[] arr, int low, int high) {
        int pivot = 1;
        
        int split = low;
        
        for (int j = low; j <= high - 1; j++) {
            
            if (arr[j] < pivot) {
                swap(arr, split, j);
                split++;
            }
        
        }
        
        swap(arr, split, high);
    }
    
    private static void problema2(int[] arr, int low, int high) {
        int pivot = 0;
        
        int split = low;
        
        for (int j = low; j <= high - 1; j++) {
            
            if (arr[j] < pivot) {
                swap(arr, split, j);
                split++;
            }
        
        }
        
        swap(arr, split, high);
    }
    
    private static void problema3(int[] arr, int low, int high) {
        int pivot = 0;
        
        int split = low;
        
        for (int j = low; j <= high - 1; j++) {
            
            if (arr[j] > pivot) {
                swap(arr, split, j);
                split++;
            }
        
        }
        
        swap(arr, split, high);
    }
    
    public static void printArray(int[] arr) {
        for(int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
    
    public static void printArray(Integer[] arr) {
        for (Integer arr1 : arr) {
            System.out.print(arr1 + " ");
        }
        System.out.println();
    }
    
    public static int[] generateRandomArray(int n) {
        int[] randomA = new int[n];
        Random r = new Random(12345);
        for (int i = 0; i < randomA.length; i++) {
            randomA[i] = r.nextInt(randomA.length*10);
        }
        return randomA;
    }
    
    public static Integer[] generateRandomArrayInteger(int n) {
        Integer[] randomA = new Integer[n];
        Random r = new Random(12345);
        for (int i = 0; i < randomA.length; i++) {
            randomA[i] = r.nextInt(randomA.length*10);
        }
        return randomA;
    }
    
    public static int[] generateOrderedArray(int n) {
        int[] randomA = new int[n];
        for (int i = 0; i < randomA.length; i++) {
            randomA[i] = i;
        }
        return randomA;
    }
    
    public static int[] generateInvertedArray(int n) {
        int[] randomA = new int[n];
        for (int i = 0; i < randomA.length; i++) {
            randomA[i] = n-i-1;
        }
        return randomA;
    }
    
    
}
