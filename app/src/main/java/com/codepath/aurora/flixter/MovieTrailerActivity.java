package com.codepath.aurora.flixter;

import android.os.Bundle;
import android.util.Log;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class MovieTrailerActivity extends YouTubeBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);

        // temporary test video id
        String videoIdAux = "";
        if (getIntent() != null) {
            videoIdAux = getIntent().getStringExtra("Key");
        }
        final String videoId = videoIdAux;


        //resolve the player view from layout
        YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);

        //Initialize with API key stored in secrets.xml
        playerView.initialize(getString(R.string.api_key_youtube), new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                // Do anywork here to cue video, play video, etc.
                youTubePlayer.cueVideo(videoId);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                // Log the error
                Log.e("MovieTrailerActivity", "Error initializing YouTube player");
            }
        });

    }
}