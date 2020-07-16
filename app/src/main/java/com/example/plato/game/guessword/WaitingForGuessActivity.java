package com.example.plato.game.guessword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plato.MainActivity;
import com.example.plato.R;
import com.example.plato.SplashScreenActivity;
import com.example.plato.game.SingletonGameContainer;
import com.example.plato.game.startPage.StartGamePageActivity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class WaitingForGuessActivity extends AppCompatActivity {

    TextView main_word_tv;
    static TextView guess_word_tv;
    static String main_word_string ;
    static String guess_word_string ;
    static Context context;
    EditText choose_word_tobe_guessed_et;
    Button choose_word_tobe_guessed_btn;
    ConstraintLayout constraintLayout;
    Socket socket ;
    DataInputStream dis;
    DataOutputStream dos;
    String opponent ;
    Integer round;

    public static  setOnUpdateUiForGuessWordGameChanges setOnUpdateUiForGuessWordGameChanges =
            new setOnUpdateUiForGuessWordGameChanges() {
                @Override
                public void onUpdateUiForGuessWordGameChanges(String converted) {
                    guess_word_string = converted;
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            guess_word_tv.setText(guess_word_string);
                        }
                    });
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_for_guess);

        Intent intent = getIntent();
        opponent = intent.getStringExtra("opponent");
        round = intent.getIntExtra("round",1);
        Log.i("listening round two",String.valueOf(round));

        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Log.i("listening round two","ksfjdlajfld;lsafl;as;flsdafjlsadfsdjf;s");
                    Socket socket = new Socket("192.168.1.4",6666);
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                    DataInputStream dis = new DataInputStream(socket.getInputStream());
                    dos.writeUTF("listening round two");
                    dos.flush();
                    dos.writeUTF(MainActivity.userName);
                    dos.flush();
                    String result = dis.readUTF();
                    dos.writeUTF("exit");
                    dos.flush();
                    socket.close();
                    if(round==2){
                        Intent back_to_start_game = new Intent(WaitingForGuessActivity.this, StartGamePageActivity.class);
                        back_to_start_game.putExtra("game name","guess word");
                        back_to_start_game.putExtra("GAME", SingletonGameContainer.getGuessWord());
                        startActivity(back_to_start_game);
                        finish();
                    }else {
                        Intent intent_to_guess_activity = new Intent(WaitingForGuessActivity.this, GuessWordActivity.class);
                        intent_to_guess_activity.putExtra("opponent", opponent);
                        intent_to_guess_activity.putExtra("round", round + 1);
                        intent_to_guess_activity.putExtra("result",result);
                        startActivity(intent_to_guess_activity);
                        finish();
                    }
                }catch (IOException io){
                    io.printStackTrace();
                }
            }
        }).start();


        main_word_tv = findViewById(R.id.main_word);
        guess_word_tv = findViewById(R.id.guess_word);
        main_word_tv.setVisibility(View.INVISIBLE);
        guess_word_tv.setVisibility(View.INVISIBLE);

        constraintLayout = findViewById(R.id.activity_start_guess_word_game);
        choose_word_tobe_guessed_et = findViewById(R.id.choose_word_tobe_guessed_editText);
        choose_word_tobe_guessed_btn = findViewById(R.id.choose_word_tobe_guessed);
        choose_word_tobe_guessed_btn.setVisibility(View.VISIBLE);
        choose_word_tobe_guessed_et.setVisibility(View.VISIBLE);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });

        choose_word_tobe_guessed_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
                if(choose_word_tobe_guessed_et.getText().toString().length()<3){
                    Toast.makeText(WaitingForGuessActivity.this,"the word must have more than 2 character",Toast.LENGTH_SHORT).show();
                }else{
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                socket = new Socket(SplashScreenActivity.IP,6666);
                                dis = new DataInputStream(socket.getInputStream());
                                dos = new DataOutputStream(socket.getOutputStream());
                                dos.writeUTF("guess word send data");
                                dos.flush();
                                dos.writeUTF(opponent);
                                dos.flush();
                                dos.writeUTF(choose_word_tobe_guessed_et.getText().toString());
                                dos.flush();
                            }catch (IOException io){
                                io.printStackTrace();
                            }
                        }
                    }).start();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            choose_word_tobe_guessed_et.setVisibility(View.INVISIBLE);
                            choose_word_tobe_guessed_btn.setVisibility(View.INVISIBLE);
                            main_word_tv.setVisibility(View.VISIBLE);
                            guess_word_tv.setVisibility(View.VISIBLE);
                            main_word_string = choose_word_tobe_guessed_et.getText().toString();
                            main_word_tv.setText(main_word_string);
                        }
                    });
                }
            }
        });

        context = this.getApplicationContext();


        main_word_tv = findViewById(R.id.main_word);
        guess_word_tv = findViewById(R.id.guess_word);
    }

    public static interface setOnUpdateUiForGuessWordGameChanges{
        void onUpdateUiForGuessWordGameChanges(String converted);
    }
}
