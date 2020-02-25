package main.java.com.gameboard;

import javax.swing.*;
import java.awt.*;

public class GameUI {
    private static final int unitSize = 10;
    private static final int width = 45;
    private static final int height = 45;

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
        SwingUtilities.invokeLater(() -> {
            GenericGameboard board = new HighLevelGameboard(GameboardInstanceFactory.createGameInstance());
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
        CellUI[][] cells = new CellUI[9][9];
        JPanel[][] squares = new JPanel[3][3];

        for(int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                JPanel panel = new JPanel();
                panel.setLayout(new GridBagLayout());
                squares[i][j] = panel;
                top.add(panel);
            }
        }

        for (int i=0; i<9; i++) {
            for (int j=0; j<9; j++){
                CellUI cui = new CellUI(board.getCell(i,j));
                cells[i][j] = cui;
                addComponent(squares[i/3][j/3], cui, j%3, i%3, GridBagConstraints.CENTER, GridBagConstraints.BOTH);
            }
        }
    }

}