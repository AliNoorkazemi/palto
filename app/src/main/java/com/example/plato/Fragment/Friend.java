package com.example.plato.Fragment;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Friend implements Serializable {
    private String name;
    private String img_str;
    ArrayList<String> chats_message;
//    ArrayList<Boolean> is_it_incomeMessage;
    ArrayList<Date> dates;
    ArrayList<Integer> type_of_messages;

    public String getImg_str() {
        return img_str;
    }

    public void setImg_str(String img_str) {
        this.img_str = img_str;
    }

    public ArrayList<Date> getDates() {
        if(dates==null)
            dates = new ArrayList<Date>();
        return dates;
    }

    public void setDates(ArrayList<Date> dates) {
        this.dates=new ArrayList<>();
        this.dates = dates;
    }

    public ArrayList<Integer> getType_of_messages() {
        if(type_of_messages==null)
            type_of_messages = new ArrayList<Integer>();
        return type_of_messages;
    }

    public void setType_of_messages(ArrayList<Integer> type_of_messages) {
        this.type_of_messages = new ArrayList<Integer>();
        this.type_of_messages = type_of_messages;
    }

    public ArrayList<String> getChats_message() {
        if(chats_message==null)
            chats_message = new ArrayList<String>();
        return chats_message;
    }

    public void setChats_message(ArrayList<String> chats_message) {
        this.chats_message=new ArrayList<>();
        this.chats_message.addAll(chats_message);
    }
//
//    public ArrayList<Boolean> getIs_it_incomeMessage() {
//        if(is_it_incomeMessage==null)
//            is_it_incomeMessage=new ArrayList<Boolean>();
//        return is_it_incomeMessage;
//    }

//    public void setIs_it_incomeMessage(ArrayList<Boolean> is_it_incomeMessage) {
//        this.is_it_incomeMessage=new ArrayList<>();
//        this.is_it_incomeMessage.addAll(is_it_incomeMessage);
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
