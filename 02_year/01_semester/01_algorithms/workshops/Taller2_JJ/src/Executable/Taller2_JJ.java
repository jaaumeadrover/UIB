/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Executable;

import ClassesLlista.*;
import ClassesCompte.*;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Jaume A
 */
public class Taller2_JJ {

    //LlistaComptesBancaris llistaComptes,LlistaLlibretes; ---> no funciona per visualitza
    LlistaComptesCorrent llistaComptes;
    LlistaLlibretaEstalvi llistaLlibretes;
    int contadorCC = 0, contadorLA = 0;

    public static void main(String[] args) {
        // TODO code application logic here
        new Taller2_JJ().programaPrincipal();
    }

    public void programaPrincipal() {
        llistaComptes = new LlistaComptesCorrent(0, 0);
        llistaLlibretes = new LlistaLlibretaEstalvi(0, 0);

        //definim un scanner per poder lletgir les opcions que ens va inserint l'usuari
        Scanner reader = new Scanner(System.in);
        //boolea per poder sortir del menu
        boolean sortir = false;

        //ens mantindre dins el bucle mentres l'usuari no insereixi el 0
        while (!sortir) {
            menu();
            System.out.print("Insereix opció: ");
            int x = reader.nextInt();
            switch (x) {

                case 1:
                    obrirCompte();
                    break;

                case 2:
                    obrirLlibreta();
                    break;

                case 3:
                    depositaCorrent();

                    break;

                case 4:
                    depositaLlibreta();

                    break;

                case 5:
                    retirarDobCorrent();

                    break;

                case 6:
                    retirarDobLlibreta();

                    break;

                case 7:
                    transpassaDob();

                    break;

                case 8:
                    aplicarInteres();

                    break;

                case 9:
                    baixaCompteCorrent();

                    break;

                case 10:
                    baixaLlibreta();
                    break;

                case 11:
                    System.out.println(llistaComptes + "\n");
                    break;

                case 12:
                    System.out.println(llistaLlibretes + "\n");
                    break;

                case 0:
                    sortir = true;
                    break;
            }
        }

    }

    private int llegirEnter(String str) {
        System.out.print(str);
        Scanner reader = new Scanner(System.in);
        return reader.nextInt();
    }

    private int generarRandom(int x) {
        Random rand = new Random();
        return rand.nextInt(x);
    }

    private double llegirDouble(String str) {
        System.out.print(str);
        Scanner reader = new Scanner(System.in);
        return reader.nextDouble();
    }

    private static void menu() {
        System.out.println("\n\n***GESTIÓ DE COMPTES BANCARIS***");
        System.out.println("\n\t1. Obrir compte corrent");
        System.out.println("\t2. Obrir llibreta d'estalvi");
        System.out.println("\t3. Depositar doblers dins un compte corrent");
        System.out.println("\t4. Depositar doblers dins una llibreta d'estalvis");
        System.out.println("\t5. Retirar doblers d'un compte corrent");
        System.out.println("\t6. Retirar doblers d'una llibreta d'estalvis");
        System.out.println("\t7. Transpasar doblers d'una llibreta a una altre");
        System.out.println("\t8. Aplica l'interés a una llibreta d'estalvis");
        System.out.println("\t9. Donar de baixa un compte corrent");
        System.out.println("\t10. Donar de baixa una llibreta d'estalvis");
        System.out.println("\t11. Mostrar tots els comptes corrents ");
        System.out.println("\t12. Mostrar totes les llibretes d'estalvis ");
        System.out.println("\t0. Sortir \n");
    }

    private void obrirCompte() {
        CompteBancari compte;

        System.out.println("\nHEU ELEGIT OBRIR UN COMPTE CORRENT\n\n");
        int ncompte = generarRandom(100);
        double saldo = llegirDouble("INTRODUIU EL SALDO INICIAL: ");

        //Instanciam l'objecte
        compte = new CompteCorrent(ncompte, saldo);

        //afegim objecte
        llistaComptes.insereixAlFinal(compte);
        contadorCC++;

    }

    private void obrirLlibreta() {
        try {
            //CREAM LLIBRETA
            System.out.println("\nHEU ELEGIT OBRIR UNA LLIBRETA D'ESTALVIS\n\n");
            int ncompte = generarRandom(100);
            double saldo = llegirDouble("INTRODUIU EL SALDO INICIAL: ");
            LlibretaEstalvi lib = new LlibretaEstalvi(ncompte, saldo);

            //ELEGIM COMPTE AL QUAL ASSOCIAR
            System.out.println("\nELEGIU A QUIN COMPTE VOLEU ASOCIAR-LA: \n\n" + llistaComptes);
            int nombre = llegirEnter("Nombre: ");

            llistaComptes.getCompteCorrent(nombre).associaLlibreta(lib);

            //inserim llibreta a la llista(feim una còpia)
            LlibretaEstalvi lib2 = new LlibretaEstalvi(ncompte, saldo);
            lib2.setNodeSeg(null);
            llistaLlibretes.insereixAlFinal(lib2);
            contadorLA++;
        } catch (NullPointerException e) {
            System.out.println("ENCARA NO S'HA CREAT CAP COMPTE CORRENT");
        }
    }

    private void depositaCorrent() {
        System.out.println("\nHEU ELEGIT DEPOSITAR DOBLERS DINS UN COMPTE CORRENT\n\n");
        System.out.println("A QUIN COMPTE CORRENT VOLEU INSERIR ELS DOBLERS?\n\n" + llistaComptes + "\n");
        int nombre = llegirEnter("Nombre: ");
        System.out.println("QUINA QUANTITAT VOLS DEPOSITAR? \n");
        double q = llegirDouble("Quantitat: ");
        llistaComptes.getCompte(nombre).depositar(q);
    }

    private void depositaLlibreta() {
        System.out.println("HEU ELEGIT DEPOSITAR DOBLERS DINS UNA LLIBRETA D'ESTALVIS");
        System.out.println("A QUINA LLIBRETA D'ESTALVIS VOLEU INSERIR ELS DOBLERS?\n\n" + llistaLlibretes + "\n");
        int nombre = llegirEnter("Nombre: ");
        System.out.println("QUINA QUANTITAT VOLS DEPOSITAR? \n");
        double q = llegirDouble("Quantitat: ");
        llistaLlibretes.getLlibreta(nombre).depositar(q);

        //ACTUALITZAR
        actualitzaLlista();
    }

    private void retirarDobCorrent() {
        System.out.println("HEU ELEGIT RETIRAR DOBLERS D'UN COMPTE CORRENT");
        System.out.println("DE QUIN COMPTE CORRENT VOLEU RETIRAR DOBLERS?\n\n" + llistaComptes + "\n");
        int nombre = llegirEnter("Nombre: ");
        System.out.println("QUINA QUANTITAT VOLS RETIRAR? \n");
        double q = llegirDouble("Quantitat: ");
        llistaComptes.getCompteCorrent(nombre).retirar(q);
    }

    private void retirarDobLlibreta() {
        System.out.println("HEU ELEGIT RETIRAR DOBLERS DINS UNA LLIBRETA D'ESTALVIS");
        System.out.println("DE QUINA LLIBRETA D'ESTALVIS VOLEU RETIRAR DOBLERS?\n\n" + llistaLlibretes + "\n");
        int nombre = llegirEnter("Nombre: ");
        System.out.println("QUINA QUANTITAT VOLS RETIRAR? \n");
        double q = llegirDouble("Quantitat: ");
        llistaLlibretes.getLlibreta(nombre).retirar(q);

        actualitzaLlista();
    }

    private void transpassaDob() {
        System.out.println("HEU ELEGIT TRANSPASSAR DOBLERS D'UNA LLIBRETA D'ESTALVIS A UN ALTRE");
        System.out.println("DE QUINA LLIBRETA D'ESTALVIS VOLEU EXTREURE DOBLERS?\n\n" + llistaLlibretes + "\n");
        int nombreExtreure = llegirEnter("Nombre: ");
        System.out.println("\nQUINA QUANTITAT VOLS EXTREURE? \n");
        double q = llegirDouble("Quantitat: ");
        System.out.println("\nA QUINA LLIBRETA D'ESTALVIS VOLEU DEPOSITAR ELS DOBLERS EXTRETS?\n\n" + llistaLlibretes + "\n");
        int nombreDepositar = llegirEnter("Nombre: ");
        llistaLlibretes.getLlibreta(nombreExtreure).transferirDiners(llistaLlibretes.getLlibreta(nombreDepositar), q);

        actualitzaLlista();

    }

    private void aplicarInteres() {
        System.out.println("\nHEU ELEGIT APLICAR L'INTERES D'UNA LLIBRETA D'ESTALVIS\n");
        System.out.println("DE QUINA LLIBRETA D'ESTALVIS VOLEU APLICAR L'INTERÉS?\n\n" + llistaLlibretes + "\n");
        int nombre = llegirEnter("Nombre: ");
        llistaLlibretes.getLlibreta(nombre).interessos();

        //ACTUALITZAM
        actualitzaLlista();
    }

    private void baixaCompteCorrent() {
        System.out.println("\nHEU ELEGIT DONAR DE BAIXA UN COMPTE CORRENT\n");
        System.out.println("QUIN COMPTE CORRENT VOLEU DONAR DE BAIXA?\n\n" + llistaComptes + "\n");
        int nombre = llegirEnter("Nombre: ");
        llistaComptes.baixa(nombre);
    }

    private void baixaLlibreta() {
        System.out.println("\nHEU ELEGIT DONAR DE BAIXA UNA LLIBRETA D'ESTALVIS\n");
        System.out.println("QUINA LLIBRETA D'ESTALVIS VOLEU DONAR DE BAIXA?\n\n" + llistaLlibretes + "\n");
        int nombre = llegirEnter("Nombre: ");
//        actualitzaBaixa(nombre);
        llistaLlibretes.baixa(nombre);
    }

    private void actualitzaLlista() {
        int j;
        int x;

        //BUCLE RECORREGUT COMPTES CORRENTS
        for (int i = 1; i <= contadorCC; i++) {
            //BUCLE RECORREGUT LLIBRETES
            for (j = 0; llistaComptes.getCompteCorrent(i).getLibAsociada(j).getNodeSeg() != null; j++) {
                x = llistaComptes.getCompteCorrent(i).getLibAsociada(j).getNCompte();

                for (int ij = 1; ij < contadorLA + 1; ij++) {
                    if (llistaLlibretes.getLlibreta(ij).getNCompte() == x) {
                        double y = llistaLlibretes.getLlibreta(ij).getSaldo();
                        llistaComptes.getCompteCorrent(i).getLibAsociada(j).setSaldo(y);
                    }
                }

            }
            //ÚLTIM CAS
            if (llistaComptes.getCompteCorrent(i).getLibAsociada(j).getNodeSeg() == null) {
                x = llistaComptes.getCompteCorrent(i).getLibAsociada(j).getNCompte();
                for (int ij = 1; ij < contadorLA + 1; ij++) {
                    if (llistaLlibretes.getLlibreta(ij).getNCompte() == x) {
                        double y = llistaLlibretes.getLlibreta(ij).getSaldo();
                        llistaComptes.getCompteCorrent(i).getLibAsociada(j).setSaldo(y);
                    }
                }
            }

        }
    }

//    private void actualitzaBaixa(int numLlib) {
//        int nCompte = llistaLlibretes.getLlibreta(numLlib).getNCompte();
//        int j;
//        
//        for (int i = 1; i <= contadorCC; i++) {
//            //BUCLE RECORREGUT LLIBRETES
//            for (j = 0; llistaComptes.getCompteCorrent(i).getLibAsociada(j).getNodeSeg() != null; j++) {
//                
//                if(llistaComptes.getCompteCorrent(i).getLibAsociada(j).getNCompte() == nCompte){
//                    llistaComptes.getCompteCorrent(i).getLibAsociades().baixa(j);
//                }
//
//            }
//        }
//    }
}
