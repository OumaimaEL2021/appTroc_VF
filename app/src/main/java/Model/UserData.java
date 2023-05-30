package Model;

public class UserData {
    public String nomComplet,phone,address,pays,ville,description;
    public String profileImageUrl = "";

    public UserData(String nomComplet,String phone,String address,String pays,String ville,String description, String profileImageUrl){
        this.nomComplet= nomComplet;
        this.phone= phone;
        this.pays=pays;
        this.ville=ville;
        this.address= address;
        this.description= description;
        this.profileImageUrl=  profileImageUrl;
    }

    public String getNomComplet() {
        return nomComplet;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getPays() {
        return pays;
    }

    public String getVille() {
        return ville;
    }

    public String getDescription() {
        return description;
    }
    public String getProfileImageUrl() {
        return profileImageUrl;
    }
}
