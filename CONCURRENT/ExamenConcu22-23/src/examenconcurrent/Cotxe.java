package examenconcurrent;

import java.util.Random;

/**
 * AUTOR: Jaume Adrover Fernández. CLASSE: Cotxe DATA CREACIÓ: 10/01/2023
 *
 * FUNCIONALITAT: classe Cotxe que implementa la interfície Runnable, permetent
 * a aquesta tenir el comportament d'un fil.
 *
 * SUPOSICIONS: he utilitzat com a plantilla aquest repositori:
 * https://github.com/PlatanosVerdes/Concurrent-Templates .
 *
 */
public class Cotxe implements Runnable {

    private final int id;
    private final Direccio dir;

    public Cotxe(int i, Direccio dir) {
        this.id = i;
        this.dir = dir;
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

    /*
    Mètode per a demanar accés segons si és cotxe sud o nord.
     */
    private void demanaAcces() throws InterruptedException {
        if (dir == Direccio.NORD) {
            Main.monitor.demanaAccesNord(id);
        } else {
            Main.monitor.demanaAccesSud(id);
        }
    }

    /*
    Mètode per a surtir del pont.
     */
    private void surtPont() {
        Main.monitor.surtVehicle(id);
    }

    @Override
    public void run() {
        try {
            //Presentam el cotxe
            System.out.println("El cotxe " + id + " està en ruta direccio " + dir);
            esperaRandom(450);//simulam el temps que tarda a arribar al pont
            demanaAcces();
            espera(3000);//simulam el temps que tarda a creuar el pont
            surtPont();

        } catch (InterruptedException ex) {
            System.out.println("ERROR EXCEPCIO: " + ex.toString());
        }
    }

}
