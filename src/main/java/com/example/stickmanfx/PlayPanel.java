package com.example.stickmanfx;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Camera;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.animation.AnimationTimer;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class PlayPanel implements Initializable {
    @FXML public  Canvas gameCanvas;
    @FXML private Text Score;
    @FXML private Text MushScore;

    private GraphicsContext gc;
    private GameController gameController;
    private Image playerImage;
    private ImageView  playerI;
    private Image[] walkingImages; // Array of walking images
    private int currentWalkingFrame = 0;
    private long lastFrameTime = 0;
    public static AnimationTimer a;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gc = gameCanvas.getGraphicsContext2D();
        playerImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/stand.png"))); // Load player image
        playerI = new ImageView(playerImage);
        try {
            gameController = new GameController(gameCanvas.getHeight());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //  Mushroom mush = new Mushroom((int) shroom.getX(), (int) shroom.getY(),0);
       // gameController.setMushroom(mush);
        setupMouseEvents();
        startGameLoop();
    }
    @FXML
    private void saveScore() throws IOException {
        ScoreLoader s1 = new ScoreLoader(gameController.getScore());
        ObjectOutputStream out = null;
        try
        {
       out = new ObjectOutputStream(new FileOutputStream("./src/main/java/com/example/stickmanfx/Scoreload"));
            out.writeObject(s1);

            // Write as many objects as you like
        } catch (FileNotFoundException e) {
            
        } catch (IOException e) {
           
        }
       out.close();
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/stickmanfx/homescreen.fxml")));

        // Create a scene with the loaded FXML layout
        Scene scene = new Scene(root, Color.BLACK);
        StickMan.prime.setTitle("Stick Hero Game");

        // Set the scene to the stage and show it
        StickMan.prime.setScene(scene);
        StickMan.prime.show();
    }
    private void setupMouseEvents() {

        gameCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> gameController.startGrowingStick());
        gameCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> gameController.stopGrowingStick());

        gameCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> gameController.invert());
    }

    // In PlayPanel's game loop
    private void startGameLoop() {
        a = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Game logic here
                try {
                    gameController.updateGame();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                try {
                    renderGame();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        a.start();
    }
    // In the renderGame method of PlayPanel class
    private void renderGame() throws IOException {
        System.out.println();
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        Score.setText((""+gameController.getScore()));

//        MushScore.setText((""+gameController.getMushscore()));
        MushScore.setText(""+gameController.getMushscore());
        // Render platforms
        for (Platform platform : gameController.getPlatforms()) {
            gc.fillRect(platform.getX(), platform.getY(), platform.getWidth(), platform.getHeight());
        }

        Stick stick = gameController.getStick();
        double stickBaseX = gameController.getInitialPlayerX() + 70;
        // Adjust as needed
        double stickBaseY = gameController.getInitialPlayerY() + 60; // Adjust as needed
         // Draw stick only when the player is not moving
            gc.save(); // Save the current state
            gc.translate(stickBaseX, stickBaseY); // Move the origin to the stick's base
            gc.rotate(-stick.getRotationAngle()); // Rotate the context
            gc.fillRect(0, 0, stick.getLength(), 5); // Draw the stick
            gc.restore(); // Restore the original state



        // Render player
        Player player = gameController.getPlayer();
        if(!gameController.isCollided)
        {
        gc.drawImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/mushroom.png"))),gameController.getMushroom().getxPosition(),gameController.getMushroom().getyPosition());}
//        System.out.println(player.isMoving());
        if (player.isMoving()) {
           // System.out.println("k");
            if(!player.isInverted())
            animatePlayer(player);
            else
                animatePlayerInverted(player);
        } else {
         //   System.out.println("Player Rotated");


          gc.drawImage(playerI.getImage(), player.getXPos(), player.getYPos());
            FileOutputStream out = null;

            try {
// both constr. throws FileNotFoundException
                out = new FileOutputStream("./src/main/java/com/example/stickmanfx/Mushroomscore");
                out.write(gameController.getMushscore());
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                if (out != null)
                    out.close(); // IOException

            }

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
    private void animatePlayerInverted(Player player) {
        // Ensure walkingImages is initialized and contains the walking frames
        walkingImages = new Image[] {
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/walk1invert.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/walk2invert.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/walk3invert.png")))
        };

        long currentTime = System.nanoTime();
        if (currentTime - lastFrameTime > 200000000) { // Change frame every 200 milliseconds
            currentWalkingFrame = (currentWalkingFrame + 1) % walkingImages.length;
            lastFrameTime = currentTime;
        }

        gc.drawImage(walkingImages[currentWalkingFrame], player.getXPos(), player.getYPos()+60);
    }

}
