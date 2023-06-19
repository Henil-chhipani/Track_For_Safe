package com.example.trackforsafe.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.trackforsafe.R;
import com.github.drjacky.imagepicker.ImagePicker;
import com.github.drjacky.imagepicker.constant.ImageProvider;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;


import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;


public class RegisterActivity extends AppCompatActivity {


    TextInputLayout name, phone, email, password, repassword;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    TextView invalidpass, invalidemail, invalidphone, haveaccount, city, lon, lat;
    Button registerbtn, get_location;
    ProgressBar progressBar2;

        private FusedLocationProviderClient fusedLocationClient;
    Uri imgUri;
    CircleImageView profile;
    FloatingActionButton changeprofile;
    FirebaseAuth mAuth;
    StorageReference storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://track-for-safe.appspot.com");
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://track-for-safe-default-rtdb.firebaseio.com/");

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[@#$%^&+=])" +     // at least 1 special character
                    "(?=\\S+$)" +            // no white spaces
                    ".{8,}" +                // at least 8 characters
                    "$");

//    public static final String MSG = "com.example.loginregister.INFO";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        profile = findViewById(R.id.profile_image);
        changeprofile = findViewById(R.id.uploadbtn);
        name = findViewById(R.id.nameLayout);
        phone = findViewById(R.id.phonenumberLayout);
        invalidphone = findViewById(R.id.invalidephone);
        email = findViewById(R.id.emailforforgotLayout);
        invalidemail = findViewById(R.id.invalidemail);
        password = findViewById(R.id.passwordLayout);
        invalidpass = findViewById(R.id.invalidpass);
        repassword = findViewById(R.id.confpasswordLayout);
        registerbtn = findViewById(R.id.registerbutton);
        haveaccount = findViewById(R.id.login);
        progressBar2 = findViewById(R.id.progressBar2);
        mAuth = FirebaseAuth.getInstance();
        get_location = findViewById(R.id.get_location);
        city = findViewById(R.id.city);
        lat = findViewById(R.id.lat);
        lon = findViewById(R.id.lon);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        // -------------------------------code for choosing profile image------------------------------------------------------------------
        ActivityResultLauncher<Intent> launcher =
                registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                    if (result.getResultCode() == RESULT_OK) {
                        imgUri = Objects.requireNonNull(result.getData()).getData();
                        profile.setImageURI(imgUri);
                    }
                });
        changeprofile.setOnClickListener(view -> ImagePicker.Companion.with(this)
                .crop()
                .cropOval()
                .maxResultSize(512, 512, true)
                .provider(ImageProvider.BOTH) //Or bothCameraGallery()
                .createIntentFromDialog(new Function1() {
                    public Object invoke(Object var1) {
                        this.invoke((Intent) var1);
                        return Unit.INSTANCE;
                    }

                    public void invoke(@NotNull Intent it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        launcher.launch(it);
                    }
                }));
        // ----------------------------------------------------------------------------------------------------------------------

        // get location button code -----------------------------------------------

        get_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLocationSettings();
                requestLocationPermission();
            }
        });


        registerbtn.setOnClickListener(view -> {
            // get data whchich user fill
            final String name1 = Objects.requireNonNull(name.getEditText()).getText().toString().trim();
            final String phone1 = Objects.requireNonNull(phone.getEditText()).getText().toString().trim();
            final String email1 = Objects.requireNonNull(email.getEditText()).getText().toString().trim();
            final String password1 = Objects.requireNonNull(password.getEditText()).getText().toString().trim();
            final String repassword1 = Objects.requireNonNull(repassword.getEditText()).getText().toString().trim();
            final String city1 = Objects.requireNonNull(city.getText()).toString().trim();
            final String latitude1 = Objects.requireNonNull(lat.getText()).toString().trim();
            final String longitude1 = Objects.requireNonNull(lon.getText()).toString().trim();


            // check either of any information is empty or not ------------------
            if (name1.equals("") || phone1.equals("") || email1.equals("") || password1.equals("") || repassword1.equals("") || city1.equals("") || latitude1.equals("") || longitude1.equals("")) {
                Toast.makeText(this, "Please enter required fields", Toast.LENGTH_SHORT).show();
            }
            // password checking ---------------------------
            else if (!password1.equals(repassword1)) {
                Toast.makeText(this, "Password are not matching", Toast.LENGTH_SHORT).show();
            } else {
                if (!validatePassword() | !validatePhone() | !validateEmail()) {
                    return;
                } else {
                    // upload image to cloud storage ----------------------------
                    String image_name = phone1 + ".jpeg";
                    StorageReference imageRef = storageReference.child(image_name);
                    UploadTask uploadTask = imageRef.putFile(imgUri);


                    uploadTask.addOnFailureListener(e -> Toast.makeText(RegisterActivity.this, "Image Upload Error : " + e.getMessage(), Toast.LENGTH_SHORT).show()).addOnSuccessListener(taskSnapshot -> {

                    });
                    databaseReference.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.hasChild(phone1)) {
                                // check if phone no. already exist-------------------
                                Toast.makeText(RegisterActivity.this, "Phone Number is already registered go to longin", Toast.LENGTH_SHORT).show();
                            } else {
                                // adding user detail to database ----------------------------
                                registerbtn.setVisibility(View.GONE);
                                progressBar2.setVisibility(View.VISIBLE);

                                databaseReference.child("Users").child(phone1).child("Name").setValue(name1);
                                databaseReference.child("Users").child(phone1).child("Phone Number").setValue(phone1);
                                databaseReference.child("Users").child(phone1).child("Email").setValue(email1);
                                databaseReference.child("Users").child(phone1).child("Password").setValue(password1);
                                databaseReference.child("Users").child(phone1).child("City").setValue(city1);
                                databaseReference.child("Users").child(phone1).child("location").child("latitude").setValue(latitude1);
                                databaseReference.child("Users").child(phone1).child("location").child("longitude").setValue(longitude1);


                                finish();
                                mAuth.createUserWithEmailAndPassword(email1, password1)
                                        .addOnCompleteListener(RegisterActivity.this, task -> {
                                            if (task.isSuccessful()) {
                                                registerbtn.setVisibility(View.VISIBLE);
                                                progressBar2.setVisibility(View.GONE);
                                                Toast.makeText(RegisterActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                            } else {
                                                registerbtn.setVisibility(View.VISIBLE);
                                                progressBar2.setVisibility(View.GONE);
                                                Toast.makeText(RegisterActivity.this, "Registered Error : " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        haveaccount.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });
    }


    @SuppressLint("SetTextI18n")
    private boolean validatePassword() {
        String password1 = Objects.requireNonNull(password.getEditText()).getText().toString().trim();
        if (!PASSWORD_PATTERN.matcher(password1).matches()) {
            invalidpass.setText("Password is too weak");
            return false;
        } else {
            invalidpass.setText("");
            return true;
        }
    }


    @SuppressLint("SetTextI18n")
    private boolean validateEmail() {
        String email1 = Objects.requireNonNull(email.getEditText()).getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
            invalidemail.setText("Please enter valid email address");
            return false;
        } else {
            invalidemail.setText("");
            return true;
        }
    }

    @SuppressLint("SetTextI18n")
    private boolean validatePhone() {
        String phone1 = Objects.requireNonNull(phone.getEditText()).getText().toString().trim();
        if (phone1.length() < 10) {
            invalidphone.setText("Please enter valid phone number");
            return false;
        } else {
            invalidphone.setText("");
            return true;
        }
    }

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

    private void getLocation() {
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

        }else {
            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                double latitude = location.getLatitude();
                                double longitude = location.getLongitude();
                            String cityName = getCityName(latitude, longitude);

                                city.setText(cityName);
                                lat.setText(latitude +"");
                                lon.setText(longitude+"");
                            } else {
                                // Location is null. Handle accordingly.
//                                checkLocationSettings();
                                Toast.makeText(RegisterActivity.this, "Please turn on the location of your device and retry", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }








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



    // code for turn on location show dialog box

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
                Toast.makeText(RegisterActivity.this, "Loation is on", Toast.LENGTH_SHORT).show();
//                getLocation();
            }
        });

        task.addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ApiException) {
                    ApiException apiException = (ApiException) e;
                    if (apiException.getStatusCode() == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                        // Location settings are not satisfied. Show the user a dialog to enable location
                        try {
                            ResolvableApiException resolvable = (ResolvableApiException) apiException;
                            resolvable.startResolutionForResult(RegisterActivity.this, LOCATION_PERMISSION_REQUEST_CODE);
                        } catch (IntentSender.SendIntentException sendEx) {
                            // Ignore the error
                        }
                    }
                }
            }
        });
    }


}