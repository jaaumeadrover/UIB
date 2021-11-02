/*
CLASE FrecuenciaCaracteres
 */
package pràcticafinaljaumejoan;

public class FrecuenciaCaracteres {
    //DECLARACIÓN ATRIBUTOS
    private static final char [] caracteres={'a','b','c','d','e','f','g','h','i','j',
                                             'k','l','m','n','ñ','o','p','q','r','s',
                                             't','u','v','w','x','y','z',' ','.',',',
                                             ':','@','?','!','"','(',')','<','>'};
    private int [] frecuencias=new int[caracteres.length];
    
    //MÉTODOS
    //CONSTRUCTOR
    public FrecuenciaCaracteres() {
        //inicialización array frecuencias
        for (int indice=0;indice<frecuencias.length;indice++) {
            frecuencias[indice]=0;
        }
    }
    
    //MÉTODOS FUNCIONALES
    
    //MÉTODO actualización TIENE COMO OBJETIVO ACTUALIZAR EL ARRAY DE frecuencias
    //DE UN OBJETO frecuenciaCaracteres DADO UN CARACTER PASADO POR PARÁMETRO
    public void actualizacion(char caracter) {
        //DECLARACIONES
        //declaración variable entera para representar la componente, en el
        //array caracteres del caracter dado por parámetro
        int posicion;
        if (valido(caracter)) {
            //localizar el caracter dado en el array caracteres
            for (posicion=0;caracter!=caracteres[posicion];posicion++) {}
            //en posicion tendremos el índice de la componente, en el array
            //caracteres, donde se ha localizado el caracter dado.
            //Con ello podemos incrementar la frecuencia de aparición
            //de dicho caracter en el array frecuencias
            frecuencias[posicion]++;              
        }
    }
    
    //MÉTODO toString QUE LLEVA A CABO LA CONVERSIÓN DE UN OBJETO frecuenciaCaracteres
    //a String con fines de visualización 
    public String toString() {
        //DECLARACIONES
        String tmp="\n";
        //Asignación al String local tmp por concatenación la información
        //relativa al objeto frecuenciaCaracteres
        for (int indice=0;indice<caracteres.length;indice++) {
            //concatencación al String local tmp del caracter indice-ésimp y de su
            //frecuencia de aparición
            tmp=tmp+"Frecuencia caracter "+caracteres[indice]+": "+frecuencias[indice]+
                    ".\n";
            
        }
        //devolver String local tmp resultante
        return tmp;
    }
    
    
    
    //MÉTODO obtenerFrecuencia LLAVA A CABO LA OBTENCIÓN DE LA FRECUENCIA DE APARICIÓN
    //DE UN CARACTER DADO CORRESPONDIENTE A UN OBJETO frecuenciaCaracteres
    public int obtenerFrecuencia(char caracter) {
        //DECLARACIONES
        //declaración variable entera que represente la posición que ocupa en
        //el array de caracteres el caracter dado
        int posicion;
        if (valido(caracter)) {
            //ACCIONES
            //buscar la posición del caracter dado en el array caracteres
            for (posicion=0;caracter!=caracteres[posicion];posicion++) {}   
            //devolución frecuencia del caracter dado
            return frecuencias[posicion];            
        }
        return -1; //frecuencia inexistente de un caracter no encuadrado en
                   //la estructura de frecuencias.
    }
    
    //MÉTODO valido QUE TIENE COMO OBJETIVO EL VERIFICAR SI UN CARACTER DADO
    //ES UN CARACTER VÁLIDO PARA GESTIONAR SU FRECUENCIA
    public boolean valido(char caracter) {
        //declaración variable entera para utilizarlo como índice en la búsnqueda
        //del caracter dado en el atributo array caracteres
        int indice;
        //buscar el caracter dado en el atributo aray caracteres para verificar
        //si el caracter dado es un caracter del que se puedfe gestionar
        //su frecuencia a través de esta clase
        for (indice=0;((indice<caracteres.length)&&(caracter!=caracteres[indice]));indice++) {}
        //Si la condición indice<caracteres.length)sigue siendo verdad entonces 
        //el caracter dado está dentro del array caracteres
        return (indice<caracteres.length);
    }
    
    
    
    public char LetraMasRepetida(){
        int i;
        int indice=0;
        int frecuenciamax=0;
        for(i=0;(i<frecuencias.length);i++){
            if (frecuenciamax<frecuencias[i]){
                frecuenciamax=frecuencias[i];
                indice=i;
            }
            
        }
        return caracteres[indice];
    }
    
}
