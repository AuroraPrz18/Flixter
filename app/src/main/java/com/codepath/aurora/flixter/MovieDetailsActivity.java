package com.codepath.aurora.flixter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codepath.aurora.flixter.databinding.ActivityMovieDetailsBinding;
import com.codepath.aurora.flixter.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {
    Movie movie; // movie to display

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // R.layout.activity_movie_details ->
        ActivityMovieDetailsBinding binding = ActivityMovieDetailsBinding.inflate(getLayoutInflater());

        // Layout of activity is stored in a special property called root
        View view = binding.getRoot();
        setContentView(view);

        // Uwrap the  movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for %s", movie.getTitle()));


        binding.tvTitleMD.setText(movie.getTitle());
        binding.tvOverviewMD.setText(movie.getOverview());
        float voteAverage = movie.getVoteAverage().floatValue();
        binding.rbVoteAverage.setRating(voteAverage / 2.0f);

        int radius = 30; //corner radius, higher value = more rounded
        int margin = 10; //crop margin, set to 0 for corners with no crop
        //Glide.with(this).load(movie.getBackdropPath()).centerCrop().transform(new RoundedCornersTransformation(radius, margin)).into(ivPoster);
        Glide.with(this).load(movie.getBackdropPath()).into(binding.ivPosterMD);
    }
}