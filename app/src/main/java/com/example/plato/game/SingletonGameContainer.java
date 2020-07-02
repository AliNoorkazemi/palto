package com.example.plato.game;

import android.util.Log;

import com.example.plato.SingletonUserContainer;

public class SingletonGameContainer {
    private static XO xoInstance;

    private SingletonGameContainer(){
    }

    public static XO getXoInstance(){
        if (xoInstance==null){
            xoInstance=new XO();
            xoInstance.setGame_name("xo");
        }
        return xoInstance;
    }

}
