package ro.ase.ie.dma11;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private FirebaseService firebaseService;
    private List<Movie> movieList = new ArrayList<>();
    private MovieAdapter movieAdapter;
    private RecyclerView recyclerView;
    private Callback<List<Movie>> dataChangeCallback()
    {
        return new Callback<List<Movie>>() {
            @Override
            public void runResultOnUiThread(List<Movie> result) {
                if(result != null)
                {
                    movieList.clear();
                    movieList.addAll(result);
                    Log.d(TAG, "Movie" + movieList);
                    movieAdapter = new MovieAdapter(MainActivity.this, movieList);
                    recyclerView.setAdapter(movieAdapter);
                }
            }
        };
    }

    public void readJsonMovies() {
        String jsonMovieArray = JsonUtil.getJsonFromResource(this, R.raw.movies_json);
        movieList = JsonUtil.readJson(jsonMovieArray);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        readJsonMovies();

        recyclerView = findViewById(R.id.recyclerView);
        //writing to the Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("movies");

        for(Movie movie: movieList)
        {
            int id = new Random().nextInt(99999999);
            movie.setMovieId("movie_" + id);
            myRef.child(movie.getMovieId()).setValue(movie);
        }

        //reading from the Firebase
        firebaseService = FirebaseService.getInstance();
        firebaseService.attachDataChangeEventListener(dataChangeCallback());

    }

    public void movieClick(Movie movie)
    {
        movie.setTitle("New Movie Title");
        firebaseService.upsert(movie);
    }

    public void update(View view)
    {
        Movie movie = movieList.get(0);
        movie.setTitle("New Title");
        firebaseService.upsert(movie);
    }
}