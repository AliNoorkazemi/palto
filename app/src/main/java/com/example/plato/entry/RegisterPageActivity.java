package com.example.plato.entry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.plato.*;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class RegisterPageActivity extends AppCompatActivity {
    EditText username_et;
    EditText password_et;
    EditText confirmPassword_et;
    Button register_btn;
    boolean[] is_valid;
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("192.168.1.4", 6666);
                    dis = new DataInputStream(socket.getInputStream());
                    dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF("Register");
                    dos.flush();
                }catch(IOException io){
                    io.printStackTrace();
                }
            }
        }).start();

        is_valid = new boolean[3];
        Arrays.fill(is_valid, false);


        username_et = findViewById(R.id.et_registerPage_username);
        password_et = findViewById(R.id.et_registerPage_password);
        confirmPassword_et = findViewById(R.id.et_registerPage_passwordConfirm);
        register_btn = findViewById(R.id.btn_registerPage_register);




        username_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    is_valid[0] = true;
                } else {
                    if (username_et.getText().toString().length() == 0) {
                        username_et.setHint("username should not be empty...");
                        username_et.setHintTextColor(getColor(R.color.red));
                        is_valid[0] = false;
                    }else{
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final TextView usernameWarn = findViewById(R.id.tv_registerPage_usernameWarning);
                                usernameWarn.setVisibility(View.INVISIBLE);
                                try {
                                    dos.writeUTF(username_et.getText().toString());
                                    dos.flush();
                                    String message = dis.readUTF();
                                    if(message.equals("Duplicated")){
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                usernameWarn.setText("The username has been already exited");
                                                usernameWarn.setVisibility(View.VISIBLE);
                                            }
                                        });
                                        is_valid[0]=false;
                                    }
                                }catch(IOException io){
                                    io.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }
            }
        });


        password_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            TextView passwordWarn = findViewById(R.id.tv_registerPage_passwordWarning);

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    passwordWarn.setVisibility(View.INVISIBLE);
                    is_valid[1] = true;
                } else if (password_et.getText().toString().length() <= 5) {
                    passwordWarn.setVisibility(View.VISIBLE);
                    is_valid[1] = false;
                    passwordWarn.setText("password must be more than 5 character");
                }

            }
        });

        confirmPassword_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            TextView conifWarn = findViewById(R.id.tv_registerPage_passwordConfWarning);

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    conifWarn.setVisibility(View.INVISIBLE);
                    is_valid[2] = true;
                } else {
                    if (!confirmPassword_et.getText().toString().equals(password_et.getText().toString())) {
                        is_valid[2] = false;
                        conifWarn.setText("Password and confirm password don't match");
                        conifWarn.setVisibility(View.VISIBLE);
                    }

                }
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_valid[0] && is_valid[1] && is_valid[2]) {
                    Intent intent = new Intent(RegisterPageActivity.this, MainActivity.class);
                    intent.putExtra("userName",username_et.getText().toString());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                dos.writeUTF("UserEnteredCorrectly");
                                dos.flush();
                                dos.writeUTF(username_et.getText().toString());
                                dos.flush();
                                dos.writeUTF(password_et.getText().toString());
                                dos.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}