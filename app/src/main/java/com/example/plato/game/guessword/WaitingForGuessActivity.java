package com.example.plato.game.guessword;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import com.example.plato.R;

public class WaitingForGuessActivity extends AppCompatActivity {

    TextView main_word_tv;
    static TextView guess_word_tv;
    static String main_word_string ;
    static String guess_word_string ;
    static Context context;
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

        context = this.getApplicationContext();

        main_word_string = getIntent().getStringExtra("word");

        main_word_tv = findViewById(R.id.main_word);
        guess_word_tv = findViewById(R.id.guess_word);

        for (int i = 0; i < main_word_string.length(); i++) {
            guess_word_string+="?";
        }

        main_word_tv.setText(main_word_string);
        guess_word_tv.setText(guess_word_string);
    }

    public static interface setOnUpdateUiForGuessWordGameChanges{
        void onUpdateUiForGuessWordGameChanges(String converted);
    }
}
