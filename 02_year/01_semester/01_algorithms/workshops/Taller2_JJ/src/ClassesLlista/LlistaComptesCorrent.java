/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesLlista;

import ClassesCompte.CompteCorrent;

/**
 *
 * @author Jaume A
 */
public class LlistaComptesCorrent extends LlistaComptesBancaris {

    public LlistaComptesCorrent(int ncompte, double saldo) {
        primer = new CompteCorrent(ncompte, saldo);
    }
    
    public CompteCorrent getCompteCorrent(int i){
        CompteCorrent c = (CompteCorrent)primer;
        for (int j = 0; j < i; j++) {
            c = (CompteCorrent) c.getNodeSeg();
        }
        return c;
    }

    @Override
    public String toString() {
        String s = "";
        int n=1;
        try {
            CompteCorrent aux = (CompteCorrent) primer;
            while (aux.getNodeSeg() != null) {
                aux = (CompteCorrent) aux.getNodeSeg();
                s+=n + "->";
                s += aux + "\n";
                n++;
            }
            return s;
        } catch (Exception e) {
            return s;
        }
    }
}
