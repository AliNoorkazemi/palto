package com.example.plato.game.dotsandboxes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import com.example.plato.MainActivity;
import com.example.plato.R;
import com.example.plato.game.Room;
import com.example.plato.game.SingletonGameContainer;

import java.util.ArrayList;

public class DotAndBoxPageActivity extends AppCompatActivity {


    static boolean is_my_turn ;
    static GridView gridView;
    static Context context;
    static ArrayList<String> members;
    String room_name;
    String gameState;
    static int my_color;
    static int my_score = 0;

    static DotsAndBoxesListener.OnFinishGame onFinishGame = new DotsAndBoxesListener.OnFinishGame() {
        @Override
        public void onFinishGame() {
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    gridView.setVisibility(View.INVISIBLE);
                    
                }
            });
        }
    };

    static DotsAndBoxesListener.OnChangeForGridView onChangeForGridView = new DotsAndBoxesListener.OnChangeForGridView() {
        @Override
        public void onChangeForGridView(int position, String location_line , int color) {
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    GridViewDotsAndBoxesAdapter.ViewHolder viewHolder = (GridViewDotsAndBoxesAdapter.ViewHolder) gridView.getItemAtPosition(position);
                    switch (location_line) {
                        case "top":
                            viewHolder.line_vertical_top.setClickable(false);
                            viewHolder.line_vertical_top.setImageResource(color);
                            break;
                        case "bottom":
                            viewHolder.line_vertical_bottom.setClickable(false);
                            viewHolder.line_vertical_bottom.setImageResource(color);
                            break;
                        case "right":
                            viewHolder.line_horizontal_right.setClickable(false);
                            viewHolder.line_horizontal_right.setImageResource(color);
                            break;
                        case "left":
                            viewHolder.line_horizontal_left.setClickable(false);
                            viewHolder.line_horizontal_left.setImageResource(color);
                            break;
                    }
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dot_and_box_page);

        Intent intent = getIntent();
        gameState = intent.getStringExtra("gameState");
        if ( gameState.equals("Casual")) {
            room_name = intent.getStringExtra("room_name");
            ArrayList<Room> rooms = SingletonGameContainer.getDotsAndBoxes().getRooms();
            Room room= null;
            for (Room r : rooms) {
                if (r.getRoom_name().equals(room_name)) {
                    room = r;
                    break;
                }
            }
            int my_index = room.getUsers().indexOf(MainActivity.userName);
            is_my_turn = my_index == 0;
            if ( my_index == 0){
                my_color = R.drawable.blue_line_dotandbox;
            }else if ( my_index == 1){
                my_color = R.drawable.red_line_dotandbox;
            }else if ( my_index == 2){
                my_color = R.drawable.green_line_dotandbox;
            }else if ( my_index == 3 ){
                my_color = R.drawable.yellow_line_dotandbox;
            }
            members = new ArrayList<>(room.getUsers());
            members.indexOf(MainActivity.userName);
        }else if ( gameState.equals("Ranked")){
            String color = intent.getStringExtra("color");
            if ( color.equals("blue")){
                my_color = R.drawable.blue_line_dotandbox;
            }else if ( color.equals("red")){
                my_color = R.drawable.red_line_dotandbox;
            }
            members = new ArrayList<>();
            int index = intent.getIntExtra("index",0);
            is_my_turn = index == 0;
            members.add(index,MainActivity.userName);
            String opponent = intent.getStringExtra("opponent");
            if ( index == 0)
                members.add(opponent);
            if ( index == 1)
                members.add(0 , opponent );
        }




        context = DotAndBoxPageActivity.this;
        gridView = findViewById(R.id.grid_view_dots_and_boxes);

        new Thread(new DotsAndBoxesListener(context)).start();

        GridViewDotsAndBoxesAdapter adapter = new GridViewDotsAndBoxesAdapter(DotAndBoxPageActivity.this);
        gridView.setAdapter(adapter);

    }
}
