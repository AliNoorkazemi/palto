package com.example.plato;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.plato.Fragment.ChatFrag;
import com.example.plato.Fragment.FriendFrag;
import com.example.plato.Fragment.GameFrag;
import com.example.plato.Fragment.HomeFrag;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    Toolbar toolbar;
    TextView toolbarTitle_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar=findViewById(R.id.toolbar_mainActivity);
        toolbarTitle_tv=findViewById(R.id.tv_mainActivity_toolbarTitle);
        setSupportActionBar(toolbar);


        setFragment(new HomeFrag());



        navigationView = findViewById(R.id.bottomNav_mainActivity);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.btn_bottomNavigation_homePage:
                        toolbarTitle_tv.setText("Home");
                        setFragment(new HomeFrag());

                        break;
                    case R.id.btn_bottomNavigation_chatPage:
                        toolbarTitle_tv.setText("Chat");
                        setFragment(new ChatFrag());
                        break;
                    case R.id.btn_bottomNavigation_Friends:
                        toolbarTitle_tv.setText("Friends");
                        setFragment(new FriendFrag());
                        break;
                    case R.id.btn_bottomNavigation_games:
                        toolbarTitle_tv.setText("Games");
                        setFragment(new GameFrag());
                        break;
                }
                return true;
            }
        });


    }

    private boolean setFragment(Fragment fragment) {
        if(fragment!=null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout_mainActivity_fragmentHolder,fragment)
                    .commit();
            return true;
        }
        return false;
    }
}
