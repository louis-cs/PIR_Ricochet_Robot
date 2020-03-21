package com.gameboardui;


import com.gameboard.Coordinates;
import com.gameboard.HighLevelGameboard;
import com.gameboard.Token;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class ControllerGUI implements Initializable {
    @FXML
    public GridPane gridpaneUI;

    HighLevelGameboard gameboard = new HighLevelGameboard(true);
    ArrayList<Coordinates> highlighted = new ArrayList<>();

    public ControllerGUI() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        update();
    }

    public void updateAffichage() {
        update();
    }

    private void update(){
        gridpaneUI.setGridLinesVisible(false);
        gridpaneUI.getChildren().clear();
        gridpaneUI.setGridLinesVisible(true);
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                PaneGUI cui = new PaneGUI(this,gameboard, j, i);
                gridpaneUI.add(cui, i, j);
            }
        }
    }

    public void chooseDirection(Token robot){
        if(robot!=null){
            //highlighted = appel de la fonction qui renvoie toutes les coordonnées à colorier

        }
        if(robot==null && !highlighted.isEmpty()){
            //moveuntilWall dans la direction
            //update
            //clear Arraylist highlighted
        }
    }

    public void trouvelasolution() {
        System.out.println("J'ai trouvé");
    }
}
