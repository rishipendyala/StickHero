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


import static java.lang.Thread.sleep;

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



     public static void start(Stage stage) throws Exception {
        // Create the game scene
        ImageView bg = new ImageView(new Image("background1.png"));
        bg.setFitHeight(600.0);
        bg.setFitWidth(400.0);
        bg.setPickOnBounds(true);
        T2 t2 = new T2();
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
        if (secondary == null) {
            secondary = createBlock();
            double randomXLayout = Math.random() * (400 - (curr.getWidth() ) - secondary.getWidth()) + (curr.getWidth() + 10 );
            secondary.setX(randomXLayout);
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
                        spaceBarPressed = false;
                    }
                }
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                if(!isrunning) {
                    cangrow = false;
                    spaceBarPressed = false;
                    t2.run();
                }
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

    private static void startStickGrowth(Scene scene, Rectangle curr) {
        stick = new Rectangle();
        stick.setWidth(5);
        stick.setHeight(0);
        stick.setFill(Color.web("#0d0d0d"));
        stick.setArcWidth(5.0);
        stick.setX(sprite.getX() + sprite.getFitWidth() -2);
        stick.setY(curr.getY());

        Group root = (Group) scene.getRoot();
        root.getChildren().add(stick);

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
    private static Rectangle updateBlock(Rectangle curr) {
        //double randomWidth = 200 - (secondary.getWidth() + 10);
        curr.setWidth(Math.random() * (200 - (sprite.getFitWidth())) + 10);

        double maxXLayout = 400 - curr.getWidth();
        curr.setX(Math.random() * maxXLayout);

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

        if ((tipX + stick.getX() <= (secondary.getX() + secondary.getWidth()) && tipX + stick.getX() >= (secondary.getX()))) {
            if(tipX + stick.getX() < (secondary.getX()+secondary.getWidth()/2)){
                translateTransition.setToX(secondary.getX());
            }
            translateTransition.setOnFinished(event -> sprite.setImage(new Image("IDLE.png")));
            translateTransition.setOnFinished(event -> {
                try {
                    translateBlocks();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

        }else if((tipX + stick.getX() <= (curr.getX() + curr.getWidth()) && tipX + stick.getX() >= (curr.getX()))){
            if(tipX + stick.getX() < (secondary.getX()+secondary.getWidth()/2)){
                translateTransition.setToX(secondary.getX());
            }
            translateTransition.setOnFinished(event -> sprite.setImage(new Image("IDLE.png")));
            translateTransition.setOnFinished(event ->
            {
                try {
                    translateBlocks();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
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
        double randomWidth = Math.random() * (200 - 100) + 10;
        x.setWidth(randomWidth);
        x.setHeight(200);
        x.setArcHeight(5.0);
        x.setArcWidth(5.0);

        // Ensure the block is placed in the open space after curr's width + 10

        x.setX(400);
        x.setY(400);

        x.setFill(Color.web("#0d0d0d"));
        x.setStroke(Color.WHITE);
        return x;
    }

    private static void translateBlocks() throws InterruptedException {
        /*
        System.out.println("HELLO");
        sprite.setImage(new Image("IDLE.png"));

        //moving the previous block outside the screen to the left (stage left)
        TranslateTransition prevBlock = new TranslateTransition(Duration.seconds(1),curr);
        prevBlock.setToX(-400);
        prevBlock.setOnFinished(event -> curr = null);

        TranslateTransition currBlock = new TranslateTransition(Duration.seconds(1), curr=secondary);
        currBlock.setToX(0);
        currBlock.setOnFinished(event -> {curr.setStroke(Color.RED);secondary = createBlock();});

        TranslateTransition spriteBlock = new TranslateTransition(Duration.seconds(1), sprite);
        spriteBlock.setToX(curr.getWidth() / 2);

        TranslateTransition stickBlock = new TranslateTransition(Duration.seconds(1), stick);
        stickBlock.setToX(-400);
        stickBlock.setOnFinished(event -> stick = null);

        double randomXLayout = Math.random() * (400 - secondary.getWidth());
        TranslateTransition newBlock = new TranslateTransition(Duration.seconds(1), secondary);
        newBlock.setToX(randomXLayout);
        newBlock.setOnFinished(event -> {
            secondary.setX(randomXLayout);
            System.out.println(secondary.getX());
            isrunning = false;
        });

        ParallelTransition parallelTransition = new ParallelTransition(prevBlock, currBlock, spriteBlock, stickBlock);
        parallelTransition.setOnFinished(event -> newBlock.play());
        parallelTransition.play();

        //move the background

        // moving the current block to the left part of the screen
        */
        /*
        TranslateTransition currBlock = new TranslateTransition(Duration.seconds(1),secondary);
        currBlock.setOnFinished(event -> {curr = secondary;secondary  = createBlock();});

        //moving the sprite back
        TranslateTransition spriteBlock = new TranslateTransition(Duration.seconds(1),sprite);
        spriteBlock.setToX(0);

        // moving the stick outside the screen
        TranslateTransition stickBlock = new TranslateTransition(Duration.seconds(1),stick);
        stickBlock.setToX(-400);
        stickBlock.setOnFinished(event -> stick = null);
        if (secondary.getWidth() > 100){
            currBlock.setToX(100 - secondary.getWidth() );
        }else{
            currBlock.setToX(0);
        }

        ParallelTransition parallelTransition = new ParallelTransition(prevBlock,spriteBlock,stickBlock,currBlock);
        //parallelTransition.setOnFinished(event -> secondary = createBlock());
        parallelTransition.play();

        // getting in the newly created block
        double randomXLayout = Math.random() * (400 - (curr.getWidth() ) - secondary.getWidth()) + (curr.getWidth() + 10 );
        TranslateTransition newBlock = new TranslateTransition(Duration.seconds(1),secondary);
        newBlock.setToX(randomXLayout);
        newBlock.setOnFinished(event -> isrunning = false);
        parallelTransition.setOnFinished(event -> newBlock.play());*/
        translateBlock(secondary,0);


    }
    private static void translateBlock(Rectangle rectangle, int to) throws InterruptedException {
        T1 t1 = new T1(rectangle,to);
        Thread t = new Thread(t1);
        t.start();
    }


}