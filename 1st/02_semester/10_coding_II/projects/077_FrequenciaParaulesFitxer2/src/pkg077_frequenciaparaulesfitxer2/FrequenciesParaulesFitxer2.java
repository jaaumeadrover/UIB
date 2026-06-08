/*
 * Prorama que compta la frequencia de paraules d'un fitxer de paraules
 * S'usen les clases Fitxer de Paraula In, Paraula i Registre de freqüències
 * de forma anàloga a l'exemple de comtar freqüències de paraules des del teclat
 * El programa és un recorregut en termes de paraules, un cop obert el fitxer
 * feim la repetició mentre quedin paraules al fitxer, el procés repetitiu
 * consisteix en llegir una paraula del fitxer i amb aquesta actualitzar el
 * registre de freqüències
 * Al final escrivim el contingut de la registre de freqüències i tancam el
 * fitxer
 */
package pkg077_frequenciaparaulesfitxer2;

import DefParaula.*;
import DefRegFreq.*;

public class FrequenciesParaulesFitxer2 {

    public static void main(String[] args) {
        try {
            FitxerParaulaIn fp = new FitxerParaulaIn("prova3.txt");
            Paraula p;
            RegFreq f = new RegFreq();

            while (fp.QuedenParaules()) {
                p = fp.llegir();
                System.out.println("Paraula: " + p);
                f.actualitza(p);
            }
            f.escriu();
            fp.TancaFitxer();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
