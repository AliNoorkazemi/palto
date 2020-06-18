package com.example.plato;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.Arrays;

public class RegisterPageActivity extends AppCompatActivity {
    EditText username_et;
    EditText password_et;
    EditText confirmPassword_et;
    Button register_btn;
    boolean[] is_valid;


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


        username_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (username_et.getText().toString().length() == 0) {
                    username_et.setHint("username should not be empty...");
                    username_et.setHintTextColor(getColor(R.color.red));
                } else {
                    is_valid[0] = true;
                }
            }
        });

        password_et.addTextChangedListener(new TextWatcher() {
            TextView passwordWarn;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordWarn.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (password_et.getText().toString().length() <= 5) {
                    passwordWarn = findViewById(R.id.tv_registerPage_passwordWarning);
                    passwordWarn.setVisibility(View.VISIBLE);
                    passwordWarn.setText("password must be more than 5 character");
                } else
                    is_valid[1] = true;
            }
        });

        confirmPassword_et.addTextChangedListener(new TextWatcher() {
            TextView passwordConifWarn;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordConifWarn.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!confirmPassword_et.getText().toString().equals(password_et.getText().toString())) {
                    passwordConifWarn.setVisibility(View.VISIBLE);
                    passwordConifWarn.setText("confirm password does not match ...");
                } else
                    is_valid[2] = true;
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
