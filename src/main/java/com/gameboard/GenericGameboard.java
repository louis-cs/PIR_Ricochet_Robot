package main.java.com.gameboard;

abstract class GenericGameboard {

    abstract Cell getCell(int x, int y);

    abstract GenericGameboard getGameboard();

    abstract void display();

}
