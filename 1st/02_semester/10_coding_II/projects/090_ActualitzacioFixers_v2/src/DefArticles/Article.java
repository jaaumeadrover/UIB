package DefArticles;

/**
 * Classe articles per exemplificar l'actualització de fitxers
 * @author miquelmascarooliver
 */
import java.io.*;

/**
 * Atributs:
 * nom: Cadena de caracters
 * codi: enter

 */
public class Article implements Serializable {
    private String nom;
    private int codi;

    /**
     * centinela: constant usada per marcar els fi de fitxer
     */
    public static final Article CENTINELA = new Article ("zzz", 999);

    /**
     * Inicialitza el nom a la cadena buida i el codi a zero
     */
    public Article() {
        nom = null;
        codi = 0;
    }

    /**
     * Inicialitza el nom amb el primer paràmetre i el codi amb el segon
     * @param s
     * @param x
     */
    public Article(String s, int x) {
        nom = s;
        codi = x;
    }

    @Override
    public String toString() {
        return "Article{" + "nom= " + nom + " codi= " + codi + '}';
    }

    /**
     *
     * @param cod
     */
    public void setCodi(int cod) {
            codi = cod;
    }

    /**
     *
     * @param n
     */
    public void setNom(String n) {
        nom = n;
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
     * retorna vertader si l'article és el centinel·la com el que treballarem
     * @return
     */
    public boolean esCentinela(){
        return codi == 999;
    }

    /**
     * canvia el nom de l'article segons l'actualització que li entra per
     * paràmetre
     * @param m
     */
    public void modifica(Actualitzacio m) {
        nom = m.getNom();
    }
}
