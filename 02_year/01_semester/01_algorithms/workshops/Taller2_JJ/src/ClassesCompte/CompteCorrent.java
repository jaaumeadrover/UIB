/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesCompte;

import java.util.Scanner;
import ClassesLlista.LlistaComptesBancaris;
import ClassesLlista.LlistaLlibretaEstalvi;

/**
 *
 * @author Jaume A
 */
public class CompteCorrent extends CompteBancari {

    private LlistaLlibretaEstalvi libAssociades;

    public CompteCorrent(int ncompte, double saldo) {
        super(ncompte, saldo);
        libAssociades = new LlistaLlibretaEstalvi(0, 0);
    }

    public void associaLlibreta(LlibretaEstalvi lib) {
        //INSEREIX ELEMENT
        libAssociades.insereixAlFinal(lib);
    }

    public void retirar(double quantitat) {
        Scanner reader = new Scanner(System.in);

        System.out.println();
        System.out.println("Retirada de fondos del compte: " + ncompte);
        System.out.println("Quantitat sol.licitada: " + quantitat);

        saldo -= quantitat;
        System.out.println("Saldo actual: " + saldo);

        if (saldo < 0) {
            System.out.println("El saldo és negatiu, heu d'elegir una llibreta d'estalvi per restablir el saldo positiu: \n\n " + libAssociades + "\n");
            System.out.println("Nombre: ");
            int x = reader.nextInt();
            LlibretaEstalvi lib = (LlibretaEstalvi) libAssociades.getCompte(x);
            lib.retirar(saldo * (-1));
            saldo = 0;
        }

    }
    
    public LlibretaEstalvi getLibAsociada(int i){
        return libAssociades.getLlibreta(i);
    }
    
    public LlistaLlibretaEstalvi getLibAsociades(){
        return libAssociades;
    }

    @Override
    public String toString() {
        return "Nombre compte: " + ncompte + ", saldo: " + saldo + ", \nLlibretes associades: \n" + libAssociades + "\n";
    }

}
