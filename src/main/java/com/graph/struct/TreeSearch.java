package com.graph.struct;

import com.gameboard.Direction;
import com.gameboard.HighLevelGameboard;
import com.gameboard.Token;

import java.util.ArrayList;

public class TreeSearch{


	public static String message = "";
	public static long MAX_RAM = (Runtime.getRuntime().maxMemory()/2/1000000);
	public static int depth = 0;

	/**
	 * tries to find a solution to a specific game and prints the solution
	 */
	public ArrayList<HighLevelGameboard> search(HighLevelGameboard gameboard) { //stopper l'algo quand on arrive à l'objectif
		BinaryHeap<HighLevelGameboard> binaryHeap = new BinaryHeap<>();
		binaryHeap.insert(gameboard);
		ArrayList<HighLevelGameboard> solutionList = new ArrayList<>();
		HighLevelGameboard solution = null;

		long RAM = 0;
		depth = gameboard.getDepth();
		int distanceToObjective = Integer.MAX_VALUE, nbIter = 0, maxIter = 200000000;
		LOOP:
		while (distanceToObjective != 0 && nbIter < maxIter && RAM < MAX_RAM) {
			for (int i = 0; i < HighLevelGameboard.nbRobots; i++) {
				for (Direction d : Direction.values()) {
					nbIter++;
					//il faut le clone autant de fois que de situations
					HighLevelGameboard gameboardClone = binaryHeap.findMin().duplicate(depth + 1);

					Token robot = gameboardClone.getRobots().get(i);
					//insert the gameboard only if the robot moved
					if (gameboardClone.moveUntilWall(robot, d) != 0) {
						//win condition
						if (gameboardClone.getDistanceToObjective() == 0) {
							solution = gameboardClone;
							break LOOP;
						}
						binaryHeap.insert(gameboardClone);
					}
					//gameboardClone.displayBoard();
					//System.out.println("distanceToObjective: " + gameboardClone.getDistanceToObjective());
				}
			}
			binaryHeap.deleteMin();
			depth = binaryHeap.findMin().getDepth();
			distanceToObjective = binaryHeap.findMin().getDistanceToObjective();
			RAM = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000;
		}

		if(nbIter< maxIter && RAM < MAX_RAM) {
			//HighLevelGameboard solution = binaryHeap.findMin();
			assert solution != null;
			for (ArrayList<Token> move : solution.getPreviousMoves()) {
				HighLevelGameboard board = new HighLevelGameboard(move, 0, solution.getPreviousMoves());
				//board.displayBoard();
				//System.out.println("distanceToObjective: " + board.getRobotSeekingObjective().getDistanceToObjective());
				solutionList.add(board);
			}
			solutionList.add(solution);
			depth = solutionList.size()-1;
			message = "iterations : " + nbIter + "\ndepth : " + depth;
		}else {
			message = "search failed\ndepth : " + depth;
			depth = -1;
		}
		System.gc();

		return solutionList;
	}
}
