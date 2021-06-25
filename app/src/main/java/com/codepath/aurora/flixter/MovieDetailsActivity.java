package com.codepath.aurora.flixter;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codepath.aurora.flixter.models.Movie;

import org.parceler.Parcels;

public class MovieDetailsActivity extends AppCompatActivity {
    Movie movie; // movie to display

    TextView tvTitle;
    TextView tvOverview;
    RatingBar rbVoteAverage;
    ImageView ivPoster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        // Uwrap the  movie passed in via intent, using its simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for %s", movie.getTitle()));

        //resolve the view objects
        tvTitle = (TextView) findViewById(R.id.tvTitleMD);
        tvOverview = (TextView) findViewById(R.id.tvOverviewMD);
        rbVoteAverage = (RatingBar) findViewById(R.id.rbVoteAverage);
        ivPoster = (ImageView) findViewById(R.id.ivPosterMD);

        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        float voteAverage = movie.getVoteAverage().floatValue();
        rbVoteAverage.setRating(voteAverage/2.0f);

        int radius = 30; //corner radius, higher value = more rounded
        int margin = 10; //crop margin, set to 0 for corners with no crop
        //Glide.with(this).load(movie.getBackdropPath()).centerCrop().transform(new RoundedCornersTransformation(radius, margin)).into(ivPoster);
        Glide.with(this).load(movie.getBackdropPath()).into(ivPoster);
    }
}