/*
CLASSE OPTATIVA.
FUNCIONALITAT:
 */
package Asignatures;

import Cursos.Curs;

/**
AUTOR: Joan Balaguer, Jaume Adrover
DATA CREACIÓ: 12/11/2021
 */
public class Optativa extends Assignatura{
    private final Perfil perfil;
    

    public Optativa(String s,String d,Curs c,int n) {
        super(s,d,c);
        perfil=Perfil.values()[n];
    }
    
    public Perfil getPerfil(){
        return perfil;
    }

    @Override
    public String toString() {
        return "Optativa{Nom="+nom + ",perfil=" + perfil+",codi="+codi+'}';
    }

    @Override
    public String toStringMAX(){
        return "Optativa{Nom="+nom + "perfil=" + perfil+",codi="+codi+", alumnes=\n"+estudiantsAsociats + '}';
    }
   
    
}
