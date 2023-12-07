module com.example.stickmanfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires junit;


    opens com.example.stickmanfx to javafx.fxml;
    exports com.example.stickmanfx;
}