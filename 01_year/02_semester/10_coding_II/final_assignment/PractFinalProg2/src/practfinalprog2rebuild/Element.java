/*
Classe Element que decriu les característiques de les imatges Sortida i fitxa
 */
package practfinalprog2rebuild;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Element {

    //assignam un String a cada tipus d'element
    public static final String sortida = "Recursos/elements/exitImage.jpg";
    public static final String fitxa = "Recursos/elements/negra.png";
    //definim un buffer per poder lletgir les imatges
    BufferedImage img;

    /**
     * Mètode constructor que lletgeix una o altre imatge en funció de l'String
     * que li pasin per paràmetre
     *
     * @param s
     * @throws IOException
     */
    public Element(String s) throws IOException {
        //lletim una imatge op una altre depenent de l'String que en spassin per paràmetre
        if (s.equals(fitxa)) {
            img = ImageIO.read(new File("Recursos/elements/negra.png"));
        } else if (s.equals(sortida)) {
            img = ImageIO.read(new File("Recursos/elements/exitImage.jpg"));
        }
    }

    /**
     * Mètode que ens retorna la imatge de l'objecte element
     *
     * @return
     */
    public BufferedImage getImatge() {
        return img;
    }
}
