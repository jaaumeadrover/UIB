/*
 * Ordenació de taules amb el mètode de la bombolla millorada.
 * Millora la bombolla de manera que quan es detecta que no s'ha canviat cap
 * element l'algorisme acaba.
 * En lloc d'incrementar un a un l'index i usa una variable lj (last j) que
 * guarda la posició del darrer intercamvi, quan se'n produeix almenys un j=i
 * si no n'hi ha cap lj=N i acaba la repetició.
 */
package _ordenaciotaules4;

/**
 *
 * @author miquelmascarooliver
 */
import java.util.Random;

public class OrdenacioTaules4 {

    public static void emplenarAleatoriament(Dades[] t, int vsup) {
        Random rnd = new Random();
        for (int i = 0; i < t.length; i++) {
            t[i] = new Dades(rnd.nextInt(vsup));
        }
    }

    public static String escriureTaula(Dades[] t) {
        String resultat = "";
        for (int i = 0; i < t.length; i++) {
            resultat += t[i].clau + " ";
        }
        return resultat;
    }

    private static void bombollaMillorada(Dades[] t) {
        final int N = t.length;
        Dades x;
        int i = 0;
        while (i < N - 1) {
            int lj = N;
            for (int j = N - 2; j >= i; j--) {
                if (t[j + 1].clau < t[j].clau) {
                    x = t[j + 1];
                    t[j + 1] = t[j];
                    t[j] = x;
                    lj = j;
                }
            }
            i = lj + 1;
            System.out.println("Índex " + i + ": " + escriureTaula(t));
        }
    }

    public static void main(String[] args) {
        final int N = 10;
        Dades t[] = new Dades[N];
        emplenarAleatoriament(t, 100);
        System.out.println("Exemple d'ordenació de taules amb el mètode"
                + " de la bombolla millorada\n");
        System.out.println("Valors a ordenar: " + escriureTaula(t) + "\n");
        bombollaMillorada(t);
        System.out.println("\nTaula ordenada: " + escriureTaula(t));
    }
}
