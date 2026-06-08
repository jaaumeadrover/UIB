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
public class Tablero {

    int[][] matriu;

    public Tablero(int n) {
        matriu = new int[n][n];
        for (int i = 0; i < matriu.length; i++) {
            for (int j = 0; j < matriu.length; j++) {
                matriu[i][j] = 0;
            }

        }
    }
    public void setCasella(int value, int x,int y){
        matriu[x][y]=value;
    }

}
