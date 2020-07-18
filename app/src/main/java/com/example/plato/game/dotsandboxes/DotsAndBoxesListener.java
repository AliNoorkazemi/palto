package com.example.plato.game.dotsandboxes;

import com.example.plato.SplashScreenActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DotsAndBoxesListener implements Runnable {


    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;


    @Override
    public void run() {
        try{
            socket = new Socket(SplashScreenActivity.IP,6666);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            dos.writeUTF("dots and boxes message listener");
            dos.flush();
            String message ;
            while (true){
                message = dis.readUTF();
                switch (message){
                    case "your turn":
//                        DotsAndBoxesActivity.is_my_turn = true;
                        break;
                    case "change":
                        break;
                }
            }
        }catch (IOException io){
            io.printStackTrace();
        }
    }
}
