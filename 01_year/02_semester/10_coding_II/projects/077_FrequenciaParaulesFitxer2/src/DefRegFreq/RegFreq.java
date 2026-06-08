/*
 * 
 */
package DefRegFreq;

import DefParaula.*;

/**
 * Classe registre de frequencies de paraules
 * @author miquelmascaro
 */
public class RegFreq {

    /**
     *
     */
    public static final int MAX = 1000;
    // ATRIBUTS
    private Paraula[] ref;
    private int[] fre;
    private int num;

    // INTERFICIE

    /**
     * Quan es creen estan inicialitzades amb zero elements
     */
        public RegFreq() {
        ref = new Paraula[MAX];
        fre = new int[MAX];
        num = 0;
    }

    /**
     * Cerca amb centinel.la la paraula p dins la taula de referencies
     * Si el troba incrementa la seva frequencia si no l'inclou
     * inicialitzant la frequencia a 1 i incrementant el nombre d'elements
     * @param p
     */
        public void actualitza(Paraula p) {
        int i = 0;

        ref[num] = p;
        while (!p.esIgualA(ref[i])) {
            i++;
        }
        if (i < num) {
            fre[i]++;
        } else {
            fre[num] = 1;
            num++;
        }
    }

    /**
     * Recorregut de l'estructura de dades des de el primer fins a l'element
     * que indica num-1
     */
        public void escriu() {
        for (int i = 0; i < num; i++) {
            System.out.println("La paraula '" + ref[i] + "'"
                    + " apareix " + fre[i] + " vegades");
        }
    }
}