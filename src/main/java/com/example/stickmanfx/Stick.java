package com.example.stickmanfx;

public class Stick {
    private double length;
    private double rotationAngle;
    private boolean isGrowing;
    private boolean isFalling;

    public Stick() {
        this.length = 0;
        this.rotationAngle = 90; // Start vertically
        this.isGrowing = false;
        this.isFalling = false;
    }

    public void grow() {
        if (isGrowing) {
            length += 1; // Adjust the growth rate as needed
        }
    }

    public void fall() {
        if (isFalling) {
            if (rotationAngle > 0) {
                rotationAngle -= 0.55; // Adjust the falling speed as needed
            }
        }
    }

    public void update() {
        if (isGrowing) {
            grow();
        } else if (isFalling) {
            fall();
        }
    }

    // Getters and setters
    public double getLength() {
        return length;
    }

    public double getRotationAngle() {
        return rotationAngle;
    }

    public void setGrowing(boolean growing) {
        isGrowing = growing;
    }

    public void startFalling() {
        isFalling = true;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public void setRotationAngle(double rotationAngle) {
        this.rotationAngle = rotationAngle;
    }

    public boolean isGrowing() {
        return isGrowing;
    }

    public boolean isFalling() {
        return isFalling;
    }

    public void setFalling(boolean falling) {
        isFalling = falling;
    }
}
