package com.gameboard;

import java.awt.*;

public class DisplayGameboard extends HighLevelGameboard{


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
				else if(getObjective().getCoordinates().getX()==i && getObjective().getCoordinates().getY()==j){
					if (getObjective().getColor().equals(Color.RED)){
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
					else if (getObjective().getColor().equals(Color.GREEN)){
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
					else if (getObjective().getColor().equals(Color.BLUE)){
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
					else if (getObjective().getColor().equals(Color.YELLOW)){
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
				if(getCollision().get(i).get(j)){
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
