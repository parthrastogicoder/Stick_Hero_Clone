package com.example.stickmanfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.animation.AnimationTimer;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class PlayPanel implements Initializable {
    @FXML public  Canvas gameCanvas;
    @FXML private Text Score;
    @FXML private Text MushScore;
    private GraphicsContext gc;
    private GameController gameController;
    private Image playerImage;
    private Image[] walkingImages; // Array of walking images
    private int currentWalkingFrame = 0;
    private long lastFrameTime = 0;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = gameCanvas.getGraphicsContext2D();
        playerImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/stand.png"))); // Load player image
        gameController = new GameController(gameCanvas.getHeight());

        setupMouseEvents();
        startGameLoop();
    }

    private void setupMouseEvents() {

        gameCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> gameController.startGrowingStick());
        gameCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> gameController.stopGrowingStick());
    }

//    private void startGameLoop() {
//        AnimationTimer gameLoop = new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//                gameController.updateGame();
//                renderGame();
//            }
//        };
//        gameLoop.start();
//    }
    // In PlayPanel's game loop
    private void startGameLoop() {
        AnimationTimer gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Game logic here
                gameController.updateGame();

                // Additional logic for checking stick fall, moving player, etc.
//                try {
//                 //   checkAndHandleGameEvents();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }

                // Render the game
                renderGame();
            }
        };
        gameLoop.start();
    }

    private void checkAndHandleGameEvents() throws IOException {
        // Check if stick has fallen on the next platform
        if (gameController.checkStickFallenOnPlatform() && !gameController.getPlayer().isMoving()) {
            System.out.println("hi");
            gameController.getPlayer().startMoving();
        }

        // Check if player has reached the end of the stick
        if (gameController.getPlayer().hasReachedEndOfStick()) {
            if( !gameController.checkStickFallenOnPlatform() )
            {
                System.out.println("hi");

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/stickmanfx/gameover.fxml")));
                Scene scene = new Scene(root);
                StickMan.prime.setScene(scene);
                StickMan.prime.show();
            }
            gameController.getPlayer().stopMoving();
//            gameController.movePlatformsAndPlayerBackward();
            gameController.generateNewPlatform(gameCanvas.getWidth());
        }
    }


    // In the renderGame method of PlayPanel class
    private void renderGame() {

        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        Score.setText((""+gameController.getScore()));
        MushScore.setText(String.valueOf(Mushroom.mushScore));
        // Render platforms
        for (Platform platform : gameController.getPlatforms()) {
            gc.fillRect(platform.getX(), platform.getY(), platform.getWidth(), platform.getHeight());
        }

        // Render stick
        Stick stick = gameController.getStick();
        double stickBaseX = gameController.getInitialPlayerX() + 70; // Adjust as needed
        double stickBaseY = gameController.getInitialPlayerY() + 60; // Adjust as needed
         // Draw stick only when the player is not moving
            gc.save(); // Save the current state
            gc.translate(stickBaseX, stickBaseY); // Move the origin to the stick's base
            gc.rotate(-stick.getRotationAngle()); // Rotate the context
            gc.fillRect(0, 0, stick.getLength(), 5); // Draw the stick
            gc.restore(); // Restore the original state


        // Render player
        Player player = gameController.getPlayer();
        System.out.println(player.isMoving());
        if (player.isMoving()) {
            System.out.println("k");
            animatePlayer(player);
        } else {
            gc.drawImage(playerImage, player.getXPos(), player.getYPos());
        }
    }


    private void animatePlayer(Player player) {
        // Ensure walkingImages is initialized and contains the walking frames
        walkingImages = new Image[] {
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/walk1.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/walk2.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/walk3.png")))
        };

        long currentTime = System.nanoTime();
        if (currentTime - lastFrameTime > 200000000) { // Change frame every 200 milliseconds
            currentWalkingFrame = (currentWalkingFrame + 1) % walkingImages.length;
            lastFrameTime = currentTime;
        }

        gc.drawImage(walkingImages[currentWalkingFrame], player.getXPos(), player.getYPos());
    }

}
