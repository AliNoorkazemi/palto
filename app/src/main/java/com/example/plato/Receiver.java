package com.example.plato;

import java.io.DataInputStream;
import java.io.IOException;

public class Receiver implements Runnable{
    DataInputStream dis;

    Receiver(DataInputStream dis){
        this.dis=dis;
    }
    @Override
    public void run() {
        try {
            while (true) {
                String message = dis.readUTF();
                switch(message){
                    case "Answer to Add Friend":{
                        NetworkThreadHandler.message=dis.readUTF();
                    }
                    break;
                    case "s":{
                        NetworkThreadHandler.message="OK";
                    }
                    break;
                }
            }
        }catch (IOException io){
            io.printStackTrace();
        }
    }
}
