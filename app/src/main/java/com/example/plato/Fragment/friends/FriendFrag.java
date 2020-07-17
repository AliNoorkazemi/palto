package com.example.plato.Fragment.friends;

import android.app.Activity;
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
import android.widget.Toast;

import com.example.plato.Fragment.Chat.chatPage.ChatPageActivity;
import com.example.plato.Fragment.Friend;
import com.example.plato.MainActivity;
import com.example.plato.R;
import com.example.plato.SingletonUserContainer;
import com.example.plato.SplashScreenActivity;
import com.example.plato.network.MessageListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import static android.app.Activity.RESULT_OK;

public class FriendFrag extends Fragment {
    static View view;
    static RecyclerView recyclerView;
    static AdapterFriend adapter;
    FloatingActionButton fab;
    static private int current_friend_position;
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

        view = inflater.inflate(R.layout.fragment_friend, container, false);


        initRecycler();

        fab = view.findViewById(R.id.fab_friendFrag_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText editText = new EditText(view.getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setCancelable(true);
                builder.setTitle("search by plato username");
                builder.setView(editText);
                builder.setPositiveButton("Search", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (editText.getText().toString().equals(MainActivity.userName)) {
                            Toast.makeText(view.getContext(), "this is your userName", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                if (MainActivity.friend_names.contains(editText.getText().toString())) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(view.getContext(), editText.getText().toString() + " already existed", Toast.LENGTH_LONG).show();
                                        }
                                    });
                                    return;
                                }
                                try {
                                    Socket socket = new Socket(SplashScreenActivity.IP, 6666);
                                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                                    dos.writeUTF("AddFriend");
                                    dos.flush();
                                    dos.writeUTF(editText.getText().toString());
                                    dos.flush();
                                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                                    String validation = dis.readUTF();
                                    if (validation.equals("ERROR")) {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(view.getContext(), "this username dose not exist", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        return;
                                    }
                                    if (validation.equals("OK")) {
                                        dos.writeUTF(MainActivity.userName);
                                        Friend friend = new Friend();
                                        friend.setName(editText.getText().toString());
                                        friend.setImg_id(R.drawable.ic_person_24dp);
//                                        ArrayList<Boolean> is_income = new ArrayList<>();
                                        ArrayList<Integer> type_of_message = new ArrayList<>();
                                        ArrayList<Date> dates = new ArrayList<>();
                                        ArrayList<String> message = new ArrayList<>();
                                        friend.setChats_message(message);
//                                        friend.setIs_it_incomeMessage(is_income);
                                        friend.setType_of_messages(type_of_message);
                                        friend.setDates(dates);
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(view.getContext(), editText.getText().toString() + " saved", Toast.LENGTH_SHORT).show();
                                                SingletonUserContainer.getInstance().getFriends().add(friend);
                                                MainActivity.friend_names.add(friend.getName());
                                                adapter.notifyItemInserted(SingletonUserContainer.getInstance().getFriends().size() - 1);
                                            }
                                        });
                                    }
                                } catch (IOException io) {
                                    io.printStackTrace();
                                }
                            }
                        }).start();

                    }
                });
                builder.create().show();
            }
        });

        return view;
    }

    private void initRecycler() {
        recyclerView = view.findViewById(R.id.rc_friendFrag_recycler);
        adapter = new AdapterFriend(view.getContext(), SingletonUserContainer.getInstance().getFriends(), new AdapterFriend.OnFriendItemClickListener() {
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
//            oldFriend.setIs_it_incomeMessage(newFriend.getIs_it_incomeMessage());
            oldFriend.setType_of_messages(newFriend.getType_of_messages());
            oldFriend.setDates(newFriend.getDates());
        }
    }


}
