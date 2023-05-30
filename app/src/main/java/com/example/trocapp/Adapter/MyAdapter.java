package com.example.trocapp.Adapter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.trocapp.Activity_view_all;
import com.example.trocapp.Home;
import com.example.trocapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

import Model.Offert;
import Model.Produit;

public class MyAdapter  extends RecyclerView.Adapter<MyViewHolder>{

    private Context context;
    private onCardViewListener oncardviewlistener;
    private List<Produit> dataList;
    //private CardClickListener onCardClickListener;

    public MyAdapter(Context context,List<Produit> dataList) {
        this.context = context;
        this.dataList = dataList;

        try{
            this.oncardviewlistener  =(onCardViewListener)context;
        }catch (Exception e){

        }

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_choice_item, parent, false);
        return new MyViewHolder(view);
    }


    int selectedItemPosition=0;

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //Glide.with(context).load(dataList.get(position).getImage()).into(holder.recImage);
        holder.recTitle.setText(dataList.get(position).getNom_produit());
        holder.hidden.setText(dataList.get(position).getHidden());
        Picasso.get().load(dataList.get(position).getImage()).into(holder.recImage);
        holder.hiddenbutton.setTag(holder.hidden.getText().toString());
        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String textViewValue = holder.hidden.getText().toString();
                // holder.hiddenbutton.setTag(textViewValue);
                // System.out.println(textViewValue);
                oncardviewlistener.onselectedCard(textViewValue);
                selectedItemPosition = position;
                notifyDataSetChanged();
                if(selectedItemPosition == position)
                    holder.recCard.setBackgroundColor(Color.parseColor("#FF018786"));
                else
                    holder.recCard.setBackgroundColor(Color.parseColor("white"));

            }


        });
 /*       holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedItemPosition = position;
                notifyDataSetChanged();
            }

        });

        if(selectedItemPosition == position){
            holder.recCard.setBackgroundColor(Color.parseColor("@color/verttroc"));


        }

      */      //String produitId = dataList.get(position).


    }
    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public void searchDataList(ArrayList<Produit> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }

    public interface onCardViewListener{
        public void onselectedCard(String text);
    }

}


class MyViewHolder extends RecyclerView.ViewHolder {
    ImageView recImage;
    TextView recTitle, recDesc, recLang, hidden;
    CardView recCard;
    //private CardClickListener cardClickListener ;
    Button hiddenbutton;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        hiddenbutton = itemView.findViewById(R.id.hiddenButton);
        recImage = itemView.findViewById(R.id.recImage);
        recCard = itemView.findViewById(R.id.recCard);
        recDesc = itemView.findViewById(R.id.recDesc);
        //recLang = itemView.findViewById(R.id.recLang);
        recTitle = itemView.findViewById(R.id.recTitle);
        hidden = itemView.findViewById(R.id.hiddenTextView2);
        //cardClickListener = onCardClickListener;
        //itemView.setOnClickListener(this);
    }
    // String textValue;
/*
    @Override
    public void onClick(View v) {
        // Récupérez la valeur du champ de texte lorsque la CardView est sélectionnée
        String textValue = hidden.getText().toString();
        //recCard.setBackgroundColor(Color.parseColor("@color/verttroc"));
        // Appelez la méthode onCardSelected() de l'interface avec la valeur du champ de texte
        int position = getAdapterPosition();
        System.out.println("la valeur est :" + textValue);
        if ( cardClickListener != null) {
            //String textValue = hidden.getText().toString();
            cardClickListener.onCardSelected(textValue);
            System.out.println("la valeur est :" + textValue);
        }

    }*/


}