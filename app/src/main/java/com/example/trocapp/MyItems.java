package com.example.trocapp;

public class MyItems {
    private final String userId,categorie, description, image, nom_produit, nom_troqueur;
    private final String date_Ajout;
    private final String hiddenID;

    public MyItems(String userId, String categorie, String description, String image, String nom_produit,  String date_Ajout, String nom_troqueur,String hiddenID) {
        this.userId=userId;
        this.categorie = categorie;
        this.description = description;
        this.image = image;
        this.nom_produit = nom_produit;
        this.nom_troqueur = nom_troqueur;
        this.date_Ajout = date_Ajout;
        this.hiddenID = hiddenID;
    }

    public  String getUserId(){ return  userId; }

    public String getCategorie() {
        return categorie;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public String getDate_Ajout() {
        return date_Ajout;
    }

    public String getNom_troqueur() {
        return nom_troqueur;
    }
    public String getHiddenID(){return hiddenID;}
}
