package com.example.plato.game.startPage.fragment.casual;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plato.Fragment.Chat.AdapterFriendinChat;
import com.example.plato.R;
import com.example.plato.game.Room;

import java.util.ArrayList;

public class AdapterCasual extends RecyclerView.Adapter<AdapterCasual.ViewHolderCasual> {
    ArrayList<Room> rooms;
    OnItemInCasualClickListener onItemInCasualClickListener;

    public AdapterCasual(ArrayList<Room> rooms, OnItemInCasualClickListener onItemInCasualClickListener) {
        this.rooms = rooms;
        this.onItemInCasualClickListener = onItemInCasualClickListener;
    }

    @NonNull
    @Override
    public ViewHolderCasual onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_sample_room_in_casual, parent, false);
        return new ViewHolderCasual(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderCasual holder, int position) {

        final Room room=rooms.get(position);
        holder.roomName_tv.setText(room.getRoom_name());
        holder.countPlayers_tv.setText(room.getUsers().size()+"/"+room.getMax_players()+"  players");
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemInCasualClickListener.onClick(room);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rooms.size();
    }

    public class ViewHolderCasual extends RecyclerView.ViewHolder {
        TextView roomName_tv;
        TextView countPlayers_tv;
        RelativeLayout relativeLayout;

        public ViewHolderCasual(@NonNull View itemView) {
            super(itemView);

            relativeLayout=itemView.findViewById(R.id.relative_itemINRecycler_casualFrag);
            countPlayers_tv=itemView.findViewById(R.id.tv_itemInRecycler_casualFrag_countplayer);
            roomName_tv=itemView.findViewById(R.id.tv_itemInRecycler_casualFrag_roomName);
        }
    }

    public interface OnItemInCasualClickListener {
        void onClick(Room room);
    }
}
