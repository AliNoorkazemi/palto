package com.example.plato.game.startPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.widget.Toolbar;

import com.example.plato.R;
import com.example.plato.game.Game;
import com.example.plato.game.XO;
import com.example.plato.game.startPage.fragment.casual.CasualFrag;
import com.example.plato.game.startPage.fragment.LeaderBoardFrag;
import com.example.plato.game.startPage.fragment.RankedFrag;
import com.google.android.material.tabs.TabLayout;

public class StartGamePageActivity extends AppCompatActivity {
    Game game;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game_page);

        Intent intent=getIntent();
        setGame(intent);


        Toolbar toolbar=findViewById(R.id.toobar_startPageActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(game.getGame_name());


        ViewPager viewPager=findViewById(R.id.viewPager_startGamePageActivity);
        TabLayout tabLayout=findViewById(R.id.tablayout_startgamePageActivity);
        StartGameFragmentPagerAdapter adapter=new StartGameFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CasualFrag(),"casual");
        adapter.addFragment(new RankedFrag(),"ranked");
        adapter.addFragment(new LeaderBoardFrag(),"leaderBoard");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setGame(Intent intent) {
        Game g= (Game) intent.getSerializableExtra("GAME");
        if(g instanceof XO){
            game=(XO)g;
        }
    }

}
