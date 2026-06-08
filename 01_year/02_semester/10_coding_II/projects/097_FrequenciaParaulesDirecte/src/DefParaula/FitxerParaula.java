package DefParaula;

import java.io.*;

/**
 * Classe Fitxer de paraules
 *
 * @author miquelmascarooliver
 */
public class FitxerParaula {

    /**
     * Conté els següents atributs: x: Enter que s'usarà llegir del fitxer de
     * text fin: fitxer de text on hi haurà les parules
     */
    private int x;
    private FileReader fin = null;

    /**
     * El constructor FitxerParaula crida al constructor de FileReader i llegeix
     * fins al principi de la primera paraula si n'hi ha cap o fins al final de
     * fitxer en cas conrari
     *
     * @param nom
     * @throws Exception
     */
    public FitxerParaula(String nom) throws Exception {
        fin = new FileReader(nom);
        x = fin.read();
        principiParaula();
    }

    /**
     * El mètode TancaFitxer tanca el fitxer de caràcters
     *
     * @throws Exception
     */
    public void TancaFitxer() throws Exception {
        fin.close();
    }

    /**
     * El mètode llegir retorna una paraula del fitxer, per això cerca el final
     * de la paraula, en aquest cas s'ha suposat que no hi ha signes de
     * puntuació diferents de planc i que la paraula mai serà més gran del
     * previst. El programa és una cerca del final de la paraula Després de
     * llegir la paraula ens posicionam al principi de la següent o al fi de
     * fitxer
     *
     * @return
     */
    public Paraula llegir() {
        Paraula p = new Paraula();
        char c = (char) x;
        try {
            while ((c != ' ') && (c != '\n') && (c != '\r') && (x != -1)) {
                p.afegirCaracter(c);
                x = fin.read();
                c = (char) x;
            }
            principiParaula();

        } catch (Exception e) {
        }
        return p;
    }

    /**
     * El mètode principiParaula és privat i és una cerca del principi de
     * paraula
     *
     * @throws Exception
     */
    private void principiParaula() throws Exception {
        char c = (char) x;
        while (c == ' ' || c == '\n' || c == '\r') {
            x = fin.read();
            c = (char) x;
        }
    }

    /**
     * El mètode quedenParaules retorna vertader si no s'està al fi de fitxer
     *
     * @return
     */
    public boolean QuedenParaules() {
        return x != -1;
    }
}
