package com.example.plato;

public class User {

    private String user_name;
    private String password;

    public User(String user_name, String password) {
        this.password = password;
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
