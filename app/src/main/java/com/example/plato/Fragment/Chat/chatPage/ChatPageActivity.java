package com.example.plato.Fragment.Chat.chatPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.plato.Fragment.Friend;
import com.example.plato.MainActivity;
import com.example.plato.R;

import java.util.Date;

public class ChatPageActivity extends AppCompatActivity {
    public static final int REQUEST_CODE=1001;
    RecyclerView recyclerView;
    AdapterChatPage adapter;
    Friend friend;
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
                friend.getChats_message().add(chatBox_et.getText().toString());
                friend.getIs_it_incomeMessage().add(false);
                friend.getDates().add(new Date());
                chatBox_et.getText().clear();
                adapter.notifyItemInserted(friend.getChats_message().size());
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount()-1);
            }
        });


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

