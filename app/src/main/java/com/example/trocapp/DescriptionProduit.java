package com.example.trocapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class DescriptionProduit extends AppCompatActivity {
    private ImageView imageView;
    private TextView date;
    private TextView user;
    private TextView nomProduit;
    private TextView description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);
        imageView=findViewById(R.id.ib_view_photo);
        date=findViewById(R.id.tv_product_view_date);
        user=findViewById(R.id.tv_product_view_user);
        nomProduit=findViewById(R.id.view_product_title);
        description=findViewById(R.id.view_product_description);
        Button button=findViewById(R.id.btn_barter);
        ImageView backBtn=findViewById(R.id.back);

        final String getDescription = getIntent().getStringExtra("Description");
        final String getdateAjout = getIntent().getStringExtra("dateAjout");
        final String getimageId = getIntent().getStringExtra("imageId");
        final String getuser = getIntent().getStringExtra("user");
        final String getIdProduit = getIntent().getStringExtra("idProduit");
        final String getnom_du_produit = getIntent().getStringExtra("nom_du_produit");
        date.setText(getdateAjout);
        description.setText(getDescription);
        nomProduit.setText(getnom_du_produit);
        user.setText(getuser);
        Picasso.get().load(getimageId).into(imageView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DescriptionProduit.this,Activity_view_all.class);
                intent.putExtra("IDproduit1",getIdProduit);
                startActivity(intent);
                //TrocProduit trocProduit = new TrocProduit();
                //trocProduit.produitId2= holder.hiddentext.getText().toString();
                //trocProduit.user1 = FirebaseAuth.getInstance().getCurrentUser().getUid();

                //FirebaseDatabase.getInstance().getReference().child("InformationTroc").push().child("offert").setValue(trocProduit);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {
                                           finish();
                                       }
                                   }
        );


    }
}