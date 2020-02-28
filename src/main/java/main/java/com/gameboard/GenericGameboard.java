package main.java.com.gameboard;

public abstract class GenericGameboard {

    public abstract Cell getCell(int x, int y);

    public abstract GenericGameboard getGameboard();

    public abstract void display();
}
