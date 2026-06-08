/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package llopovelles;

/**
 *
 * @author Jaume
 */
public class Main {
    public static final int LLOPS=10;
    public static final int OVELLES=5;
    public static final int iteracions=3;
    public static final Monitor monitor=new Monitor();
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO code application logic here
        Thread[] threads = new Thread[LLOPS+OVELLES];
        
        for (int i = 0; i < LLOPS; i++) {
            threads[i]=new Thread(new Llop(i));
            threads[i].start();
        }
        for (int i = LLOPS; i < LLOPS+OVELLES; i++) {
            threads[i]=new Thread(new Ovella(i));
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }
    }
    
}
