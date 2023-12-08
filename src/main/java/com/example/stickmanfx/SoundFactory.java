package com.example.stickmanfx;


public class SoundFactory {

    public static Soundable createSound(String soundType) {
        switch (soundType) {
            case "Background":
                return new BackgroundSound();
            case "Falling":
                return new FallingSound();
            case "Mushroom":
                return new MushroomSound();
            default:
                throw new IllegalArgumentException("Unknown sound type: " + soundType);
        }
    }
}