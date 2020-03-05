package com.gameboard;

import java.awt.*;
import java.util.ArrayList;

public class Token implements Comparable<Token>{
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

	@Override
	public int compareTo(Token token) {
		int coords = this.c.compareTo(token.getCoordinates());

		if(coords==0){
			if(this.color == token.getColor())
				return 0;
			else
				return (this.color.getRGB() - token.getColor().getRGB());
		}
		else
			return coords;
	}
}
