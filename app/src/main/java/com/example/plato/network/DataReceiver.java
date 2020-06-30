package com.example.plato.network;

import android.util.Log;

import com.example.plato.Fragment.Friend;
import com.example.plato.MainActivity;
import com.example.plato.SingletonUserContainer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

public class DataReceiver extends Thread {



    @Override
    public void run() {

        /*
        Update singleton user container for login after logout.
         */

        SingletonUserContainer.getInstance().friends = new LinkedList<Friend>();

        /*
        Receive data from server .
         */
        try{
            Socket socket = new Socket("192.168.2.102", 6666);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("giveData");
            dos.flush();
            dos.writeUTF(MainActivity.userName);
            dos.flush();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Map<String, ArrayList<String>> friendName_to_message = (Map<String,ArrayList<String>>)ois.readObject();
            Map<String,ArrayList<Date>> friendName_to_messageTime=(Map<String,ArrayList<Date>>)ois.readObject();
            Map<String,ArrayList<Boolean>> friendsName_to_messageBoolean=(Map<String,ArrayList<Boolean>>)ois.readObject();
            MainActivity.friend_names  = new ArrayList<>(friendName_to_message.keySet());
            String message = String.valueOf(MainActivity.friend_names.size());
            Log.i("LOG",  message);
            if(MainActivity.friend_names.size()>0)
                Log.i("LOG",  MainActivity.friend_names.get(0));
            for(int i = 0 ; i < friendName_to_message.size() ;i++){
                Friend friend = new Friend();
                friend.setName(MainActivity.friend_names.get(i));
                friend.setChats_message(friendName_to_message.get(MainActivity.friend_names.get(i)));
                friend.setDates(friendName_to_messageTime.get(MainActivity.friend_names.get(i)));
                friend.setIs_it_incomeMessage(friendsName_to_messageBoolean.get(MainActivity.friend_names.get(i)));
                SingletonUserContainer.getInstance().friends.add(friend);
            }
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("All lists received");
            dos.flush();
        } catch (IOException | ClassNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }
    }
}

