package com.example.stickmanfx;

import java.io.Serializable;

public class Mushroom implements Objects , Serializable {
    private int xPosition;
    private int yPosition;
    private int size;
    public static int mushScore=0;

    public Mushroom(int xPosition, int yPosition, int size) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.size = size;
    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public void update() {

    }
// Getters and setters
}
