/*
Classe en la qual definim les característiques d'una casella
 */
package practfinalprog2rebuild;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;

public class Casella {
    //atributs de la classe Casella

    //atribut q ue representa el rectangle de la casella
    private Rectangle2D.Float rect;
    //array de booleans per saber si hi ha o no paret a cada un dels costats del rectangle
    private boolean[] blnparets = new boolean[4];
    //objecte de la classe Element per poder pintar la sortda i la fitxa 
    private Element element;
    //boolean que ens indicará si la casella està o no plena
    private boolean plena;

    /**
     * Mètode constructor de la classe Casella que li passam un rectangle i un
     * array de ints per paràmetre (que representa els quatre enters
     * corresponents a la lectura del fitxer de l'enucniat)
     *
     * @param r
     * @param x
     */
    public Casella(Rectangle2D.Float r, int[] x) {
        //inicialitzam l'atribut rectangle amb el rectangle que ens passen per paràmetre
        this.rect = r;
        //realitzam un recorregut per l'array d'enters per saber si hem de posar cada posició 
        //del array de booleans a true o false 
        for (int i = 0; i < x.length; i++) {
            if (x[i] == 49) {
                blnparets[i] = true;
            } else {
                blnparets[i] = false;
            }
        }
        //per defecte, la casella no conté cap element
        plena = false;
    }

    /**
     * Mètode amb el qual inserim un objecte element del tipus que ens indiqui
     * l'String que passam per paràmetre
     *
     * @param s
     * @throws IOException
     */
    public void inserir(String s) throws IOException {

        element = new Element(s);
        plena = true;
    }

    /**
     * Mètode que utilitzam per retirar l'element que hi pugui haver dins una
     * casella
     */
    public void retirar() {
        plena = false;
    }

    /**
     * Mètode que ens retorna l'array de booleans de la casella
     *
     * @return
     */
    public boolean[] getParets() {
        return blnparets;
    }

    /**
     * Mètode paint component que utilitzarem per pintar la casella en funció de
     * l'array de booleans de l'objecte Casella
     *
     * @param g
     */
    public void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        //inicialitzam les diferents parets que es poden dibuixar (N,E,S,O)
        Rectangle2D.Float recN = new Rectangle2D.Float(rect.x, rect.y, this.rect.width, 5);
        Rectangle2D.Float recE = new Rectangle2D.Float(rect.x + rect.width, rect.y, 5, this.rect.width);
        Rectangle2D.Float recS = new Rectangle2D.Float(rect.x, rect.y + rect.width, this.rect.width, 5);
        Rectangle2D.Float recO = new Rectangle2D.Float(rect.x, rect.y, 5, this.rect.width);

        g2d.setColor(new Color(155, 42, 42));

        g2d.fill(this.rect);

        //dibuixarem la paret si el booleà de la seva corresponent posició es troba a true
        if (blnparets[0] == true) {
            g2d.setColor(Color.yellow);
            g2d.fill(recN);
        }

        if (blnparets[2] == true) {
            g2d.setColor(Color.yellow);
            g2d.fill(recS);
        }

        if (blnparets[1] == true) {
            g2d.setColor(Color.yellow);
            g2d.fill(recE);
        }
        if (blnparets[3] == true) {
            g2d.setColor(Color.yellow);
            g2d.fill(recO);
        }
        //si la casella està plena, dibuixarem l'element que estigui emmagatzemat 
        //a l'atribut element
        if (plena) {
            g2d.drawImage(element.getImatge(), (int) rect.x + 10, (int) rect.y + 10, null);
        }

    }

}
