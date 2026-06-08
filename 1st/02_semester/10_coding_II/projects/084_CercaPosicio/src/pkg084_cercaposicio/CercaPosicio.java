/*
 * Cerca per posició a un fitxer de persones emprant la tènica del centinel·la
 * Es van llegint de forma seqüencial els registres fins arribar al que ens han
 * indicat
 * Es declara una excepció per controlar accessos a posicions inexistents
 * L'algorisme és una cerca del registre nombre pos si abans s'arriba al
 * centinel·la llavors el registre no hi es en cas contrari es treuen
 * les seves dades per pantalla
 */
package pkg084_cercaposicio;

/**
 *
 * @author miquelmascaro
 *
 */
import DefPersona.Persona;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;


public class CercaPosicio {

    /**
     * Excepción que s'activarà quan no existeixi la posició
     */
    public static class PosicioIncorrecteException extends Exception {
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        int pos = llegirEnter("Insereix posició: ");
        cercaPos("persones.dat", pos);
    }

    private static void cercaPos(String nom, int p) {
        try {
            FileInputStream entrada = new FileInputStream(nom);
            ObjectInputStream ois = new ObjectInputStream(entrada);
            if (p <= 0) {
                throw new PosicioIncorrecteException();
            }
            int x = 1;
            Persona per = (Persona) ois.readObject();
            while (x < p && !per.esCentinela()) {
                per = (Persona) ois.readObject();
                x++;
            }
            if (x == p && !per.esCentinela()) {
                System.out.println("Registre trobat: " + per);
            } else {
                System.out.println("Registre no trobat");
            }
            ois.close();
        } catch (PosicioIncorrecteException pie) {
            System.out.println("ERROR: Posició inexistent");
        } catch (IOException e) {
            System.out.println("ERROR d'entrada/sortida: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR de classe: " + e.getMessage());
        }
    }

    private static int llegirEnter(String msg) {
        int x = 0;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print(msg);
            String s = in.readLine();
            x = Integer.parseInt(s);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return x;
    }
}
