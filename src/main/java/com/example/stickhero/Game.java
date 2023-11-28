package com.example.stickhero;

import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.concurrent.atomic.AtomicReference;

import static com.example.stickhero.Stick.startStickGrowth;
import static java.lang.Thread.sleep;

public class Game extends Application {

    private static final double FRAME_WIDTH = 100.0;
    private static final int FRAME_COUNT = 6;
    private static boolean spaceBarPressed = false;
    private static Rectangle curr = null;
    private static Rectangle secondary = null;
    private static Rectangle stick = null;
    private boolean cangrow = true;
    private static boolean StopRotation = true;
    private static ImageView sprite = new ImageView();
    private boolean isDead = false;
    private static boolean inverted = true;
    private static Scene scene;
    private static boolean isrunning = false;
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            startGame(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startGame(Stage stage) throws Exception {
        // Create the game scene
        ImageView bg = new ImageView(new Image("background1.png"));
        bg.setFitHeight(600.0);
        bg.setFitWidth(400.0);
        bg.setPickOnBounds(true);
        Updation t2 = new Updation();
        // Creating the first rectangle
        curr = new Rectangle();
        curr.setHeight(200);
        curr.setWidth(110);
        curr.setArcHeight(5.0);
        curr.setArcWidth(5.0);
        curr.setLayoutX(0);
        curr.setLayoutY(600 - curr.getHeight());
        curr.setFill(Color.web("#0d0d0d"));
        curr.setStroke(Color.RED);

        // Adding the sprite along with its animation
        sprite.setImage(new Image("IDLE.png"));
        sprite.setFitHeight(100);
        sprite.setFitWidth(100);

        sprite.setLayoutX(curr.getWidth() - 100);
        sprite.setLayoutY(600 - (curr.getHeight() + 75));
        if (secondary == null) {
            secondary = createBlock();
        }

        Timeline spriteAnimation = new Timeline(
                new KeyFrame(Duration.millis(100), event -> animateSprite(sprite))
        );

        spriteAnimation.setCycleCount(Timeline.INDEFINITE);
        spriteAnimation.play();

        scene = new Scene(new Group(bg, curr, sprite, secondary), 400, 600);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE && !spaceBarPressed) {
                if(!t2.isAlive() || !isrunning) {
                    spaceBarPressed = true;
                    if (stick == null) {
                        //translateBlocks();
                        startStickGrowth(scene, curr);
                    }
            }
        }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                cangrow = false;
                spaceBarPressed = false;
                t2.run();
            }
        });

        stage.setScene(scene);
        stage.show();
    }
    private static void moveSprite(){
        sprite.setImage(new Image("MOVE.png"));
        Timeline spriteAnimation = new Timeline(
                new KeyFrame(Duration.millis(100), event -> animateSprite(sprite))
        );

        spriteAnimation.setCycleCount(Timeline.INDEFINITE);
        spriteAnimation.play();
    }
    static void kickSprite(){
        sprite.setImage(new Image("KICK.png"));
        Timeline spriteAnimation = new Timeline(
                new KeyFrame(Duration.millis(100), event -> animateSprite(sprite))
        );

        spriteAnimation.setCycleCount(3);
        spriteAnimation.play();
    }

    private void startStickGrowth(Scene scene, Rectangle curr) {
        stick = new Rectangle();
        stick.setWidth(5);
        stick.setHeight(0);
        stick.setFill(Color.web("#0d0d0d"));
        stick.setArcWidth(5.0);
        stick.setLayoutX(sprite.getLayoutX() + sprite.getFitWidth() -2);
        stick.setLayoutY(curr.getLayoutY());

        Group root = (Group) scene.getRoot();
        root.getChildren().add(stick);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                    if (spaceBarPressed) {
                        stick.setHeight(stick.getHeight() + 2);
                        stick.setLayoutY(stick.getLayoutY() - 2);
                    }
                })
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    private static Rectangle updateBlock(Rectangle curr) {
        //double randomWidth = 200 - (secondary.getWidth() + 10);
        curr.setWidth(Math.random() * (200 - (sprite.getFitWidth())) + 10);

        double maxXLayout = 400 - curr.getWidth();
        curr.setLayoutX(Math.random() * maxXLayout);

        return curr;
    }
    private static int angle = 0;
    private static Timeline rotationTimeline = null;
    static void rotatestick() {
        isrunning = true;
        double pivotX = stick.getX() + stick.getWidth() / 2.0;
        double pivotY = stick.getY() + stick.getHeight();
        rotationTimeline = new Timeline(
                new KeyFrame(Duration.millis(5), event -> {
                    System.out.println("ANGLE:"+angle);
                    stick.getTransforms().clear();
                    stick.getTransforms().add(new javafx.scene.transform.Rotate(angle, pivotX, pivotY));
                    angle += 1;
                    if (angle > 90 && StopRotation) {
                        // Stop the rotation when the angle exceeds 90 degrees
                        stopRotation();
                        //StopRotation = false;

                        // Translate the sprite to the tip of the
                        double tip = stick.getHeight() ;
                        translateSpriteToTip(tip);


                    }
                })
        );

        rotationTimeline.setCycleCount(Timeline.INDEFINITE);
        rotationTimeline.setOnFinished(event -> isrunning=false );
        rotationTimeline.play();

    }

    private static void dies() {
        TranslateTransition death = new TranslateTransition(Duration.seconds(1),sprite);
        death.setToY(600);
        death.play();
    }

    private static void translateSpriteToTip(double tipX) {
        moveSprite();
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), sprite);
        translateTransition.setToX(tipX+30);

        // Check if the stick landed on the rightmost block
        System.out.println(tipX);

        System.out.println(secondary.getLayoutX());
        System.out.println(secondary.getWidth());
        System.out.println(tipX);
        System.out.println(curr.getX());
        System.out.println(curr.getLayoutX());
        System.out.println(curr.getWidth());
        if ((tipX + stick.getLayoutX() <= (secondary.getLayoutX() + secondary.getWidth()) && tipX + stick.getLayoutX() >= (secondary.getLayoutX()))) {
            System.out.println("s");
            if(tipX + stick.getLayoutX() < (secondary.getLayoutX()+secondary.getWidth()/2)){
                translateTransition.setToX(secondary.getLayoutX());
            }
            translateTransition.setOnFinished(event -> sprite.setImage(new Image("IDLE.png")));
            translateTransition.setOnFinished(event -> translateBlocks());

        }else if((tipX + stick.getLayoutX() <= (curr.getLayoutX() + curr.getWidth()) && tipX + stick.getLayoutX() >= (curr.getLayoutX()))){
            System.out.println("k");
            if(tipX + stick.getLayoutX() < (secondary.getLayoutX()+secondary.getWidth()/2)){
                translateTransition.setToX(secondary.getLayoutX());
            }
            translateTransition.setOnFinished(event -> sprite.setImage(new Image("IDLE.png")));
            translateTransition.setOnFinished(event -> translateBlocks());
        }
        else{
            translateTransition.setOnFinished(event -> dies());
        }

        translateTransition.play();
        /*scene.setOnKeyPressed(event->{
            if(event.getCode() == KeyCode.SPACE){
                //invert here
                System.out.println("w");
            }
        });*/
    }



    private static void stopRotation(){
        rotationTimeline.stop();
        rotationTimeline = null;
        angle = 0;
    }
    private static void animateSprite(ImageView sprite) {
        double currentFrame = (System.currentTimeMillis() / 100) % FRAME_COUNT;
        double nextFrameX = currentFrame * FRAME_WIDTH;
        sprite.setViewport(new javafx.geometry.Rectangle2D(nextFrameX, 0, FRAME_WIDTH, 100));
    }

    private Rectangle createBlock() {
        Rectangle x = new Rectangle();
        double randomWidth = Math.random() * (200 - (sprite.getFitWidth())) + 10;
        x.setWidth(randomWidth);
        x.setHeight(200);
        x.setArcHeight(5.0);
        x.setArcWidth(5.0);

        // Ensure the block is placed in the open space after curr's width + 10
        double randomXLayout = Math.random() * (400 - (curr.getWidth() ) - randomWidth) + (curr.getWidth() + 10 );
        x.setLayoutX(randomXLayout);
        x.setLayoutY(400);

        x.setFill(Color.web("#0d0d0d"));
        x.setStroke(Color.WHITE);
        return x;
    }

    private static void translateBlocks() {
        // Translate the blocks
        TranslateTransition translatePrimary = new TranslateTransition(Duration.seconds(1), curr);
        TranslateTransition translateSecondary = new TranslateTransition(Duration.seconds(1), secondary);
        translatePrimary.setToX(-400);
        translateSecondary.setToX(0);

        System.out.println("New Secondary: " + secondary);

        curr = updateBlock(curr);

        // Translate the new primary block to a value between 120 and 400
        double newX = Math.random() * (400 -120) ;
        TranslateTransition translateNewPrimary = new TranslateTransition(Duration.seconds(1), curr);
        translateNewPrimary.setFromX(400);
        translateNewPrimary.setToX(newX);

        // Translate stick and sprite to the right (out of the screen)
        TranslateTransition translateSprite = new TranslateTransition(Duration.seconds(1), sprite);
        translateSprite.setToX(0);
        TranslateTransition translateStick = new TranslateTransition(Duration.seconds(1), stick);
        translateStick.setToX(-400);
        sprite.setImage(new Image("IDLE.png"));
        ParallelTransition parallelTransition = new ParallelTransition(translatePrimary, translateSecondary, translateNewPrimary, translateSprite, translateStick);
        parallelTransition.play();
        Rectangle temp = curr;
        curr = secondary;
        secondary = temp;
        parallelTransition.setOnFinished(event -> stick = null);
    }



}
