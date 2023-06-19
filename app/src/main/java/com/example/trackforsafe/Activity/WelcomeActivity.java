package com.example.trackforsafe.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.drawerlayout.widget.DrawerLayout;

//import com.example.trackforsafe.databinding.ActivityWelcomeBinding;
import com.example.trackforsafe.R;
import com.example.trackforsafe.utilities.Constants;
import com.example.trackforsafe.utilities.PreferenceManager;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;


public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    TextView TVhome,TVguideline,TVupdate_location,TVsend_alert,TVask_for_help,TVlog_out;

    private DatabaseReference mDatabase;

    Toolbar toolbar;
    PreferenceManager preferenceManager;

    CardView nav_home, nav_guideLine, nav_updateLocation, nav_sendAlert, nav_askForHelp, nav_logOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        nav_home = findViewById(R.id.nav_home);
        nav_guideLine = findViewById(R.id.nav_guideLine);
        nav_updateLocation = findViewById(R.id.nav_updateLocation);
        nav_sendAlert = findViewById(R.id.nav_sendAlert);
        nav_askForHelp = findViewById(R.id.nav_askForHelp);
        nav_logOut = findViewById(R.id.nav_logOut);
        TVhome = findViewById(R.id.TVhome);
        TVguideline = findViewById(R.id.TVguideline);
        TVask_for_help  = findViewById(R.id.TVask_for_help );
        TVupdate_location = findViewById(R.id.TVupdate_location);
        TVsend_alert = findViewById(R.id.TVsend_alert);
        TVlog_out = findViewById(R.id.TVlog_out);
        preferenceManager = new PreferenceManager(getApplicationContext());
if(preferenceManager.getLanguage(Constants.LANGUAGE)!=null){
        if(preferenceManager.getLanguage(Constants.LANGUAGE)=="Hindi"){
            TVhome.setText("होम");
            TVguideline.setText("दिशा निर्देशों");
            TVupdate_location.setText("location बदले");
            TVask_for_help.setText("मदद की गुहार");
            TVsend_alert.setText("ख़तरे की सूचना");
            TVlog_out.setText("बहार निकले");
        } else if (preferenceManager.getLanguage(Constants.LANGUAGE)=="Guj") {
            TVhome.setText("હોમ");
            TVguideline.setText("માર્ગદર્શિકા");
            TVupdate_location.setText("location બદલો");
            TVask_for_help.setText("મદદ માટે પોકાર");
            TVsend_alert.setText("જોખમની સૂચના");
            TVlog_out.setText("બહાર જાઓ");
        } else if (preferenceManager.getLanguage(Constants.LANGUAGE)=="English") {
            TVhome.setText("Home");
            TVguideline.setText("     Guidelines     ");
            TVupdate_location.setText("Update location");
            TVask_for_help.setText("ask for help");
            TVsend_alert.setText("send alert");
            TVlog_out.setText("Log out");
        }
}else{
    Toast.makeText(this, "Language is not set", Toast.LENGTH_SHORT).show();
        }

        // setting on click listener:
        nav_home.setOnClickListener(this);
        nav_guideLine.setOnClickListener(this);
        nav_askForHelp.setOnClickListener(this);
        nav_updateLocation.setOnClickListener(this);
        nav_sendAlert.setOnClickListener(this);
        nav_logOut.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == nav_home) {
            // this go to the home/dashboard section which can have weather related information according to location
            Intent intent = new Intent(getApplicationContext(), navHomeActivity.class);
            startActivity(intent);
            finish();
        } else if (v == nav_guideLine) {
            Intent intent = new Intent(getApplicationContext(), navGuidelineActivity.class);
            startActivity(intent);
            finish();
            // through the backend we can set some gide line
        }else if (v == nav_updateLocation) {
            Intent intent = new Intent(getApplicationContext(), navUpadate_locationActivity.class);
            startActivity(intent);
            finish();
            // update location in this we can use get location and update in firebase
        }else if (v == nav_sendAlert) {
                // in backend we want add alert section if user click it then his location red dot showing in backend
        }else if (v == nav_askForHelp) {
                // contact details from backend
        }else if (v == nav_logOut) {
            // log out logic

        }
        else {
            Toast.makeText(this, "otherthing", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onBackPressed() {
            super.onBackPressed();
    }
    // https://www.youtube.com/watch?v=BO1utHYhsms&ab_channel=WsCubeTech
}