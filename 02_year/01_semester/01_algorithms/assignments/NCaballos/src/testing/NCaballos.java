/*
CLASSE NCaballos.
FUNCIONALITAT: 
 */
package testing;


/**
 * AUTOR: Joan Balaguer, Jaume Adrover. DATA CREACIÓ: 18/12/2021
 */
public class NCaballos {

    static Tablero[] taulers;
    static int victories = 0;
    static int m = 1;
    static int posX;
    static int posY;

    public static void main(String[] args) {
        int n = 5;

        Tablero tablero = new Tablero(n);
        taulers = new Tablero[3];
        //int[][] tablero = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
//                tablero[i][j] = 0;
                tablero.setCasella(0, i, j);
            }
        }

        //ponemos pos inicial a 1
        int cx = 0;
        int cy = 0;
        int movimientos = 1;
        //init taulers
        for (int i = 0; i < 3; i++) {
            taulers[i] = new Tablero(n);
        }

//        tablero.setCasella(1, cx, cy);
//        if (solucion(tablero, n, movimientos, cx, cy)) {
//            taulers[victories] = tablero;
//            mostrarTablero(taulers[victories], n);
//            victories++;
//            System.out.println("VICTORIES: " + victories);
//        }
        for (int i = 0; i < 3; i++) {
            tablero = new Tablero(n);
            tablero.setCasella(1, cx, cy);
            if (solucion(tablero, n, movimientos, cx, cy)) {
                taulers[victories] = tablero;
                mostrarTablero(taulers[victories], n);
                victories++;
            }
        }
        for (int i = 0; i < 3; i++) {
            System.out.println("TAULER: " + i);
            mostrarTablero(taulers[i], n);
            System.out.println("");
        }

    }

    public static boolean es_posible(Tablero tablero, int cx, int cy, int n) {

        //Si esta en el eje x
        if (cx < 0 || cx >= n) {
            return false;
        }
        //Si esta en el eje y
        if (cy < 0 || cy >= n) {
            return false;
        }
        //Si la casilla esta desocupada
        return (tablero.getCasella(cx, cy) == 0);
    }

    public static boolean solucion(Tablero tablero, int n, int caballos, int cx, int cy) {
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
                tablero.setCasella(caballos + 1, ncx, ncy);
                if (solucion(tablero, n, caballos + 1, ncx, ncy)) {
                    taulers[victories] = tablero;
                    return true;
                }
                tablero.setCasella(0, ncx, ncy);
            }

        }

        return false;
    }

    public static boolean solucioNoTrobada(Tablero t) {
        for (int i = 0; i < 3; i++) {
            if (t.esIgual(taulers[i])) {
                return false;
            }
        }
        return true;
    }

    public static void mostrarTablero(Tablero tablero, int n) {

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(tablero.getCasella(i, j) + " ");
            }
            System.out.println();
        }
    }

}
