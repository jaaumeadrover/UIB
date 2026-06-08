/*
 * Classe Tauler definida com un panell on es defineix una taula 8x8 de caselles
 *
 * El constructor defineix totes les caselles com a rectangles d'un color
 * determinat inicialitzant-les buides
 *
 * El mètode paintComponent recorr el tauler pintant les caselles
 *
 * El mètode getPreferredSize retorna el tamant del tauler
 */
package _escacs2;

/**
 *
 * @author miquelmascaro
 */
import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.geom.Rectangle2D;

public class Tauler extends JPanel {

    private static final int DIMENSIO = 8;
    private static final int MAXIM = 800;
    private static final int COSTAT = MAXIM / DIMENSIO;
    private static final Color BLANC = Color.WHITE;
    private static final Color NEGRE = Color.BLACK;
    
    private Casella t[][];

    public Tauler() {
        
    }

    @Override
    public void paintComponent(Graphics g) {
        for (int i = 0; i < DIMENSIO; i++) {
            for (int j = 0; j < DIMENSIO; j++) {                
                t[i][j].paintComponent(g);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(MAXIM, MAXIM);
    }
}
