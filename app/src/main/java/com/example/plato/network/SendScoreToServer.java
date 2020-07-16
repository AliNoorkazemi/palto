package com.example.plato.network;

import com.example.plato.MainActivity;
import com.example.plato.SplashScreenActivity;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SendScoreToServer extends Thread {
    private int score;
    private int game;
    public SendScoreToServer(int game,int score) {
        this.score = score;
        this.game=game;
    }

    @Override
    public void run() {
        try {
            Socket socket=new Socket(SplashScreenActivity.IP,6666);
            DataOutputStream dataOutputStream=new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeUTF("changeGameScore");
            dataOutputStream.flush();
            dataOutputStream.writeUTF(MainActivity.userName);
            dataOutputStream.flush();
            dataOutputStream.writeInt(game);
            dataOutputStream.flush();
            dataOutputStream.writeInt(score);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
