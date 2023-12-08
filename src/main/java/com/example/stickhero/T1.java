package com.example.stickhero;

import javafx.scene.shape.Rectangle;

import static java.lang.Thread.sleep;

public class T1 extends Game implements Runnable{
    private int to;
    private Rectangle rectangle;
    public T1(Rectangle rectangle,int to){
        this.rectangle = rectangle;
        this.to =to;
    }

    @Override
    public void run(){
        for (double  i = rectangle.getX(); i> to; i-- ){
            rectangle.setX(i);
            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
          }
}
