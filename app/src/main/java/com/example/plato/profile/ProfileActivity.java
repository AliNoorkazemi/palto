package com.example.plato.profile;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plato.MainActivity;
import com.example.plato.R;


public class ProfileActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterProfile adapter;
    TextView edit_tv ;
    TextView username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        edit_tv = findViewById(R.id.tv_profileActivity_edit);
        Toolbar toolbar=findViewById(R.id.toolbar_profileActivity);
        setSupportActionBar(toolbar);

        edit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this,EditProfileActivity.class);
                startActivityForResult(intent,1001);
            }
        });


        username=findViewById(R.id.tv_profileActivity_username);
        username.setText(MainActivity.userName);
        ImageView avatarImg=findViewById(R.id.iv_profileActivity_avatarImg);
        avatarImg.setImageBitmap(MainActivity.profile_bitmap);

        initRecycler();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1001){
            String new_username = data.getStringExtra("new_username");
            username.setText(new_username);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent ( ProfileActivity.this,MainActivity.class);
        intent.putExtra("Activity", "ProfileActivity");
        startActivity(intent);
        finish();
    }

    private void initRecycler() {
        recyclerView=findViewById(R.id.rc_profileAvtivity_gameRecycler);
        adapter=new AdapterProfile();
        LinearLayoutManager layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}
