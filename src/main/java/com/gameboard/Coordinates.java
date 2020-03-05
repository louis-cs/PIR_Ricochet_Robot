package com.gameboard;

public class Coordinates {

	int x, y;

	public Coordinates(int x, int y){
		this.x=x;
		this.y=y;
	}

	public Coordinates(){
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	public void move(Direction d){
		switch (d){
			case up:
				this.x--;
				break;
			case right:
				this.y++;
				break;
			case down:
				this.x++;
				break;
			case left:
				this.y--;
				break;
		}
	}
}
