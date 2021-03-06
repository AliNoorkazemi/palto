package com.example.plato.network;

import android.util.Log;

import com.example.plato.Fragment.Chat.ChatFrag;
import com.example.plato.Fragment.Chat.chatPage.ChatPageActivity;
import com.example.plato.Fragment.Friend;
import com.example.plato.Fragment.friends.FriendFrag;
import com.example.plato.MainActivity;
import com.example.plato.SingletonUserContainer;
import com.example.plato.SplashScreenActivity;
import com.example.plato.game.Room;
import com.example.plato.game.SingletonGameContainer;
import com.example.plato.game.startPage.fragment.casual.CasualFrag;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

public class AddRoomListener extends Thread {

    Socket socket;
    DataOutputStream dos;
    DataInputStream dis;

    @Override
    public void run() {
        try {
            Log.i("message", "enter to listening before connecting...");
            socket = new Socket(SplashScreenActivity.IP, 6666);
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("addRoom");
            dos.writeUTF(MainActivity.userName);
            dis = new DataInputStream(socket.getInputStream());
            while (true) {
                addRoom();
            }


        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    private void addRoom() throws IOException {
        String which_game = dis.readUTF();
        String roomName = dis.readUTF();
        int length = dis.readInt();
        ArrayList<String> usersjoined = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            String userjoined = dis.readUTF();
            usersjoined.add(userjoined);
        }
        Integer maxPlayer = Integer.valueOf(dis.readUTF());


        Room room = new Room();
        room.setGame_name(which_game);
        room.setRoom_name(roomName);
        room.setMax_players(maxPlayer);
        for (int i = 0; i < usersjoined.size(); i++) {
            room.joinRoom(usersjoined.get(i));
        }
        Log.i("where", "addRoom: " + "max" + maxPlayer + roomName);

        if (which_game.equals("xo"))
            SingletonGameContainer.getXoInstance().getRooms().add(room);
        else if (which_game.equals("guess word"))
            SingletonGameContainer.getGuessWord().getRooms().add(room);
        else if (which_game.equals("dots and boxes")) {
            SingletonGameContainer.getDotsAndBoxes().getRooms().add(room);
        }

        onUpdateUiForAddRoom onUpdateUiForAddRoom = CasualFrag.onUpdateUiForAddRoom;
        onUpdateUiForAddRoom.onUpdate();

    }

    public interface onUpdateUiForAddRoom {
        void onUpdate();
    }

}
