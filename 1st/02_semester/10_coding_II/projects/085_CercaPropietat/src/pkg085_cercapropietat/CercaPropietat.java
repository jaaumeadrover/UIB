/*
 * Cerca d'un registre que cumpleixi una determinada propietat, per exemple
 * que l'execució d'un mètode amb un registre retorni vertader. En el cas de
 * l'exemple que el nom tingui longitud 3.
 * NOTA: És important que el fitxer d'objectes estigui generat amb la mateixa
 * classe, això vol dir que si s'afegeix un mètode a una classe (longNomEs3 pe)
 * i no se generen o actualitzen els registres s'activarà una incompatiblilitat
 * de versió de la serialització.
 */
package pkg085_cercapropietat;

/**
 *
 * @author miquelmascaro
 *
 */
import DefPersona.Persona;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CercaPropietat {

    public static void main(String[] args) {
        try {
            creaFitxer();
            cercaProp();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void creaFitxer() {
        try {
            FileOutputStream sortida = new FileOutputStream("persones.dat");
            ObjectOutputStream oos = new ObjectOutputStream(sortida);

            for (int i = 0; i < 4; i++) {
                String s = llegirCadena("Insereix nom de la persona: ");
                int codi = llegirNum("Insereix codi de la persona: ");
                Persona p = new Persona(s, codi);
                oos.writeObject(p);
            }
            oos.writeObject(Persona.centinela);
            oos.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CercaPropietat.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(CercaPropietat.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private static void cercaProp() {
        try {
            FileInputStream entrada = new FileInputStream("persones.dat");
            ObjectInputStream ois = new ObjectInputStream(entrada);

            Persona p = (Persona) ois.readObject();
            boolean trobat = p.longNomEs3();
            while (!trobat && !p.esCentinela()) {
                p = (Persona) ois.readObject();
                trobat = p.longNomEs3();
            }
            if (trobat && !p.esCentinela()) {
                System.out.println("Registre trobat: " + p);
            } else {
                System.out.println("Registre no trobat");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String llegirCadena(String msg) {
        String s = null;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print(msg);
            s = in.readLine();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return s;

    }

    private static int llegirNum(String msg) {
        int x = 0;
        try {
            String s;
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            System.out.print(msg);
            s = in.readLine();
            x = Integer.parseInt(s);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Error: " + e.getMessage());
        }
        return x;
    }

}
