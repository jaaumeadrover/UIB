/*
CLASSE PanellInfoAssignatura.
FUNCIONALITAT: panell per a mostrar la informació d'una assignatura, amb els
seus alumnes corresponents.
 */
package GUI;

import Asignatures.Assignatura;
import Asignatures.Obligatoria;
import Asignatures.Optativa;
import Cursos.BATX;
import Cursos.FP;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * AUTOR: Joan Balaguer, Jaume Adrover DATA CREACIÓ: 21/11/2021
 */
public class PanellInfoAssignaura extends JPanel {

    //Components gràfiques
    JLabel titol, complement, curs, titolCurs, tipusCurs, complementCurs;//etiquetes
    JList JLe;//llista estudiants
    //Objecte assignatura
    private final Assignatura assign;

    public PanellInfoAssignaura(Assignatura asign) {
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);
        this.setSize(700, 700);
        this.assign = asign;
        initComponents();
    }

    private void initComponents() {
        int i;
        final String[] camps = {"NOM: " + assign.getNom(), "CODI: " + '\t' + assign.getCodi(),
            "TIPUS: \t" + assign.getClass().getSimpleName(), ""};

        //TÍTOL
        titol = new JLabel("INFORMACIÓ D'UNA ASSIGNATURA");
        titol.setFont(new Font("Verdana", Font.PLAIN, 30));
        titol.setBounds(90, 30, 600, 50);
        this.add(titol);
        //NOM,CODI,TIPUS
        for (i = 0; i < camps.length; i++) {
            JLabel label = new JLabel(camps[i]);
            label.setBounds(110, (100) + i * 40, 250, 50);
            label.setFont(new Font("Verdana", Font.PLAIN, 17));
            this.add(label);
        }
        //Distingim els casos de Obligatòria i Optativa
        if ("Obligatoria".equals(assign.getClass().getSimpleName())) {
            Obligatoria obli = (Obligatoria) assign;
            String s = obli.getCredits() + "";
            complement = new JLabel("CRÈDITS: " + s);
            complement.setBounds(110, (100) + ((i - 1) * 40), 250, 50);
            complement.setFont(new Font("Verdana", Font.PLAIN, 17));
            this.add(complement);
        } else {
            Optativa opta = (Optativa) assign;
            complement = new JLabel("PERFIL: " + opta.getPerfil().toString());
            complement.setBounds(110, (100) + ((i - 1) * 40), 250, 50);
            complement.setFont(new Font("Verdana", Font.PLAIN, 17));
            this.add(complement);
        }
        i++;
        //Mostram a quin curs pertany l'assignatura
        curs = new JLabel("CURS: " + assign.getCurs().getNom());
        curs.setBounds(110, (100) + ((i - 1) * 40), 250, 50);
        curs.setFont(new Font("Verdana", Font.PLAIN, 17));
        this.add(curs);
        //Mosstram la informació del curs al que pertany l'assignatura
        titolCurs = new JLabel("INFORMACIÓ DEL CURS " + assign.getCurs().getNom() + ":");
        titolCurs.setFont(new Font("Verdana", Font.PLAIN, 15));
        titolCurs.setBounds(35, 330, 350, 50);
        this.add(titolCurs);
        
        //TIPUS
        tipusCurs = new JLabel("TIPUS: " + assign.getCurs().getClass().getSimpleName());
        tipusCurs.setFont(new Font("Verdana", Font.PLAIN, 15));
        tipusCurs.setBounds(35, 380, 350, 50);
        this.add(tipusCurs);
        //distingim el cas de que l'assignatura sigui FP o BATX
        if ("FP".equals(assign.getCurs().getClass().getSimpleName())) {
            FP fp = (FP) assign.getCurs();
            complementCurs = new JLabel("ESPECIALITAT: " + fp.getESP());
            complementCurs.setBounds(35, 410, 350, 50);
            complementCurs.setFont(new Font("Verdana", Font.PLAIN, 15));
            this.add(complementCurs);
        } else {
            BATX batx = (BATX) assign.getCurs();
            complementCurs = new JLabel("CURS: " + batx.getCurs());
            complementCurs.setBounds(35, 410, 350, 50);
            complementCurs.setFont(new Font("Verdana", Font.PLAIN, 15));
            this.add(complementCurs);
        }
        
        //Mostram la llista de estudiants associada a la assignatura
        JScrollPane JSPe = new JScrollPane();
        
        JLabel estudiants = new JLabel("ESTUDIANTS:");
        estudiants.setBounds(450, 260, 250, 50);
        estudiants.setFont(new Font("Verdana", Font.PLAIN, 15));
        this.add(estudiants);

        JLe = new JList();
        JLe.setFont(new Font("Verdana", Font.BOLD, 10));
        JSPe.setViewportView(JLe);
        JSPe.setBounds(450, 300, 200, 360);
        this.add(JSPe);
        
        JLe.setListData(assign.getNomsCompletsEstudiants().toArray());
    }
}
