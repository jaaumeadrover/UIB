/*
FUNCIONALIDAD: Dado un texto introducido por teclado, busca en el texto las palabras que
no tienen ningún carácter igual con la primera palabra del mismo. Las palabras encontradas
deberán ser grabadas, en la misma línea separadas con el carácter espacio
,en un fichero de texto de nombre salida.txt.
 */
package examenfinal;

/**
AUTORÍA: Jaume Adrover Fernández
FECHA CREACIÓN: 02/02/2021
 */
public class EjercicioPalabras1 {

    public static void main(String[] args)throws Exception {
        new EjercicioPalabras1().programaPrincipal();
    }
    public void programaPrincipal()throws Exception{
        //DECLARACIONES OBJETOS
        Palabra primerapalabra=new Palabra();
        Palabra pal=new Palabra();
        PalabraFicherosOut ficherosalida=new PalabraFicherosOut("salida.txt");
        
        //LLAMADA A LOS MÉTODOS/SUBPROGRAMAS
        SolicitarSecuencia();
        Tratamiento(pal,primerapalabra,ficherosalida);
        
        
    }
    
    //MÉTODO QUE SOLICITA AL USUARIO QUE ESCRIBA UNA SECUENCIA DE TEXTO
    public void SolicitarSecuencia(){
        System.out.print("Introducir secuencia de texto: ");
    }
    
    //MÉTODO CON EL CUAL LEEMOS LA SECUENCIA Y EXRIBIMOS EN EL FICHERO DE SALIDA
    public void Tratamiento(Palabra pal,Palabra primerapalabra,PalabraFicherosOut ficherosalida)throws Exception{
        
        //LECTURA PRIMERA PALABRA
        if(Palabra.hayPalabras()){
        pal.lectura();
        pal.copiar(primerapalabra);
        }
        //BUCLE DE TRATAMIENTO
        while(Palabra.hayPalabras()){
            pal.lectura();
            //CONDICIÓN DE ESCRITURA EN EL FICHERO
            if(pal.EsDiferente(primerapalabra)){
                ficherosalida.escritura(pal);
                ficherosalida.escrituraSeparador();
            }
        }
        //CIERRE DEL ENLACE DE FICHERO
        ficherosalida.cerrarEnlaceFichero();
    }
    
   
    
}
