package main.java.com.gameboard;

import java.util.ArrayList;
import java.util.Random;

public class HighLevelGameboard extends GenericGameboard {

	private static int max=16;
	private ArrayList<ArrayList<Cell>> cells;
	private ArrayList<Token> robots;

	private Random random = new Random();

	public HighLevelGameboard(){

		initBoard();

		placeBorderWalls();

		placeCorners();

	}

	private boolean boundsCheck(int x, int y){
		return (x>=0 && x<max && y>=0 && y<max);
	}

	public Cell getCell(int x, int y) {
		return cells.get(x).get(y);
	}

	private void placeNeighbourWall(int x, int y, Direction d) {
		switch (d) {
			case up:
				if(boundsCheck(x-1,y)) getCell(x - 1, y).addWall(Direction.down);
			case right:
				if(boundsCheck(x,y+1)) getCell(x, y + 1).addWall(Direction.left);
			case down:
				if(boundsCheck(x+1,y)) getCell(x + 1, y).addWall(Direction.up);
			case left:
				if(boundsCheck(x,y-1)) getCell(x, y - 1).addWall(Direction.right);
		}
	}

	private void placeWall(int x, int y, Direction d){
		if(boundsCheck(x,y)) {
			getCell(x, y).addWall(d);
			placeNeighbourWall(x, y, d);
		}
	}

	private void initBoard(){
		cells = new ArrayList<>();
		robots = new ArrayList<>();

		for(int i=0; i<max; i++){
			cells.add(new ArrayList<Cell>());
			for(int j=0; j<max; j++){
				cells.get(i).add(new Cell());
			}
		}
	}

	private int randMur() {
		return random.nextInt(5) + 2;
	}

	private void placeBorderWalls(){
		//HAUT x=0
		int r=randMur();
		placeWall(0,r,Direction.left);
		r=randMur();
		placeWall(0,max-r, Direction.left);

		//DROITE y=max-1
		r=randMur();
		placeWall(r,max-1,Direction.up);
		r=randMur();
		placeWall(max-r,max-1,Direction.up);

		//BAS x=max-1
		r=randMur();
		placeWall(max-1,r, Direction.left);
		r=randMur();
		placeWall(max-1,max-r, Direction.left);

		//GAUCHE y=0
		r=randMur();
		placeWall(r,0,Direction.up);
		r=randMur();
		placeWall(max-r,0,Direction.up);

		//CENTRAL WALL
		placeWall(max/2-1,max/2-1, Direction.up);
		placeWall(max/2-1,max/2, Direction.up);
		placeWall(max/2-1,max/2, Direction.right);
		placeWall(max/2,max/2, Direction.right);
		placeWall(max/2,max/2-1, Direction.down);
		placeWall(max/2,max/2, Direction.down);
		placeWall(max/2-1,max/2-1, Direction.left);
		placeWall(max/2,max/2-1, Direction.left);

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
				placeWall(allCoords[i][0],allCoords[i][1], Direction.up);
				placeWall(allCoords[i][0],allCoords[i][1], Direction.right);
			}
			//1 = _|
			else if(randOrientation==1){
				placeWall(allCoords[i][0],allCoords[i][1], Direction.right);
				placeWall(allCoords[i][0],allCoords[i][1], Direction.down);
			}
			//2 = |_
			else if(randOrientation==2){
				placeWall(allCoords[i][0],allCoords[i][1], Direction.down);
				placeWall(allCoords[i][0],allCoords[i][1], Direction.left);
			}
			//3 = |͞
			else if(randOrientation==3){
				placeWall(allCoords[i][0],allCoords[i][1], Direction.left);
				placeWall(allCoords[i][0],allCoords[i][1], Direction.up);
			}
			else System.out.println("error generating random board corners");
		}
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

	public HighLevelGameboard getGameboard(){
		return this;
	}


	public void display() {

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				if (this.getCell(i,j).PossibleMove(Direction.up)) {
					if (this.getCell(i,j).PossibleMove(Direction.right)) {
						System.out.print("͞ |");//0
					} else if (this.getCell(i,j).PossibleMove(Direction.left)) {
						System.out.print("|͞ ");//3
					} else {
						System.out.print("͞ ͞ ");
					}
				} else if (this.getCell(i,j).PossibleMove(Direction.right)) {
					if (this.getCell(i,j).PossibleMove(Direction.down)) {
						System.out.print("_|");//1
					} else {
						System.out.print(" |");
					}
				} else if (this.getCell(i,j).PossibleMove(Direction.down)) {
					if (this.getCell(i,j).PossibleMove(Direction.left)) {
						System.out.print("|_");
					} else {
						System.out.print("__");
					}
				} else if (this.getCell(i,j).PossibleMove(Direction.left)) {
					System.out.print("| ");
				} else {
					System.out.print("##");
				}
			}
			System.out.println();
		}

		System.out.println("\n##################################");
		for (int i = 0; i < 16; i++) {
			System.out.print("#");
			for (int j = 0; j < 16; j++) {
				if (this.getCell(i,j).PossibleMove(Direction.right)) {
					if (this.getCell(i,j).PossibleMove(Direction.down)) {
						System.out.print("_|");
					} else {
						System.out.print(" |");
					}
				} else if (this.getCell(i,j).PossibleMove(Direction.down)) {
					System.out.print("__");
				} else {
					System.out.print("  ");
				}
			}
			System.out.println("#");
		}
		System.out.println("##################################");
	}
}
