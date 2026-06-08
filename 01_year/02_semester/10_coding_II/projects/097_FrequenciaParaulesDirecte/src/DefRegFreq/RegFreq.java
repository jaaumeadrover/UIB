package DefRegFreq;

import DefParaula.Paraula;
import java.io.File;
import java.io.RandomAccessFile;

/**
 * Classe de registre de freqüència de paraules El registre de freqüències es
 * defineix com un fitxer d'accéss directe el constructor el crea i després el
 * tanca
 *
 * @author miquelmascarooliver
 */
public class RegFreq {

    private static final int MIDAREG = (Paraula.MAXIM * 2) + 4;
    private static File arxiu = new File("RegFreq.dat");

    private RandomAccessFile f;

    /**
     * El cosntructor obre el fitxer en mode lectura / escriptura
     *
     * @throws Exception
     */
    public RegFreq() throws Exception {
        f = new RandomAccessFile(arxiu, "rw");
        f.close();
    }

    /**
     * El mètode actualitza fa l'actualització del fitxer per una paraula que ve
     * com a paràmetre. El programa és una cerca de la paraula dins l'estructura
     * de dades. El final del fitxer es controla sabent el nombre de registres
     * que hi ha, hem de tenir en compte per calcular-ho que cada caracter de la
     * paraula MAXIM = 20 val 2 bytes i l'enter que marca la freqüencia 4 bytes,
     * per tant el nombre de registres serà la grandària del fitxer dividit pel
     * tamany de cada registre. Dins la repetició de la cerca llegir un registre
     * vol dir llegir els 20 caracters de les lletres de la paraula i l'enter de
     * la seva freqüència. Llegim els 20 caracters a la cadena s i amb ella
     * formam una paraula p2 que és la que anam comparant amb la que ens passen
     * per paràmetre. Si hem trobat la paraula al registre ens posicionam amb el
     * cursor al registre en concret sino anam al final i escrivim les dades que
     * actualitzen el registre de freqüències
     *
     * @param p1 Paraula que ha d'actualitzar el registre de freqüències
     */
    public void actualitza(Paraula p1) {
        try {
            int freq = 0;
            String s = "";
            f = new RandomAccessFile(arxiu, "rw");
            boolean trobat = f.length() == 0;
            long numreg = f.length() / MIDAREG;
            int r = 0;
            while (!trobat && r < numreg) {
                Paraula p2 = new Paraula();
                for (int i = 0; i < Paraula.MAXIM; ++i) {
                    s += f.readChar();
                }
                p2.extreuParaula(s);
                s = "";
                freq = f.readInt();
                trobat = p2.esIgualA(p1);
                r++;
            }
            if (trobat) {
                if (f.length() != 0) {
                    r--;
                }
                freq++;
                f.seek(r * MIDAREG);

            } else {
                freq = 1;
                f.seek(f.length());
            }
            f.writeChars(p1.toStringMaxim());
            f.writeInt(freq);
            f.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * El mètode escriu és un recorregut del fitxer. Calculam el nombre de
     * registres i anam llegint la cadena i la freqüencia treguent les dades a
     * una cadena. Al final esborram el contingut del fitxer.
     *
     * @return
     */
    public String escriu() {
        String s = "";
        try {
            int freq;
            f = new RandomAccessFile(arxiu, "r");
            long numreg = f.length() / MIDAREG;
            for (int r = 0; r < numreg; r++) {
                Paraula p = new Paraula();
                for (int i = 0; i < Paraula.MAXIM; ++i) {
                    p.afegirCaracter(f.readChar());
                }
                freq = f.readInt();
                s += "La paraula " + p + " apareix " + freq
                        + " vegades\n";
            }
            f.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            arxiu.deleteOnExit();
        }
        return s;
    }
}
