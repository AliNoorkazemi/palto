package com.example.plato;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.plato.entry.EntryActivity;

public class SplashScreenActivity extends AppCompatActivity {
    public static String IP;
    private static int SPLASH_TIME_OUT = 2500;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        IP="192.168.2.102";

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent_to_main = new Intent(SplashScreenActivity.this, EntryActivity.class);
                startActivity(intent_to_main);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
}
