package com.example.plato.entry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.plato.MainActivity;
import com.example.plato.R;

public class EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        Button logIn_button = findViewById(R.id.LogInButton);
        Button register_button = findViewById(R.id.RegisterButton);
        Button to_main_btn = findViewById(R.id.MainButton);

        to_main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent to_main_intent = new Intent (EntryActivity.this, MainActivity.class);
                startActivity(to_main_intent);
            }
        });


        logIn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_to_Login_page = new Intent(EntryActivity.this,LoginPageActivity.class);
                startActivity(intent_to_Login_page);
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_to_Login_page = new Intent(EntryActivity.this,RegisterPageActivity.class);
                startActivity(intent_to_Login_page);
            }
        });
    }
}
