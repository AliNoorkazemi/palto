package com.example.plato;

import com.example.plato.Fragment.Friend;

import java.util.ArrayList;
import java.util.LinkedList;

public class User {
    public LinkedList<Friend> friends;

    public LinkedList<Friend> getFriends() {
        if(friends==null){
            friends=new LinkedList<Friend>();
        }
        return friends;
    }

    public Friend getTargetFriend(String targetName){
        Friend friend=null;
        for(int i = 0 ; i < friends.size();i++){
            if(friends.get(i).getName().equals(targetName))
                return friends.get(i);
        }
        return friend;
    }

    public void setFriends(LinkedList<Friend> friends) {
        this.friends = friends;
    }
}
