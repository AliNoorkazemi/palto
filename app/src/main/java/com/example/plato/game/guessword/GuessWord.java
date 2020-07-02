package com.example.plato.game.guessword;

import com.example.plato.game.Game;
import com.example.plato.game.Room;

import java.util.ArrayList;
import java.util.List;

public class GuessWord extends Game {

    private List<Room> rooms;

    public GuessWord(){

    }

    public List<Room> getRooms() {
        if(rooms==null)
            rooms=new ArrayList<>();
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        if(this.rooms==null)
            this.rooms=new ArrayList<>();
        this.rooms = rooms;
    }

    public void addRoom(Room room){
        if(rooms==null)
            rooms=new ArrayList<>();
        rooms.add(room);
    }

}
