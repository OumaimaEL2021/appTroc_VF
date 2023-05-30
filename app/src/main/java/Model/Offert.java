package Model;

public class Offert {

        // public  String user1 ,user2;
        public  String produitId1, produitId2 ;

        public Offert(){

        }
        public Offert(String produitId1, String produitId2) {
            //this.user1 = user1;
            //this.user2 = user2;
            this.produitId1 = produitId1;
            this.produitId2 = produitId2;
        }


        public String getProduitId1() {
            return produitId1;
        }

        public void setProduitId1(String produitId1) {
            this.produitId1 = produitId1;
        }

        public String getProduitId2() {
            return produitId2;
        }

        public void setProduitId2(String produitId2) {
            this.produitId2 = produitId2;
        }
}
