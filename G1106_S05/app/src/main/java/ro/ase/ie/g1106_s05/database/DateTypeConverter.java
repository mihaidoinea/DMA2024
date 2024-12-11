package ro.ase.ie.g1106_s05.database;

import androidx.room.TypeConverter;

import java.util.Date;


public class DateTypeConverter {

    @TypeConverter
    public Long DateToLong(Date release) {
        return release == null ? null : release.getTime();
    }

    @TypeConverter
    public Date LongToDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }
}
