package com.example.stickhero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GameScreenController implements Initializable {
    @FXML
    private ImageView background;
    @FXML
    private ImageView box;
    private int frameCount = 6;
    private int currentFrame = 0;
    private void animateSprite(){
        double frameWidth = 100.0; // Assuming each frame is 100x100 pixels
        double nextFrameX = (currentFrame % frameCount) * frameWidth;

        // Update the ImageView with the next frame
        box.setViewport(new javafx.geometry.Rectangle2D(nextFrameX, 0, frameWidth, 100));

        // Move to the next frame
        currentFrame = (currentFrame + 1) % frameCount;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image bg = new Image("background1.png");
        /*Image idlesheet = new Image("IDLE.png");
        box.setImage(idlesheet);
        Duration frameDuration = Duration.millis(100);
        Timeline timeline = new Timeline(
                new KeyFrame(frameDuration, event -> animateSprite())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();*/
        background.setImage(bg);
    }
}
