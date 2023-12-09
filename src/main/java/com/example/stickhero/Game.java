package com.example.stickhero;

import javafx.animation.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;


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
    private static boolean isDead = false;
    private static boolean inverted = false;
    private static Scene scene;
    private static boolean isrunning = false;
    private static ImageView bg = null;
    private static Pane pane ;
    private static ImageView collectible = null;
    private static Image idle;
    private static Image move;
    private static Image kick;
    private static ArrayList<Image> idleList = new ArrayList<>();
    private static ArrayList<Image> moveList = new ArrayList<>();
    private static ArrayList<Image> kickList = new ArrayList<>();
    private static ArrayList<Image> invertList = new ArrayList<>();
    private static int c = 0;
    private static int Score= -1;
    private static Button scoreB;
    private static Boolean cantInvert = false;

     public static void start(Stage stage,int count) throws Exception {
        // Create the game scene
         idleList.add(new Image("IDLE.png"));
         idleList.add(new Image("astro-idle-new.png"));
         idleList.add(new Image("mario-idle-new.png"));

        moveList.add(new Image("MOVE.png"));
        moveList.add(new Image("astro-move-new.png"));
        moveList.add(new Image("mario-move-new.png"));

        kickList.add(new Image("KICK.png"));
        kickList.add(new Image("astro-kick.png"));
        kickList.add(new Image("mario-kick.png"));

        invertList.add(new Image("base invert.png"));
        invertList.add(new Image("astro-invert-new.png"));
        invertList.add(new Image("mario-invert-new.png"));
        c = count;
        ImageView bg = new ImageView(new Image("background1.png"));
        bg.setFitHeight(600.0);
        bg.setFitWidth(400.0);
        // Creating the first rectangle
        curr = new Rectangle();
        curr.setHeight(200);
        curr.setWidth(110);
        curr.setArcHeight(5.0);
        curr.setArcWidth(5.0);
        curr.setX(0);
        curr.setY(600 - curr.getHeight());
        curr.setFill(Color.web("#0d0d0d"));
        // Adding the sprite along with its animation
        idleSprite();
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
             double randomXLayout = getRando(200,400);
             secondary.setX(randomXLayout);
         }
         spriteAnimation.setCycleCount(Timeline.INDEFINITE);
         spriteAnimation.play();
         pane = new Pane(bg, curr,  secondary,sprite);
         scene = new Scene(pane, 400, 600);
         gameLoop(scene);
         stage.setScene(scene);
         //Music.playBgm();
         stage.show();
    }
    private static void idleSprite(){
         sprite.setImage(idleList.get(c));
     }
    private static void invertSprite(){
        sprite.setImage(invertList.get(c));
    }

    private static void gameLoop(Scene scene){

        T2 t2 = new T2();
        if (scoreB == null) {
            scoreB = new Button();
            scoreB.setLayoutX(175);
            scoreB.setLayoutY(75);
            scoreB.setPrefSize(75,45);
            scoreB.setDisable(true);

            scoreB.setTextFill(Color.BLACK);
            scoreB.setOpacity(0.3);
            scoreB.setStyle("-fx-font-size: 20px;");
            pane.getChildren().add(scoreB);
        }
        // Update the text of the scoreLabel
        Score++;
        scoreB.setText(String.valueOf(Score));
        if (secondary == null) {
            secondary = new Rectangle();
            double randomWidth = getRando(90,150);
            secondary.setHeight(200);
            secondary.setStroke(Color.YELLOW);
            secondary.setWidth(randomWidth);
            secondary.setArcHeight(5.0);
            secondary.setArcWidth(5.0);
            double randomXLayout = getRando(170,(int)(430 - randomWidth));
            secondary.setX(randomXLayout);
            secondary.setY(600 - curr.getHeight());
            secondary.setFill(Color.web("#0d0d0d"));
            pane.getChildren().add(secondary);

            collectibleGenerate();
        }
        //score updation

        Game.scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE ) {
                spaceBarPressed = true;
                if(!t2.isAlive() || !isrunning) {

                    if (stick == null) {
                        startStickGrowth(Game.scene, curr);
                        spaceBarPressed = false;
                    }
                }
            }
        });

        Game.scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                spaceBarPressed = false;
                if (!isrunning) {
                    cangrow = false;
                    System.out.println("NEW thread");
                    t2.run();
                }
            }
        });



    }
    private static void moveSprite(){
        sprite.setImage(moveList.get(c));
    }
    static void kickSprite(){
        sprite.setImage(kickList.get(c));
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
                new KeyFrame(Duration.millis(25), event -> {
                    if (spaceBarPressed && !isrunning && stick != null) {
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
        if (stick == null){
            startStickGrowth(scene,curr);
        }
        isrunning = true;
            double pivotX = stick.getX() + stick.getWidth() / 2.0;
            double pivotY = stick.getY() + stick.getHeight();
            rotationTimeline = new Timeline(
                    new KeyFrame(Duration.millis(5), event -> {
                        stick.getTransforms().clear();
                        stick.getTransforms().add(new javafx.scene.transform.Rotate(angle, pivotX, pivotY));
                        angle += 1;
                        if (angle > 90 && StopRotation) {
                            stopRotation();



                        }
                    })
            );

            rotationTimeline.setCycleCount(Timeline.INDEFINITE);
            rotationTimeline.setOnFinished(event -> isrunning = false);
            rotationTimeline.play();

    }
    private static void collectibleGenerate(){
        if(Math.abs(secondary.getX() - (curr.getWidth()+ curr.getX())) > 100){
        double xPos = getRando((int)(curr.getX()+curr.getWidth()), (int)(curr.getX()+curr.getWidth()) +50);
        collectible = new ImageView();
        collectible.setFitWidth(100);
        collectible.setX(xPos);
        collectible.setY(410);
        collectible.setFitWidth(100);
        collectible.setImage(new Image("Collectible.png"));
        Timeline collectibleAnimation = new Timeline(
                new KeyFrame(Duration.millis(100), event -> animateSprite(collectible))
        );
        collectibleAnimation.setCycleCount(Timeline.INDEFINITE);
        collectibleAnimation.play();
        pane.getChildren().add(collectible);
        }
    }
    private static Timeline death;
    private static void dies() {
        cantInvert = true;
        idleSprite();
        death = new Timeline(new KeyFrame(Duration.millis(5),event -> {
            System.out.println("Sprite.gety = "+sprite.getY());
            if(sprite.getY() > 555){
                System.out.println(sprite.getY());
                stopdeath();
            }else{
                sprite.setY(sprite.getY()+1);
            }
        }));
        death.setCycleCount(Timeline.INDEFINITE);
        death.play();
        RotateTransition rotation = new RotateTransition(Duration.seconds(1), sprite);
        rotation.setByAngle(450);
        death.play();
        rotation.play();
        Music.stopBgm();

    }

        private static void stopdeath() {
            death.stop();
            death = null;
            System.out.println("a");
            Rectangle endscreen = new Rectangle();
            endscreen.setHeight(200);
            endscreen.setWidth(300);
            endscreen.setArcHeight(5.0);
            endscreen.setArcWidth(5.0);
            endscreen.setX(55);
            endscreen.setY(55);
            endscreen.setFill(Color.GRAY);
            endscreen.setStroke(Color.BLACK);
            endscreen.setStrokeWidth(5);

            // Add "GAME OVER" text
            Text gameOverText = new Text("GAME OVER");
            gameOverText.setFont(new Font(20));
            gameOverText.setFill(Color.WHITE);
            gameOverText.setX(endscreen.getX() + 100);
            gameOverText.setY(endscreen.getY() + 30);

            // Add buttons
            Pane homeButton = createCircularButton("Home");
            homeButton.setLayoutX(endscreen.getX() + 50);
            homeButton.setLayoutY(endscreen.getY() + 150);

            Pane reviveButton = createCircularButton("Revive");
            reviveButton.setLayoutX(endscreen.getX() + 150);
            reviveButton.setLayoutY(endscreen.getY() + 150);

            Pane restartButton = createCircularButton("Restart");
            restartButton.setLayoutX(endscreen.getX() + 250);
            restartButton.setLayoutY(endscreen.getY() + 150);

            Text scoreDisplay = new Text("SCORE:");
            scoreDisplay.setLayoutX(endscreen.getX()+120);
            scoreDisplay.setLayoutY(endscreen.getY()+60);
            scoreDisplay.setFont(new Font(15));
            scoreDisplay.setFill(Color.WHITE);

            Text highScoreDisplay = new Text("HIGH SCORE:");
            highScoreDisplay.setLayoutX(endscreen.getX()+100);
            highScoreDisplay.setLayoutY(endscreen.getY()+90);
            highScoreDisplay.setFont(new Font(15));
            highScoreDisplay.setFill(Color.WHITE);

            setButtonAction(homeButton,"Home");
            setButtonAction(reviveButton,"Revive");
            setButtonAction(restartButton,"Restart");
            pane.getChildren().addAll(endscreen, gameOverText, homeButton, reviveButton, restartButton,scoreDisplay,highScoreDisplay);

        }
    private static void setButtonAction(Pane buttonPane, String buttonType) {
        buttonPane.setOnMouseClicked(event -> {
            try {
                handleButtonClick(buttonType);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
    private static void reset(){
        spaceBarPressed = false;
        curr = null;
        secondary = null;
        stick = null;
        cangrow = true;
        StopRotation = true;
        sprite = new ImageView();
        isDead= false;
        inverted = false;
        scene = null;
        isrunning = false;
        bg = null;
        pane = null ;
        collectible = null;
        ArrayList<Image> idleList = new ArrayList<>();
        ArrayList<Image> moveList = new ArrayList<>();
        ArrayList<Image> kickList = new ArrayList<>();
        ArrayList<Image> invertList = new ArrayList<>();
         c=0;
        Score= -1;
        scoreB = null;
        cantInvert = false;
    }
    private static Timeline revive;
    private static boolean re = false;
    private static void handleButtonClick(String buttonType) throws Exception {
        Stage stage = (Stage)(pane.getScene().getWindow());
        if (buttonType.equalsIgnoreCase("Home")){
            // go to the home screen
            FXMLLoader fxmlLoader1 = new FXMLLoader(SceneController.class.getResource("StartScene.fxml"));
            Scene scene1 = new Scene(fxmlLoader1.load(), 400, 600);
            fxmlLoader1.setController(new StartSceneController());
            reset();
            stage.setTitle("STICK HERO");
            stage.setScene(scene1);
            stage.show();
        } else if (buttonType.equalsIgnoreCase("Restart")) {
            // restart the game loop
            System.out.println("RESTART");
            reset();
            start(stage,c);
        } else if ((buttonType.equalsIgnoreCase("Revive"))) {

            revive = new Timeline(new KeyFrame(Duration.millis(5),event -> {
                if (sprite.getY() < 325){
                    try {
                        stopRevive(stage);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    sprite.setY(sprite.getY() -1);
                }
            }));
            revive.setCycleCount(Timeline.INDEFINITE);
            revive.play();
        }
    }

    private static void stopRevive(Stage stage) throws Exception {
        if(re) {
            revive.stop();
            revive = null;
            re = false;
            sprite.setX(secondary.getX() + sprite.getFitWidth());
            Score--;
            int temp3 = c;
            Rectangle temp1 = curr;
            Rectangle temp2 = secondary;
            reset();
            curr = temp1;
            c = temp3;
            secondary = temp2;
            sprite.setX(0);
            //translateBlocks();
            start(stage, c);
        }
    }



    private static Pane createCircularButton(String text) {
        Circle button = new Circle(25); // Adjust the radius for your preferred size
        button.setFill(Color.GRAY.darker()); // Adjust the color as needed

        Text buttonText = new Text(text);
        buttonText.equals(Color.WHITE);

        // Center the text within the circle
        buttonText.setBoundsType(TextBoundsType.VISUAL);
        buttonText.setX(button.getCenterX() - buttonText.getLayoutBounds().getWidth() / 2);
        buttonText.setY(button.getCenterY() + buttonText.getLayoutBounds().getHeight() / 4);

        // Add the text to the button
        Pane buttonPane = new Pane(button, buttonText);

        return buttonPane;
    }
    private static Timeline moveSprite;
    private static void stopTranslateSpriteToTip() {
        if (moveSprite != null) {
            moveSprite.stop();
            moveSprite = null;
        }
    }

    private static void translateSpriteToTip(double tipX) {
        moveSprite();

        moveSprite = new Timeline();

        KeyValue keyValueX = new KeyValue(sprite.translateXProperty(), tipX +40);
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), keyValueX);

        System.out.println(stick.getX() + tipX);
        System.out.println(secondary.getX());
        System.out.println(secondary.getX() + secondary.getWidth());

        EventHandler<ActionEvent> onFinishLanded = event -> {
            if (inverted && sprite.getBoundsInParent().intersects(secondary.getBoundsInParent())) {

                stopTranslateSpriteToTip();
                dies();
                return; // Stop further execution of the handler
            }

            if (tipX + stick.getX() < (secondary.getX() + secondary.getWidth() / 2)) {

                moveSprite.getKeyFrames().setAll(
                        new KeyFrame(Duration.ZERO, new KeyValue(sprite.translateXProperty(), sprite.getTranslateX())),
                        new KeyFrame(Duration.seconds(1), new KeyValue(sprite.translateXProperty(), secondary.getX() + secondary.getWidth() - sprite.getFitWidth()))
                );
            }

            idleSprite();
            translateBlocks();
            stopTranslateSpriteToTip();
        };

        EventHandler<ActionEvent> onFinishNotLanded = event -> {
            dies();

        };

        if ((tipX + stick.getX() <= (secondary.getX() + secondary.getWidth()) && (tipX + stick.getX() >= (secondary.getX())))) {
            moveSprite.setOnFinished(onFinishLanded);
        } else {
            moveSprite.setOnFinished(onFinishNotLanded);
        }
        scene.setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.SPACE && !cantInvert){
                //add here invert code
                inverted = !inverted;
                System.out.println(inverted);
                if(sprite.getY() <= 325.0){
                    sprite.setY(375);
                    invertSprite();

                }else{
                    sprite.setY(325);
                    moveSprite();
                }

            }
        });

        moveSprite.getKeyFrames().add(keyFrame);
        moveSprite.setCycleCount(1);

        moveSprite.play();

    }




    private static void stopRotation(){
        rotationTimeline.stop();
        rotationTimeline = null;
        angle = 0;
        double tip = stick.getHeight();
        System.out.println("STOPPED ROTATING");
        translateSpriteToTip(tip);
    }
    private static void animateSprite(ImageView sprite) {
        double currentFrame = (System.currentTimeMillis() / 100) % FRAME_COUNT;
        double nextFrameX = currentFrame * FRAME_WIDTH;
        if(sprite != null){
        sprite.setViewport(new javafx.geometry.Rectangle2D(nextFrameX, 0, FRAME_WIDTH, 100));}
    }
    private static Rectangle createBlock() {
        Rectangle x = new Rectangle();
        double randomWidth = getRando(90,200);
        x = new Rectangle();
        x.setHeight(200);
        x.setWidth(randomWidth);
        x.setArcHeight(5.0);
        x.setArcWidth(5.0);
        double randomX = getRando(210,400);
        x.setX(randomX);
        x.setY(600 - curr.getHeight());
        x.setFill(Color.web("#0d0d0d"));
        return x;
    }
    private static Timeline translateBlocksTimeline = null;
    private static void translateBlocks() {
        System.out.println("MOVE");
        idleSprite();
        TranslateTransition bgTransition = new TranslateTransition(Duration.millis(5), bg);
        bgTransition.setByX(-1);
        bgTransition.setCycleCount(TranslateTransition.INDEFINITE);
        bgTransition.play();
        translateBlocksTimeline = new Timeline(
                new KeyFrame(Duration.millis(5),event -> {
                    if (secondary.getX() <= 0) {
                        stopTranslation();
                    }else{
                        curr.setTranslateX(curr.getTranslateX() - 1);
                        secondary.setX(secondary.getX() - 1);
                        sprite.setTranslateX(sprite.getTranslateX() - 1);
                        if(collectible != null){
                            collectible.setTranslateX(collectible.getTranslateX() -1);
                        }
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
        stick.setOpacity(0);
        stick = null;
        curr.setStroke(Color.RED);
        isrunning = false;
        collectible = null;
        gameLoop(scene);
    }
    private static int getRando(int min, int max) {
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


}