/*
CLASSE eliminaDades.
FUNCIONALITAT: implementa un JDialog que servirà per a donar de baixa un curs
o una assignatura, depenent del nombre enter donat per paràmetre.
 */
package GUI;

import Asignatures.Assignatura;
import Cursos.Curs;
import Cursos.LlistaCursos;
import Estudiant.LlistaEstudiants;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

/*
AUTOR: Joan Balaguer, Jaume Adrover
DATA CREACIÓ: 25/11/2021
 */
public class eliminaDades extends JDialog {

    //CONSTANTS
    private final int costatX = 150;
    private final int costatY = 200;
    private final Font defaultFont = new Font("Verdana", Font.BOLD, 16);
    //COMPONENTS GRÀFIQUES
    private JList JLc, JLa, JLe;
    private JScrollPane JSPc, JSPa, JSPe;
    private JButton eliminar;
    //ATRIBUTS
    private LlistaCursos llistaC;
    private LlistaEstudiants llistaE;
    private Curs c;
    private int index;

    public eliminaDades(JFrame frame, int n, LlistaCursos lc, LlistaEstudiants le) {
        super(frame, true);
        llistaC = lc;
        llistaE = le;
        this.setLayout(null);
        this.setTitle("INTRODUCCIÓ DE DADES");
        if (n == 1) {
            config1();
        } else {
            config2();
        }
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    private void config1() {
        setSize(560, 330);
        //DONAR DE BAIXA UN CURS
        JLabel titolcurs = new JLabel("CURS");
        titolcurs.setBounds(75, 20, 55, 25);
        titolcurs.setFont(defaultFont);
        this.add(titolcurs);

        //LLISTACURSOS
        JLc = new JList();
        JLc.setListData(llistaC.nomsToArr());

        MouseListener elegirCurs = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickLlista(1);
            }
        };
        JLc.addMouseListener(elegirCurs);
        JSPc = new JScrollPane();
        JSPc.setViewportView(JLc);
        JSPc.setColumnHeaderView(new JLabel("Cursos:"));
        JSPc.setBounds(35, 50, costatX, costatY);
        this.add(JSPc);

        //TÍTOL
        JLabel titolAssignatura = new JLabel("ASSIGNATURES");
        titolAssignatura.setBounds(200, 20, 160, 25);
        titolAssignatura.setFont(defaultFont);
        this.add(titolAssignatura);
        //LLISTAASSIGNATURES
        JLa = new JList();
        MouseListener elegirAssignatura = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickLlista(2);
            }
        };
        JLa.addMouseListener(elegirAssignatura);
        JSPa = new JScrollPane();
        JSPa.setViewportView(JLa);
        JSPa.setColumnHeaderView(new JLabel("Assignatures:"));
        JSPa.setBounds(200, 50, costatX, costatY);
        this.add(JSPa);

        //TITOL ESTUDIANTS
        JLabel titolEstudiant = new JLabel("ESTUDIANTS");
        titolEstudiant.setBounds(370, 20, 160, 25);
        titolEstudiant.setFont(defaultFont);
        this.add(titolEstudiant);
        //LLISTAESTUDIANTS
        JLe = new JList();
        JSPe = new JScrollPane();
        JSPe.setViewportView(JLe);
        JSPe.setColumnHeaderView(new JLabel("Estudiants:"));
        JSPe.setBounds(365, 50, costatX, costatY);
        this.add(JSPe);

        //BOTO PER ELIMINAR
        eliminar = new JButton("ELIMINA");
        eliminar.setBounds(380, 260, 130, 30);
        eliminar.setBackground(Color.red);
        eliminar.setFont(new Font("Verdana", Font.BOLD, 10));
        ActionListener aA = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                eliminaCurs();
            }
        };
        eliminar.addActionListener(aA);
        this.add(eliminar);

    }

    private void config2() {
        setSize(560, 330);
        //DONAR DE BAIXA UN CURS
        JLabel titolcurs = new JLabel("CURS");
        titolcurs.setBounds(75, 20, 55, 25);
        titolcurs.setFont(defaultFont);
        this.add(titolcurs);

        //LLISTACURSOS
        JLc = new JList();
        JLc.setListData(llistaC.nomsToArr());

        MouseListener elegirCurs = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickLlista(1);
            }
        };
        JLc.addMouseListener(elegirCurs);
        JSPc = new JScrollPane();
        JSPc.setViewportView(JLc);
        JSPc.setColumnHeaderView(new JLabel("Cursos:"));
        JSPc.setBounds(35, 50, costatX, costatY);
        this.add(JSPc);

        //TÍTOL
        JLabel titolAssignatura = new JLabel("ASSIGNATURES");
        titolAssignatura.setBounds(200, 20, 160, 25);
        titolAssignatura.setFont(defaultFont);
        this.add(titolAssignatura);
        //LLISTAASSIGNATURES
        JLa = new JList();
        MouseListener elegirAssignatura = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickLlista(2);
            }
        };
        JLa.addMouseListener(elegirAssignatura);
        JSPa = new JScrollPane();
        JSPa.setViewportView(JLa);
        JSPa.setColumnHeaderView(new JLabel("Assignatures:"));
        JSPa.setBounds(200, 50, costatX, costatY);
        this.add(JSPa);

        //TITOL ESTUDIANTS
        JLabel titolEstudiant = new JLabel("ESTUDIANTS");
        titolEstudiant.setBounds(370, 20, 160, 25);
        titolEstudiant.setFont(defaultFont);
        this.add(titolEstudiant);
        //LLISTAESTUDIANTS
        JLe = new JList();
        JSPe = new JScrollPane();
        JSPe.setViewportView(JLe);
        JSPe.setColumnHeaderView(new JLabel("Estudiants:"));
        JSPe.setBounds(365, 50, costatX, costatY);
        this.add(JSPe);

        //BOTO PER ELIMINAR
        eliminar = new JButton("ELIMINA");
        eliminar.setBounds(380, 260, 130, 30);
        eliminar.setBackground(Color.red);
        eliminar.setFont(new Font("Verdana", Font.BOLD, 10));
        ActionListener aA = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                eliminaAssignatura();
            }
        };
        eliminar.addActionListener(aA);
        this.add(eliminar);
    }

    private void clickLlista(int n) {
        Assignatura a;
        if (n == 1) {
            index = JLc.getSelectedIndex();
            c = (Curs) llistaC.getElement(index);
            JLa.setListData(c.getAsignatures().nomsToArr());
        } else if (n == 2) {
            int indexA = JLa.getSelectedIndex();
            a = (Assignatura) c.getAsignatures().getElement(indexA);
            JLe.setListData(a.getNomsEstudiants().toArray());
        }
    }

    private void eliminaCurs() {
        //llevar de les assignatures associades del estudiants, l'assignatura que anem a eliminar
        int selectedOption = JOptionPane.showConfirmDialog(null,
                "Estau segur de que voleu eliminar el curs següent: " + llistaC.nomsToArr()[index] + "?",
                "Confirmació",
                JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION) {
            Curs Celiminat=llistaC.getElementC(index);
            llistaE.eliminaAssignatures(Celiminat);
            //eliminar assignatures de estyudiant amb curs
            llistaC.eliminaElement(index);
            JOptionPane.showMessageDialog(null, "Curs eliminat correctament");
            JLc.setListData(llistaC.nomsToArr());

        }

    }

    private void eliminaAssignatura() {
        index = JLc.getSelectedIndex();
        int index2 = JLa.getSelectedIndex();
        
        Assignatura a = (Assignatura) llistaC.getElementC(index).getAsignatures().getElement(index2);
        
        
        int selectedOption = JOptionPane.showConfirmDialog(null,
                "Estau segur de que voleu eliminar la assignatura següent: " + a.getNom() + "?",
                "Confirmació",
                JOptionPane.YES_NO_OPTION);
        if (selectedOption == JOptionPane.YES_OPTION) {
            llistaC.getElementC(index).getAsignatures().eliminaElement(index2);
            //eliminar assignatures de la LLISTAE
            llistaE.eliminaAssignatura(a);
            JOptionPane.showMessageDialog(null, "Assignatura eliminada correctament");
            JLa.setListData(c.getAsignatures().nomsToArr());
            //update JLe(no contempla cas asignatures.size==0)
            int indexA = JLa.getSelectedIndex();
            a = (Assignatura) c.getAsignatures().getElement(0);
            JLe.setListData(a.getNomsEstudiants().toArray());
        }

    }
}
