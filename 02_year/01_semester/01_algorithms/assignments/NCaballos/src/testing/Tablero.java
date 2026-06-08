/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

/**
 *
 * @author Jaume A
 */
public class Tablero {

    int[][] matriu;

    public Tablero(int n) {
        matriu = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matriu[i][j] = 0;
            }

        }
    }

    public void setCasella(int value, int x, int y) {
        matriu[x][y] = value;
    }

    public int getCasella(int x, int y) {
        return matriu[x][y];
    }
    public boolean esIgual(Tablero t){
        for (int i = 0; i < matriu.length; i++) {
            for (int j = 0; j < matriu.length; j++) {
                if(matriu[i][j]!=t.getCasella(i, j)){
                    return false;
                }
            }
            
        }
        return true;
    }

    public void reset() {
        for (int i = 0; i < matriu.length; i++) {
            for (int j = 0; j < matriu.length; j++) {
                matriu[i][j] = 0;
            }
        }
    }

}
