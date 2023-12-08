package com.example.stickmanfx;

import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.Objects;

public class Player implements com.example.stickmanfx.Objects , Serializable {
    private double xPos;
    private double yPos;
    private double speed;

    // In the Player class
    private Image[] walkFrames;
    private int currentFrame;
    private boolean isMoving;

    public Player(double xPos, double yPos, double speed) {
        this.xPos=xPos;
        this.yPos=yPos;
        this.speed=speed;

        // existing initialization
        walkFrames = new Image[] {
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/walk1.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/walk2.png"))),
                new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/walk3.png")))
        };
        currentFrame = 0;
        isMoving = false;
    }

    public void update() {
        if (isMoving) {
            xPos += speed;
            currentFrame = (currentFrame + 1) % walkFrames.length;
        }
    }

    public Image getCurrentFrame() {
        return walkFrames[currentFrame];
    }

    public void startMoving() {
        isMoving = true;
    }

    public void fall()
    {
        this.setYPos(this.getYPos()-10);
    }

    public void stopMoving() {
        isMoving = false;
    }

// existing getters and setters

    public void move() {
        // Logic to move the player
        if (this.isMoving()){
        xPos += 1;}
        // You might want to add boundary checks and other movement logic
    }

    // Getters and setters
    public double getXPos() {
        return xPos;
    }

    public void setXPos(double xPos) {
        this.xPos = xPos;
    }

    public double getYPos() {
        return yPos;
    }

    public void setYPos(double yPos) {
        this.yPos = yPos;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
    // In Player class
    public boolean isMoving() {
        return isMoving;
    }

    public boolean hasReachedEndOfStick() {
        return true;
    }

    public Image[] getWalkFrames() {
        return walkFrames;
    }

    public void setWalkFrames(Image[] walkFrames) {
        this.walkFrames = walkFrames;
    }
}

