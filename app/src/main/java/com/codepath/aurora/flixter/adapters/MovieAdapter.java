package com.codepath.aurora.flixter.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.aurora.flixter.R;
import com.codepath.aurora.flixter.models.Movie;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{
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
        Log.d("MovieAdapter", "onBindViewHolder "+position);
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

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageURL;
            //Choosing the URL of the image, depending in the orientation of the device
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageURL =  movie.getBackdropPath();
            }else{
                imageURL = movie.getPosterPath();
            }

            // We add the image (that has the path described in imageURL) into the ivPoster with our known context
            Glide.with(context)
                    .load(imageURL)
                    //.placeholder() -------------------------------------------------------------------- add section 2
                    //.placeholder(R.drawable.placeholder)
                    //.error(R.drawable.imagenotfound)
                    .into(ivPoster);

        }
    }
}
