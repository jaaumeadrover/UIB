/*
CLASSE LLISTA_ALUMNES.
FUNCIONALITAT: llista on sortiran tots els nombres dels estudiants que no estan
repetits, els quals tindran una referència en un objecte Assignatura.
 */
package Estudiant;

import Asignatures.Assignatura;
import Asignatures.LlistaAsignatures;
import Cursos.Curs;
import Interfaces.Interf_Lista;
import java.util.ArrayList;

/**
 * AUTOR: DATA CREACIÓ:12/11/2021
 */
public class LlistaEstudiants implements Interf_Lista {

    private ArrayList<Estudiant> llista;

    public LlistaEstudiants() {
        llista = new ArrayList();
    }

    @Override
    public int getSize() {
        return llista.size();
    }

    @Override
    public void afegeixElement(Object obj) {
        if (this.isRepetit(obj) == false) {
            llista.add((Estudiant) obj);
            ordenaLlista();
        } else {
            System.out.println("Element ja introduït dins la llista.");
        }
    }

    @Override
    public void eliminaElement(int n) {
        llista.remove((Estudiant) this.getElement(n));
    }

    @Override
    public Object getElement(int n) {
        return llista.get(n);
    }

    @Override
    public void ordenaLlista() {
        //Algoritme ordenació per nom
        final int N = llista.size();
        int jmin;
        Estudiant min;

        for (int i = 0; i < N - 1; i++) {
            min = llista.get(i);
            jmin = i;
            for (int j = i + 1; j < N; j++) {
                if (llista.get(j).compareTo(min) < 0) {
                    min = llista.get(j);
                    jmin = j;
                }
            }
            llista.set(jmin, llista.get(i));
            llista.set(i, min);
        }
    }

    @Override
    public boolean isRepetit(Object obj) {
        for (int i = 0; i < llista.size(); i++) {
            Estudiant e = (Estudiant) obj;
            if ((llista.get(i).getNom().equals(e.getNom())) && (llista.get(i).getCodi().equals(e.getCodi()))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String[] nomsToArr() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

        for (int i = 0; i < llista.size(); i++) {
            if (llista.get(i).equals((Estudiant) e)) {
                return e;
            }
        }
        //si no troba l'objecte, retorna null
        return null;

    }

    public Estudiant cercaElement(Estudiant e) {

        for (int i = 0; i < llista.size(); i++) {
            if ((llista.get(i).getNom().equals(e.getNom())) && (llista.get(i).getCodi().equals(e.getCodi()))) {
                return llista.get(i);
            }
        }
        //si no troba l'objecte, retorna null
        return null;

    }

    public void eliminaAssignatura(Assignatura a) {
        for (int i = 0; i < this.getSize(); i++) {
            Estudiant e = (Estudiant) this.getElement(i);
            for (int j = 0; j < e.getAssignAssociades().getSize(); j++) {
                Assignatura b = (Assignatura) e.getAssignAssociades().getElement(j);
                if (a.equals(b)) {
                    e.getAssignAssociades().eliminaElement(j);
                }
            }
        }
    }

    public void eliminaAssignatures(Curs c) {
        LlistaAsignatures lA = c.getAsignatures();
        int j;

        for (int i = 0; i < this.getSize(); i++) {
            Estudiant e = (Estudiant) this.getElement(i);
            for (j = 0; j < e.getAssignAssociades().getSize(); j++) {
                Assignatura a = (Assignatura) e.getAssignAssociades().getElement(j);
                for (int k = 0; k < lA.getSize(); k++) {
                    if (a == lA.getElementA(k)) {
                        e.getAssignAssociades().eliminaElement(j);
                        j--;
                    }
                }
            }

        }
    }

}
