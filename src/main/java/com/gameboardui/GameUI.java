package com.gameboardui;

import com.gameboard.Coordinates;
import com.gameboard.GenericGameboard;
import com.gameboard.HighLevelGameboard;
import com.gameboard.Token;

import javax.swing.*;
import java.awt.*;

public class GameUI {
    private static final int unitSize = 17;
    private static final int width = 30;
    private static final int height = 30;

    final JFrame top;
    private Insets insets = new Insets(0,0,0,0);


    public GameUI(GenericGameboard gameboard) {
        top = new JFrame("RICOCHET ROBOT BABYYYYYY");
        top.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        top.setLayout(new GridLayout(16, 16));

        Dimension dim = new Dimension(unitSize * width, unitSize * height);
        top.setMinimumSize(dim);
        top.setPreferredSize(dim);

        createCellUI(gameboard);

        top.pack();
        top.setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println("Affichage de gameboard");
        SwingUtilities.invokeLater(() -> {
            HighLevelGameboard board = new HighLevelGameboard(false);
            new GameUI(board);
        });
    }

    private void addComponent(Container container, Component component, int gridx, int gridy, int anchor, int fill){
        GridBagConstraints gbc = new GridBagConstraints(gridx,gridy,1,1,1.0,1.0,anchor,fill,insets,0,0);
        container.add(component,gbc);
    }

    /**
     * Create UI for cells
     * @param gameboard
     */
    private void createCellUI(GenericGameboard gameboard) {
        CellUI[][] cells = new CellUI[16][16];

        for (int i=0; i<16; i++) {
            for (int j=0; j<16; j++){
                CellUI cui = new CellUI(gameboard, i,j);
                cells[i][j] = cui;
                addComponent(top, cui, j, i, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
            }
        }
    }

}