package com.example.plato.entry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.plato.*;



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
                intent.putExtra("userName",username_et.getText().toString());
                intent.putExtra("password",password_et.getText().toString());
                startActivity(intent);
                finish();
            }
        });

    }
}
