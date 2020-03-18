package com.gameboard;

public enum Direction{
    up ,
    right ,
    down ,
    left;

    public Direction getOppposite(){
        switch(this){
            case up: return down;
            case right: return left;
            case down: return up;
            case left: return right;
        }
        return null;
    }
}
