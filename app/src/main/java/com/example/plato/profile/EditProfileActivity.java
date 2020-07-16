package com.example.plato.profile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.plato.MainActivity;
import com.example.plato.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class EditProfileActivity extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private TextView tv_edit_title;
    private TextView tv_username_title;
    private TextView tv_password_title;
    private TextView tv_passwordConf_title;
    private EditText et_username;
    private EditText et_password;
    private EditText et_passwordConf;
    private TextView tv_username_warning;
    private TextView tv_password_warning;
    private TextView tv_passwordConf_warning;
    private Button btn_ok;
    private Button btn_cancel;
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String main_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        constraintLayout = findViewById(R.id.edit_profile_activity_constrain_layout);
        tv_edit_title = findViewById(R.id.tv_edit_title);
        tv_username_title = findViewById(R.id.tv_username_profile_change);
        tv_password_title = findViewById(R.id.tv_password_profile_change);
        tv_passwordConf_title = findViewById(R.id.tv_profile_change_passwordConf);
        et_username = findViewById(R.id.et_profile_change_username);
        et_username.setText(MainActivity.userName);
        et_password = findViewById(R.id.et_profile_change_password);
        et_passwordConf = findViewById(R.id.et_profile_change_passwordConfirm);
        tv_username_warning = findViewById(R.id.tv_profile_change_usernameWarning);
        tv_password_warning = findViewById(R.id.tv_profile_change_passwordWarning);
        tv_passwordConf_warning = findViewById(R.id.tv_profile_change_passwordConfWarning);
        btn_ok = findViewById(R.id.btn_ok_edit_profile_change);
        btn_cancel = findViewById(R.id.btn_cancel_edit_profile_change);

        InputMethodManager inputMethodManager = (InputMethodManager) constraintLayout.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(constraintLayout.getWindowToken(), 0);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("192.168.2.102", 6666);
                    dis = new DataInputStream(socket.getInputStream());
                    dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF("change profile");
                    dos.flush();
                    dos.writeUTF(MainActivity.userName);
                    dos.flush();
                    String password = dis.readUTF();
                    main_password = password;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            et_password.setText(password);
                        }
                    });
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        }).start();

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        });

        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (et_passwordConf.getVisibility() == View.INVISIBLE) {
                        et_passwordConf.setVisibility(View.VISIBLE);
                        et_passwordConf.setText(et_password.getText().toString());
                        tv_passwordConf_title.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_username.getText().toString().equals("")) {
                    tv_username_warning.setText("username must be filled");
                    tv_username_warning.setVisibility(View.VISIBLE);
                    Log.i("profile", " doesn't change profile");
                    return;
                } else if (et_password.getText().toString().length() < 6) {
                    tv_password_warning.setText("password mush be more than 5 character");
                    tv_password_warning.setVisibility(View.VISIBLE);
                    Log.i("profile", " doesn't change profile");
                    return;
                } else if (et_username.getText().toString().equals(MainActivity.userName) && et_password.getText().toString().equals(main_password)) {
                    setResult(1002);
                    finish();
                    return;
                } else if (!et_password.getText().toString().equals(et_passwordConf.getText().toString()) && et_passwordConf.getVisibility() == View.VISIBLE) {
                    tv_passwordConf_warning.setText("password and confirm password doesn't match");
                    Log.i("profile", " doesn't change profile");
                    tv_passwordConf_warning.setVisibility(View.VISIBLE);
                    return;
                } else if (!(et_username.getText().toString().equals(MainActivity.userName)) || !(et_password.getText().toString().equals(main_password))) {
                    Log.i("profile", "change profile");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String new_username = et_username.getText().toString();
                            String new_password = et_password.getText().toString();
                            try {
                                Log.i("profile", "send data to validation");
                                dos.writeUTF(new_username);
                                dos.flush();
                                Log.i("profile", "sent username to validation");
                                dos.writeUTF(new_password);
                                dos.flush();
                                Log.i("profile", "sent password to validation");
                                String validation = dis.readUTF();
                                if (validation.equals("error")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            tv_username_warning.setText("this username has been already existed...");
                                            tv_username_warning.setVisibility(View.VISIBLE);
                                        }
                                    });
                                } else if (validation.equals("ok")) {
                                    MainActivity.userName = et_username.getText().toString();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Intent data = getIntent();
                                            data.putExtra("new_username", new_username);
                                            setResult(1001, data);
                                            finish();
                                        }
                                    });
                                }
                            } catch (IOException io) {
                                io.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(1002);
                finish();
            }
        });


    }
}
