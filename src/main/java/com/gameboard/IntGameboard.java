package com.gameboard;

import java.util.ArrayList;
import java.util.HashSet;

public class IntGameboard implements GenericGameboard2 {

    private int max=16;
//    private ArrayList<ArrayList<Short>> grid;
    private ArrayList<Integer> grid;
    private ArrayList<Token> robots;
    private Token Obj;

    public IntGameboard() {
        initGameboard();
    }

    private void initGameboard() {
//        this.grid = new ArrayList<>();
//
 //       for(int i=0; i<max; i++){
 //           this.grid.add(new ArrayList<>());
 //           for(int j=0; j<max; j++){
 //               this.grid.get(i).add((short) 0);
 //           }
 //       }
        this.grid = new ArrayList<>();
        for(int i=0; i<max; i++)
                this.grid.add(0);
    }

    @Override
    public HashSet<Direction> getWalls(int x, int y) {
        HashSet Walls = new HashSet();
        if (x==15)
            Walls.add(Direction.down);
        if (y==15)
            Walls.add(Direction.right);
        int calc = (this.grid.get(x) << (((max-1) - y) * 2)) >> 30;
        if (calc % 1 == 1)
            Walls.add(Direction.left);
        if (calc / 2 == 1)
            Walls.add(Direction.up);

        return Walls;
    }

    @Override
    public ArrayList<Token> getRobots() {
        return robots;
    }

    @Override
    public Token getObj() {
        return Obj;
    }

    @Override
    public GenericGameboard getGameboard() {
        return null; //Meme chose si jte renvoie ma grille tu ssaura pas la manipuler avec tes fonctions pour le truc de charle
    }

    @Override
    public void display() {

    }
}
