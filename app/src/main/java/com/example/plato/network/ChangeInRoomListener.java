package com.example.plato.network;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.example.plato.MainActivity;
import com.example.plato.SingletonUserContainer;
import com.example.plato.SplashScreenActivity;
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
import java.util.List;


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
            socket = new Socket(SplashScreenActivity.IP, 6666);
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

        if(username_thats_want_to_join.equals("remove")){
            if(which_game.equals("xo")){
                for (Room room: SingletonGameContainer.getXoInstance().getRooms()){
                    if (room.getRoom_name().equals(roomName)) {
                        SingletonGameContainer.getXoInstance().getRooms().remove(room);
                        break;
                    }
                }
            }else if(which_game.equals("guess word")){
                for(Room room : SingletonGameContainer.getGuessWord().getRooms()){
                    if(room.getRoom_name().equals(roomName)){
                        SingletonGameContainer.getGuessWord().getRooms().remove(room);
                        break;
                    }
                }
            }
        }
        else if (which_game.equals("xo")) {
            ArrayList<Room> rooms= SingletonGameContainer.getXoInstance().getRooms();
            for (Room room:rooms) {
                if(room.getRoom_name().equals(roomName)){
                    room.joinRoom(username_thats_want_to_join);
                    onStartGame.onStart(room);
                    Log.i("where", "changeRoom: "+username_thats_want_to_join);
                    break;
                }
            }
        }else if (which_game.equals("guess word")) {
            List<Room> rooms = SingletonGameContainer.getGuessWord().getRooms();
            for (Room room : rooms){
                room.joinRoom(username_thats_want_to_join);
                onStartGame.onStart(room);
                break;
            }
        }

        ChangeInRoomListener.onUpdateUiForChangeRoom onUpdateUiForChangeRoom = CasualFrag.onUpdateUiForChangeRoom;
        onUpdateUiForChangeRoom.onUpdate();



    }

    public interface onUpdateUiForChangeRoom {
        void onUpdate();
    }

    public interface onStartGame{
       void onStart(Room room);
    }

}
