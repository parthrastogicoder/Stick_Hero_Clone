package com.example.stickmanfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.util.Objects;

public class HelpScreen {
    @FXML
    public void BackPress() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/stickmanfx/homescreen.fxml")));

        // Create a scene with the loaded FXML layout
        Scene scene = new Scene(root, Color.BLACK);
        StickMan.prime.setTitle("Stick Hero Game");

        // Set the scene to the stage and show it
        StickMan.prime.setScene(scene);
        StickMan.prime.show();
    }

}
