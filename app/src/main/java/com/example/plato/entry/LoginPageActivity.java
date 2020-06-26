package com.example.plato.entry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.plato.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class LoginPageActivity extends AppCompatActivity {
    EditText username_et;
    EditText password_et;
    Button login_btn;
    TextView warningText;
    Socket socket;
    DataOutputStream dos;
    DataInputStream dis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("192.168.1.4", 666);
                    dis = new DataInputStream(socket.getInputStream());
                    dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF("Login");
                    dos.flush();
                }catch(IOException io){
                    io.printStackTrace();
                }
            }
        }).start();


        username_et=findViewById(R.id.et_loginPage_username);
        password_et=findViewById(R.id.et_loginPage_password);
        warningText=findViewById(R.id.tv_loginPage_warning);
        login_btn=findViewById(R.id.btn_loginPage_login);
        warningText.setVisibility(View.INVISIBLE);






        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(username_et.getText().toString().equals("")){
                    warningText.setText("userName must be filled");
                    warningText.setVisibility(View.VISIBLE);
                    return;
                }
                if(password_et.getText().toString().equals("")){
                    warningText.setText("password must be filled");
                    warningText.setVisibility(View.VISIBLE);
                    return;
                }
                Thread validation = new Thread(new Runnable() {
                    String validationMessage = null;

                    @Override
                    public void run() {
                        try {
                            dos.writeUTF(username_et.getText().toString());
                            dos.flush();
                            dos.writeUTF(password_et.getText().toString());
                            dos.flush();
                            validationMessage = dis.readUTF();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (!validationMessage.startsWith("ERROR")) {
                            Intent intent = new Intent(LoginPageActivity.this, MainActivity.class);
                            intent.putExtra("userName", username_et.getText().toString());
                            intent.putExtra("password", password_et.getText().toString());
                            startActivity(intent);
                            finish();
                        }else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    warningText.setText(validationMessage);
                                    warningText.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    }
                });
                validation.start();
            }
        });

    }
}
