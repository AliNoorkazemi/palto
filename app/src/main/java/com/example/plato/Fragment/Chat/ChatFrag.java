package com.example.plato.Fragment.Chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.plato.Fragment.Chat.chatPage.ChatPageActivity;
import com.example.plato.Fragment.Friend;
import com.example.plato.R;
import com.example.plato.SingletonUserContainer;
import com.example.plato.network.MessageListener;

import java.util.Collections;
import java.util.Comparator;

import static android.app.Activity.RESULT_OK;

public class ChatFrag extends Fragment {

    static RecyclerView recyclerView;
    static AdapterFriendinChat adapter;
    private static int current_friend_position;
    static View view;
    public static MessageListener.OnUpdateUiForIncomingMessage onUpdateUiForIncomingMessage = new MessageListener.OnUpdateUiForIncomingMessage() {
        @Override
        public void onUpdateUiForIncomingMessage() {
            if (adapter != null) {
                Activity origin = (Activity) adapter.context;
                origin.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chat, container, false);



        initRecycler();


        return view;

    }

    private static void sort_list() {
        if (SingletonUserContainer.getInstance().getFriends() == null || SingletonUserContainer.getInstance().getFriends().size() == 0)
            return;
        Collections.sort(SingletonUserContainer.getInstance().getFriends(), new Comparator<Friend>() {
            @Override
            public int compare(Friend o1, Friend o2) {
                if (o1.getChats_message().size() == 0)
                    return 1;
                if (o2.getChats_message().size() == 0)
                    return -1;
                return -1 * o1.getDates().get(o1.getChats_message().size() - 1).compareTo(o2.getDates().get(o2.getChats_message().size() - 1));
            }
        });
    }


    private void initRecycler() {
        sort_list();
        recyclerView = view.findViewById(R.id.rc_chatFrag_recycler);
        adapter = new AdapterFriendinChat(view.getContext(), SingletonUserContainer.getInstance().getFriends(), new AdapterFriendinChat.OnItemINChatFragClicked() {
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(view.getContext(), ChatPageActivity.class);
                Friend friend = SingletonUserContainer.getInstance().getFriends().get(position);
                current_friend_position = position;
                intent.putExtra("FRIEND", friend);
                startActivityForResult(intent, ChatPageActivity.REQUEST_CODE);
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ChatPageActivity.REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Friend oldFriend = SingletonUserContainer.getInstance().getFriends().get(current_friend_position);
            Friend newFriend = (Friend) data.getSerializableExtra("FINISH");
            oldFriend.setChats_message(newFriend.getChats_message());
            oldFriend.setIs_it_incomeMessage(newFriend.getIs_it_incomeMessage());
            oldFriend.setDates(newFriend.getDates());
            initRecycler();
        }
    }
}
