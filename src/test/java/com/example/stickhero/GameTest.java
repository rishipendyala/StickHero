package com.example.stickhero;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    @Test
    public void PathCheck(){
        String path = Game.getIdlePath();
        assertEquals(path,"IDLE.png");
    }

    @Test
    public void ImageNull(){
        String path = Game.getIdlePath();
        assertNotNull(path);
    }
}