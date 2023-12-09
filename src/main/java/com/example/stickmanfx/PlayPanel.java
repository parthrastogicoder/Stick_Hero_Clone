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
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class PlayPanel implements Initializable {
    @FXML public  Canvas gameCanvas;
    @FXML private Text Score;
    @FXML private Text MushScore;
  //  @FXML private ImageView shroom;
    //@FXML private Button invertButton;
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
        gameController = new GameController(gameCanvas.getHeight());
      //  Mushroom mush = new Mushroom((int) shroom.getX(), (int) shroom.getY(),0);
       // gameController.setMushroom(mush);
        setupMouseEvents();
        startGameLoop();
    }

    private void setupMouseEvents() {

        gameCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, e -> gameController.startGrowingStick());
        gameCanvas.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> gameController.stopGrowingStick());
//        gameCanvas.setOnKeyPressed(event -> {
//
//            if (event.getCode() == KeyCode.SPACE) {
//                System.out.println("space clicked");
//                // Handle spacebar pressed
//                gameController.invert();
//
//            }
//        });
        gameCanvas.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> gameController.invert());
    }
// @FXML
// private void invert()
// {}

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
        a = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Game logic here
                try {
                    gameController.updateGame();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

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
        a.start();
    }

    private void checkAndHandleGameEvents() throws IOException {
        // Check if stick has fallen on the next platform
        if (gameController.checkStickFallenOnPlatform() && !gameController.getPlayer().isMoving()) {
            //System.out.println("hi");
            gameController.getPlayer().startMoving();
        }

        // Check if player has reached the end of the stick
//        if (gameController.getPlayer().hasReachedEndOfStick(s)) {
//            if( !gameController.checkStickFallenOnPlatform() )
//            {
//                System.out.println("hi");
//
//                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/stickmanfx/gameover.fxml")));
//                Scene scene = new Scene(root);
//                StickMan.prime.setScene(scene);
//                StickMan.prime.show();
//            }
//            gameController.getPlayer().stopMoving();
////            gameController.movePlatformsAndPlayerBackward();
//            gameController.generateNewPlatform(gameCanvas.getWidth());
//        }
    }


    // In the renderGame method of PlayPanel class
    private void renderGame() {
        System.out.println();
        gc.clearRect(0, 0, gameCanvas.getWidth(), gameCanvas.getHeight());
        Score.setText((""+gameController.getScore()));

//        MushScore.setText((""+gameController.getMushscore()));
        MushScore.setText(""+gameController.getMushscore());
        // Render platforms
        for (Platform platform : gameController.getPlatforms()) {
            gc.fillRect(platform.getX(), platform.getY(), platform.getWidth(), platform.getHeight());
        }
        Random random = new Random();

        // Generate a random number between 50 (inclusive) and 150 (exclusive)
      //  int randomNumber = 50 + random.nextInt(110 - 50);
//        System.out.println("Mushroom initial pos"+gameController.getMushroom().getxPosition());
//        shroom.setX(gameController.getMushroom().getxPosition());
//        shroom.setY(gameController.getMushroom().getyPosition());
        // Render stick

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

//            if(player.isInverted())
//            { gc.scale(-1, 1);
//            gc.drawImage(playerImage, -player.getXPos(), player.getYPos());}
//            else
//            {
//                gc.drawImage(playerImage, player.getXPos(), player.getYPos());}
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
