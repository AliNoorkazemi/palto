package com.example.plato;

public class SingletonUserContainer {

    private static User user;

    private SingletonUserContainer(){}

    public static User getInstance(){
        if(user==null){
            user=new User();
        }
        return user;
    }
}
