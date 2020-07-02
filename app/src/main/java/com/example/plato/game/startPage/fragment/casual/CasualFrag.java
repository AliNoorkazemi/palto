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
import com.example.plato.game.Room;
import com.example.plato.game.SingletonGameContainer;
import com.example.plato.game.XOGamePageActivity;
import com.example.plato.network.AddRoomListener;
import com.example.plato.network.MessageListener;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

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
                        Log.i("where", "onUpdateUiForIncomingMessage for casual frag...."+adapter.rooms.size());
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
                startActivityForResult(new Intent(view.getContext(),CreateNewRoomActivity.class),1002);
            }
        });


        initRecycler();



        return view;
    }

    private void initRecycler() {
        recyclerView=view.findViewById(R.id.rc_casualFrag_recycler);
        adapter=new AdapterCasual(view.getContext(),SingletonGameContainer.getXoInstance().getRooms(), new AdapterCasual.OnItemInCasualClickListener() {
            @Override
            public void onClick(Room room) {
                if(room.getMax_players()>room.getUsers().size()){
                    if(!room.getUsers().contains(MainActivity.userName)){
                        TextView room_state=view.findViewById(R.id.tv_itemInRecycler_casualFrag_state);
                        room_state.setBackground(getActivity().getDrawable(R.drawable.gray_boarder));
                        room_state.setText("watch");

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
                                while (jump < 30) {
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

                        startActivity(new Intent(getActivity(),XOGamePageActivity.class));

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
