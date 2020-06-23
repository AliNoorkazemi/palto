package com.example.plato;

import com.example.plato.Fragment.Friend;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class NetworkThreadHandler extends Thread {

    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private User currentUser ;



    @Override
    public void run() {
        /*
        received data from server ....
         */
        try{
            socket = new Socket("192.168.2.102", 6666);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("Service");
            dos.flush();
            dos.writeUTF(MainActivity.userName);
            dos.flush();
            currentUser = new User();
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Map<String, ArrayList<String>> friendName_to_message = (Map<String,ArrayList<String>>)ois.readObject();
            Map<String,ArrayList<Date>> friendName_to_messageTime=(Map<String,ArrayList<Date>>)ois.readObject();
            Map<String,ArrayList<Boolean>> friendsName_to_messageBoolean=(Map<String,ArrayList<Boolean>>)ois.readObject();
            ArrayList<String> friendNames = new ArrayList<>(friendName_to_message.keySet());
            for(int i = 0 ; i < friendName_to_message.size() ;i++){
                Friend friend = new Friend();
                friend.setName(friendNames.get(i));
                friend.setChats_message(friendName_to_message.get(friendNames.get(i)));
                friend.setDates(friendName_to_messageTime.get(friendNames.get(i)));
                friend.setIs_it_incomeMessage(friendsName_to_messageBoolean.get(friendNames.get(i)));
                currentUser.friends.add(new Friend());
            }
            dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF("All lists received");
            dos.flush();
            SingletonUserContainer.getInstance().setFriends(currentUser.friends);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


        /*
        thread to message listener....
         */


        Thread messageListener = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    while (true) {
                        String message = dis.readUTF();
                    }
                }catch (IOException io){
                    io.printStackTrace();
                }
            }
        });
        messageListener.start();




    }
}
