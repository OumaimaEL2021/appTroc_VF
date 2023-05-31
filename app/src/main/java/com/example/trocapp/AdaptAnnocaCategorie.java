package com.example.trocapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

public class AdaptAnnocaCategorie extends RecyclerView.Adapter<AdaptAnnocaCategorie.MyViewHolder> {
    private List<MyItems> items;
    private final Context context;

    public AdaptAnnocaCategorie(List<MyItems> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapt_annonce_categorie,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyItems myitems = items.get(position);
        holder.titleTextView.setText(myitems.getNom_produit());
        Picasso.get().load(myitems.getImage()).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                holder.constraintLayout.setBackground(new BitmapDrawable(context.getResources(), bitmap));
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
                Log.d(TAG, "onBitmapFailed: " + e.getMessage());
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {}
        });
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DescriptionProduit.class);
                intent.putExtra("Description",myitems.getDescription());
                intent.putExtra("dateAjout",myitems.getDate_Ajout());
                intent.putExtra("imageId",myitems.getImage());
                intent.putExtra("nom_du_produit",myitems.getNom_produit());
                intent.putExtra("user",myitems.getNom_troqueur());
                intent.putExtra("idProduit",myitems.getHiddenID());
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    static class MyViewHolder extends RecyclerView.ViewHolder{
        //private final ImageView imageView;
        private final LinearLayout constraintLayout;

        private final TextView titleTextView;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            constraintLayout =itemView.findViewById(R.id.constraint);
            //imageView =itemView.findViewById(R.id.image);
            titleTextView = itemView.findViewById(R.id.nom_produit);
        }
    }
}
