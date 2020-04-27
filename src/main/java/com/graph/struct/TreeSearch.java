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

	public ArrayList<HighLevelGameboard> search(HighLevelGameboard gameboard){
		if(HighLevelGameboard.solvingMethod == HighLevelGameboard.solvingMethods.MonteCarlo)
			return monteCarloSearch(gameboard);
		else
			return newSearch(gameboard);
	}

	/**
	 * tries to find a solution to a specific game and prints the solution
	 * @param gameboard The board to start the search on
	 * @return A list of every move to get to the objective
	 */
	public ArrayList<HighLevelGameboard> newSearch(HighLevelGameboard gameboard){

		Tree tree = new Tree(gameboard);
		Node winnerNode = null;
		BinaryHeap<Node> binaryHeap = new BinaryHeap<>();
		binaryHeap.insert(tree.getRoot());
		depth = gameboard.getDepth();

		long RAM = 0;
		int nbIter = 0;

		while (RAM < MAX_RAM && winnerNode == null) {
			nbIter++;
			Node bestNode = binaryHeap.deleteMin();
			expandNode(bestNode);
			for(Node child : bestNode.getChildArray()) {
				if(child.getState().getGameboard().getDistanceToObjective()==0) {//WIN CONDITION
					winnerNode = child;
				}
				binaryHeap.insert(child);
			}
			depth = bestNode.getState().getGameboard().getDepth();
			RAM = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000;
		}
		System.gc();

		ArrayList<HighLevelGameboard> solutionList = new ArrayList<>();

		if(winnerNode == null){
			message = "search failed\ndepth : " + depth+1;
			success = false;
			System.gc();
			return solutionList;
		}

		message = "iterations : " + nbIter + "\ndepth : " + depth+1;
		success = true;
		solutionList.add(winnerNode.getState().getGameboard());
		while(winnerNode!=tree.getRoot()){
			winnerNode = winnerNode.getParent();
			solutionList.add(winnerNode.getState().getGameboard());
		}
		Collections.reverse(solutionList);
		System.gc();
		return solutionList;
	}

	public ArrayList<HighLevelGameboard> monteCarloSearch(HighLevelGameboard gameboard) {

		Tree tree = new Tree(gameboard);
		Node rootNode = tree.getRoot();
		Node winnerNode = null;

		long RAM = 0;
		int nbIter = 0;

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
				//nodeToExplore = promisingNode.getChildWithBestScore();
			}
			int playoutResult = simulateRandomPlayout(nodeToExplore);
			backPropogation(nodeToExplore, playoutResult);

			RAM = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1000000;
			depth = promisingNode.getState().getGameboard().getDepth();
		}


		ArrayList<HighLevelGameboard> solutionList = new ArrayList<>();

		if(winnerNode == null){
			message = "search failed";
			success = false;
			System.gc();
			return solutionList;
		}

		message = "iterations : " + nbIter + "\ndepth : " + depth;
		success = true;
		solutionList.add(winnerNode.getState().getGameboard());
		while(winnerNode!=rootNode){
			winnerNode = winnerNode.getParent();
			solutionList.add(winnerNode.getState().getGameboard());
		}
		Collections.reverse(solutionList);
		System.gc();
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
