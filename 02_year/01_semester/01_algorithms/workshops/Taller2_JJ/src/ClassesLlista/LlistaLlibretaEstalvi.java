/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesLlista;

import ClassesCompte.CompteBancari;
import ClassesCompte.CompteCorrent;
import ClassesCompte.LlibretaEstalvi;

/**
 *
 * @author Jaume A
 */
public class LlistaLlibretaEstalvi extends LlistaComptesBancaris {

    public LlistaLlibretaEstalvi(int ncompte, double saldo) {
        primer = new LlibretaEstalvi(ncompte, saldo);
    }

    public LlibretaEstalvi getLlibreta(int i){
        LlibretaEstalvi ll = (LlibretaEstalvi)primer;
        for (int j = 0; j < i; j++) {
            ll = (LlibretaEstalvi) ll.getNodeSeg();
        }
        return ll;
    }
    
    @Override
    public String toString() {
        String s = "";
        int n = 1;
        try {
            LlibretaEstalvi aux = (LlibretaEstalvi) primer;
            while (aux.getNodeSeg() != null) {
                aux = (LlibretaEstalvi) aux.getNodeSeg();
                s += n +"->";
                s += aux + "\n";
                n++;
            }
            return s;
        } catch (Exception e) {
            return s;
        }
    }
}
