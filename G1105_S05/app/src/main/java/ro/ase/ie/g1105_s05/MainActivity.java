package ro.ase.ie.g1105_s05;

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
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    ActivityResultLauncher<Intent> activityResultLauncher;
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

        initiliazeEvents();
        initializeControls();
    }

    private void initializeControls() {
        lvMovies = findViewById(R.id.lvMovies);
    }

    private void initiliazeEvents() {

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContract<Intent, Movie>() {
                    @NonNull
                    @Override
                    public Intent createIntent(@NonNull Context context, Intent intent) {
                        Log.d("MainActivity", "createIntent");
                        return intent;
                    }

                    @Override
                    public Movie parseResult(int i, @Nullable Intent intent) {
                        Log.d("MainActivity", "parseResult");
                        return new Movie("Test",23.2, 32, (byte) 2,new Date(),
                                true, Genre.Action, Status.SEEN);
                    }
                }, new ActivityResultCallback<Movie>() {
                    @Override
                    public void onActivityResult(Movie o) {
                        Log.d("MainActivity", "onActivityResult" + o.toString());
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
        }
        else
            return super.onOptionsItemSelected(item);
    }
}