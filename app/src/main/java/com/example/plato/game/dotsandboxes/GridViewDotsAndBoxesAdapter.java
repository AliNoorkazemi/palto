package com.example.plato.game.dotsandboxes;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.plato.MainActivity;
import com.example.plato.R;
import com.example.plato.SplashScreenActivity;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class GridViewDotsAndBoxesAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    Context context;
    ArrayList<ViewHolder> viewHolders;

    GridViewDotsAndBoxesAdapter(Context context){
        this.context = context;
        this.viewHolders = new ArrayList<>(36);
        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return 36;
    }

    @Override
    public ViewHolder getItem(int position) {
        return this.viewHolders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if ( convertView == null) {
            convertView = inflater.inflate(R.layout.item_sample_in_dot_box,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.line_horizontal_left = convertView.findViewById(R.id.iv_itemIn_dotBox_lineleft);
            viewHolder.line_horizontal_right = convertView.findViewById(R.id.iv_itemIn_dotBox_lineright);
            viewHolder.line_vertical_top  = convertView.findViewById(R.id.iv_itemIn_dotBox_linetop);
            viewHolder.line_vertical_bottom = convertView.findViewById(R.id.iv_itemIn_dotBox_linebottom);
            if ( position>5 ){
                viewHolder.line_vertical_top.setVisibility(View.INVISIBLE);
                viewHolder.line_vertical_top.setClickable(false);
            }
            if ( position%6!=0){
                viewHolder.line_horizontal_left.setVisibility(View.INVISIBLE);
                viewHolder.line_horizontal_left.setClickable(false);
            }
            convertView.setTag(viewHolder);
            this.viewHolders.add(position,viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.line_vertical_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( DotAndBoxPageActivity.is_my_turn){
                    viewHolder.line_vertical_top.setClickable(false);
                    viewHolder.line_vertical_top.setImageResource(DotAndBoxPageActivity.my_color);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Socket socket = new Socket(SplashScreenActivity.IP,6666);
                                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                                dos.writeUTF("dots and boxes sender");
                                dos.flush();
                                dos.writeUTF(MainActivity.userName);
                                dos.writeInt(DotAndBoxPageActivity.members.size());
                                dos.flush();
                                for(String s : DotAndBoxPageActivity.members) {
                                    dos.writeUTF(s);
                                    dos.flush();
                                }
                                dos.writeInt(position);
                                dos.flush();
                                dos.writeUTF("top");
                                dos.flush();
                                dos.writeInt(DotAndBoxPageActivity.my_color);
                                dos.flush();
                                if ( position==0) {
                                    if (viewHolder.line_horizontal_right.isClickable() ||
                                            viewHolder.line_horizontal_left.isClickable()||
                                            viewHolder.line_vertical_bottom.isClickable() ) {
                                        dos.writeBoolean(false);
                                        DotAndBoxPageActivity.is_my_turn = false;
                                    } else {
                                        ++DotAndBoxPageActivity.my_score;
                                        dos.writeBoolean(true);
                                        Log.e("my Log : ", "prize in top");
                                        dos.flush();
                                    }
                                }else if( position<6){
                                    if (viewHolder.line_horizontal_right.isClickable() ||
                                            viewHolders.get(position-1).line_horizontal_right.isClickable()||
                                            viewHolder.line_vertical_bottom.isClickable() ) {
                                        dos.writeBoolean(false);
                                        DotAndBoxPageActivity.is_my_turn = false;
                                    } else {
                                        ++DotAndBoxPageActivity.my_score;
                                        dos.writeBoolean(true);
                                        Log.e("my Log : ", "prize in top");
                                        dos.flush();
                                    }
                                }
                                dos.writeBoolean(is_game_finished());
                                dos.flush();
                                if ( is_game_finished())
                                    Log.e("my Log : ","game finished");
                            }catch ( IOException io){
                                io.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
        viewHolder.line_vertical_bottom.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if ( DotAndBoxPageActivity.is_my_turn){
                    viewHolder.line_vertical_bottom.setClickable(false);
                    viewHolder.line_vertical_bottom.setImageResource(DotAndBoxPageActivity.my_color);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Socket socket = new Socket(SplashScreenActivity.IP,6666);
                                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                                dos.writeUTF("dots and boxes sender");
                                dos.flush();
                                dos.writeUTF(MainActivity.userName);
                                dos.writeInt(DotAndBoxPageActivity.members.size());
                                dos.flush();
                                for(String s : DotAndBoxPageActivity.members) {
                                    dos.writeUTF(s);
                                    dos.flush();
                                }
                                dos.writeInt(position);
                                dos.flush();
                                dos.writeUTF("bottom");
                                dos.flush();
                                dos.writeInt(DotAndBoxPageActivity.my_color);
                                dos.flush();
                                if ( position==0) {
                                    boolean prize = false;
                                    if (!viewHolder.line_horizontal_right.isClickable()&&
                                            !viewHolder.line_vertical_top.isClickable()&&
                                            !viewHolder.line_horizontal_left.isClickable() ) {
                                        prize = true;
                                        ++DotAndBoxPageActivity.my_score;
                                        Log.e("my Log : ", "prize in bottom");
                                    }
                                    if (!viewHolders.get(position+6).line_horizontal_left.isClickable()&&
                                            !viewHolders.get(position+6).line_vertical_bottom.isClickable()&&
                                            !viewHolders.get(position+6).line_horizontal_right.isClickable()){
                                        prize = true;
                                        ++DotAndBoxPageActivity.my_score;
                                        Log.e("my Log : ", "prize in bottom");
                                    }
                                    if ( prize){
                                        dos.writeBoolean(true);
                                        dos.flush();
                                    }else {
                                        dos.writeBoolean(false);
                                        dos.flush();
                                        DotAndBoxPageActivity.is_my_turn = false;
                                    }
                                }else if ( position<6){
                                    boolean prize = false;
                                    if (!viewHolder.line_horizontal_right.isClickable()&&
                                            !viewHolder.line_vertical_top.isClickable()&&
                                            !viewHolders.get(position-1).line_horizontal_right.isClickable() ) {
                                        prize = true;
                                        ++DotAndBoxPageActivity.my_score;
                                    }
                                    if ( !viewHolders.get(position+6).line_horizontal_right.isClickable()&&
                                            !viewHolders.get(position+6).line_vertical_bottom.isClickable()&&
                                            !viewHolders.get(position+5).line_horizontal_left.isClickable()){
                                        prize = true;
                                        ++DotAndBoxPageActivity.my_score;
                                    }
                                    if ( prize){
                                        dos.writeBoolean(true);
                                        dos.flush();
                                    }else{
                                        dos.writeBoolean(false);
                                        dos.flush();
                                        DotAndBoxPageActivity.is_my_turn = false;
                                    }
                                }else if ( position%6==0){
                                    boolean prize = false;
                                    if (!viewHolder.line_horizontal_right.isClickable()&&
                                            !viewHolders.get(position-6).line_vertical_bottom.isClickable()&&
                                            !viewHolder.line_horizontal_left.isClickable() ) {
                                        prize = true;
                                        ++DotAndBoxPageActivity.my_score;
                                    }
                                    if ( position!=30){
                                        if ( !viewHolders.get(position+6).line_horizontal_left.isClickable()&&
                                            !viewHolders.get(position+6).line_horizontal_right.isClickable()&&
                                            !viewHolders.get(position+6).line_vertical_bottom.isClickable()){
                                            prize = true;
                                            ++DotAndBoxPageActivity.my_score;
                                        }
                                    }
                                    if ( prize){
                                        dos.writeBoolean(true);
                                        dos.flush();
                                    }else{
                                        dos.writeBoolean(false);
                                        dos.flush();
                                        DotAndBoxPageActivity.is_my_turn = false;
                                    }
                                }else{
                                    boolean prize = false;
                                    if (!viewHolder.line_horizontal_right.isClickable()&&
                                            !viewHolders.get(position-6).line_vertical_bottom.isClickable()&&
                                            !viewHolders.get(position-1).line_horizontal_right.isClickable() ) {
                                        prize = true;
                                        ++DotAndBoxPageActivity.my_score;
                                    }
                                    if ( position<30) {
                                        if (!viewHolders.get(position + 6).line_vertical_bottom.isClickable()&&
                                            !viewHolders.get(position+6).line_horizontal_right.isClickable()&&
                                            !viewHolders.get(position+5).line_horizontal_right.isClickable()){
                                            prize = true;
                                            ++DotAndBoxPageActivity.my_score;
                                        }
                                    }
                                    if ( prize){
                                        dos.writeBoolean(true);
                                        dos.flush();
                                    }else{
                                        dos.writeBoolean(false);
                                        dos.flush();
                                        DotAndBoxPageActivity.is_my_turn = false;
                                    }
                                }
                                dos.writeBoolean(is_game_finished());
                                dos.flush();
                                if ( is_game_finished())
                                    Log.e("my Log : ","game finished");
                            }catch ( IOException io){
                                io.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
        viewHolder.line_horizontal_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( DotAndBoxPageActivity.is_my_turn){
                    viewHolder.line_horizontal_left.setClickable(false);
                    viewHolder.line_horizontal_left.setImageResource(DotAndBoxPageActivity.my_color);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Socket socket = new Socket(SplashScreenActivity.IP,6666);
                                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                                dos.writeUTF("dots and boxes sender");
                                dos.flush();
                                dos.writeUTF(MainActivity.userName);
                                dos.writeInt(DotAndBoxPageActivity.members.size());
                                dos.flush();
                                for(String s : DotAndBoxPageActivity.members) {
                                    dos.writeUTF(s);
                                    dos.flush();
                                }
                                dos.writeInt(position);
                                dos.flush();
                                dos.writeUTF("left");
                                dos.flush();
                                dos.writeInt(DotAndBoxPageActivity.my_color);
                                dos.flush();
                                if ( position==0) {
                                    if (viewHolder.line_horizontal_right.isClickable()||
                                            viewHolder.line_vertical_top.isClickable()||
                                            viewHolder.line_vertical_bottom.isClickable()) {
                                        dos.writeBoolean(false);
                                        DotAndBoxPageActivity.is_my_turn = false;
                                    } else {
                                        ++DotAndBoxPageActivity.my_score;
                                        dos.writeBoolean(true);
                                        Log.e("my Log : ", "prize in left");
                                        dos.flush();
                                    }
                                }else if ( position%6==0){
                                    if (viewHolder.line_horizontal_right.isClickable()||
                                            viewHolders.get(position-6).line_vertical_bottom.isClickable()||
                                            viewHolder.line_vertical_bottom.isClickable()) {
                                        dos.writeBoolean(false);
                                        DotAndBoxPageActivity.is_my_turn = false;
                                    } else {
                                        ++DotAndBoxPageActivity.my_score;
                                        dos.writeBoolean(true);
                                        Log.e("my Log : ", "prize in left");
                                        dos.flush();
                                    }
                                }
                                dos.writeBoolean(is_game_finished());
                                dos.flush();
                                if ( is_game_finished())
                                    Log.e("my Log : ","game finished");
                            }catch ( IOException io){
                                io.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
        viewHolder.line_horizontal_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( DotAndBoxPageActivity.is_my_turn){
                    viewHolder.line_horizontal_right.setClickable(false);
                    viewHolder.line_horizontal_right.setImageResource(DotAndBoxPageActivity.my_color);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                                Socket socket = new Socket(SplashScreenActivity.IP,6666);
                                DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
                                dos.writeUTF("dots and boxes sender");
                                dos.flush();
                                dos.writeUTF(MainActivity.userName);
                                dos.writeInt(DotAndBoxPageActivity.members.size());
                                dos.flush();
                                for(String s : DotAndBoxPageActivity.members) {
                                    dos.writeUTF(s);
                                    dos.flush();
                                }
                                dos.writeInt(position);
                                dos.flush();
                                dos.writeUTF("right");
                                dos.flush();
                                dos.writeInt(DotAndBoxPageActivity.my_color);
                                dos.flush();
                                if ( position==0) {
                                    boolean prize = false;
                                    if (!viewHolder.line_horizontal_left.isClickable()&&
                                            !viewHolder.line_vertical_top.isClickable()&&
                                            !viewHolder.line_vertical_bottom.isClickable()) {
                                          prize = true;
                                          ++DotAndBoxPageActivity.my_score;
                                    }
                                    if ( !viewHolders.get(position+1).line_vertical_top.isClickable()&&
                                            !viewHolders.get(position+1).line_horizontal_right.isClickable()&&
                                            !viewHolders.get(position+1).line_vertical_bottom.isClickable()){
                                        prize = true;
                                        ++DotAndBoxPageActivity.my_score;
                                    }
                                    if ( prize){
                                        dos.writeBoolean(true);
                                        dos.flush();
                                    }else{
                                        dos.writeBoolean(false);
                                        dos.flush();
                                        DotAndBoxPageActivity.is_my_turn = false;
                                    }
                                }else if ( position<6){
                                    boolean prize = false;
                                    if (!viewHolders.get(position-1).line_horizontal_right.isClickable()&&
                                            !viewHolder.line_vertical_top.isClickable()&&
                                            !viewHolder.line_vertical_bottom.isClickable()) {
                                        prize = true;
                                        ++DotAndBoxPageActivity.my_score;
                                    }
                                    if (position!=5){
                                        if ( !viewHolders.get(position+1).line_horizontal_right.isClickable()&&
                                            !viewHolders.get(position+1).line_vertical_bottom.isClickable()&&
                                            !viewHolders.get(position+1).line_vertical_top.isClickable()){
                                            prize = true;
                                            ++DotAndBoxPageActivity.my_score;
                                        }
                                    }
                                    if ( prize){
                                        dos.writeBoolean(true);
                                        dos.flush();
                                    }else{
                                        dos.writeBoolean(false);
                                        dos.flush();
                                        DotAndBoxPageActivity.is_my_turn = false;
                                    }
                                }else if( position%6==0){
                                    boolean prize = false;
                                    if (!viewHolder.line_horizontal_left.isClickable()&&
                                            !viewHolders.get(position-6).line_vertical_bottom.isClickable()&&
                                            !viewHolder.line_vertical_bottom.isClickable()) {
                                        prize = true;
                                        ++DotAndBoxPageActivity.my_score;
                                    }
                                    if (!viewHolders.get(position + 1).line_vertical_bottom.isClickable() &&
                                            !viewHolders.get(position + 1).line_horizontal_right.isClickable() &&
                                            !viewHolders.get(position - 5).line_vertical_bottom.isClickable()) {
                                        prize = true;
                                        ++DotAndBoxPageActivity.my_score;
                                    }
                                    if ( prize){
                                        dos.writeBoolean(true);
                                        dos.flush();
                                    }else{
                                        dos.writeBoolean(false);
                                        dos.flush();
                                        DotAndBoxPageActivity.is_my_turn = false;
                                    }
                                }else{
                                    boolean prize = false;
                                    if (!viewHolders.get(position-1).line_horizontal_right.isClickable()&&
                                            !viewHolders.get(position-1).line_vertical_bottom.isClickable()&&
                                            !viewHolder.line_vertical_bottom.isClickable()) {
                                        prize = true;
                                        ++DotAndBoxPageActivity.my_score;
                                    }
                                    if ( position%6!=5){
                                        if ( !viewHolders.get(position+1).line_horizontal_right.isClickable()&&
                                                !viewHolders.get(position+1).line_vertical_bottom.isClickable()&&
                                                !viewHolders.get(position-5).line_vertical_bottom.isClickable()){
                                            prize = true;
                                            ++DotAndBoxPageActivity.my_score;
                                        }
                                    }
                                    if ( prize){
                                        dos.writeBoolean(true);
                                        dos.flush();
                                    }else{
                                        dos.writeBoolean(false);
                                        dos.flush();
                                        DotAndBoxPageActivity.is_my_turn = false;
                                    }
                                }
                                dos.writeBoolean(is_game_finished());
                                dos.flush();
                                if ( is_game_finished())
                                    Log.e("my Log : ","game finished");
                            }catch ( IOException io){
                                io.printStackTrace();
                            }
                        }
                    }).start();
                }
            }
        });
        return convertView;
    }

    static class ViewHolder{
        ImageView line_vertical_top;
        ImageView line_vertical_bottom;
        ImageView line_horizontal_left;
        ImageView line_horizontal_right;
    }

    private boolean is_game_finished(){
        ViewHolder viewHolder;
        for (int i = 0; i < 36; i++) {
            viewHolder = this.getItem(i);
            if( viewHolder.line_horizontal_left.getVisibility()==View.VISIBLE&&
            viewHolder.line_horizontal_left.isClickable())
                return false;
            if( viewHolder.line_horizontal_right.getVisibility()==View.VISIBLE&&
            viewHolder.line_horizontal_right.isClickable())
                return false;
            if( viewHolder.line_vertical_top.getVisibility()==View.VISIBLE&&
            viewHolder.line_vertical_top.isClickable())
                return false;
            if( viewHolder.line_vertical_bottom.getVisibility()==View.VISIBLE&&
            viewHolder.line_vertical_bottom.isClickable())
                return false;
        }
        return true;
    }
}
