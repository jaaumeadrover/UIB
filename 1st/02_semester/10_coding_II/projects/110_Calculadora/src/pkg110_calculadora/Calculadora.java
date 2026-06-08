/*
 * Exemple d'implementació d'una calculadora bàsica. Permet les funcionalitats
 * senzilles d'una calculadora només emprant el teclat de la pantalla.
 * Algunes funcionalitats així com el tractament d'errors (div zero) no estàn
 * implementades. L'interés de l'exemple és al disseny de la IGU i amb la
 * taula de botons. S'ha d'observar que en aquest cas s'ha deixa com a local
 * dins initComponents ja que per aquests botons podem treure la seva
 * funcionalitat del test de l'esdeveniment que s'ha produït quan han estat
 * pitjats
 */
package pkg110_calculadora;

/**
 *
 * @author miquelmascaro
 */
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import static java.lang.Integer.parseInt;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculadora extends JFrame
        implements ActionListener {
// implements ActionListener implica la subscripció als esdeveniments
// sobre el JFrame. L'únic que gestionam són els clicks als botons del teclat

    private JTextField t; //display de la calculadora
    char op = ' '; //operador
    String nump = "", num = ""; //nump significa nombre precedent
    float MR=0;

    public Calculadora() {
        setTitle("Calculadora Bàsica");
        setSize(350, 500);
        setLocationRelativeTo(null);//La calculadora surt al centre
        setDefaultCloseOperation(Calculadora.EXIT_ON_CLOSE);
        initContents();
    }

    private void initContents() {
//Es definiexen dos panells un pels botons i l'altre per la pantalla
        JPanel jpTeclat = new JPanel();
        jpTeclat.setLayout(new GridLayout(5, 4));
//        jpTeclat.setLayout(new FlowLayout());
        String jlbBotons[] = {"M+","M-","MR ", "C","7", "8", "9", "/", "4", "5", "6", "*", "1",
            "2", "3", "-", "0", ".", "=", "+"};
        for (int i = 0; i < jlbBotons.length; i++) {
            JButton boto = new JButton(jlbBotons[i]);
            boto.addActionListener(this);
            boto.setFont(new Font("arial", 0, 50));
            jpTeclat.add(boto);
        }
        t = new JTextField();
        t.setHorizontalAlignment(JTextField.RIGHT);
        t.setEditable(false); //No es pot escriure directament s'han d'usar
        //els botons
        t.setFont(new Font("arial", 2, 25));

        JPanel jpPantalla = (JPanel) getContentPane();

        jpPantalla.setLayout(new BorderLayout());
        jpPantalla.add(t, BorderLayout.NORTH);
        jpPantalla.add(jpTeclat, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        char c = ((JButton) evt.getSource()).getText().charAt(0);
        //getSource retorna un objecte, es fa el casting a botó
        //del botó s'agafa l'string que l'identifica
        //de l'string n'extreu el caràcter
        if(c=='M'){
            t.setText(Float.toString(MR));
        }
        //Construeix el numero
        if (c >= '0' && c <= '9') {
            if (num.equals("")) {
                t.setText(num);
            }
            num = num + c;
            t.setText(num);
            //afegeix el punt decimal
        } else if (c == '.') {
            if (num.equals("")) {
                num = "0.";
                t.setText(num);
            } else if (!num.contains(".")) {
                num = num + ".";
                t.setText(num);
                MR=parseInt(num);
            }
            //esborra
        } else if (c == 'C') {
            nump = "";
            num = "";
            op = ' ';
            t.setText(num);
            //realitza els càlculs
        } else if (c == '=') {
            calc();
            //nombres negatius
        } else if (c == '-' && (op == ' ') && num.equals("")) {
            num = num + c;
            t.setText(num);
            //operadors
        } else {
            if (op == ' ') {
                op = c;
                if (!num.equals("")) {
                    nump = num;
                }
                num = "";
            } else {
                calc();
                op = c;
            }
        }
    }

    private void calc() {
        if (!num.equals("") && !nump.equals("")) {
            float a = Float.parseFloat(nump);
            float b = Float.parseFloat(num);
            float r = 0;
            if (op == '+') {
                r = a + b;
            } else if (op == '-') {
                r = a - b;
            } else if (op == '*') {
                r = a * b;
            } else if (op == '/') {
//                r = a / (b != 0 ? b : 1);
                r = a / b;
            }
            nump = Float.toString(r);
            t.setText(nump);
            MR=r;
        }
        num = "";
    }

    public static void main(String[] args) {
        new Calculadora().setVisible(true);
    }
}
