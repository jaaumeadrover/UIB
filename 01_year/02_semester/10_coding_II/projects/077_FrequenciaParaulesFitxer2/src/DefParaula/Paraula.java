package DefParaula;

/**
 * Classe Paraula
 * Conté els seüents atributs:
 * MAXIM: marca la màxima longitd de la paraula, es
 * suposa que cap paraula serà més gran que aquest màxim no hi ha el control
 * d'errors si no es cumpleix aquesta suposició
 * lletres: és la taula que conté les lletres de la paraula
 * longitud: és la longitud de la paraula (el nombre de lletres que té
 * 
 * @author miquelmascaro
 */
public class Paraula {

    private static final int MAXIM = 20;
    private char[] lletres;
    private int longitud;

    /**
     * El constructor Paraula crea una objecte paraula de longitud 0 (buida)
     */
    public Paraula() {
        lletres = new char[MAXIM];
        longitud = 0;
    }

    /**
     * passa la paraula a cadena de caràcters
     * @return 
     */
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < longitud; i++) {
            s += lletres[i];
        }
        return s;
    }

    /**
     * Afegeix una lletra que es passa com a paràmetre a la paraula
     * @param c
     */
    public void afegirCaracter(char c) {
        if (longitud < MAXIM) {
            lletres[longitud] = c;
            longitud++;
        }
    }

    /**
     * compara l'objecte amb la paraula passada per paràmetre
     * i retorna vertader si són iguals. És la cerca d'un caràcter diferent
     * @param p
     * @return
     */
    public boolean esIgualA(Paraula p) {
        int i = 0;
        while (lletres[i] == p.lletres[i] && i < longitud) {
            i++;
        }
        return lletres[i] == p.lletres[i];
    }

    /**
     * retorna vertader si la parula té longitud zero
     * @return
     */
    public boolean buida() {
        return longitud == 0;
    }

}
