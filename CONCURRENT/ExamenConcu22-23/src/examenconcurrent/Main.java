package examenconcurrent;

import java.util.Random;

/**
 * AUTOR: Jaume Adrover Fernández.
 * DATA CREACIÓ: 10/01/2023.
 * 
 * FUNCIONALITAT: implementa tota la execució dels fils i el seu acabament.
 * 
 * SUPOSICIONS: he utilitzat com a plantilla aquest repositori:
 * https://github.com/PlatanosVerdes/Concurrent-Templates .
 *
 */
public class Main {
    //ATRIBUTS 
    public static MonitorPont monitor;
    static final int COTXES = 5;

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Thread[] threads = new Thread[COTXES + 1];
        monitor = new MonitorPont();
        int i;
        
        //BUCLE COMENÇAR FILS COTXES
        for (i = 0; i < COTXES; i++) {
            Direccio dir=elegirDireccio();
            threads[i] = new Thread(new Cotxe(i, dir));
            threads[i].start();
        }
        //COMENÇAR FIL AMBULÀNCIA
        threads[i]=new Thread(new Ambulancia(1010));
        threads[i].start();
        
        //BUCLE ACABAR FILS
        for (i = 0; i < COTXES + 1; i++) {
            threads[i].join();
        }

    }
    /*
    Mètode privat estàtic per a poder elegir una direcció de manera aleatòria.
    */
    private static Direccio elegirDireccio(){
        Random rand=new Random();
        Direccio dir;
        if((rand.nextInt(10)%2)==0){//si parell -> NORD
                dir=Direccio.NORD;
            }else{                  //si senar  -> SUD
                dir=Direccio.SUD;
            }
        return dir;
    }

}
