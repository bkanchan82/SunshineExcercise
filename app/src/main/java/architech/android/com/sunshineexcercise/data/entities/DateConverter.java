package architech.android.com.sunshineexcercise.data.entities;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

public class DateConverter {

    @TypeConverter
    public Long toTimestamp(Date date){
        return date.getTime();
    }

    @TypeConverter
    public Date toDate(Long timeStamp){
        return new Date(timeStamp);
    }
}
