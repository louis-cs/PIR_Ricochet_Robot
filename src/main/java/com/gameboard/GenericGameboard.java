package com.gameboard;

import java.util.HashSet;

public abstract class GenericGameboard {

    public abstract Cell getCell(int x, int y);

    public abstract GenericGameboard getGameboard();

    public abstract void displayBoard();

    public abstract HashSet<Direction> getWalls(int x, int y);
}
