package com.example.plato.entry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.plato.R;

public class EntryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        Button logIn_button = findViewById(R.id.LogInButton);
        Button register_button = findViewById(R.id.RegisterButton);


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
