
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jaume A
 */
public class testing extends JFrame implements ActionListener{
    public JButton button;
    public boolean check;

  
    public void paint(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        if(check==true){
        super.paint(g);
        g.drawLine(200, 200, 300, 400);
        }
    }

    public void start(){
        setLayout(new BorderLayout());
        button=new JButton();

        button.setPreferredSize(new Dimension(200,20));
        button.setText("ClickMe"); 
        button.addActionListener(this);//AQUI se ejecuta
        add(button, BorderLayout.SOUTH);
        setSize(500,500);
        setVisible(true);
    }    

    public void actionPerformed(ActionEvent e) {    
        check=true;
        repaint();
    }

    public static void main(String args[]){
        testing x=new testing();
        x.start();
    }
}
