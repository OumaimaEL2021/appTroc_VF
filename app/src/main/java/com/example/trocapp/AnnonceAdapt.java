package com.example.trocapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import Model.UserData;

public class AnnonceAdapt extends RecyclerView.Adapter<AnnonceAdapt.MyViewHolder> {

    private List<MyItems> items;

    private final Context context;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private String categorie;


    public AnnonceAdapt(List<MyItems> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.ann_adapter_layout,null));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyItems myitems = items.get(position);

        holder.nom_produit.setText(myitems.getNom_produit());
        holder.date_publication.setText(myitems.getDate_Ajout());
        Picasso.get().load(myitems.getImage()).into(holder.image);
        holder.hiddentext.setText(myitems.getHiddenID());
        holder.user.setText(myitems.getNom_troqueur());

        holder.troquer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Activity_view_all.class);
                intent.putExtra("IDproduit1",holder.hiddentext.getText().toString());
                context.startActivity(intent);
                //TrocProduit trocProduit = new TrocProduit();
                //trocProduit.produitId2= holder.hiddentext.getText().toString();
                //trocProduit.user1 = FirebaseAuth.getInstance().getCurrentUser().getUid();

                //FirebaseDatabase.getInstance().getReference().child("InformationTroc").push().child("offert").setValue(trocProduit);
            }
        });


    String userId = myitems.getUserId();
        DatabaseReference userRef = databaseReference.child("Registered Users").child(userId).child("profileImageUrl");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String profileImageUrl = dataSnapshot.getValue(String.class);

                    if (profileImageUrl != null && !profileImageUrl.isEmpty()) {
                        Picasso.get().load(profileImageUrl).into(holder.profil);
                    } else {
                        // Si aucune image de profil n'est disponible, vous pouvez définir une image par défaut
                        holder.profil.setImageResource(R.drawable.baseline_account_circle_24);
                    }
                } else {
                    // Si l'URL de l'image de profil n'existe pas dans la base de données, vous pouvez définir une image par défaut
                    holder.profil.setImageResource(R.drawable.baseline_account_circle_24);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Gérer les erreurs de récupération des données de l'image de profil
            }
        });

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Afficher la boîte de dialogue avec la description du produit
                showDialogWithProductDescription(context,myitems.getDescription());
            }
        });
    }
    private void showDialogWithProductDescription(Context context, String description) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Détails du produit");
        builder.setMessage(description);
        builder.setPositiveButton("OK", null);
        builder.show();
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
    public void searchDataList(List<MyItems> searchList){
        items=searchList;
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        private final ImageView image,profil;
        private final TextView nom_produit;
        private final TextView date_publication;
        private final TextView user;
        private final TextView details ;
        Button troquer;

        private final TextView hiddentext;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            image =itemView.findViewById(R.id.image);
            troquer = itemView.findViewById(R.id.button_troquer);
            profil=itemView.findViewById(R.id.imageView);
            nom_produit = itemView.findViewById(R.id.nom_produit);
            date_publication = itemView.findViewById(R.id.date);
            user=itemView.findViewById(R.id.user);
            details = itemView.findViewById(R.id.details);
            hiddentext = itemView.findViewById(R.id.hiddenTextView);
        }
    }

}
