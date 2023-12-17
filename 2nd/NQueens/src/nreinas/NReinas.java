/*
CLASSE NReines.
FUNCIONALITAT: implementar l'algoritme de backtracking per al qual es col.loquen
N reines dins un taulell NxN.
 */
package nreinas;

import GUI.Pesa;
import GUI.Tauler;

/**
 * AUTOR: Joan Balaguer, Jaume Adrover
 * DATA CREACIÓ: 18/12/2021
 */
public class NReinas {
    //ATRIBUTS DE CLASSE
    private static Tauler taulell;
    private int nQInici;//NOMBRE de reines inicial
    
    //Constructor, que rep un taulell i el nombre de reines inicial per paràmetre
    public NReinas(Tauler t, int n) {
        taulell = t;
        nQInici = n;
    }
    /*
    Mètode que comprova si hi ha solució en el taulell.
    */
    public boolean teSolucio() {
        int n = taulell.getTamany();
        return solucion(taulell, n, nQInici);
    }
    /*
    Mètode per a verificar si cap reina es toca entre elles i realitzar poda
    */
    public static boolean es_posible(Tauler t, int fila, int columna, int n) {
        int i, j;

        //Línea && Columna
        for (i = 0; i < n; i++) {
            if (t.getCasella(i, columna).isOcupada()) {
                return false;
            }
            if (t.getCasella(fila, i).isOcupada()) {
                return false;
            }

        }

        //Diagonal inferior izq
        for (i = fila, j = columna; (j >= 0) && (i < n); i++, j--) {
            if (t.getCasella(i, j).isOcupada()) {
                return false;
            }
        }
        //Diagonal superior izq
        for (i = fila, j = columna; (i >= 0) && (j >= 0); i--, j--) {
            if (t.getCasella(i, j).isOcupada()) {
                return false;
            }
        }
        //Diagonal inferior derecha
        for (i = fila, j = columna; (i < n) && (j < n); i++, j++) {
            if (t.getCasella(i, j).isOcupada()) {
                return false;
            }
        }
        //Diagonal superior derecha
        for (i = fila, j = columna; (i >= 0) && (j < n); i--, j++) {
            if (t.getCasella(i, j).isOcupada()) {
                return false;
            }
        }
        //Si la casella està desocupada
        return true;
    }
    /*
    Mètode recursiu que ens retorna la soució, amb el taulell canviat.
    */
    private static boolean solucion(Tauler t, int n, int nreines) {
        if (nreines == n) {
            return true;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (es_posible(t, i, j, n)) {
                    //Col·locam una reina
                    t.setCasellaTo(i, j, true);
                    t.getCasella(i, j).setPesa(new Pesa("reina"));
                    //Miram si és una possible solució
                    if (solucion(t, n, nreines + 1)) {
                        return true;//
                    }
                    t.setCasellaTo(i, j, false);
                }

            }

        }

        return false;
    }
}
