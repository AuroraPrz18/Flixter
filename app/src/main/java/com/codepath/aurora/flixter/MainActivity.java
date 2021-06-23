package com.codepath.aurora.flixter;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.codepath.aurora.flixter.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {
    public static final String NOW_PLAYING_URL="https://api.themoviedb.org/3/movie/now_playing?api_key=8aaedcad6d727251424a3ce5b524309f";
    //a07e22bc18f5cb106bfe4cc1f83ad8ed
    public static final String TAG = "MainActivity";
    List<Movie> movies;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create the adapter

        //Set the adapter on the recycler view

        //Set a Layout Manager on the Recycler view



        //Instance of async HTTP client
        AsyncHttpClient client = new AsyncHttpClient();
        Log.d(TAG, "hello");
        //Making a GET client request(which URL, callback with a response handler)
        client.get(NOW_PLAYING_URL, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                //Get the actual JSON object
                JSONObject jsonObject = json.jsonObject;
                //Get the information that is inside this JSON object (in this case it is an array)
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    Log.i(TAG, "Results: "+results.toString());
                    //Get the list of movies
                    movies = Movie.fromJsonArray(results);
                    Log.i(TAG, "Movies: "+movies.size());
                } catch (JSONException e) {
                    Log.e(TAG, "Hit json EXCEPTION", e);
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.d(TAG, "onFailure",throwable );

            }
        });


    }
}