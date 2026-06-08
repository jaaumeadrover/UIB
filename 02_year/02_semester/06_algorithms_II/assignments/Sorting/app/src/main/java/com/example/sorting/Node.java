package com.example.sorting;

public class Node {
    private Object item;
    // Punter al seg¨u ent node
    private Node next;
    // Crea un node a partir de l’element i el node seg ¨uent

    public Node(Object item, Node next) {
        this.item = item;
        this.next = next;
    }
    // Retorna l’element del node

    public Object getItem() {
        return item;
    }// Especifica l’element del node

    public void setItem(Object item) {
        this.item = item;
    }
    // Retorna el node seg ¨uent

    public Node getNext() {
        return next;
    }
    // Especifica el node seg ¨uent

    public void setNext(Node next) {
        this.next = next;

    }
}
