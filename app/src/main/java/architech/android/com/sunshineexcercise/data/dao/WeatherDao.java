package architech.android.com.sunshineexcercise.data.dao;


import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.Date;
import java.util.List;

import architech.android.com.sunshineexcercise.data.entities.WeatherEntry;

@Dao
public interface WeatherDao {

    @Insert
    void insertAll(WeatherEntry[] weatherEntries);

    @Query("SELECT * FROM weather WHERE date >= :date ORDER BY date")
    DataSource.Factory<Integer,WeatherEntry>  getLiveWeatherData(Date date);

    @Query("SELECT COUNT(id) FROM weather WHERE date >=:date")
    int countAllFutureWeather(Date date);

    @Query("SELECT * FROM weather WHERE date >= :date")
    LiveData<List<WeatherEntry>> getAllWeatherData(Date date);

    @Query("DELETE FROM weather WHERE date < :date")
    void deleteOldWeather(Date date);

}
