/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Executables;

import java.util.Scanner;
import Classes.Llista;
import Classes.Node;
import java.util.Random;

/**
 *
 * @author joanbalaguer
 */
public class Exercici3 {
    
    public static void main(String[] args) throws Exception {
        new Exercici3().programaPrincipal();
    }

    Llista l = new Llista();
    
    public void programaPrincipal() {
        //definim un scanner per poder lletgir les opcions que ens va inserint l'usuari
        Scanner reader = new Scanner(System.in);
        //boolea per poder sortir del menu
        boolean sortir = false;

        //ens mantindre dins el bucle mentres l'usuari no insereixi el 0
        while (!sortir) {
            menu3();
            System.out.print("Insereix opció: ");
            int x = reader.nextInt();
            switch (x) {

                case 1:
                    creaLlistaOrdenada();
                    break;

                case 2:
                    System.out.print("\nIntrodueix el nombre que vols que contengui el node inserit: ");
                    int n = reader.nextInt();
                    l.insereixNodeLLOrdenada(n);
                    break;

                case 3:
                    mostraLlista();
                    break;

                case 0:
                    sortir = true;
                    break;
            }
        }
    }

    private static void menu3() {
        System.out.println("\n\n***Exercici 3***");
        System.out.println("\n\t1. Crea una llista ordenada amb un nombre de nodes Random");
        System.out.println("\t2. Inseriri node dins la llista ordenada");
        System.out.println("\t3. Mostrar llista");
        System.out.println("\t0. Sortir \n");
    }
    
    private void creaLlistaOrdenada(){
        Random rand = new Random();
        int i = rand.nextInt(20);
        int minim = rand.nextInt(50);
        int p = minim;
        Node n;
        n = l.getPrimer();
        for (int y = 0;i > y; y++) {
            Node nouNode = new Node(p);
            n.setNodeSeg(nouNode);
            n = nouNode;
            p = p + rand.nextInt(10);
            
           
        }
    }
    
    public void mostraLlista() {
        System.out.println(l.toString());
    }
}
