
package pràcticafinaljaumejoan;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;


public class PracticaFinal {
    //DECLARACIONES GLOBALES
    
        final int FINAL_FICHERO = -1;
        final int RETURN= (int) '\r';
        final int SALTO_LINEA= (int) '\n';
        String nombreFichero;
        int codigo=0, semilla=5;
        
        
        //DECLARACIÓN MÉTODO MAIN
    public static void main( String args[] ) throws Exception {
        new PracticaFinal().programaPrincipal();
        
    }
    
    
    public void programaPrincipal() throws Exception {
        
        boolean fin=false;
        
        //Elegir fichero para analizar
        System.out.print("Introducir nombre del fichero a analizar: ");
        nombreFichero=LT.readLine();
        
        
        //Objetos para el analisis
        PalabraFicherosIn fichero1;
        LineasFicherosIn fichero2;
        fichero1 = new PalabraFicherosIn(nombreFichero);
        fichero2 = new LineasFicherosIn(nombreFichero);
        
        //MÉTODOS A USAR
       
        //CONDICIÓN NÚMERO PALABRAS EN EL FICHERO
        if(fichero1.GetNumeroPalabras()<500){
        
            //NÚMERO CARACTERES
        System.out.println("El número de caracteres del fichero es de: "+ GetNumeroCaracteres());
            //NÚMERO PALABRAS
        System.out.println("El número de palabras del fichero es de: "+ fichero1.GetNumeroPalabras());
        
            //NÚMERO LÍNEAS
        System.out.println("El número de líneas del fichero es de: "+ (fichero2.GetNumeroLineas(nombreFichero)));
        
        
         
        //Menu Principal
        
        while (!fin) {
            
            System.out.println("\n                     OPCIONES DE ANÁLISIS ");
            System.out.println("       (1) MOSTRAR LA LETRA MÁS REPETIDA Y SU NUMERO DE APARICIONES.");
            System.out.println("       (2) MUSTRA EL NUMERO DE APARICIONES DE CADA CARACTER.");
            System.out.println("       (3) MUESTRA LA PALABRA MÁS REPETIDA Y SU NÚMERO DE APARICIONES.");
            System.out.println("       (4) BUSCA UNA PALABRA.");
            System.out.println("       (5) BUSCA UN TEXTO.");
            System.out.println("       (6) BUSCA LAS PALABRAS REPETIDAS.");
            System.out.println("       (7) CODIFICA FICHERO.");
            System.out.println("       (8) ESTABLECE LA SEMILLA DE DECODIFICACIÓN PARA DECODIFICAR EL FICHERO.");
            System.out.println("       (0) SALIR.");
            System.out.println("\n  ");
            System.out.print("         ESCOGER OPCIÓN --> ");
            
            char car = LT.readChar();
            
            switch (car) {
                
                case '1':       
                    borrarPantalla();
                    ObtenerLetraMasRepetida();                   
                    break;
                    
                case '2':  
                    borrarPantalla();
                    ObtenerFrecuenciaChar();                    
                    break;
                    
                case '3':  
                    borrarPantalla();
                    ObtenerPalabrasMasRepetidas();                  
                    break;
                    
                case '4':      
                    borrarPantalla();
                    BuscarPalabra();                  
                    break;
                    
                case '5':    
                    borrarPantalla();
                    BuscarTexto();                          
                    break;
                    
                case '6':     
                    borrarPantalla();
                    BuscarPalabrasRepetidas();
                    break;
                    
                case '7':     
                    borrarPantalla();
                    System.out.println("La semilla actual es: "+semilla);
                    System.out.print("INTRODUCIR NUEVA SEMILLA DE GENERACIÓN ALEATORIA: ");
                    semilla=LT.readInt();
                    
                    CodificarFichero(semilla,codigo,nombreFichero);                    
                    break;
                    
                case '8':     
                    borrarPantalla();
                    System.out.println("La semilla actual es: "+semilla);
                    System.out.print("INTRODUCIR NUEVA SEMILLA DE DECODIFICACIÓN: ");
                    semilla=LT.readInt();
                    DecodificarFichero(semilla, codigo,nombreFichero);             
                    break;
                case '0':
                    System.out.println("Gracias por utilizar nuestro procesador de textos. Esperamos que tengas un buen dia.");
                    fin=true;
                    break;
                default: 
                    System.out.println("Has introducido una opción incorrecta. Vuelve más tarde.");
                    fin=true;
            }
        }
    } else {
            System.out.print("El fichero es demasiado grande para ser procesado");
        }
    }
    
  //MÉTODO PARA BORRAR PANTALLA CUANDO SELECCIONAMOS UNA OPCIÓN DEL MENÚ
    public static void borrarPantalla() {       
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n"
                + "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }
    //MÉTODO PARA OBTENER NUMERO DE CARACTERES AL PRINCIPIO DEL ANÁLISIS DEL FICHE
    public int GetNumeroCaracteres() throws Exception {
        //DECLARACIONES
        int numeroCaracteres=0;
        BufferedReader fichero;   
        fichero=new BufferedReader(new FileReader(nombreFichero));
        
        //LECTURA PRIMER CARACTER
        codigo=fichero.read();
        
        //BUCLE DE TRATAMIENTO/LECTURA
        while(codigo!=-1){
            numeroCaracteres++;
            codigo=fichero.read();
        }    
        
        fichero.close();
        
        return numeroCaracteres;
}
    //MÉTODO QUE NOS MUESTRA LA LETRA MÁS REPETIDA Y SU NUMERO DE APARICIONES
    public void ObtenerLetraMasRepetida() throws Exception{
         //DECLARACIÓN OBJETOS/VARIABLES
         FrecuenciaCaracteres frecuencia= new FrecuenciaCaracteres();
         BufferedReader fichero=new BufferedReader(new FileReader(nombreFichero));
         int codigo=' ';
         
         //BUCLE DE TRATAMIENTO
         while(codigo!=FINAL_FICHERO){
            
             codigo= fichero.read();
             
            frecuencia.actualizacion((char) codigo);
            
        }
         //CIERRE ENLACE FICHERO
         fichero.close();
         
         //VISUALIZACION LETRA MÁS REPETIDA Y SUS APARICIONES
         System.out.println("La letra más repetida és: "+frecuencia.LetraMasRepetida()+
                            "-------> frecuencia"+ frecuencia.obtenerFrecuencia(frecuencia.LetraMasRepetida()));
}
    
    //MÉTODO OBTENER FRECUENCIA QUE NOS PERMITE HACER UN SYSTEM.OUT.PRINT DE LAS
    //APARICIONES DE TODOS LOS CARACTERES.
    public void ObtenerFrecuenciaChar()throws Exception{
        //DECLARACIÓN/INICIALIZACIÓN DE OBJETOS
        FrecuenciaCaracteres frecuencia=new FrecuenciaCaracteres();
        BufferedReader fichero;
        fichero=new BufferedReader(new FileReader(nombreFichero));
 
        //DECLARACION DE LA VARIABLE ENTERA CODIGO,
        //INICIALIZADA CON ESPACIO EN BLANCO PARA ENTRAR EN EL BUCLE
        //DE TRATAMIENTO.
        int codigo=' ';
        
        //BUCLE DE TRATAMIENTO
        while (codigo!=FINAL_FICHERO) {
            //LECTURA CARACTER
            codigo=fichero.read();
            //ACTUALIZACION DEL ARRAY FRECUENCIAS MEDIANTE EL MÉTODO DE CLASE
            frecuencia.actualizacion((char) codigo);
           
        }
        //VISUALIZACIÓN DE LA FRECUENCIA
        System.out.print(frecuencia.toString()); 
       
       //CIERRE ENLACE FICHERO
       fichero.close();
   
   
    }
    
    //MÉTODO OBTENER PALABRAS MÁS REPETIDAS QUE CONSISTE EN HACER UNA LECTURA DEL FICHERO Y CLASIFICAR
    //LAS PALABRAS SEGÚN SU NUMERO DE APARICIONES, SELECCIONANDO LAS QUE TIENEN UNA FRECUENCIA MAYOR.
    //EN CASO DE EMPATE SE MUESTRAN TODAS
    public void ObtenerPalabrasMasRepetidas()throws Exception{
        
        //DECLARACION/INICIALIZACION OBJETOS
        FrecuenciaPalabras frecuencia;
        frecuencia=new FrecuenciaPalabras();
        Palabra pal;
        PalabraFicherosIn fichero;
        fichero = new PalabraFicherosIn(nombreFichero);
        
        //BUCLE DE TRATAMIENTI
        while(fichero.hayPalabras()){
            pal=fichero.lectura();
            frecuencia.actualizacion(pal);
        }
        
        //VISUALIZACIÓN PALABRA MÁS REPETDIAS
        System.out.println("Palabras más repetidas: ");
        
        
        //PALABRA MÁS REPETIDA
        System.out.println("Palabra : "+frecuencia.getPalabra(frecuencia.IndicePalabraMásRepetida())
                   +"------> frecuencia "+frecuencia.getFrecuencia(frecuencia.IndicePalabraMásRepetida()));
           
        //CASO EMPATE,PALABRAS REPETIDAS: realizaremos un bucle donde se comparen
        //frecuencias con tal de visualizar el posible caso de empate
        
        //BUCLE FOR DE TRATAMIENTO
        for (int i=0;i<frecuencia.getNumeroPalabras();i++){
            //CONDICION PARA VISUALIZAR LAS PALABRAS
            if((frecuencia.getFrecuencia(i))==(frecuencia.getFrecuencia
        (frecuencia.IndicePalabraMásRepetida()))
                    &&(i!=frecuencia.IndicePalabraMásRepetida())){
                //VISUALIZACIÓN DE PALABRA Y SU RESPECTIVA FRECUENCIA
                System.out.println("Palabra: "+frecuencia.getPalabra(i)+" ------> frecuencia "+frecuencia.getFrecuencia(i));
            }
        }
            
        //CIERRE ENLACES FICHEROS                
        fichero.cerrarEnlaceFichero();
        
        
    }    
    
    //MÉTODO CODIFICAR FICHERO QUE RECIBE POR PARAMETROS UNA SEMILLA, EL CODIGO DEL CARACTER A SER LEIDO
    //Y EL NOMBRE DE FICHERO A SER CODIFICADO
     public void CodificarFichero(int semilla,int codigo,String nombrefichero)throws Exception{
         //DECLARACION OBJETOS
        CodificacionAlfabetica ficherocod;
        BufferedReader fichero1;
        BufferedWriter fichero2;
        
        //INICIALIZACION DE OBJETOS MEIDANTE MÉTODOS CONSTRUCTORES
        fichero1=new BufferedReader(new FileReader(nombrefichero));
        fichero2=new BufferedWriter(new FileWriter(nombrefichero+".cod.txt"));
        ficherocod=new CodificacionAlfabetica(semilla);
                    
                    //LECTURA PRIMER  CARACTER
                    codigo=fichero1.read();
                    //BUCLE DE TRATAMIENTO
                      while(codigo!=FINAL_FICHERO){
                        //ESCRITURA CARACTER CODIFICADO
                        fichero2.write(ficherocod.codificar((char) codigo));
                        //LECTURA SIGUIENTE CARACTER A CODIFICAR   
                        codigo=fichero1.read();
                      } 
                     //CIERRE ENLACES FICHEROS
                     fichero1.close();
                     fichero2.close();
    }
     
     //MÉTODO DECODIFICADOR FICHERO QUE RECIBE POR PARAMETROS UNA SEMILLA, EL CODIGO DEL CARACTER A LEER
     //Y EL NOMBRE DE FICHERO A DECODIFICAR
     public void DecodificarFichero(int semilla,int codigo,String nombrefichero)throws Exception{
         //DECLARACION OBJETOS
        CodificacionAlfabetica ficherodecod;
        BufferedReader ficherojoan;
        BufferedWriter fichero2;
        //INICIALIZACION DE OBJETOS MEIDANTE MÉTODOS CONSTRUCTORES
        ficherojoan=new BufferedReader(new FileReader(nombrefichero));
        fichero2=new BufferedWriter(new FileWriter(GetFicheroDecod(nombrefichero)+".txt"));
        ficherodecod=new CodificacionAlfabetica(semilla);
        
        
         //LECTURA DEL PRIMER CARACTER DEL FICHERO PARA ENTRAR EN EL BUCLE
         codigo=ficherojoan.read();
         
                      //BUCLE DE TRATAMIENTO QUE SIGUE OPERATIVO HASTA QUE SE ALCANCE
                      //EL FINAL DE FICHERO
                      while(codigo!=FINAL_FICHERO){
                        //CONVERSIÓN DE CÓDIGO A CARACTER
                        char caracter=(char) codigo;
                        //VISUALIZACION DEL PROCESO DE DECODIFICACION
                        System.out.print(caracter+"--->");
                        //DECODIFICAMOS EL CARACTER LEIDO
                        caracter=ficherodecod.deCodificar((char)codigo);
                        
                        //VISUALIZACIÓN DEL CARACTER DECODIFICADO
                        System.out.println(caracter);
                        
                        //ESCRITURA EN EL FICHERO DECODIFCIADO
                        fichero2.write(caracter);
                        //LECTURA DEL SIGUIENTE CARACTER
                        codigo=ficherojoan.read();
                      } 
                
                //CIERRE ENLACES FICHEROS
                ficherojoan.close();
                fichero2.close();
    }
     //MÉTODO STRING GETFICHERODECOD QUE NOS PERMITE OBTENER EL NOMBRE DEL FICHERO
     //EN EL CUAL INTRODUCIREMOS EL CONTENIDO DECODIFICADO
     public String GetFicheroDecod(String nombrefichero){
         //DECLARACIÓN VARIABLE TEMPORAL PARA CONCATENAR CARACTERES
        String tmp="";
        //DECLARACIÓN ARRAY DE LETRAS
        char letras[]= new char[nombrefichero.length()];
        
        //BUCLE DE TRATAMIENTO
        for(int i=0;nombrefichero.charAt(i)!='.';i++){
            tmp+=nombrefichero.charAt(i);
        }
        //DEVOLUCIÓN DEL STRING
        return tmp;
     }
     
     //MÉTODO VOID BUSCARPALABRA 
     public void BuscarPalabra() throws Exception {
       //DECLARACION OBJETOS PALABRA
       PalabraFicherosIn fichero;
       
       //INICIALIZACION OBJETOS PALABRA
       Palabra palabra=new Palabra();
       Palabra palabraBuscada= new Palabra();
       
       //SOLICITAR AL USUARIO PALABRA A BUSCAR
       System.out.print("Introducir una palabra que quieras buscar:");
       
       //LECTURA PALABRA A SER BUSCADA
       while(Palabra.hayPalabras()){
           palabraBuscada.lectura();
       }
       
       //establecimiento enclace BufferedReader con el fichero
       fichero= new PalabraFicherosIn(nombreFichero);
       //VISUALIZACIÓN PALABRA
       System.out.println("La palabra "+ palabraBuscada.toString() +
               " se encuentra en: ");
       
       //BUCLE DE TRATAMIENTO LECTURA
       while (fichero.hayPalabras()){
           
           palabra=fichero.lectura();
           //CONDICIÓN PARA MOSTRAR PALABRAS
           if(palabra.iguales(palabraBuscada)){
               
               System.out.println("La linea "+ palabra.getLinea()+
                       " y en la columna "+ palabra.getColumna());
           }
       }
       //CIERRE ENLACES FICHERO
       fichero.cerrarEnlaceFichero();
   }
     
     //MÉTODO BUSCARPALABRASREPETIDAS QUE DEVUELVE PAREJAS DE PALABRAS QUE ESTAN 
     //JUNTAS
     public void BuscarPalabrasRepetidas()throws Exception{
         //DECLARACIONES
         int i=1;
         Palabra pal1=new Palabra();
         Palabra pal2;
         PalabraFicherosIn ficheroentrada;
     
         ficheroentrada=new PalabraFicherosIn(nombreFichero);
         pal2=ficheroentrada.lectura();
         
         //BUCLE DE TRATAMIENTO
          while(ficheroentrada.hayPalabras()){
            
            pal2.copiar(pal1);
            pal2=ficheroentrada.lectura();
            if(pal2.iguales(pal1)==true){
                System.out.println();
                System.out.println("Pareja "+i);
                Visualización(pal1,pal2);
                i++;
            }
          }
          
          //CIERRE ENLACES DE FICHERO
          ficheroentrada.cerrarEnlaceFichero();
         
         
     }
     
     
     //MÉTODO VISUALIZACIÓN QUE MUESTRA CADA PALABRA Y SU RESPECTIVA LÍNEA Y COLUMNA
     public void Visualización(Palabra pal1,Palabra pal2){
         System.out.println("Palabra: "+pal1.toString()+" --> línea: "+pal1.getLinea()+", columna: "+pal1.getColumna());
         System.out.println("Palabra: "+pal2.toString()+" --> línea: "+pal2.getLinea()+", columna: "+pal2.getColumna());
     }
     //MÉTODO BUSCAR TEXTO QUE NOS PERMITE ENCONTRAR UN TEXTO DADO POR TECLADO EN EL FICHERO
     public void BuscarTexto()throws Exception{
         int numerolineas=1;
         Linea linea,lineabuscada;
         LineasFicherosIn ficherolineas=new LineasFicherosIn(nombreFichero);
         
         linea=new Linea();
         lineabuscada=new Linea();
         
         //SOLICITAR TEXTO AL USUARIO
         System.out.print("Introduce texto a ser buscado:");
         
         //LECTURA TEXTO A BUSCAR
         if(Linea.hayLinea()){
             lineabuscada.lectura();
         }
         //BUCLE DE TRATAMIENTO
         while(ficherolineas.hayLineas()){
             linea=ficherolineas.lectura();
             if(linea.ContieneLinea(lineabuscada)){
                 VisualizarTextoBuscado(lineabuscada,numerolineas);
             }
             numerolineas++;
         }
         //CIERRE ENLACE
         ficherolineas.cierre();
         
         
     }
     //VISUALIZACIÓN DEL TEXTO BUSCADO
     public void VisualizarTextoBuscado(Linea linea,int i){
         System.out.println("El texto: "+linea.toString()+" se encuentra en la línea "+i);
     }
}
