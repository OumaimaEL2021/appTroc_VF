package com.example.trocapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Catalogue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalogue);
        BottomNavigationView butt=(BottomNavigationView) findViewById(R.id.bottomNavigation);
        butt.setBackground(null);
    }
}