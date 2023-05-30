package com.example.trocapp.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.trocapp.R;
import com.example.trocapp.adapter_recycle;
import com.example.trocapp.annonces;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class Empty_Fragment extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<annonces>liste1;
    public Empty_Fragment() {
    }


    // TODO: Rename and change types and number of parameters
    public static Empty_Fragment newInstance() {
        Empty_Fragment fragment = new Empty_Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_empty_, container, false);
        recyclerView = view.findViewById(R.id.rec1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        liste1= new ArrayList<>();
        recyclerView.setAdapter(new adapter_recycle(getContext(),liste1));
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
                            String getNom_produit = elmnts.child("nom_produit").getValue(String.class);
                            String getDate_Ajout = elmnts.child("date_Ajout").getValue(String.class);
                            String getImage = elmnts.child("image").getValue(String.class);
                            String IDannonce = elmnts.getKey();
                            annonces an = new annonces(getDate_Ajout,getNom_produit,IDannonce,getImage);
                            liste1.add(an);}
                    }

                    //annonces an = snapshot.getValue(annonces.class);

                }
                recyclerView.setAdapter(new adapter_recycle(getContext(),liste1));
            }

            @Override
            public void onCancelled( DatabaseError databaseError) {

                Log.e("MyFragment", "Error getting data", databaseError.toException());
            }
        });
        return view;
    }

}
