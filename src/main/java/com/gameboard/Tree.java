package com.gameboard;

public class Tree {
	private Node root;

	public Tree(HighLevelGameboard gameboard){
		State s = new State();
		s.setGameboard(gameboard);
		root = new Node(s);
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public Node getRoot() {
		return root;
	}
}
