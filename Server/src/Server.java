

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Server {
    static Map<String,ClientHandler> users = new ConcurrentHashMap<>();

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
