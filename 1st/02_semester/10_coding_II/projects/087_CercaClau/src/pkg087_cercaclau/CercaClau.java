/*
 * Exemple de cerca per camp clau dins fitxers ordenats
 * A més hi ha un mètode que insereix les dades de n persones i un altre que
 * demana el codi a cercar. Les dades s'inseriran de forma
 * correcte amb els codis ordenats i seran menors que el centinel·la.
 * Hi ha dos exemples de com referir-se a la
 * informació privada del codi: amb un mètode específic menorCodi o amb el
 * getter.
 * Es interessant veure com en aquesta cerca es pot usar la relació d'ordre
 * en el sentit de fer menys comparacions
 */
package pkg087_cercaclau;

/**
 *
 * @author miquelmascarooliver
 */
import java.io.*;
import DefPersona.*;

public class CercaClau {

    public static void main(String[] args) {
        int n = llegirNum("\nQuans registres vols entrar? ");
        insereixPersones(n);
        int codi = llegirNum("\n\nInsereix el codi a cercar: ");
        cercaKey(codi);
    }

    private static void insereixPersones(int n) {
        try {
            FileOutputStream sortida = new FileOutputStream("persones.dat");
            ObjectOutputStream oos = new ObjectOutputStream(sortida);
            System.out.println("Els codis de les persones es generen automàticament\n");
            int x = 1;
            for (int i = 1; i <= n; i++) {
                String s = llegirCadena("Nom de la persona: ");
                Persona p = new Persona(s, x);
                oos.writeObject(p);
                System.out.println("Objecte inserit: " + p);
                x++;
            }
            oos.writeObject(Persona.centinela);
            oos.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void cercaKey(int cod) {
        try {
            FileInputStream entrada = new FileInputStream("persones.dat");
            ObjectInputStream ois = new ObjectInputStream(entrada);
            Persona p = (Persona) ois.readObject();
            while (p.codiMenor(cod) && !p.esCentinela()) {
          //while (p.getCodi() < cod &&  !p.esCentinela()) {   
                p = (Persona) ois.readObject();
            }
            if (p.getCodi() == cod && !p.esCentinela()) {
                System.out.println("Registre trobat: " + p);
            } else {
                System.out.println("No hi ha cap registre amb aquest codi");
            }
            ois.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
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
