package com.example.plato.game.startPage.fragment.leaderboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plato.ConvertBitmapByte;
import com.example.plato.R;

import java.util.ArrayList;

public class AdapterLeaderBoard extends RecyclerView.Adapter<AdapterLeaderBoard.ViewHolderAdapterLeaderBoard> {

    public Context context;
    public ArrayList<BestPlayer> bestPlayers;

    public AdapterLeaderBoard(Context context, ArrayList<BestPlayer> bestPlayers) {
        this.context = context;
        this.bestPlayers = bestPlayers;
    }

    @NonNull
    @Override
    public ViewHolderAdapterLeaderBoard onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.item_sample_in_leaderboard,parent,false);
        return new ViewHolderAdapterLeaderBoard(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderAdapterLeaderBoard holder, int position) {
        BestPlayer bestPlayer=bestPlayers.get(position);
        holder.name_tv.setText(bestPlayer.getName());
        holder.queNo_tv.setText(bestPlayer.getQueNo());
        holder.score_tv.setText(bestPlayer.getScore());
        holder.image_iv.setImageBitmap(ConvertBitmapByte.byteTobitmap(bestPlayer.getImage_bytes()));

        if(position%2==0){
            holder.relativeLayout.setBackgroundColor(context.getColor(R.color.holo_blue_light));
        }else {
            holder.relativeLayout.setBackgroundColor(context.getColor(R.color.white));
        }

    }

    @Override
    public int getItemCount() {
        return bestPlayers.size();
    }

    public class ViewHolderAdapterLeaderBoard extends RecyclerView.ViewHolder{
        RelativeLayout relativeLayout;
        TextView queNo_tv;
        TextView name_tv;
        TextView score_tv;
        ImageView image_iv;


        public ViewHolderAdapterLeaderBoard(@NonNull View itemView) {
            super(itemView);
            relativeLayout=itemView.findViewById(R.id.relative_itemInRecycler_leaderBoardFrag);
            queNo_tv=itemView.findViewById(R.id.tv_itemInRecycler_leaderboardFrag_queNo);
            name_tv=itemView.findViewById(R.id.tv_itemInRecycler_leaderboardFrag_playerName);
            score_tv=itemView.findViewById(R.id.tv_itemInRecycler_leaderboardFrag_score);
            image_iv=itemView.findViewById(R.id.iv_itemInRecycler_leaderboardFrag_image);
        }
    }
}
