package com.example.stickhero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.TimeUnit;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader1 = new FXMLLoader(SceneController.class.getResource("StartScene.fxml"));
        Scene scene1 = new Scene(fxmlLoader1.load(), 400, 600);
        stage.setTitle("STICK HERO");
        stage.setScene(scene1);
        stage.show();


    }
}