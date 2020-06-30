package com.example.plato.game.startPage.fragment.casual;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.plato.NetworkThreadHandler;
import com.example.plato.R;
import com.example.plato.SingletonUserContainer;
import com.example.plato.game.Room;
import com.example.plato.game.SingletonGameContainer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class CreateNewRoomActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_room);

        final EditText roomName_et=findViewById(R.id.et_createNewRoom_roomname);
        Button create_btn=findViewById(R.id.btn_createNewRoom_create);


        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomname=roomName_et.getText().toString();
                ArrayList<Room> rooms= (ArrayList<Room>) SingletonGameContainer.getXoInstance().getRooms();
                if(rooms!=null){
                    ArrayList<String> names=new ArrayList<>();
                    for (int i = 0; i <rooms.size() ; i++) {
                        names.add(rooms.get(i).getRoom_name());
                    }
                    if(names.contains(roomname)){
                        Toast.makeText(CreateNewRoomActivity.this,"already,this room exist ,enter another name!",Toast.LENGTH_LONG).show();
                    }else {
//                       new Thread(new Runnable() {
//                           @Override
//                           public void run() {
//
//
//                               try {
//                                   NetworkThreadHandler.dos.writeUTF("game");
//
//                               } catch (IOException e) {
//                                   e.printStackTrace();
//                               }
//
//                           }
//                       }).start();
                        Room room=new Room();
                        room.setMax_players(2);
                        room.setIs_join_enable(true);
                        room.setRoom_name(roomname);
                        room.joinRoom(SingletonUserContainer.getInstance());
                        rooms.add(room);
                        setResult(RESULT_OK);
                        finish();
                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
    }
}
