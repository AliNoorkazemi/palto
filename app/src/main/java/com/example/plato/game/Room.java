package com.example.plato.game;

import com.example.plato.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Room implements Serializable {
    private int max_players;
    private boolean is_join_enable;
    private String room_name;
    private List<User> users;

    public Room() {
        users = new ArrayList<>();
    }

    public int getMax_players() {
        return max_players;
    }

    public void setMax_players(int max_players) {
        this.max_players = max_players;
    }


    public boolean isIs_join_enable() {
        return is_join_enable;
    }

    public void setIs_join_enable(boolean is_join_enable) {
        this.is_join_enable = is_join_enable;
    }

    public String getRoom_name() {
        return room_name;
    }

    public void setRoom_name(String room_name) {
        this.room_name = room_name;
    }

    public List<User> getUsers() {
        return users;
    }

    public boolean joinRoom(User user) {
        //check is room empty?
        if (users.size() >= max_players)
            return false;

        users.add(user);
        return true;
    }

}
