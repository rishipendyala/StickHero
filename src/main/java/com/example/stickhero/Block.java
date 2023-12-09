package com.example.stickhero;

import javafx.scene.shape.Rectangle;

public class Block {

    private Rectangle block;

    public Block(Rectangle block) {
        this.block = block;
    }

    public Rectangle getBlock() {
        return block;
    }

    public void setBlock(Rectangle block) {
        this.block = block;
    }
}
