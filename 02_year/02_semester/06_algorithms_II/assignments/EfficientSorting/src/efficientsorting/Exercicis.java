/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package efficientsorting;

/**
 *
 * @author Jaume
 */
public class Exercicis {
    public static void main (String[] args){
        problem1();
    }
    public static void problem1(){
        int [] arr={0,1,0,1,0,1,1,0,0,1,0,0,0,0,1,1,1};
        mostraArray(arr);
        ordenaArrayBinari(arr);
        mostraArray(arr);
        
    }
    private static void ordenaArrayBinari(int[] x){
        Sorting.selectionSort(x);
    }
    private static void mostraArray(int [] arr){
            for (int i = 0; i < arr.length; i++) {
                System.out.print(arr[i]+" ");
            }
            System.out.println("");
    }
    public static void problem2(){
        
    }
}
