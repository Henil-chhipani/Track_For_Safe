package com.example.trackforsafe.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.trackforsafe.Adapter.News_feedAdapter;
import com.example.trackforsafe.Modal.Forecast_detailsRVModal;
import com.example.trackforsafe.Modal.News_feedModal;
import com.example.trackforsafe.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.grpc.internal.JsonParser;


public class Fragment_news extends Fragment {

    private ProgressBar progressBar;
    private News_feedAdapter news_feedAdapter;
    private RecyclerView newsdata;
    private ArrayList<News_feedModal> newsfeed_modalArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        progressBar = view.findViewById(R.id.progressbar);
        newsdata = view.findViewById(R.id.newsdata);
        newsfeed_modalArrayList = new ArrayList<>();
        news_feedAdapter = new News_feedAdapter(getContext(), newsfeed_modalArrayList);
        newsdata.setAdapter(news_feedAdapter);
//        getnews(getContext());
        return view;
    }

//    public void getnews(Context context) {
//        String apikey = "d155a582217d4d5586bbd9a231d95320";
//        String url = "https://newsapi.org/v2/top-headlines?apiKey="+apikey+"&country=in";
//
////        String url = "http://api.mediastack.com/v1/news?access_key=20bddd7fc463dbc7e3067c292ecc8c91";
//
//        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
//
//        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                progressBar.setVisibility(View.GONE);
//                newsfeed_modalArrayList.clear();
//
//                try {
//                    JSONArray article = response.getJSONArray("articles");
//                    String ok = response.getString("status");
//                    Toast.makeText(context, ok, Toast.LENGTH_SHORT).show();
//                    for (int i = 0; i < article.length(); i++) {
//                        JSONObject dataobj = article.getJSONObject(i);
//                        String source = dataobj.getJSONObject("source").getString("name");
////                        String source = dataobj.getString("source");
//                        String title = dataobj.getString("title");
//                        String description = dataobj.getString("description");
//                        String url = dataobj.getString("url");
//                        newsfeed_modalArrayList.add(new News_feedModal(title, description, url, source));
//                    }
//                    news_feedAdapter.notifyDataSetChanged();
//
//                } catch (JSONException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(context, "Error occurred: " + error.getMessage(), Toast.LENGTH_SHORT).show();
//                Log.d("error1", "onErrorResponse: "+error.getMessage());
//            }
//
//        });
//        requestQueue.add(jsonObjectRequest1);
//
//
//    }

}