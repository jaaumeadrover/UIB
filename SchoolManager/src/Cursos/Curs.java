/*
CLASSE Curs.
FUNCIONALITAT:
 */
package Cursos;

import Asignatures.Assignatura;
import Asignatures.LlistaAsignatures;
import Interfaces.Dades;

/*
  AUTOR: Joan Balaguer,Jaume Adrover
  DATA CREACIÓ:12/11/2021
 */
public class Curs implements Dades, Comparable<Curs> {
    protected final String nom;
    protected final String codi;
    protected LlistaAsignatures asignAsociades;

    public Curs(String s, String c) {
        nom = s;
        codi = c;
        asignAsociades = new LlistaAsignatures();
    }

    public void afegirAsign(Assignatura a) {
        asignAsociades.afegeixElement(a);
        asignAsociades.ordenaLlista();
    }
    public void eliminaAsign(int n){
        asignAsociades.eliminaElement(n);
    }
    
    //MÈTODES GETTERS
    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public String getCodi() {
        return codi;
    }

    public LlistaAsignatures getAsignatures() {
        return asignAsociades;
    }

    public Assignatura getAsign(int n) {
        return (Assignatura) asignAsociades.getElement(n);
    }
    
    
    
    
    
    //MÈTODE COMPARETO PER A ORDENACIÓ LLISTES
    @Override
    public int compareTo(Curs t) {
        for (int i = 0; i < this.toString().length(); i++) {
            if (this.nom.charAt(i) > t.nom.charAt(i)) {
                return 1;
            } else if (this.nom.charAt(i) < t.nom.charAt(i)) {
                return -1;
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Curs{" + "nom=" + nom + ", codi=" + codi + '}';
    }

    public String toStringMAXX() {
        return "Curs{" + "nom=" + nom + ", codi=" + codi + ", assignatures associades:\n" + asignAsociades.toStringMAX() + '}';
    }
    
    public String toStringMAXXX() {
        return "El Curs " + nom + " té assignat el codi " + codi + ", esta conformat per les següents assignatures: \n\n" + asignAsociades.toStringMAX();
    }
    public String toStringMAX() {
        return "El Curs " + nom + " té assignat el codi " + codi + ", esta conformat per les següents assignatures: \n\n" + asignAsociades.toStringMAX();
    }
}
