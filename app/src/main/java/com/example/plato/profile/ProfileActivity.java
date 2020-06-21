package com.example.plato.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plato.R;


public class ProfileActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterProfile adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar=findViewById(R.id.toolbar_profileActivity);
        setSupportActionBar(toolbar);


        Intent intent=getIntent();
        TextView username=findViewById(R.id.tv_profileActivity_username);
        username.setText(intent.getStringExtra("USERNAME"));
        ImageView avatarImg=findViewById(R.id.iv_profileActivity_avatarImg);
        avatarImg.setImageResource(intent.getIntExtra("PROFILE_IMAGE",R.drawable.ic_person_24dp));

        initRecycler();

    }

    private void initRecycler() {
        recyclerView=findViewById(R.id.rc_profileAvtivity_gameRecycler);
        adapter=new AdapterProfile();
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}
