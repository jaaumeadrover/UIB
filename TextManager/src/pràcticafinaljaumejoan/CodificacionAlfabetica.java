/*
CLASE CodificacionAlfabetica
 */
package pràcticafinaljaumejoan;

import static java.lang.Math.abs;
import java.util.Random;

public class CodificacionAlfabetica {
    //DECLARACIÓN ATRIBUTOS
    //declaración atributo de clase constante entero para representar 
    //número de caracteres que conforman el alfabeto
    private static final int NUMERO_CARACTERES=27;
    //declaración atributo de clase constante array de caracteres para representar
    //alfabeto
    private static final char [] ALFABETO="abcdefghijklmnñopqrstuvwxyz,.:@?!><()".toCharArray();
    //declaración atributo de objeto variable entero para representar el
    //desplazamiento del alfabeto utilizado en la codificación
    private int desplazamiento;
    
    //MÉTODOS
    //CONSTRUCTOR
    public CodificacionAlfabetica(int semilla) {
        //cálculo del desplazamiento en función de la semilla dada
        //para ello debemos declarar un objeto Random que actue como
        //generador con la semilla multiplicada por ejemplo por 1000 porque
        //si las semillas son pequeñas no hay cambios en la generación de
        //la secuencia aleatoria
        Random generador=new Random(semilla*1000);
        //obtención del desplazamiento de forma aleatoria a través del
        //generador.
        //el desplazamiento del alfabeto tendrá que tener un valor entero
        //perteneciente al intervalo [0,NUMERO_CARACTERES).
        desplazamiento=(int) (generador.nextDouble()*NUMERO_CARACTERES);
    }
    
    //MÉTODOS FUNCIONALES
    //MÉTODO codificar LLEVA A TÉRMINO LA OBTENCIÓN DEL CARACTER CODIFICADO
    //DEL CARACTER DADO POR PARÁMETRO
    public char codificar(char caracter) {
        if (valido(caracter)) {
            return ALFABETO[(posicion(caracter)+desplazamiento)%NUMERO_CARACTERES];
        }
        else {
            return caracter;
        }
    }

    //MÉTODOS FUNCIONALES
    //MÉTODO deCodificar LLEVA A TÉRMINO LA OBTENCIÓN DEL CARACTER DECODIFICADO
    //DEL CARACTER DADO POR PARÁMETRO
    public char deCodificar(char caracter) {
        if (valido(caracter)) {
            int valor=posicion(caracter)-desplazamiento;
            if (valor<0) {
                valor=NUMERO_CARACTERES-abs(valor);
            }
            return ALFABETO[valor%NUMERO_CARACTERES];            
        }
        else {
            return caracter;
        }
    }    
    //MÉTODO posicion OBTIENE LA POSICIÓN EN EL ARRAY ALFABETO DE UN CARACTER
    //DADO POR PARÁMETRO
    public int posicion(char caracter) {
        //declaración variable indice para acceder al array ALFABETO
        int indice;
        //búsqueda del caracter dado en el array ALFABETO
        for (indice=0;caracter!=ALFABETO[indice];indice++) {}
        //devolución posición del caracter dado
        return indice;
    }
    
    //MÉTODO valido VERIFICA SI UN CARACTER DADO ES UN CARACTER QUE PUEDE
    //SER CODIFICADO O DECODIFICADO
    public boolean valido(char caracter) {
        //verificamos si el caracter está dentro del array ALFABETO, si es así,
        //devolveremos true, en caso contrario devolveremos false
        for (int indice=0;indice<ALFABETO.length;indice++) {
            if (caracter==ALFABETO[indice]) {
                return true;
            }
        }
        return false;
    }
    
}
