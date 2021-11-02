/*
CLASE PalabraFicherosIn
Aglutina las delcaraciones y funcionalidades necesarias para posibilitar la lectura
de objetos Palabra desde un fichero de texto
 */
package pràcticafinaljaumejoan;

import java.io.BufferedReader;
import java.io.FileReader;

public class PalabraFicherosIn {
    //DECLARACIONES ATRIBUTOS
    //Declaración atributo de clase constante entero que representa el final de
    //un fichero
    private static final int FINAL_FICHERO=-1;
    //declaración atributo de clase constante entero que representa el código
    //de caracter del caracter espacio en blanco
    private static final int COD_ESPACIO=(int) ' ';
    //declaración atributo de clase constante entero que representa el código
    //del caracter de control RETURN
    private static final int RETURN=(int) '\r';
    //declaración atributo de clase constante entero que representa el código
    //del caracter de control SALTO DE LINEA
    private static final int SALTO_LINEA=(int) '\n';
    //declaración atributo de clase variable entero que represente el código
    //de caracter leido desde el fichero
    private int codigo=COD_ESPACIO;
    //declaración atributo de objeto BufferedReader que posibilite el enlace
    //con el fichero de texto a nivel de lectura
    private BufferedReader fichero=null;
    //declaración atributo de objeto que represente el número de línea actual
    //del fichero
    private int linea;
    //declaración atributo de objeto que represente el número de columna 
    //actual del fichero
    private int columna;  
    
    private int numeroPalabras=0;

    
    //MÉTODOS
    //MÉTODO CONSTRUCTOR
    public PalabraFicherosIn(String nombreFichero) throws Exception {
        //establecimiento enlace BufferedReader con fichero de texto identificado
        //a través del parámetro String nombreFichero dado
        fichero=new BufferedReader(new FileReader(nombreFichero));
        //inicializar los atributos linea y columna actuales del fichero
        linea=1;
        columna=0;
    }
    
    //MÉTODOS FUNCIONALES
    //MÉTODO hayPalabras QUE VERIFICA SI HAY ALGUNA PALABRA EN EL FICHERO REPRESENTADO
    //A TRAVÉS DEL OBJETO BufferedReader CORRESPONDIENTE
    public boolean hayPalabras() throws Exception {
        buscarPalabra();
        return (codigo!=FINAL_FICHERO);
    }
    
    //MÉTODO buscarPalabra LLEVA A CABO LA BÚSQUEDA DE UNA PALABRA EN EL FICHERO
    //REPRESENTADO POR EL OBJETO BufferedReader CORRESPONDIENTE.
    //LA BÚSQUEDA ESTÁ FUNDAMENTADA EN IDENTIFICAR UN CÓDIGO DE CARACTER LEIDO
    //DESDE EL FICHERO COMO ALBABÉTICO MINÚSCULA, LO CUAL, SINGNIFICARÁ
    //QUE SE HA ENCONTRADO UNA PALABRA EN EL FICHERO. SI EN EL FICHERO NO
    //QUEDASEN PALABRAS POR LEER ENTONCES LA BUSQUEDA TERMINARÍA CON EL
    //FINAL DE FICHERO.
    //TODO ELLO, DEBIDO A QUE EL JUEGO DE CARACTERES CON EL QUE TRABAJAMOS
    //AHORA ESTÁ CONSTITUIDO POR LOS CARACTERES ALFABÉTICOS MINÚSCULAS Y EL
    //ESPACIO EN BLANCO, LA CONDICIÓN DE BÚSQUEDA ESTARÁ FUNDAMENTADA
    //EN SEGUIR LEYENDO DEL FICHERO MIENTRAS EL CÓDIGO DE CARACTER LEIDO SE
    //CORRESPONDA CON EL CARACTER ESPACIO EN BLANCO
    private void buscarPalabra() throws Exception {
        //lectura desde el fichero mientras el código de caracter leido
        //sea igual al espacio en blanco
        while ((codigo==RETURN)||(codigo==SALTO_LINEA)
                ||(esSeparador(codigo))) {
            //actualización linea y columna correspondiente al código de caracter
            //leido desde el fichero
            actualizacionLineaColumna();
            //lectura siguiente código de caracter desde el fichero
            codigo=fichero.read();
        }
    }
    
    //MÉTODO lectura LLEVA A CABO LA LECTURA DE UNA PALABRA DESDE EL FICHERO
    //REPRESENTADO POR EL OBJETO BufferedReader CORRESPONDIENTE
    public Palabra lectura() throws Exception {
        //DECLARACIONES
        //declaración objeto Palabra que reporesentara la palabra leida desde
        //el fichero
        Palabra palabra=new Palabra();
        //declaración variable booleana para identificar si ya se le ha
        //asignado al objeto Palabra la linea y columna
        boolean asignacionPosicion=false;
        
        //ACCIONES
        //lectura y almacenamiento de los caracteres de la palabra correspondientes
        //a los códigos de caracteres leidos desde el fichero
        while ((codigo!=FINAL_FICHERO)&&(codigo!=RETURN)
                &&(codigo!=SALTO_LINEA)&&(!esSeparador(codigo))) {
            if (!asignacionPosicion)  {
                //asignación al objeto Palabra de la linea y columna donde
                //está ubicado en el fichero
                palabra.putLinea(linea);
                palabra.putColumna(columna);
                asignacionPosicion=true;
            }            
            actualizacionLineaColumna();
            //almacenar en el objeto Palabra palabra el caracter correspondiente
            //al código de caracter leido desde el fichero
            palabra.adicionCaracter((char) codigo);
            //lectura siguiente código de caracter desde el fichero
            codigo=fichero.read();           
        }
        //Devolver el objeto Palabra
        return palabra;
    }
    
    //MÉTODO cerrarEnlaceFichero QUE LLEVA A CABO EL CIERRE DEL ENLACE BufferedReader
    //con el fichero 
    public void cerrarEnlaceFichero() throws Exception {
        fichero.close();
    }
    
    
    //MÉTODO esSeparador VERIFICA SI EL CÓDIGO DE CARACTER DADO SE CORRESPONDE
    //CON UN CARACTER SEPARADOR
    private boolean esSeparador(int cod) {
        return ((cod==COD_ESPACIO)||(cod==',')||(cod=='.')||(cod==':')||(cod=='@')||(cod=='?')||
                (cod=='!')||(cod=='"')||(cod=='(')||(cod==')')||(cod=='<')||
                (cod=='>'));
    }
    
    //MÉTODO actualizacionLineaColumna LLEVA A CABO LA ACTUALIZACIÓN DEL NÚMERO
    //LINEA Y COLUMNA ACTUALES EN EL FICHERO
    public void actualizacionLineaColumna() {
        if (codigo==SALTO_LINEA) {
            linea++;
            columna=1;
        }
        else {
            columna++;
        }
    }  
    
    public int GetNumeroPalabras()throws Exception{
        
        
        while(hayPalabras()){
            lectura();
            numeroPalabras++;
        }
        return numeroPalabras;
    }
    
    public int getLinea(){
       return linea;
   }
    
   public int getColumna(){
       return columna;
   }
   
   
}
