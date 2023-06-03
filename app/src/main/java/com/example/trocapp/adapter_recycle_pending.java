package com.example.trocapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class adapter_recycle_pending extends RecyclerView.Adapter<adapter_recycle_pending.MyViewHolder>{
    ArrayList<annonces_pending> list1;
    Context context;
    public adapter_recycle_pending(Context context, ArrayList<annonces_pending> list1) {
        this.context=context;
        this.list1 = list1;
    }

    @NonNull
    @Override
    public adapter_recycle_pending.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.cards_frags_pending,parent,false);
        return new adapter_recycle_pending.MyViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull adapter_recycle_pending.MyViewHolder holder, int position) {
        annonces_pending an=list1.get(position);
        holder.ed.setId(position);
        holder.del.setId(position);
        String idann=an.Idannonce();
        String IDuser=an.IDuser();
        Picasso.get().load(an.image1()).error(R.drawable.images).into(holder.image1);
        Picasso.get().load(an.image2()).error(R.drawable.images).into(holder.image2);
        holder.ed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newEtatValue = "accepted";
                FirebaseDatabase.getInstance().getReference("Offres").child(IDuser).child(idann).child("etat").setValue(newEtatValue);
            }
        });
        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Offres").child(IDuser).child(idann);
                ref.removeValue();
            }
        });
    }
    @Override
    public int getItemCount() {
        return list1.size();


    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        //add categorie description and others
        Button ed,del;
        ImageView image1,image2;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image1=itemView.findViewById(R.id.image11);
            image2=itemView.findViewById(R.id.image22);
            ed=itemView.findViewById(R.id.acc1);
            del=itemView.findViewById(R.id.igno1);


        }
    }
}