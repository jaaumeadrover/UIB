/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package efficientsorting;

/**
 *
 * @author xisca
 */
public class LinkedList<E extends Comparable<E>> {

    private Node first;

    public LinkedList() {
        first = null;
    }

    // Afegim pel principi de la llista
    public void add(E element) {
        /*if (first == null) {
            first = new Node(element, null);
        } else {
            Node n = new Node(element, first);
            first = n;
        }*/
        Node n = new Node(element, first);
        first = n;
    }

    @Override
    public String toString() {
        String s = "LinkedList = ";
        Node n = first;
        while (n != null) {
            s += n.getItem().toString();
            n = n.getNext();
            if (n != null) {
                s += ", ";
            }
        }

        return s;
    }

    private Node merge(Node l, Node r) {
        // Cap de la llista
        Node head;
        // Iterador sobre la llista resultant
        Node p;

        // Seleccionam el cap de la llista
        if (((E) l.getItem()).compareTo((E) r.getItem()) < 0) {
            head = l;
            l = l.getNext();
        } else {
            head = r;
            r = r.getNext();
        }
        // Inicialitzam l'iterador de la llista resultant
        p = head;

        // Tenim elements a les dues subllistes ordenades
        while (l != null && r != null) {
            if (((E) l.getItem()).compareTo((E) r.getItem()) < 0) {
                p.setNext(l);
                l = l.getNext();
            } else {
                p.setNext(r);
                r = r.getNext();
            }
            p = p.getNext();
        }

        // Només tenim elements a la subllista esquerra
        while (l != null) {
            p.setNext(l);
            p = p.getNext();
            l = l.getNext();
        }

        // Només tenim elements a la subllista dreta
        while (r != null) {
            p.setNext(r);
            p = p.getNext();
            r = r.getNext();
        }
        return head;

    }

    private Node mergeSort(Node n) {
        // La llista es troba buida o només té un element? La llista ja està ordenada
        if (n == null || n.getNext() == null) {
            return n;
        }
        // Seleccionam el node intermig de la llista
        Node middle = middle(n);
        
        // Llista dreta: seleccionam el node següent al node intermig de la llista
        Node nextToMiddle = middle.getNext();
        // Tallam la llista esquerra
        middle.setNext(null);
        
        // Crida recursiva amb la subllista esquerra
        Node l = mergeSort(n);
        // Crida recursiva amb la subllista dreta
        Node r = mergeSort(nextToMiddle);
        
        // Merge de les dues subllistes ordenades
        return merge(l, r);

    }

    public void mergeSort() {
        this.first = mergeSort(this.first);
    }

    // O(n)
    private Node middle(Node firstNode) {
        Node n1 = firstNode, n2 = firstNode;
        while (n2.getNext() != null) {
            n2 = n2.getNext();
            if (n2.getNext() != null) {
                n1 = n1.getNext();
                n2 = n2.getNext();
            }
        }

        return n1;
    }

}
