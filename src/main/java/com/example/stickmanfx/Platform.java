package com.example.stickmanfx;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.io.Serializable;

public class Platform implements Serializable , Objects {
    private int width;
    private int height;
    private int xPosition;
    private int yPosition;
    private IntegerProperty xPosition1;
    public Platform(int width, int height, int xPosition, int yPosition) {
        this.width = width;
        this.height = height;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.xPosition1 = new SimpleIntegerProperty(xPosition);
    }
    public IntegerProperty xPositionProperty() {
        return xPosition1;
    }
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return xPosition;
    }

    public void setX(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getY() {
        return yPosition;
    }

    public void setY(int yPosition) {
        this.yPosition = yPosition;
    }

    @Override
    public void update() {

    }
// Getters and setters
}

