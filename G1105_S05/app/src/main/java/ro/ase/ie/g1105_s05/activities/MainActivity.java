package ro.ase.ie.g1105_s05.activities;

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
import java.util.Date;
import java.util.List;
import java.util.Map;

import ro.ase.ie.g1105_s05.adapters.MovieAdapter;
import ro.ase.ie.g1105_s05.R;
import ro.ase.ie.g1105_s05.database.DatabaseManager;
import ro.ase.ie.g1105_s05.database.MovieDao;
import ro.ase.ie.g1105_s05.model.Genre;
import ro.ase.ie.g1105_s05.model.IMovieEvents;
import ro.ase.ie.g1105_s05.model.Movie;
import ro.ase.ie.g1105_s05.model.ParentalApprovalEnum;
import ro.ase.ie.g1105_s05.util.JsonUtil;

public class MainActivity extends AppCompatActivity implements IMovieEvents {

    ActivityResultLauncher<Intent> activityResultLauncher;
    ListView lvMovies;
    RecyclerView rvMovies;

    static ArrayList<Movie> movieArrayList = new ArrayList<>();
    MovieAdapter movieAdapter = null;

    DatabaseManager databaseManager;
    MovieDao movieDao;

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

        String jsonFromRes = JsonUtil.getJsonFromResources(this, R.raw.movies);
        ArrayList<Movie> movies = JsonUtil.parseJsonContent(jsonFromRes);
        movieArrayList.addAll(movies);

        movieAdapter = new MovieAdapter(movieArrayList, MainActivity.this);

        initializeControls();

        initiliazeEvents();

        databaseManager = DatabaseManager.getInstance(this);
        movieDao = databaseManager.getMovieDao();

    }

    private void initializeControls() {
        rvMovies = findViewById(R.id.rvMovies);
    }

    private void initiliazeEvents() {

        rvMovies.setAdapter(movieAdapter);

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContract<Intent, Movie>() {
                    @NonNull
                    @Override
                    public Intent createIntent(@NonNull Context context, Intent intent) {
                        Log.d("MainActivity", "createIntent");
                        return intent;
                    }

                    @Override
                    public Movie parseResult(int resultCode, @Nullable Intent intent) {
                        Log.d("MainActivity", "parseResult");
                        if(resultCode == RESULT_OK) {
                            Movie movie = intent.getParcelableExtra("movieKey");
                            return movie;
                        }
                        return null;
                    }
                }, new ActivityResultCallback<Movie>() {
                    @Override
                    public void onActivityResult(Movie movie) {
                        if (movie != null) {
                            Log.d("MainActivity", "onActivityResult" + movie.toString());
                            if(movieArrayList.contains(movie))
                            {
                                int position = movieArrayList.indexOf(movie);
                                movieArrayList.set(position, movie);
                                movieAdapter.notifyItemChanged(position);
                            }
                            else
                            {
                                movieArrayList.add(movie);
                                movieAdapter.notifyItemInserted(movieArrayList.size()-1);
                            }
                        }
                    }
                });
/*        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {

            }
        });*/
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
            Intent intent = new Intent(getApplicationContext(), MovieActivity.class);
            activityResultLauncher.launch(intent);
            return true;
        }
        else if(item.getItemId() == R.id.item_about_app)
        {
            Toast.makeText(MainActivity.this, "G1105 @ DAM-CSIE", Toast.LENGTH_LONG).show();
            return true;
        } else if (item.getItemId() == R.id.persist) {

            Map<Integer, ArrayList<Movie>> movieOptions = movieAdapter.getMovieOptions();
            ArrayList<Movie> movies = movieOptions.get(R.id.rbPersist);
            for(Movie movie:movies) {
                long id = movieDao.getMovieByTitleAndRelease(movie.getTitle(), movie.getRelease().getTime());
                if (id == 0)
                    movieDao.insert(movie);
                else {
                    movie.setMovieId(id);
                    movieDao.upsert(movie);
                }
            }
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieItemClick(int position) {
        Movie movie = movieArrayList.get(position);
        Intent intent = new Intent(getApplicationContext(), MovieActivity.class);
        intent.putExtra("movieKey", movie);
        activityResultLauncher.launch(intent);
    }
}