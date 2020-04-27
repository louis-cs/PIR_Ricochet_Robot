package com.graph.struct;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Random;

public class Node implements Comparable<Node>{
	private State state;
	private Node parent;
	private List<Node> childArray = new ArrayList<>();

	private static final Random random = new Random();

	public Node(State state){
		this.state = state;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public State getState(){
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

	public Node getRandomChildNode() {
		return childArray.get(random.nextInt(childArray.size()));
	}

	@Override
	public int compareTo(Node node) {
		return state.compareTo(node.getState());
	}
}
