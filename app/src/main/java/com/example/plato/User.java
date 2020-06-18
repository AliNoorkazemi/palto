package com.example.plato;

public class User {

    private String password;
    private String user_name;

    public User(String password, String user_name) {
        this.password = password;
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public String getUser_name() {
        return user_name;
    }
}
