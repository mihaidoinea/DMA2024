package ro.ase.ie.g1096_s05.database;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public static Long dateToTimestamp(Date release) {
        return release == null ? null : release.getTime();
    }

    @TypeConverter
    public static Date timeStampToDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }
}
