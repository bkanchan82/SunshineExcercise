package architech.android.com.sunshineexcercise.data;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.arch.persistence.room.Dao;
import android.support.annotation.Nullable;
import android.util.Log;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;

import java.util.Date;
import java.util.List;

import architech.android.com.sunshineexcercise.data.dao.WeatherDao;
import architech.android.com.sunshineexcercise.data.entities.WeatherEntry;
import architech.android.com.sunshineexcercise.data.network.WeatherNetworkDataSource;
import architech.android.com.sunshineexcercise.utils.AppExecutor;
import architech.android.com.sunshineexcercise.utils.AppExecutors;
import architech.android.com.sunshineexcercise.utils.SunshineDateUtils;

public class WeatherRepository {

    private static final String TAG = WeatherRepository.class.getSimpleName();

    private static WeatherRepository sInstance;

    private final WeatherNetworkDataSource mWeatherNetworkDataSource;
    private final AppExecutor mAppExecutors;
    private final WeatherDao weatherDao;

    private static boolean sInitialized = false;

    private WeatherRepository(WeatherDao weatherDao, WeatherNetworkDataSource weatherNetworkDataSource, AppExecutor appExecutors) {
        mWeatherNetworkDataSource = weatherNetworkDataSource;
        mAppExecutors = appExecutors;
        this.weatherDao = weatherDao;

        mWeatherNetworkDataSource.getWeatherEntries().observeForever(new Observer<WeatherEntry[]>() {
            @Override
            public void onChanged(@Nullable final WeatherEntry[] weatherEntries) {
//                mDb.weatherDao().nukeTable();
                mAppExecutors.getDiskIo().execute(new Runnable() {
                    @Override
                    public void run() {
                       deleteOldData();
                       for(WeatherEntry weatherEntry : weatherEntries){
                            Log.d(TAG,"Weather Date : "+weatherEntry.getDate().getTime()
                            +" Weather Max : "+weatherEntry.getMax()
                            +" Weather Min : "+weatherEntry.getMin()
                            +" Weather id : "+weatherEntry.getWeatherIconId()
                                    +" id : "+weatherEntry.getWeatherIconId());
                       }
                        Log.d(TAG,"Deletion : "+weatherEntries.length);
                        WeatherRepository.this.weatherDao.insertAll(weatherEntries);
//                        Log.d(TAG,"Posted the values"+weatherEntries.length);
                    }
                });

            }
        });
    }

    public synchronized static WeatherRepository getInstance(WeatherDao weatherDao, WeatherNetworkDataSource weatherNetworkDataSource, AppExecutor appExecutors) {
        if (sInstance == null) {
            synchronized (WeatherRepository.class) {
                if(sInstance ==null) {
                    sInstance = new WeatherRepository(weatherDao,
                            weatherNetworkDataSource,
                            appExecutors);
                }
            }
        }
        return sInstance;
    }

    private void deleteOldData() {
        Date today = SunshineDateUtils.getNormalizedUtcDateForToday();
        weatherDao.deleteOldWeather(today);
    }


    public LiveData<List<WeatherEntry>> getTodaysWeather() {
        initializeWeatherData();
        Date date = SunshineDateUtils.getNormalizedUtcDateForToday();
        return weatherDao.getAllWeatherData(date);
    }

    public LiveData<PagedList<WeatherEntry>> getTodaysLiveWeather(boolean isPreferenceChanged ){
        if(isPreferenceChanged){
            startFetchWeatherService();
        }else {
            initializeWeatherData();
        }
        Date date = SunshineDateUtils.getNormalizedUtcDateForToday();
        return new LivePagedListBuilder<>(weatherDao.getLiveWeatherData(date),10).build();
    }

    public void initializeWeatherData() {

        if(sInitialized)return;
        sInitialized = true;

        mWeatherNetworkDataSource.sheduleRecurringJobService();
        mAppExecutors.getDiskIo().execute(new Runnable() {
            @Override
            public void run() {
                if (isFetchNeeded()) {
                    startFetchWeatherService();
                }
            }
        });
    }



    private boolean isFetchNeeded() {

        Date date = SunshineDateUtils.getNormalizedUtcDateForToday();
        return weatherDao.countAllFutureWeather(date) > 0 ? false : true;


    }

    private void startFetchWeatherService() {
        mWeatherNetworkDataSource.startFetchWeatherService();
    }


}
