package com.gameboardui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class MainGUI extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        URL url = Paths.get("./src/main/java/com/gameboardui/MainGUI.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(url);
        primaryStage.setMinWidth(750);
        primaryStage.setMinHeight(600);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }

}