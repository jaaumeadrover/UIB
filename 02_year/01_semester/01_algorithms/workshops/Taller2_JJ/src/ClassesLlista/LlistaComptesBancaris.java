/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesLlista;

import ClassesCompte.CompteBancari;

/**
 *
 * @author Jaume A
 */
public class LlistaComptesBancaris {

    protected CompteBancari primer;

    public LlistaComptesBancaris() {
        primer = new CompteBancari();
    }

    public CompteBancari getCompte(int i) {
        CompteBancari compt = primer;
        for (int j = 0; j < i; j++) {
            compt = compt.getNodeSeg();
        }
        return compt;

    }

    public void insereixAlFinal(CompteBancari n) {
        CompteBancari element = this.primer;
        while ((element.getNodeSeg() != null)) {
            element = element.getNodeSeg();
        }
        //n.setNodeSeg(null);

        element.setNodeSeg(n);

    }
    
    
    public void baixa(int n){
        try{
        CompteBancari element = this.primer, eliminat;
        
        for(int i = 0; i < (n-1); i++){
            element = element.getNodeSeg();
        }
        eliminat = element.getNodeSeg();
        element.setNodeSeg(element.getNodeSeg().getNodeSeg());
        eliminat.setNodeSeg(null);
        
        }catch(NullPointerException e){
            System.out.println("No s'ha trobat");
        }
        
    }

}
