package com.gameboard;

import java.util.HashSet;

public class Cell {

    private HashSet<Direction> walls ;

    public Cell() {
        walls = new HashSet<>();
    }

    public void addWall(Direction d){
        walls.add(d) ;
    }

    public boolean PossibleMove(Direction d) {
        return walls.contains(d);
    }

    public HashSet<Direction> getWalls() {
        return walls;
    }
}
