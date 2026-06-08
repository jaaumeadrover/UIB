/*
 Classe Node que identifica els objectes de tipus referanciaEstdudiant i rederenciaAssignatura
 */
package Llista_Ref;

/**
AUTOR: Joan Balaguer, Jaume Adrover
DATA CREACIÓ: 12/11/2021
 */

public class Node_Ref {
    private Object obj;
    private Node_Ref seg;

    public Node_Ref(Object obj, Node_Ref seg) {
        this.obj = obj;
        this.seg = seg;
    }
    /*
    GETTERS
    */
    public Object getObj() {
        return obj;
    }

    public Node_Ref getNodeSeg() {
        return seg;
    }
    
    /*
    SETTERS
    */   
    public void setNodeSeg(Node_Ref x){
        seg=x;
    }
    
    public void setObj(Object o){
        obj =o;
    }
    
    
}
