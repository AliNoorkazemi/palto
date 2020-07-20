package com.example.plato.profile;

import android.graphics.drawable.Drawable;

public class GameInProfileItemSample {
    private String game_name;
    private String score;
    private int image;

    public void setImage(int image) {
        this.image = image;
    }

    public int getImage() {
        return image;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

}
