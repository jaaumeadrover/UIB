/*
CLASSE Pesa.
FUNCIONALITAT: implementa la imatge de la peça a ser utilitzada.
 */
package GUI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * AUTOR: Joan Balaguer, Jaume Adrover
 * DATA CREACIÓ: 18/12/2021
 */
public class Pesa {
    //ATRIBUTS DE CLASSE
    private final String REINA = "resources/images/reinaN.png";
    BufferedImage img;
    
    /*
    Constructor que rep per paràmetre la peça a ser introduïda, en el nostre cas,
    només utilitzarem reines.
    */
    public Pesa(String s) {
        try {
            img = ImageIO.read(new File(REINA));
        } catch (IOException ex) {
            System.out.println("ERROR I/O: " + ex);
        }
    }
    /*
    Retornam la imatge associada a cada objecte, per tal d'esser dibuixada.
    */
    public BufferedImage getImatge() {
        return img;
    }
}
