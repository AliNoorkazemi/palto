package com.example.plato.game.guessword;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plato.game.guessword.WaitingForGuessActivity.setOnUpdateUiForGuessWordGameChanges;
import com.example.plato.R;


public class GuessWordActivity extends AppCompatActivity {

    private String word;
    private ConstraintLayout constraintLayout;
    private TextView guess_word_tv;
    private TextView validation_warning_tv;
    private EditText guess_word_et;
    private Button validation_guess_btn;
    private TextView chances_number_tv;
    private setOnUpdateUiForGuessWordGameChanges setOnUpdateUiForGuessWordGameChanges;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guess_word);

        word = getIntent().getStringExtra("word");

        String guess_word_string = "";
        for (int i = 0; i < word.length(); i++) {
            guess_word_string += "?";
        }

        constraintLayout = findViewById(R.id.constraintLayout);
        guess_word_tv = findViewById(R.id.word_tobe_guess);
        validation_warning_tv = findViewById(R.id.validation_warning);
        validation_warning_tv.setVisibility(View.INVISIBLE);
        guess_word_tv.setText(guess_word_string);
        guess_word_tv.setVisibility(View.VISIBLE);
        guess_word_et = findViewById(R.id.guess_edit_text);
        validation_guess_btn = findViewById(R.id.validate_guess_button);
        chances_number_tv = findViewById(R.id.chances_number);
        chances_number_tv.setText(String.valueOf(word.length()));

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
                                    setOnUpdateUiForGuessWordGameChanges = WaitingForGuessActivity.setOnUpdateUiForGuessWordGameChanges;
                                    setOnUpdateUiForGuessWordGameChanges.onUpdateUiForGuessWordGameChanges(converted);
                                }
                            }
                        }
                    });
                }
            }
        });
    }
}
