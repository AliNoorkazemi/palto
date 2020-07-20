package com.example.plato.Fragment.Chat;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plato.ConvertBitmapByte;
import com.example.plato.Fragment.Friend;
import com.example.plato.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class AdapterFriendinChat extends RecyclerView.Adapter<AdapterFriendinChat.ViewHolderFriendinChat> {
    Context context;
    LinkedList<Friend> friends;
    OnItemINChatFragClicked onItemINChatFragClicked;


    public AdapterFriendinChat(Context context, LinkedList<Friend> friends, OnItemINChatFragClicked onItemINChatFragClicked) {
        this.context = context;
        this.friends = friends;
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
        Friend friend = friends.get(position);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemINChatFragClicked.onClick(position);
            }
        });

        holder.name_tv.setText(friend.getName());
        Bitmap bitmap= ConvertBitmapByte.byteTobitmap(Base64.decode(friend.getImg_str(),Base64.DEFAULT));
        holder.prof_iv.setImageBitmap(bitmap);
        if(friend.getChats_message().size()!=0)
            holder.last_message.setText(friend.getChats_message().get(friend.getChats_message().size()-1));

        //compare date to set date in chatFrag
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("EEE,HH:mm a");
        if(friend.getChats_message().size()!=0)
            holder.last_message_time_tv.setText(simpleDateFormat.format(friend.getDates().get(friend.getChats_message().size()-1)));
    }


    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class ViewHolderFriendinChat extends RecyclerView.ViewHolder {

        CardView cardView;
        TextView name_tv;
        TextView last_message;
        TextView last_message_time_tv;
        ImageView prof_iv;

        public ViewHolderFriendinChat(@NonNull View itemView) {
            super(itemView);

            cardView=itemView.findViewById(R.id.cv_chatFrag_recycleritem_cardView);
            name_tv=itemView.findViewById(R.id.tv_chatFrag_recycleritem_friendName);
            prof_iv=itemView.findViewById(R.id.iv_chatFrag_recycleritem_profImg);
            last_message=itemView.findViewById(R.id.tv_chatFrag_recycleritem_lastMessage);
            last_message_time_tv=itemView.findViewById(R.id.tv_chatFrag_recycleritem_lastMessageTime);
        }
    }
    public interface OnItemINChatFragClicked {
        void onClick(int position);
    }
}

