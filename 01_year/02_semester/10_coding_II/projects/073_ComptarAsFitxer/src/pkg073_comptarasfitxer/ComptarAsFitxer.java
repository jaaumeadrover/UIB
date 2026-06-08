/*
 * Exemple de recorregut dins un fitxer de text
 * Usa el fluxe (stream) de caràcters FileReader amb una variable de tipus enter
 * per llegir els caracters del fixer en format Unicode
 * El codi que marca el final del fluxe (fi de fitxer) és el -1
 * Per cada element llegit s'ha de convertir a caràcter fent el casting de tipus
 * Quan és crida el constructor s'obri el fitxer
 * Al final del programa s'ha de tancar amb l'operació close
 * Per que es pugui trobar el fitxer prova.txt ha d'estar dins la carpeta del
 * projecte
 */
package pkg073_comptarasfitxer;

import java.io.*;

public class ComptarAsFitxer {

    public static void main(String[] args) {
        try {
            FileReader f = new FileReader("prova.txt");
            char c;
            int x;
            int n = 0;
            x = f.read();
            while (x != -1) {
                c = (char) x;
                if (c == 'a') {
                    n++;
                }
                x = f.read();
            }
            f.close();
            System.out.println("Hi havia un total de " + n + " lletres 'a'");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
