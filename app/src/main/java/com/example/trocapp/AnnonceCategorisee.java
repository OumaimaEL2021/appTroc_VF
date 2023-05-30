package com.example.trocapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AnnonceCategorisee extends AppCompatActivity {
    private String categorie;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private final List<MyItems> myItemsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        categorie=getIntent().getStringExtra("categorie");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annonce_categorisee);
        BottomNavigationView butt=(BottomNavigationView) findViewById(R.id.bottomNavigation);
        butt.setBackground(null);
        //navigation entre les categorie
        //pour school
        ImageButton school = (ImageButton) findViewById(R.id.manuel_id);
        school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnnonceCategorisee.this,AnnonceCategorisee.class);
                startActivity(intent);
            }
        });
        //pour sport
        ImageButton sport = (ImageButton) findViewById(R.id.sport_id);
        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnnonceCategorisee.this,Sportcategorie.class);
                startActivity(intent);
            }
        });
        //pour meuble
        ImageButton meuble = (ImageButton) findViewById(R.id.meuble_id);
        meuble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnnonceCategorisee.this,Meuble.class);
                startActivity(intent);
            }
        });
        //pour vaisselle
        ImageButton vaisselle = (ImageButton) findViewById(R.id.vaisselle_id);
        vaisselle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnnonceCategorisee.this,Vaisselle.class);
                startActivity(intent);
            }
        });
        //pour divertissement
        ImageButton divertissement = (ImageButton) findViewById(R.id.divertissement_id);
        divertissement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AnnonceCategorisee.this,Divertissement.class);
                startActivity(intent);
            }
        });
        final RecyclerView recyclerView1= findViewById(R.id.recyclerView1);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView1.setLayoutManager(layoutManager);
        //fixer recyclerview sire
        //recyclerView1.setHasFixedSize(true);
        /*recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));*/
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myItemsList.clear();
                for(DataSnapshot Produits : snapshot.child("Produits").getChildren()){
                    for(DataSnapshot elemnt : Produits.getChildren()){
                        final String userId= elemnt.child("userId").getValue(String.class);
                        final String getnom_produit = elemnt.child("nom_produit").getValue(String.class);
                        final String getImage = elemnt.child("image").getValue(String.class);
                        final String getcategorie= elemnt.child("categorie").getValue(String.class);
                        final String getdescrip = elemnt.child("description").getValue(String.class);
                        final String getdate = elemnt.child("date_Ajout").getValue(String.class);
                        MyItems myItems = new MyItems(userId,getcategorie,getdescrip, getImage, getnom_produit, getdate,"nom_troqueur");
                        if (getcategorie != null && getcategorie.equals("Papeterie")) {
                            myItemsList.add(myItems);
                        }

                    }

                }
                recyclerView1.setAdapter(new AdaptAnnocaCategorie(myItemsList,AnnonceCategorisee.this));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //menu
        butt.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    Intent intent = new Intent(AnnonceCategorisee.this,Home.class);
                    startActivity(intent);
                    return true;
                case R.id.menu_profil:
                    Intent intent2 = new Intent(AnnonceCategorisee.this,ProfilActivity.class);
                    startActivity(intent2);
                    return true;
                case R.id.menu_chat:
                    // Action à effectuer pour le menu item 3
                    return true;
                case R.id.menu_notification:
                    // Action à effectuer pour le menu item 3
                    return true;
                default:
                    return false;
            }
        });
        /*FloatingActionButton floatingActionButton=(FloatingActionButton) findViewById(R.id.bottomApp);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(AnnonceCategorisee.this,Ajouter.class);
                startActivity(intent2);
            }
        });*/
        FloatingActionButton button=(FloatingActionButton) findViewById(R.id.add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AnnonceCategorisee.this,AjoutProduit.class));
            }
        });
    }
}