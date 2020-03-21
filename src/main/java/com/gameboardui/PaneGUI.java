package com.gameboardui;

import com.gameboard.Direction;
import com.gameboard.HighLevelGameboard;
import com.gameboard.Token;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class PaneGUI extends Pane {

    private static ControllerGUI controller;

    PaneGUI(ControllerGUI controllerParam, HighLevelGameboard gameboard, int i, int j) {
        controller = controllerParam;
        Paint TOP = Color.gray(0, 0);
        Paint BOTTOM = Color.gray(0, 0);
        Paint RIGHT = Color.gray(0, 0);
        Paint LEFT = Color.gray(0, 0);

        for (Direction d : gameboard.getCell(i, j).getWalls()) {
            switch (d) {
                case up:
                    TOP = Color.RED;
                    break;
                case right:
                    RIGHT = Color.RED;
                    break;
                case down:
                    BOTTOM = Color.RED;
                    break;
                case left:
                    LEFT = Color.RED;
                    break;
            }
        }
        setBorder(new Border(new BorderStroke(TOP, RIGHT, BOTTOM, LEFT, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, new CornerRadii(1), BorderWidths.DEFAULT, new Insets(0, 0, 0, 0))));
        Token robot = gameboard.isThereARobot(i, j);
        if (robot != null) {
            Paint p = Color.rgb(robot.getColor().getRed(), robot.getColor().getGreen(), robot.getColor().getBlue());
            Circle c = new Circle(22, 22, 20, p);
            getChildren().add(c);
        }
        setOnMousePressed(e -> {
            System.out.println("i : " + i + " j  : " + j);
            controller.chooseDirection(robot);
            if (robot != null) {
                gameboard.moveUntilWall(robot, Direction.right);
            }
        });
    }
}
