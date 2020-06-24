package com.example.plato.Fragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Friend implements Serializable {
    private String name;
    private int img_id;
    ArrayList<String> chats_message;
    ArrayList<Boolean> is_it_incomeMessage;
    ArrayList<Date> dates;



    public ArrayList<Date> getDates() {
        if(dates==null)
            dates = new ArrayList<Date>();
        return dates;
    }

    public void setDates(ArrayList<Date> dates) {
        this.dates=new ArrayList<>();
        this.dates = dates;
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

    public ArrayList<Boolean> getIs_it_incomeMessage() {
        if(is_it_incomeMessage==null)
            is_it_incomeMessage=new ArrayList<Boolean>();
        return is_it_incomeMessage;
    }

    public void setIs_it_incomeMessage(ArrayList<Boolean> is_it_incomeMessage) {
        this.is_it_incomeMessage=new ArrayList<>();
        this.is_it_incomeMessage.addAll(is_it_incomeMessage);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImg_id() {
        return img_id;
    }

    public void setImg_id(int img_id) {
        this.img_id = img_id;
    }


}
