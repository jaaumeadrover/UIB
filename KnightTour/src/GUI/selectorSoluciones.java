/*
CLASSE SELECTORSOLUCIONS.
FUNCIONALITAT: implementa una finestra per tal d'elegir les possibles solucions obtingudes
en el programa principal.
 */
package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import Main.Main;

/**
 * AUTOR: Joan Balaguer, Jaume Adrover
 * DATA CREACIÓ: 18/12/2021
 */
public class selectorSoluciones extends JFrame {
    private final Main principal;
    private ArrayList<JButton> botons;
    private final int victories;
    private int opcioActual;

    public selectorSoluciones(Main frame, int wins) {
        //config
        this.setResizable(false);
        this.setTitle("Joc laberint");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.yellow);
        this.setLocationRelativeTo(frame);
        this.setLayout(null);
        //values
        principal=frame;
        victories = wins;
        opcioActual = 0;
        //components
        initComponents();
    }

    private void initComponents() {
        int i;
        //TÍTOL
        JLabel title=new JLabel("POSSIBLES SOLUCIONS: "+victories);
        title.setFont(new Font("Arial Times",Font.PLAIN,14));
        title.setBounds(75, 0, 175, 100);
        this.getContentPane().add(title);
        //BOTONS
        for (i = 0; i < victories; i++) {
            JButton boto = new JButton(""+(i+1));
            boto.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent me) {
                    opcioActual=(Integer.parseInt(boto.getText())-1);
                    principal.canviarSolucio();
                }

                @Override
                public void mousePressed(MouseEvent me) {
                }

                @Override
                public void mouseReleased(MouseEvent me) {
                }

                @Override
                public void mouseEntered(MouseEvent me) {
                }

                @Override
                public void mouseExited(MouseEvent me) {
                }
            });
            boto.setBounds(i * 100, victories * 65, 100, 75);
            this.getContentPane().add(boto);
        }
        this.setSize(victories * 100, victories * 100);
    }
    /*
    Retorna la opció elegida actualment, per defecte és la primera.
    */
    public int getOpcio() {
        return opcioActual;
    }

}
