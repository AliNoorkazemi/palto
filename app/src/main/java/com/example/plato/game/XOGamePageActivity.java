package com.example.plato.game;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.plato.R;

import java.util.Arrays;

public class XOGamePageActivity extends AppCompatActivity {

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;

    String[] xo_table;

    boolean are_you_O = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_o_game);


        init_xoTable();


        btn1 = findViewById(R.id.btn_xogame_btn1);
        btn2 = findViewById(R.id.btn_xogame_btn2);
        btn3 = findViewById(R.id.btn_xogame_btn3);
        btn4 = findViewById(R.id.btn_xogame_btn4);
        btn5 = findViewById(R.id.btn_xogame_btn5);
        btn6 = findViewById(R.id.btn_xogame_btn6);
        btn7 = findViewById(R.id.btn_xogame_btn7);
        btn8 = findViewById(R.id.btn_xogame_btn8);
        btn9 = findViewById(R.id.btn_xogame_btn9);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (are_you_O) {
                    btn1.setBackground(getDrawable(R.drawable.o_background));
                    xo_table[0] = "o";
                    if (check().equals("o"))
                        Toast.makeText(XOGamePageActivity.this, "you win", Toast.LENGTH_LONG).show();
                } else {
                    btn1.setBackground(getDrawable(R.drawable.x_background));

                    xo_table[0] = "x";
                }
                btn1.setClickable(false);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (are_you_O) {
                    btn2.setBackground(getDrawable(R.drawable.o_background));
                    xo_table[1] = "o";
                    if (check().equals("o"))
                        Toast.makeText(XOGamePageActivity.this, "you win", Toast.LENGTH_LONG).show();
                } else {
                    btn2.setBackground(getDrawable(R.drawable.x_background));
                    xo_table[1] = "x";
                }
                btn2.setClickable(false);
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (are_you_O) {
                    btn3.setBackground(getDrawable(R.drawable.o_background));
                    xo_table[2] = "o";
                    if(check().equals("o"))
                        Toast.makeText(XOGamePageActivity.this,"you win",Toast.LENGTH_LONG).show();
                } else {
                    btn3.setBackground(getDrawable(R.drawable.x_background));
                    xo_table[2] = "x";
                }
                btn3.setClickable(false);
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (are_you_O) {
                    btn4.setBackground(getDrawable(R.drawable.o_background));
                    xo_table[3] = "o";
                    if(check().equals("o"))
                        Toast.makeText(XOGamePageActivity.this,"you win",Toast.LENGTH_LONG).show();
                } else {
                    btn4.setBackground(getDrawable(R.drawable.x_background));
                    xo_table[3] = "x";
                }
                btn4.setClickable(false);
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (are_you_O) {
                    btn5.setBackground(getDrawable(R.drawable.o_background));
                    xo_table[4] = "o";
                    if(check().equals("o"))
                        Toast.makeText(XOGamePageActivity.this,"you win",Toast.LENGTH_LONG).show();
                } else {
                    btn5.setBackground(getDrawable(R.drawable.x_background));
                    xo_table[4] = "x";
                }
                btn5.setClickable(false);
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (are_you_O) {
                    btn6.setBackground(getDrawable(R.drawable.o_background));
                    xo_table[5] = "o";
                    if(check().equals("o"))
                        Toast.makeText(XOGamePageActivity.this,"you win",Toast.LENGTH_LONG).show();
                } else {
                    btn6.setBackground(getDrawable(R.drawable.x_background));
                    xo_table[5] = "x";
                }
                btn6.setClickable(false);
            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (are_you_O) {
                    btn7.setBackground(getDrawable(R.drawable.o_background));
                    xo_table[6] = "o";
                    if(check().equals("o"))
                        Toast.makeText(XOGamePageActivity.this,"you win",Toast.LENGTH_LONG).show();
                } else {
                    btn7.setBackground(getDrawable(R.drawable.x_background));
                    xo_table[6] = "x";
                }
                btn7.setClickable(false);
            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (are_you_O) {
                    btn8.setBackground(getDrawable(R.drawable.o_background));
                    xo_table[7] = "o";
                    if(check().equals("o"))
                        Toast.makeText(XOGamePageActivity.this,"you win",Toast.LENGTH_LONG).show();
                } else {
                    btn8.setBackground(getDrawable(R.drawable.x_background));
                    xo_table[7] = "x";
                }
                btn8.setClickable(false);
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (are_you_O) {
                    btn9.setBackground(getDrawable(R.drawable.o_background));
                    xo_table[8] = "o";
                    if(check().equals("o"))
                        Toast.makeText(XOGamePageActivity.this,"you win",Toast.LENGTH_LONG).show();
                } else {
                    btn9.setBackground(getDrawable(R.drawable.x_background));
                    xo_table[8] = "x";
                    if(check().equals("x"))
                        Toast.makeText(XOGamePageActivity.this,"you win",Toast.LENGTH_LONG).show();
                }
                btn9.setClickable(false);
            }
        });


    }

    private void init_xoTable() {
        xo_table = new String[9];
        Arrays.fill(xo_table, "*");
    }

    private String check() {
        for (int i = 0; i < 8; i++) {
            String line = null;
            switch (i) {
                case 0:
                    line = xo_table[0] + xo_table[1] + xo_table[2];
                    break;
                case 1:
                    line = xo_table[3] + xo_table[4] + xo_table[5];
                    break;
                case 2:
                    line = xo_table[6] + xo_table[7] + xo_table[8];
                    break;
                case 3:
                    line = xo_table[0] + xo_table[3] + xo_table[6];
                    break;
                case 4:
                    line = xo_table[1] + xo_table[4] + xo_table[7];
                    break;
                case 5:
                    line = xo_table[2] + xo_table[5] + xo_table[8];
                    break;

                case 6:
                    line = xo_table[0] + xo_table[4] + xo_table[8];
                    break;
                case 7:
                    line = xo_table[2] + xo_table[4] + xo_table[6];
                    break;
            }
            if (line.equals("xxx")) {
                return "x";
            } else if (line.equals("ooo"))
                return "o";
        }
        return "resume";
    }

}
