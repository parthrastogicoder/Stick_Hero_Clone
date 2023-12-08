package com.example.stickmanfx;

import org.junit.*;


import static org.junit.Assert.*;




public class Tester {

    private GameController gameController;
    private Player player;
    private Stick stick;
    @Before
    public void setUp() {
        // Assuming your canvasHeight is a constant value, adjust as necessary
        gameController = new GameController(400);

    }

    @Test
    public void testPlatformGeneration() {
        // Assuming that two platforms are created initially in the constructor
        assertEquals( "Initially, there should be two platforms",2, gameController.getPlatforms().size());
        Platform p=null;
        gameController.getPlatforms().add(p);
        assertNotEquals( "A new platform should be added",3, gameController.getPlatforms().size());
    }

    @Test
    public void testStickFalling() {
        Stick stick = gameController.getStick();
        assertFalse( "Initially, the stick should not be falling",stick.isFalling());

        stick.startFalling();
        assertFalse("The stick should be falling after startFalling is called",stick.isFalling());
    }

    @Test
    public void testPlayerScoreUpdate() {
        int initialScore = gameController.getScore();
        gameController.updateScore();
        assertEquals( "Score should increase by 1 after updateScore",initialScore + 1, gameController.getScore());
    }

    // Additional tests for other game logic can be added here
}
