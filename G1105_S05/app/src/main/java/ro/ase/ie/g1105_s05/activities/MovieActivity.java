package ro.ase.ie.g1105_s05.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ro.ase.ie.g1105_s05.R;
import ro.ase.ie.g1105_s05.model.Genre;
import ro.ase.ie.g1105_s05.model.Movie;
import ro.ase.ie.g1105_s05.model.ParentalApprovalEnum;

public class MovieActivity extends AppCompatActivity  {

    private EditText etTitle;
    private EditText etBudget;
    private EditText etRelease;
    private EditText etPoster;
    private Spinner spGenre;
    private SeekBar sbDuration;
    private RatingBar rbRating;
    private Switch swRecommended;
    private RadioGroup rgApproval;
    private Button btnMovieAction;
    private String MovieTag = MovieActivity.class.getSimpleName();
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");

    private Movie movie = null;
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

        Intent intent = getIntent();
        movie = intent.getParcelableExtra("movieKey");
        if(movie == null)
        {
            movie = new Movie();
            movie.setRating(rbRating.getRating());
            movie.setDuration(sbDuration.getProgress());
            movie.setGenre(Genre.valueOf(spGenre.getSelectedItem().toString()));
            movie.setRecommended(swRecommended.isChecked());
        } else {
            etTitle.setText(movie.getTitle());
            etBudget.setText(movie.getBudget().toString());
            etRelease.setText(sdf.format(movie.getRelease()));
            etPoster.setText(movie.getPosterUrl());
            spGenre.setSelection(movie.getGenre().ordinal());
            sbDuration.setProgress(movie.getDuration());
            rbRating.setRating(movie.getRating());
            swRecommended.setChecked(movie.getRecommended());
            int id = R.id.rbGeneral;
            switch (movie.getStatus()) {
                case PG:
                    id = R.id.rbParentGuidance;
                    break;
                case G:
                    id = R.id.rbGeneral;
                    break;
                case R:
                    id = R.id.rbR;
                    break;
                case NC17:
                    id = R.id.rbNC17;
                    break;
                case PG13:
                    id = R.id.rbPG13;
                    break;
            }
            rgApproval.check(id);
            btnMovieAction.setText("Update");

        }

    }

    private void initializeEvents() {
        etRelease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        calendar.set(Calendar.HOUR, 0);
                        calendar.set(Calendar.MINUTE, 0);
                        calendar.set(Calendar.SECOND, 0);

                        etRelease.setText(sdf.format(calendar.getTime()));

                        movie.setRelease(calendar.getTime());
                    }
                };
                DatePickerDialog dpd = new DatePickerDialog(MovieActivity.this,
                        onDateSetListener,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });

        spGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String genre = "Genre: " + parent.getSelectedItem();
                Toast.makeText(MovieActivity.this, genre, Toast.LENGTH_SHORT).show();
                movie.setGenre(Genre.valueOf(parent.getSelectedItem().toString()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sbDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                movie.setDuration(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        rbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                movie.setRating(rating);
            }
        });

        swRecommended.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                movie.setRecommended(isChecked);
            }
        });

        rgApproval.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ParentalApprovalEnum status = null;
                if(checkedId == R.id.rbGeneral)
                    status = ParentalApprovalEnum.G;
                else if(checkedId == R.id.rbNC17)
                    status = ParentalApprovalEnum.NC17;
                else if(checkedId == R.id.rbR)
                    status = ParentalApprovalEnum.R;
                else if(checkedId == R.id.rbPG13)
                    status = ParentalApprovalEnum.PG13;
                else if(checkedId == R.id.rbParentGuidance)
                    status = ParentalApprovalEnum.PG;
                movie.setStatus(status);
            }
        });

        btnMovieAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(MovieTag, "Action triggered for the movie.");
                String title = etTitle.getText().toString();
                String poster = etPoster.getText().toString();

                movie.setPosterUrl(poster);
                movie.setTitle(title);
                movie.setBudget(Double.parseDouble(etBudget.getText().toString()));

                Log.d("MovieActivity", "Movie: " + movie);

                    Intent intent =new Intent();
                    intent.putExtra("movieKey", movie);
                    setResult(RESULT_OK, intent);
                    finish();
                }
        });
    }

    private void initializeControls() {
        etTitle = findViewById(R.id.etTitle);
        etBudget = findViewById(R.id.etBudget);
        etRelease = findViewById(R.id.etRelease);
        etPoster = findViewById(R.id.etPoster);
        swRecommended = findViewById(R.id.swRecommended);
        rbRating = findViewById(R.id.rbRating);
        rgApproval = findViewById(R.id.rgApproval);
        sbDuration = findViewById(R.id.sbDuration);
        spGenre = findViewById(R.id.spGenre);
        btnMovieAction = findViewById(R.id.btnMovieAction);
    }
}