package com.example.trocapp;

public class annonces {

    String categorie,description,date_Ajout,nom_produit,userId,image,IDannonce;

    public annonces( String date_Ajout, String nom_produit,String IDannonce,String image) {
        this.date_Ajout = date_Ajout;
        this.nom_produit = nom_produit;
        this.IDannonce = IDannonce;
        this.image = image;

    }

    public String getCategorie() {
        return categorie;
    }

    public String getDescription() {
        return description;
    }
    public String getIDannonce() {
        return IDannonce;
    }

    public String getDate_Ajout() {
        return date_Ajout;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public String getUserId() {
        return userId;
    }

    public String getImage() {
        return image;
    }

}
