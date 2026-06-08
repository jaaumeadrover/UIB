/*
 * Programa que defineix un tauler d'escacs dins un marc
 *
 */
package _escacs2;
/**
 *
 * @author miquelmascaro
 */
import javax.swing.*;

public class Escacs2 extends JFrame {

    Tauler tauler;

    public Escacs2() {
        super("Escacs");
        tauler = new Tauler();
        this.getContentPane().add(tauler);
        this.setSize(tauler.getPreferredSize());
        this.pack();
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        Escacs2 esc = new Escacs2();
        esc.setVisible(true);
    }

}

