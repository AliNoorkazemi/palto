import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class ClientHandler implements Runnable {

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;

    ClientHandler(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            List<String>userNames = Server.users.keySet().stream().map(User::getUser_name).collect(Collectors.toList());
            while (true) {
                String message = dis.readUTF();
                if(message.equals("UserEnteredCorrectly")){
                    break;
                }else if (userNames.contains(message)){
                    dos.writeUTF("Duplicated");
                }else{
                    dos.writeUTF("Ok");
                }
            }


        }catch(IOException io){
            io.printStackTrace();
        }
    }
}
