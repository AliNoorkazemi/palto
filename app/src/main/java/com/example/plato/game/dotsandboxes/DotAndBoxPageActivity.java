package com.example.plato.game.dotsandboxes;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import com.example.plato.MainActivity;
import com.example.plato.R;
import com.example.plato.SingletonUserContainer;
import com.example.plato.SplashScreenActivity;
import com.example.plato.game.Room;
import com.example.plato.game.SingletonGameContainer;
import com.example.plato.network.SendScoreToServer;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class DotAndBoxPageActivity extends AppCompatActivity {


    static boolean is_my_turn ;
    static GridView gridView;
    static Context context;
    static ArrayList<String> members;
    static String room_name;
    static String gameState;
    static int my_color;
    static int my_score = 0;
    static FrameLayout frameLayout;
    static Button close_btn;
    static TextView player1_tv;
    static TextView player2_tv;
    static TextView vs_tv;
    static TextView winOrLose_tv;


    static DotsAndBoxesListener.OnFinishGame onFinishGame = new DotsAndBoxesListener.OnFinishGame() {
        @Override
        public void onFinishGame(String winner) {
            Log.e("sdhfalfdjsa","go to show box .................");
            ((Activity)context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.e("sfdjkalfjs","show box started........................");
                    gridView.setVisibility(View.INVISIBLE);
                    frameLayout.setVisibility(View.VISIBLE);
                    if ( gameState.equals("Casual")){
                        if ( members.size()==2){
                            player1_tv.setText(members.get(0));
                            player2_tv.setText(members.get(1));
                        }else {
                            player1_tv.setVisibility(View.INVISIBLE);
                            player2_tv.setVisibility(View.INVISIBLE);
                            vs_tv.setVisibility(View.INVISIBLE);
                        }
                        if ( !winner.equals(MainActivity.userName))
                            winOrLose_tv.setText(winner + " won!");
                        close_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if ( members.indexOf(MainActivity.userName)==0) {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Socket socket = new Socket(SplashScreenActivity.IP, 6666);
                                                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                                                dos.writeUTF("game");
                                                dos.flush();
                                                dos.writeUTF("removeRoom");
                                                dos.flush();
                                                dos.writeUTF("dots and boxes");
                                                dos.flush();
                                                dos.writeUTF(room_name);
                                                dos.flush();
                                            }catch (IOException io){
                                                io.printStackTrace();
                                            }
                                        }
                                    }).start();
                                }
                                ((Activity)context).finish();
                            }
                        });
                    }else if ( gameState.equals("Ranked")){
                        player1_tv.setText(members.get(0));
                        player2_tv.setText(members.get(1));
                        if ( !winner.equals(MainActivity.userName))
                            winOrLose_tv.setText(winner + " won!");
                        else{
                            int score = SingletonUserContainer.getInstance().getGameScore().get(2);
                            SingletonUserContainer.getInstance().getGameScore().set(2, score + 30);
                            SendScoreToServer sendScoreToServer = new SendScoreToServer(2, score + 30);
                            Thread thread = new Thread(sendScoreToServer);
                            thread.start();
                        }
                        close_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            Socket socket = new Socket(SplashScreenActivity.IP, 6666);
                                            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                                            dos.writeUTF("removePlayerFromRankedGame");
                                            dos.flush();
                                            dos.writeInt(2);
                                            dos.flush();
                                            dos.writeUTF(MainActivity.userName);
                                            dos.flush();
                                        }catch (IOException io){
                                            io.printStackTrace();
                                        }
                                    }
                                }).start();
                                ((Activity)context).finish();
                            }
                        });
                    }
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
            room_name = intent.getStringExtra("RoomName");
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
            members = new ArrayList<>();
            String opponent = intent.getStringExtra("opponent");
            if ( color.equals("blue")){
                my_color = R.drawable.blue_line_dotandbox;
                members.add(MainActivity.userName);
                members.add(opponent);
                is_my_turn = true;
            }else if ( color.equals("red")){
                my_color = R.drawable.red_line_dotandbox;
                members.add(opponent);
                members.add(MainActivity.userName);
                is_my_turn = false;
            }
        }




        context = DotAndBoxPageActivity.this;
        gridView = findViewById(R.id.grid_view_dots_and_boxes);
        frameLayout = findViewById(R.id.frameLayout_dots_and_boxes_wonOrLoseBox);
        close_btn = findViewById(R.id.btn_dots_and_boxes_closeBtn);
        player1_tv = findViewById(R.id.tv_dots_and_boxes_player1Name_inWonBox);
        player2_tv = findViewById(R.id.tv_dots_and_boxes_player2Name_inWonBox);
        winOrLose_tv = findViewById(R.id.tv_dots_and_boxes_wonOrloseTxt);
        vs_tv = findViewById(R.id.vs_dots_and_boxes);

        new Thread(new DotsAndBoxesListener(context)).start();

        GridViewDotsAndBoxesAdapter adapter = new GridViewDotsAndBoxesAdapter(DotAndBoxPageActivity.this);
        gridView.setAdapter(adapter);

    }
}
