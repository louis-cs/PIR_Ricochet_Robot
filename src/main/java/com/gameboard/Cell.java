package main.java.com.gameboard;

import java.util.HashSet;
import java.util.Set;

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
