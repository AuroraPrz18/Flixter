package com.codepath.aurora.flixter;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.codepath.aurora.flixter.adapters.MovieAdapter;
import com.codepath.aurora.flixter.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    public String NOW_PLAYING_URL;
    public static final String TAG = "MainActivity";
    List<Movie> movies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView rvMovies = findViewById(R.id.rvMovies);

        NOW_PLAYING_URL= "https://api.themoviedb.org/3/movie/now_playing?api_key="+ getString(R.string.api_key_moviedb);
        movies = new ArrayList<>();

        //Create the adapter
        MovieAdapter movieAdapter = new MovieAdapter(this, movies);

        //Set the adapter on the recycler view
        rvMovies.setAdapter(movieAdapter);

        //Set a Layout Manager on the Recycler view
        rvMovies.setLayoutManager(new LinearLayoutManager(this));


        //Instance of async HTTP client
        AsyncHttpClient client = new AsyncHttpClient();

        //Making a GET client request(which URL, callback with a response handler)
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.i(TAG, "onSuccess");
                //Get the actual JSON object
                JSONObject jsonObject = json.jsonObject;
                //Get the information that is inside this JSON object (in this case it is an array)
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: "+results.toString());
                    //Get the list of movies
                    movies.addAll(Movie.fromJsonArray(results));
                    movieAdapter.notifyDataSetChanged();
                    Log.i(TAG, "Movies: "+movies.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json EXCEPTION", e);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.i(TAG, "onFailure",throwable );

            }
        });


    }
}