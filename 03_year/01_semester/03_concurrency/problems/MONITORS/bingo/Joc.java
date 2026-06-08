/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bingo;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Jaume
 */
public class Joc {

    //ATRIBUTS STATIC
    private static final int MAX = 90;
    private static final int MIN = 1;
    //ATRIBUTS INSTÀNCIA
    private final int JUGADORS;
    private ArrayList<Integer> llista;
    private  boolean bingo;
    private int aEscoltar;

    public Joc(int jugadors) {
        llista = new ArrayList<>();
        JUGADORS = jugadors;
        aEscoltar = 0;
        bingo=false;
    }

    public synchronized int presentaNombre() throws InterruptedException {
        //mentre quedi gent per escoltar
        while (this.aEscoltar > 0) {
            this.wait();
        }
        Random rand = new Random();
        int nombre = rand.nextInt(MAX) + MIN;
        while (llista.contains(nombre)) {
            nombre = rand.nextInt(MAX) + MIN;
        }
        llista.add(nombre);
        aEscoltar = JUGADORS;
        this.notifyAll();
        return nombre;
    }

    public synchronized int escoltaNombre(int id) throws InterruptedException {
        //mentre no s'hagui presentat el nombre
        while(aEscoltar==0){
            this.wait();
        }
        aEscoltar--;
        System.out.println("        El jugador "+id+" ha escoltat al presentador.");
        if (aEscoltar == 0) {
            System.out.println("El presentador ja pot mostrar el següent nombre");
            this.notifyAll();
        }
        return llista.get(llista.size() - 1);
    }
    
    public void acaba(){
        this.bingo=false;
    }
    public boolean haAcabat(){
        return bingo;
    }
}
