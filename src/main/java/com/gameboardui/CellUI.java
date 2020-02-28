package com.gameboardui;

import com.gameboard.Cell;

import javax.swing.*;
import java.awt.*;

public class CellUI extends JTextField{

    /**
     * create a cell UI
     *
     * @param cell a cell model
     */
    private static final int width = 45;
    private static final int height = 45;

    CellUI(Cell cell) {
        initCellUI(cell);

        Dimension dim = new Dimension(width, height);
        this.setMinimumSize(dim);
        this.setPreferredSize(dim);
    }

    /**
     * Mark this cell UI as active
     */
//    public void setActivate() {
//        setBorder(BorderFactory.createLineBorder(Color.BLACK));
//        setEditable(true);
//    }

    /**
     * Mark this cell UI as inactive
     */
    public void setDeactivate() {
        setBorder(BorderFactory.createLineBorder(Color.RED));
        setEditable(false);
    }


    /**
     * Initialize this cell UI
     *
     * @param cell a cell model
     */
    private void initCellUI(Cell cell) {
        setFont(new Font("Times", Font.BOLD, 30));
        setHorizontalAlignment(JTextField.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
}
