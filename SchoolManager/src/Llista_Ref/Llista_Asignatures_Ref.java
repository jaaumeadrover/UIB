/*
CLASSE LLISTA_ASIGNATURES_REF.
FUNCIONALITAT: Llista que tindrà cada objecte estudiant, que siran nodes(referència) 
a las assignatures originals.
 */
package Llista_Ref;

import Asignatures.Assignatura;
import Interfaces.Interf_Lista;
import java.util.ArrayList;

/**
 * AUTOR: DATA CREACIÓ: 12/11/2021
 */
public class Llista_Asignatures_Ref implements Interf_Lista {

    private Node_Ref primer;
    private int contador;

    public Llista_Asignatures_Ref() {
        primer = null;
        contador = 0;
    }

    @Override
    public int getSize() {
        return contador;
    }

    @Override
    public void afegeixElement(Object obj) {
        Node_Ref nuevo = new Node_Ref(obj, null);
        if (primer != null) {

            Node_Ref index = primer;
            while (index.getNodeSeg() != null) {
                index = index.getNodeSeg();
            }
            index.setNodeSeg(nuevo);

        } else {

            primer = nuevo;

        }
        contador++;
    }

    @Override
    public void eliminaElement(int n) {
        //Si la llista no està buida
        if (primer != null) {
            Node_Ref node = primer;
            Node_Ref aux = null;
            for (int i = 0; i < n; i++) {
                aux = node;
                node = node.getNodeSeg();

            }
            //cas 1:eliminam el primer
            if (n == 0) {
                if (primer.getNodeSeg() != null) {
                    primer = node.getNodeSeg();
                } else {//nomes hi ha 1 element
                    primer = null;
                }

            } else if (n < contador) {
                //Cas 2: eliminam un node intermig
                aux.setNodeSeg(node.getNodeSeg());
                node.setNodeSeg(null);
            } else if (n == contador) {
                //Cas 3: eliminam el darrer node
                aux.setNodeSeg(null);
            }
            contador--;

        } else {
        }
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
        //Algoritme ordenació
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
            int n = 0;
            while (n < 1) {
                s += (Assignatura) node.getObj();
                s += '\n';
                node = node.getNodeSeg();
                n++;
            }
            return s;
        } else {
            return "{buit}";
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

    @Override
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

    @Override
    public ArrayList<String> nomsToArrayList() {
        ArrayList<String> arr = new ArrayList();
        Node_Ref node = primer;
        int i = 0;
        if (node != null) {
            while (node != null) {
                Assignatura e = (Assignatura) node.getObj();
                arr.add(e.getNom());
                node = node.getNodeSeg();
            }
            return arr;
        } else {
            return null;//Vacío
        }
    }

    @Override
    public Object cercaElement(Object e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
