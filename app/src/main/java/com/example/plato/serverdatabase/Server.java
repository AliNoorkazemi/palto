package com.example.plato.serverdatabase;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {

        try {
            ServerSocket serverSocket = null;
            serverSocket = new ServerSocket();

            while (true) {
                Socket socket = serverSocket.accept();
                Thread clientHandler = new Thread(new ClientHandler(socket));
                clientHandler.start();
            }
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
