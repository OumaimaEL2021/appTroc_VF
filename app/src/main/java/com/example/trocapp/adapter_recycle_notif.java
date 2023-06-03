package com.example.trocapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter_recycle_notif extends RecyclerView.Adapter<adapter_recycle_notif.MyViewHolder>{
    ArrayList<annonces_pending> list1;
    Context context;
    public adapter_recycle_notif(Context context, ArrayList<annonces_pending> list1) {
        this.context=context;
        this.list1 = list1;
    }

    @NonNull
    @Override
    public adapter_recycle_notif.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_notif,parent,false);
        return new adapter_recycle_notif.MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull adapter_recycle_notif.MyViewHolder holder, int position) {
        annonces_pending an=list1.get(position);
        String idann=an.Idannonce();
        String IDuser=an.IDuser();
        Picasso.get().load(an.image1()).error(R.drawable.images).into(holder.image1);
        Picasso.get().load(an.image2()).error(R.drawable.images).into(holder.image2);

    }
    @Override
    public int getItemCount() {
        return list1.size();


    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //add categorie description and others

        ImageView image1,image2;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image1=itemView.findViewById(R.id.image111);
            image2=itemView.findViewById(R.id.image222);



        }
    }
}