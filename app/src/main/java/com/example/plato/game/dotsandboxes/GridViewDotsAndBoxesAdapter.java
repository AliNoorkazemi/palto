package com.example.plato.game.dotsandboxes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.plato.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
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
            convertView.setTag(viewHolder);
            this.viewHolders.add(position,viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.line_vertical_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewHolder.line_vertical_bottom.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int color = context.getResources().getColor(R.color.colorPrimary);
                viewHolder.line_vertical_bottom.setBackgroundColor(color);
            }
        });
        viewHolder.line_horizontal_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = context.getResources().getColor(R.color.colorPrimary);
                viewHolder.line_horizontal_left.setBackgroundColor(color);
            }
        });
        viewHolder.line_horizontal_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = context.getResources().getColor(R.color.colorPrimary);
                viewHolder.line_horizontal_right.setBackgroundColor(color);
            }
        });
        return convertView;
    }

    private static class ViewHolder{
        private ImageView line_vertical_top;
        private ImageView line_vertical_bottom;
        private ImageView line_horizontal_left;
        private ImageView line_horizontal_right;
    }
}
