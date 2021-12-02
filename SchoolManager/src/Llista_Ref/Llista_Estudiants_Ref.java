/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Llista_Ref;

import Estudiant.Estudiant;
import Estudiant.LlistaEstudiants;
import Interfaces.Interf_Lista;
import java.util.ArrayList;

/**
 *
 * @author Jaume A
 */
public class Llista_Estudiants_Ref implements Interf_Lista {

    private Node_Ref primer;
    private int contador;

    public Llista_Estudiants_Ref() {
        primer = null;
        contador = 0;
    }

    @Override
    public int getSize() {
        return contador;
    }

    @Override
    public void afegeixElement(Object obj) {

        Node_Ref nou = new Node_Ref(obj, null);
        if (primer != null) {

            Node_Ref index = primer;
            while (index.getNodeSeg() != null) {
                index = index.getNodeSeg();
            }
            index.setNodeSeg(nou);

        } else {

            primer = nou;

        }
        contador++;
        this.ordenaLlista();
    }

    @Override
    public void eliminaElement(int n) {

    }

    @Override
    public Object getElement(int n) {
        Node_Ref node = primer;
        for (int i = 0; i < n; i++) {
            node = node.getNodeSeg();

        }
        return node.getObj();
    }

    @Override
    public void ordenaLlista() {
        LlistaEstudiants lcAux = new LlistaEstudiants();

        for (int i = 0; i < contador; i++) {
            lcAux.afegeixElement(this.getElement(i));

        }
        lcAux.ordenaLlista();

        for (int i = 0; i < contador; i++) {
            this.getNode(i).setObj(lcAux.getElement(i));

        }
    }

    @Override
    public boolean isRepetit(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        String s = "";
        Node_Ref node = primer;
        if (node != null) {

            while (node != null) {
                s += (Estudiant) node.getObj();
                s += '\n';
                node = node.getNodeSeg();
            }
            return s;
        } else {
            return "{}";
        }

    }

    @Override
    public String[] nomsToArr() {
        ArrayList<String> arr = new ArrayList();
        Node_Ref node = primer;
        int i = 0;
        if (node != null) {
            while (node != null) {
                arr.add(node.getObj().toString());
                node = node.getNodeSeg();
            }
            return (String[]) arr.toArray();
        } else {
            return null;//Vacío
        }

    }

    public ArrayList<String> nomsCompletToArrayList() {
        ArrayList<String> arr = new ArrayList();
        Node_Ref node = primer;
        int i = 0;
        if (node != null) {
            while (node != null) {
                arr.add(node.getObj().toString());
                node = node.getNodeSeg();
            }
            return arr;
        } else {
            return null;//Vacío
        }
    }

    public ArrayList<String> nomsToArrayList() {
        ArrayList<String> arr = new ArrayList();
        Node_Ref node = primer;
        int i = 0;
        if (node != null) {
            while (node != null) {
                Estudiant e = (Estudiant) node.getObj();
                arr.add(e.getNom());
                node = node.getNodeSeg();
            }
            return arr;
        } else {
            return null;//Vacío
        }
    }

    private Node_Ref getNode(int n) {
        Node_Ref node = primer;
        for (int i = 0; i < n; i++) {
            node = node.getNodeSeg();

        }
        return node;
    }

    @Override
    public Object cercaElement(Object e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
