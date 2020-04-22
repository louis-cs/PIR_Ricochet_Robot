package com.gameboard;

import java.util.List;
import java.util.Collections;

public class Node implements Comparable<Node>{
	private HighLevelGameboard state;
	private Node parent;
	private List<Node> childArray;

	public HighLevelGameboard getState(){
		return state;
	}

	public Node getParent() {
		return parent;
	}

	public List<Node> getChildArray() {
		return childArray;
	}

	/**
	 * @return the child with the fewest points
	 */
	public Node getChildWithBestScore() {
		Collections.sort(childArray);
		return childArray.get(0);
	}

	@Override
	public int compareTo(Node node) {
		return state.compareTo(node.getState());
	}

	public void setState(HighLevelGameboard board) {
		state = board;
	}
}
