/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package _ordenaciotaules4;

import java.util.Scanner;

/**
 *
 * @author Jaume A
 */
public class Menu {

    Scanner reader = new Scanner(System.in);
    boolean sortir = false;

//    while (!sortir) {
//            menu();
//        System.out.print("Insereix opció: ");
//        int x = reader.nextInt();
//        switch (x) {
//
//            case 1:
//
//                break;
//
//            case 2:
//
//                break;
//
//            case 3:
//
//                break;
//
//            case 0:
//                sortir = true;
//                break;
//        }
//    }

    private static void menu() {
        System.out.println("\n\nGestio de dades de Colors");
        System.out.println("\n\t1. Inizialitza el fitxer de Colors");
        System.out.println("\t2. Calcul del Color promitg");
        System.out.println("\t3. Array de color ordenat");
        System.out.println("\t0. Sortir \n");
    }
}
