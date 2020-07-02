package com.example.plato.game;

import android.util.Log;

import com.example.plato.SingletonUserContainer;
import com.example.plato.game.guessword.GuessWord;

public class SingletonGameContainer {
    private static XO xoInstance;
    private static GuessWord guessWordInstance;

    private SingletonGameContainer(){
    }

    public static XO getXoInstance(){
        if (xoInstance==null){
            Log.i("where", "getXoInstance: ");
            xoInstance=new XO();
            xoInstance.setGame_name("XO");
        }
        return xoInstance;
    }

    public static GuessWord getGuessWord(){
        if(guessWordInstance==null){
            guessWordInstance = new GuessWord();
            guessWordInstance.setGame_name("Guess Word");
        }
        return guessWordInstance;
    }

}
