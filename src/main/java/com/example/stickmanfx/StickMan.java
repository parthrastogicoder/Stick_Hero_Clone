package com.example.stickmanfx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.media.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class StickMan extends Application {
    public static Stage prime = null;

    @Override

    public void start(Stage primaryStage) throws IOException {
        prime = primaryStage;

        // Load the initial FXML file for the home screen
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/stickmanfx/homescreen.fxml")));
        String ssound = "/images/mario.mp3";
        Media sound = new Media(getClass().getResource(ssound).toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        // Create a scene with the loaded FXML layout
        Scene scene = new Scene(root, Color.BLACK);
        primaryStage.setTitle("Stick Hero Game");

        // Set the scene to the stage and show it
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}
