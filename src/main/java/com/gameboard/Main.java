package com.gameboard;

public class Main{

	//public Pion[] pions;

	public static void main(String[] args){
		Plateau p=new Plateau();
		boolean[][][] plateau = p.getPlateau();

		System.out.println();
		for(int i=0; i<16; i++){
			for(int j=0; j<16; j++){
				if(plateau[i][j][0]) {
					if(plateau[i][j][1]){
						System.out.print("͞ |");//0
					}
					else if(plateau[i][j][3]){
						System.out.print("|͞ ");//3
					}
					else{
						System.out.print("͞ ͞ ");
					}
				}
				else if(plateau[i][j][1]) {
					if(plateau[i][j][2]) {
						System.out.print("_|");//1
					}
					else{
						System.out.print(" |");
					}
				}
				else if(plateau[i][j][2]) {
					if(plateau[i][j][3]) {
						System.out.print("|_");
					}
					else {
						System.out.print("__");
					}
				}
				else if(plateau[i][j][3]) {
					System.out.print("| ");
				}
				else{
					System.out.print("##");
				}
			}
			System.out.println();
		}

		System.out.println("\n##################################");
		for(int i=0; i<16; i++) {
			System.out.print("#");
			for (int j = 0; j < 16; j++) {
				if (plateau[i][j][1]) {
					if(plateau[i][j][2]){
						System.out.print("_|");
					}
					else{
						System.out.print(" |");
					}
				}
				else if (plateau[i][j][2]){
					System.out.print("__");
				}
				else {
					System.out.print("  ");
				}
			}
			System.out.println("#");
		}
		System.out.println("##################################");
	}
}

