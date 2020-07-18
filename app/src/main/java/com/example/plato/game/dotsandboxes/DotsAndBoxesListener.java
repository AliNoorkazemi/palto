package com.example.plato.game.dotsandboxes;

import android.content.Context;

import com.example.plato.MainActivity;
import com.example.plato.SplashScreenActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DotsAndBoxesListener implements Runnable {


    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private Context context;
    private OnChangeForGridView onChangeForGridView;
    private OnFinishGame onFinishGame ;
    private String winner;

    DotsAndBoxesListener(Context context) {
        this.context = context;
    }


    @Override
    public void run() {
        try{
            DotAndBoxPageActivity.my_score = 0;
            socket = new Socket(SplashScreenActivity.IP,6666);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            dos.writeUTF("dots and boxes message listener");
            dos.flush();
            dos.writeUTF(MainActivity.userName);
            dos.flush();
            String purpose ;
            LOOP:while (true){
                purpose = dis.readUTF();
                switch (purpose){
                    case "your turn": {
                        DotAndBoxPageActivity.is_my_turn = true;
                    }
                        break;
                    case "change": {
                        int position = dis.readInt();
                        String location_line = dis.readUTF();
                        int color = dis.readInt();
                        onChangeForGridView = DotAndBoxPageActivity.onChangeForGridView;
                        onChangeForGridView.onChangeForGridView(position, location_line, color);
                    }
                        break;
                    case "finish":{
                        dos.writeInt(DotAndBoxPageActivity.my_score);
                        dos.flush();
                        winner = dis.readUTF();
                        break LOOP;
                    }
                }
            }
        }catch (IOException io){
            io.printStackTrace();
        }
        onFinishGame = DotAndBoxPageActivity.onFinishGame ;
        onFinishGame.onFinishGame(winner);
    }

    interface OnChangeForGridView{
        void onChangeForGridView(int position , String location_line , int color);
    }

    interface OnFinishGame{
        void onFinishGame(String winner);
    }
}
