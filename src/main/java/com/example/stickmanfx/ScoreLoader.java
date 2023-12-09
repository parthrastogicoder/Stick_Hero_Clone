package com.example.stickmanfx;

import java.io.Serializable;

public class ScoreLoader implements Serializable {
    private int Score;

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    public ScoreLoader(int score) {
        Score = score;
    }
}
