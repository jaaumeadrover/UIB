/*
INTERFACE LLISTA.
FUNCIONALITAT: serveix per a implementar totes les operacions que uilitzaran
les llistes en aquesta pràctica.
 */
package Interfaces;

import java.util.ArrayList;

/**
AUTOR: Joan Balaguer,Jaume Adrover
DATA CREACIÓ: 11/11/2021.
 */

public interface Interf_Lista {

    public int getSize();

    public void afegeixElement(Object obj);

    public void eliminaElement(int n);
    
    public Object getElement(int n);//Donar per paràmetre un int?
    
    public void ordenaLlista();
    
    public boolean isRepetit(Object obj);
    
    public String [] nomsToArr();
    
    public ArrayList<String> nomsCompletToArrayList();
    
    public ArrayList<String> nomsToArrayList();
    
    public Object cercaElement(Object e);
    
    @Override
    public String toString();
}
