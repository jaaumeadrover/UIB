/*
CLASSE NCaballos.
FUNCIONALITAT: 
 */
package NCaballos;

import GUI.Tauler;

/**
 * AUTOR: Joan Balaguer, Jaume Adrover.
 * DATA CREACIÓ: 18/12/2021
 */
public class NCaballos {

    static Tauler[] taulers;
    static Tauler tauler;
    static int victories = 0;
    static int m = 1;
    static int posX;
    static int posY;

    public NCaballos(Tauler t) {
        tauler = t;
    }

    public void resol(int cxInicial, int cyInicial) {
        int n = tauler.getTamany();
        //inicialitzam l'array de possibles solucions
        initTaulers(n);
        //Assignam coords inicials al cavall
        int cx = cxInicial;
        int cy = cyInicial;
        int moviments = 1;
        for (int i = 0; i < 3; i++) {
            tauler = new Tauler(n);
            tauler.setCasellaTo(cx, cy, true, 1);
            if (solucion(tauler, n, moviments, cx, cy)) {
                taulers[victories] = tauler;
                //mostrarTauler
                victories++;
            }

        }

    }

    private void initTaulers(int dimensio) {
        taulers = new Tauler[3];
        for (int i = 0; i < taulers.length; i++) {
            taulers[i] = new Tauler(dimensio);
        }
    }

    public boolean te3Solucions() {
        return victories == 3;
    }

    public int getSolucions() {
        return victories;
    }

    public Tauler getTauler(int i) {
        return taulers[i];
    }
    public void setSolucions(int x){
        victories=x;
    }
    public static boolean es_posible(Tauler tablero, int cx, int cy, int n) {

        //Si esta en el eje x
        if (cx < 0 || cx >= n) {
            return false;
        }
        //Si esta en el eje y
        if (cy < 0 || cy >= n) {
            return false;
        }
        //Si la casilla esta desocupada
        //return (tablero.getCasella(cx, cy) == 0);
        return !(tauler.getCasella(cx, cy).isTransitada());
    }

    public static boolean solucion(Tauler tablero, int n, int caballos, int cx, int cy) {
        if ((caballos == n * n) && (solucioNoTrobada(tablero))) {
            System.out.println("He trobat una solucio diferent!");
            return true;
        }

        int ncx;
        int ncy;
        int despX[] = {-2, -2, +2, +2, +1, -1, +1, -1};
        int despY[] = {+1, -1, -1, +1, +2, +2, -2, -2};

        for (int i = 0; i < despX.length; i++) {
            ncx = cx + despX[i];
            ncy = cy + despY[i];
            if (es_posible(tablero, ncx, ncy, n)) {
                tablero.setCasellaTo(ncx, ncy, true, caballos + 1);
                if (solucion(tablero, n, caballos + 1, ncx, ncy)) {
                    taulers[victories] = tablero;
                    return true;
                }
                tablero.setCasellaTo(ncx, ncy, false, 0);
            }

        }
        return false;
    }

    public static boolean solucioNoTrobada(Tauler t) {
        for (int i = 0; i < 3; i++) {
            if (t.esIgual(taulers[i])) {
                return false;
            }
        }
        return true;
    }


}
