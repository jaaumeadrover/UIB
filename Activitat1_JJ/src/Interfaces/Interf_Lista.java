/*
INTERFACE LLISTA.
FUNCIONALITAT: serveix per a implementar totes les operacions que uilitzaran
les llistes en aquesta pràctica.
 */
package Interfaces;

/**
AUTOR: Joan Balaguer,Jaume Adrover
DATA CREACIÓ: 11/11/2021.
 */

public interface Interf_Lista {

    public int getSize();

    public void afegeixElement(Object obj);

    public void eliminaElement(String s);
    
    public Object getElement(String s);//Donar per paràmetre un int?
}
