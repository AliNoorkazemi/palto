package com.example.plato.entry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.plato.*;


import java.util.Arrays;

public class RegisterPageActivity extends AppCompatActivity {
    EditText username_et;
    EditText password_et;
    EditText confirmPassword_et;
    Button register_btn;
    boolean[] is_valid;

    private boolean on_password_clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);


        is_valid = new boolean[3];
        Arrays.fill(is_valid, false);


        username_et = findViewById(R.id.et_registerPage_username);
        password_et = findViewById(R.id.et_registerPage_password);
        confirmPassword_et = findViewById(R.id.et_registerPage_passwordConfirm);
        register_btn = findViewById(R.id.btn_registerPage_register);


        username_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    is_valid[0] = true;

                } else {
                    if (username_et.getText().toString().length() == 0) {
                        username_et.setHint("username should not be empty...");
                        username_et.setHintTextColor(getColor(R.color.red));
                        is_valid[0] = false;
                    }

                }
            }
        });


        password_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            TextView passwordWarn = findViewById(R.id.tv_registerPage_passwordWarning);

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    passwordWarn.setVisibility(View.INVISIBLE);
                    is_valid[1] = true;
                } else if (password_et.getText().toString().length() <= 5) {
                    passwordWarn.setVisibility(View.VISIBLE);
                    is_valid[1] = false;
                    passwordWarn.setText("password must be more than 5 character");
                }

            }
        });

        confirmPassword_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            TextView conifWarn = findViewById(R.id.tv_registerPage_passwordConfWarning);

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    conifWarn.setVisibility(View.INVISIBLE);
                    is_valid[2] = true;
                } else {
                    if (!confirmPassword_et.getText().toString().equals(password_et.getText().toString())) {
                        is_valid[2] = false;
                        conifWarn.setText("Password and confirm password don't match");
                        conifWarn.setVisibility(View.VISIBLE);
                    }

                }
            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_valid[0] && is_valid[1] && is_valid[2]) {
                    Intent intent = new Intent(RegisterPageActivity.this, MainActivity.class);
                    User user = new User();
                    user.setUser_name(username_et.getText().toString());
                    user.setPassword(password_et.getText().toString());
                    intent.putExtra("NewUser", user);
                    startActivity(intent);
                }
            }
        });

    }
}