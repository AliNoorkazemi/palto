package com.example.plato.entry;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.TypedArrayUtils;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.plato.*;
import com.example.plato.profile.ProfileActivity;


import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

public class RegisterPageActivity extends AppCompatActivity {
    EditText username_et;
    EditText password_et;
    EditText confirmPassword_et;
    Button register_btn;
    boolean[] is_valid;
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;
    TextView usernameWarn;
    TextView passwordWarn;
    TextView conifWarn;

    ImageButton profile_ib;
    Uri profile_uri = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket(SplashScreenActivity.IP, 6666);
                    dis = new DataInputStream(socket.getInputStream());
                    dos = new DataOutputStream(socket.getOutputStream());
                    dos.writeUTF("Register");
                    dos.flush();
                } catch (IOException io) {
                    io.printStackTrace();
                }
            }
        }).start();

        is_valid = new boolean[3];
        Arrays.fill(is_valid, false);


        username_et = findViewById(R.id.et_registerPage_username);
        password_et = findViewById(R.id.et_registerPage_password);
        confirmPassword_et = findViewById(R.id.et_registerPage_passwordConfirm);
        usernameWarn = findViewById(R.id.tv_registerPage_usernameWarning);
        register_btn = findViewById(R.id.btn_registerPage_register);
        passwordWarn = findViewById(R.id.tv_registerPage_passwordWarning);
        conifWarn = findViewById(R.id.tv_registerPage_passwordConfWarning);


        username_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    is_valid[0] = true;
                    usernameWarn.setVisibility(View.INVISIBLE);
                } else {
                    if (username_et.getText().toString().length() == 0) {
                        username_et.setHint("username should not be empty...");
                        username_et.setHintTextColor(getColor(R.color.red));
                        is_valid[0] = false;
                    } else {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                usernameWarn.setVisibility(View.INVISIBLE);
                                try {
                                    dos.writeUTF(username_et.getText().toString());
                                    dos.flush();
                                    String message = dis.readUTF();
                                    if (message.equals("Duplicated")) {
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                usernameWarn.setText("The username has been already exited");
                                                usernameWarn.setVisibility(View.VISIBLE);
                                            }
                                        });
                                        is_valid[0] = false;
                                    } else {
                                        is_valid[0] = true;
                                    }
                                } catch (IOException io) {
                                    io.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }
            }
        });


        password_et.setOnFocusChangeListener(new View.OnFocusChangeListener() {


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
                if (!password_et.getText().toString().equals(confirmPassword_et.getText().toString())) {
                    conifWarn.setText("Password and confirm password don't match");
                    conifWarn.setVisibility(View.VISIBLE);
                    return;
                }
                if (is_valid[0] && is_valid[1] && is_valid[2]) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            usernameWarn.setVisibility(View.INVISIBLE);
                            try {
                                dos.writeUTF(username_et.getText().toString());
                                dos.flush();
                                String message = dis.readUTF();
                                if (message.equals("Duplicated")) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            usernameWarn.setText("The username has been already exited");
                                            usernameWarn.setVisibility(View.VISIBLE);
                                        }
                                    });
                                    is_valid[0] = false;
                                } else {
                                    Intent intent = new Intent(RegisterPageActivity.this, MainActivity.class);
                                    intent.putExtra("Activity", "RegisterPageActivity");
                                    intent.putExtra("userName", username_et.getText().toString());
                                    try {
                                        dos.writeUTF("UserEnteredCorrectly");
                                        dos.flush();
                                        dos.writeUTF(username_et.getText().toString());
                                        dos.flush();
                                        dos.writeUTF(password_et.getText().toString());
                                        dos.flush();

                                        Bitmap bitmap;
                                        if (profile_uri != null)
                                            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), profile_uri);
                                        else
                                            bitmap = getRandomAvatarProfile();

                                        byte[] byteArray = ConvertBitmapByte.bitmapTobyte(bitmap);

                                        String prof = Base64.encodeToString(byteArray, Base64.DEFAULT);
                                        ObjectOutputStream oos=new ObjectOutputStream(socket.getOutputStream());
                                        oos.writeObject(prof);
                                        dos.flush();

                                        intent.putExtra("profile", byteArray);
                                        startActivity(intent);
                                        finish();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (OutOfMemoryError out) {
                                        RegisterPageActivity.this.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Toast.makeText(RegisterPageActivity.this, "image size is too large!", Toast.LENGTH_LONG).show();
                                            }
                                        });
                                    }
                                }
                            } catch (IOException io) {
                                io.printStackTrace();
                            }
                        }
                    }).start();
                } else if (!is_valid[0]) {
                    usernameWarn.setText("username should not be empty...");
                    usernameWarn.setVisibility(View.VISIBLE);
                }
                if (!is_valid[1]) {
                    passwordWarn.setText("password should not be empty...");
                    passwordWarn.setVisibility(View.VISIBLE);
                }
                if (!is_valid[2]) {
                    conifWarn.setVisibility(View.VISIBLE);
                    conifWarn.setText("confirm password should not be empty...");
                }
            }
        });

        profile_ib =

                findViewById(R.id.ib_registerPageActivity_profileImg);
        profile_ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery_intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery_intent, 1004);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1004 && resultCode == RESULT_OK && data != null) {

            profile_uri = data.getData();
            if (profile_uri != null){
                Bitmap bitmap = null;

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), profile_uri);
                } catch (IOException e) {



                }
                profile_ib.setImageBitmap(bitmap);

            }

        }
    }

    public Bitmap getRandomAvatarProfile(){
        Bitmap bitmap=null;

        int random=(int)(Math.random()*4);
        switch (random){
            case 0:
                bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.img_avatar2);
                break;
            case 1:
                bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.img_avatar3);
                break;
            case 2:
                bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.img_avatar4);
                break;
            case 3:
                bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.img_avatar6);
                break;
        }
        return bitmap;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}