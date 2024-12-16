package ro.ase.ie.g1096_s05.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import ro.ase.ie.g1096_s05.model.Movie;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Movie movie);

    @Query("SELECT movieId FROM movie_table WHERE rowid = :rowId")
    int getMovieId(long rowId);
}
