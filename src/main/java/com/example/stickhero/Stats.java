package com.example.stickhero;

import java.io.Serializable;

public class Stats implements Serializable {

    private int highScore;
    private int coinCount;

    public Stats(int highScore, int coinCount) {
        this.highScore = highScore;
        this.coinCount = coinCount;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }
}
