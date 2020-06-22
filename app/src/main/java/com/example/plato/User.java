package com.example.plato;

import com.example.plato.Fragment.Friend;

import java.util.ArrayList;
import java.util.LinkedList;

public class User {
    private LinkedList<Friend> friends;

    public LinkedList<Friend> getFriends() {
        return friends;
    }

    public void setFriends(LinkedList<Friend> friends) {
        this.friends = friends;
    }
}
