package com.example.trackforsafe.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.trackforsafe.Modal.Forecast_detailsRVModal;
import com.example.trackforsafe.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Forecast_detailsRVAdapter extends RecyclerView.Adapter<Forecast_detailsRVAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Forecast_detailsRVModal> forecast_detailsRVModalArrayList;

    public Forecast_detailsRVAdapter(Context context, ArrayList<Forecast_detailsRVModal> forecast_detailsRVModalArrayList) {
        this.context = context;
        this.forecast_detailsRVModalArrayList = forecast_detailsRVModalArrayList;
    }

    @NonNull
    @Override
    public Forecast_detailsRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.forecast_details_rv_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Forecast_detailsRVAdapter.ViewHolder holder, int position) {
        Forecast_detailsRVModal modal = forecast_detailsRVModalArrayList.get(position);
        holder.temperature.setText(modal.getTemperature() + "°c");
        holder.windSpeed.setText(modal.getWindSpeed() + "Km/h");
        Glide.with(context /* context */)
                .load("https:".concat(modal.getIcon()))
                .fitCenter()
                .into(holder.condition_info);
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        SimpleDateFormat output = new SimpleDateFormat("hh:mm aa");
        try {
            Date t = input.parse(modal.getTime());
            holder.timeTV.setText(output.format(t));
        }catch (ParseException e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return forecast_detailsRVModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView timeTV, temperature, windSpeed;
        private ImageView condition_info;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            timeTV = itemView.findViewById(R.id.timeTV);
            temperature = itemView.findViewById(R.id.temperature);
            windSpeed = itemView.findViewById(R.id.windSpeed);
            condition_info = itemView.findViewById(R.id.condition_info);
        }
    }
}
