/*
CLASSE lecturaDaDES.
FUNCIONALITAT: implementam les funcionalitats per a llegir per pantalla.
 */
package GUI;

import Estudiant.Estudiant;
import Asignatures.Assignatura;
import Cursos.Curs;
import Cursos.LlistaCursos;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class lecturaDades extends JDialog {

    //Components gràfiques
    private JList JLc, JLa,JLe;
    private JScrollPane JSPc, JSPa,JSPe;
    //Objectes a ser tractats
    private LlistaCursos Lc;
    private Curs c;
    private Assignatura asign;
    private Estudiant estudiant;

    //MÉTODO CONSTRUCTOR
   
    public lecturaDades(JFrame frame, LlistaCursos lc, int n) {
        super(frame, true);
        Lc = lc;

        initComponents(n);

        //paràmetres de visibilitat
        setSize(n * 200 + 15, (4 * 50) + 40);
        //centram la finestra
        setLocationRelativeTo(frame);
        //posam a true la visibilitat del JDialog
        setVisible(true);
    }

    private void initComponents(int n) {
        setTitle("INTRODUCCIÓ DE DADES");

        //declaració del panell de continguts
        Container panelContenidos = getContentPane();
        panelContenidos.setLayout(null);

        JLc = new JList(Lc.nomsToArr());
        JLc.setFont(new Font("Verdana", Font.PLAIN, 15));

        MouseListener elegirCurs = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = JLc.getSelectedIndex();//começa 0?
                c = (Curs) Lc.getElement(index);
                JLa.setListData(c.getAsignatures().nomsToArr());
            }

        };
        JLc.addMouseListener(elegirCurs);
        JSPc = new JScrollPane();
        JSPc.setViewportView(JLc);
        JSPc.setColumnHeaderView(new JLabel("Curs"));


        /*
        JScrollPane ASSIGNATURES
         */
        JLa = new JList();
        JLa.setFont(new Font("Verdana", Font.PLAIN, 15));

        MouseListener elegirAssignatura = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = JLa.getSelectedIndex();//começa 0?
                asign = c.getAsign(index);
                JLe.setListData(asign.getNomsEstudiants().toArray());
            }

        };
        JLa.addMouseListener(elegirAssignatura);
        JSPa = new JScrollPane();
        JSPa.setViewportView(JLa);
        JSPa.setColumnHeaderView(new JLabel("Assignatura"));
        
        JLe=new JList();
        JLe.setFont(new Font("Verdana", Font.PLAIN, 15));
        MouseListener elegirEstudiant = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int index = JLe.getSelectedIndex();//começa 0?
                estudiant = (Estudiant) asign.getLlistaEstudiants().getElement(index);//fer mètode dins assignatura?
            }

        };
        JLe.addMouseListener(elegirEstudiant);
        JSPe=new JScrollPane();
        JSPe.setViewportView(JLe);
        JSPe.setColumnHeaderView(new JLabel("Estudiant"));

        //Botó sortir
        //declaració component JButton sortir
        JButton salirBoton = new JButton("CONFIRMAR");
        salirBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //pone a FALSE la visibilidad del contenedor JDialog
                setVisible(false);
            }
        });

        //Organització según modos; 1(elegir Curs),2(elegir Assignatura),3(elegir estudiant).
        switch (n) {
            case 1:
                JSPc.setBounds(0, 0, 200, 180);
                salirBoton.setBounds(0, 180, 200, 20);
                panelContenidos.add(salirBoton);
                this.add(JSPc);
                break;
            case 2:
                //llista cursos
                JSPc.setBounds(0, 0, 200, 180);
                this.add(JSPc);

                //llista assignatures
                JSPa.setBounds(200, 0, 200, 180);
                this.add(JSPa);

                salirBoton.setBounds(0, 180, 400, 20);
                panelContenidos.add(salirBoton);

                break;
            case 3:
                //llista cursos
                JSPc.setBounds(0, 0, 200, 180);
                this.add(JSPc);

                //llista assignatures
                JSPa.setBounds(200, 0, 200, 180);
                this.add(JSPa);
                
                JSPe.setBounds(400, 0, 200, 180);
                this.add(JSPe);

                salirBoton.setBounds(0, 180, 600, 20);
                panelContenidos.add(salirBoton);
                break;
        }

    }

    public Curs getCurs() {
        return c;
    }

    //Retornam assignatura
    public Assignatura getAssignatura() {
        return asign;
    }

    public Estudiant getEstudiant() {
        return estudiant;
    }
}
