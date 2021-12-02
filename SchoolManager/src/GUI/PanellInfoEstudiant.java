/*
Classe PanellInfoEstudiant
FUNCIONALITAT: panell en el qual mostrarem les dades d'un estudiant donat
 */
package GUI;

import Asignatures.Assignatura;
import Asignatures.Obligatoria;
import Asignatures.Optativa;
import Cursos.BATX;
import Cursos.FP;
import Estudiant.Estudiant;
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
 *
 * @author joanbalaguer
 */
public class PanellInfoEstudiant extends JPanel {

    private JLabel titol, nom, DNI, tipusAssignatura, perfilAssignatura, creditsAssignatura, cursPertanyent, tipusCurs, especialitatCurs, cursCurs;
    private JList JLa;
    private final Estudiant e;
    private Assignatura a;

    public PanellInfoEstudiant(Estudiant estudiant) {
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);
        this.setSize(700, 700);
        e = estudiant;
        initComponents();
    }

    private void initComponents() {
        int i;
        final String[] camps = {"NOM: " + e.getNom(), "DNI: " + '\t' + e.getCodi()};

        //TÍTOL
        titol = new JLabel("INFORMACIÓ D'UN ESTUDIANT");
        titol.setFont(new Font("Verdana", Font.PLAIN, 30));
        titol.setBounds(90, 30, 600, 50);
        this.add(titol);
        //NOM i DNI
        for (i = 0; i < camps.length; i++) {
            JLabel label = new JLabel(camps[i]);
            label.setBounds(110, (100) + i * 40, 250, 50);
            label.setFont(new Font("Verdana", Font.PLAIN, 17));
            this.add(label);
        }
        //LLISTA ASSIGNATURES ASSOCIADES
        JScrollPane JSPa = new JScrollPane();

        JLabel assignatures = new JLabel("ASSIGNATURES:");
        assignatures.setBounds(110, 210, 250, 50);
        assignatures.setFont(new Font("Verdana", Font.PLAIN, 15));
        this.add(assignatures);
        JLa = new JList(e.getNomsAssignatures().toArray());
        JLa.setFont(new Font("Verdana", Font.BOLD, 15));

        MouseListener mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                clickLlista();
            }
        };

        JLa.addMouseListener(mouseListener);
        JSPa.setViewportView(JLa);
        JSPa.setBounds(105, 250, 140, 360);
        this.add(JSPa);

        //MOSTRAM ATRIBUTS DE LA ASSIGNATURA SELECIONADA
        JLabel titolAssignatura = new JLabel("DADES ASSIGNATURA:");
        titolAssignatura.setBounds(300, 210, 250, 50);
        titolAssignatura.setFont(new Font("Verdana", Font.PLAIN, 15));
        this.add(titolAssignatura);

        //TIPUS D'ASSIGNATURA
        tipusAssignatura = new JLabel("TIPUS: ");
        tipusAssignatura.setBounds(300, (200 + (2 * 50)) - 50, 250, 50);
        tipusAssignatura.setFont(new Font("Verdana", Font.PLAIN, 15));
        this.add(tipusAssignatura);
        //PERFIL ASSIGNATURA
        perfilAssignatura = new JLabel("PERFIL: ");
        perfilAssignatura.setBounds(300, (200 + (3 * 50)) - 50, 250, 50);
        perfilAssignatura.setFont(new Font("Verdana", Font.PLAIN, 15));
        this.add(perfilAssignatura);
        perfilAssignatura.setVisible(false);
        //CREDITS ASSIGNATURA
        creditsAssignatura = new JLabel("CRÈDITS: ");
        creditsAssignatura.setBounds(300, (200 + (3 * 50)) - 50, 250, 50);
        creditsAssignatura.setFont(new Font("Verdana", Font.PLAIN, 15));
        this.add(creditsAssignatura);
        creditsAssignatura.setVisible(false);
        //CURS PERTANYENT
        cursPertanyent = new JLabel("CURS PERTANYENT: ");
        cursPertanyent.setBounds(300, (200 + (5 * 50)) - 50, 250, 50);
        cursPertanyent.setFont(new Font("Verdana", Font.PLAIN, 15));
        this.add(cursPertanyent);
        //TIPUS CURS
        tipusCurs = new JLabel("TIPUS CURS: ");
        tipusCurs.setBounds(300, (200 + (6 * 50)) - 50, 250, 50);
        tipusCurs.setFont(new Font("Verdana", Font.PLAIN, 15));
        this.add(tipusCurs);
        //ESPECIALITAT
        especialitatCurs = new JLabel("ESPECIALITAT CURS: ");
        especialitatCurs.setBounds(300, (200 + (7 * 50)) - 50, 250, 50);
        especialitatCurs.setFont(new Font("Verdana", Font.PLAIN, 15));
        especialitatCurs.setVisible(false);
        this.add(especialitatCurs);
        //CURS (PRIMER o SEGON)
        cursCurs = new JLabel("CURS DEL CURS: ");
        cursCurs.setBounds(300, (200 + (7 * 50)) - 50, 250, 50);
        cursCurs.setFont(new Font("Verdana", Font.PLAIN, 15));
        cursCurs.setVisible(false);
        this.add(cursCurs);
    }

    private void clickLlista() {
        int index = JLa.getSelectedIndex();
        a = (Assignatura) e.getAssignAssociades().getElement(index);
        //Modificam el text dels atributs de la assignatura actual
        tipusAssignatura.setText("TIPUS: " + a.getClass().getSimpleName());
        //Modificam el curs al qual pertany l'assigantura
        cursPertanyent.setText("CURS PERTANYENT: " + a.getCurs().getNom());
        //Modificam el tipus de curs
        tipusCurs.setText("TIPUS CURS: " + a.getCurs().getClass().getSimpleName());
        //Distingim els casos de que el curs sigui FP o BATX
        if ("FP".equals(a.getCurs().getClass().getSimpleName())) {
            FP fp = (FP) a.getCurs();
            String s = fp.getESP() + "";
            especialitatCurs.setText("ESPECIALITAT CURS: " + s);
            especialitatCurs.setVisible(true);
            cursCurs.setVisible(false);
        } else {
            BATX batx = (BATX) a.getCurs();
            cursCurs.setText("CURS: " + batx.getCurs());
            cursCurs.setVisible(true);
            especialitatCurs.setVisible(false);
        }

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
