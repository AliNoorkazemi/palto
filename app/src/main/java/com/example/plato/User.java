package com.example.plato;

import java.io.Serializable;

public class User implements Serializable {

    private String password;
    private String user_name;

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public String getUser_name() {
        return user_name;
    }
}
