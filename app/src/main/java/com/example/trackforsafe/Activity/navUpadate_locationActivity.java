package com.example.trackforsafe.Activity;

import static androidx.constraintlayout.motion.widget.Debug.getLocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.trackforsafe.R;
import com.example.trackforsafe.utilities.Constants;
import com.example.trackforsafe.utilities.PreferenceManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class navUpadate_locationActivity extends AppCompatActivity {
    PreferenceManager preferenceManager;
    Button update_location;
    TextView city_2, longitude_2, latitude_2, latitude_1, longitude_1, city_1;
    private FusedLocationProviderClient fusedLocationClient;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav_upadate_location);
        update_location = findViewById(R.id.update_location);
        city_1 = findViewById(R.id.city_1);
        city_2 = findViewById(R.id.city_2);
        longitude_1 = findViewById(R.id.longitude_1);
        longitude_2 = findViewById(R.id.longitude_2);
        latitude_1 = findViewById(R.id.lattitude_1);
        latitude_2 = findViewById(R.id.lattitude_2);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        preferenceManager = new PreferenceManager(getApplicationContext());
        String username = preferenceManager.getString(Constants.PHONE_NUMBER);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://track-for-safe-default-rtdb.firebaseio.com/");


        getDatabaselocation(databaseReference, username);
        getNewLocation();

        update_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String update_latitude = latitude_1.getText().toString(); // Get the text from the TextView
                String update_longitude = longitude_1.getText().toString();
                String update_city = city_1.getText().toString();

// Extract the number from the string
                String[] parts_latitude = update_latitude.split(":");
                String latitudeString = parts_latitude[1].trim();
                String[] parts_longitude = update_longitude.split(":");
                String longitudeString = parts_longitude[1].trim();
                String[] parts_city = update_city.split(":");
                String cityString = parts_city[1].trim();

                Map<String,Object> updates_longitude= new HashMap<>();
                updates_longitude.put("longitude",longitudeString);
                Map<String,Object> updates_latitude = new HashMap<>();
                updates_latitude.put("latitude",latitudeString);
                Map<String,Object> updates_city = new HashMap<>();
                updates_city.put("City",cityString);
                databaseReference.child("Users").child(username).child("location").updateChildren(updates_longitude);
                databaseReference.child("Users").child(username).child("location").updateChildren(updates_latitude);
                databaseReference.child("Users").child(username).updateChildren(updates_city);

                getDatabaselocation(databaseReference,username);
                preferenceManager.putString(Constants.CITY,cityString);
                preferenceManager.putString(Constants.LATITUDE,latitudeString);
                preferenceManager.putString(Constants.LONGITUDE,longitudeString);

            }
        });


    }

    private void getNewLocation() {
        checkLocationSettings();
        requestLocationPermission();

        //        Toast.makeText(this, "getLocation() method called successfully ", Toast.LENGTH_SHORT).show();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

        } else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {

                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();
                                String cityName = getCityName(latitude, longitude);

                                city_1.setText("city: "+cityName);
                                latitude_1.setText("latitude: "+latitude);
                                longitude_1.setText("longitude: "+longitude);
                            } else {
                                // Location is null. Handle accordingly.
//                                checkLocationSettings();
                                Toast.makeText(navUpadate_locationActivity.this, "Please turn on the location of your device and retry", Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }
    }

    // -------------------------------------------------------------------------------------------------------------
    private String getCityName(double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses;
        String cityName = "";

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);
                cityName = address.getLocality();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityName;
    }

    // -------------------------------------------------------------------------------------------------------------
    private void requestLocationPermission() {
//        Toast.makeText(this, "requestLocationPermission() method called successfully ", Toast.LENGTH_SHORT).show();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getLocation();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        Toast.makeText(this, "requestLocationPermissionResult() method called successfully ", Toast.LENGTH_SHORT).show();
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLocation();
            } else {
                // Permission denied. Handle accordingly.
                Toast.makeText(this, "please allow app to access your location it is necessary", Toast.LENGTH_SHORT).show();
            }
        }
    }
//-------------------------------------------------------------------------------------------------------------------

    private void checkLocationSettings() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest);

        Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(this)
                .checkLocationSettings(builder.build());

        task.addOnSuccessListener(this, new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                // Location settings satisfied. Proceed with location-related operations
                Toast.makeText(navUpadate_locationActivity.this, "Loation is on", Toast.LENGTH_SHORT).show();
            }
        });
    }

        //----------------------------------------------------------------------------------------------------------------
        private void getDatabaselocation(DatabaseReference databaseReference, String username){
            databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String latitude = Objects.requireNonNull(snapshot.child(username).child("location").child(Constants.LATITUDE).getValue()).toString();
                        String longitude = Objects.requireNonNull(snapshot.child(username   ).child("location").child(Constants.LONGITUDE).getValue()).toString();
                        String city = Objects.requireNonNull(snapshot.child(username).child(Constants.CITY).getValue().toString());
                        // Process the latitude and longitude values as needed
                        // For example, you can store them in variables, display them, or perform calculations
                        latitude_2.setText("Latitude: " + latitude);
                        longitude_2.setText("Longitude: " + longitude);
                        city_2.setText("City: " + city);


                    } else {
                        Log.d("Location", "Location data not found for user: " + username);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.d("Location", "Database error: " + error.getMessage());
                }
            });
        }


    }