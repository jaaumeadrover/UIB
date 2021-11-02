/*
Clase Linea --> aglutina las declaraciones y funcionalidades para gestionar
objetos Lineas.
Un objeto Linea estará constituido por una secuencia de caracteres delimitado por
un salto de linea. Como máximo el número de caracteres que puede conformar un
objeto Linea será de 250 caracteres.
 */
package pràcticafinaljaumejoan;


public class Linea {
    //DECLARACIONES
    //Declaración atributo de clase constante entero que representa el final de
    //un fichero
    private static final char FINAL_LINEA='\n';
    //declaración atributo de clase constante entero que representa el máximo
    //número de caracteres que puede contener un objetoLinea
    private static final int MAXIMO=250;
    //declaración atributo de objeto variable array de caracteres que representa 
    //los caracteres de un objeto Linea 
    private char [] caracteres=new char[MAXIMO];  
    //declaración atributo de objeto variable entero que represente el número
    //de caracteres de un objeto Linea
    private int numeroCaracteres;   
    //declaración atributo de clase variable caracter que representa el último
    //caracter leido desde la secuencia de entrada por teclado
    private static char caracter;
    
    
    //MÉTODOS
    
    //Métodos Constructores
    public Linea() {
        numeroCaracteres=0;
    }
    
    //Métodos funcionales
    //método que lleva a cabo la verificación de si una objeto Linea ha sido
    //totalmente leido desde el teclado
    public static boolean hayLinea() throws Exception {
        //lectura primer caracter de la secuencia de entrada por teclado
        caracter=LT.readCharacterLine();
        //verificar final de linea
        return (caracter!=FINAL_LINEA);
    }
    
    //método que lleva a cabo la lectura de una linea desde el teclado
    public void lectura() throws Exception {
        //inicialización de numeroCaracteres
        numeroCaracteres=0;
        while (caracter!=FINAL_LINEA) {
            //almacenar el caracter leido en el array caracteres del objeto Linea
            caracteres[numeroCaracteres]=caracter;
            //incrementar numeroCaracteres
            numeroCaracteres++;
            //lectura del siguiente caracter de la secuencia
            caracter=LT.readCharacterLine();    
        }
    }
    
    //método que lleva a cabo la conversión de un objeto Linea a String para su
    //visualización en pantalla

    @Override
    public String toString(){
        String salida="";
        for (int indice=0;indice<numeroCaracteres;indice++) {
            salida=salida+caracteres[indice];
        }
        return salida;
    }
    
    
        
    //método que lleva a cabo la adición de un caracter dado por parámetro
    //al objeto Linea
    public void adicionCaracter(int cod) {
        caracteres[numeroCaracteres]=(char)cod;
        numeroCaracteres++;
    }
    
    
    
    
    
    //MÉTODO QUE NOS SIRVE PARA BUSCAR TEXTO
    public boolean ContieneLinea(Linea a){
        
     if (numeroCaracteres>=a.numeroCaracteres) {
         //verificación de si la palabra pasada por parámetro está contenida
            //en el objeto Palabra correspondiente
            for (int indice=0;indice<numeroCaracteres;indice++) {
                //verificar que el número de caracteres que restan por verificar del objeto
                //Palabra es mayor o igual que el número de caracteres de
                //la palabra a verificar que está contenida
                if ((numeroCaracteres-indice)>=a.numeroCaracteres) {
                    //declaración variable booleana para verificar si la palabra
                    //buscada está contenida en el objeto Palabra a partir del
                    //su caracter almacenado en la componente indice-ésima
                    boolean contenida=true;
                    for (int indice2=0;indice2<a.numeroCaracteres;indice2++) {
                        if (caracteres[indice+indice2]!=a.caracteres[indice2]) {
                            contenida=false;
                        }
                    }
                    //si la variable contenida sigue siendo true significará
                    //que el objeto Palabra contiene a la palabra dada por parámetro
                    if (contenida) {
                        return true;
                    }
                }
            }
            //una vez terminado el bucle de verificación podemos concluir que
            //el objeto linea NO contiene a la linea dada por parámetro
            return false;
     }else {
            //el objeto Palabra no contiene a la palabra dada por parámetro porque
            //su número de caracteres es menor a la palabra dada
            return false;
        }
     
    }
}
