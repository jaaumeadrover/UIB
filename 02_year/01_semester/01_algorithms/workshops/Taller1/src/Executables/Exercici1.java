/*
Exercici 1 Taller 1
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
public class Exercici1 {

    public static void main(String[] args) throws Exception {
        new Exercici1().programaPrincipal();
    }

    Llista l = new Llista();

    public void programaPrincipal() {
        //definim un scanner per poder lletgir les opcions que ens va inserint l'usuari
        Scanner reader = new Scanner(System.in);
        //boolea per poder sortir del menu
        boolean sortir = false;

        //ens mantindre dins el bucle mentres l'usuari no insereixi el 0
        while (!sortir) {
            menu1();
            System.out.print("Insereix opció: ");
            int x = reader.nextInt();
            switch (x) {

                case 1:
                    creaLlistaRand();
                    break;

                case 2:
                    System.out.print("\nIntrodueix un nombre que vulguis cercar dins la llista: ");
                    int n = reader.nextInt();
                    l.insereixDavant(n);
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

    /**
     * Mètodes menu per donar les diferents ocions a l'usuari
     */
    private static void menu1() {
        System.out.println("\n\n***Exercici 1***");
        System.out.println("\n\t1. Crea una llista amb un nombre de nodes Random (Màxim 100)");
        System.out.println("\t2. Inserir node davant un de donat");
        System.out.println("\t3. Mostrar llista");
        System.out.println("\t0. Sortir \n");
    }

    public void creaLlistaRand() {
        Random rand = new Random();
        int i = rand.nextInt(20);
        Node n;
        n = l.getPrimer();
        for (int y = 0; y < i; y++) {
            Node nouNode = new Node(rand.nextInt(i));
            n.setNodeSeg(nouNode);
            n = nouNode;
        }
    }
    
    public void mostraLlista() {
        System.out.println(l.toString());
    }

}
