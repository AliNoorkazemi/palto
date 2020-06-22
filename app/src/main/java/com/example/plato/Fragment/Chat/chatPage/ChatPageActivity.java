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

import com.example.plato.Fragment.Friend;
import com.example.plato.R;

public class ChatPageActivity extends AppCompatActivity {

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

    }

    private void initRecycler() {
        recyclerView=findViewById(R.id.rc_chatPageActivity);
        adapter=new AdapterChatPage(this,friend);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}
