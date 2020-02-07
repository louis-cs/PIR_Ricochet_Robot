package com.gameboard;

import java.util.Random;

public class Plateau {
    /*
    0 mur en haut
    1 mur à droite
    2 mur en bas
    3 mur à gauche
     */
    private boolean[][][] plateau = new boolean[16][16][4];
    private static int max=15;

    private Random random = new Random();

    public Plateau(){

        initPlateau();

        initMur();

        initCoins();

    }

    private void initPlateau(){
        for(int i=0; i<16; i++){
            for(int j=0; j<16; j++){
                for(int k=0; k<4; k++){
                    this.plateau[i][j][k]=false;
                }
            }
        }
    }

    private void initMur(){
        //HAUT
        int r=randMur();
        this.plateau[0][r-1][1]=true;
        this.plateau[0][r][3]=true;
        r=randMur();
        this.plateau[0][16-r-1][1]=true;
        this.plateau[0][16-r][3]=true;

        //DROITE
        r=randMur();
        this.plateau[r-1][max][2]=true;
        this.plateau[r][max][0]=true;
        r=randMur();
        this.plateau[16-r-1][max][2]=true;
        this.plateau[16-r][max][0]=true;

        //BAS
        r=randMur();
        this.plateau[max][r-1][1]=true;
        this.plateau[max][r][3]=true;
        r=randMur();
        this.plateau[max][16-r-1][1]=true;
        this.plateau[max][16-r][3]=true;

        //GAUCHE
        r=randMur();
        this.plateau[r-1][0][2]=true;
        this.plateau[r][0][0]=true;
        r=randMur();
        this.plateau[16-r-1][0][2]=true;
        this.plateau[16-r][0][0]=true;
    }

    private void initCoins(){

    }

    private int randMur() {
        return random.nextInt(5) + 2;
    }

    public boolean[][][] getPlateau(){
        return this.plateau;
    }
}
