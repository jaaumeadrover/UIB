/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author joanbalaguer
 */
public class PanellInit extends JPanel{
    
    //atributs
    JLabel titol, noms, mensaje;
    
    //mètode constructor
    public PanellInit(){
        
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);
        this.setSize(700,700);
        initComponents();
    }
    
    public void initComponents(){
        int i;
        final String[] camps = {"- Jaume Adrover Fernandez", "- Joan Balaguer Llagostera", "- Eduardo Bonnin Narvaez"};
        
        //TÍTOL
        titol = new JLabel("GESTIÓ D'ESCOLES");
        titol.setFont(new Font("Verdana", Font.PLAIN, 30));
        titol.setBounds(215, 30, 400, 50);
        this.add(titol);
        
        //Missatge previ a noms
        JLabel l = new JLabel("Creat per:");
        l.setBounds(90, 120, 250, 50);
        l.setFont(new Font("Verdana", Font.PLAIN, 20));
        this.add(l);
        //Noms dels Creadors
        for (i = 0; i < camps.length; i++) {
            JLabel label = new JLabel(camps[i]);
            label.setBounds(110, (160) + i * 50, 300, 50);
            label.setFont(new Font("Verdana", Font.PLAIN, 20));
            this.add(label);

        }
        
        //Missatge dirigit a l'usuari
        JLabel la = new JLabel("PITJA UN DELS BOTONS PER COMENÇAR A UTILITZAR L'EINA");
        la.setBounds(90, 360, 600, 300);
        la.setFont(new Font("Verdana", Font.PLAIN, 15));
        this.add(la);
    }
    
}
