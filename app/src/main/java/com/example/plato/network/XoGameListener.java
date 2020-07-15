package com.example.plato.network;

import android.content.Intent;

import com.example.plato.MainActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class XoGameListener extends Thread {

   private ListenerForTurnInXoGame listenerForTurnInXoGame;

    public XoGameListener(ListenerForTurnInXoGame listenerForTurnInXoGame) {
        this.listenerForTurnInXoGame = listenerForTurnInXoGame;
    }

    @Override
    public void run() {
        try {
            Socket socket=new Socket("192.168.2.102",6666);
            DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
            DataInputStream dis=new DataInputStream(socket.getInputStream());
            dos.writeUTF("XoListener");
            dos.flush();
            dos.writeUTF(MainActivity.userName);
            dos.flush();

            while (true){
                int index =dis.readInt();
                listenerForTurnInXoGame.onListen(true,index);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public interface ListenerForTurnInXoGame{
        void onListen(Boolean are_you, int index);
    }
}
