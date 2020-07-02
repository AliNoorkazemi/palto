package com.example.plato.network;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.example.plato.MainActivity;
import com.example.plato.game.Room;
import com.example.plato.game.SingletonGameContainer;
import com.example.plato.game.XOGamePageActivity;
import com.example.plato.game.startPage.StartGamePageActivity;
import com.example.plato.game.startPage.fragment.casual.CasualFrag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;


public class ChangeInRoomListener extends Thread {
    Socket socket;
    DataOutputStream dos;
    DataInputStream dis;
    onStartGame onStartGame;

    public ChangeInRoomListener(ChangeInRoomListener.onStartGame onStartGame) {
        this.onStartGame = onStartGame;
    }

    @Override
    public void run() {
        try {
            Log.i("message", "enter to listening before connecting...");
            socket = new Socket("192.168.1.4", 6666);
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("changeRoom");
            dos.writeUTF(MainActivity.userName);
            dis = new DataInputStream(socket.getInputStream());
            while (true) {
                changeRoom();
            }


        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private void changeRoom() throws IOException {
        String which_game = dis.readUTF();
        String roomName = dis.readUTF();
        String username_thats_want_to_join = dis.readUTF();

        Log.i("where", "changeRoom: ");
        if (which_game.equals("xo")) {
            ArrayList<Room> rooms=SingletonGameContainer.getXoInstance().getRooms();
            for (Room room:rooms
                 ) {
                if(room.getRoom_name().equals(roomName)){
                    room.joinRoom(username_thats_want_to_join);
                    onStartGame.onStart(room.getUsers().get(0));
                    Log.i("where", "changeRoom: "+username_thats_want_to_join);
                    break;
                }
            }
        }

        ChangeInRoomListener.onUpdateUiForChangeRoom onUpdateUiForChangeRoom = CasualFrag.onUpdateUiForChangeRoom;
        onUpdateUiForChangeRoom.onUpdate();



    }

    public interface onUpdateUiForChangeRoom {
        void onUpdate();
    }

    public interface onStartGame{
       void onStart(String username);
    }

}
