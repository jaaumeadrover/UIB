/*
CLASSE Casella.
FUNCIONALITAT: implementa el diseny d'una casella del tauler.
 */
package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * AUTOR: Joan Balaguer, Jaume Adrover
 * DATA CREACIÓ: 18/12/2021
 */
public class Casella {

    private final Rectangle2D.Float rect;
    private final Color color;
    private boolean ocupada;
    private Pesa pesa;

    public Casella(Rectangle2D.Float r, Color c) {
        rect = r;
        color = c;
        ocupada = false;
    }

    /*
    Mètode per afegir una peça a una casella determinada.
     */
    public void setPesa(Pesa p) {
        pesa = p;
        ocupada = true;
    }
    /*
    Mètode que posa el boolean ocupat a true
    */
    public void setOcupadaTo(boolean bln){
        ocupada=bln;
    }
    /*
    Mètode que ens diu si una casella està ocupada o no.
    */
    public boolean isOcupada(){
        return ocupada;
    }
    /*
    Mètode per a pintar la casella amb la seva imatge corresponent.
    */
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(color);
        g2d.fill(this.rect);

        if (ocupada) {
            g2d.drawImage(pesa.getImatge(), (int) rect.x + 10, (int) rect.y + 10, null);
        }
    }
}
