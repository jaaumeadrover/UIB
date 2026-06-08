package DefPersona;

/**
 * Classe Persona
 * @author miquelmascarooliver
 */
public class Persona {

    private String nom;
    private int codi;

    /**
     * Es defineix com una cadena de caracters i un enter, nom i codi respectivament
     * El constructor inialitza la cadena i l'enter
     */
    public Persona() {
        nom = null;
        codi = 0;
    }

    @Override
    public String toString() {
        return "Persona{" + "nom=" + nom + " / codi=" + codi + '}';
    }

    /**
     * Assigna un enter a codi
     * @param codi
     */
    public void setCodi(int codi) {
        this.codi = codi;
    }

    /**
     * Assigna un string a nom
     * @param nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

}
