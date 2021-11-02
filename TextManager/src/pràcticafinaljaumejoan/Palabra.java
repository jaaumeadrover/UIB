/*
CLASE Palabra
ENTORNO DE TRABAJO:
La secuencia de caracteres introducida por teclado puede contener como 
caracteres os siguientes: espacios en blanco, saltos de línea, caracteres
del alfabeto (minúsculas sin acentos ni diéresis), el espacio en blanco y los siguientes 
signos de puntuación: . , : @ ? ! " ( ) < >.
El final de secuencia vendrá dado por el caracter de control salto de línea '\n'
 */
package pràcticafinaljaumejoan;


public class Palabra {
    //ATRIBUTOS (describen el estado de los objetos Palabra)
    //declaración atributo de clase constante caracter que representa el final de
    //la secuencia de caracteres
    private static final char FINAL_SECUENCIA='\n';
    //declaración atributo de clase constante caracter que representa el espacio
    //en blanco
    private static final char ESPACIO=' ';
    //declaración atributo de clase constante entero que representa el máximo número
    //de caracteres que puede aglutinar un objetp Palabra
    private static final int MAXIMO_NUMERO_CARACTERES=20;
    //declaración atributo de clase variable caracter que representa el caracter leido
    //de la secuencia de caracteres introducida por teclado
    private static char caracter=ESPACIO; //es inicializada con el caracter espacio
                                          //para llevar a cabo la lectura
                                          //del primer caracter de la secuencia
                                          //a través del método buscarPalabra() la
                                          //primera vez que es llamado.
    //declaración atributo de objeto variable array de componentes caracter que representa
    //los caracteres de un objeto Palabra
    private char [] caracteres=new char[MAXIMO_NUMERO_CARACTERES];
    //declaración atributo de objeto variable entero que representa el número de caracteres
    //de un objeto Palabra
    private int numeroCaracteres;
    //declaración atributo de clase que posibilita representar el número
    //de linea de la secuencia de entrada por teclado (es 1 siempre)
    private static int lineaSecuencia=1;
    //declaración atributo de clase que posibilita representar el
    //número de la columna actual de lectura de la secuencia de entrada por teclado
    private static int columnaSecuencia=0;
    //declaración atributo de objeto que posibilita representar el número
    //de linea donde se encuentra el objeto Palabra 
    //Si el atributo linea de un objeto Palabra es 0 significará que
    //dicha palabra no se deriva de una secuencia o fichero
    private int linea=0;
    //declaración atributo de objeto que posibilita representar el
    //número de la columna donde comienza el objeto Palabra
    //Si el atributo columna de un objeto Palabra es 0 significará que
    //dicha palabra no se deriva de una secuencia o fichero
    private int columna=0;    
    
    //MÉTODOS
    
    //CONSTRUCTORES
    public Palabra() {
        numeroCaracteres=0;
    }
    
    
    
    //MÉTODOS FUNCIONALES (describen el comportamiento de los objetos Palabra)
    
    //MÉTODO buscarPalabra QUE TIENE COMO OBJETIVO LA BÚSQUEDA DE UNA PALABRA
    //EN LA SECUENCIA DE CARACTERES DE ENTRADA. DICHA BÚSQUEDA SE BASA EN LA
    //LECTURA E IDENTIFICACIÓN DEL PRIMER CARACTER DE LA PALABRA
    private static void buscarPalabra() throws Exception {
        //leer siguiente caracter mientras el caracter leido no sea ni alfabético
        //ni final de secuencia ('.'), es decir, como el conjunto de caracteres
        //que entran en juego en el entorno de tratamiento secuencia son: los
        //caracteres alfabéticos minúsculas, es espacio en blanco y el '.', la
        //condición compuesta anterior es análoga a decir que hay que seguir leyendo
        //desde la secuencia mientras que el caracter leido sea espacio en blanco
        while (esSeparador(caracter)) {
            //lectura siguiente caracter de la secuencia
            caracter=LT.readCharacterLine();
            //actualización columna actual de lectura de la secuencia
            columnaSecuencia++;
        }
    }
    
    //MÉTODO hayPalabras QUE TIENE COMO OBJETIVO EL RESPONDER A LA PREGUNTA DE SI
    //HAY ALGUNA PALABRA EN LA SECUENCIA DE CARACTERES PARA LEER
    public static boolean hayPalabras() throws Exception {
        buscarPalabra();
        return (caracter!=FINAL_SECUENCIA);
    }
    
    //MÉTODO lectura QUE TIENE COMO OBJETIVO LA LECTURA DE UN OBJETO Palabra 
    //DESDE LA SECUENCIA DE CARACTERES DE ENTRADA.
    //NOTA: EL MÉTODO lectura SÓLO PODRÁ LLAMARSE DESPUÉS DE HABER LLAMADO AL
    //MÉTODO hayPalabras Y QUE SE HAYA VERIFICADO QUE HAY ALGUNA PALABRA PARA LEER
    //
    // VERSIÓN MÉTODO DE OBJETO
    //
    public void lectura() throws Exception {
        //inicializar a 0 el atributo numeroCaracteres del objeto Palabra que ha
        //llamado a este método y que será el obvjeto en el que vamos a almacenar
        //la palabra leida desde la secuencia
        numeroCaracteres=0;
        //declaración variable booleana para identificar si se ya se le ha
        //asignado al objeto Palabra la linea y columna
        boolean asignacionPosicion=false;
        
        //lectura y almacenamiento de los caracteres de la palabra leida desde
        //la secuencia de caracteres
        while ((caracter!=FINAL_SECUENCIA)&&(!esSeparador(caracter))) {
            if (!asignacionPosicion) {
                //Asignación al objeto Palabra linea y columna
                linea=lineaSecuencia;
                columna=columnaSecuencia;
                //actualizar variable control asignación
                asignacionPosicion=true;
            }
            //almacenar el caracter de la palabra leido en la componente
            //correspondiente del atributo array caracteres del objeto Palabra
            caracteres[numeroCaracteres]=caracter;
            //inrementar el atributo numeroCaracteres del objeto Palabra en una
            //unidad porque acabamos de almacenar el array caracteres un caracter
            //de la palabra
            numeroCaracteres++;
            //lectura siguiente caracter de la secuencia
            caracter=LT.readCharacterLine();
            //actualización columna actual de lectura de la secuencia
            columnaSecuencia++;
        } 
    }
    
    //MÉTODO toString que tiene como objetivo la conversión de un objeto Palabra en
    ///String con fines de ser utilizado con los métodos de visualización System.out.print
    //y System.out.println
    public String toString() {
        //DECLARACIÓN
        String temporal="";
        //añadir, por concxatenación, al String temporal los caracteres del objeto
        //palabra
        for (int indice=0;indice<numeroCaracteres;indice++) {
            //concatenar al String temporal el caracter del objeto Palabra
            //que está almacenado en la componente indice-ésima del array
            //caracteres de dicho objeto
            temporal=temporal+caracteres[indice];
        }
        //devolver String resultante
        return temporal;
    }
    
   
   
    
    
    
    
    
    
   
    
    //MÉTODO getNumeroCaracteres QUE TIENE COMO OBJETIBO EL OBTENER EL
    //NÚMERO DE CARACTERES DE UN OVJETO Palabra
    public int getNumeroCaracteres() {
        return numeroCaracteres;
    }
    
    //MÉTODO iguales QUE TIENE COMO OBJETIVO VERIFICAR SI UN OBJETO Palabra
    //ES IGUAL A OTRO OBJETO Palabra PASADO POR PARÁMETRO
    public boolean iguales(Palabra a) {
        //verificar si tienen el mismo número de caracteres
        if (numeroCaracteres==a.numeroCaracteres) {
            //verificar si son iguales a nivel de caracteres
            for (int indice=0;indice<numeroCaracteres;indice++) {
                //comparar los caracteres de ambas palabras que están en las
                //componentes indice-ésimas de los atributos arrays caracteres
                //de ambos objetos
                if (caracteres[indice]!=a.caracteres[indice]) {
                    //devolver el valor false porque ambas palabras son diferentes
                    //en cuanto que los dos caracteres que se acaban de comparar
                    //son diferentes
                    return false;
                }
            }
            //devolvervalor true porque ambas palabras son iguales
            return true;
        }
        else {
            //devolver el valor false porque las palabras son diferentes en cuanto
            //a que tienen un número de caracteres diferente
            return false;
        }
    }
    
    //MÉTODO copiar QUE TIENE COMO OBJETIO COPIAR UN OBJETO Palabra EN OTRO OBJETO
    //Palabra DADO PORPARÁMETRO
    public void copiar(Palabra a) {
        //copiar el atributo numeroCaracteres
        a.numeroCaracteres=numeroCaracteres;
        //copiar los caracteres del atributo array caracteres componente a componente
        for (int indice=0;indice<numeroCaracteres;indice++) {
            a.caracteres[indice]=caracteres[indice];
        }
        a.linea=linea;
        a.columna=columna;
    }
  
    //MÉTODO adicionCaracter LLEVA A CABO LA ADICIÓN DE UN CARACTER DADO POR PARÁMETRO
    //EN UN OBJETO Palabra
    public void adicionCaracter(char caracter) {
        //almacenar el caracter dado en la primera componente libre del atributo
        //caracteres del objeto Palabra. El índice de dicha componente coincide
        //con el valor del atributo numeroCaracteres del mismo objeto Palabra
        caracteres[numeroCaracteres]=caracter;
        //incrementar el atributo numeroCaracteres del objeto Palabra para que
        //tenga en cuenta el caracter que acabamos de añadir en la palabra
        numeroCaracteres++;
    }
    
    //MÉTODO obtenerCaracter LLEVA A CABO LA OBTENCIÓN DEL CARACTER DE UNA
    //PALABRA QUE ESTÁ ALMACENADO EN LA POSICIÓN DADA DENTRO DEL ATRIBUTO
    //caracteres DE DICHO OBJETO Palabra
    public char obtenerCaracter(int posicion) {
        //devolver el caracter del objeto Palabra que está en la posición
        //dada dentro del atributo array caracteres
        return (caracteres[posicion]);
    }

    //MÉTODO esSeparador VERIFICA SI EL CÓDIGO DE CARACTER DADO SE CORRESPONDE
    //CON UN CARACTER SEPARADOR
    private static boolean esSeparador(char car) {
        return ((car==',')||(car=='.')||(car==':')||(car=='@')||(car=='?')||
                (car=='!')||(car=='"')||(car=='(')||(car==')')||(car=='<')||
                (car=='>')||(car==ESPACIO));
    }    
    
    //MÉTODO putColumna LLEVA A CABO LA ESPECIFICACIÓN DE LA columna DONDE
    //COMIENZA UN OBJETO Palabra
    public void putColumna(int col) {
        columna=col;
    }

    //MÉTODO getColumna LLEVA A CABO LA OBTENCIÓN DE LA columna DONDE
    //COMIENZA UN OBJETO Palabra
    public int getColumna() {
        return columna;
    }  
    
    //MÉTODO putLinea LLEVA A CABO LA ESPECIFICACIÓN DE LA linea DONDE
    //SE ENCUENTRA UN OBJETO Palabra
    public void putLinea(int lin) {
        linea=lin;
    }   
    
    //MÉTODO getLinea LLEVA A CABO LA OBTENCIÓN DE LA linea DONDE
    //SE ENCUENTRA UN OBJETO Palabra
    public int getLinea() {
        return linea;
    }  
    
}
