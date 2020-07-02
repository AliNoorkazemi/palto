package com.example.plato.game.startPage.fragment.casual;

import androidx.annotation.UiThread;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.plato.MainActivity;
import com.example.plato.R;
import com.example.plato.SingletonUserContainer;
import com.example.plato.game.Room;
import com.example.plato.game.SingletonGameContainer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class CreateNewRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_room);

        final EditText roomName_et = findViewById(R.id.et_createNewRoom_roomname);
        Button create_btn = findViewById(R.id.btn_createNewRoom_create);


        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomname = roomName_et.getText().toString();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Socket socket = new Socket("192.168.1.4", 6666);
                            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                            DataInputStream dis = new DataInputStream(socket.getInputStream());
                            dos.writeUTF("game");
                            dos.writeUTF("addRoom");
                            dos.writeUTF("xo");

                            dos.writeUTF(roomname);
                            if (dis.readBoolean()) {

                                //send room data to server
                                dos.writeUTF("2");
                                dos.writeUTF(MainActivity.userName);

                                finish();
                            } else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(CreateNewRoomActivity.this, "already,this room exist ,enter another name!", Toast.LENGTH_LONG).show();
                                    }
                                });
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();


            }
        });

    }

}
