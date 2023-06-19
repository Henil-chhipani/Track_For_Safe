package com.example.trackforsafe.Fragment;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.trackforsafe.Adapter.Forecast_detailsRVAdapter;
import com.example.trackforsafe.Modal.Forecast_detailsRVModal;
import com.example.trackforsafe.R;
import com.example.trackforsafe.utilities.Constants;
import com.example.trackforsafe.utilities.PreferenceManager;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.zip.Inflater;

import de.hdodenhof.circleimageview.CircleImageView;


public class Fragment_home extends Fragment {
    StorageReference storageReference ;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://track-for-safe-default-rtdb.firebaseio.com/");
    TextView name,city,email,phone;
    CircleImageView profile_image;
    PreferenceManager preferenceManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment__home, null);
        name = (TextView) root.findViewById(R.id.name);
        city = (TextView) root.findViewById(R.id.city);
        email = (TextView) root.findViewById(R.id.email);
        phone = (TextView) root.findViewById(R.id.phone);
        profile_image = root.findViewById(R.id.profile_image);
        preferenceManager = new PreferenceManager(root.getContext());

        name.setText(preferenceManager.getString(Constants.NAME));
        city.setText(preferenceManager.getString(Constants.CITY));
        email.setText(preferenceManager.getString(Constants.EMAIL));
        phone.setText(preferenceManager.getString(Constants.PHONE_NUMBER));
        String url =
                "https://firebasestorage.googleapis.com/v0/b/track-for-safe.appspot.com/o/"+preferenceManager.getString(Constants.PHONE_NUMBER)+".jpeg?alt=media&token=b55230d6-02aa-46ad-a00a-4121422c304b";
       storageReference =  FirebaseStorage.getInstance().getReferenceFromUrl("gs://track-for-safe.appspot.com/9510398098.jpeg");

        Glide.with(this /* context */)
                .load(url)
                .fitCenter()
                .into(profile_image);



        return root;
    }


}