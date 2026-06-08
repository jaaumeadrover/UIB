/*
 * Recorregut d'un fitxer de persones
 * Es defineix un fitxer de persones com un fluxe de bytes, el fitxer
 * persones.dat ha estat creat amb un editor de text posant a una línia
 * el nom i a l'altre el codi, és adir llegir les dades d'una persona és llegir
 * les dues línies corresponents a la seva informació
 */
package pkg080_recorregutfitxers;

/**
 *
 * @author miquelmascarooliver
 */
import DefPersona.Persona;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class RecorregutFitxers {

    public static void main(String[] args) {
        try {
            Persona p = new Persona();
            String s;
            FileInputStream entrada;
            BufferedReader in;

            entrada = new FileInputStream("persones.dat");
            in = new BufferedReader(new InputStreamReader(entrada));
            s = in.readLine();
            while (s != null) {
//            while (in.ready()) {
                p.setNom(s);
                p.setCodi(Integer.parseInt(in.readLine()));
                System.out.println(p);
                s = in.readLine();
            }
            in.close();
            System.out.println("A reveure");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }


    }
}
