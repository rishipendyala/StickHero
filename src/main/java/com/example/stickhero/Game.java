package com.example.stickhero;

import javafx.animation.*;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Game  {

    private static final double FRAME_WIDTH = 100.0;
    private static final int FRAME_COUNT = 6;
    private static boolean spaceBarPressed = false;
    private static Rectangle curr = null;
    private static Rectangle secondary = null;
    private static Rectangle stick = null;
    private static boolean cangrow = true;
    private static boolean StopRotation = true;
    private static ImageView sprite = new ImageView();
    private boolean isDead = false;
    private static boolean inverted = true;
    private static Scene scene;
    private static boolean isrunning = false;
    private static ImageView bg = null;
    private static Pane pane ;



     public static void start(Stage stage) throws Exception {
        // Create the game scene
        ImageView bg = new ImageView(new Image("background1.png"));
        bg.setFitHeight(600.0);
        bg.setFitWidth(400.0);
        bg.setPickOnBounds(true);
        // Creating the first rectangle
        curr = new Rectangle();
        curr.setHeight(200);
        curr.setWidth(110);
        curr.setArcHeight(5.0);
        curr.setArcWidth(5.0);
        curr.setX(0);
        curr.setY(600 - curr.getHeight());
        curr.setFill(Color.web("#0d0d0d"));
        curr.setStroke(Color.RED);
        // Adding the sprite along with its animation
        sprite.setImage(new Image("IDLE.png"));
        sprite.setFitHeight(100);
        sprite.setFitWidth(100);

        sprite.setX(curr.getWidth() - 100);
        sprite.setY(600 - (curr.getHeight() + 75));

         Timeline spriteAnimation = new Timeline(
                 new KeyFrame(Duration.millis(100), event -> animateSprite(sprite))
         );
         T2 t2 = new T2();
         if (secondary == null) {
             secondary = createBlock();
             double randomXLayout = Math.random() * (400 - (curr.getWidth() ) - secondary.getWidth()) + (curr.getWidth() + 20 );
             secondary.setX(randomXLayout);
         }
         spriteAnimation.setCycleCount(Timeline.INDEFINITE);
         spriteAnimation.play();
         pane = new Pane(bg, curr, sprite, secondary);
         scene = new Scene(pane, 400, 600);
         gameLoop(scene);
         stage.setScene(scene);
         stage.show();



    }
    private static void gameLoop(Scene scene){

        T2 t2 = new T2();
        if (secondary == null) {
            secondary = new Rectangle();
            double randomWidth = Math.random() * (200 - 100);
            secondary.setHeight(200);
            secondary.setWidth(randomWidth);
            secondary.setArcHeight(5.0);
            secondary.setArcWidth(5.0);
            double randomXLayout = Math.random() * (400 - (curr.getWidth()) - secondary.getWidth()) + (curr.getWidth() + 30);
            secondary.setX(randomXLayout);
            secondary.setY(600 - curr.getHeight());
            secondary.setFill(Color.web("#0d0d0d"));
            secondary.setStroke(Color.YELLOW);
            pane.getChildren().add(secondary);
        }
        Game.scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE && !spaceBarPressed) {
                if(!t2.isAlive() || !isrunning) {
                    spaceBarPressed = true;
                    if (stick == null) {
                        startStickGrowth(Game.scene, curr);
                        spaceBarPressed = false;
                    }
                }
            }
        });

        Game.scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                if(!isrunning) {
                    cangrow = false;
                    spaceBarPressed = false;
                    t2.run();
                }
            }
        });


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
    private static void startStickGrowth(Scene scene, Rectangle curr) {
        stick = new Rectangle();
        stick.setWidth(5);
        stick.setHeight(0);
        stick.setFill(Color.web("#0d0d0d"));
        stick.setArcWidth(5.0);
        stick.setX(curr.getX() + curr.getWidth() - 2);
        stick.setY(curr.getY());

        pane.getChildren().add(stick);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(10), event -> {
                    if (spaceBarPressed && !isrunning ) {
                        stick.setHeight(stick.getHeight() + 2);
                        stick.setY(stick.getY() - 2);
                    }
                })
        );

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    private static int angle = 0;
    private static Timeline rotationTimeline = null;
    static void rotatestick() {
        isrunning = true;
        double pivotX = stick.getX() + stick.getWidth() / 2.0;
        double pivotY = stick.getY() + stick.getHeight();
        rotationTimeline = new Timeline(
                new KeyFrame(Duration.millis(5), event -> {
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
        death.setOnFinished(event->{
            //code for end screen
            //create a rectangle
            //set rectangle opacity to 0.7
            //create labels,buttons
            //set their positions in there
            //thats about it
        });
    }
    private static void translateSpriteToTip(double tipX) {
        moveSprite();
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(1), sprite);
        translateTransition.setToX(tipX+30);
        System.out.println(stick.getX()+tipX);
        System.out.println(secondary.getX());
        System.out.println(secondary.getX()+secondary.getWidth());
        if ((tipX + stick.getX() <= (secondary.getX() + secondary.getWidth()) && tipX + stick.getX() >= (secondary.getX()))) {
            System.out.println("landed");
            if(tipX + stick.getX() < (secondary.getX()+secondary.getWidth()/2)){
                translateTransition.setToX(secondary.getX());
            }
            translateTransition.setOnFinished(event -> sprite.setImage(new Image("IDLE.png")));
            translateTransition.setOnFinished(event -> {
                translateBlocks();
            });

        }
        else{
            translateTransition.setOnFinished(event -> dies());
        }

        translateTransition.play();

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

    private static Rectangle createBlock() {
        Rectangle x = new Rectangle();
        double randomWidth = Math.random() * (200 - 100);
        x = new Rectangle();
        x.setHeight(200);
        x.setWidth(randomWidth);
        x.setArcHeight(5.0);
        x.setArcWidth(5.0);
        double randomX = Math.random() * (400 - 210);
        x.setX(randomX);
        x.setY(600 - curr.getHeight());
        x.setFill(Color.web("#0d0d0d"));
        x.setStroke(Color.WHITE);
        return x;
    }
    private static Timeline translateBlocksTimeline = null;
    private static void translateBlocks() {
        sprite.setImage(new Image("IDLE.png"));
        translateBlocksTimeline = new Timeline(
                new KeyFrame(Duration.millis(5),event -> {
                    if (secondary.getX() <= 0) {
                        stopTranslation();
                    }else{
                        curr.setTranslateX(curr.getTranslateX() - 1);
                        secondary.setX(secondary.getX() - 1);
                        sprite.setTranslateX(sprite.getTranslateX() - 1);
                        if (stick != null) {
                            stick.setTranslateX(stick.getTranslateX() - 1);
                        }
                    }
                })
        );
        translateBlocksTimeline.setCycleCount(Timeline.INDEFINITE);
        translateBlocksTimeline.play();
       }

    private static void stopTranslation() {
        translateBlocksTimeline.stop();
        translateBlocksTimeline = null;

        // Update the properties of curr to match secondary

        curr = secondary;
        secondary = null;



        // Reset stick
        stick = null;

        curr.setStroke(Color.RED);
        isrunning = false;
        gameLoop(scene);
    }



}