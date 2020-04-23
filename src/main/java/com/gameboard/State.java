package com.gameboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class State {
	HighLevelGameboard gameboard;
	int visitCount;

	private static final Random random = new Random();

	public void incrementVisit() {
		this.visitCount++;
	}

	public void setGameboard(HighLevelGameboard gameboard) {
		this.gameboard = gameboard;
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}

	public double getWinScore() {
		return -gameboard.getDistanceToObjective();
	}

	public int getVisitCount() {
		return visitCount;
	}

	public HighLevelGameboard getGameboard() {
		return gameboard;
	}

	public List<State> getAllPossibleStates() {
		int depth = gameboard.getDepth();
		List<State> ret = new ArrayList<>();
		for (int i = 0; i < HighLevelGameboard.nbRobots; i++) {
			for (Direction d : Direction.values()) {
				State s = new State();
				s.setGameboard(gameboard.duplicate(depth + 1));
				ret.add(s);
			}
		}
		return ret;
	}

	public void randomPlay() {
		gameboard.moveUntilWall(getRandomRobot(), getRandomDirection());
	}

	public Direction getRandomDirection(){

		Direction d = Direction.up;

		switch(random.nextInt(4)){
			case 0:
				d = Direction.up;
				break;
			case 1:
				d = Direction.right;
				break;
			case 2:
				d = Direction.down;
				break;
			case 3:
				d = Direction.left;
				break;
		}
		return d;
	}

	public Token getRandomRobot(){
		return gameboard.getRobots().get(random.nextInt(HighLevelGameboard.nbRobots));
	}

	public State duplicate(){
		State s = new State();
		s.setGameboard(gameboard.duplicate(gameboard.getDistanceToObjective()));
		s.setVisitCount(this.visitCount);
		return s;
	}

	public int compareTo(State state) {
		return this.gameboard.compareTo(state.getGameboard());
	}
}
