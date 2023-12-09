package com.example.stickmanfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class GameoverPanel extends Canvas implements Initializable {
    @FXML private Button b;
    @FXML private Text Score;

    static int MushroomCollected=0;
    static int score=0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Score.setText(""+GameController.score);

    }
    @FXML
    public void handleReplay(ActionEvent actionEvent) throws IOException {
        FileInputStream in = null;
        int c;
        try {
// both constr. throws FileNotFoundException
            in = new FileInputStream("./src/main/java/com/example/stickmanfx/Mushroomscore");
            c = in.read();
        } finally {
            if (in != null)
                in.close(); // IOException

        }
        System.out.println(c);
        if (c>4){
        GameController.mushscore-=5;
           Mushroom.mushScore-=5;
           c-=5;
           FileOutputStream out = null;

           try {
// both constr. throws FileNotFoundException
               out = new FileOutputStream("./src/main/java/com/example/stickmanfx/Mushroomscore");
               out.write(c);

           }  finally {
               if (out != null)
                   out.close(); // IOException

           }
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/stickmanfx/playscreen.fxml")));
        Scene scene = new Scene(root);
        StickMan.prime.setScene(scene);
        StickMan.prime.show();}}





@FXML
    public void handleHome(ActionEvent actionEvent) throws IOException {    Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/stickmanfx/homescreen.fxml")));
    String ssound = "/images/mario.mp3";
    Media sound = new Media(getClass().getResource(ssound).toString());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);
    mediaPlayer.play();
    // Create a scene with the loaded FXML layout
    Scene scene = new Scene(root, Color.BLACK);
    StickMan.prime.setTitle("Stick Hero Game");

    // Set the scene to the stage and show it
    StickMan.prime.setScene(scene);
    StickMan.prime.show();
    }


}
