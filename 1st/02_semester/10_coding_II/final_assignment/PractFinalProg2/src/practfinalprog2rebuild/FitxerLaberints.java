/*
Classe que implementa les funcionalitats necessaries per poder lletgir els continguts del fitxers
de l'enunciat
 */
package practfinalprog2rebuild;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.Integer.parseInt;

/**
 *
 * @author Jaume A
 */
public class FitxerLaberints {

    //atributs de la classe
    private BufferedReader lector;
    private int[] paret;
    private int files, columnes;

    /**
     * Mètode constructor que inicialitza el lector BufferedReader amb un
     * FileReader, on li passam per paràmetre el fitxer des d'on volem lletgir
     * l'informació
     *
     * @param fitxer
     * @throws FileNotFoundException
     */
    public FitxerLaberints(String fitxer) throws FileNotFoundException {

        lector = new BufferedReader(new FileReader(fitxer));

    }

    /**
     * Mètode per lletgir les dues primeres lineas del fitxer on hi trobarem el
     * nombre de files i columnes del laberint i també per lletgir les dues
     * ultimes files del fitxer, on hi trobarem la posició de la sortida
     *
     * @throws IOException
     */
    public void LlegirDimensio() throws IOException {
        //lletgim el nombre de files
        files = parseInt(lector.readLine());
        //lletguim el nombre de columnes
        columnes = parseInt(lector.readLine());

    }

    /**
     * Mètode amb el qual lletgim 4 nombres del fitxer, que corresponen als
     * digits que ens indicarán si a una casella determinada existeix una paret
     * o no.
     *
     * @return
     * @throws IOException
     */
    public int[] LlegirParets() throws IOException {
        //definim un array de 4 components enteres per posteriorment, retornar-lo
        int parets[] = {0, 0, 0, 0};
        //lletgim 4 enters seguits i els introduim dins l'array que retornarem
        for (int i = 0; i < parets.length; i++) {
            parets[i] = lector.read();
        }
        return parets;
    }

    /**
     * Mètode per lletgir un sol niombre d'una linea del fitxer
     *
     * @return
     * @throws IOException
     */
    public int llegir() throws IOException {
        return lector.read();
    }

    /**
     * Mètode amb el qual lletgim una linea sencera del fitxer (ho farem servir
     * principalment per saltar de línea)
     *
     * @return
     * @throws IOException
     */
    public String llegirLinea() throws IOException {

        return lector.readLine();
    }

    /**
     * Mètode que retorna el nombre de files lletgides al principi del fitxer
     *
     * @return
     */
    public int getFiles() {
        return files;
    }

    /**
     * Mètode que retorna el nombre de columnes lletgides al principi del fitxer
     *
     * @return
     */
    public int getColumnes() {
        return columnes;
    }
}
