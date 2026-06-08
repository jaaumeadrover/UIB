package DefPersona;

import java.io.*;

/**
 * Classe Persona
 *
 * Atributs: nom: Cadena de caracters codi: enter
 *
 * @author miquelmascarooliver
 */
public class Persona
        implements Serializable {

    private String nom;
    private int codi;

    public static final Persona centinela = new Persona("zzz", 999);

    /**
     * Inicialitza el nom a la cadena buida i el codi a zero
     */
    public Persona() {
        nom = null;
        codi = 0;
    }

    /**
     * Inicialitza el nom amb el primer paràmetre i el codi amb el segon
     *
     * @param s
     * @param x
     */
    public Persona(String s, int x) {
        nom = s;
        codi = x;
    }

    @Override
    public String toString() {
        return "Persona{" + "nom= " + nom + "codi= " + codi + '}';
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
        try {
            nom = s;
        } catch (Exception e) {
        }
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
     * Retorna vertader si la persona té de nom "zzz"
     * @return 
     */
    public boolean esCentinela() {
        return nom.equals(centinela.nom);
    }

}
