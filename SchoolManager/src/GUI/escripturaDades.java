/*
CLASSE ESCRIPTURA_DADES
FUNCIONALITAT: obtenir mitjançant l'input de l'usuari els atributs
corresponents per a afegir elements a les diferents llistes.
 */
package GUI;

import Asignatures.Assignatura;
import Asignatures.Obligatoria;
import Asignatures.Optativa;
import Asignatures.Perfil;
import Cursos.BATX;
import Cursos.Curs;
import Cursos.CursBatx;
import Cursos.Especialitat;
import Cursos.FP;
import Cursos.LlistaCursos;
import Estudiant.Estudiant;
import Estudiant.LlistaEstudiants;
import Llista_Ref.Llista_Estudiants_Ref;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * AUTOR: Joan Balaguer,Jaume Adrover DATA CREACIÓ: 22/11/2021
 */
public class escripturaDades extends JDialog {

    //Atributs gràfics
    private final int xLABEL = 50;
    private final int yLABEL = 25;
    private final Font DefaultFont = new Font("Verdana", Font.BOLD, 16);
    //Components gràfiques
    JComboBox cTipus1, cEsp, aTipus1, aPerfil;
    JTextField cNom, cCodi, cTipus, cEspecialitat;//Curs
    JTextField aNom, aCodi, aTipus, aCredits;//Assignatura
    JButton afegirA, eliminarA, afegirC;
    JLabel perfilA, creditsA;
    JScrollPane JSPa;
    JScrollPane JSPc;
    JList JLa;
    JList JLc;
    //atributs Objecte Curs
    private String nomC, codiC;
    //atributs Objecte Assignatura
    private String nomA, codiA;
    private String credits;
    //arrayList de assignatures a afegir
    ArrayList<Assignatura> llistaA = new ArrayList();
    //variabe per saber si hem seleccionat una assignatura BATX o FP
    int index, index2;
    //Objecte curs global el qual es podrà obtenir amb un mètode get
    Curs c;
    Assignatura a;
    //Llista de cursos que en s passes per paràmetre
    LlistaCursos lc;
    //Llista d'estudiants que ens passen per paràmetre
    LlistaEstudiants le;

    public escripturaDades(JFrame frame) {
        super(frame, true);
        this.setLayout(null);
        setTitle("INTRODUCCIÓ DE DADES");
        config1();
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    public escripturaDades(JFrame frame, LlistaCursos lc, LlistaEstudiants le) {
        super(frame, true);
        this.setLayout(null);
        setTitle("INTRODUCCIÓ DE DADES");
        this.lc = lc;
        this.le = le;
        config2();
        setLocationRelativeTo(frame);
        setVisible(true);
    }

    //Donar alta curs
    private void config1() {
        setSize(600, 390);
        index2 = 0;
        //CURS TÍTOL
        JLabel titolcurs = new JLabel("CURS");
        titolcurs.setBounds(75, 10, xLABEL, yLABEL);
        titolcurs.setFont(DefaultFont);
        this.add(titolcurs);
        //NOM
        JLabel nomLabel = new JLabel("NOM:");
        nomLabel.setBounds(30, 75, xLABEL, yLABEL);
        cNom = new JTextField();
        cNom.setBounds(75, 75, 75, 25);
        this.add(nomLabel);
        this.add(cNom);

        //CODI
        JLabel codiLabel = new JLabel("CODI:");
        codiLabel.setBounds(30, 115, xLABEL, yLABEL);
        cCodi = new JTextField();
        cCodi.setBounds(75, 115, 75, 25);
        this.add(codiLabel);
        this.add(cCodi);

        //TIPUS
        JLabel tipus = new JLabel("TIPUS: ");
        tipus.setBounds(30, 155, xLABEL, yLABEL);
        cTipus1 = new JComboBox();
        cTipus1.setBounds(75, 155, 75, 25);
        cTipus1.addItem("BATX");
        cTipus1.addItem("FP");
        ActionListener elegirTipus = (ActionEvent ae) -> {
            index2 = cTipus1.getSelectedIndex();
            DefaultComboBoxModel<String> model;
            if (index2 == 0) {
                String[] a = {"PRIMER", "SEGON"};
                model = new DefaultComboBoxModel<>(a);
                cEsp.setModel(model);
            } else if (index2 == 1) {
                String[] a = {"MECÀNICA", "ELECTRÒNICA", "INFORMÀTICA"};
                model = new DefaultComboBoxModel<>(a);
                cEsp.setModel(model);
            }
        };
        cTipus1.addActionListener(elegirTipus);
        this.add(tipus);
        this.add(cTipus1);

        //ESPECIALITAT
        JLabel especialitat = new JLabel("ESP:");
        especialitat.setBounds(35, 200, xLABEL, yLABEL);
        this.add(especialitat);

        cEsp = new JComboBox();
        cEsp.setBounds(75, 200, 75, 25);
        cEsp.addItem("PRIMER");
        cEsp.addItem("SEGON");
        this.add(cEsp);

        //ASSIGNATURES!
        //títol assignatures
        JLabel assign = new JLabel("ASSIGNATURES:");
        assign.setBounds(325, 10, xLABEL * 3, yLABEL);
        assign.setFont(DefaultFont);
        this.add(assign);

        //nom assignatures
        JLabel nomA = new JLabel("NOM:");
        nomA.setBounds(275 - 50, 75, xLABEL, yLABEL);
        aNom = new JTextField("");
        aNom.setBounds(315 - 50, 75, 75, 25);
        this.add(aNom);
        this.add(nomA);

        //codi assignatures
        JLabel codiA = new JLabel("CODI:");
        codiA.setBounds(275 - 50, 115, xLABEL, yLABEL);
        aCodi = new JTextField("");
        aCodi.setBounds(315 - 50, 115, 75, 25);
        this.add(aCodi);
        this.add(codiA);

        //tipus assignatura(0)Obligatoria,1(OPTATIVA)
        JLabel tipusA = new JLabel("TIPUS:");
        tipusA.setBounds(275 - 50, 155, xLABEL, yLABEL);
        aTipus1 = new JComboBox();
        aTipus1.addItem("Obligatòria");
        aTipus1.addItem("Optativa");
        aTipus1.setBounds(315 - 50, 155, 75, 25);
        ActionListener elegirTipusA = (ActionEvent ae) -> {
            index = aTipus1.getSelectedIndex();
            if (index == 0) {
                //amagam perfil
                perfilA.setVisible(false);
                aPerfil.setVisible(false);
                //mostram credits
                creditsA.setVisible(true);
                aCredits.setVisible(true);

            } else if (index == 1) {
                //amagam credits
                creditsA.setVisible(false);
                aCredits.setVisible(false);
                //mostram perfil
                perfilA.setVisible(true);
                aPerfil.setVisible(true);
            }
        };
        aTipus1.addActionListener(elegirTipusA);
        this.add(tipusA);
        this.add(aTipus1);

        //CreditsAssignatura
        creditsA = new JLabel("CRÈDITS:");
        creditsA.setBounds(260 - 50, 195, 75, 25);
        aCredits = new JTextField("");
        aCredits.setBounds(315 - 50, 195, 75, 25);
        this.add(creditsA);
        this.add(aCredits);

        //perfilAssignatura
        perfilA = new JLabel("PERFIL:");
        perfilA.setBounds(260 - 50, 195, 75, 25);
        aPerfil = new JComboBox();
        aPerfil.addItem("Teòric");
        aPerfil.addItem("Pràctic");
        aPerfil.setBounds(315 - 50, 195, 75, 25);
        perfilA.setVisible(false);
        aPerfil.setVisible(false);
        this.add(aPerfil);
        this.add(perfilA);

        //Llista Assignatures afegides
        JSPa = new JScrollPane();
        JSPa.setBounds(420 - 50, 75, 200, 200);
        JLabel l = new JLabel("ASSIGNATURES A AFEGIR");
        l.setFont(new Font("Verdana", Font.BOLD, 9));
        JSPa.setColumnHeaderView(l);
        JLa = new JList();
        //JLa.setListData(llistaA.toArray());
        JSPa.setViewportView(JLa);
        this.add(JSPa);

        //Boto per confirmar que inserim l'estudiant dins el curs
        afegirA = new JButton("ADD ASSIGN");
        afegirA.setBounds(290 - 50, 250, 120, 20);
        afegirA.setFont(new Font("Verdana", Font.BOLD, 10));
        ActionListener aA = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                afegirAssign(index);
            }
        };
        afegirA.addActionListener(aA);
        this.add(afegirA);
        //Boto per confirmar que insterim el curs dins la llista global de cursos
        afegirC = new JButton("ADD CURS");
        afegirC.setBounds(450, 300, 120, 50);
        afegirC.setFont(new Font("Verdana", Font.BOLD, 10));
        ActionListener aC = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                afegirCurs();
            }
        };
        afegirC.addActionListener(aC);
        this.add(afegirC);

    }

    //matricular estudiant
    private void config2() {
        setSize(600, 390);
        //ESTUDIANT TÍTOL
        JLabel titolEstudiant = new JLabel("ESTUDIANT");
        titolEstudiant.setBounds(50, 10, xLABEL + 70, yLABEL);
        titolEstudiant.setFont(DefaultFont);
        this.add(titolEstudiant);
        //EESTUDIANT NOM
        JLabel nomLabel = new JLabel("NOM:");
        nomLabel.setBounds(30, 75, xLABEL, yLABEL);
        aNom = new JTextField();
        aNom.setBounds(75, 75, 75, 25);
        this.add(nomLabel);
        this.add(aNom);
        //CODI
        JLabel codiLabel = new JLabel("DNI:");
        codiLabel.setBounds(30, 115, xLABEL, yLABEL);
        aCodi = new JTextField();
        aCodi.setBounds(75, 115, 75, 25);
        this.add(codiLabel);
        this.add(aCodi);

        //LLISTA DE CURSOS
        JSPc = new JScrollPane();

        JLabel titolCurs = new JLabel("CURSOS");
        titolCurs.setBounds(250, 10, xLABEL + 40, yLABEL);
        titolCurs.setFont(DefaultFont);
        this.add(titolCurs);

        JLc = new JList(lc.nomsToArr());
        JLc.setFont(DefaultFont);

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickLlista();
            }
        };

        JLc.addMouseListener(mouseListener);
        JLc.setFont(new Font("Verdana", Font.PLAIN, 15));
        JSPc.setViewportView(JLc);
        JSPc.setBounds(225, 50, 140, 275);
        this.add(JSPc);

        //LLISTA D'ASSIGNATURES
        JSPa = new JScrollPane();

        JLabel titolAssignatura = new JLabel("ASSIGNATURES");
        titolAssignatura.setBounds(420, 10, xLABEL + 100, yLABEL);
        titolAssignatura.setFont(DefaultFont);
        this.add(titolAssignatura);

        JLa = new JList();
        JLa.setFont(DefaultFont);

        MouseListener mouseListener2 = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickLlista2();
            }
        };

        JLa.addMouseListener(mouseListener2);
        JLa.setFont(new Font("Verdana", Font.PLAIN, 15));
        JSPa.setViewportView(JLa);
        JSPa.setBounds(420, 50, 140, 275);
        this.add(JSPa);

        //BOTO PER AFEGIR ESTDUIANT
        afegirA = new JButton("ADD ESTUDIANT");
        afegirA.setBounds(70, 280, 140, 40);
        afegirA.setFont(new Font("Verdana", Font.BOLD, 10));
        ActionListener aA = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                afegirEstudiant();
            }
        };
        afegirA.addActionListener(aA);
        this.add(afegirA);

        //BOTO PER FINALITZAR EL PROCES DE INSERIR ALUMNES
        JButton ok = new JButton("OK");
        ok.setBounds(490, 330, 70, 20);
        ok.setFont(new Font("Verdana", Font.BOLD, 10));
        ActionListener aOK = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ev) {
                dispose();
            }
        };
        ok.addActionListener(aOK);
        this.add(ok);
    }

    private void afegirCurs() {
        //CAS Curs inserit correctament
        if (!(cNom.getText().isEmpty()) && !(cCodi.getText().isEmpty())) {
            //1.Creas curs amb les dades introduides per l'usuari
            nomC = cNom.getText();
            codiC = cCodi.getText();
            //Distingim  el cas de que sigui una assignatura BATX o FP
            if (index2 == 0) {//BATX
                c = new BATX(nomC, codiC, cTipus1.getSelectedIndex());
            } else {//FP
                c = new FP(nomC, codiC, cTipus1.getSelectedIndex());
            }
            //2.Actualitzar el curs associat de totes les assignatures de la llista
            for (int i = 0; i < llistaA.size(); i++) {
                llistaA.get(i).setCurs(c);
                //3.Ficar totes les assignatures dins la llista d'assignatures del curs
                c.getAsignatures().afegeixElement(llistaA.get(i));
            }
            dispose();
            JOptionPane.showMessageDialog(null, "Curs inserit correctament");
        } else {
            JOptionPane.showMessageDialog(null, "ENTRADA INCORRECTA");
        }

    }

    //Mètode amb el qual afegim l'assignatura actual a la llista d'assignatures a afegir
    private void afegirAssign(int index) {
        //Control try catch control de errors
        nomA = aNom.getText();
        codiA = aCodi.getText();
        //Distingim el cas de Obligatòria i Optattiva
        credits = aCredits.getText();
        if (index == 0) {//cas obligatòria
            if (!(aNom.getText().isEmpty()) && !(aCodi.getText().isEmpty()) && !(aCredits.getText().isEmpty())) {
                Obligatoria obli = new Obligatoria(nomA, codiA, null, credits);
                llistaA.add(obli);
            } else {
                JOptionPane.showMessageDialog(null, "ENTRADA INCORRECTA");
            }
        } else {//Cas Optativa
            //Cream assignatura optativa
            if (!(aNom.getText().isEmpty()) && !(aCodi.getText().isEmpty())) {
                Optativa opt = new Optativa(nomA, codiA, null, aPerfil.getSelectedIndex());
                llistaA.add(opt);
            } else {
                JOptionPane.showMessageDialog(null, "ENTRADA INCORRECTA");
            }
        }
        JLa.setListData(llistaA.toArray());
        //Fer mpètode per quan apretem OK assignem a totels les assignatures el curs associat
    }

    private void afegirEstudiant() {
        String nom = aNom.getText();
        String DNI = aCodi.getText();
        if (!(aNom.getText().isEmpty()) && !(aCodi.getText().isEmpty())) {
            //Hem de mirar si l'estudiant està registrat a la llista global d'estudiants
            Estudiant e = new Estudiant(nom, DNI);
            if (le.isRepetit(e)) {
                //cercam l'estudiant dins la llista
                le.cercaElement(e).afegirAsignatura(a);
            } else {
                e.afegirAsignatura(a);
                le.afegeixElement(e);
            }
            a.afegirEstudiant(e);
            JOptionPane.showMessageDialog(null, "ESTUDIANT INSERIT CORRECTAMENT");
        } else {
            JOptionPane.showMessageDialog(null, "ENTRADA INCORRECTA");
        }
    }

    private void clickLlista() {
        int index = JLc.getSelectedIndex();
        c = (Curs) lc.getElement(index);
        try {
            JLa.setListData(c.getAsignatures().nomsToArr());
        } catch (NullPointerException e) {
            System.out.println("llista d'assignatures buida");
        }
    }

    private void clickLlista2() {
        int index = JLa.getSelectedIndex();
        a = c.getAsign(index);
    }

    public Curs getCurs() {
        return c;
    }

}
