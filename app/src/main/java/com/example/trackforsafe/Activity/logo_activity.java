package com.example.trackforsafe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.trackforsafe.R;
import com.example.trackforsafe.utilities.PreferenceManager;


public class logo_activity extends AppCompatActivity {

private PreferenceManager preferenceManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferenceManger = new PreferenceManager(getApplicationContext());
//        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_logo);
        Handler handler = new Handler();
        handler.postDelayed(() -> {
//            if(!preferenceManger.getBoolean("sing_in"))
                Intent intent;
                intent = new Intent(logo_activity.this, LoginActivity.class);
                preferenceManger.putBoolean("sing_in",true);
                startActivity(intent);
                finish();
//            }else{
//                Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
//                startActivity(intent);
//                finish();
//                }
        },2000);
    }
}