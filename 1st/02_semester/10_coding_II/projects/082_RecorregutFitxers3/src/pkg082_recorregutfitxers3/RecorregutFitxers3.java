/*
 * Recorregut d'un fitxer de persones
 *
 * En primer lloc el programa crea tres registres. Al fial de la creació dels
 * registres s'afegeix un nou registre que és el centinel·la. Per això la classe
 * Persona té una constant que és centinela que és un registre amb el nom igual
 * a "zzz" i codi 999. (basta un camp)
 *
 * A l'hora de llegir els fitxers com a mínim sempre hi haurà un element per
 * tant és possible fer la primera lectura sense cap comprovació prèvia.
 * Per fer la repetició del recorregut no cal tenir en compt cap tipus de marca
 * de fí de fitxer ja que hi ha el centinel·la que ens marca el final.
 */
package pkg082_recorregutfitxers3;

/**
 *
 * @author miquelmascarooliver
 */
import DefPersona.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecorregutFitxers3 {

    public static void main(String[] args) {
        try {

            FileOutputStream sortida = new FileOutputStream("persones.dat");
            ObjectOutputStream oos = new ObjectOutputStream(sortida);

            for (int i = 1; i < 4; i++) {
                Persona p = new Persona(llegirCadena(), llegirNum());
                oos.writeObject(p);
            }
            oos.writeObject(Persona.centinela);
            oos.close();

            FileInputStream entrada = new FileInputStream("persones.dat");
            ObjectInputStream ois = new ObjectInputStream(entrada);

            Persona p = (Persona)ois.readObject();
            while (!p.esCentinela()) {
                System.out.println(p);
                p = (Persona) ois.readObject();
            }
            ois.close();
            System.out.println("A reveure");
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    
    
    private static String llegirCadena() {
        String s = "";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Nom: ");
            s = in.readLine();
        } catch (IOException ex) {
            Logger.getLogger(RecorregutFitxers3.class.getName()).log(Level.SEVERE, null, ex);
        }
        return s;
    }

    private static int llegirNum() {
        int x = 0;
        try {
            String s;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Codi: ");
            s = in.readLine();
            x = Integer.parseInt(s);
        } catch (IOException e) {
        } catch (NumberFormatException e) {
        }
        return x;
    }
}
