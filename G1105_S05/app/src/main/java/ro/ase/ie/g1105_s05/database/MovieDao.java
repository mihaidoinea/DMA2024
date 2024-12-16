package ro.ase.ie.g1105_s05.database;

import static android.icu.text.MessagePattern.ArgType.SELECT;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Upsert;

import ro.ase.ie.g1105_s05.model.Movie;

@Dao
public interface MovieDao {

    @Insert
    long insert(Movie movie);

    @Query("SELECT movieId FROM movie WHERE title = :title AND release = :release")
    long getMovieByTitleAndRelease(String title, Long release);

    @Upsert
    long upsert(Movie movie);

}
