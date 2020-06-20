import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.stream.Collectors;

public class ClientHandler implements Runnable {

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;


    ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            List<String> userNames = Server.users.values().stream().map(User::getUser_name).collect(Collectors.toList());
            System.out.println(userNames);
            String purpose = dis.readUTF();
            if (purpose.equals("Register")) {
                System.out.println("user is trying to registering...");
                while (true) {
                    String message = dis.readUTF();
                    if (message.equals("UserEnteredCorrectly")) {
                        User newUser = new User();
                        newUser.setUser_name(dis.readUTF());
                        newUser.setPassword(dis.readUTF());
                        Server.users.put(newUser.getUser_name(), newUser);
                        Server.usersClientHandler.put(newUser.getUser_name(), this);
//                    ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("Users.txt"));
//                    oos.writeObject(Server.users);
                        break;
                    } else if (userNames.contains(message)) {
                        dos.writeUTF("Duplicated");
                        dos.flush();
                    }
                }
            }else if(purpose.equals("Login")){
                System.out.println("user trying to logging in");
                String userName=null;
                String password=null;
                while (true){
                    userName = dis.readUTF();
                    password = dis.readUTF();
                    if(!userNames.contains(userName)){
                        dos.writeUTF("ERROR:this userName don't exist...");
                        dos.flush();
                        continue;
                    }else{
                        User currentUser = Server.users.get(userName);
                        if(!password.equals(currentUser.getPassword())){
                            dos.writeUTF("ERROR:password is't correct...");
                            dos.flush();
                            continue;
                        }
                    }
                    dos.writeUTF("OK");
                    dos.flush();
                    break;
                }
            }
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
