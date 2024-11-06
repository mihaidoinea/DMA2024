package ro.ase.ie.g1106_s05.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Date;

import ro.ase.ie.g1106_s05.R;
import ro.ase.ie.g1106_s05.model.GenreEnum;
import ro.ase.ie.g1106_s05.model.Movie;
import ro.ase.ie.g1106_s05.model.ParentalApprovalEnum;

public class MovieActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etBudget;
    private EditText etRelease;
    private EditText etPoster;
    private Spinner spGenre;
    private SeekBar sbDuration;
    private RadioGroup rgApproval;
    private Switch swRecommended;
    private Button movieAction;
    private RatingBar rbRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_movie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initializeControls();

        initializeEvents();
    }

    private void initializeEvents() {
        rbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(MovieActivity.this, "Rating: " + rating, Toast.LENGTH_LONG).show();
            }
        });
        sbDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Toast.makeText(MovieActivity.this, "Duration: " + progress, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        spGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String genre = "Genre: " + parent.getSelectedItem();
                Toast.makeText(MovieActivity.this, genre, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        swRecommended.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(MovieActivity.this, "Recommended: " + isChecked, Toast.LENGTH_SHORT).show();
            }
        });

        rgApproval.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ParentalApprovalEnum pae = null;
                if (checkedId == R.id.rbGeneral)
                    pae = ParentalApprovalEnum.G;
                if (checkedId == R.id.rbNC17)
                    pae = ParentalApprovalEnum.NC17;
                if (checkedId == R.id.rbPG13)
                    pae = ParentalApprovalEnum.PG13;
                if (checkedId == R.id.rbR)
                    pae = ParentalApprovalEnum.R;
                if (checkedId == R.id.rbParentGuidance)
                    pae = ParentalApprovalEnum.PG;
                Toast.makeText(MovieActivity.this, "Parental approval: " + pae.toString(), Toast.LENGTH_SHORT).show();

            }
        });

        movieAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                Date release = null;
                String poster = "";
                Double budget = 0.0;
                Double duration = 0.0;
                Boolean recommended = null;
                GenreEnum genre = null;
                ParentalApprovalEnum status = null;
                Float rating = 0f;
                Movie movie = new Movie(title, release, duration, recommended, genre, status, poster, budget, rating);
            }
        });
    }

    private void initializeControls() {
        etTitle = findViewById(R.id.etTitle);
        etPoster = findViewById(R.id.etPoster);
        etRelease = findViewById(R.id.etRelease);
        etBudget = findViewById(R.id.etBudget);
        spGenre = findViewById(R.id.spGenre);
        sbDuration = findViewById(R.id.sbDuration);
        rgApproval = findViewById(R.id.rgApproval);
        rbRating = findViewById(R.id.rbRating);
        movieAction = findViewById(R.id.btnMovieAction);
        swRecommended = findViewById(R.id.swRecommended);
    }
}