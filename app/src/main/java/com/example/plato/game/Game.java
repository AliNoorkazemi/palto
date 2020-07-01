package com.example.plato.game;

import java.io.Serializable;

public class Game implements Serializable {
    private String game_name;
    private int game_image_res;

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public int getGame_image_res() {
        return game_image_res;
    }

    public void setGame_image_res(int game_image_res) {
        this.game_image_res = game_image_res;
    }
}
