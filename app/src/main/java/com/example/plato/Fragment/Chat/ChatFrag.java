package com.example.plato.Fragment.Chat;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.plato.Fragment.Chat.chatPage.ChatPageActivity;
import com.example.plato.Fragment.Friend;
import com.example.plato.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import static android.app.Activity.RESULT_OK;

public class ChatFrag extends Fragment {

    LinkedList<Friend> friends;
    RecyclerView recyclerView;
    AdapterFriendinChat adapter;
    private int current_friend_position;

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

        ArrayList<Date> dates=new ArrayList<>();
        dates.add(new Date());
        dates.add(new Date());
        dates.add(new Date());
        dates.add(new Date());

        friend.setChats_message(message);
        friend.setIs_it_incomeMessage(is_income);
        friend.setDates(dates);
        return friend;
    }

    private void initRecycler(final View view) {
        recyclerView=view.findViewById(R.id.rc_chatFrag_recycler);
        adapter=new AdapterFriendinChat(view.getContext(), friends, new AdapterFriendinChat.OnItemINChatFragClicked() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(view.getContext(), ChatPageActivity.class);
                Friend friend = friends.get(position);
                current_friend_position=position;
                intent.putExtra("FRIEND", friend);
                startActivityForResult(intent,ChatPageActivity.REQUEST_CODE);
            }
        });
        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==ChatPageActivity.REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            Log.i("where?", "onActivityResult: ");
            Friend oldFriend=friends.get(current_friend_position);
            Friend newFriend= (Friend) data.getSerializableExtra("FINISH");
            oldFriend.setChats_message(newFriend.getChats_message());
            oldFriend.setIs_it_incomeMessage(newFriend.getIs_it_incomeMessage());
            oldFriend.setDates(newFriend.getDates());
        }
    }
}
