package com.example.plato;

import com.example.plato.Fragment.Friend;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class SingletonUserContainer {
    private static User user;
    private SingletonUserContainer(){

    }

    public static User getInstance(){
        if(user==null){
            user=new User();
        }
        return user;
    }

//    private static void setData() {
//        LinkedList<Friend> friends=new LinkedList<>();
//        for (int i = 0; i < 5; i++) {
//            friends.add(generateUserFriend());
//        }
//        user.setFriends(friends);
//    }

//    private static Friend generateUserFriend() {
//        Friend friend = new Friend();
//        friend.setName("ali nooka");
//        friend.setImg_id(R.drawable.ic_people_24dp);
//
//        ArrayList<String> message = new ArrayList<>();
//        message.add("salam");
//        message.add("hello");
//        message.add("khobi?");
//        message.add("zer nzn");
//
//        ArrayList<Boolean> is_income = new ArrayList<>();
//        is_income.add(true);
//        is_income.add(false);
//        is_income.add(true);
//        is_income.add(false);
//
//        ArrayList<Date> dates = new ArrayList<>();
//        dates.add(new Date());
//        dates.add(new Date());
//        dates.add(new Date());
//        dates.add(new Date());
//
//        friend.setChats_message(message);
//        friend.setIs_it_incomeMessage(is_income);
//        friend.setDates(dates);
//        return friend;
//    }




}
