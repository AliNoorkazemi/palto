package com.example.plato.Fragment.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plato.Fragment.UserFriend;
import com.example.plato.R;

import java.util.LinkedList;

public class AdapterFriendinChat extends RecyclerView.Adapter<AdapterFriendinChat.ViewHolderFriendinChat> {
    Context context;
    LinkedList<UserFriend> userFriends;
    OnItemINChatFragClicked onItemINChatFragClicked;

    public AdapterFriendinChat(Context context, LinkedList<UserFriend> userFriends, OnItemINChatFragClicked onItemINChatFragClicked) {
        this.context = context;
        this.userFriends = userFriends;
        this.onItemINChatFragClicked = onItemINChatFragClicked;
    }


    @NonNull
    @Override
    public ViewHolderFriendinChat onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_sample_friends_in_chat, parent, false);
        return new ViewHolderFriendinChat(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFriendinChat holder, final int position) {
        UserFriend userFriend=userFriends.get(position);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemINChatFragClicked.onClick(position);
            }
        });

        holder.name_tv.setText(userFriend.getName());
        holder.prof_iv.setImageResource(userFriend.getImg_id());

    }


    @Override
    public int getItemCount() {
        return userFriends.size();
    }

    public class ViewHolderFriendinChat extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView name_tv;
        ImageView prof_iv;

        public ViewHolderFriendinChat(@NonNull View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.cv_chatFrag_recycleritem_cardView);
            name_tv=itemView.findViewById(R.id.tv_chatFrag_recycleritem_friendName);
            prof_iv=itemView.findViewById(R.id.iv_chatFrag_recycleritem_profImg);

        }
    }

    public interface OnItemINChatFragClicked {
        void onClick(int position);
    }
}

