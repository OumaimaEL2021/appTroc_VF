package com.example.trocapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.trocapp.LoginActivity;
import com.example.trocapp.R;
import com.example.trocapp.adapter_recycle_pending;
import com.example.trocapp.annonces_pending;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Pending_Fragment extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<annonces_pending> liste1;
    ArrayList<String> mesannonces;


    public Pending_Fragment() {

    }

    public static Pending_Fragment newInstance() {
        Pending_Fragment fragment = new Pending_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_pending_, container, false);
        recyclerView = view.findViewById(R.id.rec2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        liste1= new ArrayList<>();
        mesannonces=new ArrayList<String>();
        recyclerView.setAdapter(new adapter_recycle_pending(getContext(),liste1));
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();
        // Query the database for the products
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                liste1.clear();
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                for (DataSnapshot snapshot : dataSnapshot.child("Produits").getChildren()) {
                    if(snapshot.getKey().equals(userId)){
                        for(DataSnapshot elmnts: snapshot.getChildren()){
                            mesannonces.add(elmnts.getKey());}
                    }
                }




                for (DataSnapshot snapshot : dataSnapshot.child("Offres").getChildren()) {
                    if(snapshot.getKey().equals(userId)==false){
                        String IDuser=snapshot.getKey();
                        for(DataSnapshot elmnts: snapshot.getChildren()){

                            if(mesannonces.contains(elmnts.child("produitId2").getValue(String.class))&& elmnts.child("etat").getValue(String.class).equals("pending") )
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
                recyclerView.setAdapter(new adapter_recycle_pending(getContext(),liste1));
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

                Log.e("MyFragment", "Error getting data", databaseError.toException());
            }
        });
        return view;
    }
}