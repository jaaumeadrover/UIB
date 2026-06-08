/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preguntagamificacion_jaume_adrover;

/**
 *
 * @author Jaume A
 */
public class PreguntaGamificacion_Jaume_Adrover {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)throws Exception {
        new PreguntaGamificacion_Jaume_Adrover().ProgramaPrincipal();
    }
    public void ProgramaPrincipal()throws Exception{
        
        
        
        
        
        
        
        
        
        
    }
    //MÉTODO QUE DEVUELVE EL VALOR DE LA SUMA DE LOS CARACTERES VOCALES MINUSCULAS
    //Y N,S
    public void GetValorSuma(char[]x){
        int suma=0;
        char letrassumadas[]=new char[x.length];
        int contadorletrassumadas=0,y=0;
        
        for (int i=0;i<x.length;i++){
            if (esValido(x[i])){
                suma+=(int)x[i];
                letrassumadas[y]=x[i];
                contadorletrassumadas++;
            }
        }
        
        
    }
    //MÉTODO BOOLEAN QUE DEVUELVE EL VALOR TRUE SI LA LETRA A ALMACENAR
    //ES VÁLIDA
    public boolean esValido(char caracter){
        //ARRAY CHAR CON LAS LETRAS VALIDAS
        char letras[]="aeiouns".toCharArray();
        //TARTAMIENTO PARA VER SI CARACTER ES VALIDO
        for (int i=0;i<letras.length;i++){
            if(caracter==letras[i]){
                return true;
            }
        }
        return false;
    }
    
    //MÉTODO QUE DEVUELVE UN ARRAY CHAR EN MAYUSCULAS SUMANDOLE 32(CODIGO ASCII/UNICODE)
    //VARIABLES DADAS POR PARAMETRO SON EL ARRAY Y EL NUMERO DE LETRAS SUMADAS, QUE ES IGUAL A
    //AL LENGTH DEL ARRAY
    
    public char [] DevolverMayúsculas(char [] a,int contadorletrassumadas)throws Exception{
        char [] letrasmayusculas=new char[contadorletrassumadas];
        
        for (int i=0;i<contadorletrassumadas;i++){
            letrasmayusculas[i]=(char)(a[i]+32);
        }
        return letrasmayusculas;
    }
}
