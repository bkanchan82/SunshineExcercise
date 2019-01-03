package architech.android.com.sunshineexcercise.data;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import java.security.PublicKey;

import architech.android.com.sunshineexcercise.data.dao.WeatherDao;
import architech.android.com.sunshineexcercise.data.entities.DateConverter;
import architech.android.com.sunshineexcercise.data.entities.WeatherEntry;

@Database(entities = {WeatherEntry.class},version = SunshineDatabase.DATABASE_VERSION,exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class SunshineDatabase extends RoomDatabase {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "sunshine_db";

    private static final Object LOCK = new Object();

    private static SunshineDatabase sInstance;

    public static SunshineDatabase getInstance(Context context){
        if(sInstance == null){
            synchronized (LOCK){
                if(sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            SunshineDatabase.class,
                            SunshineDatabase.DATABASE_NAME).build();
                }
            }
        }
        return sInstance;
    }


    public abstract WeatherDao weatherDao();

}
