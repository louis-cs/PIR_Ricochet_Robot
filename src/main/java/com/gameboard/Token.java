package com.gameboard;

import java.awt.*;
import java.util.ArrayList;

public class Token implements Comparable<Token>{
	private Coordinates c;
	private Color color;
	private int distanceToObjective;

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
