/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Mappings;

import java.util.Iterator;
import java.util.Stack;

/**
 *
 * @author Jaume
 * @param <E>
 */
public class BSTSet<E extends Comparable> {
    private Node root;

    private class Node {

        private E elem;
        private Node left, right;

        public Node(E elem, Node left, Node right) {
            this.elem = elem;
            this.left = left;
            this.right = right;
        }

    }
    public Iterator IteratorBSTSet(){
        return new IteratorBSTSet();
    }


    private class Cerca {

        public Cerca(boolean trobat) {
            this.trobat = trobat;
        }

        boolean trobat;
    }

    public BSTSet() {
        root = null;
    }

    boolean isEmpty() {
        return root == null;
    }

    public boolean contains(E elem) {
        return contains(elem, root);
    }

    private boolean contains(E elem, Node current) {
        if (current == null) { // Si l’arbre és buit: no trobat
            return false;
        } else {
            if (current.elem.equals(elem)) {// Si el node conté l’element: trobat
                return true;
            }
// Si l’element és inferior a l’element del node:
            if (elem.compareTo(current.elem) < 0) {
                return contains(elem, current.left); // cercar al fill esquerra
            } else {
                return contains(elem, current.right); // cercar al fill dret
            }
        }
    }

    public boolean add(E elem) {
        Cerca cerca = new Cerca(false);
        this.root = add(elem, root, cerca);
        return !cerca.trobat;
    }

    private Node add(E elem, Node current, Cerca cerca) {
        if (current == null) {// Si l’arbre és buit: retornam un node nou
            return new Node(elem, null, null);
        } else {
            if (current.elem.equals(elem)) {// Si el node conté l’element
                cerca.trobat = true;
                return current; // retornam el node (sense modificar)
            }
            if (elem.compareTo(current.elem) < 0) {// Si l’element és inferior
// hem d’afegir al subarbre esquerre
                current.left = add(elem, current.left,cerca);
            } else {
// hem d’afegir al subarbre dret
                current.right = add(elem, current.right,cerca);
            }
            //cerca.trobat=
            return current;
        }
    }
    public boolean remove(E elem) {
        Cerca cerca = new Cerca(false);
        this.root = remove(elem, root, cerca);
        return cerca.trobat;
    }
    private Node remove(E elem, Node current, Cerca cerca) {
        if (current == null) { // Element no trobat
            return null;
        }
        if (current.elem.equals(elem)) { // Element trobat          
            //és un node fulla
            if (current.left == null && current.right == null) {
                cerca.trobat=true;
                return null;
            } else if (current.left == null && current.right != null) {//té només un fill
                cerca.trobat=true;
                return current.right;
            } else if (current.left != null && current.right == null) {
                cerca.trobat=true;
                return current.left;
            }
            // Node més esquerra del fill dret
            Node plowest = current.right;
            Node parent = current;
            while (plowest.left != null) {
                parent = plowest;
                plowest = plowest.left;
            }
            plowest.left = current.left;
            if (plowest != current.right) {
                parent.left = plowest.right;
                plowest.right = current.right;
            }
            cerca.trobat=true;
            return plowest;

        }
        if (elem.compareTo(current.elem) < 0) { // Subarbre esquerra
            current.left = remove(elem, current.left, cerca);
        } else {// Subarbre dret
            current.right = remove(elem, current.right, cerca);
        }
        return current;
    }

    private class IteratorBSTSet implements Iterator {
        private Stack<Node> iterator;
// Quin és el primer node a visitar

        public IteratorBSTSet() {
            Node p;
            iterator = new Stack();
            if (root != null) {
                p = root;
                while (p.left != null) {
                    iterator.push(p);
                    p = p.left;
                }
                iterator.push(p);
            }
        }
// Tenim més nodes per visitar?

        @Override
        public boolean hasNext() {
            return !iterator.isEmpty();
        }
// Quin és el següent node a visitar

        @Override
        public Object next() {
            Node p = iterator.pop();
            E elem = p.elem;
            if (p.right != null) {
                p = p.right;
                while (p.left != null) {
                    iterator.push(p);
                    p = p.left;
                }
                iterator.push(p);
            }
            return elem;
        }
    }
}
