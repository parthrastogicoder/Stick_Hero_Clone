package com.example.stickmanfx;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

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
 private int score ;
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
 public void updateGame() {
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
  System.out.println("stick fell? "+ checkStickFallenOnPlatform());
  System.out.println("stick landed? "+ hasStickLanded());
  if (checkStickFallenOnPlatform() && !hasStickLanded()) {
   //stickHasLanded = true;
   removeFirstPlatform();
   generateNewPlatformWithAnimation();
   resetStickForNextRound();
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

double distance = platforms.get(1).getX() -platforms.get(0).getX() -platforms.get(1).getWidth()/2;
  System.out.println(distance);
  System.out.println(stick.getLength());
  if(hasStickLanded()){
if(stick.getLength()<distance)
{return true;}}
 return false;
  // Implement logic to check if the stick has fallen on the next platform
 }

 public void movePlatformsAndPlayerBackward() {
  // Move all platforms and the player backward by a certain amount
  double moveDistance = 100; // Example distance
  for (Platform platform : platforms) {
   platform.setX((int) (platform.getX() - moveDistance));
  }
  player.setXPos(player.getXPos() - moveDistance);
 }

 public void generateNewPlatform(double width) {
 }
 public double getInitialPlayerX() {
  return initialPlayerX;
 }

 public double getInitialPlayerY() {
  return initialPlayerY;
 }

 private void removeFirstPlatform() {
  if (!platforms.isEmpty()) {
   platforms.remove(0);
  }
 }

 private void generateNewPlatformWithAnimation() {
  // Calculate new platform position and size
  int newPlatformWidth = 100; // example width
  int newPlatformHeight = 160; // example height
  int newPlatformX = (int) 500; // start off-screen
  int newPlatformY = (int) (canvasHeight - newPlatformHeight); // adjust as needed

  Platform newPlatform = new Platform(newPlatformWidth, newPlatformHeight, newPlatformX, newPlatformY);
  platforms.add(newPlatform);

  // Animate the new platform moving into position
  // You need to implement this method to animate the platform
  animatePlatformEntry(newPlatform);
 }

 private void resetStickForNextRound() {
  stick = new Stick(); // Reset the stick
  // Reset any other necessary states for the next round
 }

 private void animatePlatformEntry(Platform platform) {
  // Define the final position of the platform
  double finalXPosition = 500; // example final position

  // Create a KeyValue for the final position
  KeyValue kv = new KeyValue(platform.xPositionProperty(), finalXPosition, Interpolator.EASE_BOTH);

  // Create a KeyFrame for the end of the animation
  KeyFrame kf = new KeyFrame(Duration.seconds(2), kv); // 2 seconds for example

  // Create the Timeline with the KeyFrame
  Timeline timeline = new Timeline(kf);
  timeline.setOnFinished(event -> {
   // Actions to perform after the animation ends, if any
  });

  // Start the animation
  timeline.play();
 }
// Existing method generateNewPlatform

}
