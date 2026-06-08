/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Jaume
 */
public class Atomic_v2 implements Runnable{
    static final int THREADS=2;
    static final int MAX_COUNT=100;
    static volatile int n=0;
    static AtomicInteger sumador;
    static AtomicInteger turn;

    @Override
    public void run() {
        int max=MAX_COUNT/THREADS;
        for (int i = 0; i < max; i++) {
            int current=sumador.getAndAdd(1);
            while(current!=turn.intValue()){
                Thread.yield();
            }
            n++;
            turn.getAndAdd(1);
        }
    }
    
    public static void main(String [] args) throws InterruptedException{
        Thread threads []=new Thread[THREADS];
        Atomic_v2 ac=new Atomic_v2();
        sumador=new AtomicInteger(0);
        turn=new AtomicInteger(0);

        for (int i = 0; i < THREADS; i++) {
            threads[i]=new Thread(ac);
            threads[i].start();
        }
        for (int i = 0; i < THREADS; i++) {
            threads[i].join();
        }
        float error = (MAX_COUNT - n) / (float) MAX_COUNT * 100;
        System.out.printf("Counter value: %d Expected: %d Error: %3.6f%%\n", n, MAX_COUNT, error);
    }
    
    
}
