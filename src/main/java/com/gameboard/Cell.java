package com.gameboard;

import java.util.HashSet;

public class Cell {

    private HashSet<Direction> walls ;
    private int distanceObjective = Integer.MAX_VALUE;

    public Cell() {
        walls = new HashSet<>();
    }

    public void addWall(Direction d){
        walls.add(d) ;
    }

    public boolean containsWall(Direction d) {
        return walls.contains(d);
    }

    public HashSet<Direction> getWalls() {
        return walls;
    }

    public void setDistanceObjective(int d){
        distanceObjective=d;
    }

    public int getDistanceObjective() {
        return distanceObjective;
    }
}
