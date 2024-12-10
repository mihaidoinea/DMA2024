package ro.ase.ie.g1105_s05.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import ro.ase.ie.g1105_s05.R;
import ro.ase.ie.g1105_s05.activities.MainActivity;
import ro.ase.ie.g1105_s05.model.Movie;
import ro.ase.ie.g1105_s05.networking.DownloadTask;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {

    List<Movie> persistedMovies = new ArrayList<>();
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = (MainActivity) context;
                mainActivity.onMovieItemClick(holder.getAdapterPosition());
            }
        });

        holder.movieDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = collection.indexOf(movie);
                boolean deleted = collection.remove(movie);
                if(deleted)
                {
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Movie item removed!", Toast.LENGTH_LONG).show();
                }
            }
        });

        holder.movieOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.rbPersist)
                {
                    persistedMovies.add(movie);
                }
                else {
                    if (persistedMovies.contains(movie)) {
                        persistedMovies.remove(movie);
                    }
                }
            }
        });

        DownloadTask downloadTask = new DownloadTask(movie.getPosterUrl(), holder.moviePoster);
        Thread thread = new Thread(downloadTask);
        thread.start();
    }

    @Override
    public int getItemCount() {
        return collection.size();
    }

    public List<Movie> getPersistedMovies() {
        return persistedMovies;
    }
}
