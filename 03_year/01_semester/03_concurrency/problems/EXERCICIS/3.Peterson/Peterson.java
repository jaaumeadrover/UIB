package peterson;


import java.time.LocalTime;
import java.util.Random;

public class Peterson implements Runnable {

    static final int THREADS = 2;
    static final int MAX_COUNT = 20;
    static volatile int[] want = new int[THREADS];
    static volatile int last = 0;
    static volatile int n = 0;
    int id;
    private float averageTime;

    public Peterson(int id) {
        this.id = id;
        averageTime=0;
    }

    @Override
    public void run() {
        Random rand=new Random();
        int max = MAX_COUNT / THREADS;
        int altreProces = (id + 1) % THREADS;
        long time=0;
        for (int i = 0; i < max; i++) {
            //Esperar un número aleatori fins a 5000 milisegons
            try {
                Thread.sleep(rand.nextInt(5000));
            } catch (InterruptedException ex) {
                System.out.println("Error durant la interrupció aleatòria.");
            }
            want[id] = 1;
            last = id;
            while (want[altreProces] == 1 && last == id) {
            }
            if(i>0){
                averageTime+=(System.currentTimeMillis()-time);
            }
            System.out.println("Porta "+id+": "+(i+1)+" entrades de : "+(n+1)+" Temps: "+LocalTime.now());
            n += 1; //SC
            want[id] = 0;
            time=System.currentTimeMillis();
        }
        averageTime=(averageTime/(max-1))/1000;
    }
    
    public float getAverageTime(){
        return averageTime;
    }
    
    public static void main(String[] args) throws InterruptedException {
        Peterson[]contadors=new Peterson[THREADS];
        Thread[] threads = new Thread[THREADS];
        want[0] = 0;
        want[1] = 0;

        int i;
        for (i = 0; i < THREADS; i++) {
            contadors[i]=new Peterson(i);
            threads[i] = new Thread(contadors[i]);
            threads[i].start();
        }
        for (i = 0; i < THREADS; i++) {
            threads[i].join();
        }
        System.out.println("Entrades totals: "+n);
        for (i = 0;  i< THREADS; i++) {
            System.out.println("Temps mig porta "+i+": "+contadors[i].getAverageTime()+" segons");
            
        }
        
        
    }

}