/*
CLASSE PANELLINFOCURS:
FUNCIONALITAT: mostra la informació d'un curs respectiu.
 */
package GUI;

import Asignatures.Assignatura;
import Asignatures.Obligatoria;
import Asignatures.Optativa;
import Cursos.BATX;
import Cursos.Curs;
import Cursos.FP;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * AUTOR: Joan Balaguer, Jaume Adrover DATA CREACIÓ:19/11/2021
 */
public class PanellInfoCurs extends JPanel {
    private final Font defaultFont=new Font("Verdana", Font.PLAIN, 15);

    //Components gràfiques
    JLabel title, complement, assign, nomAssignatura, codiAssignatura, creditsAssignatura, perfilAssignatura, tipusAssignatura;
    //Cream els objectes JList per mostrar les assignatures juntament amb els seus alumnes
    private JList JLa, JLe;
    //Objecte curs a ser mostrat
    private final Curs c;
    //Objecte Assignatura a se mostrat
    private Assignatura a;

    public PanellInfoCurs(Curs c) {
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);
        this.setSize(700, 700);
        this.c = c;
        initComponents();
    }

    private void initComponents() {
        int i;
        final String[] campsC = {"NOM: " + c.getNom(), "CODI: " + '\t' + c.getCodi(),
            "TIPUS: \t" + c.getClass().getSimpleName(), ""};
        a = null;

        //TÍTOL
        title = new JLabel("INFORMACIÓ D'UN CURS");
        title.setFont(new Font("Verdana", Font.BOLD, 30));
        title.setBounds(130, 30, 450, 50);
        this.add(title);
        //NOM,CODI,TIPUS
        for (i = 0; i < campsC.length; i++) {
            JLabel label = new JLabel(campsC[i]);
            label.setBounds(110, (100) + i * 30, 250, 50);
            label.setFont(new Font("Verdana", Font.PLAIN, 15));
            this.add(label);

        }
        //DISTINGIM CAS FP O BATX
        if ("FP".equals(c.getClass().getSimpleName())) {
            FP fp = (FP) c;
            complement = new JLabel("ESPECIALITAT: " + fp.getESP());
            complement.setBounds(110, (100) + ((i - 1) * 30), 250, 50);
            complement.setFont(defaultFont);
            this.add(complement);
        } else {
            BATX batx = (BATX) c;
            complement = new JLabel("CURS: " + batx.getCurs());
            complement.setBounds(110, (100) + ((i - 1) * 30), 250, 50);
            complement.setFont(defaultFont);
            this.add(complement);
        }

        //CRREACIÓ LLISTA ASSIGNATURES
        JScrollPane JSPa = new JScrollPane();
        JScrollPane JSPe = new JScrollPane();
        
        JLabel assignatures = new JLabel("ASSIGNATURES:");
        assignatures.setBounds(35, 260, 250, 50);
        assignatures.setFont(new Font("Verdana", Font.BOLD, 15));
        this.add(assignatures);
        JLa = new JList(c.getAsignatures().nomsToArr());
        JLa.setFont(defaultFont);

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickLlista();
            }
        };

        JLa.addMouseListener(mouseListener);
        JSPa.setViewportView(JLa);
        JSPa.setBounds(30, 300, 140, 360);
        this.add(JSPa);

        //MOSTRAM ATRIBUTS DE LA ASSIGNATURA SELECIONADA
        JLabel titolAssignatura = new JLabel("DADES ASSIGNATURA:");
        titolAssignatura.setBounds(200, 260, 250, 50);
        titolAssignatura.setFont(new Font("Verdana", Font.BOLD, 15));
        this.add(titolAssignatura);
        //NOM ASSIGNATURA
        nomAssignatura = new JLabel("NOM: ");
        nomAssignatura.setBounds(200, 290, 250, 50);
        nomAssignatura.setFont(defaultFont);
        this.add(nomAssignatura);
        //CODI ASSIGNATURA
        codiAssignatura = new JLabel("CODI: ");
        codiAssignatura.setBounds(200, 290 + 50, 250, 50);
        codiAssignatura.setFont(defaultFont);
        this.add(codiAssignatura);
        //TIPUS D'ASSIGNATURA
        tipusAssignatura = new JLabel("TIPUS: ");
        tipusAssignatura.setBounds(200, 290 + (2 * 50), 250, 50);
        tipusAssignatura.setFont(defaultFont);
        this.add(tipusAssignatura);
        //PERFIL ASSIGNATURA
        perfilAssignatura = new JLabel("PERFIL: ");
        perfilAssignatura.setBounds(200, 290 + (3 * 50), 250, 50);
        perfilAssignatura.setFont(defaultFont);
        this.add(perfilAssignatura);
        perfilAssignatura.setVisible(false);
        //CREDITS ASSIGNATURA
        creditsAssignatura = new JLabel("CRÈDITS: ");
        creditsAssignatura.setBounds(200, 290 + (3 * 50), 250, 50);
        creditsAssignatura.setFont(defaultFont);
        this.add(creditsAssignatura);
        creditsAssignatura.setVisible(false);

        //CREACIÓ LLISTA ESTUDIANTS
        JLabel estudiants = new JLabel("ESTUDIANTS:");
        estudiants.setBounds(450, 260, 250, 50);
        estudiants.setFont(new Font("Verdana", Font.BOLD, 15));
        this.add(estudiants);

        JLe = new JList();
        JLe.setFont(new Font("Verdana", Font.BOLD, 10));
        JSPe.setViewportView(JLe);
        JSPe.setBounds(450, 300, 200, 360);
        this.add(JSPe);

    }

    private void clickLlista() {
        int index = JLa.getSelectedIndex();
        a = c.getAsign(index);
        try{
        JLe.setListData(a.getNomsCompletsEstudiants().toArray());
        }catch(NullPointerException e){
        }
        //Modificam el text dels atributs de la assignatura actual
        nomAssignatura.setText("NOM: " + a.getNom());
        codiAssignatura.setText("CODI: " + a.getCodi());
        tipusAssignatura.setText("TIPUS: " + a.getClass().getSimpleName());
        //Distingim el cas de Obligatoria i optativa
        if ("Obligatoria".equals(a.getClass().getSimpleName())) {
            Obligatoria obli = (Obligatoria) a;
            String s = obli.getCredits() + "";
            creditsAssignatura.setText("CRÈDITS: " + s);
            creditsAssignatura.setVisible(true);
            perfilAssignatura.setVisible(false);
        } else {
            Optativa opta = (Optativa) a;
            perfilAssignatura.setText("PERFIL: " + opta.getPerfil().toString());
            perfilAssignatura.setVisible(true);
            creditsAssignatura.setVisible(false);
        }

    }

}
