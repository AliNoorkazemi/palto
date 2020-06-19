package com.example.plato;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

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



        navigationView = findViewById(R.id.bottomNav_mainActivity);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.btn_bottomNavigation_homePage:
                        toolbarTitle_tv.setText("Home");
                        break;
                    case R.id.btn_bottomNavigation_chatPage:
                        toolbarTitle_tv.setText("Chat");
                        break;
                    case R.id.btn_bottomNavigation_people:
                        toolbarTitle_tv.setText("Friends");
                        break;
                    case R.id.btn_bottomNavigation_games:
                        toolbarTitle_tv.setText("Games");
                        break;
                }
                return true;
            }
        });


    }
}
