package com.example.trocapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Model.UserData;

public class Home extends AppCompatActivity {
    //base de donnees recuperation
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    private final List<MyItems> myItemsList = new ArrayList<>();


    private String nomuser="";


    private SearchView searchView;
    private AnnonceAdapt adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //correction de l'affichage du buttom

        BottomNavigationView butt=(BottomNavigationView) findViewById(R.id.bottomNavigation);
        butt.setBackground(null);

        //navigation entre les categorie
            //pour school
        ImageButton school = (ImageButton) findViewById(R.id.manuel_id);
        school.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,AnnonceCategorisee.class);
                startActivity(intent);
            }
        });
            //pour sport
        ImageButton sport = (ImageButton) findViewById(R.id.sport_id);
        sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,Sportcategorie.class);
                startActivity(intent);
            }
        });
            //pour meuble
        ImageButton meuble = (ImageButton) findViewById(R.id.meuble_id);
        meuble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,Meuble.class);
                startActivity(intent);
            }
        });
            //pour vaisselle
        ImageButton vaisselle = (ImageButton) findViewById(R.id.vaisselle_id);
        vaisselle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,AnnonceCategorisee.class);
                startActivity(intent);
            }
        });
            //pour divertissement
        ImageButton divertissement = (ImageButton) findViewById(R.id.divertissement_id);
        divertissement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this,Divertissement.class);
                startActivity(intent);
            }
        });
        //partage des annonces



        adapter= new AnnonceAdapt(myItemsList, Home.this);
        final RecyclerView recyclerView= findViewById(R.id.recyclerView);
        searchView= findViewById(R.id.search);
        searchView.clearFocus();
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                myItemsList.clear();

                for(DataSnapshot Produits : snapshot.child("Produits").getChildren()){
                    for(DataSnapshot elemnt : Produits.getChildren()){
                        final String hiddenID = elemnt.getKey();
                        final String userId=elemnt.child("userId").getValue(String.class);
                    final String getnom_produit = elemnt.child("nom_produit").getValue(String.class);
                    final String getImage = elemnt.child("image").getValue(String.class);
                    final String getcategorie= elemnt.child("categorie").getValue(String.class);
                    final String getdescrip = elemnt.child("description").getValue(String.class);
                    final String getdate = elemnt.child("date_Ajout").getValue(String.class);
                    String getuser="";
                    //final String getuser=resturnTroqueur((String) Produits.getKey());
                    for(DataSnapshot user : snapshot.child("Registered Users").getChildren()){
                        String id = user.getKey();
                        if (id.equals(Produits.getKey())) {
                            HashMap<String, Object> userData = (HashMap<String, Object>) user.getValue();
                            getuser = (String) userData.get("nomComplet");

                        }

                    }

                    //showDialogWithProductDetails(getdescrip, getcategorie);
                    MyItems myItems = new MyItems(userId,getcategorie,getdescrip, getImage, getnom_produit, getdate,getuser,hiddenID);
                    myItemsList.add(myItems);
                }
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchListMethod(newText);
                return true;
            }
        });
        //menu
        butt.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.menu_home:
                    Intent intent = new Intent(Home.this,Home.class);
                    startActivity(intent);
                    return true;
                case R.id.menu_profil:
                    Intent intent2 = new Intent(Home.this,ProfilActivity.class);
                    startActivity(intent2);
                    return true;
                case R.id.menu_chat:
                    Intent intent3 = new Intent(Home.this,ChatPart.class);
                    startActivity(intent3);
                    return true;
                case R.id.menu_notification:

                    return true;
                default:
                    return false;
            }
        });
        /*FloatingActionButton floatingActionButton=(FloatingActionButton) findViewById(R.id.bottomApp);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(Home.this,Ajouter.class);
                startActivity(intent2);
            }
        });*/
        FloatingActionButton button=(FloatingActionButton) findViewById(R.id.add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this,AjoutProduit.class));
            }
        });

    }
    public String resturnTroqueur(String userId){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot user : snapshot.child("Registered Users").getChildren()){
                    String id = user.getKey();

                    if (id.equals(userId)) {
                        HashMap<String, Object> userData = (HashMap<String, Object>) user.getValue();
                        nomuser = (String) userData.get("nomComplet");

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return nomuser;
    }
    public void searchListMethod(String text){
        ArrayList<MyItems> searchList=new ArrayList<>();

        for (MyItems myItems: myItemsList){
            if (myItems.getNom_produit().toLowerCase().contains(text.toLowerCase())){
                searchList.add(myItems);
            }
            if (myItems.getNom_troqueur().toLowerCase().contains(text.toLowerCase())){
                searchList.add(myItems);
            }

        }

        adapter.searchDataList(searchList);
    }



}