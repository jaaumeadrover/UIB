/*
 * Exemple de fusió simètrica de dos fitxers emprant la tècnica del centinel·la
 * Els fitxers que usa aquest algorisme han estats creats amb l'algorisme de
 * cercaClau
 * L'algorisme s'anomena simètric ja que és un recorregut dels dos fitxers
 */
package pk088_fusiosimetrica;

/**
 *
 * @author miquelmascarooliver
 */
import DefPersona.Persona;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class FusioSimetrica {

    public static void main(String[] args) {
        System.out.println("\nInsereix les dades del primer fitxer");
        int n = llegirNum("Quans registres vols entrar? ");
        insereixPersones("f1.dat", n);
        System.out.println("\nInsereix les dades del segon fitxer");
        n = llegirNum("Quans registres vols entrar? ");
        insereixPersones("f2.dat", n);
        System.out.println("\nLes dades del primer fitxer");
        mostra("f1.dat");
        System.out.println("\nLes dades del segon fitxer");
        mostra("f2.dat");
        fusiona();
        System.out.println("\nLes dades del fitxer fusió");
        mostra("f3.dat");
    }

    private static void insereixPersones(String nom, int n) {
        try {
            FileOutputStream sortida = new FileOutputStream(nom);
            ObjectOutputStream oos = new ObjectOutputStream(sortida);
            System.out.println("Els codis de les persones s'han d'inserir de menor a major!");
            for (int i = 1; i <= n; i++) {
                String s = llegirCadena("Nom de la persona: ");
                int x = llegirNum("Codi de la persona: ");
                Persona p = new Persona(s, x);
                oos.writeObject(p);
                System.out.println("Objecte inserit: " + p);
            }
            oos.writeObject(Persona.centinela);
            oos.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void fusiona() {
        try {
            FileInputStream entrada1 = new FileInputStream("f1.dat");
            ObjectInputStream f1 = new ObjectInputStream(entrada1);
            FileInputStream entrada2 = new FileInputStream("f2.dat");
            ObjectInputStream f2 = new ObjectInputStream(entrada2);
            FileOutputStream sortida = new FileOutputStream("f3.dat");
            ObjectOutputStream f3 = new ObjectOutputStream(sortida);
            Persona x1, x2;
            x1 = (Persona) f1.readObject();
            x2 = (Persona) f2.readObject();
            while (!x1.esCentinela() || !x2.esCentinela()) {
//                if (x1.codiMenor(x2)) {
                if (x1.getCodi() < x2.getCodi()) {
                    f3.writeObject(x1);
                    x1 = (Persona) f1.readObject();
                } else {
                    f3.writeObject(x2);
                    x2 = (Persona) f2.readObject();
                }
            }
            f3.writeObject(Persona.centinela);
            f1.close();
            f2.close();
            f3.close();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void mostra(String s) {
        try {
            FileInputStream entrada = new FileInputStream(s);
            ObjectInputStream ois = new ObjectInputStream(entrada);

            Persona p = (Persona) ois.readObject();
            while (!p.esCentinela()) {
                System.out.println(p);
                p = (Persona) ois.readObject();
            }
            ois.close();
            System.out.println("A reveure");
        } catch (Exception e) {
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
