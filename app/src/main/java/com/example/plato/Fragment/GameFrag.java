package com.example.plato.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.plato.MainActivity;
import com.example.plato.R;
import com.example.plato.SplashScreenActivity;
import com.example.plato.game.Room;
import com.example.plato.game.SingletonGameContainer;
import com.example.plato.game.XOGamePageActivity;
import com.example.plato.game.dotsandboxes.DotAndBoxPageActivity;
import com.example.plato.game.guessword.WaitingForGuessActivity;
import com.example.plato.game.startPage.StartGamePageActivity;
import com.example.plato.game.startPage.fragment.casual.CasualFrag;
import com.example.plato.network.AddRoomListener;
import com.example.plato.network.ChangeInRoomListener;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Map;

public class GameFrag extends Fragment {
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_game, container, false);

        Button xo_game_btn = view.findViewById(R.id.btn_gameFrag_xo);
        xo_game_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Socket socket = new Socket(SplashScreenActivity.IP, 6666);
                            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                            dos.writeUTF("game");
                            dos.writeUTF("getAllRooms");
                            dos.writeUTF("xo");

                            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                            Map<String, ArrayList<String>> roomName_to_joined_user = (Map<String, ArrayList<String>>) ois.readObject();
                            Map<String, Integer> roomName_to_maxPlayers = (Map<String, Integer>) ois.readObject();
                            ArrayList<Room> rooms = new ArrayList<>();
                            for (String str : roomName_to_joined_user.keySet()
                            ) {
                                Room room = new Room();
                                room.setGame_name("xo");
                                room.setRoom_name(str);
                                room.setMax_players(roomName_to_maxPlayers.get(str));
                                ArrayList<String> joined_user = roomName_to_joined_user.get(str);
                                for (int i = 0; i < joined_user.size(); i++) {
                                    room.joinRoom(joined_user.get(i));
                                }
                                rooms.add(room);
                            }

                            SingletonGameContainer.getXoInstance().setRooms(rooms);


                            Intent intent = new Intent(view.getContext(), StartGamePageActivity.class);
                            intent.putExtra("game name","xo");
                            intent.putExtra("GAME", SingletonGameContainer.getXoInstance());
                            new AddRoomListener().start();
                            new ChangeInRoomListener(new ChangeInRoomListener.onStartGame() {
                                @Override
                                public void onStart(Room room) {
                                    if (room.getUsers().get(0).equals(MainActivity.userName)) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {

                                                Intent intent=new Intent(view.getContext(),XOGamePageActivity.class);
                                                intent.putExtra("gameState","Casual");//***
                                                intent.putExtra("areYouO",true);
                                                intent.putExtra("opponent",room.getUsers().get(1));
                                                intent.putExtra("RoomName",room.getRoom_name());
                                                CasualFrag.start_timer(intent ,  view.getContext());
                                            }
                                        });
                                    }
                                }

                            }).start();
                            startActivity(intent);


                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();


            }
        });


        Button guessWord_game_btn = view.findViewById(R.id.btn_gameFrag_guessTheWord);
        guessWord_game_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            Socket socket = new Socket(SplashScreenActivity.IP, 6666);
                            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                            dos.writeUTF("game");
                            dos.writeUTF("getAllRooms");
                            dos.writeUTF("guess word");

                            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                            Map<String, ArrayList<String>> roomName_to_joined_user = (Map<String, ArrayList<String>>) ois.readObject();
                            Map<String, Integer> roomName_to_maxPlayers = (Map<String, Integer>) ois.readObject();
                            ArrayList<Room> rooms = new ArrayList<>();

                            for (String str : roomName_to_joined_user.keySet()) {
                                Room room = new Room();
                                room.setGame_name("guess word");
                                room.setRoom_name(str);
                                room.setMax_players(roomName_to_maxPlayers.get(str));
                                ArrayList<String> joined_user = roomName_to_joined_user.get(str);
                                for (int i = 0; i < joined_user.size(); i++) {
                                    room.joinRoom(joined_user.get(i));
                                }
                                rooms.add(room);
                            }

                            SingletonGameContainer.getGuessWord().setRooms(rooms);

                            Intent intent = new Intent(view.getContext(), StartGamePageActivity.class);
                            intent.putExtra("game name","guess word");
                            intent.putExtra("GAME", SingletonGameContainer.getGuessWord());
                            new AddRoomListener().start();
                            new ChangeInRoomListener(new ChangeInRoomListener.onStartGame() {
                                @Override
                                public void onStart(Room room) {
                                    Log.i("nafar aval",room.getUsers().get(0));
                                    if (room.getUsers().get(0).equals(MainActivity.userName)) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent=new Intent(view.getContext(), WaitingForGuessActivity.class);
                                                intent.putExtra("gameState","Casual");
                                                intent.putExtra("round",1);
                                                intent.putExtra("opponent",room.getUsers().get(1));
                                                intent.putExtra("RoomName",room.getRoom_name());
                                                CasualFrag.start_timer(intent , view.getContext());
                                            }
                                        });
                                    }
                                }

                            }).start();
                            startActivity(intent);


                        }catch(IOException | ClassNotFoundException io){
                            io.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        Button dotsAndBoxes=view.findViewById(R.id.btn_gameFrag_dotAndBox);
        dotsAndBoxes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Socket socket = new Socket(SplashScreenActivity.IP, 6666);
                            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                            dos.writeUTF("game");
                            dos.writeUTF("getAllRooms");
                            dos.writeUTF("dots and boxes");

                            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                            Map<String, ArrayList<String>> roomName_to_joined_user = (Map<String, ArrayList<String>>) ois.readObject();
                            Map<String, Integer> roomName_to_maxPlayers = (Map<String, Integer>) ois.readObject();
                            ArrayList<Room> rooms = new ArrayList<>();
                            for (String str : roomName_to_joined_user.keySet()
                            ) {
                                Room room = new Room();
                                room.setGame_name("dots and boxes");
                                room.setRoom_name(str);
                                room.setMax_players(roomName_to_maxPlayers.get(str));
                                ArrayList<String> joined_user = roomName_to_joined_user.get(str);
                                for (int i = 0; i < joined_user.size(); i++) {
                                    room.joinRoom(joined_user.get(i));
                                }
                                rooms.add(room);
                            }

                            SingletonGameContainer.getDotsAndBoxes().setRooms(rooms);


                            Intent intent = new Intent(view.getContext(), StartGamePageActivity.class);
                            intent.putExtra("game name","dots and boxes");
                            intent.putExtra("GAME", SingletonGameContainer.getDotsAndBoxes());
                            new AddRoomListener().start();
                            new ChangeInRoomListener(new ChangeInRoomListener.OnStartDotsAndBoxesGame() {
                                @Override
                                public void onStart(Room room, boolean is_start) {
                                    if(is_start && room.getUsers().contains(MainActivity.userName)){
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Intent intent=new Intent(view.getContext(), DotAndBoxPageActivity.class);
                                                intent.putExtra("gameState","Casual");//***
                                                intent.putExtra("RoomName",room.getRoom_name());
                                                CasualFrag.start_timer(intent , view.getContext());
                                            }
                                        });
                                    }
                                }
                            }).start();
                            startActivity(intent);
                        } catch (IOException | ClassNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

            }
        });


        return view;
    }
}
