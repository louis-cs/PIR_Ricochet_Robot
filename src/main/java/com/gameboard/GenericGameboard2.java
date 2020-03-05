package com.gameboard;

import java.util.ArrayList;
import java.util.HashSet;

public interface GenericGameboard2 {

    public abstract HashSet<Direction> getWalls(int x, int y);

    public abstract ArrayList<Token> getRobots();

    public abstract Token getObj();

    public abstract GenericGameboard getGameboard();

    public abstract void display();
}
