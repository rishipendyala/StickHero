package com.example.stickhero;

public class T2 extends Thread{
    @Override
    public void run(){
        Sprite.kickSprite();
        Game.rotatestick();
    }
}