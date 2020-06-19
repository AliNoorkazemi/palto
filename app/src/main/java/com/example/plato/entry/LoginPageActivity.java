package com.example.plato.entry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.plato.MainActivity;
import com.example.plato.R;
import com.example.plato.serverdatabase.User;

import java.nio.file.Watchable;
import java.util.Arrays;

public class LoginPageActivity extends AppCompatActivity {
    EditText username_et;
    EditText password_et;
    Button login_btn;
    TextView warningText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        username_et=findViewById(R.id.et_loginPage_username);
        password_et=findViewById(R.id.et_loginPage_password);
        warningText=findViewById(R.id.tv_loginPage_warning);
        login_btn=findViewById(R.id.btn_loginPage_login);






        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginPageActivity.this, MainActivity.class);
                User user=new User();
                user.setUser_name(username_et.getText().toString());
                user.setPassword(password_et.getText().toString());
                intent.putExtra("ExistUser", user);
                startActivity(intent);
            }
        });

    }
}