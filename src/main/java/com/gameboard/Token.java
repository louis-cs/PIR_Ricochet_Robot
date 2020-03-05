package com.gameboard;

import java.awt.*;

public class Token {
	private Coordinates c;
	private Color color;

	public Token(Coordinates c, Color color){
		this.c=c;
		this.color=color;
	}

	public Coordinates getCoordinates(){
		return this.c;
	}

	public Color getColor(){
		return this.color;
	}

	public void setCooordinates(Coordinates c) {
		this.c = c;
	}
}
