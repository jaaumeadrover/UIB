/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Lists;

import java.util.Iterator;

/**
 *
 * @author Jaume
 */
public class UnsortedLinkedListSet<E> {

    private class Node {

        private E elem;
        private Node next;

        public Node(E item, Node next) {
            this.elem = item;
            this.next = next;
        }

        public E getItem() {
            return elem;
        }

        public void setItem(E item) {
            this.elem = item;
        }

        public Node getNext() {
            return next;
        }

        public void setNext(Node next) {
            this.next = next;
        }
    }
    private Node first;

    public UnsortedLinkedListSet() {
        first = null;
    }

    public Iterator iterator() {
        return new IteratorUnsortedLinkedListSet();
    }

    public boolean isEmpty() {
        return first == null;
    }

    public boolean contains(E elem) {
        Node p = first;
        boolean trobat = false;
        while (p != null && !trobat) {
            trobat = p.elem.equals(elem);
            p = p.next;
        }
        return trobat;
    }

    public boolean add(E elem) {
        boolean trobat = contains(elem);
        if (!trobat) {
            Node n = new Node(elem, first);
            first = n;
        }
        return !trobat;
    }

    public boolean remove(E elem) {
        Node p = first;
        Node pp = null;
        boolean trobat = false;
        while (p != null && !trobat) {
            trobat = p.elem.equals(elem);
            if (!trobat) {
                pp = p;
                p = p.next;
            }
        }
        if (trobat) {
            if (pp == null) {
                first = p.next;
            } else {
                pp.next = p.next;
            }
        }
        return trobat;
    }

    private class IteratorUnsortedLinkedListSet implements Iterator {

        private Node idxIterator;

        private IteratorUnsortedLinkedListSet() {
            idxIterator = first;
        }

        @Override
        public boolean hasNext() {
            return idxIterator != null;
        }

        @Override
        public Object next() {
            E elem = idxIterator.elem;
            idxIterator = idxIterator.next;
            return elem;
        }
    }

}
