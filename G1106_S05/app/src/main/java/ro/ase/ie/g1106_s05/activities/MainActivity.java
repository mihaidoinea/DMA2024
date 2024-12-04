package ro.ase.ie.g1106_s05.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ro.ase.ie.g1106_s05.R;
import ro.ase.ie.g1106_s05.adapters.MovieAdapter;
import ro.ase.ie.g1106_s05.model.IMovieEvents;
import ro.ase.ie.g1106_s05.model.Movie;
import util.JsonUtil;

public class MainActivity extends AppCompatActivity implements IMovieEvents {


    ActivityResultLauncher<Intent> activityLauncher;
    //    ListView lvMovies;
    RecyclerView rvMovies;

    static ArrayList<Movie> movieList = new ArrayList<>();
    MovieAdapter movieAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String jsonFromResources = JsonUtil.getJsonFromResources(this, R.raw.movies);
        ArrayList<Movie> movies = JsonUtil.parseJsonContent(jsonFromResources);
        movieList.addAll(movies);

        movieAdapter = new MovieAdapter(movieList, MainActivity.this);

        initializeControls();

        initializeEvents();
    }

    private void initializeEvents() {

        rvMovies.setAdapter(movieAdapter);

        activityLauncher = registerForActivityResult(new ActivityResultContract<Intent, Movie>() {
            @NonNull
            @Override
            public Intent createIntent(@NonNull Context context, Intent intent) {
                Log.d("MainActivity", "createIntent");
                return intent;
            }

            @Override
            public Movie parseResult(int resultCode, @Nullable Intent intent) {
                Movie movie = null;
                if (resultCode == RESULT_OK) {
                    movie = intent.getParcelableExtra("movieKey");
                }
                return movie;
            }
        }, new ActivityResultCallback<Movie>() {
            @Override
            public void onActivityResult(Movie movie) {
                if (movie != null) {
                    Log.d("MainActivity", "Movie: " + movie);
                    int position = movieList.indexOf(movie);
                    if (movieList.contains(movie)) {
                        Log.d("MainActivity", "Duplicate: " + movie);
                        movieList.set(position, movie);
                        movieAdapter.notifyItemChanged(position);
                    } else {
                        movieList.add(movie);
                        movieAdapter.notifyItemInserted(movieList.size());
                    }
                }
            }
        });
    }

    private void initializeControls() {
//        lvMovies = findViewById(R.id.lvMovies);
        rvMovies = findViewById(R.id.rvMovies);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.item_add_movie)
        {
            Intent secondActIntent = new Intent(getApplicationContext(), MovieActivity.class);
            activityLauncher.launch(secondActIntent);
            return true;
        }
        else if(item.getItemId() == R.id.item_about)
        {
            Toast.makeText(MainActivity.this, "CSIE - DMA @ 2024", Toast.LENGTH_LONG).show();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieItemClick(int index) {
        Intent secondActIntent = new Intent(getApplicationContext(), MovieActivity.class);
        Movie movie = movieList.get(index);
        secondActIntent.putExtra("movieKey", movie);
        activityLauncher.launch(secondActIntent);
    }
}