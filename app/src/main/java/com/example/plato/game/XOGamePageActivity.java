package com.example.plato.game;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;
import android.widget.Toast;

import com.example.plato.MainActivity;
import com.example.plato.R;
import com.example.plato.network.XoGameListener;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class XOGamePageActivity extends AppCompatActivity {

    String roomName;

    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;

    TextView player1Name_tv;
    TextView player2Name_tv;
    ImageView player1Turn_iv;
    ImageView player2Turn_iv;

    FrameLayout wonOrLoseBox_framelayout;
    TextView wonOrLose_tv;
    TextView player1NameInBox_tv;
    TextView player2NameInBox_tv;
    Button close_btn;


    String[] xo_table;

    boolean are_you_O;

    boolean is_it_your_turn = false; //nobat

    String opponent_name;

    Drawable button_background;
    String xORo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_x_o_game);


        firstInitialize();
        init_xoTable();

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(are_you_O){
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Socket socket=new Socket("192.168.2.102",6666);
                                DataOutputStream dos=new DataOutputStream(socket.getOutputStream());
                                dos.writeUTF("game");
                                dos.flush();
                                dos.writeUTF("removeRoom");
                                dos.flush();
                                dos.writeUTF("xo");
                                dos.flush();
                                dos.writeUTF(roomName);
                                dos.flush();

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }).start();
                }
                finish();
            }
        });

        XoGameListener xoGameListener = new XoGameListener(new XoGameListener.ListenerForTurnInXoGame() {
            @Override
            public void onListen(Boolean are_you, int index) {
                if (are_you) {
                    is_it_your_turn = true;
                } else
                    is_it_your_turn = false;


                changeIndexButtonState(index);
            }
        });
        xoGameListener.start();


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_it_your_turn) {
                    btn1.setBackground(button_background);
                    xo_table[0] = xORo;
                    check();

                    btn1.setClickable(false);
                    sendDataToServer(0);
                    is_it_your_turn = false;
                }
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_it_your_turn) {
                    btn2.setBackground(button_background);
                    xo_table[1] = xORo;
                    check();

                    btn2.setClickable(false);
                    sendDataToServer(1);
                    is_it_your_turn = false;
                }
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_it_your_turn) {
                    btn3.setBackground(button_background);
                    xo_table[2] = xORo;
                    check();

                    btn3.setClickable(false);
                    sendDataToServer(2);
                    is_it_your_turn = false;
                }
            }
        });

        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_it_your_turn) {
                    btn4.setBackground(button_background);
                    xo_table[3] = xORo;
                    check();


                    btn4.setClickable(false);
                    sendDataToServer(3);
                    is_it_your_turn = false;
                }
            }
        });

        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_it_your_turn) {
                    btn5.setBackground(button_background);
                    xo_table[4] = xORo;
                    check();

                    btn5.setClickable(false);
                    sendDataToServer(4);
                    is_it_your_turn = false;
                }
            }
        });

        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_it_your_turn) {
                    btn6.setBackground(button_background);
                    xo_table[5] = xORo;
                    check();

                    btn6.setClickable(false);
                    sendDataToServer(5);
                    is_it_your_turn = false;
                }
            }
        });

        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_it_your_turn) {
                    btn7.setBackground(button_background);
                    xo_table[6] = xORo;
                    check();

                    btn7.setClickable(false);
                    sendDataToServer(6);
                    is_it_your_turn = false;
                }
            }
        });

        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_it_your_turn) {
                    btn8.setBackground(button_background);
                    xo_table[7] = xORo;
                    check();

                    btn8.setClickable(false);
                    sendDataToServer(7);
                    is_it_your_turn = false;
                }
            }
        });

        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_it_your_turn) {
                    btn9.setBackground(button_background);
                    xo_table[8] = xORo;
                    check();

                    btn9.setClickable(false);
                    sendDataToServer(8);
                    is_it_your_turn = false;
                }
            }
        });


    }

    private void firstInitialize() {
        player1Name_tv = findViewById(R.id.tv_xoGamePageActivity_player1Name);
        player2Name_tv = findViewById(R.id.tv_xoGamePageActivity_player2Name);
        player1Turn_iv = findViewById(R.id.iv_xoGamePageActivity_player1turnImg);
        player2Turn_iv = findViewById(R.id.iv_xoGamePageActivity_player2turnImg);


        wonOrLoseBox_framelayout = findViewById(R.id.frameLayout_xoGamePageActivity_wonOrLoseBox);
        wonOrLose_tv = findViewById(R.id.tv_xoGamePageActivity_wonOrloseTxt);
        player1NameInBox_tv = findViewById(R.id.tv_xoGamePageActivity_player1Name_inWonBox);
        player2NameInBox_tv = findViewById(R.id.tv_xoGamePageActivity_player2Name_inWonBox);
        close_btn = findViewById(R.id.btn_xoGamePageActivity_closeBtn);


        btn1 = findViewById(R.id.btn_xogame_btn1);
        btn2 = findViewById(R.id.btn_xogame_btn2);
        btn3 = findViewById(R.id.btn_xogame_btn3);
        btn4 = findViewById(R.id.btn_xogame_btn4);
        btn5 = findViewById(R.id.btn_xogame_btn5);
        btn6 = findViewById(R.id.btn_xogame_btn6);
        btn7 = findViewById(R.id.btn_xogame_btn7);
        btn8 = findViewById(R.id.btn_xogame_btn8);
        btn9 = findViewById(R.id.btn_xogame_btn9);


        Intent intent = getIntent();
        are_you_O = intent.getBooleanExtra("areYouO", true);
        opponent_name = intent.getStringExtra("opponent");
        if (are_you_O) {
            is_it_your_turn = true;
            player1Name_tv.setText(MainActivity.userName + ":");
            player2Name_tv.setText(opponent_name + ":");
            player1NameInBox_tv.setText(MainActivity.userName);
            player2NameInBox_tv.setText(opponent_name);
            button_background = getDrawable(R.drawable.o_background);
            xORo = "o";
            roomName=intent.getStringExtra("RoomName");
        } else {
            player1Name_tv.setText(opponent_name + ":");
            player2Name_tv.setText(MainActivity.userName + ":");
            player1NameInBox_tv.setText(opponent_name);
            player2NameInBox_tv.setText(MainActivity.userName);
            button_background = getDrawable(R.drawable.x_background);
            xORo = "x";
        }
    }

    private void changeIndexButtonState(int index) {
        Drawable opponent_btn_background;
        String x_o_str;
        if (are_you_O) {
            opponent_btn_background = getDrawable(R.drawable.x_background);
            x_o_str = "x";
        } else {
            opponent_btn_background = getDrawable(R.drawable.o_background);
            x_o_str = "o";
        }

        XOGamePageActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                updateGameData();
                switch (index) {
                    case 0:
                        btn1.setBackground(opponent_btn_background);
                        xo_table[0] = x_o_str;
                        btn1.setClickable(false);
                        break;
                    case 1:
                        btn2.setBackground(opponent_btn_background);
                        xo_table[1] = x_o_str;
                        btn2.setClickable(false);
                        break;
                    case 2:
                        btn3.setBackground(opponent_btn_background);
                        xo_table[2] = x_o_str;
                        btn3.setClickable(false);
                        break;
                    case 3:
                        btn4.setBackground(opponent_btn_background);
                        xo_table[3] = x_o_str;
                        btn4.setClickable(false);
                        break;
                    case 4:
                        btn5.setBackground(opponent_btn_background);
                        xo_table[4] = x_o_str;
                        btn5.setClickable(false);
                        break;
                    case 5:
                        btn6.setBackground(opponent_btn_background);
                        xo_table[5] = x_o_str;
                        btn6.setClickable(false);
                        break;
                    case 6:
                        btn7.setBackground(opponent_btn_background);
                        xo_table[6] = x_o_str;
                        btn7.setClickable(false);
                        break;
                    case 7:
                        btn8.setBackground(opponent_btn_background);
                        xo_table[7] = x_o_str;
                        btn8.setClickable(false);
                        break;
                    case 8:
                        btn9.setBackground(opponent_btn_background);
                        xo_table[8] = x_o_str;
                        btn9.setClickable(false);
                        break;
                }
                check();
            }
        });

    }

    private void sendDataToServer(int index) {
        updateGameData();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket("192.168.2.102", 6666);
                    DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

                    dos.writeUTF("XoSendData");
                    dos.flush();
                    dos.writeUTF(opponent_name);
                    dos.flush();
                    dos.writeInt(index);
                    dos.flush();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void updateGameData() {
        if (player1Turn_iv.getVisibility() == View.VISIBLE) {
            player1Turn_iv.setVisibility(View.INVISIBLE);
            player2Turn_iv.setVisibility(View.VISIBLE);
        } else {
            player1Turn_iv.setVisibility(View.VISIBLE);
            player2Turn_iv.setVisibility(View.INVISIBLE);
        }
    }


    private void init_xoTable() {
        xo_table = new String[9];
        Arrays.fill(xo_table, "*");
    }

    private void check() {
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
                wonOrLoseBox_framelayout.setVisibility(View.VISIBLE);
                if (are_you_O) {
                    wonOrLose_tv.setText("you lose!");
                } else {
                    wonOrLose_tv.setText("you won!");
                }
                break;
            } else if (line.equals("ooo")) {
                wonOrLoseBox_framelayout.setVisibility(View.VISIBLE);
                if (!are_you_O) {
                    wonOrLose_tv.setText("you lose!");
                } else {
                    wonOrLose_tv.setText("you won!");
                }
                break;
            }
        }
    }

}
