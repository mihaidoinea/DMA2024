package ro.ase.ie.g1106_s05.adapters;

import android.media.Rating;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ro.ase.ie.g1106_s05.R;

public class MovieHolder extends RecyclerView.ViewHolder {

    TextView movieTitle;
    TextView movieRelease;
    Button movieDelete;
    RadioGroup movieOptions;
    ImageView moviePoster;
    RatingBar movieRating;

    public MovieHolder(@NonNull View itemView) {
        super(itemView);
        movieTitle = itemView.findViewById(R.id.movieTitle);
        movieRelease = itemView.findViewById(R.id.movieRelease);
        movieDelete = itemView.findViewById(R.id.movieDelete);
        movieOptions = itemView.findViewById(R.id.movieOptions);
        moviePoster = itemView.findViewById(R.id.moviePoster);
        movieRating = itemView.findViewById(R.id.movieRating);
    }
}
