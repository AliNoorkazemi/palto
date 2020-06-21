package com.example.plato.Fragment.Chat;

import java.io.Serializable;
import java.util.ArrayList;

public class ChatContent implements Serializable {
    ArrayList<String> chats_message;
    ArrayList<Boolean> is_it_incomeMessage;

    public ArrayList<String> getChats_message() {
        return chats_message;
    }

    public void setChats_message(ArrayList<String> chats_message) {
        this.chats_message = chats_message;
    }

    public ArrayList<Boolean> getIs_it_incomeMessage() {
        return is_it_incomeMessage;
    }

    public void setIs_it_incomeMessage(ArrayList<Boolean> is_it_incomeMessage) {
        this.is_it_incomeMessage = is_it_incomeMessage;
    }
}
