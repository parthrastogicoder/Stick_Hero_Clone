package com.example.stickmanfx;

public class ResourceLoadException extends Exception {
    public ResourceLoadException(String message) {
        super(message);
    }

    public ResourceLoadException(String message, Throwable cause) {
        super(message, cause);
    }
}