package com.example.plato;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Intent intent=getIntent();
        TextView username=findViewById(R.id.tv_profileActivity_username);
        username.setText(intent.getStringExtra("USERNAME"));
        ImageView avatarImg=findViewById(R.id.iv_profileActivity_avatarImg);
        avatarImg.setImageResource(intent.getIntExtra("PROFILE_IMAGE",R.drawable.ic_person_24dp));



    }
}
