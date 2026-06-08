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
public class Exercici2 {
    
    public static void main(String[] args) throws Exception {
        new Exercici2().programaPrincipal();
    }
    
    Llista principal = new Llista();
    Llista lliures = new Llista();
    
    public void programaPrincipal() {
        //definim un scanner per poder lletgir les opcions que ens va inserint l'usuari
        Scanner reader = new Scanner(System.in);
        //boolea per poder sortir del menu
        boolean sortir = false;

        //ens mantindre dins el bucle mentres l'usuari no insereixi el 0
        while (!sortir) {
            menu2();
            System.out.print("Insereix opció: ");
            int x = reader.nextInt();
            switch (x) {

                case 1:
                    creaLlistaRand();
                    break;

                case 2:
                    System.out.print("\nIntrodueix un nombre que vulguis eliminar de la llista: ");
                    int n = reader.nextInt();
                    Node eliminat = principal.eliminaNode(n);
                    lliures.insereixAlFinal(eliminat);
                    break;

                case 3:
                    mostraLlistaPrincipal();
                    break;
                
                case 4:
                    mostraLlistaLliures();
                    break;
                case 0:
                    sortir = true;
                    break;
            }
        }
    }

    private static void menu2() {
        System.out.println("\n\n***Exercici 2***");
        System.out.println("\n\t1. Crea una llista amb un nombre de nodes Random (Màxim 100)");
        System.out.println("\t2. Eliminar node i inserir-lo dins la llista de lliures");
        System.out.println("\t3. Mostrar llista principal");
        System.out.println("\t4. Mostrar llista de lliures");
        System.out.println("\t0. Sortir \n");
    }
    
    public void creaLlistaRand() {
        Random rand = new Random();
        int i = rand.nextInt(20);
        Node n;
        n = principal.getPrimer();
        for (int y = 0; y < i; y++) {
            Node nouNode = new Node(rand.nextInt(i));
            n.setNodeSeg(nouNode);
            n = nouNode;
        }
    }

    public void mostraLlistaPrincipal() {
        System.out.println(principal.toString());
    }
    
    public void mostraLlistaLliures() {
        System.out.println(lliures.toString());
    }
}
