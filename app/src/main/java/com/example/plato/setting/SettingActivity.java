package com.example.plato.setting;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;

import com.example.plato.MainActivity;
import com.example.plato.R;
import com.example.plato.SplashScreenActivity;
import com.example.plato.entry.EntryActivity;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        Toolbar toolbar=findViewById(R.id.toolbar_settingActivity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Button logout_btn=findViewById(R.id.btn_settingActivity_logout);
        Button aboutUs_btn=findViewById(R.id.btn_settingActivity_aboutus);

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(SettingActivity.this);
                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(SettingActivity.this, EntryActivity.class);
                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                    Socket socket = new Socket(SplashScreenActivity.IP, 6666);
                                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                                    dos.writeUTF("offline");
                                    dos.flush();
                                    dos.writeUTF(MainActivity.userName);
                                    dos.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                        startActivity(i);
                        finish();
                    }
                });
                builder.setTitle("Are you sure to log out?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                builder.setCancelable(true);

                builder.create().show();
            }
        });

        aboutUs_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this,AboutUsActivity.class));
            }
        });



    }
}
