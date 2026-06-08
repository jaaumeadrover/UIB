/*
CLASSE MAIN.
FUNCIONALITAT: programa principal que executa l'algoritme de backtracking amb
recursivitat.Donada una casella, elegida per l'usuari, introduïm una reina i miram
si hi ha qualque possible combinació, notificant per pantalla el resultat.
 */
package GUI;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import nreinas.NReinas;

/**
 * AUTOR: Joan Balaguer, Jaume Adrover 
 * DATA CREACIÓ: 18/12/2021
 */
public class Main extends JFrame implements MouseListener {

    //ATRIBUTS
    private Tauler taulell;
    private final Pesa pesaDef = new Pesa("REINA");
    private int tamany;

    public Main() {
        this.setResizable(false);
        this.setTitle("Joc laberint");
        this.setDefaultCloseOperation(Main.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.yellow);
        this.setLocationRelativeTo(null);
        initComponents();
    }
    /*
    Mètode per a inicialitzar les components de la nostra finestra
    */
    private void initComponents() {
        //elegir dimensio amb la variable tamany
        tamany = Integer.parseInt(JOptionPane.showInputDialog("Introdueix el tamany del tauler:"));
        //afegir panell
        taulell = new Tauler(tamany);
        taulell.addMouseListener(this);
        this.add(taulell);
        this.setSize(taulell.getPreferredSize());
    }
    /*
    Mètode que ens permet obtenir una casella a partir de les coords obtingudes 
    per l'escoltador del ratolí.
    */
    private Casella obtenirCasella(int x, int y) {
        int c = taulell.getCostat();
        return taulell.getCasella(x / c, y / c);
    }
    /*
    Mètode per a afegir una peça a una casella corresponent donada per paràmetre.
    */
    private void afegirPeça(Pesa p, int coordx, int coordy) {
        try {
            Casella c = obtenirCasella(coordy, coordx);
            c.setPesa(p);
        } catch (Exception e) {
            System.out.println("Has seleccionat una casella incorrecte");
        }
    }
    /*
    Mètode que ens ajuda a resoldre un cert problema de les NReines, mostrant si
    té o no solució.
    */
    private void resoldreBacktracking(){
        NReinas queen=new NReinas(taulell,1);
        if(queen.teSolucio()){
            JOptionPane.showMessageDialog(null, "Solució trobada amb èxit");
        }else{
            JOptionPane.showMessageDialog(null, "No s'ha trobat cap solució");
            taulell.resetTauler();
        }
    }
    /*
    Escoltador que realitza la acció quan el ratolí ha estat clicat
    */
    @Override
    public void mouseClicked(MouseEvent me) {
        taulell.resetTauler();
        //variable int de confirmació
        int selectedOption = JOptionPane.showConfirmDialog(null,
                "Estau segur de que voleu introduir la reina en aquesta casella" + "?",
                "Confirmació",
                JOptionPane.YES_NO_OPTION);
        //si l'usuari accepta, introduïm la reina al taulell
        if (selectedOption == JOptionPane.YES_OPTION) {
            afegirPeça(pesaDef, me.getX(), me.getY());
            resoldreBacktracking();
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
