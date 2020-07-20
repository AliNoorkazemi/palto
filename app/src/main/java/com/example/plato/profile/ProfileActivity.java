package com.example.plato.profile;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.GestureDetector;
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
import com.example.plato.SingletonUserContainer;
import com.example.plato.game.guessword.GuessWord;

import java.util.ArrayList;


public class ProfileActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    AdapterProfile adapter;
    TextView edit_tv;
    TextView username;
    ArrayList<GameInProfileItemSample> gameInProfileItemSamples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        edit_tv = findViewById(R.id.tv_profileActivity_edit);
        Toolbar toolbar = findViewById(R.id.toolbar_profileActivity);
        setSupportActionBar(toolbar);

        edit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivityForResult(intent, 1001);
            }
        });


        username = findViewById(R.id.tv_profileActivity_username);
        username.setText(MainActivity.userName);
        ImageView avatarImg = findViewById(R.id.iv_profileActivity_avatarImg);
        avatarImg.setImageBitmap(MainActivity.profile_bitmap);

        gameInProfileItemSamples = new ArrayList<>();

        GameInProfileItemSample XOgame = new GameInProfileItemSample();
        XOgame.setGame_name("xo");
        XOgame.setImage(R.drawable.xo_icon);
        XOgame.setScore(String.valueOf(SingletonUserContainer.getInstance().getGameScore().get(0)));

        GameInProfileItemSample GUESSgame = new GameInProfileItemSample();
        GUESSgame.setGame_name("guess word");
        GUESSgame.setImage(R.drawable.guessword_icon);
        GUESSgame.setScore(String.valueOf(SingletonUserContainer.getInstance().getGameScore().get(1)));

        GameInProfileItemSample DOTBOXgame = new GameInProfileItemSample();
        DOTBOXgame.setGame_name("dots and boxes");
        DOTBOXgame.setImage(R.drawable.dots_and_boxes_icon_in_casual);
        DOTBOXgame.setScore(String.valueOf(SingletonUserContainer.getInstance().getGameScore().get(2)));

        gameInProfileItemSamples.add(XOgame);
        gameInProfileItemSamples.add(GUESSgame);
        gameInProfileItemSamples.add(DOTBOXgame);

        initRecycler();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1001) {
            String new_username = data.getStringExtra("new_username");
            username.setText(new_username);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
        intent.putExtra("Activity", "ProfileActivity");
        startActivity(intent);
        finish();
    }

    private void initRecycler() {
        recyclerView = findViewById(R.id.rc_profileAvtivity_gameRecycler);
        adapter = new AdapterProfile(ProfileActivity.this,gameInProfileItemSamples);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
    }
}
