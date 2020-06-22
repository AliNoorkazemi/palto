package com.example.plato.Fragment.Chat;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plato.Fragment.Chat.chatPage.ChatPageActivity;
import com.example.plato.Fragment.Friend;
import com.example.plato.R;

import java.util.ArrayList;
import java.util.LinkedList;

public class ChatFrag extends Fragment {

    LinkedList<Friend> friends;
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

        friends =new LinkedList<>();
        for (int i=0;i<5;i++){
            friends.add(generateUserFriend());
        }

        initRecycler(view);



        return view;

    }

    private Friend generateUserFriend() {
        Friend friend =new Friend();
        friend.setName("ali nooka");
        friend.setImg_id(R.drawable.ic_people_24dp);

        ArrayList<String> message=new ArrayList<>();
        message.add("salam");
        message.add("hello");
        message.add("khobi?");
        message.add("zer nzn");

        ArrayList<Boolean> is_income=new ArrayList<>();
        is_income.add(true);
        is_income.add(false);
        is_income.add(true);
        is_income.add(false);

        friend.setChats_message(message);
        friend.setIs_it_incomeMessage(is_income);
        return friend;
    }

    private void initRecycler(final View view) {
        recyclerView=view.findViewById(R.id.rc_chatFrag_recycler);
        adapter=new AdapterFriendinChat(view.getContext(), friends, new AdapterFriendinChat.OnItemINChatFragClicked() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(view.getContext(), ChatPageActivity.class);
                Friend friend = friends.get(position);
                intent.putExtra("FRIEND", friend);
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
