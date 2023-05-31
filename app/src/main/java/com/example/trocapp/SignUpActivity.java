package com.example.trocapp;

import androidx.appcompat.app.AppCompatActivity;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;
import android.widget.AdapterView;
import java.util.List;
import java.util.Arrays;
import android.widget.ArrayAdapter;

import Model.UserData;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity {


    private FirebaseAuth auth;
    private EditText signupEmail, signupPassword, signupNomComplet, signupPhone, signupDescription, signupVille, signupPays, signupAddress;
    private Button signupButton;
    private TextView loginRedirectText;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //final FirebaseUser mFirebaseUser = auth.getCurrentUser();
       // if (mFirebaseUser!=null){
        //    currentUser = auth.getUid();
        //}
        auth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.signup_email);
        signupPassword = findViewById(R.id.signup_password);
        signupNomComplet = findViewById(R.id.signup_nomComplet);
        signupPhone = findViewById(R.id.signup_phone);
        signupAddress = findViewById(R.id.signup_address);

        signupDescription = findViewById(R.id.signup_description);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        Spinner countrySpinner = (Spinner) findViewById(R.id.country_spinner);
        Spinner citySpinner = (Spinner) findViewById(R.id.city_spinner);
        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");
        final String[] selectedCity = new String[1];

        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedCountry = adapterView.getItemAtPosition(position).toString();
                // Appeler une méthode pour charger les villes pour le pays sélectionné
                String[] cityArray = getResources().getStringArray(R.array.maroc_cities); // remplacer "maroc_cities" par le nom de la liste de villes correspondante au pays sélectionné
                List<String> cityList = Arrays.asList(cityArray);

                // Remplir le Spinner des villes avec la liste correspondante
                ArrayAdapter<String> adapter = new ArrayAdapter<>(SignUpActivity.this, android.R.layout.simple_spinner_dropdown_item, cityList);
                citySpinner.setAdapter(adapter);
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ne rien faire si aucun pays n'est sélectionné
            }
        });

// Listener pour le Spinner de la ville
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Récupérer la ville sélectionnée
                selectedCity[0] = (String) parent.getItemAtPosition(position);

                // Enregistrer la sélection dans la base de données Firebase

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Ne rien faire si aucune ville n'est sélectionnée
            }
        });
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = signupEmail.getText().toString().trim();
                String pass = signupPassword.getText().toString().trim();
                String nomComplet = signupNomComplet.getText().toString().trim();
                String address = signupAddress.getText().toString().trim();
                String phone = signupPhone.getText().toString().trim();
                String pays = "Morocco";
                String ville = selectedCity[0];
                System.out.println(ville);
                String description = signupDescription.getText().toString().trim();
                UserData writeUserDetails = new UserData(nomComplet, phone, address, pays, ville, description,"");





                if (nomComplet.isEmpty()) {
                    signupNomComplet.setError("Nom cannot be empty");
                }
                if (mail.isEmpty()) {
                    signupEmail.setError("Email cannot be empty");
                }
                if (address.isEmpty()) {
                    signupAddress.setError("Adresse cannot be empty");
                }
                if (phone.isEmpty()) {
                    signupPhone.setError("Phone cannot be empty");
                }
                if (description.isEmpty()) {
                    signupDescription.setError("Description cannot be empty");
                }
                if (pass.isEmpty()) {
                    signupPassword.setError("Password cannot be empty");
                } else {
                    auth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                referenceProfile.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                });


                                Toast.makeText(SignUpActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(SignUpActivity.this, "Signup Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                            //auth.getCurrentUser().getUid()

                }
            }
        });

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }
}