package com.example.plato;

import com.example.plato.Fragment.Friend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class User implements Serializable {
    public LinkedList<Friend> friends;

    public LinkedList<Friend> getFriends() {
        if(friends==null){
            friends=new LinkedList<Friend>();
        }
        return friends;
    }

    public void setFriends(LinkedList<Friend> friends) {
        this.friends = friends;
    }
}
