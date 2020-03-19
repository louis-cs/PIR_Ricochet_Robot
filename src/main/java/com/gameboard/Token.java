package com.gameboard;

import java.awt.*;
import java.util.ArrayList;

public class Token implements Comparable<Token>{
	private Coordinates c;
	private Color color;
	private int distanceToObjective;

	public Token(Coordinates c, Color color, int distanceToObjective){
		this.c=c;
		this.color=color;
		this.distanceToObjective = distanceToObjective;
	}

	public Token duplicate(){
		return new Token(c.duplicate(), color, distanceToObjective);
	}

	public Coordinates getCoordinates(){
		return this.c;
	}

	public Color getColor(){
		return this.color;
	}

	public void setCooordinates(Coordinates c, int distanceToObjective) {
		this.c = c;
		this.distanceToObjective=distanceToObjective;
	}

	public int getDistanceToObjective(){
		return distanceToObjective;
	}

	@Override
	public int compareTo(Token token) {
		return distanceToObjective-token.getDistanceToObjective();
/*
		if(diff==0){
			if(this.color == token.getColor())
				return 0;
			else
				return (this.color.getRGB() - token.getColor().getRGB());
		}
		else
			return diff;*/
	}
}
