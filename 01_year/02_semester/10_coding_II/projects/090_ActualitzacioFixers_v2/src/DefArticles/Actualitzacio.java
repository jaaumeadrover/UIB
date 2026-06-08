package DefArticles;

/**
 * Classe actualizació per exemplificar l'actualització de fitxers
 *
 * @author miquelmascarooliver
 */
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;

enum TipusAct {

    MODIFICACIO
}

/**
 * Atributs: nom: Cadena de caracters codi: enter act: tipus d'actualització en
 * aquest cas totes les actualitzacions són modificacions dels articles
 *
 */
public class Actualitzacio implements Serializable {

    private String nom;
    private int codi;
    private TipusAct act;

    /**
     * centinela: constant usada per marca els fi de fitxer
     */
    public static final Actualitzacio CENTINELA = new Actualitzacio("zzz", 999);

    /**
     * Constructors: Inicialitza el nom a la cadena buida i el codi a zero
     */
    public Actualitzacio() {
        nom = null;
        codi = 0;
        act = TipusAct.MODIFICACIO;
    }

    /**
     * Inicialitza el nom amb el primer paràmetre i el codi amb el segon de
     * forma automàtica assigna al tipus d'actualització el valor de MODIFICACIO
     *
     * @param s
     * @param x
     */
    public Actualitzacio(String s, int x) {
        nom = s;
        codi = x;
        act = TipusAct.MODIFICACIO;
    }

    @Override
    public String toString() {
        return "Actualitzacio{" + " nom=" + nom + " codi=" + codi + " act=" + act + '}';
    }

    /**
     *
     * @param x
     */
    public void setCodi(int x) {
        codi = x;
    }

    /**
     *
     * @param s
     */
    public void setNom(String s) {
        nom = s;
    }

    /**
     * Posa l'actualització a MODIFICACIO
     */
    public void setAct() {
        this.act = TipusAct.MODIFICACIO;
    }

    /**
     *
     * @return
     */
    public int getCodi() {

        return codi;
    }

    /**
     *
     * @return
     */
    public String getNom() {
        return nom;
    }

    /**
     * retorna vertader si l'actualització és el centinel·la, com que el fitxer
     * ha d'estar ordenat per codi es suposarà que el darrer val 999
     *
     * @return
     */
    public boolean esCentinela() {
        return codi == 999;
    }
}
