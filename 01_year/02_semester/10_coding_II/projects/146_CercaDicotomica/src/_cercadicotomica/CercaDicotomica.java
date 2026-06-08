/*
 * Exemple de la cerca dicotòmica dins taules ordenades.
 *
 * S'usen dos índex que indiquen els límits superior i inferior de la taula es
 * calcula el punt intermig i si és l'element que cercam ja està si no
 * actualitzam el índex depenents is l'element a cercar és menor o major que el
 * punt intermig, si és menor ens quedam amb la part baixa de la taula i si és
 * major en la part alta.
 */
package _cercadicotomica;

/**
 *
 * @author miquelmascarooliver
 */
import java.util.Random;

public class CercaDicotomica {

    public static final int NoTrobat = -1;

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

    private static void espolsada(Dades[] t) {
        final int N = t.length;
        Dades x;
        int esq = 0, dre = N - 1;
        while (esq < dre) {
            int lj = dre;
            for (int j = dre - 1; j >= esq; j--) {
                if (t[j + 1].clau < t[j].clau) {
                    x = t[j + 1];
                    t[j + 1] = t[j];
                    t[j] = x;
                    lj = j;
                }
            }
            esq = lj + 1;
            for (int j = esq; j <= dre - 1; j++) {
                if (t[j + 1].clau < t[j].clau) {
                    x = t[j + 1];
                    t[j + 1] = t[j];
                    t[j] = x;
                    lj = j + 1;
                }
            }
            dre = lj - 1;
        }
    }

    private static int cercaDicotomica(Dades[] t, Dades x) {
        int inf = 0, sup = t.length - 1;
        int mig = (sup + inf) / 2;
        while ((inf < sup) && (t[mig].clau != x.clau)) {
            if (t[mig].clau < x.clau) {
                inf = mig + 1;
            } else {
                sup = mig - 1;
            }
            mig = (sup + inf) / 2;
        }
        return (t[mig].clau == x.clau ? mig : NoTrobat);
    }

    public static void main(String[] args) {
        final int N = 10;
        Dades t[] = new Dades[N];
        emplenarAleatoriament(t, 100);
        System.out.println("Exemple de cerca dins taules ordenades: "
                + "Cerca dicotòmica");
        espolsada(t);
        System.out.println("\nTaula ordenada: " + escriureTaula(t));
        Random rnd = new Random();
        Dades x = new Dades(rnd.nextInt(100));
        System.out.println("Element a cercar: " + x.clau);
        int pos = cercaDicotomica(t, x);
        if (pos != NoTrobat) {
            System.out.println("Element trobat a la posició: " + pos);
        } else {
            System.out.println("Element no trobat");
        }
    }
}
