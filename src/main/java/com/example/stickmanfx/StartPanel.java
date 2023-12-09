package com.example.stickmanfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class StartPanel implements Initializable {
    @FXML private Text highScore;
    @FXML private Text MushScore;

    @FXML
    private void handleStart() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/stickmanfx/playscreen.fxml")));
        Scene scene = new Scene(root);
        Thread s = new Thread(new Sound("Background"));
        s.start();
        StickMan.prime.setScene(scene);
        StickMan.prime.show();
    }
    @FXML
    private void handleHelp() throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/stickmanfx/helpScreen.fxml")));
        Scene scene = new Scene(root);
        Thread s = new Thread(new Sound("Background"));
        s.start();
        StickMan.prime.setScene(scene);
        StickMan.prime.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        FileReader r;
        try {
             r = new FileReader("./src/main/java/com/example/stickmanfx/highscore");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String a;
        try {
            a = ""+r.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            r.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        highScore.setText(""+a);
        FileInputStream in = null;
        int c;
        try {
// both constr. throws FileNotFoundException
            in = new FileInputStream("./src/main/java/com/example/stickmanfx/Mushroomscore");
            c = in.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (in != null) {
                try {
                    in.close(); // IOException
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }
        MushScore.setText(""+c);
    }
}
