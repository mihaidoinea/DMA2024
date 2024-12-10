package ro.ase.ie.g1105_s05.database;

import androidx.room.Dao;
import androidx.room.Insert;

import ro.ase.ie.g1105_s05.model.Movie;

@Dao
public interface MovieDao {

    @Insert
    long insert(Movie movie);

}
