package com.example.plato.network;


import android.util.Log;

import com.example.plato.Fragment.Chat.ChatFrag;
import com.example.plato.Fragment.Chat.chatPage.ChatPageActivity;
import com.example.plato.Fragment.Friend;
import com.example.plato.Fragment.friends.FriendFrag;
import com.example.plato.MainActivity;
import com.example.plato.SingletonUserContainer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Date;

public class MessageListener extends Thread {

    private OnUpdateUiForIncomingMessage onUpdateUiForIncomingMessage;
    private Socket socket;

    @Override
    public void run() {
        try{
            socket = new Socket("192.168.2.102", 6666);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("messageListener");
            dos.writeUTF(MainActivity.userName);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            while (true){
                String sender_name = dis.readUTF();
                String message = dis.readUTF();
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Date time = (Date)ois.readObject();
                dis = new DataInputStream(socket.getInputStream());
                if(MainActivity.friend_names.contains(sender_name)){
                    Friend friend = SingletonUserContainer.getInstance().getTargetFriend(sender_name);
                    friend.getChats_message().add(message);
//                    friend.getIs_it_incomeMessage().add(true);
                    friend.getType_of_messages().add(0);
                    friend.getDates().add(time);
                }else{
                    MainActivity.friend_names.add(sender_name);
                    Friend friend = new Friend();
                    friend.setName(sender_name);
                    friend.getChats_message().add(message);
//                    friend.getIs_it_incomeMessage().add(true);
                    friend.getType_of_messages().add(0);
                    friend.getDates().add(time);
                    SingletonUserContainer.getInstance().getFriends().add(friend);
                }
                onUpdateUiForIncomingMessage = FriendFrag.onUpdateUiForIncomingMessage;
                onUpdateUiForIncomingMessage.onUpdateUiForIncomingMessage();
                onUpdateUiForIncomingMessage = ChatFrag.onUpdateUiForIncomingMessage;
                onUpdateUiForIncomingMessage.onUpdateUiForIncomingMessage();
                onUpdateUiForIncomingMessage = ChatPageActivity.onUpdateUiForIncomingMessage;
                onUpdateUiForIncomingMessage.onUpdateUiForIncomingMessage();
            }
        }catch (IOException | ClassNotFoundException io){
            io.printStackTrace();
        }
    }

    public void close(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("192.168.2.102", 6666);
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF("offline");
                    dos.flush();
                    dos.writeUTF(MainActivity.userName);
                    dos.flush();
                    Log.i("message","ERRRksdjflafdjkldfjas;ldfkjaslfjdslkfjklsajflj");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public static interface OnUpdateUiForIncomingMessage{
        void onUpdateUiForIncomingMessage();
    }
}



