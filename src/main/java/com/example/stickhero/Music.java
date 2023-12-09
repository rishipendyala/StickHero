package com.example.stickhero;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class Music {
    private String type;
    private String filePath;

    private MediaPlayer mediaPlayer;

    private static Map<String, Music> instances =
            new HashMap<String, Music>();

    public static Music getInstance(String type, String filePath)
    {
        String key = type + ", " + filePath;
        if (!instances.containsKey(key)) {
            instances.put(key, new Music(type, filePath));
        }
        return instances.get(key);
    }


    public String getFilePath() {
        return filePath;
    }


    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    private Music(String type, String filePath) {
        this.type = type;
        this.filePath = filePath;
        this.mediaPlayer = createMediaPlayer(filePath);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private MediaPlayer createMediaPlayer(String filePath) {
        Media media = new Media(new File(filePath).toURI().toString());
        return new MediaPlayer(media);
    }


    public void playMusic(){

        mediaPlayer.play();
    }

    public void stopMusic(){
        mediaPlayer.stop();
    }

//    @FXML
//    public static void playCoinSound()  {
//        String fileName = "src/main/resources/Audio/cherry.mp3";
//        CoinSound(fileName);
//    }
//
//    private void CoinSound(String fileName) {
//
//            Media media = new Media(new File(fileName).toURI().toString());
//            mediaPlayer = new MediaPlayer(media);
//            mediaPlayer.play();
//
//
//    }
//
//    @FXML
//    public static void playBgm() {
//        String fileName = "src/main/resources/Audio/bgm.mp3";
//        bgm(fileName);
//    }
//
//    private static void bgm(String fileName){
//        Media media = new Media(new File(fileName).toURI().toString());
//        mediaPlayer = new MediaPlayer(media);
//        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
//        mediaPlayer.play();
//    }
//
//    public static void stopBgm(){
//        String fileName = "src/main/resources/Audio/bgm.mp3";
//            Media media = new Media(new File(fileName).toURI().toString());
//            mediaPlayer = new MediaPlayer(media);
//            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
//            mediaPlayer.stop();
//    }
//
//    @FXML
//    public static void playDeathSound(){
//        String fileName = "src/main/resources/Audio/dies.mp3";
//        dies(fileName);
//    }
//
//    private static void dies(String fileName)  {
//
//            Media media = new Media(new File(fileName).toURI().toString());
//            mediaPlayer = new MediaPlayer(media);
//            mediaPlayer.play();
//
//    }


}
