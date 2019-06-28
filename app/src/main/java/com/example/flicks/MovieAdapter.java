package com.example.flicks;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.flicks.models.Config;
import com.example.flicks.models.Movie;

import org.parceler.Parcels;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    // list of movies
    ArrayList<Movie> movies;

    //config needed for image
    Config config;

    //context for rendering
    Context context;

    //intialize with list

    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    public void setConfig(Config config) {
        this.config = config;
    }

    @NonNull

    //creates and inflates a new view
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        //get the inflater
        context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //create the view
        View movieView = inflater.inflate(R.layout.item_movie, viewGroup, false);
        //return a new Viewholder
        return new ViewHolder(movieView);
    }

    // binds view to item
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //get the data at specific position
        Movie movie = movies.get(i);
        //populate view with data from movie
        viewHolder.tvTitle.setText(movie.getTitle());
        viewHolder.tvOverview.setText(movie.getOverview());

        // determine orientation of the phone
        boolean isPotrait = context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;

        // build url for config object
        String imageUrl = null;

        if (isPotrait){
            imageUrl = config.getImageUrl(config.getPosterSize(),movie.getPosterPath());
        } else {
            imageUrl = config.getImageUrl(config.getBackdropSize(),movie.getBackDropPath());
        }

        // get a placeholder and imageview for oreintation
        int placeholderId = isPotrait ? R.drawable.flicks_movie_placeholder : R.drawable.flicks_backdrop_placeholder;
        ImageView imageView = isPotrait ? viewHolder.ivPosterImage : viewHolder.ivBackdropImage;
        //load image using glide
        Glide.with(context)
                .load(imageUrl)
                .bitmapTransform(new RoundedCornersTransformation(context,25,0))
                .placeholder(placeholderId)
                .error(placeholderId)
                .into(imageView);


    }

    // returns size of data set
    @Override
    public int getItemCount() {
        return movies.size();
    }

    //create the viewholder class
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // track view objects
        ImageView ivPosterImage;
        ImageView ivBackdropImage;
        TextView tvTitle;
        TextView tvOverview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // lookup view objects by Id

            ivPosterImage = (ImageView) itemView.findViewById(R.id.ivPosterImage);
            ivBackdropImage = (ImageView) itemView.findViewById(R.id.ivBackdropImage);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // gets item position
            int position = getAdapterPosition();
            // make sure the position is valid, i.e. actually exists in the view
            if (position != RecyclerView.NO_POSITION) {
                // get the movie at the position, this won't work if the class is static
                Movie movie = movies.get(position);
                // create intent for the new activity
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                // serialize the movie using parceler, use its short name as a key
                intent.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                // show the activity
                context.startActivity(intent);
            }
        }
    }
}
