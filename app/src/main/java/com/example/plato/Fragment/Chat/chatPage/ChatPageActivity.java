package com.example.plato.Fragment.Chat.chatPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.plato.Fragment.Friend;
import com.example.plato.MainActivity;
import com.example.plato.R;
import com.example.plato.SingletonUserContainer;
import com.example.plato.network.MessageListener;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;

public class ChatPageActivity extends AppCompatActivity {

    public static final int REQUEST_CODE=1001;
    static AdapterChatPage adapter;
    static RecyclerView recyclerView;
    public static Friend friend;
    public static MessageListener.OnUpdateUiForIncomingMessage onUpdateUiForIncomingMessage = new MessageListener.OnUpdateUiForIncomingMessage() {
        @Override
        public void onUpdateUiForIncomingMessage() {
            if(adapter==null)
                return;
            Activity origin = (Activity)adapter.context;
            origin.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                    recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount()-1);
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_page);

        Toolbar toolbar=findViewById(R.id.toolbar_chatPageActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        Intent intent=getIntent();
        friend= (Friend) intent.getSerializableExtra("FRIEND");
        friend = SingletonUserContainer.getInstance().getTargetFriend(friend.getName());


        TextView friendName_tv=findViewById(R.id.tv_chatPageActivity_friendName);
        ImageView friendImg_iv=findViewById(R.id.iv_chatPageActivity_friendImg);


        friendName_tv.setText(friend.getName());
        friendImg_iv.setImageResource(friend.getImg_id());

        initRecycler();
        final EditText chatBox_et=findViewById(R.id.et_chatPageActivity_chatbox);
        ImageButton send_btn=findViewById(R.id.ib_chatPageActivity_sendBtn);


        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(chatBox_et.getText().toString().equals(""))
                    return;
                friend.getChats_message().add(chatBox_et.getText().toString());
//                friend.getIs_it_incomeMessage().add(false);
                friend.getType_of_messages().add(1);
                Date date = new Date();
                friend.getDates().add(date);
                sendMessage(chatBox_et.getText().toString(),date);
                chatBox_et.getText().clear();
                adapter.notifyItemInserted(friend.getChats_message().size());
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount()-1);
            }
        });


    }

    private void sendMessage(String message,Date date){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("192.168.2.102", 6666);
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF("sendMessage");
                    dos.flush();
                    dos.writeUTF(MainActivity.userName);
                    dos.flush();
                    dos.writeUTF(friend.getName());
                    dos.flush();
                    dos.writeUTF(message);
                    dos.flush();
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(date);
                    oos.flush();
                }catch (IOException io){
                    io.printStackTrace();
                }
            }
        }).start();
    }

    private void initRecycler() {
        recyclerView=findViewById(R.id.rc_chatPageActivity);
        adapter=new AdapterChatPage(this,friend);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent();
        intent.putExtra("FINISH",friend);
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        onBackPressed();
    }
}

