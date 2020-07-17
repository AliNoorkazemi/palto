package com.example.plato.game;

import com.example.plato.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {
    private String game_name;
    private int max_players;
    private String room_name;
    private List<String> users=new ArrayList<>();

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public Room() {
        users = new ArrayList<>();
    }

    public int getMax_players() {
        return max_players;
    }

    public void setMax_players(int max_players) {
        this.max_players = max_players;
    }


    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public List<String> getUsers() {
        return users;
    }

    public boolean joinRoom(String username) {
        //check is room empty?
        if (users.size() >= max_players)
            return false;

        users.add(username);
        return true;
    }

}
