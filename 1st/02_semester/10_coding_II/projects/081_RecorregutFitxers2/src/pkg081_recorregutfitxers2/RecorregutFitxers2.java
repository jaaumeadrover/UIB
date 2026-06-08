package pkg081_recorregutfitxers2;

/**
 * Recorregut d'un fitxer de persones
 *
 * Exemple de l'us de Object Stream, cal que la classe que defineix
 * l'objecte sigui serialitzable
 * El codi demana les dades de tres objectes persona els enregistre al
 * fitxer i després els mostra per pantalla.
 * Quan es llegeix el darrer objecte s'activa el fi de fitxer
 * @author miquelmascarooliver
 */
import DefPersona.Persona;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author miquelmascarooliver
 */
public class RecorregutFitxers2 {

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        try {

            FileOutputStream sortida = new FileOutputStream("persones.dat");
            ObjectOutputStream oos = new ObjectOutputStream(sortida);

            for (int i = 1; i < 4; i++) {
                Persona p = new Persona();
                String s = llegirCadena();
                int x = llegirNum();
                p.setNom(s);
                p.setCodi(x);
                oos.writeObject(p);
            }
            oos.close();

            FileInputStream entrada = new FileInputStream("persones.dat");
            ObjectInputStream ois = new ObjectInputStream(entrada);
            /*
             * Primera versió amb l'error del fí de fitxer
             */
            Persona p = (Persona) ois.readObject();
            while (p != null) {
                System.out.println(p);
                p = (Persona) ois.readObject();
            }
            ois.close();
            System.out.println("A reveure");
        } catch (IOException e) {
            System.out.println("ERROR: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        /*
         * Segona versió amb el tractament del fí de fitxer com a recuperació
         * de l'excepció pertinent
         */
//            Persona p;
//            boolean fidefitxer = false;
//            do {
//                try {
//                    p = (Persona) ois.readObject();
//                    System.out.println(p);
//                } catch (EOFException eofe) {
//                    fidefitxer = true;
//                }
//            } while (!fidefitxer);
//            ois.close();
//            System.out.println("A reveure");
//        } catch (Exception e) {
//            System.out.println("ERROR: " + e.getMessage());
//        }

    }

    private static String llegirCadena() {
        String s = "";
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Nom: ");
            s = in.readLine();
        } catch (IOException ex) {
            Logger.getLogger(RecorregutFitxers2.class.getName()).log(Level.SEVERE, null, ex);
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
