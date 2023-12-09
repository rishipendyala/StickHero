package com.example.stickhero;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.util.HashMap;
import java.util.Map;


public class Music {
    // THIS CLASS IMPLEMENTS THE FLYWEIGHT DESIGN PATTERN
    // WE ONLY HAVE ONE OBJECT PER STATE - implemented using hashmap and lazy instantiation
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


}
