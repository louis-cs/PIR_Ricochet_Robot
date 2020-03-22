package com.gameboardui;


import com.gameboard.Coordinates;
import com.gameboard.Direction;
import com.gameboard.HighLevelGameboard;
import com.gameboard.Token;
import com.graph.struct.TreeSearch;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;


public class ControllerMainGUI implements Initializable {
    @FXML
    public GridPane gridpaneUI;
    @FXML
    public Label stepLabel;
    @FXML
    public Label treeSearchLabel;
    public TextField seedTextField;
    public RadioButton depthFirstButton;
    public RadioButton bestFirstButton;

    private Random random = new Random();
    private int seed = random.nextInt();

    private HighLevelGameboard gameboard = new HighLevelGameboard(false, seed);
    private ArrayList<HighLevelGameboard> solutionList = new ArrayList<>();
    private HighLevelGameboard gameboardSave = gameboard.duplicate(0); //pour reset tu re-duplicate la save dans le gameboard
    private ArrayList<Coordinates> highlighted = new ArrayList<>();
    private Token robotHighlighted;

    /*
    la fonction search te rends une arraylist de gameboards
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HighLevelGameboard.setDepthFirst(false);
        seedTextField.setText(String.valueOf(seed));
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
            stepLabel.setText(String.valueOf(Integer.parseInt(stepLabel.getText())+1));
        }

        //there is a robot
        if(robot!=null){
            highlighted = gameboard.potentialMoves(c);
            robotHighlighted=robot;
        }

        update();
    }

    public void treeSearch() {
        reset();
        TreeSearch t = new TreeSearch();
        solutionList = t.search(gameboard);
        treeSearchLabel.setText(TreeSearch.getMessage());
        System.gc();
    }

    public void reset() {
        gameboard = gameboardSave.duplicate(0);
        update();
        stepLabel.setText("0");
    }

    public void forwardMove() {
        int move = Integer.parseInt(stepLabel.getText());
        if(solutionList.size()-1>move) {
            move++;
            stepLabel.setText(String.valueOf(move));
            gameboard = solutionList.get(move);
            System.out.println(move);
            update();
        }
    }

    public void reverseMove() {
        int move = Integer.parseInt(stepLabel.getText());
        if(!solutionList.isEmpty() && move>0) {
            move--;
            stepLabel.setText(String.valueOf(move));
            gameboard = solutionList.get(move);
            System.out.println(move);
            update();
        }
    }

    public void generateRandom() {
        seed = random.nextInt();
        seedTextField.setText(String.valueOf(seed));
        stepLabel.setText("0");
        solutionList.clear();
        treeSearchLabel.setText("");
        gameboard = new HighLevelGameboard(false, seed);
        gameboardSave = gameboard.duplicate(0);
        update();
    }

    public void seedChanged(KeyEvent keyEvent) {
        //if user typed ENTER and textfield not empty
        if(keyEvent.getCode().equals(KeyCode.ENTER) && !seedTextField.getText().equals("")) {
            seed = Integer.parseInt(seedTextField.getText());
            gameboard = new HighLevelGameboard(false, seed);
            gameboardSave = gameboard.duplicate(0);
            update();
        }
    }

    public void treeSearchModeChanged() {
        if(depthFirstButton.isSelected())
            HighLevelGameboard.setDepthFirst(true);
        else
            HighLevelGameboard.setDepthFirst(false);
    }
}
