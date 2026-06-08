/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package bingo;

import llopovelles.*;

/**
 *
 * @author Jaume
 */
public class Main {
    private static final int JUGADORS=5;
    private static final int nCartons=4;
    public static final Joc joc=new Joc(JUGADORS);
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        Thread[] threads = new Thread[JUGADORS+1];
        
        threads[0]=new Thread(new Presentador());
        for (int i = 1; i < JUGADORS+1; i++) {
            threads[i]=new Thread(new Jugador(nCartons,i));
        }
        for (int i = 0; i < JUGADORS+1; i++) {
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
    }
    
}
