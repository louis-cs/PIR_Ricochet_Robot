package com.gameboardui;


import com.gameboard.Coordinates;
import com.gameboard.Direction;
import com.gameboard.HighLevelGameboard;
import com.gameboard.Token;
import com.graph.struct.TreeSearch;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
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

    public GridPane gridpaneUI;
    public Label stepLabel;
    public Label label;
    public TextField seedTextField;
    public RadioButton breadthFirstButton;
    public RadioButton bestFirstButton;
    public Button backwardButton;
    public Button forwardButton;
    public TextField RamTextField;
    public ScatterChart<Integer,Integer> chart;
    public ToggleGroup toggleGroup;
    public RadioButton AStarButton;
    public RadioButton MonteCarloButton;

    private Random random = new Random();
    private int seed = random.nextInt();

    private HighLevelGameboard gameboard = new HighLevelGameboard(false, seed);
    private ArrayList<HighLevelGameboard> solutionList = new ArrayList<>();
    private HighLevelGameboard gameboardSave = gameboard.duplicate(0); //pour reset tu re-duplicate la save dans le gameboard
    private ArrayList<Coordinates> highlighted = new ArrayList<>();
    private Token robotHighlighted;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        HighLevelGameboard.solvingMethod = HighLevelGameboard.solvingMethods.breadthFirst;
        seedTextField.setText(String.valueOf(seed));
        RamTextField.setText(String.valueOf(TreeSearch.MAX_RAM));
        update();

        chart.getXAxis().setAutoRanging(true);
        chart.getXAxis().setLabel("Depth");
        chart.getYAxis().setAutoRanging(true);
        chart.getYAxis().setLabel("Time in ms");
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
        seedTextField.setText(String.valueOf(seed));
        reset();
        backwardButton.setDisable(false);
        forwardButton.setDisable(false);
        TreeSearch t = new TreeSearch();
        solutionList = t.search(gameboard);
        label.setText(TreeSearch.message);
        if(label.getText().contains("search failed"))
        {
            backwardButton.setDisable(true);
            forwardButton.setDisable(true);
        }
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
            update();
        }
    }

    public void reverseMove() {
        int move = Integer.parseInt(stepLabel.getText());
        if(!solutionList.isEmpty() && move>0) {
            move--;
            stepLabel.setText(String.valueOf(move));
            gameboard = solutionList.get(move);
            update();
        }
    }

    public void generateRandom() {
        backwardButton.setDisable(true);
        forwardButton.setDisable(true);
        seed = random.nextInt();
        seedTextField.setText(String.valueOf(seed));
        stepLabel.setText("0");
        solutionList.clear();
        label.setText("");
        gameboard = new HighLevelGameboard(false, seed);
        gameboardSave = gameboard.duplicate(0);
        update();
    }

    public void seedChanged() {
        String text = seedTextField.getText();
        if(!text.equals("")) {
            seed = Integer.parseInt(text);
            gameboard = new HighLevelGameboard(false, seed);
            gameboardSave = gameboard.duplicate(0);
            update();
        }
        else{
            seed = random.nextInt();
        }
    }

    public void treeSearchModeChanged() {
        if(breadthFirstButton.isSelected())
            HighLevelGameboard.solvingMethod = HighLevelGameboard.solvingMethods.breadthFirst;
        else if(bestFirstButton.isSelected())
            HighLevelGameboard.solvingMethod = HighLevelGameboard.solvingMethods.bestFirst;
        else if(AStarButton.isSelected())
            HighLevelGameboard.solvingMethod = HighLevelGameboard.solvingMethods.AStar;
        else
            HighLevelGameboard.solvingMethod = HighLevelGameboard.solvingMethods.MonteCarlo;
    }

    public void evaluatePerformance() {
        int avrgDepth=0, succesRate=0, nb=20, avrgTime = 0;
        TreeSearch t = new TreeSearch();

        //---------------------CHART----------------------
        XYChart.Series<Integer,Integer> series = new XYChart.Series<>();
        XYChart.Series<Integer,Integer> seriesFailures = new XYChart.Series<>();
        if(breadthFirstButton.isSelected()) {
            series.setName("Breadth First");
            seriesFailures.setName("Breadth First Failures");
        }
        else if(bestFirstButton.isSelected()){
            series.setName("Best First");
            seriesFailures.setName("Best First Failures");
        }
        else if(AStarButton.isSelected()) {
            series.setName("A*");
            seriesFailures.setName("A* Failures");
        }
        else {
            series.setName("Monte Carlo");
            seriesFailures.setName("Monte Carlo Failures");
        }

        random = new Random(seed);
        for(int i=0; i<nb; i++){
            gameboard = new HighLevelGameboard(false, random.nextInt());

            long startTime = System.nanoTime();
            t.search(gameboard);
            long endTime = System.nanoTime();

            int time = (int) ((endTime - startTime)/1000000);  //divide by 1000000 to get milliseconds.
            avrgTime += time;
            //success
            if(TreeSearch.success) {
                avrgDepth += TreeSearch.depth;
                succesRate++;
                series.getData().add(new XYChart.Data<>(TreeSearch.depth, time));
            }
            else{
                seriesFailures.getData().add(new XYChart.Data<>(TreeSearch.depth, time));
            }
        }
        avrgTime /= nb;
        avrgDepth /= nb;
        succesRate *= 100/nb;

        label.setText("average time : "+avrgTime+"ms"+
                "\naverage depth : "+avrgDepth+
                "\nsuccess rate : "+succesRate+"%");
        RamTextField.setText(String.valueOf(TreeSearch.MAX_RAM));

        chart.getData().add(series);
    }

    public void performanceRamChanged() {
        String text = RamTextField.getText();
        if(!text.equals("")) {
            TreeSearch.MAX_RAM = Integer.parseInt(text);
        }
        else{
            TreeSearch.MAX_RAM = (Runtime.getRuntime().maxMemory()/2/1000000);
        }
    }

    public void clearGraph() {
        chart.getData().clear();
    }
}
