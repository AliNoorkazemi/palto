package com.example.plato.Fragment.Chat.chatPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plato.Fragment.Friend;
import com.example.plato.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdapterChatPage extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    ArrayList<String> messages;
    ArrayList<Boolean> is_income_message;
    ArrayList<Date> dates;
    Friend friend;

    public AdapterChatPage(Context context, Friend friend) {
        this.context = context;
        this.friend=friend;
        messages=friend.getChats_message();
        is_income_message = friend.getIs_it_incomeMessage();
        dates=friend.getDates();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        switch (viewType){
            case 0:
                return new ViewholderIncomeMessage(layoutInflater.inflate(R.layout.item_sample_incomemessage_chatpageactivity,parent,false));
            case 1:return new ViewholderMyMessage(layoutInflater.inflate(R.layout.item_sample_mymessage_chatpageactivity,parent,false));
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if(is_income_message.get(position)){
            return 0; //0 is income message view type
        }
        return 1; //1 is income message view type
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewholderIncomeMessage){
            ((ViewholderIncomeMessage) holder).incomeMessage_tv.setText(messages.get(position));
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("h:mm a");
            ((ViewholderIncomeMessage) holder).time_tv.setText(simpleDateFormat.format(dates.get(position)));
        }else if(holder instanceof  ViewholderMyMessage){
            ((ViewholderMyMessage) holder).myMessage_tv.setText(messages.get(position));
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("h:mm a");
            ((ViewholderMyMessage) holder).time_tv.setText(simpleDateFormat.format(dates.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewholderMyMessage extends RecyclerView.ViewHolder{
        TextView myMessage_tv;
        TextView myMessageTime_tv;
        TextView time_tv;

        public ViewholderMyMessage(@NonNull View itemView) {
            super(itemView);
            myMessage_tv=itemView.findViewById(R.id.tv_itemInRecycler_myMessage_message);
            myMessageTime_tv=itemView.findViewById(R.id.tv_itemInRecycler_mycomeMessage_time);
            time_tv=itemView.findViewById(R.id.tv_itemInRecycler_mycomeMessage_time);
        }
    }


    public class ViewholderIncomeMessage extends RecyclerView.ViewHolder{
        TextView incomeMessage_tv;
        TextView incomeMessageTime_tv ;
        TextView time_tv ;

        public ViewholderIncomeMessage(@NonNull View itemView) {
            super(itemView);
            incomeMessage_tv=itemView.findViewById(R.id.tv_itemInRecycler_incomeMessage_message);
            incomeMessageTime_tv=itemView.findViewById(R.id.tv_itemInRecycler_incomeMessage_time);
            time_tv=itemView.findViewById(R.id.tv_itemInRecycler_incomeMessage_time);
        }
    }
}
