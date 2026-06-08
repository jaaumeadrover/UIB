/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package llopovelles;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jaume
 */
public class Llop implements Runnable{
    private final int id;
    public Llop(int id){
        this.id=id;
    }

    @Override
    public void run() {
        System.out.println("BON DIA SOM EL LLOP"+id);
        for (int i = 0; i < Main.iteracions; i++) {
            try {
                System.out.println("El llop "+id+" vol entrar al riu");
                Main.monitor.entraRiuLlop(id);
                System.out.println("El llop "+id+" és al riu,beu aigua");
                Thread.sleep(1500);
                Main.monitor.surtRiuLlop(id);
                System.out.println("El llop "+id+" ha partit del riu");
            } catch (InterruptedException ex) {
                Logger.getLogger(Llop.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        System.out.println("LLOP "+id+": ja me'n vaig");
    }
}
