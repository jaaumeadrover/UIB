/*
Classe Laberint, que descriu les característiques del taulell on l'usuari jugarà al 
joc 
 */
package practfinalprog2rebuild;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Random;
import javax.swing.JPanel;

public class Laberint extends JPanel {

    //Atributs de la classe
    private static final int DIMENSIOX = 500;
    private static final int DIMENSIOY = 750;
    private final int COSTAT;
    private FitxerLaberints lector;
    //array bidimensional de objectes casella
    private Casella[][] matriu;
    private static int files, columnes;
    //array que ens indicarà les coordenades de la sortida
    private static int[] coordsSortida;
    //array que ens indicarà les coordenades de la fitxa
    private static int[] coordsFitxa;

    /**
     * Mètode constructor elqual construeix un laberint a partir del nom del
     * Fitxer que li passin per paràmetre
     *
     * @param fitxer
     * @throws FileNotFoundException
     * @throws IOException
     */
    public Laberint(String fitxer) throws FileNotFoundException, IOException {
        this.setBackground(new Color(225, 225, 0));
        //inicialitzam el lector
        lector = new FitxerLaberints(fitxer);

        AssignarFilesColumnes();

        //inicialitzam l'atribut de COSTAT
        COSTAT = DIMENSIOY / files;

        //inicialitzam l'array bidimensional de caselles en funció del valor de files i columnes que 
        //haguem lletgit del fitxer
        matriu = new Casella[files][columnes];

        //feim un recorregut de les files i les columnes del array bidimensional
        //i anem creant objectes casella a mesura que anem passat per les 
        //diferents files i columnes de l'array
        int y = 0;
        for (int i = 0; i < files; i++) {
            int x3 = 0;
            for (int j = 0; j < columnes; j++) {

                Rectangle2D.Float r = new Rectangle2D.Float(x3, y, COSTAT, COSTAT);
                matriu[i][j] = new Casella(r, lector.LlegirParets());
                x3 += COSTAT;
            }
            y += COSTAT;
            lector.llegirLinea();
        }
        AssignarSortida();
        AssignarFitxa();
    }

    /**
     * Mètode en el qual lletgim la dimensió del laberint i després assignam els
     * nombres lletgits als atributs files i columnes
     *
     * @throws IOException
     */
    public void AssignarFilesColumnes() throws IOException {

        lector.LlegirDimensio();
        files = lector.getFiles();
        columnes = lector.getColumnes();
    }

    /**
     * Mètode amb el qual dibuixarem la sortida segons els valors que lletgirem
     * del fitxer
     *
     * @throws IOException
     */
    public void AssignarSortida() throws IOException {
        coordsSortida = new int[2];
        coordsSortida[0] = Integer.parseInt(lector.llegirLinea());
        coordsSortida[1] = Integer.parseInt(lector.llegirLinea());
        matriu[coordsSortida[0]][coordsSortida[1] - 1].inserir("Recursos/elements/exitImage.jpg");

    }

    /**
     * Mètode amb el qual dibuixarem la fitxa (sempre sortirà a la primera
     * columna)
     *
     * @throws IOException
     */
    public void AssignarFitxa() throws IOException {
        coordsFitxa = new int[2];
        Random rand = new Random();
        int x = rand.nextInt(files);
        matriu[x][0].inserir("Recursos/elements/negra.png");
        coordsFitxa[0] = x;
        coordsFitxa[1] = 0;
    }

    /**
     * Mètode que s'encarregarà de "moure" la fitxa, es a dir, de dibuixar o no
     * la fitxa a una casella o una altre en funció de l'String que li pasaràn
     * per paràmetre
     *
     * @param c
     * @throws IOException
     */
    public void moureFitxa(char c) throws IOException {

        switch (c) {
            case 'a':
                if (matriu[coordsFitxa[0]][coordsFitxa[1]].getParets()[3] == false) {

                    matriu[coordsFitxa[0]][coordsFitxa[1]].retirar();

                    matriu[coordsFitxa[0]][coordsFitxa[1] - 1].inserir("Recursos/elements/negra.png");

                    coordsFitxa[1]--;
                }
                break;

            case 'w':
                if (matriu[coordsFitxa[0]][coordsFitxa[1]].getParets()[0] == false) {

                    matriu[coordsFitxa[0]][coordsFitxa[1]].retirar();

                    matriu[coordsFitxa[0] - 1][coordsFitxa[1]].inserir("Recursos/elements/negra.png");

                    coordsFitxa[0]--;
                }
                break;

            case 's':
                if (matriu[coordsFitxa[0]][coordsFitxa[1]].getParets()[2] == false) {

                    matriu[coordsFitxa[0]][coordsFitxa[1]].retirar();

                    matriu[coordsFitxa[0] + 1][coordsFitxa[1]].inserir("Recursos/elements/negra.png");

                    coordsFitxa[0]++;
                }
                break;

            case 'd':
                if (matriu[coordsFitxa[0]][coordsFitxa[1]].getParets()[1] == false) {

                    matriu[coordsFitxa[0]][coordsFitxa[1]].retirar();

                    matriu[coordsFitxa[0]][coordsFitxa[1] + 1].inserir("Recursos/elements/negra.png");

                    coordsFitxa[1]++;
                }
                break;
        }

    }

    /**
     * Mètode amb el qual comprovarem si les coordenades de la sortida, son les
     * mateixes que les de la fitxa
     *
     * @return
     */
    public boolean checkVictoria() {
        if ((coordsSortida[0] == (coordsFitxa[0]) && (coordsSortida[1] - 1 == coordsFitxa[1]))) {
            return true;
        }
        return false;
    }

    /**
     * Definim i ajustam la dimensió del panell
     *
     * @return
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DIMENSIOX + 5, DIMENSIOY + 55);
    }

    /**
     * Mètode el qual s'encarrega de pintar cada una de les fitxes contingudes
     * disn l'array bidimensional de caselles
     *
     * @param g
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < files; i++) {
            for (int j = 0; j < columnes; j++) {
                matriu[i][j].paintComponent(g);
            }
        }
    }
}
