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
    private Rectangle r;
    private Paint pRec, pToken;
    private Circle c;

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
                    TOP = Color.BLACK;
                    break;
                case right:
                    RIGHT = Color.BLACK;
                    break;
                case down:
                    BOTTOM = Color.BLACK;
                    break;
                case left:
                    LEFT = Color.BLACK;
                    break;
            }
        }
        setBorder(new Border(new BorderStroke(TOP, RIGHT, BOTTOM, LEFT, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, BorderStrokeStyle.SOLID, new CornerRadii(0), BorderWidths.DEFAULT, new Insets(1, 1, 1, 1))));

        //OBJECTIVE
        Coordinates objective = gameboard.getObjective().getCoordinates();
        if(objective.getX()==i && objective.getY()==j) {
            java.awt.Color awt = gameboard.getObjective().getColor();
            pRec = Color.rgb(awt.getRed(), awt.getGreen(), awt.getBlue());
            r = new Rectangle(38, 38, pRec);//valeurs inutiles à part la paint
            getChildren().add(r);
        }

        //ROBOT
        Token robot = gameboard.isThereARobot(i, j);
        if (robot != null) {
            pToken = Color.rgb(robot.getColor().getRed(), robot.getColor().getGreen(), robot.getColor().getBlue());
            c = new Circle(20, 20, 15, pToken);//valeurs inutiles à part la paint
            getChildren().add(c);
        }
        setOnMousePressed(e -> {
            controller.chooseDirection(new Coordinates(i,j));
        });

        heightProperty().addListener((obs, oldVal, newVal) -> {
            update();
        });
        widthProperty().addListener((obs, oldVal, newVal) -> {
            update();
        });
    }
    private void update(){
        if(r!=null) {
            r.setWidth(getWidth()-2);
            r.setHeight(getHeight()-2);
            r.setX(1);
            r.setY(1);
        }
        if(c!=null){
            c.setCenterX(getWidth()/2);
            c.setCenterY(getHeight()/2);
            c.setRadius((getHeight()+getWidth())/4-2);
        }
    }
}
