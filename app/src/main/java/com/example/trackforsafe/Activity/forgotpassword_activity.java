package com.example.trackforsafe.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.trackforsafe.R;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;


public class forgotpassword_activity extends AppCompatActivity {

    TextInputLayout email;
    TextView invalidemail;
    Button continuebtn;

    private FirebaseAuth mAuth;

    @SuppressLint("IntentReset")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotpassword);

        email = findViewById(R.id.emailforforgotLayout);
        invalidemail = findViewById(R.id.invalidemail1);
        continuebtn = findViewById(R.id.continuebutton);
        mAuth = FirebaseAuth.getInstance();

        continuebtn.setOnClickListener(view -> {
            String email1 = Objects.requireNonNull(email.getEditText()).getText().toString().trim();
            if(email1.equals("")) {
                Toast.makeText(this, "Please enter require field", Toast.LENGTH_SHORT).show();
            }
            else{
                if(validateEmail()){
                        mAuth.sendPasswordResetEmail(email1).addOnCompleteListener(task -> {
                            if(task.isSuccessful()){
                                Toast.makeText(forgotpassword_activity.this, "Check your email for password", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(forgotpassword_activity.this,LoginActivity.class));
                                finish();
                            }
                            else{
                                Toast.makeText(forgotpassword_activity.this, "Error : " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                }
                else{
                    Toast.makeText(this, "Your email address is not exists in our database", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private boolean validateEmail() {
        String emailforpass = Objects.requireNonNull(email.getEditText()).getText().toString().trim();
        if(!Patterns.EMAIL_ADDRESS.matcher(emailforpass).matches()){
            invalidemail.setText("Please enter valid email address");
            return false;
        }
        else{
            invalidemail.setText("");
            return true;
        }
    }
}