/*
FUNCIONALIDAD: Clase Tela.
 */
package examenfinal;

import java.util.Random;

/**
AUTORÍA: Jaume Adrover Fernández
FECHA CREACIÓN: 02/02/2021
 */
//ENUMERADO PARA REPRESENTAR COLORES DE LAS TELAS
enum Color{ROJO,AMARILLO,VERDE,AZUL}

public class Tela {
    //DECLARACIONES DE ATRIBUTOS
    
    private final int NumeroCaracteres=10;
    //ATRIBUTO CLASE CON LA LONGITUD POR DEFECTO
    private final static int LongitudDefault=500;
    //ATRIBUTO DE OBJETO LONGITUD
    private int longitud;
    //ATRIBUTO CON CODIGO DE COLOR
    private char [] codigocolor=new char[NumeroCaracteres];
    //ATRIBUTO DE ENUM COLOR
    private Color color;
    
    
    
    
    
    //MÉTODO CONSTRUCTOR
    public Tela(){
        longitud=LongitudDefault;
        GeneracionCodigoColor();
    }
    
    //MÉTODOS FUNCIONALES
    
    //MÉTODO EN EL CUAL GENERAMOS ALEATORIAMENTE UN CODIGO DE COLOR
    private void GeneracionCodigoColor(){
        char abecedario[]="abcdefghijklmniopqrstuvwxyz1234".toCharArray();
        Random generador=new Random();
        
        //GENERAMOS UN PRIMERO DÍGITO NUMERICO PARA DISTINGIR EL COLOR DE LA TELA
        codigocolor[0]=abecedario[(abecedario.length-generador.nextInt(5))];
        //GENERAMOS LOS OTROS DÍGITOS ALFANÚMERICOS
        for(int i=1;i<NumeroCaracteres;i++){
            codigocolor[i]=abecedario[generador.nextInt(NumeroCaracteres)+1];
        }
        
    }
    //MÉTODO PÚBLICO PARA OBTENER 
    public int getLongitud(){
        return longitud;
    }
    //MÉTODO PARA OBTENER EL ATRIBUTO PRIVATE CODIGOCOLOR
    public char[] getCodigoColor(){
        return codigocolor;
    }
    
    //MÉTODO CORTA QUE RESTA METROS A LA LONGITUD DE LA TELA
    public void corta(int x){
        if(x>longitud){
            System.out.print("No tienes tela suficiente para realizar este corte.");
        }else{
            longitud-=x;
        }
        
    }
    
    //MÉTODO EQUALS QUE NOS INDICA SI DOS TELAS SON IGUALES
    public boolean equals(Tela a){
        return(codigocolor==a.getCodigoColor());
    }
    
}
