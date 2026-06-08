/*
CLASSE OBLIGATORIA.
FUNCIONALITAT:
 */
package Asignatures;

import Cursos.Curs;

/**
AUTOR: Joan Balaguer, Jaume Adrover
DATA CREACIÓ: 12/11/2021
 */
public class Obligatoria extends Assignatura{
    private final String credits;
    
    public Obligatoria(String s,String d,Curs c,String n) {
        super(s,d,c);
        credits=n;
    }
    public String getCredits(){
        return credits;
    }

    @Override
    public String toString() {
        return "Obligatoria{Nom=" +nom+",credits=" + credits + ",codi= "+codi+'}';
    }

    /**
     *
     * @return
     */
    @Override
    public String toStringMAX(){
        return "Obligatoria{Nom=" +nom+",credits=" + credits + ",codi= "+codi+",alumnes=\n"+estudiantsAsociats+'}';
    }
}
