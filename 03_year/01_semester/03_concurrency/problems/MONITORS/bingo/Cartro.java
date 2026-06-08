/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bingo;

import java.util.Random;

/**
 *
 * @author Jaume
 */
public class Cartro {

    private static final int MAX = 15;
    private int num[];
    private boolean afegit[];

    public Cartro() {
        num = new int[MAX];
        afegit=new boolean[MAX];
        Random rand = new Random();
        for (int i = 0; i < num.length; i++) {
            int nombre = rand.nextInt(90) + 1;
            while (contains(nombre)) {
                nombre = rand.nextInt(90) + 1;
            }
            num[i] = nombre;
            afegit[i] = false;
        }
    }

    private boolean contains(int n) {
        for (int i = 0; i < num.length; i++) {
            if (num[i] == n) {
                return true;
            }

        }
        return false;
    }
    
    //afegir nombre al cartró
    public boolean afegeixNombre(int n) {
        for (int i = 0; i < num.length; i++) {
            if (num[i] == n) {
                afegit[i] = true;
                return true;
            }
        }
        return false;
    }
    
    //veure si un catro esta acabat
    public boolean acabat() {
        for (int i = 0; i < num.length; i++) {
            if (afegit[i] == false) {
                return false;
            }
        }
        return true;
    }
}
