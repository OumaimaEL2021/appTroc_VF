package Model;

public class Produit {
    String image = "";
    String nom_produit="";
    String description="";
    String categorie="";
    String date_Ajout="";

    String hidden="";


    public String getHidden(){
        return hidden;
    }

    public void setHidden(String hidden1){
        hidden = hidden1;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    String userId = "";

    public  Produit(){}

    public Produit(String image, String nom_produit, String description, String categorie, String date_Ajout,String id) {
        this.image = image;
        this.nom_produit = nom_produit;
        this.description = description;
        this.categorie = categorie;
        this.date_Ajout = date_Ajout;
        this.userId = id;
    }


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDate_Ajout() {
        return date_Ajout;
    }

    public void setDate_Ajout(String date_Ajout) {
        this.date_Ajout = date_Ajout;
    }
}
