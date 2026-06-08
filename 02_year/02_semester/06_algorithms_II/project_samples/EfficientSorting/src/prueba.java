
import java.util.Comparator;
import java.util.PriorityQueue;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jaume
 */
public class prueba {

    public static void main(String[] args) {
        heapSort();
    }
    
    
    public static void heapSort(){
        PriorityQueue <Integer>coa=new PriorityQueue<Integer>(new Comparator<Integer>(){
            @Override
            public int compare(Integer a,Integer b){
                return b.compareTo(a);
            }
        });
        int nombres[]={12,150,4,2,200,30};
        
        for (int i = 0; i < nombres.length; i++) {
            coa.add(nombres[i]);
        }
        while(!coa.isEmpty()){
            System.out.println(coa.poll());
        }
    }
}
