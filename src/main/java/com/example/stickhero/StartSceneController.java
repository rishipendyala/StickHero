package com.example.stickhero;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StartSceneController  implements Initializable {
    @FXML
    private ImageView characterImageView;
    @FXML
    private ImageView background;
    @FXML
    private Button next;
    private final int frameCount = 6;
    private int currentFrame = 0;
    private Stage stage = new Stage();
    private ArrayList<Image> imageList = new ArrayList<Image>();
    private static int counter = 0;
    public static Event Event_;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Image img = new Image("nextbutton.png");
        //ImageView view = new ImageView(img);
        //view.setFitHeight(55);
       // view.setFitWidth(55);
       // view.setPreserveRatio(true);
        //next.setGraphic(view);
        imageList.add(new Image("IDLE.png"));
        imageList.add(new Image("astro-idle.png"));
        imageList.add(new Image("mario-idle.png"));
        Image bg = new Image("background1.png");
        Image idlesheet = new Image("IDLE.png");
        characterImageView.setImage(idlesheet);
        Duration frameDuration = Duration.millis(100);
        Timeline timeline = new Timeline(
                new KeyFrame(frameDuration, event -> animateSprite())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        background.setImage(bg);
    }
    public void animateSprite(){
        double frameWidth = 100.0;
        double nextFrameX = (currentFrame % frameCount) * frameWidth;
        characterImageView.setViewport(new javafx.geometry.Rectangle2D(nextFrameX, 0, frameWidth, 100));
        currentFrame = (currentFrame + 1) % frameCount;
    }

    public void changeToGameScene(ActionEvent event) throws Exception {

        Event_ = event;
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Game.start(stage,counter);
    }
    public void changeSkin(ActionEvent event) throws Exception {
        System.out.println(counter);
        counter++;
        if (counter >= imageList.size()) {
            counter = 0;
        }
        characterImageView.setImage(imageList.get(counter));
    }

}