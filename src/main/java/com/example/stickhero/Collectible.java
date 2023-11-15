package com.example.stickhero;

public class Collectible {
    private static int count;
    private String path;

    private int xLoc;

    private int yLoc;

    public Collectible(int count, String path, int xLoc, int yLoc) {
        this.count = count;
        this.path = path;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

    public void incrCount(){
        count++;
    }
}
