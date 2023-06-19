package com.example.trackforsafe.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.trackforsafe.R;
import com.example.trackforsafe.utilities.Constants;
import com.example.trackforsafe.utilities.PreferenceManager;

public class Language extends AppCompatActivity implements View.OnClickListener {
TextView Hindi,English,Gujrati;
PreferenceManager preferenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        Hindi = findViewById(R.id.Hindi);
        English = findViewById(R.id.English);
        Gujrati= findViewById(R.id.Gujrati);
        Hindi.setOnClickListener(this);
        Gujrati.setOnClickListener(this);
        English.setOnClickListener(this);
        preferenceManager = new PreferenceManager(getApplicationContext());


    }

    @Override
    public void onClick(View v) {
        if(v == Hindi){
            preferenceManager.putLanguage(Constants.LANGUAGE,"Hindi");
            Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
            startActivity(intent);
        }else if (v == Gujrati){
            preferenceManager.putLanguage(Constants.LANGUAGE,"Guj");
            Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
            startActivity(intent);
        }else if(v == English){
            preferenceManager.putLanguage(Constants.LANGUAGE,"English");
            Intent intent = new Intent(getApplicationContext(),WelcomeActivity.class);
            startActivity(intent);
        }
    }
}