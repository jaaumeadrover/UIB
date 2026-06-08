/*
Classe Llista que defineix les característiques i funcionalitats d'un objecte Llista
 */
package Classes;

import java.util.Random;

/**
 *
 * @author joanbalaguer
 */
public class Llista {

    //Declaració de atributs
    private Node primer;

    //Constructor que inicialitza una llista buida
    public Llista() {
        primer = new Node();
    }

    //mètode que ens retorna el primer node de la llista
    public Node getPrimer() {
        return primer;
    }


    //Operació per poder inserir un element davant un de donat (Exercici 1)
    public void insereixDavant(int n) {
        try {
            Node aux, nouNode;
            aux = this.primer;
            Random rnd = new Random();
            if (n == this.primer.getInfo()) {
                nouNode = new Node(rnd.nextInt(100));
                nouNode.setNodeSeg(primer.getNodeSeg());
                primer.setNodeSeg(nouNode);
                System.out.println("Inserit Correctament");
            } else {
                while ((aux.getNodeSeg() != null) && (aux.getNodeSeg().getInfo() != n)) {
                    aux = aux.getNodeSeg();
                }
                if ((aux.getNodeSeg().getInfo() != n)) {
                    System.out.println("No s'ha trobat\n");
                } else {
                    nouNode = new Node(rnd.nextInt(100));
                    nouNode.setNodeSeg(aux.getNodeSeg());
                    aux.setNodeSeg(nouNode);
                    System.out.println("Inserit Correctament");
                }
            }
        } catch (NullPointerException e) {
            System.out.println("No s'ha trobat");
        }

    }

    //Mètode que ens permetrà eliminar un node de la llista (Exercici 2)
    public Node eliminaNode(int n) {
        try {
            Node element, eliminat = new Node();
            element = this.primer;
            while ((element.getNodeSeg() != null) && (element.getNodeSeg().getInfo() != n)) {
                element = element.getNodeSeg();
            }
            if ((element.getNodeSeg() == null) && (element.getNodeSeg().getInfo() != n)) {
                System.out.println("No s'ha trobat\n");
                return null;
            } else {
                eliminat.copyNode(element.getNodeSeg());
                element.setNodeSeg(element.getNodeSeg().getNodeSeg());
                System.out.println("Element eliminat correctament");
                eliminat.setNodeSeg(null);
                return eliminat;
            }
        } catch (NullPointerException e) {
            System.out.println("No s'ha trobat");
        }
        return null;
    }

    //Mètode amb el qual inserirem un node que en passaràn per paràmetre al final 
    //de la llista que estiguem tractant (Exercici 2)
    public void insereixAlFinal(Node n) {
        Node aux = this.primer;
        while ((aux.getNodeSeg() != null)) {
            aux = aux.getNodeSeg();
        }
        aux.setNodeSeg(n);

    }

    //Mètode que ens inserirà un node dins una llista ordenada (Exercici 3)
    public void insereixNodeLLOrdenada(int n) {
        Node aux = this.primer, nouNode = new Node(n);
        while ((aux.getNodeSeg() != null) && (aux.getNodeSeg().getInfo() < n)) {
            aux = aux.getNodeSeg();
        }
        if (aux.getNodeSeg() == null) {
            aux.setNodeSeg(nouNode);
        } else {
            nouNode.setNodeSeg(aux.getNodeSeg());
            aux.setNodeSeg(nouNode);
        }
        System.out.println("Node inserit correctament");
    }

    @Override
    public String toString() {
        String acum = "";
        int i = 1;
        Node n = primer;
        acum = "Primer Node: " + n + "\n";
        n = n.getNodeSeg();
        while (n != null) {
            acum += "Node " + i + ": " + n + "\n";
            n = n.getNodeSeg();
            i++;
        }
        return acum;
    }
}
