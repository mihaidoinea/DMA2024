package ro.ase.ie.g1096_s05.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import ro.ase.ie.g1096_s05.model.Movie;

@Database(version = 1, entities = {Movie.class})
public abstract class DatabaseManager extends RoomDatabase {

    private static final String DB_NAME = "movies.db";
    private static DatabaseManager databaseManager;

    public static DatabaseManager getInstance(Context context) {
        if (databaseManager == null) {
            synchronized (DatabaseManager.class) {
                if (databaseManager == null) {
                    databaseManager = Room.databaseBuilder(context, DatabaseManager.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return databaseManager;
    }

    public abstract MovieDao getMovieReference();

}
