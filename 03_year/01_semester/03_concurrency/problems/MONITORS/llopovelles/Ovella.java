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
public class Ovella implements Runnable{
    private final int id;
    public Ovella(int id){
        this.id=id;
    }

    @Override
    public void run() {
        System.out.println("Hola soc l'ovella "+id);
        for (int i = 0; i < Main.iteracions; i++) {
            try {
                //arriba la ovella
                System.out.println("        Ovella "+id+": vaig a entrar al riu...");
                Main.monitor.entraRiuOvella(id);
                System.out.println("        Ovella "+id+": estic beguent aigua al riu.");
                Thread.sleep(1000);
                System.out.println("        Ovella "+id+": vaig a surtir del riu...");
                Main.monitor.surtRiuOvella(id);
                System.out.println("        Ovella "+id+": he surtit del riu.");
                
                //entra al riu
            } catch (InterruptedException ex) {
                Logger.getLogger(Ovella.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    }
    
    
}
