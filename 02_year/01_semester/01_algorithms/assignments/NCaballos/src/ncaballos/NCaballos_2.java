/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ncaballos;

/**
 *
 * @author Jaume A
 */
public class NCaballos_2 {
    static Tablero []taulers;
    static int victories = 0;
    static int m = 1;
    static int posX;
    static int posY;

    public static void main(String[] args) {
        int n = 5;
        
        Tablero t=new Tablero(n);
        //int[][] tablero = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
//                tablero[i][j] = 0;
                  t.setCasella(0, i, j);
            }
        }

        //ponemos pos inicial a 1
        int cx = 0;
        int cy = 0;
        int movimientos = 1;

//        tablero[cx][cy] = 1;
          t.setCasella(1, cx, cy);
        for (int i = 0; i < 3; i++) {
            cx+=i;
            if (solucion(tablero, n, movimientos, cx, cy)) {
                mostrarTablero(tablero, n);
            }

        }
        //mostrarTablero(tablero,n);
    }

    public static boolean es_posible(int[][] tablero, int cx, int cy, int n) {

        //Si esta en el eje x
        if (cx < 0 || cx >= n) {
            return false;
        }
        //Si esta en el eje y
        if (cy < 0 || cy >= n) {
            return false;
        }
        //Si la casilla esta desocupada
        return (tablero[cx][cy] == 0);
    }

    public static boolean solucion(int[][] tablero, int n, int caballos, int cx, int cy) {
        if (caballos == n * n) {
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
                tablero[ncx][ncy] = caballos + 1;
                //System.out.println(tablero[ncx][ncy]);
                if (solucion(tablero, n, caballos + 1, ncx, ncy)) {
                    return true;
                }
                tablero[ncx][ncy] = 0;
            }

        }

        return false;
    }

    public static void mostrarTablero(int[][] tablero, int n) {

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(tablero[i][j] + " ");
            }
            System.out.println();
        }
    }

}