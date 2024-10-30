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

import ro.ase.ie.g1106_s05.R;
import ro.ase.ie.g1106_s05.model.Movie;

public class MainActivity extends AppCompatActivity {


    ActivityResultLauncher<Intent> activityLauncher;
    ListView lvMovies;

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

        initializeControls();
        initializeEvents();
    }

    private void initializeEvents() {
        activityLauncher = registerForActivityResult(new ActivityResultContract<Intent, Movie>() {
            @NonNull
            @Override
            public Intent createIntent(@NonNull Context context, Intent intent) {
                Log.d("MainActivity", "createIntent");
                return intent;
            }

            @Override
            public Movie parseResult(int resultCode, @Nullable Intent intent) {
                return null;
            }
        }, new ActivityResultCallback<Movie>() {
            @Override
            public void onActivityResult(Movie o) {

            }
        });
    }

    private void initializeControls() {
        lvMovies = findViewById(R.id.lvMovies);
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
}