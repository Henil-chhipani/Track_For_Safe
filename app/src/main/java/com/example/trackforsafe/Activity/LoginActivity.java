package com.example.trackforsafe.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trackforsafe.R;
import com.example.trackforsafe.utilities.Constants;
import com.example.trackforsafe.utilities.PreferenceManager;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;


public class LoginActivity extends AppCompatActivity {

    TextInputLayout usernameoremail, password;
    TextView signup, forgotpass;
    Button loginbtn;
    PreferenceManager preferenceManager;


    ProgressBar progressBar1;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://track-for-safe-default-rtdb.firebaseio.com/");

    FirebaseAuth mAuth;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameoremail = findViewById(R.id.phonenumberforotpLayout);
        password = findViewById(R.id.passwordLayout1);
        forgotpass = findViewById(R.id.forgotpassword);
        signup = findViewById(R.id.signup);
        loginbtn = findViewById(R.id.sendotpbtn);
        progressBar1 = findViewById(R.id.progressBar1);
        mAuth = FirebaseAuth.getInstance();
        preferenceManager = new PreferenceManager(getApplicationContext());
        if (preferenceManager.getBoolean(Constants.IS_LOGIN)) {
            Intent intent = new Intent(getApplicationContext(), Language.class);
            startActivity(intent);
            finish();
        }

            forgotpass.setOnClickListener(view -> {
                Intent intent = new Intent(getApplicationContext(), forgotpassword_activity.class);
                startActivity(intent);
            });

            signup.setOnClickListener(view -> {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            });

            loginbtn.setOnClickListener(view -> {

                String username = Objects.requireNonNull(usernameoremail.getEditText()).getText().toString().trim();
                String pass = Objects.requireNonNull(password.getEditText()).getText().toString().trim();

                if (username.equals("") || pass.equals("")) {
                    Toast.makeText(this, "Please enter required fields", Toast.LENGTH_SHORT).show();
                } else {
                    loginbtn.setVisibility(View.GONE);
                    progressBar1.setVisibility(View.VISIBLE);

                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!validateEmail()) {
                                final String databasephone = Objects.requireNonNull(snapshot.child(username).child(Constants.PHONE_NUMBER).getValue()).toString();
                                if (username.equals(databasephone)) {
                                    final String getPass = Objects.requireNonNull(snapshot.child(username).child(Constants.PASSWORD).getValue()).toString();
                                    if (getPass.equals(pass)) {

                                        preferenceManager.putBoolean(Constants.IS_LOGIN, true);
                                        preferenceManager.putString(Constants.NAME, Objects.requireNonNull(snapshot.child(username).child(Constants.NAME).getValue()).toString());
                                        preferenceManager.putString(Constants.PHONE_NUMBER, Objects.requireNonNull(snapshot.child(username).child(Constants.PHONE_NUMBER).getValue()).toString());
                                        preferenceManager.putString(Constants.PASSWORD, Objects.requireNonNull(snapshot.child(username).child(Constants.PASSWORD).getValue()).toString());
                                        preferenceManager.putString(Constants.EMAIL, Objects.requireNonNull(snapshot.child(username).child(Constants.EMAIL).getValue()).toString());
                                        preferenceManager.putString(Constants.CITY, Objects.requireNonNull(snapshot.child(username).child(Constants.CITY).getValue()).toString());
                                        preferenceManager.putString(Constants.LONGITUDE, Objects.requireNonNull(snapshot.child(username).child("location").child(Constants.LONGITUDE).getValue()).toString());
                                        preferenceManager.putString(Constants.LATITUDE, Objects.requireNonNull(snapshot.child(username).child("location").child(Constants.LATITUDE).getValue()).toString());
                                        loginbtn.setVisibility(View.VISIBLE);
                                        progressBar1.setVisibility(View.GONE);


                                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), Language.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Please enter valid password", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(LoginActivity.this, "Please do registration first", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                mAuth.signInWithEmailAndPassword(username, pass).addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        loginbtn.setVisibility(View.VISIBLE);
                                        progressBar1.setVisibility(View.GONE);
                                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();

                                        startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                                    } else {
                                        loginbtn.setVisibility(View.VISIBLE);
                                        progressBar1.setVisibility(View.GONE);
                                        Toast.makeText(LoginActivity.this, "Login Error : " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            });
        }


    private boolean validateEmail() {
        String email1 = Objects.requireNonNull(usernameoremail.getEditText()).getText().toString().trim();
        return Patterns.EMAIL_ADDRESS.matcher(email1).matches();
    }

    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
        alertDialog.setTitle("Exit App");
        alertDialog.setMessage("Do you want to exit app?");
        alertDialog.setPositiveButton("Yes", (dialogInterface, i) -> finishAffinity());
        alertDialog.setNegativeButton("No", (dialogInterface, i) -> dialogInterface.dismiss());
        alertDialog.show();
    }
}