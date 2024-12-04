package ro.ase.ie.g1096_s05.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ro.ase.ie.g1096_s05.R;
import ro.ase.ie.g1096_s05.model.GenreEnum;
import ro.ase.ie.g1096_s05.model.Movie;
import ro.ase.ie.g1096_s05.model.ParentalApprovalEnum;

public class MovieActivity extends AppCompatActivity {

    private EditText etTitle;
    private EditText etBudget;
    private EditText etRelease;
    private EditText etPoster;
    private Switch swRecommended;
    private Spinner spGenre;
    private SeekBar sbDuration;
    private RadioGroup rgApproval;
    private Button movieAction;
    private RatingBar rbRating;

    Movie movie;
    Calendar calendar;
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
        calendar = Calendar.getInstance();

        initializeControls();

        initializeEvents();

        //test to see a parameter has been received for updating it
        Intent intent = getIntent();
        movie = intent.getParcelableExtra("movieKey");
        //if not it means we add a new movie instance to the collection
        if (movie == null) {
            movie = new Movie();
            movie.setGenre(GenreEnum.valueOf(spGenre.getSelectedItem().toString()));
            movie.setGenre(GenreEnum.values()[spGenre.getSelectedItemPosition()]);
            movie.setDuration(sbDuration.getProgress());
            movie.setRating(rbRating.getRating());
            movie.setParentalApproval(ParentalApprovalEnum.G);
            movie.setRecommended(false);
        } else {
            etTitle.setText(movie.getTitle());
            etBudget.setText(movie.getBudget().toString());
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
            etRelease.setText(sdf.format(movie.getRelease()));
            etPoster.setText(movie.getPosterUrl());
            rbRating.setRating(movie.getRating());
            swRecommended.setChecked(movie.getRecommended());
            sbDuration.setProgress(movie.getDuration());
            int position = movie.getGenre().ordinal();
            spGenre.setSelection(position);
            int id = R.id.rbParentGuidance;
            switch (movie.getParentalApproval()) {
                case PG:
                    id = R.id.rbParentGuidance;
                    break;
                case PG13:
                    id = R.id.rbPG13;
                    break;
                case R:
                    id = R.id.rbR;
                    break;
                case NC17:
                    id = R.id.rbNC17;
                    break;
                case G:
                    id = R.id.rbGeneral;
                    break;
            }
            rgApproval.check(id);
        }
        movieAction.setText("Update");

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
                        calendar.set(Calendar.MILLISECOND, 0);

                        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMMM-yyyy");
                        String date = sdf.format(calendar.getTime());
                        etRelease.setText(date);

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
        swRecommended.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                movie.setRecommended(isChecked);
            }
        });

        spGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                movie.setGenre(GenreEnum.valueOf(parent.getSelectedItem().toString()));
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
        rgApproval.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ParentalApprovalEnum pae = null;
                if (checkedId == R.id.rbGeneral)
                    pae = ParentalApprovalEnum.G;
                else if (checkedId == R.id.rbNC17)
                    pae = ParentalApprovalEnum.NC17;
                else if (checkedId == R.id.rbR)
                    pae = ParentalApprovalEnum.R;
                else if (checkedId == R.id.rbPG13)
                    pae = ParentalApprovalEnum.PG13;
                else if (checkedId == R.id.rbParentGuidance)
                    pae = ParentalApprovalEnum.PG;
                movie.setParentalApproval(pae);
            }
        });
        rbRating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                movie.setRating(rating);
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

              /*  Bundle extras = intent.getExtras();
                extras.putParcelable("movieKey", movie);*/
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    private void initializeControls() {
        etTitle = findViewById(R.id.etTitle);
        etPoster = findViewById(R.id.etPoster);
        etBudget = findViewById(R.id.etBudget);
        etRelease = findViewById(R.id.etRelease);
        spGenre = findViewById(R.id.spGenre);
        sbDuration = findViewById(R.id.sbDuration);
        rgApproval = findViewById(R.id.rgApproval);
        swRecommended = findViewById(R.id.swRecommended);
        rbRating = findViewById(R.id.rbRating);
        movieAction = findViewById(R.id.btnMovieAction);
    }
}