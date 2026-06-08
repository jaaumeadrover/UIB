 /*
CLASE PalabraFicherosOut
Aglutina las delcaraciones y funcionalidades necesarias para posibilitar la escritura
de objetos Palabra en un fichero de texto
 */
package examenfinal;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class PalabraFicherosOut {
    //DECLARACIÓN ATRIBUTOS
    //declaración atributo de clase constante entero que representa el código
    //de caracter del caracter espacio en blanco
    private static final int COD_ESPACIO=(int) ' ';
    //declaración atributo de clase constante entero que representa el código
    //del caracter de control RETURN
    private static final int RETURN=(int) '\r';
    //declaración atributo de clase constante entero que representa el código
    //del caracter de control SALTO DE LINEA
    private static final int SALTO_LINEA=(int) '\n';
    //declaración atributo de objeto BufferedWriter que posibilite el enlace
    //con el fichero de texto a nivel de escritura
    private BufferedWriter fichero=null;  
    
    //MÉTODOS
    //MÉTODO CONSTRUCTOR
    public PalabraFicherosOut(String nombreFichero) throws Exception {
        //establecimiento enlace BufferedWriter con fichero de texto identificado
        //a través del parámetro String nombreFichero dado
        fichero=new BufferedWriter(new FileWriter(nombreFichero));
    }
    //MÉTODOS FUNCIONALES
    
    //MÉTODO escritura QUE LLEVA A CABO LA ESCRITURA DE UNA PALABRA EN EL
    //FICHERO DE TEXTO REPRESENTADO POR EL OBJETO BufferedWriter CORRESPONDIENTE
    public void escritura(Palabra palabra) throws Exception {
        //escritura de los caracteres de la palabra dada, caracter a caracter en
        //el fichero
        for (int indice=0;indice<palabra.getNumeroCaracteres();indice++) {
            //escritura en el fichero del caracter indice-ésimo de la palabra
            //dada
            fichero.write(palabra.obtenerCaracter(indice));
        }
    }
    
    //MÉTODO escrituraSeparador QUE LLEVA A CABO LA ESCRITURA DEL CÓDIGO
    //DE CARACTER DEL ESPACIO EN BLANCO EN EL FICHERO
    public void escrituraSeparador() throws Exception {
        //escritura del código del espacio en blanco en el fichero
        fichero.write(COD_ESPACIO);
    }
    //MÉTODO cerrarEnlaceFichero QUE LLEVA A CABO EL CIERRE DEL ENLACE BufferedWriter
    //con el fichero 
    public void cerrarEnlaceFichero() throws Exception {
        fichero.close();
    }
}
