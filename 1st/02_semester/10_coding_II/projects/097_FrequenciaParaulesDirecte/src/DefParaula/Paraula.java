package DefParaula;

/**
 * Classe Paraula
 *
 * @author miquelmascarooliver
 */
public class Paraula {

    /**
     * Conté els seüents atributs: MAXIM: marca la màxima longitd de la paraula,
     * es suposa que cap paraula serà més gran que aquest màxim no hi ha el
     * control d'errors si no es cumpleix aquesta suposició lletres: és la taula
     * que conté les lletres de la paraula longitud: és la longitud de la
     * paraula (el nombre de lletres que té
     */
    public static final int MAXIM = 20;
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
     * El mètode toString passa la paraula a cadena de caràcters
     *
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
     * Si la paraula no té la longitud màxima s'afageixen blancs fins que la
     * tingui
     * @return
     */
    public String toStringMaxim() {
        String s = "";
        for (int i = 0; i < longitud; i++) {
            s += lletres[i];
        }
        for (int i = longitud; i < MAXIM; i++) {
            s += " ";
        }
        return s;
    }

    /**
     * El mètode afegirCaracter efegeix una lletra que es passa com a paràmetre
     * a la paraula
     *
     * @param c
     */
    public void afegirCaracter(char c) {
        if (longitud < MAXIM) {
            lletres[longitud] = c;
            longitud++;
        }
    }

    /**
     * El mètode esIgualA compara l'objecte amb la paraula passada per paràmetre
     * i retorna vertader si són iguals. És la cerca d'un caràcter diferent
     *
     * @param p
     * @return
     */
    public boolean esIgualA(Paraula p) {
        boolean iguals = longitud == p.longitud;
        for (int i = 0; (i < longitud) && iguals; i++) {
            iguals = lletres[i] == p.lletres[i];
        }
        return iguals;
    }

    /**
     * El mètode buida retorna vertader si la parula té longitud zero
     *
     * @return
     */
    public boolean buida() {
        return longitud == 0;
    }

    /**
     * El mètode extreuParaula creauna paraula a partir d'una cadena que se li
     * passa per paràmetre
     *
     * @param s
     */
    public void extreuParaula(String s) {
        longitud = 0;
        char c = s.charAt(longitud);
        while (c != ' ' && longitud < MAXIM) {
            lletres[longitud] = c;
            longitud++;
            c = s.charAt(longitud);
        }

    }
}
