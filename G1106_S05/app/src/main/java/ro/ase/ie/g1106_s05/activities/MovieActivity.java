package ro.ase.ie.g1106_s05.activities;

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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    Movie movie = null;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
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
/*        Bundle extras = intent.getExtras();
        extras.getString("keyname");*/
        movie = intent.getParcelableExtra("movieKey");
        if (movie == null) {
            movie = new Movie();
            movie.setGenre(GenreEnum.valueOf(spGenre.getSelectedItem().toString()));
            movie.setDuration(sbDuration.getProgress());
            movie.setRecommended(swRecommended.isChecked());
            movie.setRating(rbRating.getRating());
        } else {
            etTitle.setText(movie.getTitle());
            etBudget.setText(movie.getBudget().toString());
            etRelease.setText(sdf.format(movie.getRelease()));
            etPoster.setText(movie.getPosterUrl());
            sbDuration.setProgress(movie.getDuration());
            int pos = 0;
            for (int i = 0; i < GenreEnum.values().length; i++) {
                if (movie.getGenre() == GenreEnum.values()[i])
                    pos = i;
            }
            spGenre.setSelection(pos);
            rbRating.setRating(movie.getRating());
            swRecommended.setChecked(movie.getRecommended());
            movieAction.setText("Update");
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
                DatePickerDialog dpd = new DatePickerDialog(MovieActivity.this, onDateSetListener,
                        calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

                dpd.show();
            }
        });

        rbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                Toast.makeText(MovieActivity.this, "Rating: " + rating, Toast.LENGTH_LONG).show();
                movie.setRating(rating);
            }
        });
        sbDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Toast.makeText(MovieActivity.this, "Duration: " + progress, Toast.LENGTH_SHORT).show();
                movie.setDuration(progress);
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
                movie.setGenre(GenreEnum.valueOf(parent.getSelectedItem().toString()));
//                movie.setGenre(GenreEnum.values()[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        swRecommended.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(MovieActivity.this, "Recommended: " + isChecked, Toast.LENGTH_SHORT).show();
                movie.setRecommended(isChecked);
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
                movie.setApproval(pae);
            }
        });

        movieAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movie.setTitle(etTitle.getText().toString());
                movie.setBudget(Double.parseDouble(etBudget.getText().toString()));
                movie.setPosterUrl(etPoster.getText().toString());

                Intent intent = new Intent();
                intent.putExtra("movieKey", movie);
                setResult(RESULT_OK, intent);
                finish();
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