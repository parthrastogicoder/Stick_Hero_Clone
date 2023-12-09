package com.example.stickmanfx;


public class SoundFactory {// Here our Design pattern Object factory is being used

    public static Soundable createSound(String soundType) {
        switch (soundType) {
            case "Background":
                return new BackgroundSound();
            case "Falling":
                return new FallingSound();
            case "Mushroom":
                return new MushroomSound();
            default:
                return new NullSound();
                //throw new IllegalArgumentException("Unknown sound type: " + soundType);
        }
    }
}