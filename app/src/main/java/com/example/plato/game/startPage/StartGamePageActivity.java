package com.example.plato.game.startPage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import com.example.plato.R;
import com.example.plato.game.Game;
import com.example.plato.game.XO;
import com.example.plato.game.dotsandboxes.DotsAndBoxes;
import com.example.plato.game.guessword.GuessWord;
import com.example.plato.game.startPage.fragment.casual.CasualFrag;
import com.example.plato.game.startPage.fragment.leaderboard.LeaderBoardFrag;
import com.example.plato.game.startPage.fragment.ranked.RankedFrag;
import com.google.android.material.tabs.TabLayout;

public class StartGamePageActivity extends AppCompatActivity {
    public static Game game;
    public static String game_name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game_page);

        Intent intent=getIntent();
        setGame(intent);
        game_name = intent.getStringExtra("game name");


        Toolbar toolbar=findViewById(R.id.toobar_startPageActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(game.getGame_name());


        ViewPager viewPager=findViewById(R.id.viewPager_startGamePageActivity);
        TabLayout tabLayout=findViewById(R.id.tablayout_startgamePageActivity);
        StartGameFragmentPagerAdapter adapter=new StartGameFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CasualFrag(),"Casual");
        adapter.addFragment(new RankedFrag(),"Ranked");
        adapter.addFragment(new LeaderBoardFrag(),"LeaderBoard");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setGame(Intent intent) {
        Game g= (Game) intent.getSerializableExtra("GAME");
        if(g instanceof XO){
            game=(XO)g;
        }else if(g instanceof GuessWord){
            game = (GuessWord)g;
        }else if(g instanceof DotsAndBoxes){
            game=(DotsAndBoxes)g;
        }
    }
}
