/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Jaume
 */
public class AtomicCounter implements Runnable{
    static final int THREADS=2;
    static final int MAX_COUNT=100;
    static volatile int n=0;
    static AtomicInteger sumador;

    @Override
    public void run() {
        int max=MAX_COUNT/THREADS;
        for (int i = 0; i < max; i++) {
            sumador.getAndAdd(1);
            //n++;
        }
    }
    public static int getValue(){
        return sumador.get();
    }
    
    
    public static void main(String [] args) throws InterruptedException{
        Thread threads []=new Thread[THREADS];
        AtomicCounter ac=new AtomicCounter();
        sumador=new AtomicInteger(0);

        for (int i = 0; i < THREADS; i++) {
            threads[i]=new Thread(ac);
            threads[i].start();
        }
        for (int i = 0; i < THREADS; i++) {
            threads[i].join();
        }
        float error = (MAX_COUNT - AtomicCounter.getValue()) / (float) MAX_COUNT * 100;
        System.out.printf("Counter value: %d Expected: %d Error: %3.6f%%\n", AtomicCounter.getValue(), MAX_COUNT, error);
    }
    
    
}
