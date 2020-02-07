package com.gameboard;

public class Main{

    public Pion pions[];

    public static void main(String args[]){
        Plateau p=new Plateau();
        boolean plateau[][][] = p.getPlateau();

        for(int i=0; i<16; i++){
            for(int j=0; j<16; j++){
                if(plateau[i][j][0])
                    System.out.print("- ");
                else if(plateau[i][j][1])
                    System.out.print(" |");
                else if(plateau[i][j][2])
                    System.out.print("_ ");
                else if(plateau[i][j][3])
                    System.out.print("| ");
                else System.out.print("##");
            }
            System.out.print("end\n");
        }
    }
}

