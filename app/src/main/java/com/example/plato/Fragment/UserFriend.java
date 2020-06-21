package com.example.plato.Fragment;

import com.example.plato.Fragment.Chat.ChatContent;

import java.util.ArrayList;

public class UserFriend {
    private ChatContent chatContent;
    private String name;
    private int img_id;

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

    public ChatContent getChatContent() {
        return chatContent;
    }

    public void setChatContent(ChatContent chatContent) {
        this.chatContent = chatContent;
    }
}
