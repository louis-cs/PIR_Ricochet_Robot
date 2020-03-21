package com.gameboardui;

import com.gameboard.Coordinates;
import com.gameboard.Direction;
import com.gameboard.HighLevelGameboard;
import com.gameboard.Token;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class ControllerPaneGUI extends Pane {

    private static ControllerMainGUI controller;

    ControllerPaneGUI(ControllerMainGUI controllerParam, HighLevelGameboard gameboard, int i, int j, Background background) {
        setBackground(background);

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
        setBorder(new Border(new BorderStroke(TOP, RIGHT, BOTTOM, LEFT, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, new CornerRadii(0), BorderWidths.DEFAULT, new Insets(0, 0, 0, 0))));

        //OBJECTIVE
        Coordinates objective = gameboard.getObjective().getCoordinates();
        if(objective.getX()==i && objective.getY()==j) {
            java.awt.Color awt = gameboard.getObjective().getColor();
            Paint p = Color.rgb(awt.getRed(), awt.getGreen(), awt.getBlue());
            Rectangle r = new Rectangle(38, 38, p);
            getChildren().add(r);
        }

        //ROBOT
        Token robot = gameboard.isThereARobot(i, j);
        if (robot != null) {
            Paint p = Color.rgb(robot.getColor().getRed(), robot.getColor().getGreen(), robot.getColor().getBlue());
            Circle c = new Circle(20, 20, 15, p);
            getChildren().add(c);
        }
        setOnMousePressed(e -> controller.chooseDirection(new Coordinates(i,j)));
    }

    public void a(){
        System.out.println("a");
    }
}
