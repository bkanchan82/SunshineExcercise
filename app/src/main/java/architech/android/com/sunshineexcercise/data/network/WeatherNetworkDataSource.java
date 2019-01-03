package architech.android.com.sunshineexcercise.data.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import architech.android.com.sunshineexcercise.R;
import architech.android.com.sunshineexcercise.data.SunshinePreferences;
import architech.android.com.sunshineexcercise.data.entities.WeatherEntry;
import architech.android.com.sunshineexcercise.data.network.retrofit.ListItem;
import architech.android.com.sunshineexcercise.data.network.retrofit.Temp;
import architech.android.com.sunshineexcercise.data.network.retrofit.Weather;
import architech.android.com.sunshineexcercise.data.network.retrofit.WeatherData;
import architech.android.com.sunshineexcercise.data.network.retrofit.WeatherRetrofitClient;
import architech.android.com.sunshineexcercise.utils.AppExecutor;
import architech.android.com.sunshineexcercise.utils.SunshineDateUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherNetworkDataSource {

    private static final String TAG = WeatherNetworkDataSource.class.getSimpleName();

    private MutableLiveData<WeatherEntry[]> mWeatherEntries;

    private static final Object LOCK = new Object();
    private static WeatherNetworkDataSource sInstance;
    private final Context mContext;
    private final AppExecutor mAppExecutor;

    private WeatherNetworkDataSource(Context context, AppExecutor appExecutor) {
        mContext = context;
        mAppExecutor = appExecutor;
        mWeatherEntries = new MutableLiveData<>();
    }


    public static WeatherNetworkDataSource getInstance(Context context, AppExecutor appExecutor) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new WeatherNetworkDataSource(context, appExecutor);
            }
        }
        return sInstance;
    }

    public void fetchWeather() {

        mAppExecutor.getNetworkIo().execute(new Runnable() {
            @Override
            public void run() {
                Retrofit.Builder builder = new Retrofit.Builder()
                        .baseUrl("https://andfun-weather.udacity.com")
                        .addConverterFactory(GsonConverterFactory.create());
                Retrofit retrofit = builder.build();
                WeatherRetrofitClient weatherRetrofitClient = retrofit.create(WeatherRetrofitClient.class);
                String location = SunshinePreferences.getPreferredWeatherLocation(mContext);
                String unit = mContext.getString(R.string.pref_units_metric);
                if (!SunshinePreferences.isMetric(mContext)) {
                    unit = mContext.getString(R.string.pref_units_imperial);
                }
                Call<WeatherData> call = weatherRetrofitClient.weatherDataForLocation(location, "json", unit, 14);

                call.enqueue(new Callback<WeatherData>() {
                    @Override
                    public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                        WeatherData weatherData = response.body();

                        List<ListItem> inprocessLists = weatherData.getList();

                        WeatherEntry[] weatherEntries = new WeatherEntry[inprocessLists.size()];
                        long normalizedUtcStartDay = SunshineDateUtils.getNormalizedUtcMsForToday();

                        int i = 0;
                        for (ListItem listItem : inprocessLists) {
                            Log.d(TAG, "In LIST : " + listItem.getHumidity());
                            Temp temp = listItem.getTemp();
                            List<Weather> weathers = listItem.getWeather();
                            int iconId = 0;
                            if (weathers != null && weathers.size() > 0) {
                                iconId = weathers.get(0).getId();
                            }
                            long dateTimeMillis = normalizedUtcStartDay + SunshineDateUtils.DAY_IN_MILLIS * i;
                            Date date = new Date(dateTimeMillis);
                            weatherEntries[i] = new WeatherEntry(iconId, date, temp.getMin(), temp.getMax());
                            i++;
                        }

                        mWeatherEntries.postValue(weatherEntries);
                    }

                    @Override
                    public void onFailure(Call<WeatherData> call, Throwable t) {

                        Log.e(TAG, "Error message : " + t.getMessage());
                    }
                });

            }
        });
    }

    public LiveData<WeatherEntry[]> getWeatherEntries() {
        return mWeatherEntries;
    }


    public void sheduleRecurringJobService() {

        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(mContext));
        Job myJob = dispatcher.newJobBuilder()
                // the JobService that will be called
                .setService(SunshineFirebaseJobService.class)
                // uniquely identifies the job
                .setTag("my-unique-tag")
                // one-off job
                .setRecurring(true)
                // don't persist past a device reboot
                .setLifetime(Lifetime.UNTIL_NEXT_BOOT)
                // start between 0 and 60 seconds from now
                .setTrigger(Trigger.executionWindow(0, 180))
                // don't overwrite an existing job with the same tag
                .setReplaceCurrent(true)
                // retry with exponential backoff
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                // constraints that need to be satisfied for the job to run
                .setConstraints(
                        // only run on an unmetered network
                        Constraint.ON_UNMETERED_NETWORK,
                        // only run when the device is charging
                        Constraint.DEVICE_CHARGING
                )
                .build();
        Log.d(TAG,"Job is scheduling");
        dispatcher.mustSchedule(myJob);

    }

    public void startFetchWeatherService() {
        Intent intentToFetch = new Intent(mContext, SunshineNetworkIntentService.class);
        mContext.startService(intentToFetch);
        Log.d(TAG, "Service created");
    }
}
