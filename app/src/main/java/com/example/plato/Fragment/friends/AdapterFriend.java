package com.example.plato.Fragment.friends;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plato.ConvertBitmapByte;
import com.example.plato.Fragment.Chat.AdapterFriendinChat;
import com.example.plato.Fragment.Friend;
import com.example.plato.R;

import java.util.ArrayList;
import java.util.LinkedList;

public class AdapterFriend extends RecyclerView.Adapter<AdapterFriend.ViewHolderFriend> {
    Context context;
    LinkedList<Friend> friends;
    OnFriendItemClickListener onFriendItemClickListener;

    public AdapterFriend(Context context, LinkedList<Friend> friends, OnFriendItemClickListener onFriendItemClickListener) {
        this.context = context;
        this.friends = friends;
        this.onFriendItemClickListener = onFriendItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolderFriend onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.item_sample_friends,parent,false);
        return new ViewHolderFriend(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderFriend holder, final int position) {
        final Friend friend=friends.get(position);
        holder.friendName_tv.setText(friend.getName());
        Bitmap bitmap= ConvertBitmapByte.byteTobitmap(Base64.decode(friend.getImg_str(),Base64.DEFAULT));
        holder.friendProfile_tv.setImageBitmap(bitmap);

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFriendItemClickListener.onClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return friends.size();
    }

    public class ViewHolderFriend extends RecyclerView.ViewHolder{
        RelativeLayout relativeLayout;
        TextView friendName_tv;
        ImageView friendProfile_tv;
        public ViewHolderFriend(@NonNull View itemView) {
            super(itemView);
            relativeLayout=itemView.findViewById(R.id.rv_friendFrag_itemSample_relative);
            friendName_tv=itemView.findViewById(R.id.tv_friendFrag_itemSample_friendName);
            friendProfile_tv=itemView.findViewById(R.id.iv_friendFrag_itemSample_friendProf);
        }
    }
    public interface OnFriendItemClickListener{
        void onClick(int position);
    }
}
