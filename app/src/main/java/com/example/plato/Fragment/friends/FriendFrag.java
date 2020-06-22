package com.example.plato.Fragment.friends;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plato.Fragment.Chat.chatPage.ChatPageActivity;
import com.example.plato.Fragment.Friend;
import com.example.plato.R;
import com.example.plato.SingletonUserContainer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import static android.app.Activity.RESULT_OK;

public class FriendFrag extends Fragment {
    View view;
    RecyclerView recyclerView;
    AdapterFriend adapter;
    LinkedList<Friend> friends;
    FloatingActionButton fab;
    private int current_friend_position;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_friend, container, false);

        friends=new LinkedList<>();
        friends.addAll(SingletonUserContainer.getInstance().getFriends());

        initRecycler();

        fab=view.findViewById(R.id.fab_friendFrag_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText=new EditText(view.getContext());
                AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                builder.setCancelable(true);
                builder.setTitle("search by plato username");
                builder.setView(editText);
                builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //just for test , this is must handle by service :)
                        Friend friend=new Friend();
                        friend.setName(editText.getText().toString());
                        friend.setImg_id(R.drawable.ic_person_24dp);
                        ArrayList<Boolean> is_income = new ArrayList<>();
                        ArrayList<Date> dates = new ArrayList<>();
                        ArrayList<String> message = new ArrayList<>();
                        friend.setChats_message(message);
                        friend.setIs_it_incomeMessage(is_income);
                        friend.setDates(dates);
                        Toast.makeText(view.getContext(),"saved",Toast.LENGTH_LONG).show();
                        friends.add(friend);
                        adapter.notifyItemInserted(friends.size());
                    }
                });
                builder.create().show();
            }
        });

        return view;
    }

    private void initRecycler() {
        recyclerView=view.findViewById(R.id.rc_friendFrag_recycler);
        adapter=new AdapterFriend(view.getContext(), friends, new AdapterFriend.OnFriendItemClickListener() {
            @Override
            public void onClick(int position) {
                Intent intent=new Intent(view.getContext(), ChatPageActivity.class);
                Friend friend = friends.get(position);
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
        if(requestCode==ChatPageActivity.REQUEST_CODE && resultCode==RESULT_OK && data!=null){
            Friend oldFriend = friends.get(current_friend_position);
            Friend newFriend = (Friend) data.getSerializableExtra("FINISH");
            oldFriend.setChats_message(newFriend.getChats_message());
            oldFriend.setIs_it_incomeMessage(newFriend.getIs_it_incomeMessage());
            oldFriend.setDates(newFriend.getDates());
        }
    }



}
