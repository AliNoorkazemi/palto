package com.example.plato.game.startPage.fragment.leaderboard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.plato.ConvertBitmapByte;
import com.example.plato.MainActivity;
import com.example.plato.R;
import com.example.plato.SingletonUserContainer;
import com.example.plato.SplashScreenActivity;
import com.example.plato.game.startPage.StartGamePageActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderBoardFrag extends Fragment {
    ArrayList<BestPlayer> bestPlayers;
    RecyclerView recyclerView;
    AdapterLeaderBoard adapter;

    LinearLayout linearLayout1;
    TextView player1Name_tv;
    TextView player1Score_tv;
    ImageView player1Image_iv;
    LinearLayout linearLayout2;
    TextView player2Name_tv;
    TextView player2Score_tv;
    ImageView player2Image_iv;
    LinearLayout linearLayout3;
    TextView player3Name_tv;
    TextView player3Score_tv;
    ImageView player3Image_iv;

    TextView myName_tv;
    TextView myScore_tv;
    ImageView myImage_iv;

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("where", "onCreate: leaderboard");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_leader_board, container, false);

        Log.i("where", "onCreateView: leaderboard");


        bestPlayers=new ArrayList<>();
        Thread thread = new Thread(new GetBestPlayerThread());
        thread.start();

        linearLayout1=view.findViewById(R.id.line1_linear);
        linearLayout2=view.findViewById(R.id.line2_linear);
        linearLayout3=view.findViewById(R.id.line3_linear);
        player1Name_tv=view.findViewById(R.id.tv_leaderboard_1_name);
        player2Name_tv=view.findViewById(R.id.tv_leaderboard_2_name);
        player3Name_tv=view.findViewById(R.id.tv_leaderboard_3_name);
        player1Score_tv=view.findViewById(R.id.tv_leaderboard_1_score);
        player2Score_tv=view.findViewById(R.id.tv_leaderboard_2_score);
        player3Score_tv=view.findViewById(R.id.tv_leaderboard_3_score);
        player1Image_iv=view.findViewById(R.id.iv_leaderboard_1_image);
        player2Image_iv=view.findViewById(R.id.iv_leaderboard_2_image);
        player3Image_iv=view.findViewById(R.id.iv_leaderboard_3_image);


        myName_tv=view.findViewById(R.id.tv_leaderboardFrag_MyName);
        myImage_iv=view.findViewById(R.id.iv_leaderboardFrag_MyImage);
        myScore_tv=view.findViewById(R.id.tv_leaderboardFrag_MyScore);


        myName_tv.setText(MainActivity.userName);
        int gameIndex=0;
        if(StartGamePageActivity.game.getGame_name().equals("xo"))
            gameIndex=0;
        else if(StartGamePageActivity.game.getGame_name().equals("guess word"))
            gameIndex=1;
        else
            gameIndex=2;
        myScore_tv.setText(String.valueOf(SingletonUserContainer.getInstance().getGameScore().get(gameIndex)));
        myImage_iv.setImageBitmap(MainActivity.profile_bitmap);



        return view;
    }

    class GetBestPlayerThread extends Thread {
        @Override
        public void run() {
            try {
                Socket socket = new Socket(SplashScreenActivity.IP, 6666);
                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                dos.writeUTF("getBestPlayer");
                dos.flush();
                dos.writeUTF(StartGamePageActivity.game.getGame_name());
                dos.flush();

                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());

                Map<String, Integer> name_score = (Map<String, Integer>) ois.readObject();
                Map<String, String> name_image = (Map<String, String>) ois.readObject();

                for (String name :
                        name_score.keySet()) {
                    BestPlayer bestPlayer = new BestPlayer();
                    bestPlayer.setName(name);
                    bestPlayer.setScore(name_score.get(name));
                    if (name_image.containsKey(name)) {
                        byte[] array_byte= Base64.decode(name_image.get(name),Base64.DEFAULT);
                        bestPlayer.setImage_bytes(ConvertBitmapByte.byteTobitmap(array_byte));
                    } else {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_image);
                        bestPlayer.setImage_bytes(bitmap);
                    }

                    bestPlayers.add(bestPlayer);

                }
                Collections.sort(bestPlayers, new Comparator<BestPlayer>() {
                    @Override
                    public int compare(BestPlayer o1, BestPlayer o2) {
                        return o1.getScore()-o2.getScore();
                    }
                });

                Log.i("where", "bestplayerlenght : "+bestPlayers.size());
                ArrayList<BestPlayer> bestPlayerAdapter = new ArrayList<>();
                for (int i = 3; i < bestPlayers.size(); i++) {
                    bestPlayerAdapter.add(bestPlayers.get(i));
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("where", "bestplayeradapterlenght : "+bestPlayerAdapter.size()
                        );

                        if(bestPlayers.size()>0){
                            linearLayout1.setVisibility(View.VISIBLE);
                            //player1Image_iv.setImageBitmap(ConvertBitmapByte.byteTobitmap(bestPlayers.get(0).getImage_bytes()));
                            player1Name_tv.setText(bestPlayers.get(0).getName());
                            player1Score_tv.setText(String.valueOf(bestPlayers.get(0).getScore()));
                            player1Image_iv.setImageBitmap(bestPlayers.get(0).getImage_bytes());
                        }
                        if(bestPlayers.size()>1){
                            linearLayout2.setVisibility(View.VISIBLE);
                           // player2Image_iv.setImageBitmap(ConvertBitmapByte.byteTobitmap(bestPlayers.get(1).getImage_bytes()));
                            player2Name_tv.setText(bestPlayers.get(1).getName());
                            player2Score_tv.setText(String.valueOf(bestPlayers.get(1).getScore()));
                            player1Image_iv.setImageBitmap(bestPlayers.get(1).getImage_bytes());
                        }
                        if(bestPlayers.size()>2){
                            linearLayout3.setVisibility(View.VISIBLE);

                            //player3Image_iv.setImageBitmap(ConvertBitmapByte.byteTobitmap(bestPlayers.get(2).getImage_bytes()));
                            player3Name_tv.setText(bestPlayers.get(2).getName());
                            player3Score_tv.setText(String.valueOf(bestPlayers.get(2).getScore()));
                            player1Image_iv.setImageBitmap(bestPlayers.get(2).getImage_bytes());
                        }


                        recyclerView = view.findViewById(R.id.rc_leaderboardFrag_recyclerView);
                        adapter = new AdapterLeaderBoard(view.getContext(), bestPlayerAdapter);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
                    }
                });


            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }


        }
    }

}
