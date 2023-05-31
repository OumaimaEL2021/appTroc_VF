package com.example.trocapp.messages;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trocapp.Chatt;
import com.example.trocapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    private List<MessageList> messageLists;
    private final Context context;
    public MessageAdapter(List<MessageList> messageLists, Context context) {
        this.messageLists = messageLists;
        this.context = context;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.message_adapter_layout,parent,false);
        return new MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MessageList list2 =messageLists.get(position);
        holder.name.setText(list2.getName());
        holder.lastmessage.setText(list2.getLastMessages());
        Picasso.get().load(list2.getProfilePic()).into(holder.profilePic);
        if(list2.getUnseenMessages()==0){
            holder.unseenmessages.setVisibility(View.GONE);
            holder.lastmessage.setTextColor(Color.parseColor("#959595"));
        }
        else{
            holder.unseenmessages.setVisibility(View.VISIBLE);
            holder.unseenmessages.setText(list2.getUnseenMessages()+"");
            holder.lastmessage.setTextColor(Color.parseColor("#41c1ba"));
            holder.unseenmessages.setTextColor(Color.parseColor("#41c1ba"));
        }
        holder.rootLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Chatt.class);
                intent.putExtra("name",list2.getName());
                intent.putExtra("userId",list2.getUserId());
                intent.putExtra("chat_key",list2.getChatKey());
                intent.putExtra("photo",list2.getProfilePic());
                context.startActivity(intent);
            }
        }

        );
    }

    public void updateData(List<MessageList> messageLists ){
        this.messageLists=messageLists;
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return messageLists.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView profilePic;
        private TextView name;
        private TextView lastmessage;
        private TextView unseenmessages;
        private LinearLayout rootLayout;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            profilePic=itemView.findViewById(R.id.profilePic);
            name = itemView.findViewById(R.id.name);
            lastmessage=itemView.findViewById(R.id.lastmessage);
            unseenmessages=itemView.findViewById(R.id.unseenmessages);
            rootLayout=itemView.findViewById(R.id.rootLayout);

        }
    }
}
