package com.example.plato;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.plato.Fragment.Chat.ChatFrag;
import com.example.plato.Fragment.friends.FriendFrag;
import com.example.plato.Fragment.GameFrag;
import com.example.plato.Fragment.HomeFrag;

import com.example.plato.entry.EntryActivity;
import com.example.plato.entry.LoginPageActivity;
import com.example.plato.entry.RegisterPageActivity;
import com.example.plato.network.DataReceiver;
import com.example.plato.network.MessageListener;
import com.example.plato.profile.ProfileActivity;
import com.example.plato.setting.SettingActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView navigationView;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    NavigationView drawerNavigationView;
    public static String userName;
    public static Bitmap profile_bitmap;
    public static ArrayList<String> friend_names;
    public static DataReceiver dataReceiver;
    public static MessageListener messageListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_navigation);


        Intent intent = getIntent();
        String activity = intent.getStringExtra("Activity");
        if(activity!=null) {
            if (activity.equals("RegisterPageActivity") || activity.equals("LoginPageActivity")) {
                userName = intent.getStringExtra("userName");
                byte[] byteArray = intent.getByteArrayExtra("profile");
                Toast.makeText(MainActivity.this, "byte:" + byteArray.length, Toast.LENGTH_LONG).show();
                profile_bitmap = ConvertBitmapByte.byteTobitmap(byteArray);
                Log.i("to main activity", "to main activity from register page or login page come in....");
            }
        }


        dataReceiver = new DataReceiver();
        dataReceiver.start();


        messageListener = new MessageListener();
        messageListener.start();


        toolbar = findViewById(R.id.toolbar_mainActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");


        drawerLayout = findViewById(R.id.drawerlayout_mainActivity);
        drawerNavigationView = findViewById(R.id.navigationView_main_drawer);

        //header
        View headerLayout = drawerNavigationView.inflateHeaderView(R.layout.drawer_nav_header);
        ImageView profile_iv = headerLayout.findViewById(R.id.iv_mainActivity_header_avatarimg);
        TextView username_in_header = headerLayout.findViewById(R.id.tv_mainActivity_header_username);
        profile_iv.setImageBitmap(profile_bitmap);
        username_in_header.setText(userName);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getColor(R.color.white));
        actionBarDrawerToggle.syncState();

        drawerNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.btn_drawerNavigation_profile:
                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
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
                        getSupportActionBar().setTitle("Home");
                        setFragment(new HomeFrag());

                        break;
                    case R.id.btn_bottomNavigation_chatPage:
                        getSupportActionBar().setTitle("Chat");
                        setFragment(new ChatFrag());
                        break;
                    case R.id.btn_bottomNavigation_Friends:
                        getSupportActionBar().setTitle("Friends");
                        setFragment(new FriendFrag());
                        break;
                    case R.id.btn_bottomNavigation_games:
                        getSupportActionBar().setTitle("Games");
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
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainActivity.this, EntryActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        Log.i("message", "ERROReeeeeeeeeeeeeeeee");
        messageListener.close();
        startActivity(intent);
        finish();
    }
}
