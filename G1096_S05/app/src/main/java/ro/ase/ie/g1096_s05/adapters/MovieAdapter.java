package ro.ase.ie.g1096_s05.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ro.ase.ie.g1096_s05.model.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieHolder> {

    private Context context;
    private ArrayList<Movie> collection;

    public MovieAdapter(Context context, ArrayList<Movie> collection) {
        this.context = context;
        this.collection = collection;
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
