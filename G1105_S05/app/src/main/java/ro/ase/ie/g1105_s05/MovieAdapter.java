package ro.ase.ie.g1105_s05;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {

    ArrayList<Movie> collection;
    Context context;

    public MovieAdapter(ArrayList<Movie> collection, Context context) {
        this.collection = collection;
        this.context = context;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView = layoutInflater.inflate(R.layout.movie_item, parent, false);
        return new MovieHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {
        Log.d("MovieAdapter","onBindViewHolder for" + position);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
        Movie movie = collection.get(position);
        holder.movieTitle.setText(movie.getTitle());
        holder.movieRating.setRating(movie.getRating());
        holder.movieRelease.setText(sdf.format(movie.getRelease()));

        String name = "sherlock_holmes";
        int drawable = context.getResources()
                .getIdentifier(name, "drawable", context.getPackageName());
        holder.moviePoster.setImageResource(drawable);

    }

    @Override
    public int getItemCount() {
        return collection.size();
    }
}
