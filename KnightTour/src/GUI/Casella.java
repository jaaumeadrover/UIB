/*
CLASSE Casella.
FUNCIONALITAT: implementa el diseny d'una casella del tauler.
OBSERVACIONS: ha estat adaptada al projecte de NCavalls, ja que no necessitam imatges,
sino nombres per tal de designar l'ordre del trànsit de la peça.
 */
package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 AUTOR: Joan Balaguer, Jaume Adrover.
 DATA CREACIÓ: 18/12/2021
 */
public class Casella {
    //ATRIBUTS DE CLASSE
    private final Rectangle2D.Float rect;
    private final Color color;
    private boolean transitada;
    private int ordre;
    //CONSTRUCTOR
    public Casella(Rectangle2D.Float r, Color c) {
        rect = r;
        color = c;
        transitada = false;
    }
    /*
    `Mètode per a marcar la casella com a ja visitada.
    */
    public void setTransitadaTo(boolean bln){
        transitada=bln;
    }
    /*
    Mètode que assigna l'ordre d'arribada del cavall a la casella.
    */
    public void setOrdre(int x){
        ordre=x;
    }
    public int getOrdre(){
        return ordre;
    }
    /*
    Mètode per a verificar si la casella ja ha estat visitada.
    */
    public boolean isTransitada(){
        return transitada;
    }
    /*
    Mètode que ens pinta la casella al tauler, amb el seu ordre respectivament
    */
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(color);
        g2d.fill(this.rect);

        if (transitada) {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial Times",Font.PLAIN,24));
            g2d.drawString(Integer.toString(ordre),rect.x,rect.y+20);
        }
    }
}
