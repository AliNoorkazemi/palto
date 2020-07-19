package com.example.plato.game.dotsandboxes;

import com.example.plato.game.Game;
import com.example.plato.game.Room;

import java.util.ArrayList;

public class DotsAndBoxes extends Game {

    private ArrayList<Room> rooms;

    public DotsAndBoxes(){}

    public ArrayList<Room> getRooms() {
        if(rooms==null)
            rooms=new ArrayList<>();
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        if(this.rooms==null)
            this.rooms=new ArrayList<>();
        this.rooms = rooms;
    }
}
