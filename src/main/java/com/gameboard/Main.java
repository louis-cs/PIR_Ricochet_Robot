package com.gameboard;

import com.graph.struct.TreeSearch;

public class Main {

	public static void main(String[] args){
		System.out.println("displaying console gameboard");
		HighLevelGameboard gameboard=new HighLevelGameboard();
		/*
		gameboard.displayBoard();
		Token r = gameboard.getRobots().get(0);
		gameboard.moveUntilWall(r,Direction.left);*/
		TreeSearch.search(gameboard);

		/*
		Coordinates c1 = new Coordinates(1,2);
		Coordinates c2 = new Coordinates(2,2);
		Token robot1 = new Token(c1, Color.BLUE);
		Token robot2 = new Token(c1, Color.BLUE);
		Token robot3 = new Token(c1, Color.RED);
		Token robot4 = new Token(c2, Color.BLUE);
		System.out.println("coords diff: "+c1.compareTo(c2));
		System.out.println("coords pareil: "+c1.compareTo(c1));
		System.out.println("robot1.compareTo(robot2), pareil: "+robot1.compareTo(robot2));
		System.out.println("robot1.compareTo(robot3), color diff: "+robot1.compareTo(robot3));
		System.out.println("robot3.compareTo(robot1), color diff: "+robot3.compareTo(robot1));
		System.out.println("robot1.compareTo(robot4), coord diff: "+robot1.compareTo(robot4));
		//gameboard.displayCollision();*/
	}
}

