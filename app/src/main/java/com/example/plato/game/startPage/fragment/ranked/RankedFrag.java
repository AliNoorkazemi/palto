package com.example.plato.game.startPage.fragment.ranked;

import android.content.Intent;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.plato.MainActivity;
import com.example.plato.R;
import com.example.plato.SingletonUserContainer;
import com.example.plato.SplashScreenActivity;
import com.example.plato.game.XOGamePageActivity;
import com.example.plato.game.dotsandboxes.DotAndBoxPageActivity;
import com.example.plato.game.guessword.GuessWordActivity;
import com.example.plato.game.guessword.WaitingForGuessActivity;
import com.example.plato.game.startPage.StartGamePageActivity;
import com.example.plato.network.XoGameListener;
import com.google.android.material.snackbar.Snackbar;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RankedFrag extends Fragment {
    View view;
    Button play_btn;
    TextView gameName_tv;
    TextView gameScore_tv;
    ImageView gameImage_iv;
    CoordinatorLayout coordinatorLayout;
    int gameIndex;
    boolean is_looking_cancle = false;
    Snackbar snackbar;
    public static int gameScore;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_ranked, container, false);

        Log.i("where", "onCreateView: rankedfrag");


        gameName_tv = view.findViewById(R.id.tv_rankedFrag_gameName);
        gameScore_tv = view.findViewById(R.id.tv_rankedFrag_gameScore);
        play_btn = view.findViewById(R.id.btn_rankedFrag_play);
        gameImage_iv = view.findViewById(R.id.iv_rankedFragment_gameImage);
        coordinatorLayout = view.findViewById(R.id.cdl_rankedFrag_coordinatoorLayout);

        String gameName = StartGamePageActivity.game.getGame_name();
        gameName_tv.setText(gameName);
        if (gameName.equals("xo")) {
            gameImage_iv.setImageResource(R.drawable.xo_rankedfrag_icon);
            gameIndex = 0;
        } else if (gameName.equals("guess word")) {
            gameImage_iv.setImageResource(R.drawable.guessword_rankedfrag_icon);
            gameIndex = 1;
        } else {
            gameImage_iv.setImageResource(R.drawable.dotandbox_rankedfrag_icon);
            gameIndex = 2;
        }




        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play_btn.setBackground(getContext().getDrawable(R.drawable.gray_boarder));
                play_btn.setClickable(false);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Socket socket = new Socket(SplashScreenActivity.IP, 6666);
                            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                            DataInputStream dis = new DataInputStream(socket.getInputStream());

                            dos.writeUTF("searchForRankedGame");
                            dos.flush();


                            Log.i("where", "gameIndex: " + gameIndex);
                            dos.writeUTF(String.valueOf(gameIndex));
                            dos.flush();
                            dos.writeUTF(String.valueOf(gameScore));
                            dos.flush();
                            dos.writeUTF(MainActivity.userName);
                            dos.flush();

                            String play_status = dis.readUTF();
                            if (play_status.equals("startRankedGamePlayer2")) {
                                String opponentName = dis.readUTF();
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = null;
                                        if (gameIndex == 0) {//xo
                                            intent = new Intent(view.getContext(), XOGamePageActivity.class);
                                            intent.putExtra("gameState", "Ranked");//***
                                            intent.putExtra("areYouO", false);
                                            intent.putExtra("opponent", opponentName);
                                        } else if (gameIndex == 1) {//guess word
                                            intent = new Intent(view.getContext(), GuessWordActivity.class);
                                            intent.putExtra("gameState","Ranked");
                                            intent.putExtra("round",1);
                                            intent.putExtra("opponent",opponentName);
                                        } else if (gameIndex == 2) {//dotAndBox
                                            intent=new Intent(view.getContext(), DotAndBoxPageActivity.class);
                                            intent.putExtra("gameState","Ranked");
                                            intent.putExtra("color","red");
                                            intent.putExtra("opponent",opponentName);
                                        }
                                        play_btn.setClickable(true);
                                        if ( snackbar!= null)
                                            snackbar.dismiss();
                                        startActivity(intent);
                                        play_btn.setBackground(getContext().getDrawable(R.drawable.green_boarder));
                                    }
                                });


                            } else {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        snackbar = Snackbar.make(coordinatorLayout, "Looking for another player ...", Snackbar.LENGTH_LONG);
                                        snackbar.setDuration(500000);
                                        snackbar.setAction("cancel", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                is_looking_cancle = true;
                                                play_btn.setBackground(getContext().getDrawable(R.drawable.green_boarder));
                                                play_btn.setClickable(true);
                                                snackbar.dismiss();
                                            }
                                        });
                                        snackbar.show();
                                    }
                                });

                                while (true) {
                                    try {
                                        String str = dis.readUTF();
                                        if (str.equals("startRankedGamePlayer1")) {
                                            String opponentName = dis.readUTF();
                                            Intent intent = null;
                                            if (gameIndex == 0) {//xo
                                                intent = new Intent(view.getContext(), XOGamePageActivity.class);
                                                intent.putExtra("gameState", "Ranked");//***
                                                intent.putExtra("areYouO", true);
                                                intent.putExtra("opponent", opponentName);
                                            } else if (gameIndex == 1) {//guess word
                                                intent = new Intent(view.getContext(), WaitingForGuessActivity.class);
                                                intent.putExtra("gameState","Ranked");
                                                intent.putExtra("round",1);
                                                intent.putExtra("opponent",opponentName);
                                            } else if (gameIndex == 2) {//dotAndBox
                                                intent=new Intent(view.getContext(), DotAndBoxPageActivity.class);
                                                intent.putExtra("gameState","Ranked");
                                                intent.putExtra("color","blue");
                                                intent.putExtra("opponent",opponentName);
                                            }
                                            if ( snackbar!= null)
                                                snackbar.dismiss();
                                            startActivity(intent);
                                            getActivity().runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    play_btn.setBackground(getContext().getDrawable(R.drawable.green_boarder));
                                                    play_btn.setClickable(true);
                                                }
                                            });
                                            break;
                                        }
                                        if(is_looking_cancle){
                                            dos.writeUTF("cancel");
                                            dos.flush();
                                            break;
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });


        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        gameScore = SingletonUserContainer.getInstance().getGameScore().get(gameIndex);
        gameScore_tv.setText("score: " + gameScore);
    }
}
