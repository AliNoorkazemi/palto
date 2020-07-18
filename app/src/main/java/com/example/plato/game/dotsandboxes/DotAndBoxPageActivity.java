package com.example.plato.game.dotsandboxes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.GridView;

import com.example.plato.R;
import com.example.plato.game.Room;
import com.example.plato.game.SingletonGameContainer;

import java.util.ArrayList;

public class DotAndBoxPageActivity extends AppCompatActivity {


    static boolean is_my_turn ;
    static GridView gridView;
    Context context;
    ArrayList<String> opponents;
    String room_name;
    String gameState;
    int my_color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dot_and_box_page);

        Intent intent = getIntent();
        gameState = intent.getStringExtra("gameState");
        room_name = intent.getStringExtra("room_name");
        ArrayList<Room> rooms = SingletonGameContainer.getDotsAndBoxes().getRooms();
        Room room ;
        for( Room r : rooms){
            if (r.getRoom_name().equals(room_name) ){
                room = r;
                break;
            }
        }




        context = DotAndBoxPageActivity.this;
        gridView = findViewById(R.id.grid_view_dots_and_boxes);

        new Thread(new DotsAndBoxesListener(context)).start();

        GridViewDotsAndBoxesAdapter adapter = new GridViewDotsAndBoxesAdapter(DotAndBoxPageActivity.this);
        gridView.setAdapter(adapter);

    }
}
