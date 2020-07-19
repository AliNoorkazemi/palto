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
    OnStartDotsAndBoxesGame onStartDotsAndBoxesGame;

    public ChangeInRoomListener(ChangeInRoomListener.onStartGame onStartGame) {
        this.onStartGame = onStartGame;
    }

    public ChangeInRoomListener(OnStartDotsAndBoxesGame onStartDotsAndBoxesGame) {
        this.onStartDotsAndBoxesGame = onStartDotsAndBoxesGame;
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

        int index = 0;
        if (username_thats_want_to_join.equals("remove")) {
            if (which_game.equals("xo")) {
                for (Room room : SingletonGameContainer.getXoInstance().getRooms()) {
                    if (room.getRoom_name().equals(roomName)) {
                        index=SingletonGameContainer.getXoInstance().getRooms().indexOf(room);
                        SingletonGameContainer.getXoInstance().getRooms().remove(room);
                        break;
                    }
                }
            } else if (which_game.equals("guess word")) {
                for (Room room : SingletonGameContainer.getGuessWord().getRooms()) {
                    if (room.getRoom_name().equals(roomName)) {
                        index=SingletonGameContainer.getGuessWord().getRooms().indexOf(room);
                        SingletonGameContainer.getGuessWord().getRooms().remove(room);
                        break;
                    }
                }
            } else if (which_game.equals("dots and boxes")) {
                for (Room room : SingletonGameContainer.getDotsAndBoxes().getRooms()) {
                    if (room.getRoom_name().equals(roomName)) {
                        index=SingletonGameContainer.getDotsAndBoxes().getRooms().indexOf(room);
                        SingletonGameContainer.getDotsAndBoxes().getRooms().remove(room);
                        break;
                    }
                }
            }
        } else if (which_game.equals("xo")) {
            ArrayList<Room> rooms = SingletonGameContainer.getXoInstance().getRooms();
            for (Room room : rooms) {
                if (room.getRoom_name().equals(roomName)) {
                    room.joinRoom(username_thats_want_to_join);
                    index = rooms.indexOf(room);
                    onStartGame.onStart(room);
                    Log.i("where", "changeRoom: " + username_thats_want_to_join);
                    break;
                }
            }
        } else if (which_game.equals("guess word")) {
            List<Room> rooms = SingletonGameContainer.getGuessWord().getRooms();
            for (Room room : rooms) {
                room.joinRoom(username_thats_want_to_join);
                index = rooms.indexOf(room);
                onStartGame.onStart(room);
                break;
            }
        } else if (which_game.equals("dots and boxes")) {
            String state = dis.readUTF();
            List<Room> rooms = SingletonGameContainer.getDotsAndBoxes().getRooms();
            for (Room room : rooms) {
                room.joinRoom(username_thats_want_to_join);
                index = rooms.indexOf(room);
                if (state.equals("start"))
                    onStartDotsAndBoxesGame.onStart(room, true);
                break;
            }
        }


        if (username_thats_want_to_join.equals("remove")) {
            onUpdateUiForDeleteRoom onUpdateUiForDeleteRoom=CasualFrag.onUpdateUiForDeleteRoom;
            onUpdateUiForDeleteRoom.onUpdate(index);
        } else {
            ChangeInRoomListener.onUpdateUiForChangeRoom onUpdateUiForChangeRoom = CasualFrag.onUpdateUiForChangeRoom;
            onUpdateUiForChangeRoom.onUpdate(index);
        }


    }

    public interface onUpdateUiForChangeRoom {
        void onUpdate(int index);
    }

    public interface onUpdateUiForDeleteRoom {
        void onUpdate(int index);
    }

    public interface onStartGame {
        void onStart(Room room);
    }

    public interface OnStartDotsAndBoxesGame {
        void onStart(Room room, boolean is_start);
    }

}
