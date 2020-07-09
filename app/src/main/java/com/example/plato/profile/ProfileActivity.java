package com.example.plato.profile;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plato.MainActivity;
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


        TextView username=findViewById(R.id.tv_profileActivity_username);
        username.setText(MainActivity.userName);
        ImageView avatarImg=findViewById(R.id.iv_profileActivity_avatarImg);
        avatarImg.setImageBitmap(MainActivity.profile_bitmap);

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
