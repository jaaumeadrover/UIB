/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClassesCompte;

import ClassesCompte.CompteBancari;

/**
 *
 * @author Jaume A
 */
public class LlibretaEstalvi extends CompteBancari {
    private int interes;
    private LlibretaEstalvi seg;

    public LlibretaEstalvi(int ncompte, double saldo) {
        super(ncompte, saldo);
        this.interes = 10;
    }
    
    public void setInteres(int interes) {
        this.interes = interes;
    }
    
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public void interessos() {
        System.out.println();
        System.out.println("Aplicant interessos del compte: " + ncompte);
        System.out.println("Saldo actual: "+ saldo);
        System.out.println("Interés actual: " + interes);
        saldo += saldo * interes / 100;
        System.out.println("Saldo despres de la aplicació: " + saldo);
    }

    @Override
    public String toString() {
        return "LlibretaEstalvi{Nombre: " + ncompte + ", saldo: " + saldo + ", interes=" + interes + '}';
    }

    public int getNCompte() {
       return ncompte;
    }

}
