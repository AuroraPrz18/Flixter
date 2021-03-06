package com.codepath.aurora.flixter.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.aurora.flixter.MovieDetailsActivity;
import com.codepath.aurora.flixter.R;
import com.codepath.aurora.flixter.databinding.ItemMovieBinding;
import com.codepath.aurora.flixter.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    Context context; // We will need it to know where the adapter is being constructed from
    List<Movie> movies; // Data

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    // Is where we decide which layout is going to represent each item
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Involves populating data into the item through holder
    // Having a layout, specify what information is going to show in each of their views
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        //Get the movie at the position
        Movie movie = movies.get(position);
        //Bind the movie data into the VH
        holder.bind(movie);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ItemMovieBinding binding;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            binding = ItemMovieBinding.bind(itemView);
            itemView.setOnClickListener(this); // Sets its own onClick method to be call when it is clicked
        }

        public void bind(Movie movie) {
            binding.tvTitle.setText(movie.getTitle());
            binding.tvOverview.setText(movie.getOverview());
            String imageURL;
            int radius = 30; //corner radius, higher value = more rounded
            int margin = 10; //crop margin, set to 0 for corners with no crop

            //Choosing the URL of the image, depending in the orientation of the device
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageURL = movie.getBackdropPath();
                // We add the image (that has the path described in imageURL) into the ivPoster with our known context
                Glide.with(context).load(imageURL)
                        .transform(new RoundedCornersTransformation(radius, margin))
                        .placeholder(R.drawable.flicks_backdrop_placeholder).
                        into(binding.ivPoster);
            } else {
                imageURL = movie.getPosterPath();
                // We add the image (that has the path described in imageURL) into the ivPoster with our known context
                Glide.with(context).load(imageURL)
                        .transform(new RoundedCornersTransformation(radius, margin))
                        .placeholder(R.drawable.flicks_movie_placeholder)
                        .into(binding.ivPoster);
            }

            //Choosing the ivVote_average icon source, depending in the vote_average
            if (movie.getVoteAverage() <= 4) {
                binding.ivVoteAverage.setImageResource(R.drawable.ic_bad_24);
            } else if (movie.getVoteAverage() <= 7) {
                binding.ivVoteAverage.setImageResource(R.drawable.ic_soso_24);
            } else {
                binding.ivVoteAverage.setImageResource(R.drawable.ic_good_24);
            }
        }

        @Override
        public void onClick(View v) {
            //Gets item position
            int position = getAdapterPosition();
            //Make sure the position is valid
            if (position != RecyclerView.NO_POSITION) {
                // Get the movie at the position, this won't work if the class is static
                Movie movieSelected = movies.get(position);
                //Create intent for the new Activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                //Serialize the movie using parceler, use its short name as a key, the movie is passed as a extra serialized
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movieSelected));
                // Show the activity
                context.startActivity(intent);


            } else {
                Toast.makeText(context, "Something is going wrong, choose another movie", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
