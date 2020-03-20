package com.gameboardui;

import com.gameboard.Cell;
import com.gameboard.Direction;
import com.gameboard.GenericGameboard;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Iterator;

public class CellUI extends JPanel{

    /**
     * create a cell UI
     *
     * @param cell a cell model
     */
    private static final int width = 30;
    private static final int height = 30;

    private static final int wallThickness = 2;

    HashSet<Direction> walls;

    CellUI(GenericGameboard gameboard, int i, int j) {
        walls = gameboard.getWalls(i,j);

        initCellUI();

        Dimension dim = new Dimension(width, height);
        this.setMinimumSize(dim);
        this.setPreferredSize(dim);
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.setColor(Color.red);

        Iterator iter = walls.iterator();
        while (iter.hasNext()) {
            Direction dir = (Direction)iter.next();
            switch (dir) {
                case up:
                    g.fillRect(0,0,width,wallThickness);
                    break;
                case down:
                    g.fillRect(0, height-wallThickness, width, wallThickness);
                    break;
                case left:
                    g.fillRect(0, 0, wallThickness, height);
                    break;
                case right:
                    g.fillRect(width-wallThickness, 0, wallThickness, height);
                    break;
            }
        }
    }

    /**
     * Initialize this cell UI
     */
    private void initCellUI() {
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
}
