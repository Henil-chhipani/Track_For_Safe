package com.example.trackforsafe.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trackforsafe.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class OTPActivity extends AppCompatActivity {

    EditText num1, num2, num3, num4, num5, num6;
    Button verifyotpbtn;
    ProgressBar progressBar4;
    TextView noticetxt, resendOTP;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String getOTPbackend;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpactivity);

        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        num5 = findViewById(R.id.num5);
        num6 = findViewById(R.id.num6);
        verifyotpbtn = findViewById(R.id.verifyotpbtn);
        noticetxt = findViewById(R.id.receivemessagetxt);
        resendOTP = findViewById(R.id.resendotp);
        progressBar4 = findViewById(R.id.progressBar4);

        String txt = getString(R.string.otptext2);

        Intent intent = getIntent();
        String mno = intent.getStringExtra("mobile");
        String countrycode = intent.getStringExtra("countrycode");
        getOTPbackend = intent.getStringExtra("backendotp");
        noticetxt.setText(txt + " +" + countrycode + " " + mno);

        verifyotpbtn.setOnClickListener(view -> {
            if(!num1.getText().toString().trim().isEmpty() &&!num2.getText().toString().trim().isEmpty()
                    && !num3.getText().toString().trim().isEmpty() && !num4.getText().toString().trim().isEmpty()
                    && !num5.getText().toString().trim().isEmpty() && !num6.getText().toString().trim().isEmpty()){
                verifyotpbtn.setVisibility(View.GONE);
                progressBar4.setVisibility(View.VISIBLE);
                String enteredotp = num1.getText().toString() + num2.getText().toString() + num3.getText().toString()
                        + num4.getText().toString() + num5.getText().toString() + num6.getText().toString();

                if(getOTPbackend != null){
                    verifyotpbtn.setVisibility(View.INVISIBLE);
                    progressBar4.setVisibility(View.VISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(getOTPbackend,enteredotp);
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential).addOnCompleteListener(task -> {

                        verifyotpbtn.setVisibility(View.VISIBLE);
                        progressBar4.setVisibility(View.GONE);
                        if (task.isSuccessful()){
                            Intent intent1 = new Intent(getApplicationContext(),WelcomeActivity.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent1);
                        }
                        else{
                            Toast.makeText(OTPActivity.this, "Enter correct OTP", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
            else{
                Toast.makeText(OTPActivity.this, "Please enter all number", Toast.LENGTH_SHORT).show();
            }
        });

        numberotpmove();

        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(OTPActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String newverificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                getOTPbackend = newverificationId;
                Toast.makeText(OTPActivity.this, "OTP Resend Successfully", Toast.LENGTH_SHORT).show();
            }
        };

        resendOTP.setOnClickListener(view -> {
            String mnoforresend = "+" + countrycode + mno;
            PhoneAuthOptions options =
                    PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber(mnoforresend)       // Phone number to verify
                            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                            .setActivity(OTPActivity.this)
                            .setCallbacks(mCallbacks)
                            .build();
            PhoneAuthProvider.verifyPhoneNumber(options);
        });
    }

    private void numberotpmove() {
        num1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    num2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        num2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    num3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        num3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    num4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        num4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    num5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        num5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(!charSequence.toString().trim().isEmpty()){
                    num6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}