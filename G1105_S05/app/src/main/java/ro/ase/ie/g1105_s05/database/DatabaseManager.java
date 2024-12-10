package ro.ase.ie.g1105_s05.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ro.ase.ie.g1105_s05.model.Movie;

@Database(entities = {Movie.class}, exportSchema = false, version = 2)
public abstract class DatabaseManager extends RoomDatabase{

    private static final String DB_NAME = "movies_db";
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager != null) {
            return databaseManager;
        } else {
            synchronized (DatabaseManager.class) {
                if (databaseManager == null) {
                    databaseManager = Room
                            .databaseBuilder(context, DatabaseManager.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
                return databaseManager;
            }
        }
    }

    public abstract MovieDao getMovieDao();
}
