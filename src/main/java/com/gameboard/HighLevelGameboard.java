package com.gameboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class HighLevelGameboard extends GenericGameboard implements Comparable<HighLevelGameboard> {

	private static int max=16;
	public static int nbRobots=4;
	private static ArrayList<ArrayList<Cell>> cells;
	private static ArrayList<ArrayList<Boolean>> collision;
	private ArrayList<Token> robots;
	private static Token objective;

	private int depth;

	private Random random = new Random();

	public HighLevelGameboard(){
		//treesearch depth
		depth = 0;

		initBoard();

		//placeBorderWalls();

		//place aussi l'objectif
		//placeCorners();


		//calculateDistanceToObjective(objective.getCoordinates(), 0);


		createStaticBoard();
		placeRobots();


	}

	public HighLevelGameboard(ArrayList<Token> robots, int depth){
		this.robots = robots;
		this.depth = depth;
	}

	public HighLevelGameboard duplicate(int depth){
		ArrayList<Token> robotsDuplicate = new ArrayList<>();
		for(Token robot : robots)
			robotsDuplicate.add(robot.duplicate());
		return new HighLevelGameboard(robotsDuplicate, depth);
	}

	@Override
	public int compareTo(HighLevelGameboard highLevelGameboard) {

		int distanceDiff = getDistanceToObjective()-highLevelGameboard.getDistanceToObjective();
		if(distanceDiff!=0)
			return distanceDiff;

		return depth - highLevelGameboard.getDepth();
	}

	public int getDistanceToObjective(){
		Token robot = null;
		for(Token r : robots){
			if(r.getColor().equals(objective.getColor())){
				robot = r;
				break;
			}
		}
		assert robot != null;
		return robot.getDistanceToObjective();
	}

	public int getDepth() {
		return depth;
	}

	public HashSet<Direction> getWalls(int x, int y){
		return cells.get(x).get(y).getWalls();
	}

	public ArrayList<Token> getRobots(){
		return this.robots;
	}

	public Token getObjective(){
		return objective;
	}

	public int moveUntilWall(Token robot, Direction d){
		int numberOfMoves = 0;
		Coordinates c = robot.getCoordinates();

		while(boundsCheck(c, d) && !getCell(c).containsWall(d) && !isThereARobot(c, d)){
			c.move(d);
			numberOfMoves++;
		}
		robot.setCooordinates(c, getCell(c).getDistanceObjective());

		return numberOfMoves;
	}

	public HighLevelGameboard getGameboard(){
		return this;
	}

	private boolean boundsCheck(Coordinates c, Direction d){

		boolean ret = false;

		switch (d){
			case up:
				ret = c.getX()!=0;
				break;
			case right:
				ret = c.getY()!=max-1;
				break;
			case down:
				ret = c.getX()!=max-1;
				break;
			case left:
				ret = c.getY()!=0;
				break;
		}

		return ret;
	}

	private boolean boundsCheck(int x, int y){
		return (x>=0 && x<max && y>=0 && y<max);
	}

	public Cell getCell(Coordinates c) {
		return cells.get(c.getX()).get(c.getY());
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
						collision.get(i).set(j, true);
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
						collision.get(i).set(j, true);
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
						collision.get(i).set(j, true);
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
						collision.get(i).set(j, true);
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
		return random.nextInt((max/2)-3) + 2;
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

	// TODO : Function to modify to have an equal number of walls in each quarter of the board.
	private void placeCorners(){
		//4-5 coins par quartier du plateau (min 4)
		//il faut tirer une coordonnée et une orientation au pif
		//espace de 2 minimum entre les coords

		//( (max/4)^2)? )
		int nbCoinsMax = max;
		//System.out.println("nbCoins: " + nbCoins);

		//CREATION DES COORDONNEES
		int nbCoins=0, nbIter=0;
		while(nbCoins<nbCoinsMax && nbIter<1000){
			nbIter++;
			Coordinates c = randCoordCorner();

			if(!collision.get(c.getX()).get(c.getY())){

				//PLACE THE OBJECTIVE
				if(nbCoins==0) {
					System.out.println("objective: " + c.toString());
					placeObjective(c);
				}
				//PLACE A CORNER
				int randOrientation = random.nextInt(4);
				placeCorner(c, randOrientation);
				nbCoins++;
			}
		}

		//System.out.println("\n" + nbCoins + " Coins:");
	}

	private void placeCorner(Coordinates coord, int orientation){

		//System.out.println(coord.getX() + " " + coord.getY() + " orientation " + randOrientation);

		//0 = ͞ |
		if(orientation==0){
			placeWall(coord.getX(),coord.getY(), Direction.up);
			placeWall(coord.getX(),coord.getY(), Direction.right);
		}
		//1 = _|
		else if(orientation==1){
			placeWall(coord.getX(),coord.getY(), Direction.right);
			placeWall(coord.getX(),coord.getY(), Direction.down);
		}
		//2 = |_
		else if(orientation==2){
			placeWall(coord.getX(),coord.getY(), Direction.down);
			placeWall(coord.getX(),coord.getY(), Direction.left);
		}
		//3 = |͞
		else if(orientation==3){
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
		objective = new Token(c, getColor(random.nextInt(3)), 0);
	}

	private void calculateDistanceToObjective(Coordinates c, int distanceToObjective) {

		Cell currentCell = getCell(c);
		int currentDistance = currentCell.getDistanceObjective();

		//si on a trouvé une meilleure distance
		if(distanceToObjective<currentDistance) {
			currentCell.setDistanceObjective(distanceToObjective);

			for(Direction d : Direction.values()) {
				Coordinates explore = new Coordinates(c.getX(), c.getY());
				//on continue d'avancer tant qu'il y pas de mur
				while (boundsCheck(explore, d) && !getCell(explore).containsWall(d)){
					explore.move(d);
					calculateDistanceToObjective(explore, distanceToObjective+1);
				}
			}
		}
	}

	private void placeRobots(){
		for(int i=0; i<nbRobots; i++){

			int x=0, y=0;
			boolean placed = false;

			while(!placed) {
				x = random.nextInt(max);
				y = random.nextInt(max);

				//si on est dans le carré central
				if (x == max / 2 - 1 || x == max / 2) {
					y = random.nextInt(max - 2);
					if (y > max / 2 - 2)
						y += 2;
				}

				//collision avec l'objectif
				if(!(x==objective.getCoordinates().getX() && y==objective.getCoordinates().getY()))
					placed=true;
			}
			Coordinates c = new Coordinates(x, y);
			System.out.println("robot "+getColor(i)+": "+c.toString());
			this.robots.add(new Token(c, getColor(i), getCell(c).getDistanceObjective()));
		}
	}

	public void displayFull() {

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				if (this.getCell(i, j).containsWall(Direction.up)) {
					if (this.getCell(i, j).containsWall(Direction.right)) {
						System.out.print("͞ |");//0
					} else if (this.getCell(i, j).containsWall(Direction.left)) {
						System.out.print("|͞ ");//3
					} else {
						System.out.print("͞ ͞ ");
					}
				} else if (this.getCell(i, j).containsWall(Direction.right)) {
					if (this.getCell(i, j).containsWall(Direction.down)) {
						System.out.print("_|");//1
					} else {
						System.out.print(" |");
					}
				} else if (this.getCell(i, j).containsWall(Direction.down)) {
					if (this.getCell(i, j).containsWall(Direction.left)) {
						System.out.print("|_");
					} else {
						System.out.print("__");
					}
				} else if (this.getCell(i, j).containsWall(Direction.left)) {
					System.out.print("| ");
				} else {
					System.out.print("##");
				}
			}
			System.out.println();
		}
	}

	public void displayDistanceToObjective() {

		for (int i = 0; i < 16; i++) {
			for (int j = 0; j < 16; j++) {
				int d = getCell(i,j).getDistanceObjective();
				if(d==Integer.MAX_VALUE)
					System.out.print("M ");
				else
					System.out.print(d + " ");
			}
			System.out.println();
		}
	}

	private boolean isThereARobot(Coordinates c, Direction d) {

		boolean ret = false;

		switch (d){
			case up:
				ret = isThereARobot(c.getX()-1, c.getY())!=null;
				break;
			case right:
				ret = isThereARobot(c.getX(), c.getY()+1)!=null;
				break;
			case down:
				ret = isThereARobot(c.getX()+1, c.getY())!=null;
				break;
			case left:
				ret = isThereARobot(c.getX(), c.getY()-1)!=null;
				break;
		}

		return ret;
	}

	private Token isThereARobot(int x, int y) {
		for (Token robot : robots) {
			if (robot.getCoordinates().getX() == x && robot.getCoordinates().getY() == y) {
				return robot;
			}
		}
		return null;
	}

	public void displayBoard(){
		System.out.println("\n##################################");
		for (int i = 0; i < 16; i++) {
			System.out.print("#");
			for (int j = 0; j < 16; j++) {

				Token robot = isThereARobot(i,j);
				if(robot!=null) {
					if (robot.getColor().equals(Color.RED)){
						if (this.getCell(i,j).containsWall(Direction.right)) {
							if (this.getCell(i,j).containsWall(Direction.down)) {
								System.out.print("\uD835\uDD63|");
							} else {
								System.out.print("r|");
							}
						} else if (this.getCell(i,j).containsWall(Direction.down)) {
							System.out.print("\uD835\uDD63 ");
						} else {
							System.out.print("r ");
						}
					}
					else if (robot.getColor().equals(Color.GREEN)){
						if (this.getCell(i,j).containsWall(Direction.right)) {
							if (this.getCell(i,j).containsWall(Direction.down)) {
								System.out.print("\uD835\uDD58|");
							} else {
								System.out.print("g|");
							}
						} else if (this.getCell(i,j).containsWall(Direction.down)) {
							System.out.print("\uD835\uDD58 ");
						} else {
							System.out.print("g ");
						}
					}
					else if (robot.getColor().equals(Color.BLUE)){
						if (this.getCell(i,j).containsWall(Direction.right)) {
							if (this.getCell(i,j).containsWall(Direction.down)) {
								System.out.print("\uD835\uDD53|");
							} else {
								System.out.print("b|");
							}
						} else if (this.getCell(i,j).containsWall(Direction.down)) {
							System.out.print("\uD835\uDD53 ");
						} else {
							System.out.print("b ");
						}
					}
					else if (robot.getColor().equals(Color.YELLOW)){
						if (this.getCell(i,j).containsWall(Direction.right)) {
							if (this.getCell(i,j).containsWall(Direction.down)) {
								System.out.print("\uD835\uDD6A|");
							} else {
								System.out.print("y|");
							}
						} else if (this.getCell(i,j).containsWall(Direction.down)) {
							System.out.print("\uD835\uDD6A ");
						} else {
							System.out.print("y ");
						}
					}
				}
				else if(objective.getCoordinates().getX()==i && objective.getCoordinates().getY()==j){
					if (objective.getColor().equals(Color.RED)){
						if (this.getCell(i,j).containsWall(Direction.right)) {
							if (this.getCell(i,j).containsWall(Direction.down)) {
								System.out.print("ℝ|");
							} else {
								System.out.print("R|");
							}
						} else if (this.getCell(i,j).containsWall(Direction.down)) {
							System.out.print("ℝ ");
						} else {
							System.out.print("R ");
						}
					}
					else if (objective.getColor().equals(Color.GREEN)){
						if (this.getCell(i,j).containsWall(Direction.right)) {
							if (this.getCell(i,j).containsWall(Direction.down)) {
								System.out.print("\uD835\uDD3E|");
							} else {
								System.out.print("G|");
							}
						} else if (this.getCell(i,j).containsWall(Direction.down)) {
							System.out.print("\uD835\uDD3E ");
						} else {
							System.out.print("G ");
						}
					}
					else if (objective.getColor().equals(Color.BLUE)){
						if (this.getCell(i,j).containsWall(Direction.right)) {
							if (this.getCell(i,j).containsWall(Direction.down)) {
								System.out.print("\uD835\uDD39|");
							} else {
								System.out.print("B|");
							}
						} else if (this.getCell(i,j).containsWall(Direction.down)) {
							System.out.print("\uD835\uDD39 ");
						} else {
							System.out.print("B ");
						}
					}
					else if (objective.getColor().equals(Color.YELLOW)){
						if (this.getCell(i,j).containsWall(Direction.right)) {
							if (this.getCell(i,j).containsWall(Direction.down)) {
								System.out.print("\uD835\uDD50|");
							} else {
								System.out.print("Y|");
							}
						} else if (this.getCell(i,j).containsWall(Direction.down)) {
							System.out.print("\uD835\uDD50 ");
						} else {
							System.out.print("Y ");
						}
					}
				}
				else {
					if (this.getCell(i, j).containsWall(Direction.right)) {
						if (this.getCell(i, j).containsWall(Direction.down)) {
							System.out.print("_|");
						} else {
							System.out.print(" |");
						}
					} else if (this.getCell(i, j).containsWall(Direction.down)) {
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

	public void createStaticBoard(){
		placeWall(7,6,Direction.right);
		placeWall(8,6,Direction.right);
		placeWall(8,8,Direction.right);
		placeWall(7,8,Direction.right);

		placeWall(6,7,Direction.down);
		placeWall(6,8,Direction.down);
		placeWall(8,7,Direction.down);
		placeWall(8,8,Direction.down);


		placeWall(0,3,Direction.right);
		placeWall(0,11,Direction.right);
		placeWall(15,5,Direction.right);
		placeWall(15,11,Direction.right);

		placeWall(4,0,Direction.down);
		placeWall(10,0,Direction.down);
		placeWall(5,15,Direction.down);
		placeWall(8,15,Direction.down);


		placeCorner(new Coordinates(4,2),0);
		placeCorner(new Coordinates(6,1),3);
		placeCorner(new Coordinates(2,5),1);
		placeCorner(new Coordinates(5,7),2);

		placeCorner(new Coordinates(9,1),0);
		placeCorner(new Coordinates(9,5),3);
		placeCorner(new Coordinates(14,2),2);
		placeCorner(new Coordinates(12,6),1);

		placeCorner(new Coordinates(10,8),3);
		placeCorner(new Coordinates(11,10),1);
		placeCorner(new Coordinates(10,13),3);
		placeCorner(new Coordinates(12,14),0);
		placeCorner(new Coordinates(14,9),0);

		placeCorner(new Coordinates(4,10),2);
		placeCorner(new Coordinates(2,11),1);
		placeCorner(new Coordinates(5,12),3);
		placeCorner(new Coordinates(3,13),0);


		placeObjective(new Coordinates(6,1));
	}

}
