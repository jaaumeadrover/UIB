/*
 * Classe casella
 *
 * El constructor defineix una casella com un rectangle d'un color i si està
 * ocupada o no
 *
 * El mètode paintComponent pinta el rectangle de la casella
 */

package _escacs2;

/**
 *
 * @author miquelmascaro
 */
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

class Casella {
    
    private Rectangle2D.Float rec;
    private Color col;
    private Boolean ocupada;

    public Casella(Rectangle2D.Float r, Color c, Boolean ocu ) {
        this.rec = r;
        this.col = c;
        this.ocupada = ocu;
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(this.col);
        g2d.fill(this.rec);
    }

}
