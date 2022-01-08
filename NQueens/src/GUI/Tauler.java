/*
CLASSE TAULER.
FUNCIONALITAT: implementa el disseny d'un panell de escacs.
 */
package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

/**
 * AUTOR: Joan Balaguer, Jaume Adrover 
 * DATA CREACIÓ: 18/12/2021
 */
public class Tauler extends JPanel {
    //ATRIBUTS DE CLASSE
    private static final int DIMENSIO = 500;
    private int tamany;
    private final int COSTAT;
    private final Casella matriu[][];
    /*
    CONSTRUCTOR: rep per paràmetre la dimensió del tauler, i el construeix a partir
    d'objectes Rectangle2D.Float.
    */
    public Tauler(int n) {
        tamany = n;
        COSTAT = DIMENSIO / tamany;
        matriu = new Casella[tamany][tamany];

        int y = 0;
        for (int i = 0; i < n; i++) {
            int x3 = 0;
            for (int j = 0; j < n; j++) {
                Rectangle2D.Float r = new Rectangle2D.Float(x3, y, COSTAT, COSTAT);
                int x = i + j;
                if (x % 2 == 0) {
                    matriu[i][j] = new Casella(r, Color.WHITE);
                } else {
                    matriu[i][j] = new Casella(r, Color.BLACK);
                }

                x3 += COSTAT;
            }
            y += COSTAT;
        }
    }
    /*
    Mètode que ens retorna una casella a partir de la seva fila i columna.
    */
    public Casella getCasella(int x, int y) {
        return matriu[x][y];
    }
    /*
    Mètode que ens canvia l'estat d'una casella
    */
    public void setCasellaTo(int x,int y ,boolean bln){
        matriu[x][y].setOcupadaTo(bln);
    }
    /*
    Mètode que ens retorna la dimensió del costat d'una casella.
    */
    public int getCostat() {
        return COSTAT;
    }
    /*
    Mètode que ens retorna el tamany del tauler(N)
    */
    public int getTamany() {
        return tamany;
    }
    /*
    Mètode per a resetear els valors del tauler.
    */
    public void resetTauler(){
        for (int i = 0; i < matriu.length; i++) {
            for (int j = 0; j < matriu.length; j++) {
                matriu[i][j].setOcupadaTo(false);
            }
            
        }
    }
    /*
    Mètode per a retornar les dimensions del tauler i aplicar-les al JFrame, per 
    tal de que encaixin.
    */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DIMENSIO + 5, DIMENSIO + 35);
    }
    /*
    Mètode per a pintar el tauler
    */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < tamany; i++) {
            for (int j = 0; j < tamany; j++) {
                matriu[i][j].paintComponent(g);
            }
        }
    }
    /*
    Mètode toString per a verificar per terminal si la matriu ha estat resolta
    correctament, ús de DEBUGGER.
    */
    @Override
    public String toString(){
        String s="";
        for (int i = 0; i < matriu.length; i++) {
            for (int j = 0; j < matriu.length; j++) {
                s+=matriu[i][j].isOcupada()+" ";
            }
            s+="\n";
        }
        return s;
    }
}
