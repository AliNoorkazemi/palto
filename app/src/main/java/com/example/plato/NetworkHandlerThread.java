package com.example.plato;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class NetworkHandlerThread implements Runnable {

    DataInputStream dis ;
    DataOutputStream dos ;
    Socket socket;

    @Override
    public void run() {
        try {
            socket = new Socket("192.168.2.102", 6666);
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        }catch(IOException io){
            io.printStackTrace();
        }
    }
}
