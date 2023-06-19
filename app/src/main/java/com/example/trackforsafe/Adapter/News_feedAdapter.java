package com.example.trackforsafe.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trackforsafe.Modal.News_feedModal;
import com.example.trackforsafe.R;

import java.util.ArrayList;

public class News_feedAdapter extends RecyclerView.Adapter<News_feedAdapter.ViewHolder> {
    private Context context;
    private ArrayList<News_feedModal> news_feedModalArrayList;

    public News_feedAdapter(Context context, ArrayList<News_feedModal> news_feedModalArrayList){
            this.context=context;
            this.news_feedModalArrayList = news_feedModalArrayList;
    }


    @NonNull
    @Override
    public News_feedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_feed_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull News_feedAdapter.ViewHolder holder, int position) {
        News_feedModal modal = news_feedModalArrayList.get(position);
        holder.title.setText(modal.getTitle()+"");
        holder.source.setText(modal.getSource()+"");
        holder.description.setText(modal.getDescription());
        String link = modal.getUrl();
        holder.url.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotourl(link);
            }
        });

    }

    public void gotourl(String link){
         Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title,source,description;
        Button url;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            source = itemView.findViewById(R.id.source);
            description = itemView.findViewById(R.id.disc);
            url = itemView.findViewById(R.id.url);
        }
    }
}
