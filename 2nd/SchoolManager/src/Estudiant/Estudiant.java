package Estudiant;

import Asignatures.Assignatura;
import Interfaces.Dades;
import Llista_Ref.Llista_Asignatures_Ref;
import java.util.ArrayList;

/*
CLASSE ALUMNE.
FUNCIONALITAT: 
 */
/**
 * AUTOR: Joan Balaguer, Jaume Adrover DATA CREACIÓ: 12/11/2021
 */
public class Estudiant implements Comparable<Estudiant>, Dades {

    private final String nom;
    private final String dni;
    private Llista_Asignatures_Ref asignAsociades;

    public Estudiant(String s, String dni) {
        nom = s;
        this.dni = dni;
        asignAsociades = new Llista_Asignatures_Ref();
    }

    @Override
    public String getNom() {
        return nom;
    }

    @Override
    public String getCodi() {
        return dni;
    }
    
    public Llista_Asignatures_Ref getAssignAssociades(){
        return asignAsociades;
    }
    
    public ArrayList<String> getNomsCompletsAssignatures(){
        return asignAsociades.nomsCompletToArrayList();
    }
    public ArrayList<String> getNomsAssignatures(){
        return asignAsociades.nomsToArrayList();
    }

    public void afegirAsignatura(Assignatura a) {
        asignAsociades.afegeixElement(a);
    }

    @Override
    public int compareTo(Estudiant t) {
        return this.nom.compareTo(t.nom);
    }

    @Override
    public String toString() {
        return "Nom: " + nom + "      DNI: " + dni;
    }

    public String toStringMAX() {
        return "Estudiant{" + "nom=" + nom + ", dni=" + dni + ", asignAsociades=\n" + asignAsociades + '}';
    }
    
    
}
