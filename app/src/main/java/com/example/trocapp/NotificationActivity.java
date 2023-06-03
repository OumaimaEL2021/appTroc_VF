package com.example.trocapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<annonces_pending> liste1;
    ArrayList<String> mesannonces;
    Toolbar toolbar ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        recyclerView = findViewById(R.id.rec3);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        liste1= new ArrayList<>();
        toolbar = findViewById(R.id.app_toolbar2);
        mesannonces=new ArrayList<String>();
        recyclerView.setAdapter(new adapter_recycle_pending(this,liste1));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                liste1.clear();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();


                for (DataSnapshot snapshot : dataSnapshot.child("Offres").getChildren()) {
                    if(snapshot.getKey().equals(userId)){
                        String IDuser=snapshot.getKey();
                        for(DataSnapshot elmnts: snapshot.getChildren()){

                            if(elmnts.child("etat").getValue(String.class).equals("accepted") )
                            {
                                String getproduitId1 = elmnts.child("produitId1").getValue(String.class);
                                String getproduitId2 = elmnts.child("produitId2").getValue(String.class);
                                String IDannonce = elmnts.getKey();
                                String getImage1="";
                                String getImage2="";

                                for(DataSnapshot s : dataSnapshot.child("Produits").getChildren()){
                                    for(DataSnapshot e: s.getChildren()){
                                        if(getproduitId1.equals(e.getKey())){
                                            getImage1=e.child("image").getValue(String.class);
                                        }
                                        else if(getproduitId2.equals(e.getKey())){
                                            getImage2=e.child("image").getValue(String.class);
                                        }

                                    }

                                }
                                annonces_pending an = new annonces_pending(getproduitId1,getImage1,getproduitId2,getImage2,IDannonce,IDuser);
                                liste1.add(an);

                            }}
                    }

                    //annonces an = snapshot.getValue(annonces.class);

                }
                recyclerView.setAdapter(new adapter_recycle_notif(NotificationActivity.this,liste1));
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

                Log.e("MyFragment", "Error getting data", databaseError.toException());
            }
        });
    }
}