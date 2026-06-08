package examenconcurrent;

import java.util.Random;

/**
 * AUTOR: Jaume Adrover Fernández. CLASSE: Ambulancia DATA CREACIÓ: 10/01/2023
 *
 * FUNCIONALITAT: classe Ambulancia que implementa la interfície Runnable,
 * permetent a aquesta tenir el comportament d'un fil.
 *
 * SUPOSICIONS: he utilitzat com a plantilla aquest repositori:
 * https://github.com/PlatanosVerdes/Concurrent-Templates .
 *
 */
public class Ambulancia implements Runnable {

    private final int id;

    public Ambulancia(int id) {
        this.id = id;
    }

    /*
    Mètode per a realitzar una espera del temps passat per paràmetre
     */
    private void espera(int w) {
        try {
            Thread.sleep(w);
        } catch (InterruptedException ex) {
            System.out.println("ERROR EXCEPCIÓ: " + ex.toString());
        }
    }

    /*
    Mètode per a realitzar una espera totalment aleatòria.
     */
    private void esperaRandom(int w) {
        try {
            Random rand = new Random();
            Thread.sleep(rand.nextInt(w));
        } catch (InterruptedException ex) {
            System.out.println("ERROR EXCEPCIO: " + ex.toString());
        }
    }

    @Override
    public void run() {
        try {
            //Presentam l'ambulància
            System.out.println("    L'ambulancia " + id + " està en ruta");
            esperaRandom(450);//simulam el temps que tarda a arribar al pont
            Main.monitor.demanaAccesAmbulancia(id);
            espera(3000);//simulam el temps que tarda a creuar el pont
            Main.monitor.surtVehicle(id);
            
        } catch (InterruptedException ex) {
            System.out.println("ERROR EXCEPCIÓ: " + ex.toString());
        }
    }

}
