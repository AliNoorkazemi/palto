package com.example.plato.game.startPage.fragment.leaderboard;

import android.graphics.Bitmap;

public class BestPlayer {
    private String Name;
    private int Score;
    private Bitmap image_bitmap;

    public Bitmap getImage_bytes() {
        return image_bitmap;
    }

    public void setImage_bytes(Bitmap image_bitmap) {
        this.image_bitmap = image_bitmap;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

}
