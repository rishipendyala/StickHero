package com.example.stickhero;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SpriteTest {

    @Test
    public void onlyOneCharacter(){
        Sprite sprite1 = Sprite.getInstance();
        Sprite sprite2 = Sprite.getInstance();
        assertEquals(sprite1,sprite2);
    }

    @Test
    public void UniqueSprite(){
        Sprite sprite1 = Sprite.getInstance();
        Sprite sprite2 = Sprite.getInstance();
        assertTrue(sprite1.equals(sprite2));
    }

}