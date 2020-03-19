package com.graph.struct;

import com.gameboard.Direction;
import com.gameboard.HighLevelGameboard;
import com.gameboard.Token;

public class TreeSearch{

	private static BinaryHeap<HighLevelGameboard> binaryHeap = new BinaryHeap<>();

	public static void search(HighLevelGameboard gameboard) {
		gameboard.displayDistanceToObjective();
		gameboard.displayBoard();
		System.out.println("distanceToObjective: " + gameboard.getDistanceToObjective());
		binaryHeap.insert(gameboard);

		int depth = gameboard.getDepth(), distanceToObjective = Integer.MAX_VALUE, x=0;
		while (depth<10 && distanceToObjective!=0 && x<100000){x++;

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

		binaryHeap.findMin().displayBoard();
		System.out.println("distanceToObjective: " + binaryHeap.findMin().getDistanceToObjective());
		System.out.println("nombre d'iterations: "+x);
		System.out.print("depth: "+depth);
	}
}
