package com.example.trocapp;

public class annonces_pending {
    String userId1;
    String image1;
    String userId2;
    String image2;
    String IDuser;

    String Idannonce;

    public annonces_pending(String userId1, String image1, String userId2, String image2,String I,String j) {
        this.userId1 = userId1;
        this.image1 = image1;
        this.userId2 = userId2;
        this.image2 = image2;
        this.Idannonce=I;
        this.IDuser=j;
    }

    public String Idannonce() {
        return Idannonce;
    }

    public void setIdannonce(String idannonce) {
        Idannonce = idannonce;
    }

    public String IDuser() {
        return IDuser;
    }

    public void setIDuser(String IDuser) {
        this.IDuser = IDuser;
    }
    public String userId1() {
        return userId1;
    }

    public void setUserId1(String userId1) {
        this.userId1 = userId1;
    }

    public String image1() {
        return image1;
    }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String userId2() {
        return userId2;
    }

    public void setUserId2(String userId2) {
        this.userId2 = userId2;
    }

    public String image2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }
}
