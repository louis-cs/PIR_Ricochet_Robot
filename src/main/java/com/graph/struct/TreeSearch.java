package com.graph.struct;

import com.gameboard.Direction;
import com.gameboard.HighLevelGameboard;
import com.gameboard.Token;

import java.util.ArrayList;

public class TreeSearch{

	private static BinaryHeap<HighLevelGameboard> binaryHeap = new BinaryHeap<>();

	private static int maxIter = 200000;
	private static String message = "";

	/**
	 * tries to find a solution to a specific game and prints the solution
	 */
	public static ArrayList<HighLevelGameboard> search(HighLevelGameboard gameboard) { //stopper l'algo quand on arrive à l'objectif
		binaryHeap.insert(gameboard);
		ArrayList<HighLevelGameboard> solutionList = new ArrayList<>();

		int depth = gameboard.getDepth(), distanceToObjective = Integer.MAX_VALUE, x=0;
		while (distanceToObjective!=0 && x<maxIter){x++;

			for (int i = 0; i < HighLevelGameboard.nbRobots; i++) {
				for (Direction d : Direction.values()) {

					//il faut le clone autant de fois que de situations
					HighLevelGameboard gameboardClone = binaryHeap.findMin().duplicate(depth+1);

					Token robot = gameboardClone.getRobots().get(i);
					//insert the gameboard only if the robot moved
					if(gameboardClone.moveUntilWall(robot, d)!=0)
						binaryHeap.insert(gameboardClone);
					//gameboardClone.displayBoard();
					//System.out.println("distanceToObjective: " + gameboardClone.getDistanceToObjective());
				}
			}
			binaryHeap.deleteMin();
			depth = binaryHeap.findMin().getDepth();
			distanceToObjective = binaryHeap.findMin().getDistanceToObjective();
		}

		if(x<maxIter) {
			HighLevelGameboard solution = binaryHeap.findMin();
			for (ArrayList<Token> move : solution.getPreviousMoves()) {
				HighLevelGameboard board = new HighLevelGameboard(move, 0, solution.getPreviousMoves());
				//board.displayBoard();
				//System.out.println("distanceToObjective: " + board.getRobotSeekingObjective().getDistanceToObjective());
				solutionList.add(board);
			}
			message = "iterations : " + x + "\ndepth : " + depth;
		}else
			message = "search failed\ndepth : " + depth;

		return solutionList;
	}

	public static String getMessage(){
		return message;
	}
}
