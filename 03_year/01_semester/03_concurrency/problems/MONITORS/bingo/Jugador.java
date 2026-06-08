/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bingo;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jaume
 */
public class Jugador implements Runnable{
    private Cartro cartrons[];
    private int id;
    
    public Jugador(int n,int id){
        cartrons=new Cartro[n];
        for (int i = 0; i < cartrons.length; i++) {
            cartrons[i]=new Cartro();
        }
        this.id=id;
    }
    
    private boolean afegeixNombre(int n){
        int indx = 0;
        for (int i = 0; i < cartrons.length; i++) {
            if(cartrons[i].afegeixNombre(n)){
                //System.out.println("JUGADOR "+id+": tenc el nombre "+n+", al cartró "+i);
                indx=i;
            }
        }
        if(cartrons[indx].acabat()){
            Main.joc.acaba();
            return true;
        }
        return false;
    }
    @Override
    public void run() {
        System.out.println("BON DIA SOM EL JUGADOR "+id);
        while(!Main.joc.haAcabat()){
            try {
                int nombre=Main.joc.escoltaNombre(id);
                //si ha acabat
                if(afegeixNombre(nombre)){
                    System.out.println("JUGADOR "+id+": BINGO!");
                }
                Thread.sleep(1000);

            } catch (InterruptedException ex) {
                Logger.getLogger(Jugador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
