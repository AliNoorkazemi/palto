

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket ;
    private ObjectInputStream ois;
    private ObjectOutputStream ops;
    private DataOutputStream dos;
    private DataInputStream dis;

    ClientHandler(Socket socket){
        this.socket=socket;
    }
    @Override
    public void run() {
        try{
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        }catch(IOException io){
            io.printStackTrace();
        }
    }
}
