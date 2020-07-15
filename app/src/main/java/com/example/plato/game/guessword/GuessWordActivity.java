package com.example.plato.game.guessword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plato.game.SingletonGameContainer;
import com.example.plato.game.guessword.WaitingForGuessActivity.setOnUpdateUiForGuessWordGameChanges;
import com.example.plato.R;
import com.example.plato.game.startPage.StartGamePageActivity;

import java.io.IOException;


public class GuessWordActivity extends AppCompatActivity {

    private static String word;
    String opponent;
    Integer round;
    private static Context context ;
    private ConstraintLayout constraintLayout;
    private static TextView guess_word_tv;
    private TextView validation_warning_tv;
    private EditText guess_word_et;
    private Button validation_guess_btn;
    private static TextView chances_number_tv;
    private static String guess_word_string = "";
    private setOnUpdateUiForGuessWordGameChanges setOnUpdateUiForGuessWordGameChanges;
    private static ProgressDialog progressDialog;
    public static SetOnGetWord setOnGetWord = new SetOnGetWord() {
        @Override
        public void onGetWord(String word) {
            GuessWordActivity.word = word;
            for (int i = 0; i < GuessWordActivity.word.length(); i++) {
                Log.i("guess_word_string ",guess_word_string);
                guess_word_string += "?";
                Log.i("guess_word_string ",guess_word_string);
            }
            Activity origin = (Activity) context;
            origin.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    guess_word_tv.setText(guess_word_string);
                    Log.i("guess_word_tv . text " , guess_word_tv.getText().toString());
                    chances_number_tv.setText(String.valueOf(word.length()));
                    progressDialog.cancel();
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_word);

        Intent intent = getIntent();
        opponent = intent.getStringExtra("opponent");
        round = intent.getIntExtra("round",1);

        context = GuessWordActivity.this;

        GuessWordListener guessWordListener = new GuessWordListener();
        guessWordListener.start();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("your rival are trying to choose a word please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        constraintLayout = findViewById(R.id.constraintLayout);
        guess_word_tv = findViewById(R.id.word_tobe_guess);
        validation_warning_tv = findViewById(R.id.validation_warning);
        validation_warning_tv.setVisibility(View.INVISIBLE);
        guess_word_tv.setVisibility(View.VISIBLE);
        guess_word_et = findViewById(R.id.guess_edit_text);
        validation_guess_btn = findViewById(R.id.validate_guess_button);
        chances_number_tv = findViewById(R.id.chances_number);

        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
            }
        });

        validation_guess_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(),0);
                if (guess_word_et.getText().toString().equals(""))
                    Toast.makeText(GuessWordActivity.this, "please enter a letter before validate", Toast.LENGTH_SHORT).show();
                else if (guess_word_et.getText().toString().length() > 1)
                    Toast.makeText(GuessWordActivity.this, "you should not enter more than 1 letter", Toast.LENGTH_SHORT).show();
                else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            chances_number_tv.setText(String.valueOf(Integer.parseInt(chances_number_tv.getText().toString())-1));
                            boolean wrong = true;
                            for (int i = 0; i < word.length(); i++) {
                                String str1 = String.valueOf(word.charAt(i));
                                String str2 = guess_word_et.getText().toString();
                                if (str1.equalsIgnoreCase(str2)) {
                                    wrong = false;
                                    break;
                                }
                            }
                            if (wrong) {
                                validation_warning_tv.setText("wrong");
                                validation_warning_tv.setTextColor(Color.parseColor("#E53935"));
                                validation_warning_tv.setVisibility(View.VISIBLE);
                            }else {
                                boolean duplicated = false;
                                for (int i = 0; i < word.length(); i++) {
                                    String str1 = String.valueOf(guess_word_tv.getText().toString().charAt(i));
                                    String str2 = guess_word_et.getText().toString();
                                    if(str1.equalsIgnoreCase(str2)){
                                        duplicated = true;
                                        break;
                                    }
                                }
                                if (duplicated) {
                                    validation_warning_tv.setText("duplicated");
                                    validation_warning_tv.setTextColor(Color.parseColor("#E53935"));
                                    validation_warning_tv.setVisibility(View.VISIBLE);
                                } else {
                                    validation_warning_tv.setText("correct");
                                    validation_warning_tv.setTextColor(Color.parseColor("#FF388E3C"));
                                    validation_warning_tv.setVisibility(View.VISIBLE);
                                    String converted = "";
                                    for (int i = 0; i < word.length(); i++) {
                                        String str1 = String.valueOf(word.charAt(i));
                                        String str2 = guess_word_et.getText().toString();
                                        if (str1.equalsIgnoreCase(str2))
                                            converted += word.charAt(i);
                                        else
                                            converted += guess_word_tv.getText().toString().charAt(i);
                                    }
                                    guess_word_tv.setText(converted);
                                    if(guess_word_tv.getText().toString().equals(word)){
                                        if(round==2){
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        guessWordListener.dos.writeUTF(opponent);
                                                        guessWordListener.dos.flush();
                                                        guessWordListener.dos.writeUTF("win");
                                                        guessWordListener.dos.flush();
                                                    }catch (IOException io){
                                                        io.printStackTrace();
                                                    }
                                                }
                                            }).start();
                                            Intent back_to_start_game = new Intent(GuessWordActivity.this, StartGamePageActivity.class);
                                            back_to_start_game.putExtra("game name","guess word");
                                            back_to_start_game.putExtra("GAME", SingletonGameContainer.getGuessWord());
                                            startActivity(back_to_start_game);
                                        }else {
                                            new Thread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    try {
                                                        guessWordListener.dos.writeUTF(opponent);
                                                        guessWordListener.dos.flush();
                                                        guessWordListener.dos.writeUTF("win");
                                                        guessWordListener.dos.flush();
                                                    }catch (IOException io){
                                                        io.printStackTrace();
                                                    }
                                                }
                                            }).start();
                                            Intent intent_to_waiting_activity = new Intent(GuessWordActivity.this, WaitingForGuessActivity.class);
                                            intent_to_waiting_activity.putExtra("opponent", opponent);
                                            intent_to_waiting_activity.putExtra("round", round + 1);
                                            intent_to_waiting_activity.putExtra("win", true);
                                            startActivity(intent_to_waiting_activity);
                                        }
                                    }
                                }
                            }
                            if(chances_number_tv.getText().toString().equals(String.valueOf(0))){
                                if(round==2){
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                guessWordListener.dos.writeUTF(opponent);
                                                guessWordListener.dos.flush();
                                                guessWordListener.dos.writeUTF("lose");
                                                guessWordListener.dos.flush();
                                            }catch (IOException io){
                                                io.printStackTrace();
                                            }
                                        }
                                    }).start();
                                    Intent back_to_start_game = new Intent(GuessWordActivity.this, StartGamePageActivity.class);
                                    back_to_start_game.putExtra("game name","guess word");
                                    back_to_start_game.putExtra("GAME", SingletonGameContainer.getGuessWord());
                                    startActivity(back_to_start_game);
                                }else {
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                guessWordListener.dos.writeUTF(opponent);
                                                guessWordListener.dos.flush();
                                                guessWordListener.dos.writeUTF("lose");
                                                guessWordListener.dos.flush();
                                            }catch (IOException io){
                                                io.printStackTrace();
                                            }
                                        }
                                    }).start();
                                    Intent intent_to_waiting_activity = new Intent(GuessWordActivity.this, WaitingForGuessActivity.class);
                                    intent_to_waiting_activity.putExtra("opponent", opponent);
                                    intent_to_waiting_activity.putExtra("round", round + 1);
                                    intent_to_waiting_activity.putExtra("win", false);
                                    startActivity(intent_to_waiting_activity);
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    public static interface SetOnGetWord{
        void onGetWord(String word);
    }
}
