package com.example.stickmanfx;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Sound implements Runnable{
    private String soundType;

    public Sound(String soundType) {
        this.soundType = soundType;
    }

    @Override
    public void run() {
        Soundable soundObject = SoundFactory.createSound(soundType);
        soundObject.makeSound();



    }
}
