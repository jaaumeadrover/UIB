/*
Classe Node que defineix les funcionalitats d'un objecte Node
 */
package Classes;

/**
 *
 * @author joanbalaguer
 */
public class Node {

    //declaració d'atributs
    private int info;
    private Node seg;

    //Creació d'un node buit
    public Node() {
        seg = null;
    }

    //Creació d'un node amb les dades que ens passin per paràmetre
    public Node(int dades) {

        info = dades;
        seg = null;
    }
    
    //Creació d'un node amb les dades que ens passin per paràmetre i el següent node
    public Node(int dades, Node n) {

        info = dades;
        seg = n;
    }

    //Mètode que utilitzarem per fer que el node que crida a aquest mètode apunti 
    //al node que ens passaràn per paràmetre
    public void setNodeSeg(Node apuntat) {
        
        seg = apuntat;
    }

    //Mètode que retorna el node al qual apunta l'objecte node que crida a aquest mètode
    public Node getNodeSeg() {
        
        return seg;
    }
    
    //Mètode que retorna la dada contenida en el node
    public int getInfo() {
        
        return info;
    }
    
    //mètode per poder modificar l'atribut info
    public void setInfo(int info){
        this.info = info;
    }
    
    //Mètode per poder copiar un node en un altre node
    public void copyNode(Node n){
        this.info = n.info;
        this.seg = n.seg;
    }
    
    //Mètode amb el qual passam un node a String
    @Override
    public String toString(){
        String acum = "";
        acum = "Info = "+info;
        return acum;
    }

}
