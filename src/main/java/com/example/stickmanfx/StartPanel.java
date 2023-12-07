package com.example.stickmanfx;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.io.IOException;
import java.util.Objects;

public class StartPanel {

    @FXML
    private void handleStart(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/stickmanfx/playscreen.fxml")));
        Scene scene = new Scene(root);
        StickMan.prime.setScene(scene);
        StickMan.prime.show();
    }
}
