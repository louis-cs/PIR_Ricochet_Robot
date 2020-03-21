package com.gameboardui;


import com.gameboard.Coordinates;
import com.gameboard.Direction;
import com.gameboard.HighLevelGameboard;
import com.gameboard.Token;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ControllerMainGUI implements Initializable {
    @FXML
    public GridPane gridpaneUI;

    HighLevelGameboard gameboard = new HighLevelGameboard(false);
    HighLevelGameboard gameboardSave = gameboard.duplicate(0); //pour reset tu re-duplicate la save dans le gameboard
    ArrayList<Coordinates> highlighted = new ArrayList<>();
    Token robotHighlighted;

    /*
    la fonction search te rends une arraylist de gameboards
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update();
    }

    @FXML
    private void update(){
        gridpaneUI.setGridLinesVisible(false);
        gridpaneUI.getChildren().clear();
        gridpaneUI.setGridLinesVisible(true);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                Color c = Color.WHITE;
                for(Coordinates h : highlighted){
                    if (h.getX() == j && h.getY() == i) {
                        c = Color.LIGHTYELLOW;
                        break;
                    }
                }
                Background background = new Background(new BackgroundFill(c, new CornerRadii(0), new Insets(1,1,1,1)));
                ControllerPaneGUI pane = new ControllerPaneGUI(this,gameboard, j, i, background);
                gridpaneUI.add(pane, i, j);
            }
        }
    }

    public void chooseDirection(Coordinates c){
        Token robot = gameboard.isThereARobot(c.getX(), c.getY());

        //no robot, either direction or nothing
        if(robotHighlighted!=null){
            Direction d = null;
            if (c.getX() == robotHighlighted.getCoordinates().getX()) {//up or down
                if (c.getY() > robotHighlighted.getCoordinates().getY())
                    d = Direction.right;
                else
                    d = Direction.left;
            }
            if (c.getY() == robotHighlighted.getCoordinates().getY()) {//up or down
                if (c.getX() > robotHighlighted.getCoordinates().getX())
                    d = Direction.down;
                else
                    d = Direction.up;
            }

            if (d != null)
                gameboard.moveUntilWall(robotHighlighted, d);

            highlighted.clear();
            robotHighlighted = null;
        }

        //there is a robot
        if(robot!=null){
            highlighted = gameboard.potentialMoves(c);
            robotHighlighted=robot;
        }

        update();
    }

    public void trouvelasolution() {
        System.out.println("J'ai trouvé");
    }
}
