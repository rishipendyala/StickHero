package com.example.stickhero;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(SceneController.class.getResource("StartScene.fxml"));
        fxmlLoader.setController(new StartSceneController());
        Scene scene = new Scene(fxmlLoader.load(), 400, 600);
        stage.setTitle("STICK HERO");
        stage.setScene(scene);
        stage.show();
    }

}
