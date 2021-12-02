/*
CLASSE ASIGNATURA.
FUNCIONALITAT:
 */
package Asignatures;

import Estudiant.Estudiant;
import Cursos.Curs;
import Interfaces.Dades;
import Llista_Ref.Llista_Estudiants_Ref;
import java.util.ArrayList;

/**
AUTOR: Joan Balaguer, Jaume Adrover
DATA CREACIÓ: 12/11/2021
 */

public class Assignatura implements Dades,Comparable <Assignatura>{
    protected final String nom;
    protected final String codi;
    protected Curs cursPertanyent;
    protected Llista_Estudiants_Ref estudiantsAsociats;
    
    public Assignatura(String s,String d,Curs c){
        nom=s;
        codi=d;
        cursPertanyent=c;
        estudiantsAsociats=new Llista_Estudiants_Ref();
    }
    
    public void afegirEstudiant(Estudiant a){
        estudiantsAsociats.afegeixElement(a);
    }
    //MÈTODES GETTERS
    @Override
    public String getNom() {
        return nom;
    }
    public ArrayList<String> getNomsCompletsEstudiants(){
        return estudiantsAsociats.nomsCompletToArrayList();
    }
    public ArrayList<String> getNomsEstudiants(){
        return estudiantsAsociats.nomsToArrayList();
    }
    @Override
    public String getCodi() {
         return codi;
    }
    public Curs getCurs(){
        return cursPertanyent;
    }
    public Llista_Estudiants_Ref getLlistaEstudiants(){
        return estudiantsAsociats;
    }
    //COMPARETO PER A ORDENACIÓ DE LLISTA
    @Override
    public int compareTo(Assignatura t) {
//        for (int i = 0; i < this.toString().length(); i++) {
//            
//            if((this.nom.length() >= i) && (t.nom.length() >= i)){
//                if(this.nom.charAt(i)>t.nom.charAt(i)){
//                    return 1;
//                }else if(this.nom.charAt(i)<t.nom.charAt(i)){
//                    return -1;
//                }
//            }
//        }
//       return 0;
    return this.nom.compareTo(t.nom); 
    }
    
    public void setCurs(Curs c){
        this.cursPertanyent=c;
    }
    //2 MÈTODES TOSTRING PER A DIFERENTS USOS
    @Override
    public String toString() {
        return "Asignatura{" + "nom=" + nom + ", codi=" + codi +'}';
    }
    public String toStringMAXX(){
        return "Asignatura{" + "nom=" + nom + ", codi=" + codi + ",alumnes associats:\n"+estudiantsAsociats+'}';
    }
    
    public String toStringMAX(){
        return "-L'Assignatura: " + nom + " té assignat el codi " + codi + " i té els següents estudiants matriculats:\n" + estudiantsAsociats + "\n";
    }
}
