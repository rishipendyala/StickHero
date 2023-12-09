package com.example.stickhero;

import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Sprite {

    // SINGLETON DESIGN PATTERN HAS BEEN USED HERE

    private static Sprite sprite;
    private ImageView spriteImage;

    private Sprite(ImageView spriteImage) {
        this.spriteImage = spriteImage;
    }

    public static Sprite getInstance(){
        if (sprite==null){
            sprite = new Sprite(new ImageView());
        }
        return sprite;
    }

    public static Sprite getSprite() {
        return sprite;
    }

    public static void setSprite(Sprite sprite) {
        Sprite.sprite = sprite;
    }

    public ImageView getSpriteImage() {
        return spriteImage;
    }

    public void setSpriteImage(ImageView spriteImage) {
        this.spriteImage = spriteImage;
    }

    private Skin currentSkin;
    private ArrayList<Skin> mySkins = new ArrayList<>();
    private String userDetails;

    private ArrayList<Collectible> collected = new ArrayList<>();

    private int highScore;

    public Sprite(Skin currentSkin, String userDetails, int collected, int highScore) {
        this.currentSkin = currentSkin;
        this.userDetails = userDetails;
        this.highScore = highScore;
    }

    public Skin getCurrentSkin() {
        return currentSkin;
    }

    public void setCurrentSkin(Skin currentSkin) {
        this.currentSkin = currentSkin;
    }

    public ArrayList<Skin> getSkins() {
        return mySkins;
    }

    public void setSkins(ArrayList<Skin> skins) {
        this.mySkins = skins;
    }

    public String getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(String userDetails) {
        this.userDetails = userDetails;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public ArrayList<Skin> getMySkins() {
        return mySkins;
    }

    public void setMySkins(ArrayList<Skin> mySkins) {
        this.mySkins = mySkins;
    }

    public void setCollected(ArrayList<Collectible> collected) {
        this.collected = collected;
    }

    public void invert(){

    }

    public void move(){

    }

    public void draw(){

    }

    public void changeSkin(){

    }

    public void fall(){

    }

    public void checkRevival(){

    }

    public void revive(){
;
    }

    public void isOnStick(){

    }

    public void addSkin(){
        // add skin to the skins
    }

}
