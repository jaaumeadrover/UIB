/*
CLASSE LlistaCursos.
FUNCIONALITAT: classe que ens permet l'enumeració de objectes Curs mitjançant
punters.
 */
package Cursos;

import Asignatures.Assignatura;
import Interfaces.Interf_Lista;
import java.util.ArrayList;

/**
 * AUTOR: Joan Balaguer,Jaume Adrover DATA CREACIÓ: 11/11/2021.
 */
public class LlistaCursos implements Interf_Lista {

    private ArrayList<Curs> llista;

    public LlistaCursos() {
        llista = new ArrayList();
    }

    /*
    MÈTODES GETTERS
     */
    @Override
    public int getSize() {
        return llista.size();
    }

    @Override
    public Object getElement(int n) {
        return llista.get(n);
    }
    
    public Curs getElementC(int n) {
        return (Curs) llista.get(n);
    }

    @Override
    public boolean isRepetit(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /*
    MÈTODES VOID
     */
    @Override
    public void afegeixElement(Object obj) {
        //afegir element if no repe
        llista.add((Curs) obj);
    }

    @Override
    public void eliminaElement(int n) {
        llista.remove((Curs)this.getElement(n));
    }

    @Override
    public void ordenaLlista() {
        //Algoritme ordenació per nom
        final int N = llista.size();
        int jmin;
        Curs min;

        for (int i = 0; i < N - 1; i++) {
            min = llista.get(i);
            jmin = i;
            for (int j = i + 1; j < N; j++) {
                if (llista.get(j).compareTo(min) == -1) {
                    min = llista.get(j);
                    jmin = j;
                }
            }
            llista.set(jmin, llista.get(i));
            llista.set(i, min);
        }
    }

    /*
    MÈTODE TOSTRING
    */
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < llista.size(); i++) {
            s += llista.get(i).toString();
            s += "\n";
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
