package com.gameboard;

import java.util.Random;

public class Plateau {
	/*
	0 mur en haut
	1 mur à droite
	2 mur en bas
	3 mur à gauche
	 */

	private static int max=16;
	private boolean[][][] plateau = new boolean[max][max][4];

	private Random random = new Random();

	public Plateau(){

		initPlateau();

		placeBorderWalls();

		placeCorners();

	}

	private boolean boundsCheck(int x, int y){
		return (x>=0 && x<max && y>=0 && y<max);
	}

	private void placeWallUp(int x, int y){
		if(boundsCheck(x,y))
			this.plateau[x][y][0]=true;
		if(boundsCheck(x-1,y))
			this.plateau[x-1][y][2]=true;
	}

	private void placeWallRight(int x, int y){
		if(boundsCheck(x,y))
			this.plateau[x][y][1]=true;
		if(boundsCheck(x,y+1))
			this.plateau[x][y+1][3]=true;
	}

	private void placeWallDown(int x, int y){
		if(boundsCheck(x,y))
			this.plateau[x][y][2]=true;
		if(boundsCheck(x+1,y))
			this.plateau[x+1][y][0]=true;
	}

	private void placeWallLeft(int x, int y){
		if(boundsCheck(x,y))
			this.plateau[x][y][3]=true;
		if(boundsCheck(x,y-1))
			this.plateau[x][y-1][1]=true;
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

	private void placeBorderWalls(){
		//HAUT
		int r=randMur();
		placeWallLeft(0,r);
		r=randMur();
		placeWallLeft(0,max-r);

		//DROITE
		r=randMur();
		placeWallUp(r,max-1);
		r=randMur();
		placeWallUp(max-r,max-1);

		//BAS
		r=randMur();
		placeWallLeft(max-1,r);
		r=randMur();
		placeWallLeft(max-1,max-r);

		//GAUCHE
		r=randMur();
		placeWallUp(r,0);
		r=randMur();
		placeWallUp(max-r,0);

		//CENTRAL WALL
		placeWallUp(max/2-1,max/2-1);
		placeWallUp(max/2-1,max/2);
		placeWallRight(max/2-1,max/2);
		placeWallRight(max/2,max/2);
		placeWallDown(max/2,max/2-1);
		placeWallDown(max/2,max/2);
		placeWallLeft(max/2-1,max/2-1);
		placeWallLeft(max/2,max/2-1);

	}

	private void placeCorners(){
		//4-5 coins par quartier du plateau (min 4)
		//il faut tirer une coordonnée et une orientation au pif
		//espace de 2 minimum entre les coords

		//( (max/4)^2)? )
		int nbCoins = max;
		System.out.println("nbCoins: " + nbCoins);
		int[][] allCoords = new int[nbCoins][2];

		//CREATION DES COORDONNEES
		int i=0, j=0;
		while(i<nbCoins && j<1000){
			j++;
			allCoords[i] = randCoordCorner();
			if(!collision(allCoords,i)){
				i++;
			}
		}
		nbCoins=i;

		System.out.println("\n" + nbCoins + " Coins:");
		//PLACEMENT
		for(i=0; i<nbCoins; i++){

			int randOrientation = random.nextInt(4);

			System.out.println(allCoords[i][0] + " " + allCoords[i][1] + " orientation " + randOrientation);

			//0 = ͞ |
			if(randOrientation==0){
				placeWallUp(allCoords[i][0],allCoords[i][1]);
				placeWallRight(allCoords[i][0],allCoords[i][1]);
			}
			//1 = _|
			else if(randOrientation==1){
				placeWallRight(allCoords[i][0],allCoords[i][1]);
				placeWallDown(allCoords[i][0],allCoords[i][1]);
			}
			//2 = |_
			else if(randOrientation==2){
				placeWallDown(allCoords[i][0],allCoords[i][1]);
				placeWallLeft(allCoords[i][0],allCoords[i][1]);
			}
			//3 = |͞
			else if(randOrientation==3){
				placeWallLeft(allCoords[i][0],allCoords[i][1]);
				placeWallUp(allCoords[i][0],allCoords[i][1]);
			}
		}

	}

	private int randMur() {
		return random.nextInt(5) + 2;
	}

	private int[] randCoordCorner() {
		int[] coords = new int[2];

		coords[0] = random.nextInt(max-2) + 1;

		//éviter le carré central
		if(coords[0]>=max/2-2 && coords[0]<=max/2+2){
			//entre 0 et 5  et  5 et 10
			int r = random.nextInt(max-6);
			if(r>=max/2-2)
				r+=4;//eviter le carré central
			coords[1]=r;
		}
		else coords[1] = random.nextInt(max-2) + 1;

		return coords;
	}

	private boolean collision(int[][] allCoords, int i){
		boolean collision=false;
		//System.out.println("check collision de :");
		//System.out.println(allCoords[i][0] + " " + allCoords[i][1]);
		for(int j=0; j<i; j++){
			if(Math.abs(allCoords[j][0] -  allCoords[i][0]) <= 2){
				if(Math.abs(allCoords[j][1] -  allCoords[i][1]) <= 2){
					collision=true;
				}
			}
		}
		return collision;
	}

	public boolean[][][] getPlateau(){
		return this.plateau;
	}
}
