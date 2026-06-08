/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappings;

import Mappings.BSTMapping.Pair;
import java.util.Iterator;

/**
 *
 * @author Jaume
 */
public class Pruebas {

    public static void main(String[] args) {
        String[] dic={"Arbre","Arbuesto","as","po","op"};
        BSTMapping<Integer, String> bst = new BSTMapping();
        
        for (int i = 0; i < 5; i++) {
            if(bst.put(i,dic[i])==null){
                System.out.println("hola");
            }
        }
        Iterator it=bst.IteratorBSTSetMapping();
        while(it.hasNext()){
            System.out.println(((Pair)it.next()).getValue());
        }
    }
}
