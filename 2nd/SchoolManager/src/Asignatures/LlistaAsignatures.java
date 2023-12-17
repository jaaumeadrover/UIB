/*
CLASSE LLISTAASSIGNATURES.
FUNCIONALITAT: utilitzam mitjançant arrays la enumeració de assignatures.
 */
package Asignatures;

import Interfaces.Interf_Lista;
import java.util.ArrayList;

/**
AUTOR: Joan Balaguer, Jaume Adrover
DATA CREACIÓ: 12/11/2021
 */
public class LlistaAsignatures implements Interf_Lista{
    private ArrayList<Assignatura> llista;
    
    public LlistaAsignatures(){
        llista=new ArrayList();
    }

    @Override
    public int getSize() {
        return llista.size();
    }

    @Override
    public void afegeixElement(Object obj) {
        //if(norepe)
        if(this.isRepetit(obj)==false){
            llista.add((Assignatura) obj);
            this.ordenaLlista();
        }else{
            System.out.println("Element ja introduït dins la llista!");
        }

    }

    @Override
    public void eliminaElement(int n) {
        llista.remove((Assignatura)this.getElement(n));
    }
 
    @Override
    public Object getElement(int n) {
        return llista.get(n);
    }
    
    public Assignatura getElementA(int n) {
        return llista.get(n);
    }

    @Override
    public void ordenaLlista() {
        //Algoritme ordenació per nom
        final int N = llista.size();
        int jmin;
        Assignatura min;

        for (int i = 0; i < N - 1; i++) {
            min = llista.get(i);
            jmin = i;
            for (int j = i + 1; j < N; j++) {
                if (llista.get(j).compareTo(min)<0) {
                    min = llista.get(j);
                    jmin = j;
                }
            }
            llista.set(jmin, llista.get(i));
            llista.set(i,min);
        }
    }

    /*
    Mètode No repetit per a un arrayList.
    Comparar només String????
    */
    @Override
    public boolean isRepetit(Object obj) {
        for (int i = 0; i < llista.size(); i++) {
            if(llista.get(i).equals(obj)){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString(){
        String s="";
        for (int i = 0; i < llista.size(); i++) {
            s+=llista.get(i);
            s+="\n";
        }
        return s;
    }
    
        
    public String toStringMAX(){
        String s="";
        for (int i = 0; i < llista.size(); i++) {
            s+=llista.get(i).toStringMAX();
            s+="\n";
        }
        return s;
    }

    @Override
    public String[] nomsToArr() {
        
        String [] arr = new String[llista.size()];
        for (int j = 0; j < llista.size(); j++) {
            arr[j] = llista.get(j).getNom();   
        }
        
        return arr;
    }

    @Override
    public ArrayList<String> nomsCompletToArrayList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> nomsToArrayList() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object cercaElement(Object e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    

}
