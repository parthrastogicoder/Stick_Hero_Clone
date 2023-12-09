package com.example.stickmanfx;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.util.Duration;

import java.io.*;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GameController {
 private Stick stick;
 private Player player;
 private Mushroom mushroom;
 private List<Platform> platforms;
 private boolean isStickGrowing;
 private boolean isStickFalling;
 private double canvasHeight;
 private double canvasWidth;
 private double initialPlayerX;
 private double initialPlayerY;
 public static int score ;
 public static int mushscore=0;

 public static boolean isCollided;

 public  int getMushscore() {
  return mushscore;
 }

 public  void setMushscore(int mushscore) {
  GameController.mushscore = mushscore;
 }

 public static boolean isIsCollided() {
  return isCollided;
 }

 public static void setIsCollided(boolean isCollided) {
  GameController.isCollided = isCollided;
 }

 public GameController(double canvasHeight) throws IOException, ClassNotFoundException {
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
  this.mushroom = new Mushroom((int) (initialPlayerX+300),(int)initialPlayerY+72,50);
  this.score = 0;
  ObjectInputStream in1 = null;
     try {
         in1 = new ObjectInputStream(new FileInputStream("./src/main/java/com/example/stickmanfx/Scoreload"));
         while (true) {

             ScoreLoader s = (ScoreLoader) in1.readObject();
             this.score = s.getScore();

         }
     } catch (Exception e)
     {}
  in1.close();
  //this.score = 0;

  FileInputStream in = null;
  int c;
  try {
// both constr. throws FileNotFoundException
   in = new FileInputStream("./src/main/java/com/example/stickmanfx/Mushroomscore");
c = in.read();
  } catch (IOException e) {
      throw new RuntimeException(e);
  } finally {
   if (in != null)
    in.close(); // IOException

  }
  this.setMushscore(c);
  isCollided=false;
 }

 public void setStick(Stick stick) {
  this.stick = stick;
 }

 public void setPlayer(Player player) {
  this.player = player;
 }

 public Mushroom getMushroom() {
  return mushroom;
 }

 public void setMushroom(Mushroom mushroom) {
  this.mushroom = mushroom;
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
   //System.out.println(player.isMoving());
  t=0;
  }
//  check_collision();
  if (player.isMoving() &&hasStickLanded()) {

   // Check if player has reached the end of the stick

   if (player.getXPos()-this.getInitialPlayerX()-50 <= stick.getLength()) {
    player.move();

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
  if(!isCollided)
  {
  check_collision();}

  if (checkStickFallenOnPlatform() && hasStickLanded()&& player.hasReachedEndOfStick(stick)) {
   //stickHasLanded = true;
   Platform p=removeFirstPlatform();
   generateNewPlatformWithAnimation(p);
   resetStickForNextRound();
  }

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
 // System.out.println(distance);
 // System.out.println(stick.getLength());
  if(hasStickLanded()){
if(stick.getLength()<distance && stick.getLength()>distance-100 )
{return true;}}
 return false;

 }



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
  //System.out.println("Generating");
  Random random = new Random();

  // Generate a random number between 50 (inclusive) and 150 (exclusive)
  int randomNumber = 70 + random.nextInt(110 - 50);
  int newPlatformWidth = randomNumber; // example width
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
  if(isCollided)
  {mushscore++;}
  isCollided= false;

  // Reset any other necessary states for the next round
 }

 private void animatePlatformEntry(Platform platform) {

  double finalXPosition = 700; // example final position

  KeyValue kv = new KeyValue(platform.xPositionProperty(), finalXPosition, Interpolator.EASE_BOTH);
  KeyFrame kf = new KeyFrame(Duration.seconds(0), kv);

  Timeline timeline = new Timeline(kf);
  timeline.setOnFinished(event -> {
   platform.setX((int) finalXPosition); // Update the actual position
  });

  timeline.play();
 }
public void gameEnd() throws IOException {

  PlayPanel.a.stop();
 Thread sound = new Thread(new Sound("Falling"));
 sound.start();
 Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/stickmanfx/gameover.fxml")));
 Scene scene = new Scene(root);
 StickMan.prime.setScene(scene);
 StickMan.prime.show();
 ScoreLoader s1 = new ScoreLoader(0);
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

}

 public void invert() {

  if(player.isMoving()) {
   if(!player.isInverted())
   player.setInverted(true);
   else
    player.setInverted(false);
  }


 }

 public void  check_collision()
 {
  if( player.isInverted())
  {
   if(player.getXPos()>mushroom.getxPosition()-10 && player.getXPos() <mushroom.getxPosition())
   {
    isCollided = true;
   }
  }
  Thread s = new Thread(new Sound("Mushroom"));
  s.start();

 }

}
