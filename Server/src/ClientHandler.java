import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class ClientHandler implements Runnable{

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
            List<String>userNames = Server.users.values().stream().map(User::getUser_name).collect(Collectors.toList());
            System.out.println(userNames);
            while (true) {
                String message = dis.readUTF();
                if(message.equals("UserEnteredCorrectly")){
                    User newUser = new User();
                    newUser.setUser_name(dis.readUTF());
                    System.out.println(newUser.getUser_name());
                    newUser.setPassword(dis.readUTF());
                    System.out.println(newUser.getPassword());
                    Server.users.put(newUser.getUser_name(),newUser);
                    Server.usersClientHandler.put(newUser.getUser_name(),this);
//                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Users.txt"));
//                    oos.writeObject(Server.users);
                    break;
                }else if (userNames.contains(message)){
                    dos.writeUTF("Duplicated");
                    dos.flush();
                }else{
                    dos.writeUTF("Ok");
                    dos.flush();
                }
            }


        }catch(IOException io){
            io.printStackTrace();
        }
    }
}
