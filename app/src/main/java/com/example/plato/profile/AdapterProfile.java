package com.example.plato.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plato.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterProfile extends RecyclerView.Adapter<AdapterProfile.ViewHolderProfile> {
    Context context;
    ArrayList<GameInProfileItemSample> arrayList;

    public AdapterProfile(Context context, ArrayList<GameInProfileItemSample> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolderProfile onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.item_sample_gameboard_profile,parent,false);
        return new ViewHolderProfile(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProfile holder, int position) {
        GameInProfileItemSample game=arrayList.get(position);
        holder.gameName_tv.setText(game.getGame_name());
        holder.myScore_tv.setText(game.getScore());
        holder.game_iv.setImageResource(game.getImage());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolderProfile extends RecyclerView.ViewHolder{
        RelativeLayout relativeLayout;
        TextView gameName_tv;
        ImageView game_iv;
        TextView myScore_tv;

        public ViewHolderProfile(@NonNull View itemView) {
            super(itemView);
            relativeLayout=itemView.findViewById(R.id.rv_itemInRecycler_profile_relative);
            game_iv=itemView.findViewById(R.id.iv_itemInRecycler_profile_gameImg);
            myScore_tv=itemView.findViewById(R.id.tv_itemInRecycler_profile_gameScore);
            gameName_tv=itemView.findViewById(R.id.tv_profile_itemInRecycler_gameTitle);
        }
    }
}
