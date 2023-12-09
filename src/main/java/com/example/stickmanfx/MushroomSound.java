package com.example.stickmanfx;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MushroomSound implements Soundable {

    @Override
    public void makeSound() {
        String ssound = "/images/Collide.mp3";
        Media sound = new Media(getClass().getResource(ssound).toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);

        mediaPlayer.play();
    }
}
