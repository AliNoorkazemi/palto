package com.example.plato.game.startPage.fragment.casual;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.plato.MainActivity;
import com.example.plato.R;
import com.example.plato.SplashScreenActivity;
import com.example.plato.game.Room;
import com.example.plato.game.SingletonGameContainer;
import com.example.plato.game.XOGamePageActivity;
import com.example.plato.game.guessword.GuessWordActivity;
import com.example.plato.game.startPage.StartGamePageActivity;
import com.example.plato.network.AddRoomListener;
import com.example.plato.network.ChangeInRoomListener;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CasualFrag extends Fragment {

    Button create_room_btn;
    RecyclerView recyclerView;
    static AdapterCasual adapter;
    static TextView timer_tv;
    private static CountDownTimer timer;
    private Context context;



    public static ChangeInRoomListener.onUpdateUiForDeleteRoom onUpdateUiForDeleteRoom=new ChangeInRoomListener.onUpdateUiForDeleteRoom() {
        @Override
        public void onUpdate(int index) {
            if (adapter != null) {
                Activity origin = (Activity) adapter.context;
                origin.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyItemRemoved(index);
                        Log.i("where", "onUpdateUiForIAddRoom for casual frag...." + adapter.rooms.size());
                    }
                });
            }
        }
    };

    public static AddRoomListener.onUpdateUiForAddRoom onUpdateUiForAddRoom = new AddRoomListener.onUpdateUiForAddRoom() {
        @Override
        public void onUpdate() {
            if (adapter != null) {
                Activity origin = (Activity) adapter.context;
                origin.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyItemInserted(adapter.rooms.size()-1);
                        Log.i("where", "onUpdateUiForIAddRoom for casual frag...." + adapter.rooms.size());
                    }
                });
            }
        }
    };

    public static ChangeInRoomListener.onUpdateUiForChangeRoom onUpdateUiForChangeRoom = new ChangeInRoomListener.onUpdateUiForChangeRoom() {
        @Override
        public void onUpdate(int index) {
            if (adapter != null) {
                Activity origin = (Activity) adapter.context;
                origin.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyItemChanged(index);
                        Log.i("where", "onUpdateUiforChangeRoom for casual frag...." + adapter.rooms.size());
                    }
                });
            }
        }
    };


    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_casual, container, false);

        Log.i("where", "onCreateView: casualfrag");


        timer_tv = view.findViewById(R.id.tv_timer_casual_frag);
        context = getContext();

        create_room_btn = view.findViewById(R.id.btn_casualFrag_createNewRoom);
        create_room_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(), CreateNewRoomActivity.class);
                Log.i("go to create activity ", "go to create activity ");
                startActivityForResult(intent, 1002);
            }
        });


        initRecycler();


        return view;
    }

    private void initRecycler() {
        recyclerView = view.findViewById(R.id.rc_casualFrag_recycler);
        ArrayList<Room> rooms = new ArrayList<>();
        if (StartGamePageActivity.game_name.equals("xo"))
            rooms = SingletonGameContainer.getXoInstance().getRooms();
        else if (StartGamePageActivity.game_name.equals("guess word"))
            rooms = SingletonGameContainer.getGuessWord().getRooms();
        else if(StartGamePageActivity.game_name.equals("dots and boxes")){
            rooms = SingletonGameContainer.getDotsAndBoxes().getRooms();
        }
        adapter = new AdapterCasual(view.getContext(), rooms, new AdapterCasual.OnItemInCasualClickListener() {
            @Override
            public void onClick(Room room) {
                if (room.getMax_players() > room.getUsers().size()) {
                    if (!room.getUsers().contains(MainActivity.userName)) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Log.i("where", "onclickinCasual  "+room.getRoom_name() );
                                    Socket socket = new Socket(SplashScreenActivity.IP, 6666);
                                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                                    dos.writeUTF("game");
                                    dos.flush();
                                    dos.writeUTF("update");
                                    dos.flush();
                                    dos.writeUTF(StartGamePageActivity.game.getGame_name());
                                    dos.flush();
                                    dos.writeUTF(room.getRoom_name());
                                    dos.flush();
                                    dos.writeUTF(MainActivity.userName);
                                    dos.flush();


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        }).start();

                        if (!StartGamePageActivity.game.getGame_name().equals("dots and boxes")) {
                            if (StartGamePageActivity.game_name.equals("xo")) {
                                Intent intent = new Intent(getActivity(), XOGamePageActivity.class);
                                intent.putExtra("gameState", "Casual");//***
                                intent.putExtra("areYouO", false);
                                intent.putExtra("opponent", room.getUsers().get(0));
                                start_timer(intent , context);
                            } else if (StartGamePageActivity.game_name.equals("guess word")) {
                                Intent intent = new Intent(getActivity(), GuessWordActivity.class);
                                intent.putExtra("gameState", "Casual");
                                intent.putExtra("round", 1);
                                intent.putExtra("opponent", room.getUsers().get(0));
                                start_timer(intent , context);
                            }
                        }
                    }
                }
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    public static void start_timer(Intent mintent , Context mcontext){
        timer_tv.setVisibility(View.VISIBLE);
        timer = new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer_tv.setText(new SimpleDateFormat("ss").format(new Date( millisUntilFinished)));
                if ( timer_tv.getText().toString().equals("00"))
                    timer_tv.setText("Start!");
            }

            public void onFinish() {
                mcontext.startActivity(mintent);
                timer_tv.setVisibility(View.GONE);
            }
        }.start();
    }


}
