package com.example.plato.game.startPage.fragment.casual;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.example.plato.R;
import com.example.plato.game.Room;
import com.example.plato.game.SingletonGameContainer;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;

public class CasualFrag extends Fragment {

    Button create_room_btn;
    RecyclerView recyclerView;
    AdapterCasual adapter;

    ArrayList<Room> rooms;

    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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
        if(rooms==null){
            rooms=new ArrayList<>();
            rooms.addAll(SingletonGameContainer.getXoInstance().getRooms());
        }
        recyclerView=view.findViewById(R.id.rc_casualFrag_recycler);
        adapter=new AdapterCasual(rooms, new AdapterCasual.OnItemInCasualClickListener() {
            @Override
            public void onClick(Room room) {

            }
        });
        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1002 && resultCode==RESULT_OK ){
            rooms.clear();
            rooms.addAll(SingletonGameContainer.getXoInstance().getRooms());
            adapter.notifyItemInserted(rooms.size()-1);
        }
    }
}
