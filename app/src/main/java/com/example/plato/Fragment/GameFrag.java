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
import com.example.plato.game.Room;
import com.example.plato.game.SingletonGameContainer;
import com.example.plato.game.XOGamePageActivity;
import com.example.plato.game.startPage.StartGamePageActivity;
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
                            Socket socket = new Socket("192.168.1.4", 6666);
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
                            intent.putExtra("GAME", SingletonGameContainer.getXoInstance());
                            new AddRoomListener().start();
                            new ChangeInRoomListener(new ChangeInRoomListener.onStartGame() {
                                @Override
                                public void onStart(String username) {
                                    if (username.equals(MainActivity.userName)) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Log.i("where", "run: "+username);
                                                Intent intent=new Intent(view.getContext(),XOGamePageActivity.class);
                                                startActivity(intent);
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
