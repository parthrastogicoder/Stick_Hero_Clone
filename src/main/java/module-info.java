module com.example.stickmanfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.stickmanfx to javafx.fxml;
    exports com.example.stickmanfx;
}