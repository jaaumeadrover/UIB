/*
CLASSE BATX.
FUNCIONALITAT: classe filla de Curs. Pot ser PRIMER o SEGON.
 */
package Cursos;

/**
 *
 * @author Jaume A
 */
public class BATX extends Curs{
    CursBatx curs;
    
    public BATX(String s,String d,int n) {
        super(s,d);
        curs=CursBatx.values()[n];
    }
    
    public String getCurs(){
        return curs.toString();
    }

    @Override
    public String toString() {
        return "BATX{Nom=" + nom+", curs=" + curs + ", codi="+codi+",asignatures=\n"+asignAsociades+'}';
    }
    
}
