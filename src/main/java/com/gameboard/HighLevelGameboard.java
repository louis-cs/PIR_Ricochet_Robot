package com.gameboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class HighLevelGameboard extends GenericGameboard {

	private int max=16;
	private int nbRobots=4;
	private ArrayList<ArrayList<Cell>> cells;
	private ArrayList<ArrayList<Boolean>> collision;
	private ArrayList<Token> robots;
	private Token objective;

	private Random random = new Random();

	public HighLevelGameboard(){

		initBoard();

		placeBorderWalls();

		placeCorners();

		placeRobots();
	}

	public HashSet<Direction> getWalls(int x, int y){
		return this.cells.get(x).get(y).getWalls();
	}

	public ArrayList<Token> getRobots(){
		return this.robots;
	}

	public Token getObjective(){
		return this.objective;
	}

	public void makeAllMoves(){
		for(Token robot : robots){
			for(Direction d : Direction.values()){
				moveUntilWall(robot, d);
			}
		}
	}

	public void moveUntilWall(Token robot, Direction d){

		Coordinates c = robot.getCoordinates();

		while(possibleMove(c, d)){
			c.move(d);
		}

		robot.setCooordinates(c);
	}

	public boolean possibleMove(Coordinates c, Direction d){
		return this.cells.get(c.getX()).get(c.getY()).PossibleMove(d);
	}

	public HighLevelGameboard getGameboard(){
		return this;
	}

	private boolean boundsCheck(int x, int y){
		return (x>=0 && x<max && y>=0 && y<max);
	}

	public Cell getCell(int x, int y) {
		return cells.get(x).get(y);
	}

	private void addCollision(int x, int y, Direction d){
		int i,j;
		/*Up
		X X X
		X ͞ ͞ X
		O O O
		*/
		if(d==Direction.up) {
			for (i = x - 1; i <= x; i++)
				for (j = y - 1; j <= y + 1; j++)
					if (boundsCheck(i, j))
						this.collision.get(i).set(j, true);
		}
		/*Right
		O X X
		O  |X
		O X X
		*/
		else if(d==Direction.right) {
			for (i = x - 1; i <= x + 1; i++)
				for (j = y; j <= y + 1; j++)
					if (boundsCheck(i, j))
						this.collision.get(i).set(j, true);
		}
		/*Down
		O O O
		X _ X
		X X X
		*/
		if(d==Direction.down) {
			for (i = x; i <= x + 1; i++)
				for (j = y - 1; j <= y + 1; j++)
					if (boundsCheck(i, j))
						this.collision.get(i).set(j, true);
		}
		/*Left
		X X O
		X|  O
		X X O
		*/
		else if(d==Direction.left) {
			for (i = x - 1; i <= x + 1; i++)
				for (j = y - 1; j <= y; j++)
					if (boundsCheck(i, j))
						this.collision.get(i).set(j, true);
		}
	}

	private void placeNeighbourWall(int x, int y, Direction d) {
		switch (d) {
			case up:
				if(boundsCheck(x-1,y)) getCell(x - 1, y).addWall(Direction.down);
				break;
			case right:
				if(boundsCheck(x,y+1)) getCell(x, y + 1).addWall(Direction.left);
				break;
			case down:
				if(boundsCheck(x+1,y)) getCell(x + 1, y).addWall(Direction.up);
				break;
			case left:
				if(boundsCheck(x,y-1)) getCell(x, y - 1).addWall(Direction.right);
				break;
		}
	}

	private void placeWall(int x, int y, Direction d){
		if(boundsCheck(x,y)) {
			getCell(x, y).addWall(d);
			placeNeighbourWall(x, y, d);
			addCollision(x, y, d);
		}
	}

	private void initBoard(){
		cells = new ArrayList<>();
		collision = new ArrayList<>();
		robots = new ArrayList<>();

		for(int i=0; i<max; i++){
			cells.add(new ArrayList<>());
			collision.add(new ArrayList<>());
			for(int j=0; j<max; j++){
				cells.get(i).add(new Cell());
				collision.get(i).add(false);
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
		ArrayList<Coordinates> allCoords = new ArrayList<>();

		//CREATION DES COORDONNEES
		int i=0, j=0;
		while(i<nbCoins && j<1000){
			j++;
			allCoords.set(i, randCoordCorner());
			if(!collision.get(allCoords.get(i).getX()).get(allCoords.get(i).getY())){
				placeCorner(allCoords.get(i));
				i++;
			}
		}
		nbCoins=i;

		//OBJECTIF
		placeObjective(allCoords.get(0));

		System.out.println("\n" + nbCoins + " Coins:");
	}

	private void placeCorner(Coordinates coord){
		int randOrientation = random.nextInt(4);

		System.out.println(coord.getX() + " " + coord.getY() + " orientation " + randOrientation);

		//0 = ͞ |
		if(randOrientation==0){
			placeWall(coord.getX(),coord.getY(), Direction.up);
			placeWall(coord.getX(),coord.getY(), Direction.right);
		}
		//1 = _|
		else if(randOrientation==1){
			placeWall(coord.getX(),coord.getY(), Direction.right);
			placeWall(coord.getX(),coord.getY(), Direction.down);
		}
		//2 = |_
		else if(randOrientation==2){
			placeWall(coord.getX(),coord.getY(), Direction.down);
			placeWall(coord.getX(),coord.getY(), Direction.left);
		}
		//3 = |͞
		else if(randOrientation==3){
			placeWall(coord.getX(),coord.getY(), Direction.left);
			placeWall(coord.getX(),coord.getY(), Direction.up);
		}
	}

	private Coordinates randCoordCorner() {
		Coordinates coords = new Coordinates();

		//de 1 à 15
		coords.setX(random.nextInt(max-2) + 1);

		//éviter le carré central
		if(coords.getX()>=max/2-2 && coords.getX()<=max/2+2){
			//entre 0 et 5  et  5 et 10
			int r = random.nextInt(max-6);
			if(r>=(max-6)/2)
				r+=4;//eviter le carré central
			else
				r+=1;
			coords.setY(r);
		}
		else coords.setY(random.nextInt(max-2) + 1);

		return coords;
	}

	private Color getColor(int x){
		Color color;
		switch(x){
			case 0:
				color = Color.RED;
				break;
			case 1:
				color = Color.BLUE;
				break;
			case 2:
				color = Color.GREEN;
				break;
			case 3:
				color = Color.YELLOW;
				break;
			default:
				color = Color.BLACK;
				break;
		}
		return color;
	}

	private void placeObjective(Coordinates c){
		this.objective = new Token(c, getColor(random.nextInt(3)));
	}

	private void placeRobots(){
		for(int i=0; i<nbRobots; i++){

			int x = random.nextInt(max);
			int y = random.nextInt(max);
			boolean placed = false;

			while(!placed) {
				//si on est dans le carré central
				if (x == max / 2 - 1 || x == max / 2) {
					y = random.nextInt(max - 2);
					if (y > max / 2 - 2)
						y += 2;
				}

				//collision avec l'objectif
				if(!(x==objective.getCoordinates().getX() && y==objective.getCoordinates().getY()))
					placed=true;

				x = random.nextInt(max);
				y = random.nextInt(max);
			}
			this.robots.add(new Token(new Coordinates(x, y), getColor(i)));
		}
	}

	public void displayFull() {

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				if (this.getCell(i, j).PossibleMove(Direction.up)) {
					if (this.getCell(i, j).PossibleMove(Direction.right)) {
						System.out.print("͞ |");//0
					} else if (this.getCell(i, j).PossibleMove(Direction.left)) {
						System.out.print("|͞ ");//3
					} else {
						System.out.print("͞ ͞ ");
					}
				} else if (this.getCell(i, j).PossibleMove(Direction.right)) {
					if (this.getCell(i, j).PossibleMove(Direction.down)) {
						System.out.print("_|");//1
					} else {
						System.out.print(" |");
					}
				} else if (this.getCell(i, j).PossibleMove(Direction.down)) {
					if (this.getCell(i, j).PossibleMove(Direction.left)) {
						System.out.print("|_");
					} else {
						System.out.print("__");
					}
				} else if (this.getCell(i, j).PossibleMove(Direction.left)) {
					System.out.print("| ");
				} else {
					System.out.print("##");
				}
			}
			System.out.println();
		}
	}

	private boolean isThereARobot(int x, int y) {
		for (int h = 0; h < nbRobots; h++) {
			if (robots.get(h).getCoordinates().getX() == x && robots.get(h).getCoordinates().getY() == y) {
				return true;
			}
		}
		return false;
	}

	public void displayBoard(){
		System.out.println("\n##################################");
		for (int i = 0; i < 16; i++) {
			System.out.print("#");
			for (int j = 0; j < 16; j++) {

				if(isThereARobot(i,j) || objective.getCoordinates().getX()==i && objective.getCoordinates().getY()==j){
					if (this.getCell(i,j).PossibleMove(Direction.right)) {
						if (this.getCell(i,j).PossibleMove(Direction.down)) {
							System.out.print("±|");
						} else {
							System.out.print("+|");
						}
					} else if (this.getCell(i,j).PossibleMove(Direction.down)) {
						System.out.print("± ");
					} else {
						System.out.print("+ ");
					}
				}
				else {
					if (this.getCell(i, j).PossibleMove(Direction.right)) {
						if (this.getCell(i, j).PossibleMove(Direction.down)) {
							System.out.print("_|");
						} else {
							System.out.print(" |");
						}
					} else if (this.getCell(i, j).PossibleMove(Direction.down)) {
						System.out.print("_ ");
					} else {
						System.out.print("  ");
					}
				}
			}
			System.out.println("#");
		}
		System.out.println("##################################");
	}

	public void displayCollision(){
		System.out.println("##################################");
		for(int i=0; i<max; i++){
			System.out.print("#");
			for(int j=0; j<max; j++){
				if(collision.get(i).get(j)){
					System.out.print("X ");
				}
				else{
					System.out.print("O ");
				}
			}
			System.out.print("#\n");
		}
		System.out.println("##################################");
	}
}
