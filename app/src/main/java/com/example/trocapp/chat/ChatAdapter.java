package com.example.trocapp.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trocapp.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private List<ChatList> chatList;
    private final Context context;
    private String userId;

    public ChatAdapter(List<ChatList> chatList, Context context) {
        this.chatList = chatList;
        this.context = context;
        this.userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @NonNull
    @Override


    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_chat_adapter,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            ChatList list2 =chatList.get(position);
            if(list2.getUserId().equals(userId)){
                    holder.myLayout.setVisibility(View.VISIBLE);
                    holder.oppoLayout.setVisibility(View.GONE);
                    holder.myMessage.setText(list2.getMessage());
                    holder.myTime.setText(list2.getDate()+" "+list2.getTime());
            }
            else{
                holder.myLayout.setVisibility(View.GONE);
                holder.oppoLayout.setVisibility(View.VISIBLE);
                holder.oppoMessage.setText(list2.getMessage());
                holder.oppoTime.setText(list2.getDate()+" "+list2.getTime());
            }

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }
    public void updateChatList(List<ChatList> chatList){
        this.chatList=chatList;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout oppoLayout, myLayout;
        private TextView oppoMessage, myMessage;
        private TextView oppoTime,myTime;
        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            oppoLayout=itemView.findViewById(R.id.oppoLayout);
            myLayout=itemView.findViewById(R.id.myLayout);
            oppoMessage=itemView.findViewById(R.id.oppoMessage);
            myMessage=itemView.findViewById(R.id.myMessage);
            oppoTime=itemView.findViewById(R.id.oppoMsgTime);
            myTime=itemView.findViewById(R.id.myMsgTime);
        }
    }
}