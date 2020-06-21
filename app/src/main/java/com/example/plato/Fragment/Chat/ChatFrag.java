package com.example.plato.Fragment.Chat;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plato.Fragment.UserFriend;
import com.example.plato.R;

import java.util.LinkedList;

public class ChatFrag extends Fragment {

    LinkedList<UserFriend> userFriends;
    RecyclerView recyclerView;
    AdapterFriendinChat adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_chat, container, false);

        userFriends=new LinkedList<>();
        for (int i=0;i<5;i++){
            userFriends.add(generateUserFriend());
        }

        initRecycler(view);



        return view;

    }

    private UserFriend generateUserFriend() {
        UserFriend userFriend=new UserFriend();
        userFriend.setName("ali nooka");
        userFriend.setImg_id(R.drawable.ic_people_24dp);
        return userFriend;
    }

    private void initRecycler(View view) {
        recyclerView=view.findViewById(R.id.rc_chatFrag_recycler);
        adapter=new AdapterFriendinChat(view.getContext(), userFriends, new AdapterFriendinChat.OnItemINChatFragClicked() {
            @Override
            public void onClick(int position) {

            }
        });
        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
