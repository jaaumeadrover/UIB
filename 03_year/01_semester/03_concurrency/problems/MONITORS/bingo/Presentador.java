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
public class Presentador implements Runnable{
    public Presentador(){
        
    }

    @Override
    public void run() {
        System.out.println("BON DIA SOM EL PRESENTADOR");
        //mentre no acabat el joc
        while(!Main.joc.haAcabat()){
            try {
                System.out.println("    PRESENTADOR: vaig a presentar un nou nombre...");
                int nombre=0;//presenta nombre -> espera q tothom el rebi
                nombre=Main.joc.presentaNombre();
                System.out.println("    PRESENTADOR: ha surtit el nombre "+nombre+". ");
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Presentador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
