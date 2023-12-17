/*
CLASSE MAIN.
FUNCIONALITAT: 
 */
package Main;

/**
AUTOR: Joan Balaguer, Jaume Adrover
DATA CREACIÓ: 15/12/2021
 */
import NCaballos.NCaballos;
import GUI.Casella;
import GUI.Tauler;
import GUI.selectorSoluciones;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * AUTOR: Joan Balaguer, Jaume Adrover DATA CREACIÓ: 18/12/2021
 */
public class Main extends JFrame implements MouseListener {

    //ATRIBUTS DE CLASSE
    private NCaballos knightTour;
    private Tauler taulell;
    private int tamany;
    private selectorSoluciones selector;

    public Main() {
        this.setResizable(false);
        this.setTitle("Joc laberint");
        this.setDefaultCloseOperation(Main.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.yellow);
        this.setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        //elegir dimensio amb la variable tamany
        tamany = Integer.parseInt(JOptionPane.showInputDialog("Introdueix el tamany del tauler:"));
        //afegir panell
        taulell = new Tauler(tamany);
        taulell.addMouseListener(this);
        this.add(taulell);
        this.setSize(taulell.getPreferredSize());
    }

    private Casella obtenirCasella(int x, int y) {
        int c = taulell.getCostat();
        return taulell.getCasella(x / c, y / c);
    }

    private void initSelector() {
        selector = new selectorSoluciones(this, knightTour.getSolucions());
        selector.setVisible(true);
    }

    private void resoldreBacktracking(int cx, int cy) {
        //reset tauler
        taulell.resetTauler();
        //reset problema cavalls
        knightTour = new NCaballos(taulell);
        knightTour.setSolucions(0);
        //resol algoritme
        knightTour.resol(cx, cy);
        if (knightTour.te3Solucions()) {
            JOptionPane.showMessageDialog(null, "S'han trobat 3 solucions");
            initSelector();
            taulell.setTauler(knightTour.getTauler(0));
        } else {
            if (knightTour.getSolucions() == 0) {
                JOptionPane.showMessageDialog(null, "No s'ha trobat cap solució");
            } else {
                taulell.setTauler(knightTour.getTauler(0));
                initSelector();
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent me) {
        try {
            if (selector.isShowing()) {
                selector.dispose();
            }
        } catch (Exception e) {

        }
        //variable int de confirmació
        int selectedOption = JOptionPane.showConfirmDialog(null,
                "Estau segur de que voleu introduir la reina en aquesta casella" + "?",
                "Confirmació",
                JOptionPane.YES_NO_OPTION);
        //si l'usuari accepta, introduïm la reina al taulell
        if (selectedOption == JOptionPane.YES_OPTION) {
            //afegirPeça(me.getX(), me.getY());
            int c = taulell.getCostat();
            int fila = me.getY() / c;
            int columna = me.getX() / c;
            resoldreBacktracking(fila, columna);
            repaint();
        }
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

    public void canviarSolucio() {
        taulell.setTauler(knightTour.getTauler(selector.getOpcio()));
        repaint();
    }

    public static void main(String[] args) throws IOException {
        try { //Control de l'aspecte
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            System.out.println("No s'ha establert el look desitjat: " + e);
        }
        Main main = new Main();
        main.setVisible(true);
    }

}
