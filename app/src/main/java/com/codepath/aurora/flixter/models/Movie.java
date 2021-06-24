package com.codepath.aurora.flixter.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel // annotation indicates class is Parcelable
public class Movie {

    // fields must be public for parceler
    String backdropPath;
    String posterPath;
    String title;
    String overview;
    Double voteAverage;


    // no-arg, empty constructor required for Parceler
    public Movie () {}

    public Movie(JSONObject jsonObject) throws JSONException {
        //Converting JSON object
        backdropPath = jsonObject.getString("backdrop_path");
        posterPath  = jsonObject.getString("poster_path");
        title  = jsonObject.getString("title");
        overview  = jsonObject.getString("overview");
        voteAverage = jsonObject.getDouble("vote_average");
    }

    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        //Turn an JSONArray into a List of Movies
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i < movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
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
