package com.example.plato.Fragment.Chat.chatPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.plato.Fragment.Chat.ChatContent;
import com.example.plato.R;

import java.util.ArrayList;

public class ChatPageActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterChatPage adapter;
    ArrayList<String> messages;
    ArrayList<Boolean> is_income;


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


        messages=new ArrayList<>();
        is_income=new ArrayList<>();

        Intent intent=getIntent();
        ChatContent chatContent= (ChatContent) intent.getSerializableExtra("CHAT_CONTENT");
        messages.addAll(chatContent.getChats_message());
        is_income.addAll(chatContent.getIs_it_incomeMessage());
        TextView friendName_tv=findViewById(R.id.tv_chatPageActivity_friendName);
        ImageView friendImg_iv=findViewById(R.id.iv_chatPageActivity_friendImg);

        friendName_tv.setText(intent.getStringExtra("CHAT_FRIEND_NAME"));
        friendImg_iv.setImageResource(intent.getIntExtra("CHAT_FRIEND_PROFILE",R.drawable.ic_person_24dp));

        initRecycler();

    }

    private void initRecycler() {
        recyclerView=findViewById(R.id.rc_chatPageActivity);
        adapter=new AdapterChatPage(this,messages,is_income);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}
