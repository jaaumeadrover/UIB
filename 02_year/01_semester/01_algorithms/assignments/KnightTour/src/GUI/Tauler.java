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
    Mètode constructor que rep per paràmetre la dimensió N del tauler
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
        initOrdres();
    }
    /*
    Inicialitzam la matriu amb ordres tot a 0, indicant que encara no hi ha
    passat ningú.
    */
    private void initOrdres() {
        for (int i = 0; i < matriu.length; i++) {
            for (int j = 0; j < matriu.length; j++) {
                matriu[i][j].setOrdre(0);
            }
        }
    }
    /*
    Mètode per a obtenir la casella a partir de la seva fila i columna.
    */
    public Casella getCasella(int x, int y) {
        return matriu[x][y];
    }
    /*
    Mètode que ens permet copiar el contingut d'un tauler a un altre, ja que no 
    es pot fer de la següent manera(taulell=t), ja que les caselles no canvien 
    el seu contingut.
    */
    public void setTauler(Tauler t) {
        for (int i = 0; i < matriu.length; i++) {
            for (int j = 0; j < matriu.length; j++) {
                matriu[i][j] = t.getCasella(i, j);
            }
        }
    }
    /*
    Mètode per a resetear el tauler(totes les caselles sense transitar)
    */
    public void resetTauler() {
        for (int i = 0; i < matriu.length; i++) {
            for (int j = 0; j < matriu.length; j++) {
                matriu[i][j].setTransitadaTo(false);
                matriu[i][j].setOrdre(0);
            }
        }
    }
    /*
    Mètode per a canviar l'estat d'una casella, tant el boolea, com l'ordre.
    */
    public void setCasellaTo(int x, int y, boolean bln, int ordre) {
        matriu[x][y].setOrdre(ordre);
        matriu[x][y].setTransitadaTo(bln);
    }
    /*
    Mètode que retorna la dimensió de cada casella
    */
    public int getCostat() {
        return COSTAT;
    }
    /*
    Mètode que retrona el nombre d files i columnes del tauler.
    */
    public int getTamany() {
        return tamany;
    }
    /*
    Mètode per a verificar si dos taulers són iguals, comprovant un a un, si una 
    parella ja és diferent, els taulers ja no poden ser iguals.Si tots els elements
    son iguals, retornam true;
    */
    public boolean esIgual(Tauler t) {
        for (int i = 0; i < tamany; i++) {
            for (int j = 0; j < tamany; j++) {
                if (t.getCasella(i, j).getOrdre() != (matriu[i][j].getOrdre())) {
                    return false;
                }
            }
        }

        return true;
    }
    /*
    Retornam la dimensió del JPanel, per a poder ajustar-la al programa principal.
    */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DIMENSIO + 5, DIMENSIO + 20);
    }
    /*
    Mètode per a dibuixar el taulell al JFrame, consisteix en dibuixar totes les caselles.
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
    Mètode per a verificar el contingut del tauler a través de la terminal, ús 
    principalment en debugger.
    */
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < matriu.length; i++) {
            for (int j = 0; j < matriu.length; j++) {
                s += matriu[i][j].isTransitada() + ":" + matriu[i][j].getOrdre() + " ";
            }
            s += "\n";
        }
        return s;
    }
}
