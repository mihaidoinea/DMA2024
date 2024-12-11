package ro.ase.ie.g1096_s05.activities;

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

import ro.ase.ie.g1096_s05.R;
import ro.ase.ie.g1096_s05.adapters.MovieAdapter;
import ro.ase.ie.g1096_s05.database.DatabaseManager;
import ro.ase.ie.g1096_s05.database.MovieDao;
import ro.ase.ie.g1096_s05.model.IMovieItemEvents;
import ro.ase.ie.g1096_s05.model.Movie;
import util.JsonUtil;

public class MainActivity extends AppCompatActivity implements IMovieItemEvents {

    private ActivityResultLauncher<Intent> activityLauncher;
    //    private ListView lvMovies;
    private RecyclerView rvMovies;
    private static ArrayList<Movie> movieArrayList = new ArrayList<>();
    private MovieAdapter movieAdapter;

    private DatabaseManager databaseManager;
    private MovieDao movieDao;

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

        databaseManager = DatabaseManager.getInstance(this);
        movieDao = databaseManager.getMovieReference();

        String jsonFromResources = JsonUtil.getJsonFromResources(this, R.raw.movies);
        ArrayList<Movie> movies = JsonUtil.parseJsonContent(jsonFromResources);
        movieArrayList.addAll(movies);


        movieAdapter = new MovieAdapter(MainActivity.this, movieArrayList);
        rvMovies = findViewById(R.id.rvMovies);
        rvMovies.setAdapter(movieAdapter);

        activityLauncher = registerForActivityResult(new ActivityResultContract<Intent, Movie>() {
            @NonNull
            @Override
            public Intent createIntent(@NonNull Context context, Intent intent) {
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

                    if (!movieArrayList.contains(movie)) {
                        Log.d("MainActivity", "Movie: " + movie);
                        movieArrayList.add(movie);
                        movieAdapter.notifyItemInserted(movieArrayList.size() - 1);
                    } else {
                        Log.d("MainActivity", "Duplicate Movie: " + movie);
                        int index = movieArrayList.indexOf(movie);
                        movieArrayList.set(index, movie);
                        movieAdapter.notifyItemChanged(index);
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.movie_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.item_add_movie)
        {
            Intent intent = new Intent(getApplicationContext(), MovieActivity.class);
            activityLauncher.launch(intent);
            return true;
        }
        else if(item.getItemId() == R.id.item_about)
        {
            Toast.makeText(MainActivity.this, "CSIE - DAM @ 2024", Toast.LENGTH_LONG).show();
            return true;
        } else if (item.getItemId() == R.id.item_persist_movie) {
            ArrayList<Movie> movies = movieAdapter.getMovieOptions().get(R.id.rbPersist);
            for (Movie movie : movies) {
                movieDao.insert(movie);
            }
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieItemClick(int index) {
        Intent intent = new Intent(getApplicationContext(), MovieActivity.class);
        Movie movie = movieArrayList.get(index);
        intent.putExtra("movieKey", movie);
        activityLauncher.launch(intent);
    }
}