/*
CLASE Palabra
 */
package examenfinal;


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
        while (caracter==ESPACIO) {
            //lectura siguiente caracter de la secuencia
            caracter=LT.readCharacterLine();
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
        
        //lectura y almacenamiento de los caracteres de la palabra leida desde
        //la secuencia de caracteres
        while ((caracter!=FINAL_SECUENCIA)&&(caracter!=ESPACIO)) {
            //almacenar el caracter de la palabra leido en la componente
            //correspondiente del atributo array caracteres del objeto Palabra
            caracteres[numeroCaracteres]=caracter;
            //inrementar el atributo numeroCaracteres del objeto Palabra en una
            //unidad porque acabamos de almacenar el array caracteres un caracter
            //de la palabra
            numeroCaracteres++;
            //lectura siguiente caracter de la secuencia
            caracter=LT.readCharacterLine();
        }  
    }

    public int getNumeroCaracteres() {
        return numeroCaracteres;
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
    //MÉTODO ES DIFERENTE QUE NOS PERMITE COMPROBAR SI UNA PALABRA ES DFERENTE TOTALMENTE
    //DE OTRA, SIN COINCIDIR NINGUNA LETRA
    public boolean EsDiferente(Palabra a){
        
        for (int i=0;i<numeroCaracteres;i++){

            for(int x=0;x<a.getNumeroCaracteres();x++){
                if (caracteres[i]==a.caracteres[x]){
                    return false;
                }
            }
        }
        
        
        return true;
    }
}