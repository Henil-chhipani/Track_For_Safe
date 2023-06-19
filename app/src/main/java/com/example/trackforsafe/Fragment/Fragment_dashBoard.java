package com.example.trackforsafe.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.trackforsafe.Adapter.Forecast_detailsRVAdapter;
import com.example.trackforsafe.Modal.Forecast_detailsRVModal;
import com.example.trackforsafe.R;
import com.example.trackforsafe.utilities.Constants;
import com.example.trackforsafe.utilities.PreferenceManager;
import com.google.android.material.textfield.TextInputEditText;

import org.checkerframework.checker.units.qual.A;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;


public class Fragment_dashBoard extends Fragment {
    // https://api.openweathermap.org/data/2.5/weather?q=Rajkot,india&APPID=db352f9bbced2e9349cb6f3faedd4b7c
    private ConstraintLayout ConLayout;
    private ProgressBar progressbar;
    private TextView city_name, temperature_tv, condition_info;
    private ImageView weather_icon, bg_imageView, search_btn;
    private TextInputEditText search_box;
    private RecyclerView forecast_detailsRV;

    private PreferenceManager preferenceManager;
    private String cityName;

    private ArrayList<Forecast_detailsRVModal> forecast_detailsRVModalArrayList;
    private Forecast_detailsRVAdapter forecast_detailsRVAdapter;
//    ViewGroup root;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_dash_board, container, false);

//        root = (ViewGroup) inflater.inflate(R.layout.fragment_dash_board, null);
        ConLayout = root.findViewById(R.id.ConLayout);
        progressbar = root.findViewById(R.id.progressbar);
        city_name = root.findViewById(R.id.city_name);
        temperature_tv = root.findViewById(R.id.temperature);
        condition_info = root.findViewById(R.id.condition_info);
        weather_icon = root.findViewById(R.id.weather_icon);
        bg_imageView = root.findViewById(R.id.bg_imageView);
        search_btn = root.findViewById(R.id.search_btn);
        search_box = root.findViewById(R.id.search_box);
        preferenceManager = new PreferenceManager(requireContext());
        forecast_detailsRV = root.findViewById(R.id.forecast_detailsRV);
        forecast_detailsRVModalArrayList = new ArrayList<>();
        forecast_detailsRVAdapter = new Forecast_detailsRVAdapter(getContext(), forecast_detailsRVModalArrayList);
        forecast_detailsRV.setAdapter(forecast_detailsRVAdapter);


        cityName = preferenceManager.getString(Constants.CITY);
        getWeatherInfo(cityName,getContext());

        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = search_box.getText().toString();
                if (city.isEmpty()) {
                    Toast.makeText(root.getContext(), "Enter City name", Toast.LENGTH_SHORT).show();
                } else {
                    city_name.setText(city);
                    getWeatherInfo(city,getContext());
                }
            }
        });
        return root;
    }

    private void getWeatherInfo(String cityName, Context context) {


        String url = "https://api.weatherapi.com/v1/forecast.json?key=a18bbe50002747e1add194930233105&q="+cityName+"&days=1&aqi=yes&alerts=yes" ;

        city_name.setText(cityName);
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressbar.setVisibility(View.GONE);

                forecast_detailsRVModalArrayList.clear();

                try {
                    String temperature = response.getJSONObject("current").getString("temp_c");
                    temperature_tv.setText(temperature+"°c");
                    int isDay = response.getJSONObject("current").getInt("is_day");
                    String condition = response.getJSONObject("current").getJSONObject("condition").getString("text");
                    condition_info.setText(condition);
                    String condition_icon = response.getJSONObject("current").getJSONObject("condition").getString("icon");
                    Glide.with(context).load("http:".concat(condition_icon)).into(weather_icon);

                    JSONObject forecast_obj = response.getJSONObject("forecast");
                    JSONObject forecastO = forecast_obj.getJSONArray("forecastday").getJSONObject(0);
                    JSONArray hourArray = forecastO.getJSONArray("hour");

                    for (int i = 0; i < hourArray.length(); i++) {
                        JSONObject hourObj = hourArray.getJSONObject(i);
                        String time = hourObj.getString("time");
                        String tempr = hourObj.getString("temp_c");
                        String img = hourObj.getJSONObject("condition").getString("icon");
                        String win_s = hourObj.getString("wind_kph");
                        forecast_detailsRVModalArrayList.add(new Forecast_detailsRVModal(time, tempr, img, win_s));
                    }
                    forecast_detailsRVAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Enter valid city name", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}