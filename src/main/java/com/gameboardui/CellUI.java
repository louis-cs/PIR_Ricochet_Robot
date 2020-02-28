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
    private static final int width = 45;
    private static final int height = 45;

    HashSet<Direction> walls;

    CellUI(GenericGameboard gameboard, int i, int j) {
        //walls = gameboard.getWalls(i,j);

        // TEST WALLS
        walls = new HashSet<>();
        walls.add(Direction.up);
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
                    g.drawLine(0, height, width, height);
                    break;
                case down:
                    g.drawLine(0, 0, width, 0);
                    break;
                case left:
                    g.drawLine(0, 0, 0, height);
                    break;
                case right:
                    g.drawLine(width, 0, width, height);
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
