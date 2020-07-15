package com.example.plato.game.guessword;

import com.example.plato.MainActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GuessWordListener extends Thread {

    private Socket socket ;
    private DataInputStream dis ;
    DataOutputStream dos;
    private GuessWordActivity.SetOnGetWord setOnGetWord;

    @Override
    public void run() {
        try{
            socket = new Socket("192.168.2.102",6666);
            dis = new DataInputStream(socket.getInputStream());
            dos =new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("guess word listener");
            dos.flush();
            dos.writeUTF(MainActivity.userName);
            dos.flush();
            String word = dis.readUTF();
            setOnGetWord = GuessWordActivity.setOnGetWord;
            setOnGetWord.onGetWord(word);
            while(true){

            }
        }catch (IOException io){
            io.printStackTrace();
        }
    }
}
