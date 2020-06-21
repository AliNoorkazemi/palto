package com.example.plato.Fragment.Chat;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plato.Fragment.Chat.chatPage.ChatPageActivity;
import com.example.plato.Fragment.UserFriend;
import com.example.plato.R;

import java.util.ArrayList;
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
        userFriend.setChatContent(generateChatContent());
        return userFriend;
    }

    private ChatContent generateChatContent() {
        ChatContent chatContent=new ChatContent();
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
        chatContent.setChats_message(message);
        chatContent.setIs_it_incomeMessage(is_income);
        return chatContent;
    }

    private void initRecycler(final View view) {
        recyclerView=view.findViewById(R.id.rc_chatFrag_recycler);
        adapter=new AdapterFriendinChat(view.getContext(), userFriends, new AdapterFriendinChat.OnItemINChatFragClicked() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(view.getContext(), ChatPageActivity.class);
                UserFriend userFriend=userFriends.get(position);
                intent.putExtra("CHAT_CONTENT",userFriend.getChatContent());
                intent.putExtra("CHAT_FRIEND_NAME",userFriend.getName());
                intent.putExtra("CHAT_FRIEND_PROFILE",userFriend.getImg_id());
                startActivity(intent);
            }
        });
        LinearLayoutManager layoutManager=new LinearLayoutManager(view.getContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
