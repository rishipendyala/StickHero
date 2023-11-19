package com.example.stickhero;

public class BonusBlock extends Block {

    public BonusBlock(int height, int width, int x, int y) {
        super(5, 5, x, y);
    }

    @Override
    public boolean hasLanded(){
        // if landed perform ops and
        return true;
    }


}
