package com.example.stickhero;

public class BonusBlock extends Block {

    public BonusBlock(double height, double width, double x, double y) {
        super(height, width, x, y);
    }

    @Override
    public boolean hasLanded(){
        // if landed perform ops and
        return true;
    }


}
