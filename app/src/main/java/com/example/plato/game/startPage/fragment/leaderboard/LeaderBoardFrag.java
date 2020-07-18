package com.example.plato.game.startPage.fragment.leaderboard;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.plato.ConvertBitmapByte;
import com.example.plato.R;
import com.example.plato.SplashScreenActivity;
import com.example.plato.game.startPage.StartGamePageActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LeaderBoardFrag extends Fragment {
    ArrayList<BestPlayer> bestPlayers;
    RecyclerView recyclerView;
    AdapterLeaderBoard adapter;

    TextView player1Name_tv;
    TextView player1Score_tv;
    ImageView player1Score_iv;
    TextView player2Name_tv;
    TextView player2Score_tv;
    ImageView player2Score_iv;
    TextView player3Name_tv;
    TextView player3Score_tv;
    ImageView player3Score_iv;


    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_leader_board, container, false);

        Log.i("where", "onCreateView: leaderboard");

        Thread thread = new Thread(new GetBestPlayerThread());
        thread.start();

        bestPlayers=new ArrayList<>();

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
                Map<String, Integer> name_ranked = (Map<String, Integer>) ois.readObject();
                Map<String, byte[]> name_image = (Map<String, byte[]>) ois.readObject();

                for (String name :
                        name_score.keySet()) {
                    BestPlayer bestPlayer = new BestPlayer();
                    bestPlayer.setName(name);
                    bestPlayer.setScore(name_score.get(name));
                    bestPlayer.setQueNo(name_ranked.get(name));
                    if (name_image.containsKey(name)) {
                        bestPlayer.setImage_bytes(name_image.get(name));
                    } else {
                        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.avatar_image);
                        bestPlayer.setImage_bytes(ConvertBitmapByte.bitmapTobyte(bitmap));
                    }

                    bestPlayers.add(bestPlayer);

                }
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
