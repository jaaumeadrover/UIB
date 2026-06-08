/*
Autors: Jaume Adrover i Joan Balaguer

Enllaç video: https://youtu.be/ltYOLVZSWeQ

 */
package practfinalprog2rebuild;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class Main extends JFrame implements KeyListener {

    //atributs que contindrà la finestra
    private JMenuBar barraMenu;
    private JMenu fitxerMenu;
    private JMenuItem nouLaberintMenu;
    private JMenuItem reiniciaFitxaMenu;
    private JMenuItem sortirMenu;
    private Laberint laberint;
    private String fitxer;

    /**
     * Mètode constructor de la finestra
     *
     * @throws IOException
     */
    public Main() throws IOException {

        this.setResizable(true);
        this.setTitle("Joc laberint");
        this.setDefaultCloseOperation(Main.EXIT_ON_CLOSE);
        this.getContentPane().setBackground(Color.yellow);
        this.addKeyListener(this);
        this.setLocationRelativeTo(null);
        initComponents();

    }

    /**
     * Mètode en el qual inicialitzam i definim totes les components de de la
     * finestra
     *
     * @throws IOException
     */
    private void initComponents() throws IOException {

        //inicialitzam la barra de menu
        barraMenu = new JMenuBar();
        setJMenuBar(barraMenu);

        fitxerMenu = new JMenu("Menu");
        barraMenu.add(fitxerMenu);

        nouLaberintMenu = new JMenuItem("Reinicia Laberint");
        //afegim l'escoltador al boto
        nouLaberintMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {

                try {
                    reiniciarLaberint(ev);
                    repaint();
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        fitxerMenu.add(nouLaberintMenu);

        reiniciaFitxaMenu = new JMenuItem("Reinicia Fitxa");
        //afegim l'escoltador al boto
        reiniciaFitxaMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                try {
                    reiniciarFitxa(ev);
                } catch (IOException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        fitxerMenu.add(reiniciaFitxaMenu);

        sortirMenu = new JMenuItem("Sortir");
        //afegim l'escoltador al boto
        sortirMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                System.exit(0);
            }
        });
        fitxerMenu.add(sortirMenu);

        //primer triem aleatoriament quin fitxer lletgirem
        triarLaberintInicial();
        //inicialitzam el laberint
        laberint = new Laberint(fitxer);
        //assignam el preferedsize de del laberint a la finestra
        this.setSize(laberint.getPreferredSize());
        //afegim el laberint a la finestra
        this.getContentPane().add(laberint);

    }

    /**
     * Mètode que inicialitza l'atribut fitxer segons el valor d'un numero
     * aleatori
     *
     * @return
     */
    private String triarLaberintInicial() {

        Random rmd = new Random();
        int x = rmd.nextInt(4) + 1;

        switch (x) {

            case 1:
                fitxer = "Recursos/Laberints/maze1.txt";
                break;

            case 2:
                fitxer = "Recursos/Laberints/maze2.txt";
                break;

            case 3:
                fitxer = "Recursos/Laberints/maze3.txt";
                break;

            case 4:
                fitxer = "Recursos/Laberints/maze4.txt";
                break;
        }
        return fitxer;
    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

    /**
     * En aquest mètode es descriuen les accions que es duràn a terme quan
     * pitjem una tecla (W,A,S,D), en cas que la fitxa i la sortida es trobin a
     * la mateixa casella, el mètode checkVictoria tronarà un true i per tant
     * mostrarem el la finestra de victoria
     *
     * @param ke
     */
    @Override
    public void keyPressed(KeyEvent ke) {
        char c = ke.getKeyChar();

        try {
            laberint.moureFitxa(c);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }

        repaint();
        if (laberint.checkVictoria()) {
            try {
                JOptionPane.showMessageDialog(null, "HAS GUANYAT!"
                        + "\n" + "Tria un nou laberint.");
                reiniciarLaberint(null);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Mètode en le qual reiniciam la pintura del laberint, però sense canviar
     * el fitxer que lletim, per tant, l'única component que es modificarà serà
     * la fitxa
     *
     * @param ev
     * @throws IOException
     */
    private void reiniciarFitxa(ActionEvent ev) throws IOException {

        //llevam el laberint per posteriorment tornar-lo a inicialitzar amb el 
        //mateix fitxer que ja teniem
        this.remove(laberint);
        laberint = new Laberint(fitxer);
        this.add(laberint);
        this.setVisible(true);
    }

    /**
     * Mètode amb el qual, mitjançant un fileChooser, donam l'opció a l'usuari
     * de reiniciar el laberint amb un fitxer que esculli ell
     *
     * @param ev
     * @throws IOException
     */
    private void reiniciarLaberint(ActionEvent ev) throws IOException {

        JFileChooser fileChoo = new JFileChooser();

        int returnValue = fileChoo.showOpenDialog(null);

        //Mostram la finestra fileChooser
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChoo.getSelectedFile();
            fitxer = selectedFile.getAbsolutePath();
        }
        //llevam el laberint per després tornar-lo a inicialitzar
        this.remove(laberint);
        laberint = new Laberint(fitxer);
        this.add(laberint);
        this.setVisible(true);
    }

    @Override
    public void keyReleased(KeyEvent ke) {

    }

    /**
     * Mètode main on feim visible la finestra
     *
     * @param args
     * @throws IOException
     */
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
