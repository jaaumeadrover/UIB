/*
CLASSE LlistaCursos.
FUNCIONALITAT: classe que ens permet l'enumeració de objectes Curs mitjançant
punters.
 */
package Cursos;

import Interfaces.Interf_Lista;

/**
AUTOR: Joan Balaguer,Jaume Adrover
DATA CREACIÓ: 11/11/2021.
 */
 
public class LlistaCursos implements Interf_Lista{
    private Curs primer;
    private int contador;
    
    public LlistaCursos(){
        primer=new Curs();
        contador=0;
    }

    @Override
    public int getSize() {
        return contador;
    }

    @Override
    public void afegeixElement(Object obj) {
        
        //afegir element
        
        
        contador++;
    }

    @Override
    public void eliminaElement(String s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getElement(String s) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
