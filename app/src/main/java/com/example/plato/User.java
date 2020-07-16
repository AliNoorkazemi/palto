package com.example.plato;

import com.example.plato.Fragment.Friend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class User implements Serializable {
    public LinkedList<Friend> friends;

    public String myName;

    private ArrayList<Integer> gameScore; // 0->xo 1->guessWord 2->dotandbox

    public ArrayList<Integer> getGameScore() {
        if(gameScore==null){
            gameScore=new ArrayList<>();
            gameScore.add(0);
            gameScore.add(0);
            gameScore.add(0);
        }
        return gameScore;
    }

    public void setGameScore(ArrayList<Integer> gameScore) {
        this.gameScore = gameScore;
    }

    public String getMyName() {
        return myName;
    }

    public void setMyName(String myName) {
        this.myName = myName;
    }

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
