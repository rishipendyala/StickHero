package com.example.stickhero;

import com.example.stickhero.Skin;

import java.util.ArrayList;

public class Sprite {

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
