package com.gameboard;

public class Main {

	public static void main(String[] args){
		System.out.println("displaying console gameboard");
		HighLevelGameboard gameboard=new HighLevelGameboard();
		gameboard.displayBoard();
		//gameboard.displayCollision();
	}
}

