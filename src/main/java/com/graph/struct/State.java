package com.graph.struct;

import com.gameboard.Direction;
import com.gameboard.HighLevelGameboard;
import com.gameboard.Token;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class State {

	private HighLevelGameboard gameboard;
	private int visitCount;
	private int playoutsResult;

	private static final Random random = new Random();

	public void incrementVisit() {
		this.visitCount++;
	}

	public void setGameboard(HighLevelGameboard gameboard) {
		this.gameboard = gameboard;
		this.playoutsResult = -gameboard.getDistanceToObjective();
	}

	public void setVisitCount(int visitCount) {
		this.visitCount = visitCount;
	}

	public void addScore(int playoutResult) {
		this.playoutsResult -= playoutResult;
	}

	public double getWinScore() {
		return playoutsResult;
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

				HighLevelGameboard duplicate = gameboard.duplicate(depth + 1);
				Token robot = duplicate.getRobots().get(i);

				//if the robot moved
				if(duplicate.moveUntilWall(robot, d) != 0) {
					State s = new State();
					s.setGameboard(duplicate);
					ret.add(s);
				}
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
