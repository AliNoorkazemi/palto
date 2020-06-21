package com.example.plato.profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plato.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterProfile extends RecyclerView.Adapter<AdapterProfile.ViewHolderProfile> {
    Context context;


    @NonNull
    @Override
    public ViewHolderProfile onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.item_sample_gameboard_profile,parent,false);
        return new ViewHolderProfile(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderProfile holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolderProfile extends RecyclerView.ViewHolder{

        public ViewHolderProfile(@NonNull View itemView) {
            super(itemView);
        }
    }
}
