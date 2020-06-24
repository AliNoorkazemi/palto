package com.example.plato.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class XO extends Game  {

    private List<Room> rooms;

    public XO() {

    }

    public List<Room> getRooms() {
        if(rooms==null)
            rooms=new ArrayList<>();
        return rooms;
    }

    private void addRoom(Room room){
        if(rooms==null)
            rooms=new ArrayList<>();
        rooms.add(room);
    }
}
