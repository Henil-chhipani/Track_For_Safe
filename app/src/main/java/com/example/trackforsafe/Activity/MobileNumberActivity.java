package com.example.trackforsafe.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.trackforsafe.R;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.hbb20.CountryCodePicker;

import java.util.concurrent.TimeUnit;


public class MobileNumberActivity extends AppCompatActivity {

    Button sendOTP;
    EditText enternumber;
    ProgressBar progressBar3;
    CountryCodePicker countryCodePicker;
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    String smsCode = "123456";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number);

        countryCodePicker = findViewById(R.id.ccp);
        enternumber = findViewById(R.id.phonefortop);
        sendOTP = findViewById(R.id.sendotpbtn);
        String countrycode = countryCodePicker.getSelectedCountryCode();
        progressBar3 = findViewById(R.id.progressBar3);
        mAuth = FirebaseAuth.getInstance();

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential credential) {
                sendOTP.setVisibility(View.VISIBLE);
                progressBar3.setVisibility(View.GONE);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                sendOTP.setVisibility(View.VISIBLE);
                progressBar3.setVisibility(View.GONE);
                Toast.makeText(MobileNumberActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                sendOTP.setVisibility(View.VISIBLE);
                progressBar3.setVisibility(View.GONE);
                Intent intent = new Intent(getApplicationContext(),OTPActivity.class);
                intent.putExtra("mobile",enternumber.getText().toString());
                intent.putExtra("countrycode",countrycode);
                intent.putExtra("backendotp",verificationId);
                startActivity(intent);
            }
        };

        sendOTP.setOnClickListener(view -> {
            if(!enternumber.getText().toString().trim().isEmpty()){
                if((enternumber.getText().toString().trim()).length() == 10) {
                    String mno = "+" + countrycode + enternumber.getText().toString();
                    sendOTP.setVisibility(View.INVISIBLE);
                    progressBar3.setVisibility(View.VISIBLE);
//                    FirebaseAuthSettings firebaseAuthSettings = mAuth.getFirebaseAuthSettings();
//                    firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(mno, smsCode);
                    PhoneAuthOptions options =
                            PhoneAuthOptions.newBuilder(mAuth)
                                    .setPhoneNumber(mno)       // Phone number to verify
                                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                    .setActivity(MobileNumberActivity.this)
                                    .setCallbacks(mCallbacks)
                                    .build();
                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
                else{
                    Toast.makeText(MobileNumberActivity.this, "Please enter correct number", Toast.LENGTH_SHORT).show();
                }
            }
            else{
                Toast.makeText(MobileNumberActivity.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
            }
        });
    }
}