package com.example.trocapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import android.util.Log;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Model.Produit;

public class SettingsActivity extends AppCompatActivity {



        private EditText textViewFullName, textViewPhoneNumber, textViewAddress, textViewCountry, textViewCity, textViewDescription;
        Button editUser,cancel;
    private ImageView profileImageView;
    private Button chooseImageButton;
    private StorageReference storageReference;
    private Uri imageUri;
    String profilImage;
    private static final int PICK_IMAGE_REQUEST = 1;
        private DatabaseReference userRef= FirebaseDatabase.getInstance().getReference();

        @SuppressLint("MissingInflatedId")
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_settings);
            storageReference = FirebaseStorage.getInstance().getReference();
            profileImageView = findViewById(R.id.photo);
            chooseImageButton = findViewById(R.id.camera);
            textViewFullName = findViewById(R.id.editTextFullName);
            editUser= findViewById(R.id.editUser);
            cancel= findViewById(R.id.cancel);
            textViewPhoneNumber = findViewById(R.id.editTextPhoneNumber);
            textViewAddress = findViewById(R.id.editTextAddress);
            textViewCountry = findViewById(R.id.editTextCountry);
            textViewCity = findViewById(R.id.editTextCity);
            textViewDescription = findViewById(R.id.editTextDescription);
            FirebaseAuth auth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = auth.getCurrentUser();
            if (currentUser != null) {
                String userId = currentUser.getUid();
                // Utilisez l'ID de l'utilisateur pour accéder aux données correspondantes dans la base de données
                userRef = FirebaseDatabase.getInstance().getReference().child("Registered Users").child(userId);
                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String fullName = snapshot.child("nomComplet").getValue(String.class);
                            String phoneNumber = snapshot.child("phone").getValue(String.class);
                            String address = snapshot.child("address").getValue(String.class);
                            String country = snapshot.child("pays").getValue(String.class);
                            String city = snapshot.child("ville").getValue(String.class);
                            String description = snapshot.child("description").getValue(String.class);
                            profilImage=snapshot.child("profileImageUrl").getValue(String.class);
                            textViewFullName.setText(fullName);
                            textViewPhoneNumber.setText(phoneNumber);
                            textViewAddress.setText(address);
                            textViewCountry.setText(country);
                            textViewCity.setText(city);
                            textViewDescription.setText(description);
                            Picasso.get().load(profilImage).into(profileImageView);


                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle database error
                    }
                });

                } else {
                // L'utilisateur n'est pas connecté
            }
            editUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String fullName = textViewFullName.getText().toString();
                    String phoneNumber = textViewPhoneNumber.getText().toString();
                    String address = textViewAddress.getText().toString();
                    String country = textViewCountry.getText().toString();
                    String city = textViewCity.getText().toString();
                    String description = textViewDescription.getText().toString();



                    // Obtenez l'ID de l'utilisateur en cours
                    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (currentUser != null) {
                        String userId = currentUser.getUid();
                        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Registered Users").child(userId);

                        // Mettez à jour les données de l'utilisateur dans la base de données
                        userRef.child("nomComplet").setValue(fullName);
                        userRef.child("phone").setValue(phoneNumber);
                        userRef.child("address").setValue(address);
                        userRef.child("pays").setValue(country);
                        userRef.child("ville").setValue(city);
                        userRef.child("description").setValue(description);
                        userRef.child("profileImageUrl").setValue(profilImage);
                        uploadImage();


                        Toast.makeText(SettingsActivity.this, "Données mises à jour avec succès", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            chooseImageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openFileChooser();
                }
            });

        }
    // Méthode pour ouvrir la galerie et sélectionner une image
    private void openFileChooser() {
        if (imageUri == null) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
        }
    }


    // Méthode pour gérer le résultat de la sélection d'image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();

            // Chargez l'image sélectionnée dans l'ImageView
            Picasso.get().load(imageUri).into(profileImageView);
        } else {
            // Si aucune image n'a été sélectionnée, conserver la même photo
            Picasso.get().load(profilImage).into(profileImageView);
        }
    }

    private void uploadImage() {
        if (imageUri != null) {
            // Générez un nom de fichier unique pour l'image
            String filename = System.currentTimeMillis() + ".jpg";

            // Référence à l'emplacement où l'image sera stockée dans Firebase Storage
                StorageReference imageRef = storageReference.child("profile_images/" + filename);

            // Téléchargez l'image sur Firebase Storage
            imageRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Obtenez l'URL de téléchargement de l'image
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imageUrl = uri.toString();

                                    // Enregistrez l'URL de l'image dans la base de données ou effectuez toute autre opération nécessaire
                                    // Exemple :
                                    userRef.child("profileImageUrl").setValue(imageUrl)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Toast.makeText(SettingsActivity.this, "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(SettingsActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SettingsActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Picasso.get().load(profilImage).into(profileImageView);
            Toast.makeText(SettingsActivity.this, "No image selected", Toast.LENGTH_SHORT).show();
        }
    }

}