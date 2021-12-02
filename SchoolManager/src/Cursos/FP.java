/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cursos;

/**
 *
 * @author Jaume A
 */
public class FP extends Curs{
    Especialitat esp;
    
    public FP(String s,String d,int n) {
        super(s,d);
        esp=Especialitat.values()[n-1];
    }
    public String getESP(){
        return esp.toString();
    }
    @Override
    public String toString() {
        return "FP{Nom=" +nom+ ", especialitat=" + esp + ", codi="+codi+",assignatures=\n"+asignAsociades+'}';
    }
    
}
