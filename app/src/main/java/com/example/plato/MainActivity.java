package com.example.plato;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.example.plato.Fragment.Chat.ChatFrag;
import com.example.plato.Fragment.friends.FriendFrag;
import com.example.plato.Fragment.GameFrag;
import com.example.plato.Fragment.HomeFrag;

import com.example.plato.entry.EntryActivity;
import com.example.plato.network.DataReceiver;
import com.example.plato.network.MessageListener;
import com.example.plato.profile.ProfileActivity;
import com.example.plato.setting.SettingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    Toolbar toolbar;
    TextView toolbarTitle_tv;
    DrawerLayout drawerLayout;
    NavigationView drawerNavigationView;
    public static String userName;
    public static ArrayList<String> friend_names;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_navigation);

        userName = getIntent().getStringExtra("userName");


        DataReceiver dataReceiver = new DataReceiver();
        dataReceiver.start();

        MessageListener messageListener = new MessageListener();
        messageListener.start();


        toolbar = findViewById(R.id.toolbar_mainActivity);
        toolbarTitle_tv = findViewById(R.id.tv_mainActivity_toolbarTitle);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawerlayout_mainActivity);
        drawerNavigationView = findViewById(R.id.navigationView_main_drawer);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getColor(R.color.white));
        actionBarDrawerToggle.syncState();

        drawerNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Log.i("MainLog", "onNavigationItemSelected: ");
                switch (menuItem.getItemId()) {
                    case R.id.btn_drawerNavigation_profile:
                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                        TextView textView = findViewById(R.id.tv_mainActivity_header_username);
                        ImageView imageView = findViewById(R.id.iv_mainActivity_header_avatarimg);
                        intent.putExtra("USERNAME", textView.getText().toString());
                        intent.putExtra("PROFILE_IMAGE", R.drawable.ic_person_24dp);
                        startActivity(intent);
                        break;

                    case R.id.btn_drawerNavigation_setting:
                        startActivity(new Intent(MainActivity.this, SettingActivity.class));
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });


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
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.frameLayout_mainActivity_fragmentHolder, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("where", "onDestroy: ");

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, EntryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
