/*
CLASSE Curs.
FUNCIONALITAT:
 */
package Cursos;

import Interfaces.Dades;

/**
 *
 * @author Jaume A
 */
public class Curs implements Dades{
    private String nom;
    private int codi;
    private Curs seg;

    @Override
    public String getNom(){
        return nom;
    }

    @Override
    public int getCodi() {
        return codi;
    }
    
}
