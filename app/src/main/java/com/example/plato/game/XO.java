package com.example.plato.game;

import java.util.ArrayList;
import java.util.List;

public class XO extends Game {

    private List<Room> rooms;

    public XO() {
        rooms = new ArrayList<>();
    }

    public List<Room> getRooms() {
        return rooms;
    }

    private void addRoom(Room room){
        rooms.add(room);
    }
}
