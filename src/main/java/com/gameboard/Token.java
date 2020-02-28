package com.gameboard;

import java.awt.*;

public class Token {
	private int x,y;
	private Color color;

	public Token(int x, int y, Color color){
		this.x=x;
		this.y=y;
		this.color=color;
	}

	public int getX(){
		return this.x;
	}

	public int getY(){
		return this.y;
	}

	public Color getColor(){
		return this.color;
	}


}
