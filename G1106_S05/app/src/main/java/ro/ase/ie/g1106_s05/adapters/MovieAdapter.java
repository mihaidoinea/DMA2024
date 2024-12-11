package ro.ase.ie.g1106_s05.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ro.ase.ie.g1106_s05.R;
import ro.ase.ie.g1106_s05.activities.MainActivity;
import ro.ase.ie.g1106_s05.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {

    private Map<Integer, ArrayList<Movie>> movieOptions;
    private ArrayList<Movie> collection;
    private Context context;

    public MovieAdapter(ArrayList<Movie> collection, Context context) {
        this.collection = collection;
        this.context = context;
        this.setMovieOptions(new HashMap<>());
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
        Log.d("MovieAdapter", "onBindViewHolder for position: " + position);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
        Movie movie = collection.get(position);
        holder.movieTitle.setText(movie.getTitle());
        holder.movieRelease.setText(sdf.format(movie.getRelease()));
        holder.movieRating.setRating(movie.getRating());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (context instanceof MainActivity) {
                    ((MainActivity) context).onMovieItemClick(holder.getAdapterPosition());
                }
            }
        });

        holder.movieOptions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                movieOptions.computeIfAbsent(checkedId, integer -> new ArrayList<>()).add(movie);
                if (checkedId == R.id.rbPersist) {
                    ArrayList<Movie> movies = movieOptions.get(R.id.rbExport);
                    if (movies != null && movies.contains(movie))
                        movies.remove(movie);
                }
                if (checkedId == R.id.rbExport) {
                    ArrayList<Movie> movies = movieOptions.get(R.id.rbPersist);
                    if (movies != null && movies.contains(movie))
                        movies.remove(movie);
                }
            }
        });

        /*String name = "sherlock_holmes";
        int identifier = context.getResources()
                .getIdentifier(name, "drawable", context.getPackageName());
        holder.moviePoster.setImageResource(identifier);*/
    }

    @Override
    public int getItemCount() {
        return collection.size();
    }

    public Map<Integer, ArrayList<Movie>> getMovieOptions() {
        return movieOptions;
    }

    public void setMovieOptions(Map<Integer, ArrayList<Movie>> movieOptions) {
        this.movieOptions = movieOptions;
    }
}
