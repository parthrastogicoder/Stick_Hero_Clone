package com.example.stickmanfx;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.io.IOException;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GameController {
 private Stick stick;
 private Player player;
 private List<Platform> platforms;
 private boolean isStickGrowing;
 private boolean isStickFalling;
 private double canvasHeight;
 private double canvasWidth;
 private double initialPlayerX;
 private double initialPlayerY;
 public static int score ;
 public GameController(double canvasHeight) {
  this.canvasHeight = canvasHeight;
  this.platforms = new ArrayList<>();
  this.stick = new Stick();

  // Adjust platform position and size
  double platformHeight = 160; // Example platform height
  double platformY = canvasHeight - platformHeight ; // Position higher
  platforms.add(new Platform(100, (int) platformHeight, 100, (int) platformY));
  platforms.add(new Platform(100, (int) platformHeight, 500, (int) platformY));

  // Adjust player size and position
  double playerHeight = 60; // 1.5 times the original height
  double playerWidth = 45;  // 1.5 times the original width
  this.player = new Player(100, platformY - playerHeight, 4);
  this.initialPlayerX = player.getXPos();
  this.initialPlayerY = player.getYPos();

  this.score = 0;
 }

 public int getScore() {
  return score;
 }
 public void updateScore()
 {
  score +=1;
 }
 public void updateMush()
 {
  Mushroom.mushScore++;
 }

 public void setScore(int score) {
  this.score = score;
 }

 int t=0;
 public void updateGame() throws IOException {
  stick.update();
  if (hasStickLanded() && !player.isMoving()) {
  player.startMoving();
   System.out.println(player.isMoving());
  t=0;
  }

  if (player.isMoving() &&hasStickLanded()) {

   // Check if player has reached the end of the stick

   if (player.getXPos()-this.getInitialPlayerX()-50 <= stick.getLength()) {
    player.move();
    // Logic for handling what happens when the player reaches the end
   }
  }
  if(hasStickLanded()&&player.hasReachedEndOfStick(stick) )
  {
   player.fall();
   if(player.getYPos()>initialPlayerY+230)
   {
    gameEnd();

   }
  }
  System.out.println("stick fell? "+ checkStickFallenOnPlatform());
  System.out.println("stick landed? "+ hasStickLanded());
  if (checkStickFallenOnPlatform() && hasStickLanded()&& player.hasReachedEndOfStick(stick)) {
   //stickHasLanded = true;
   Platform p=removeFirstPlatform();
   generateNewPlatformWithAnimation(p);
   resetStickForNextRound();
  }

  if(player.isInverted())
  {
   player.setYPos(player.getYPos()-20);
  }
  // Additional game logic goes here
 }
 private boolean hasStickLanded() {
  // Implement stick landing logic
  return stick.isFalling() && stick.getRotationAngle() <= 0;
 }
 public void startGrowingStick() {
  isStickGrowing = true;
  stick.setGrowing(true);
 }

 public void stopGrowingStick() {
  isStickGrowing = false;
  stick.setGrowing(false);
  isStickFalling = true;
  stick.startFalling();
 }

 // Getters for Stick, Player, and Platforms
 public Stick getStick() {
  return stick;
 }

 public Player getPlayer() {
  return player;
 }

 public List<Platform> getPlatforms() {
  return platforms;
 }
 // In GameController class
 public boolean checkStickFallenOnPlatform() {

double distance = platforms.get(1).getX() -platforms.get(0).getX() +30;
  System.out.println(distance);
  System.out.println(stick.getLength());
  if(hasStickLanded()){
if(stick.getLength()<distance && stick.getLength()>distance-100 )
{return true;}}
 return false;
  // Implement logic to check if the stick has fallen on the next platform
 }

// public void movePlatformsAndPlayerBackward() {
//  // Move all platforms and the player backward by a certain amount
//  double moveDistance = 100; // Example distance
//  for (Platform platform : platforms) {
//   platform.setX((int) (platform.getX() - moveDistance));
//  }
//  player.setXPos(player.getXPos() - moveDistance);
// }

 public void generateNewPlatform(double width) {
 }
 public double getInitialPlayerX() {
  return initialPlayerX;
 }

 public double getInitialPlayerY() {
  return initialPlayerY;
 }

 private Platform removeFirstPlatform() {

  if (!platforms.isEmpty()) {
   return platforms.remove(0);
  }
  return null;
 }

 private void generateNewPlatformWithAnimation(Platform p) {
  System.out.println("Generating");
  int newPlatformWidth = 100; // example width
  int newPlatformHeight = 160; // example height
  int newPlatformX = (int) 1000; // start off-screen
  int newPlatformY = (int) (canvasHeight - newPlatformHeight);

  Platform newPlatform = new Platform(newPlatformWidth, newPlatformHeight, newPlatformX, newPlatformY);
  platforms.add(newPlatform);
  platforms.get(0).setX(p.getX());
  animatePlatformEntry(newPlatform);
  player.setXPos(initialPlayerX);
  player.stopMoving();
 }


 private void resetStickForNextRound() {
  stick = new Stick();// Reset the stick
  score++;
  // Reset any other necessary states for the next round
 }

 private void animatePlatformEntry(Platform platform) {
  double finalXPosition = 700; // example final position

  KeyValue kv = new KeyValue(platform.xPositionProperty(), finalXPosition, Interpolator.EASE_BOTH);
  KeyFrame kf = new KeyFrame(Duration.seconds(1), kv);

  Timeline timeline = new Timeline(kf);
  timeline.setOnFinished(event -> {
   platform.setX((int) finalXPosition); // Update the actual position
  });

  timeline.play();
 }
public void gameEnd() throws IOException {
  PlayPanel.a.stop();
 Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/stickmanfx/gameover.fxml")));
 Scene scene = new Scene(root);
 StickMan.prime.setScene(scene);
 StickMan.prime.show();
}

 public void invert() {
  if(player.isInverted())
  {
   player.setInverted(false);
  }
  else
  {
   player.setInverted(true);
  }
 }
// Existing method generateNewPlatform

}
