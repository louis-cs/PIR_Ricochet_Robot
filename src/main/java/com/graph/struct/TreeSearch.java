package com.graph.struct;

import com.gameboard.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TreeSearch{


	public static String message = "";
	public static long MAX_RAM = (Runtime.getRuntime().maxMemory()/1000000)/10;
	public static int depth = 0;
	public static boolean success = false;

	/**
	 * tries to find a solution to a specific game and prints the solution
	 */
	public ArrayList<HighLevelGameboard> search(HighLevelGameboard gameboard) { //stopper l'algo quand on arrive à l'objectif

		BinaryHeap<HighLevelGameboard> binaryHeap = new BinaryHeap<>();
		binaryHeap.insert(gameboard);
		ArrayList<HighLevelGameboard> solutionList = new ArrayList<>();
		HighLevelGameboard solution = null;
		depth = gameboard.getDepth();

		long RAM = 0;
		int distanceToObjective = Integer.MAX_VALUE, nbIter = 0;

		LOOP:
		while (distanceToObjective != 0 && RAM < MAX_RAM) {
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
							depth = solution.getDepth();
							//System.out.println("finito" + depth);
							break LOOP;
						}
						binaryHeap.insert(gameboardClone);
					}
					//gameboardClone.displayBoard();
					//System.out.println("distanceToObjective: " + gameboardClone.getDistanceToObjective());
				}
			}
			binaryHeap.deleteMin();
			solution = binaryHeap.findMin();
			depth = solution.getDepth();
			distanceToObjective = solution.getDistanceToObjective();
			RAM = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000;
		}

		//distance to objective should never be 0 if functioning right
		if(RAM < MAX_RAM || distanceToObjective==0) {

			for (ArrayList<Token> move : solution.getPreviousMoves()) {
				HighLevelGameboard board = new HighLevelGameboard(move, 0, solution.getPreviousMoves());
				//board.displayBoard();
				//System.out.println("distanceToObjective: " + board.getRobotSeekingObjective().getDistanceToObjective());
				solutionList.add(board);
			}
			solutionList.add(solution);
			//System.out.println("depth:"+depth+" solution size:"+(solutionList.size()-1));
			//depth = solutionList.size()-1;
			message = "iterations : " + nbIter + "\ndepth : " + depth;
			success = true;
		}else {
			message = "search failed\ndepth : " + depth;
			success = false;
		}
		System.gc();

		return solutionList;
	}

	public ArrayList<HighLevelGameboard> findNextMove(HighLevelGameboard gameboard) {

		Tree tree = new Tree(gameboard);
		Node rootNode = tree.getRoot();

		long RAM = 0;
		int nbIter = 0;
		Node winnerNode = null;
		//System.out.println("RAM" + RAM + "MAX_RAM" + MAX_RAM);
		while (RAM < MAX_RAM && winnerNode == null) {
			nbIter++;
			Node promisingNode = selectPromisingNode(rootNode);
			if (promisingNode.getState().getGameboard().getDistanceToObjective() == 0)//WIN CONDITION
				winnerNode = promisingNode;
			else
				expandNode(promisingNode);

			Node nodeToExplore = promisingNode;
			if (promisingNode.getChildArray().size() > 0) {
				nodeToExplore = promisingNode.getRandomChildNode();
			}
			int playoutResult = simulateRandomPlayout(nodeToExplore);
			backPropogation(nodeToExplore, playoutResult);

			RAM = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000;
		}

		System.gc();

		ArrayList<HighLevelGameboard> solutionList = new ArrayList<>();

		if(winnerNode == null){
			TreeSearch.message = "search failed";
			return solutionList;
		}

		TreeSearch.message = "iterations : " + nbIter + "\ndepth : " + depth;
		solutionList.add(winnerNode.getState().getGameboard());
		while(winnerNode!=rootNode){
			winnerNode = winnerNode.getParent();
			solutionList.add(winnerNode.getState().getGameboard());
		}
		Collections.reverse(solutionList);
		return solutionList;
	}

	private Node selectPromisingNode(Node rootNode) {
		Node node = rootNode;
		while (node.getChildArray().size() != 0) {
			node = UCT.findBestNodeWithUCT(node);
		}
		return node;
	}

	private void expandNode(Node node) {
		List<State> possibleStates = node.getState().getAllPossibleStates();
		possibleStates.forEach(state -> {
			Node newNode = new Node(state);
			newNode.setParent(node);
			node.getChildArray().add(newNode);
		});
	}

	private void backPropogation(Node nodeToExplore, int playoutResult) {
		Node tempNode = nodeToExplore;
		while (tempNode != null) {
			tempNode.getState().incrementVisit();
			tempNode.getState().addScore(playoutResult);
			tempNode = tempNode.getParent();
		}
	}

	private int simulateRandomPlayout(Node node) {
		Node tempNode = new Node(node);
		State tempState = tempNode.getState();

		int i=0;
		while (tempState.getGameboard().getDistanceToObjective() != 0 || i<4) {
			i++;
			tempState.randomPlay();
		}
		return tempState.getGameboard().getDistanceToObjective();
	}
}
