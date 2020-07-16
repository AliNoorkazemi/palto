package com.example.plato.game.startPage.fragment.casual;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;
import android.widget.Toast;

import com.example.plato.MainActivity;
import com.example.plato.R;
import com.example.plato.SingletonUserContainer;
import com.example.plato.SplashScreenActivity;
import com.example.plato.game.Room;
import com.example.plato.game.SingletonGameContainer;
import com.example.plato.game.XOGamePageActivity;
import com.example.plato.game.guessword.GuessWordActivity;
import com.example.plato.game.startPage.StartGamePageActivity;
import com.example.plato.network.AddRoomListener;
import com.example.plato.network.ChangeInRoomListener;
import com.example.plato.network.MessageListener;
import com.example.plato.network.XoGameListener;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class CasualFrag extends Fragment {

    Button create_room_btn;
    RecyclerView recyclerView;
    static AdapterCasual adapter;


    public static AddRoomListener.onUpdateUiForAddRoom onUpdateUiForAddRoom=new AddRoomListener.onUpdateUiForAddRoom() {
        @Override
        public void onUpdate() {
            if (adapter != null) {
                Activity origin = (Activity) adapter.context;
                origin.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        Log.i("where", "onUpdateUiForIAddRoom for casual frag...."+adapter.rooms.size());
                    }
                });
            }
        }
    };

    public static ChangeInRoomListener.onUpdateUiForChangeRoom onUpdateUiForChangeRoom=new ChangeInRoomListener.onUpdateUiForChangeRoom() {
        @Override
        public void onUpdate() {
            if (adapter != null) {
                Activity origin = (Activity) adapter.context;
                origin.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                        Log.i("where", "onUpdateUiforChangeRoom for casual frag...."+adapter.rooms.size());
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
        Log.i("where", "onCreateView: ");

        view=inflater.inflate(R.layout.fragment_casual, container, false);


        create_room_btn=view.findViewById(R.id.btn_casualFrag_createNewRoom);
        create_room_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(),CreateNewRoomActivity.class);
                Log.i("go to create activity " , "go to create activity ");
                startActivityForResult(intent,1002);
            }
        });


        initRecycler();



        return view;
    }

    private void initRecycler() {
        recyclerView=view.findViewById(R.id.rc_casualFrag_recycler);
        ArrayList<Room> rooms = new ArrayList<>();
        if(StartGamePageActivity.game_name.equals("xo"))
            rooms = SingletonGameContainer.getXoInstance().getRooms();
        else if (StartGamePageActivity.game_name.equals("guess word"))
            rooms = SingletonGameContainer.getGuessWord().getRooms();
        adapter=new AdapterCasual(view.getContext(),rooms, new AdapterCasual.OnItemInCasualClickListener() {
            @Override
            public void onClick(Room room) {
                if(room.getMax_players()>room.getUsers().size()){
                    if(!room.getUsers().contains(MainActivity.userName)){
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Socket socket=new Socket(SplashScreenActivity.IP,6666);
                                    DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
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

                        ProgressDialog progressDialog;
                        progressDialog = new ProgressDialog(getActivity());
                        progressDialog.setTitle("joining to the game...");
                        progressDialog.setIndeterminate(true);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.show();

                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                int jump = 0;
                                while (jump < 10) {
                                    try {
                                        sleep(200);
                                        jump+=5;
                                        progressDialog.setProgress(jump);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                                progressDialog.dismiss();
                            }
                        };
                        thread.start();


                        if (StartGamePageActivity.game_name.equals("xo")){
                            Intent intent=new Intent(getActivity(),XOGamePageActivity.class);
                            intent.putExtra("gameState","Casual");//***
                            intent.putExtra("areYouO",false);
                            intent.putExtra("opponent",room.getUsers().get(0));
                            startActivity(intent);
                        }else if (StartGamePageActivity.game_name.equals("guess word")){
                            Intent intent = new Intent(getActivity(), GuessWordActivity.class);
                            intent.putExtra("areYouO",false);
                            intent.putExtra("round", 1);
                            intent.putExtra("opponent",room.getUsers().get(0));
                            startActivity(intent);
                        }

                    }
                }
            }
        });
        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(requestCode==1002 && resultCode==RESULT_OK ){
//            rooms.clear();
//            rooms.addAll(SingletonGameContainer.getXoInstance().getRooms());
//            adapter.notifyItemInserted(rooms.size()-1);
//        }
    }
}
