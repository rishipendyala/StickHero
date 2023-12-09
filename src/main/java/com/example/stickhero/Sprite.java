package com.example.stickhero;

import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Sprite extends Game{

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



    public  static void idleSprite(){
        sprite.getSpriteImage().setImage(idleList.get(c));
    }
    public static void invertSprite(){
        sprite.getSpriteImage().setImage(invertList.get(c));
    }

    public static void moveSprite(){
        sprite.getSpriteImage().setImage(moveList.get(c));
    }
    public static void kickSprite(){
        sprite.getSpriteImage().setImage(kickList.get(c));
    }

    public static void animateSprite(ImageView sprite) {
        double currentFrame = (System.currentTimeMillis() / 100) % FRAME_COUNT;
        double nextFrameX = currentFrame * FRAME_WIDTH;
        if(sprite != null){
            sprite.setViewport(new javafx.geometry.Rectangle2D(nextFrameX, 0, FRAME_WIDTH, 100));}
    }

}
