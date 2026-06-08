/*
FUNCIONALITAT: Classe MARE de la qual heredaran les característiques
les següents classes:
-CompteCorrent
-LlibretaEstalvi
 */
package ClassesCompte;

/**
 * AUTORS: Joan Balaguer, Jaume Adrover DATA CREACIÓ: 26/10/2021
 */
public class CompteBancari {
    protected int ncompte;
    protected double saldo;
    CompteBancari seg;

    /*
    MÈTODES CONSTRUCTORS:
    */
    public CompteBancari() {
    }

    public CompteBancari(int ncompte, double saldo) {
        this.ncompte = ncompte;
        this.saldo = saldo;
        seg=null;
    }
    /*
    MÈTODES POINTERS
    */
    public void setNodeSeg(CompteBancari apuntat) {
        seg = apuntat;
    }
    public CompteBancari getNodeSeg(){
        return seg;
    }
    /*
    DEPOSITAR
    */
    public void depositar(double quantitat) {
        System.out.println("Dipòsit en el compte: " + saldo);
        System.out.println("Quantitat dipositada: " + quantitat);
        saldo += quantitat;
        System.out.println("Saldo actual: " + saldo);
    }

    public void retirar(double quantitat) {
        System.out.println();
        System.out.println("Retirada de fondos del compte: " + ncompte);
        System.out.println("Saldo abans de la retirada: " + saldo);
        System.out.println("Quantitat sol.licitada: " + quantitat);

        if (saldo < quantitat) {
            System.out.println("No hi ha fons suficient.");
        } else {
            saldo -= quantitat;
            System.out.println("Saldo actual: " + saldo);
        }

    }
    

    public double getSaldo() {
        return saldo;
    }

    public void transferirDiners(CompteBancari compte, double q) {
        saldo -= q;
        compte.depositar(q);
    }

}
