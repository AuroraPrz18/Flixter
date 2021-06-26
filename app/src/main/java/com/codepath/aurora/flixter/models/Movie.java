package com.codepath.aurora.flixter.models;

import android.util.Log;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Headers;

@Parcel // annotation indicates class is Parcelable
public class Movie {

    // fields must be public for parceler
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    String key;
    Double voteAverage;

    //Where I'm going to save the keyYoutube (THIS IS NO GOOD PROGRAMMING - Have to change it later)
    static Map<Integer, String> keyYoutube = new HashMap<Integer, String>();
    int id;


    // no-arg, empty constructor required for Parceler
    public Movie () {}

    public Movie(JSONObject jsonObject, String apiKey) throws JSONException {
        //Converting JSON object
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath  = jsonObject.getString("poster_path");
        title  = jsonObject.getString("title");
        overview  = jsonObject.getString("overview");
        voteAverage = jsonObject.getDouble("vote_average");
        id= jsonObject.getInt("id");
        getItsYouTubeKey(id, apiKey);
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray, String apiKey) throws JSONException {
        //Turn an JSONArray into a List of Movies
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i < movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i), apiKey));
        }
        return movies;
    }

    public static void getItsYouTubeKey(int id, String apiKey){
        String URLMovie= "https://api.themoviedb.org/3/movie/"+id+"/videos?api_key="+ apiKey;


        //Instance of async HTTP client
        AsyncHttpClient client = new AsyncHttpClient();

        //Making a GET client request(which URL, callback with a response handler)
        client.get(URLMovie, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Headers headers, JSON json) {
                //Get the actual JSON object
                JSONObject jsonObject = json.jsonObject;
                //Get the information that is inside this JSON object (in this case it is an array)
                try {
                    JSONArray results = jsonObject.getJSONArray("results");
                    //Get the KEY
                    if(results.length()>0){
                            Movie.keyYoutube.put(id, results.getJSONObject(0).getString("key"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int i, Headers headers, String s, Throwable throwable) {
                Log.e("Movie", "onFailure", throwable );
            }
        });
    }


    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public int getId() {
        return id;
    }

    public String getKey() {
        return key;
    }

    public static String getKeyYoutube(int id) {
        return keyYoutube.get(id);
    }

    public String getBackdropPath(){
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }
}
